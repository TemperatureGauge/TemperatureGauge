package com.example.theone.temperaturegaugebaby.utils;

import android.content.Context;

import java.math.BigDecimal;

public class DataUtils {
	// 返回数据和校验
	public static boolean sumCheckORD(byte[] data) {
		int sum = 0;
		for (int i = 0; i < data.length - 1; i++) {
			int d = data[i] & 0x0FF;
			sum += d;
		}
		return (byte) (0xff & sum) == data[data.length - 1];
	}
	//得到的crc校验码为整数，转为byte[]
	public static byte[] intTobyte(int in) {
		byte[] a = new byte[2];
        a[1] = (byte) (0xff & in);
        a[0] = (byte) ((0xff00 & in) >> 8);
		return a;
	}
	// 发送蓝牙数据
	public static byte[] getDataToSend(byte fc_b1) {

		return getDataToSend(fc_b1, (byte) 0x00);
	}

	public static byte[] getDataToSend(byte fc_b1, byte fc_b2) {

		return getDataToSend(fc_b1, fc_b2, (byte) 0x00);
	}

	public static byte[] getDataToSend(byte fc_b1, byte fc_b2, byte fc_b3) {

		return getDataToSend(fc_b1, fc_b2, fc_b3, (byte) 0x00);
	}

	public static byte[] getDataToSend(byte fc_b1, byte fc_b2, byte fc_b3,
			byte fc_b4) {

		return getDataToSend(fc_b1, fc_b2, fc_b3, fc_b4, (byte) 0x00,
				(byte) 0x00);
	}

	public static byte[] getDataToSend(byte fc_b1, byte fc_b2, byte fc_b3,
			byte fc_b4, byte fc_b5, byte fc_b6) {
		byte[] bytetosum = { (byte) 0xA8,  fc_b1, fc_b2, fc_b3,
				fc_b4, fc_b5, fc_b6, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 , (byte) 0x00 };
		byte[] bytetoSend = { (byte) 0xA8, fc_b1, fc_b2, fc_b3,
				fc_b4, fc_b5, fc_b6, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,(byte) 0x00,
				sumCheck(bytetosum) };
		return bytetoSend;
	}

	// 和校验
	public static byte sumCheck(byte[] data) {
		int sum = 0;
		for (int i = 0; i < data.length; i++) {
			int d = data[i] & 0x0FF;
			sum += d;
		}
		return (byte) (0xff & sum);
	}

	public static byte[] getDataToSend(byte[] bs) {
		byte[] bytes = null;
		switch (bs.length) {
		case 1:
			bytes = getDataToSend(bs[0]);
			break;
		case 2:
			bytes = getDataToSend(bs[0], bs[1]);
			break;
		case 3:
			bytes = getDataToSend(bs[0], bs[1], bs[2]);
			break;
		case 4:
			bytes = getDataToSend(bs[0], bs[1], bs[2], bs[3]);
			break;
		case 5:
			bytes = getDataToSend(bs[0], bs[1], bs[2], bs[3], bs[4]);
			break;
		case 6:
			bytes = getDataToSend(bs[0], bs[1], bs[2], bs[3], bs[4], bs[5]);
			break;

		}
		return bytes;
	}

	private static byte[] getDataToSend(byte b, byte c, byte d, byte e, byte f) {
		return getDataToSend(b, c, d, e, f, (byte) 0x00);
	}

	public static byte[] longtobyte(long in) {
		byte[] a = new byte[4];
		a[3] = (byte) (0xff & in);
		a[2] = (byte) ((0xff00 & in) >> 8);
		a[1] = (byte) ((0xff0000 & in) >> 16);
		a[0] = (byte) ((0xff000000 & in) >> 24);
		return a;
	}

	public static String showData(byte[] data) {
		StringBuffer stringBuffer = new StringBuffer();
		for (byte b : data) {
			String b1 = String.format("%02X", b);
			stringBuffer.append(" " + b1);
		}
		LogUtil.LogI("showdata",stringBuffer.toString());
		return stringBuffer.toString();
	}

	// 解析两个字节为整形
	public static int extrackCount(byte a1, byte a2) {

		String b1 = String.format("%02X", a1);
		String b2 = String.format("%02X", a2);
		int b = Integer.parseInt(b1, 16) * 16 * 16 + Integer.parseInt(b2, 16);

		return b;
	}

	/* 解析时间戳 */
	public static long bytetoLong(byte a1, byte a2, byte a3, byte a4) {
		byte[] byteNum = new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, a1, a2, a3, a4 };
		long num = 0;
		for (int ix = 0; ix < 8; ++ix) {
			num <<= 8;
			num |= (byteNum[ix] & 0xff);
		}
		return num;
	}

	/* 毫秒转分钟 */
	public static float milToMin(long milsec) {
		float min = (float) milsec / 60000;
		return min;
	}
	
	
	
	/*小数数据保留及四舍五入*/
	public static float decimalFomat(double decimal,int n){
        BigDecimal bg = new BigDecimal(decimal);
        float f1 = (float) bg.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
        LogUtil.LogW("==========返回值===============",String.valueOf(f1));
        return f1;
	}
	
	/*
	 * 根据id获取字符串
	 * 
	 * */
	
	
	public static String getStringById(Context mContext,int id){
		String str = mContext.getResources().getString(id);
		if(str==null){ 
			return "";
		}else { 
			return str;
		}

		
	}
}
