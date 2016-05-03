package com.example.theone.temperaturegaugebaby.service;


import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Toast;

import com.example.theone.temperaturegaugebaby.UUIDS.UUIDS;
import com.example.theone.temperaturegaugebaby.application.MyApplication;
import com.example.theone.temperaturegaugebaby.service.BLEDevice.RFStarBLEBroadcastReceiver;
import com.example.theone.temperaturegaugebaby.utils.DataUtils;

public class BleManager {
	
	// 是否支持蓝牙4.0
	private static boolean isBleSupport() {
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		return currentapiVersion > 17;
	}
	public static CubicBLEDevice cubicBLEDevice = null;
	// 开启蓝牙
	@SuppressLint("NewApi") public static BluetoothAdapter openble(Context context) {
		if (isBleSupport()) {
			// Use this check to determine whether BLE is supported on the device.
			// Then you can
			// selectively disable BLE-related features.
			if (!context.getPackageManager().hasSystemFeature(
					PackageManager.FEATURE_BLUETOOTH_LE)) {
				return null;
			}

			// Initializes a Bluetooth adapter. For API level 18 and above, get
			// a
			// reference to
			// BluetoothAdapter through BluetoothManager.
			final BluetoothManager bluetoothManager = (BluetoothManager) context
					.getSystemService(Context.BLUETOOTH_SERVICE);
			BluetoothAdapter mBluetoothAdapter = bluetoothManager.getAdapter();

			// Checks if Bluetooth is supported on the device.
			if (mBluetoothAdapter == null) {
				return null;
			}
			// 打开蓝牙

			mBluetoothAdapter.enable();
			return mBluetoothAdapter;
		}else {
			return null;
		}
	}
	
	public static void searchBLE(Context context,View parent,RFStarBLEBroadcastReceiver delegate) {
		SearchDevice.getinstance().showBleList(context, parent, delegate);
	}
	public static void connectBLEByMac(String mac,RFStarBLEBroadcastReceiver delegate){
		cubicBLEDevice = new CubicBLEDevice(MyApplication.getInstance(), SearchDevice.getinstance().getDeviceByMac(mac));
		cubicBLEDevice.setBLEBroadcastDelegate(delegate);
	}
	public static void connectBLE(BluetoothDevice device,Context context,RFStarBLEBroadcastReceiver delegate) {
		cubicBLEDevice = new CubicBLEDevice(MyApplication.getInstance(), device);
		cubicBLEDevice.setBLEBroadcastDelegate(delegate);

	}
	

	
	public static void setBLEBroadcastDelegate(RFStarBLEBroadcastReceiver delegate){
		if(cubicBLEDevice != null){
			cubicBLEDevice.setBLEBroadcastDelegate(delegate);
		}
	}
	
	public static void sendData(byte...bs){
			if (cubicBLEDevice != null){
				cubicBLEDevice.writeValue(UUIDS.SUUID_WRITE, UUIDS.CUUID_WRITE, DataUtils.getDataToSend(bs));
			}

	}
	
}
