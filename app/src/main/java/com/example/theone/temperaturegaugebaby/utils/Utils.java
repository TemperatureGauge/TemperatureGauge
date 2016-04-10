package com.example.theone.temperaturegaugebaby.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

//import org.apache.http.client.CookieStore;


public class Utils {
//	public static CookieStore cookieStore = null;
	public static String token = "";
	public static String uid = "";

	// 和校验
	public static byte sumCheck(byte[] data) {
		int sum = 0;
		for (int i = 0; i < data.length; i++) {
			int d = data[i] & 0x0FF;
			sum += d;
		}
		return (byte) (0xff & sum);
	}

	// 返回数据和校验
	public static boolean sumCheckORD(byte[] data) {
		int sum = 0;
		for (int i = 0; i < data.length - 1; i++) {
			int d = data[i] & 0x0FF;
			sum += d;
		}
		return (byte) (0xff & sum) == data[data.length - 1];
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

	public static byte[] getDataToSend(byte fc_b1, byte fc_b2, byte fc_b3, byte fc_b4) {

		return getDataToSend(fc_b1, fc_b2, fc_b3, fc_b4, (byte) 0x00, (byte) 0x00);
	}

	public static byte[] getDataToSend(byte fc_b1, byte fc_b2, byte fc_b3, byte fc_b4, byte fc_b5, byte fc_b6) {
		byte[] bytetosum = { (byte) 0x5f, (byte) 0x60, fc_b1, fc_b2, fc_b3, fc_b4, fc_b5, fc_b6, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
		byte[] bytetoSend = { (byte) 0x5f, (byte) 0x60, fc_b1, fc_b2, fc_b3, fc_b4, fc_b5, fc_b6, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, Utils.sumCheck(bytetosum) };
		return bytetoSend;
	}

	public static boolean checkData(byte[] data) {
		return data[0] == (byte) 0x5F && data[1] == (byte) 0x60 && Utils.sumCheckORD(data);
	}

	// 解析两个字节为整形
	public static int extrackCount(byte a1, byte a2) {

		String b1 = String.format("%02X", a1);
		String b2 = String.format("%02X", a2);
		int b = Integer.parseInt(b1, 16) * 16 * 16 + Integer.parseInt(b2, 16);

		return b;
	}

	public static int bytetoint(byte b1, byte b2) {
		int temp = 0;
		// 将b1,b2字节码转换成无符号的int型数据
		int byte1 = Integer.parseInt(String.format("%02X", b1), 16);
		int byte2 = Integer.parseInt(String.format("%02X", b2), 16);
		// 将这两个字节转换成一个int型数据
		temp = byte1;
		temp = (temp << 8) + byte2;
		temp <<= 16;
		temp = temp / 65536;
		return temp;
	}

	public static long bytetoLong(byte a1, byte a2, byte a3, byte a4) {
		byte[] byteNum = new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, a1, a2, a3, a4 };
		long num = 0;
		for (int ix = 0; ix < 8; ++ix) {
			num <<= 8;
			num |= (byteNum[ix] & 0xff);
		}
		return num;
	}

	public static byte[] longtobyte(long in) {
		byte[] a = new byte[4];
		a[3] = (byte) (0xff & in);
		a[2] = (byte) ((0xff00 & in) >> 8);
		a[1] = (byte) ((0xff0000 & in) >> 16);
		a[0] = (byte) ((0xff000000 & in) >> 24);
		return a;
	}

	public static String unicodeToUtf8(String theString) {
		char aChar;
		if (theString == null) {
			return "";
		}
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = 't';
					else if (aChar == 'r')
						aChar = 'r';
					else if (aChar == 'n')
						aChar = 'n';
					else if (aChar == 'f')
						aChar = 'f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}

	public static String jsonString(String s) {
		char c;
		char[] temp = s.toCharArray();
		int n = temp.length;
		for (int i = 0; i < n; i++) {
			if (temp[i] == ':' && temp[i + 1] == '"') {
				for (int j = i + 2; j < n; j++) {
					if (temp[j] == '"') {
						if (temp[j + 1] != ',' && temp[j + 1] != '}') {
							temp[j] = '“';
							c = temp[j + 1];
						} else if (temp[j + 1] == ',' || temp[j + 1] == '}') {
							break;
						}
					}
				}
			}
		}
		return new String(temp);
	}

	/**
	 * 保存文件 　　
	 * 
	 * @param toSaveString
	 *            　　
	 * @param filePath
	 *            　　
	 */
	public static void saveFile(String toSaveString, String filePath) {
		try {
			File saveFile = new File(filePath);
			if (!saveFile.exists()) {
				File dir = new File(saveFile.getParent());
				dir.mkdirs();
				saveFile.createNewFile();
			} else {
				saveFile.delete();
				saveFile.createNewFile();
			}
			FileOutputStream outStream = new FileOutputStream(saveFile);
			outStream.write(toSaveString.getBytes());
			outStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 　　* 读取文件内容 　　* @param filePath 　　* @return 文件内容 　　
	 */
	public static String readFile(String filePath) {
		String str = "";
		try {
			File readFile = new File(filePath);
			if (!readFile.exists()) {
				return null;
			}
			FileInputStream inStream = new FileInputStream(readFile);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length = -1;
			while ((length = inStream.read(buffer)) != -1) {
				stream.write(buffer, 0, length);
			}
			str = stream.toString();
			stream.close();
			inStream.close();
			return str;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			File newfile = new File(newPath);
			if (newfile.exists()) {
				newfile.delete();
			}
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1024];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.flush();
				fs.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();
		}
	}

	public static String getIconurl(String iconurl) {
		return iconurl = iconurl.replaceAll("/", "0");
	}


	public static void showDialog(Context context, String content, String title) {
		new AlertDialog.Builder(context).setTitle(title)// 设置对话框标题

				.setMessage(content)// 设置显示的内容

				.setPositiveButton("确定", new DialogInterface.OnClickListener() {// 添加确定按钮

							@Override
							public void onClick(DialogInterface dialog, int which) {// 确定按钮的响应事件

								dialog.dismiss();

							}

						}).show();// 在按键响应事件中显示此对话框

	}

	public static String showData(byte[] data) {
		StringBuffer stringBuffer = new StringBuffer();
		for (byte b : data) {
			String b1 = String.format("%02X", b);
			stringBuffer.append(" " + b1);
		}
		return stringBuffer.toString();
	}

/*	public static String readMainDataFile(String date) {
		if (date != null) { //
			String filePath = URLConstants.maindatalistPath + date + "main.coollang";
			return readFile(filePath);
		} else {
			return null;
		}

	}*/
	
	/**
	 * 格式化成1位小数
	 * @param time
	 * @return
	 */
	public static float formatFloat(float time) {
		// 格式化小数的位数
		String pattern = "0.#";
		DecimalFormat df = new DecimalFormat(pattern);
		return Float.parseFloat(df.format(time));
	}

}
