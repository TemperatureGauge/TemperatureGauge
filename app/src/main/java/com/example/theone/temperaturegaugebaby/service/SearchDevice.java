package com.example.theone.temperaturegaugebaby.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.PopupWindow.OnDismissListener;

import com.example.theone.temperaturegaugebaby.R;
import com.example.theone.temperaturegaugebaby.adapter.myArrayAdapter;
import com.example.theone.temperaturegaugebaby.application.MyApplication;
import com.example.theone.temperaturegaugebaby.service.BLEDevice.RFStarBLEBroadcastReceiver;


public class SearchDevice {
	static SearchDevice searcher;
	private static BluetoothAdapter mBluetoothAdapter;
	private static final long SCANDURATION = Integer.MAX_VALUE;
	//初始化获取实例
	public static SearchDevice getinstance(){
		if (searcher == null) {
			searcher = new SearchDevice();
			mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		}
		
		return searcher;
	}
	
	public BluetoothDevice getDeviceByMac(String mac){
		return mBluetoothAdapter.getRemoteDevice(mac);
	}
	
	byte[] bytelock = new byte[0];
	private ArrayList<BluetoothDevice> devicelist = new ArrayList<BluetoothDevice>();
	
	@SuppressLint("NewApi") //蓝牙搜索回调

	BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
		@Override
		public void onLeScan(final BluetoothDevice device,
				final int rssi, final byte[] scanRecord) {
//			synchronized (bytelock) {

			MyApplication.getInstance().getMainThreadHandler().post(new Runnable() {

				@Override
				public void run() {


					if (!devicelist.contains(device)
							&& device != null
							&& device.getName() != null) {
						devicelist.add(device);
					}
					if (arrayAdapter != null) arrayAdapter.addDevice(device.getAddress(), rssi);
					if (arrayAdapter != null) {
						arrayAdapter.DataSetChanged();
					}
				}
			});
//			}
		

		}				
	};
	
	
	private myArrayAdapter arrayAdapter;
	private PopupWindow lpopupWindow;
	protected int itemposition = -1;
	
	private void scanLeDevice(final boolean enable, long scanperiod) {

				if (enable) {
					// Stops scanning after a pre-defined scan period.
					if (MyApplication.getInstance() != null){
					MyApplication.getInstance().getMainThreadHandler().postDelayed(new Runnable() {
						@Override
						public void run() {
							stopScan();
							if (devicelist.isEmpty()) {

							} else {

							}
						}
					}, scanperiod);}
					devicelist.clear();
					startScan();
				} else {
					stopScan();
				}
		}
	
	@SuppressLint("NewApi") private void startScan() {
		if (mBluetoothAdapter != null) 
			mBluetoothAdapter.startLeScan(mLeScanCallback);
			
	}
	
	@SuppressLint("NewApi") private void stopScan() {
		if (mBluetoothAdapter != null) mBluetoothAdapter.stopLeScan(mLeScanCallback);
	}
	
	public void showBleList(final Context context,View parent,final RFStarBLEBroadcastReceiver delegate){
		scanLeDevice(true, SCANDURATION);
		View popupwindow_linknrefresh = View.inflate(context, R.layout.popupwindow_linknrefresh, null);
		final View rl_devicelist = popupwindow_linknrefresh.findViewById(R.id.rl_devicelist);
		final View rl_pop = popupwindow_linknrefresh.findViewById(R.id.rl_pop);
		rl_devicelist.startAnimation(AnimationUtils.loadAnimation(context, R.anim.alpha_in));
		rl_pop.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_right));
		rl_devicelist.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popupwindowDismiss(rl_devicelist, rl_pop, context);
			}
		});
		lpopupWindow = new PopupWindow(popupwindow_linknrefresh,
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);

		lpopupWindow.setTouchable(true);
		lpopupWindow.setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});
		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
		lpopupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 有设备弹出窗口
		lpopupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
		lpopupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				
				stopScan();
				arrayAdapter.onStopscan();
				devicelist.clear();
			}
		});
		final ListView blelist = (ListView) popupwindow_linknrefresh
				.findViewById(R.id.blelist);
		TextView textView3 = (TextView) popupwindow_linknrefresh
				.findViewById(R.id.textView3);
		Button bt_link = (Button) popupwindow_linknrefresh
				.findViewById(R.id.bt_link);
		arrayAdapter = new myArrayAdapter(context,
				devicelist);
		blelist.setAdapter(arrayAdapter);
		blelist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (itemposition == position) {
					itemposition = -1;
				} else {
					itemposition = position;
				}
				arrayAdapter.setPosition(itemposition);
				
			}
		});
		bt_link.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (itemposition != -1) {
					stopScan();
					//TODO 这里若没有搜到设备 而去点击联系 有可能会数组越界 从而造成程序崩溃
					BluetoothDevice device = devicelist.get(itemposition);
					popupwindowDismiss(rl_devicelist, rl_pop, context);
					BleManager.connectBLE(device, context, delegate);
				}
			}
		});
		
	}
	
	void popupwindowDismiss(View rl_devicelist,View rl_pop,Context context){
		//窗口消失
		rl_devicelist.startAnimation(AnimationUtils.loadAnimation(context, R.anim.alpha_out));
		rl_pop.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_right));
		MyApplication.getInstance().getMainThreadHandler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				lpopupWindow.dismiss();
			}
		}, 300);
	}


}
