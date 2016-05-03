package com.example.theone.temperaturegaugebaby.adapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.example.theone.temperaturegaugebaby.R;

public class myArrayAdapter extends BaseAdapter {
	List<BluetoothDevice> devicelist = new ArrayList<BluetoothDevice>();
	Map<String, Integer> devicemap = new LinkedHashMap<String, Integer>();
	Context context;
	private int checkedPosition = -1;
	private boolean isok = true;
    public final Handler h;

	public myArrayAdapter(Context context, List<BluetoothDevice> devicelist) {
		this.devicelist = devicelist;
		this.context = context;
		h = new Handler();
		h.postDelayed(r, 2000);
	}
	
	Runnable r = new Runnable() {
		@Override
		public void run() {
			isok = true;
			h.postDelayed(this, 2000);
		}
	};
	
	public void onStopscan(){
		h.removeCallbacks(r);
	}
	@Override
	public int getCount() {
		return devicelist.size();
	}

	@Override
	public BluetoothDevice getItem(int position) {
		return devicelist.get(position);
	}

	@SuppressLint("ViewHolder") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(context, R.layout.list_tv, null);
		@SuppressWarnings("unused")
		ImageView iv_devicemark = (ImageView) view.findViewById(R.id.iv_devicemark);
/*		if (PreferencesUtils.isBindedDevice(context, DeviceUtils.handleMac(devicelist.get(position).getAddress()), false)) {
			iv_devicemark.setImageResource(R.drawable.bindeddevice);
			}*/
		CheckBox cb_ble = (CheckBox) view.findViewById(R.id.cb_ble);
		ImageView iv_rssi = (ImageView) view.findViewById(R.id.iv_rssi);

		if (position == checkedPosition) {
			cb_ble.setChecked(true);
		} else {
			cb_ble.setChecked(false);
		}

		if (devicelist.size() > 0) {
			if (devicelist.get(position).getName() != null) {

				if (!TextUtils.isEmpty(devicelist.get(position).getAddress().trim())) {

					cb_ble.setText(devicelist.get(position).getName().trim());
					if (devicemap.size() > 0) {
						int rssi = devicemap.get(devicelist.get(position).getAddress());
						if ( rssi > -55 ) {
							iv_rssi.setImageResource(R.drawable.rssi5);
						} else if ( rssi < -55 && rssi > -65) {
							iv_rssi.setImageResource(R.drawable.rssi4);
						} else if (rssi < -65 && rssi  > -85) {
							iv_rssi.setImageResource(R.drawable.rssi3);
						} else if (rssi < -85 && rssi  > -100) {
							iv_rssi.setImageResource(R.drawable.rssi2);
						} else {
							iv_rssi.setImageResource(R.drawable.rssi1);
						}
					}
			
				}
			}
		}

		return view;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void addDevice(String deviceaddress, int Rssi) {
		devicemap.put(deviceaddress, Rssi);
	}
	
	public void DataSetChanged() {
		if (isok ) {
			isok = false;
			notifyDataSetChanged();
		}
	}

	public void setPosition(int position) {
		checkedPosition = position;
		notifyDataSetChanged();
		
	}
}
