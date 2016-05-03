package com.example.theone.temperaturegaugebaby.utils;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	private final static byte[] hex = "0123456789ABCDEF".getBytes();
	private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

	private final static Pattern httpUrl = Pattern
			.compile(
					"^(http|https|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$",
					Pattern.CASE_INSENSITIVE);

	private static int parse(char c) {
		if (c >= 'a')
			return (c - 'a' + 10) & 0x0f;
		if (c >= 'A')
			return (c - 'A' + 10) & 0x0f;
		return (c - '0') & 0x0f;
	}

	/**
	 * 半角转换为全角
	 * 
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++)
		{
			if (c[i] == 12288)
			{
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	/**
	 * 从字节数组到十六进制字符串转换
	 * */
	public static String bytes2HexString(byte[] b) {
		byte[] buff = new byte[2 * b.length];
		for (int i = 0; i < b.length; i++)
		{
			buff[2 * i] = hex[(b[i] >> 4) & 0x0f];
			buff[2 * i + 1] = hex[b[i] & 0x0f];
		}
		return new String(buff);
	}

	/**
	 * 从十六进制字符串到字节数组转换
	 * */
	public static byte[] hexString2Bytes(String hexstr) {
		byte[] b = new byte[hexstr.length() / 2];
		int j = 0;
		for (int i = 0; i < b.length; i++)
		{
			char c0 = hexstr.charAt(j++);
			char c1 = hexstr.charAt(j++);
			b[i] = (byte) ((parse(c0) << 4) | parse(c1));
		}
		return b;
	}

	/**
	 * 用一个字符串替换指定位置的字符
	 * 
	 * @param index
	 * @param res
	 * @param str
	 * */
	public static String replaceIndex(int index, String res, String str) {
		return res.substring(0, index) + str + res.substring(index + 1);
	}

	/**
	 * 判断是不是一个合法的电子邮件地址
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (email == null || email.trim().length() == 0)
			return false;
		return emailer.matcher(email).matches();
	}

	/**
	 * 判断是不是一个合法的Http Url地址
	 * 
	 * @param url
	 * @return
	 */
	public static boolean isHttpUrl(String url) {
		if (url == null || url.trim().length() == 0)
			return false;
		return httpUrl.matcher(url).matches();
	}

	/**
	 * 判断是否是合法的手机号
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isPhone(String str) {
		String telRegex = "[1][3578]\\d{9}";
		return str.matches(telRegex);
	}

	private static String showData(byte[] data) {
		StringBuffer stringBuffer = new StringBuffer();
		for (byte b : data)
		{
			String b1 = String.format("%02X", b);
			stringBuffer.append(b1);
		}
		System.out.println(hexStringToString(stringBuffer.toString(), 2));
		return stringBuffer.toString();
	}

	public static String hexStringToString(String hexString, int encodeType) {
		String result = "";
		int max = hexString.length() / encodeType;
		for (int i = 0; i < max; i++)
		{
			char c = (char) hexStringToAlgorism(hexString.substring(i * encodeType, (i + 1) * encodeType));
			result += c;
		}
		return result;
	}
	
	public static int hexStringToAlgorism(String hex) {
		hex = hex.toUpperCase();
		int max = hex.length();
		int result = 0;
		for (int i = max; i > 0; i--)
		{
			char c = hex.charAt(i - 1);
			int algorism = 0;
			if (c >= '0' && c <= '9')
			{
				algorism = c - '0';
			} else
			{
				algorism = c - 55;
			}
			result += Math.pow(16, max - i) * algorism;
		}
		return result;
	}

	/**
	 * 获取一个字符串的长度，并将EditText的光标移至最后
	 */
	public static void setSelectionEnd(String name, EditText et) {
		if (et.getText().toString().trim().length() < name.length())
		{
			et.setSelection(et.getText().toString().trim().length());
		} else
		{
			et.setSelection(name.length());
		}
	}

	/**
	 * 判断是否是Emoji
	 * 
	 * @param codePoint
	 *            比较的单个字符
	 * @return
	 */
	private static boolean isEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
				|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}

	/**
	 * 检测是否有emoji表情
	 * 
	 * @param source
	 * @return
	 */
	public static boolean containsEmoji(String source) {
		int len = source.length();
		for (int i = 0; i < len; i++)
		{
			char codePoint = source.charAt(i);
			if (!isEmojiCharacter(codePoint))
			{ // 如果不能匹配,则该字符是Emoji表情
				return true;
			}
		}
		return false;
	}

	/**
	 * KJ加密
	 */
	public static String KJencrypt(String str) {
		char[] cstr = str.toCharArray();
		StringBuilder hex = new StringBuilder();
		for (char c : cstr)
		{
			hex.append((char) (c + 5));
		}
		return hex.toString();
	}

	/**
	 * KJ解密
	 */
	public static String KJdecipher(String str) {
		char[] cstr = str.toCharArray();
		StringBuilder hex = new StringBuilder();
		for (char c : cstr)
		{
			hex.append((char) (c - 5));
		}
		return hex.toString();
	}

	/**
	 * MD5加密
	 */
	public static String md5(String string) {
		byte[] hash;
		try
		{
			hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e)
		{
			throw new RuntimeException("Huh, MD5 should be supported?", e);
		} catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException("Huh, UTF-8 should be supported?", e);
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash)
		{
			if ((b & 0xFF) < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString();
	}

	/**
	 * 获取手机IMEI码
	 */
	public static String getPhoneIMEI(Activity aty) {
		TelephonyManager tm = (TelephonyManager) aty.getSystemService(Activity.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	/**
	 * 判断给定字符串是否空白串 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
	 */
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++)
		{
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n')
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * 验证码是否合法
	 * 
	 * @param inviteCode
	 * @return
	 */
	public static boolean isLegal(String inviteCode) {
		String regx = "^[a-zA-Z0-9_]{2,20}$";
		if (inviteCode == null)
		{
			return false;
		} else if ("".equalsIgnoreCase(inviteCode))
		{
			return true;
		} else
		{
			return inviteCode.matches(regx);
		}
	}

	/*********** 返回英文时间戳 *******************/
	public static String parseStrToEn(String createTime) {
		if (createTime.contains("秒"))
		{ // N秒前
			if (Integer.parseInt(createTime.substring(0, createTime.indexOf("秒"))) == 1)
			{ // 单数
				return createTime.replace("秒前", "second ago");
			} else
			{ // 复数
				return createTime.replace("秒前", "seconds ago");
			}
		} else if (createTime.contains("分钟"))
		{ // N分钟前
			if (Integer.parseInt(createTime.substring(0, createTime.indexOf("分钟"))) == 1)
			{ // 单数
				return createTime.replace("分钟前", "minute ago");
			} else
			{ // 复数
				return createTime.replace("分钟前", "minutes ago");
			}
		} else if (createTime.contains("小时"))
		{ // N小时前
			if (Integer.parseInt(createTime.substring(0, createTime.indexOf("小时"))) == 1)
			{ // 单数
				return createTime.replace("小时前", "hour ago");
			} else
			{ // 复数
				return createTime.replace("小时前", "hours ago");
			}
		} else if (createTime.contains("天"))
		{ // N天前
			if (Integer.parseInt(createTime.substring(0, createTime.indexOf("天"))) == 1)
			{ // 单数
				return createTime.replace("天前", "day ago");
			} else
			{ // 复数
				return createTime.replace("天前", "days ago");
			}
		} else if (createTime.contains("月"))
		{ // N月前
			if (Integer.parseInt(createTime.substring(0, createTime.indexOf("月"))) == 1)
			{ // 单数
				return createTime.replace("月前", "month ago");
			} else
			{ // 复数
				return createTime.replace("月前", "months ago");
			}
		} else
		{
			return "a moment ago";
		}
	}

	public static String parseSignatureToEn(String signature) {
		if (signature.contains("这个人很懒,什么都没留下"))
		{
			return "I am my superhero.";
		} else
		{
			return signature;
		}
	}

	public static boolean judgeVersion(String version) {
		// 以 1.2.2为标准
		if (version != null && !"".equalsIgnoreCase(version))
		{// 不为空或者null
			version = version.trim();
			String regEx = "[^0-9]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(version);
			version = m.replaceAll("").trim();
			int versionInt = Integer.parseInt(version);
			if (versionInt > 122)
			{// 大于
				return true;
			} else
			{
				return false;
			}

		} else
		{
			return false;
		}
	}

	/**
	 * 安全解析Json
	 * 
	 * @param json
	 * @param classT
	 * @return
	 */
//	public static Object parseJSonSafe(String json, Object classT) {
//		Gson gson = new Gson();
//		try
//		{
//			return gson.fromJson(json, classT.getClass());
//		} catch (JsonSyntaxException e)
//		{
//			return null;
//		}
//	}

	/**
	 * String安全转换为Float
	 * 
	 * @param string
	 * @return
	 */
	public static float parseStringToFloatSafe(String string) {
		if (string != null && !"".equalsIgnoreCase(string.trim()))
		{// 非空非null
			float a = 0;
			try
			{
				a = Float.parseFloat(string);
			} catch (NumberFormatException e)
			{

			}
			return a;
		} else
		{
			return 0;
		}
	}

	/**
	 * String安全转换为Ineger
	 * 
	 * @param string
	 * @return
	 */
	public static int parseStringToIntegerSafe(String string) {
		if (string != null && !"".equalsIgnoreCase(string.trim()))
		{// 非空非null
			int a = 0;
			try
			{
				a = Integer.parseInt(string);
			} catch (NumberFormatException e)
			{

			}
			return a;
		} else
		{
			return 0;
		}

	}

	public static String getChannel(Activity activity) {
		String key = "UMENG_CHANNEL";
		if (activity == null || TextUtils.isEmpty(key))
		{
			return null;
		}
		String resultData = null;
		try
		{
			PackageManager packageManager = activity.getPackageManager();
			if (packageManager != null)
			{
				ApplicationInfo applicationInfo = packageManager.getApplicationInfo(activity.getPackageName(), PackageManager.GET_META_DATA);
				if (applicationInfo != null)
				{
					if (applicationInfo.metaData != null)
					{
						resultData = applicationInfo.metaData.getString(key);
					}
				}

			}
		} catch (PackageManager.NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return resultData;
	}

	/**
	 * @author han
	 * @param version
	 *            固件版本
	 * @param getVersion
	 *            服务器固件版本
	 * @return 是否需要升级
	 * 
	 *         比较固件版本判断是否需要升级
	 * 
	 */
	public static boolean compareVersion(String version, String getVersion) {

		if (version == null || getVersion == null || "".equalsIgnoreCase(version) || "".equalsIgnoreCase(getVersion))
		{
			return false;
		} else
		{
			String regEx = "[^0-9]";
			version = version.trim();
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(version);
			version = m.replaceAll("").trim();
			getVersion = getVersion.trim();
			Matcher n = p.matcher(getVersion);
			getVersion = n.replaceAll("").trim();
			if (Integer.parseInt(version) < Integer.parseInt(getVersion))
			{
				return true;
			} else
			{
				return false;
			}
		}
	}

	public static String getString(InputStream inputStream) {
		InputStreamReader inputStreamReader = null;
		 try {
			    inputStreamReader = new InputStreamReader(inputStream, "utf-8");
		 } catch (UnsupportedEncodingException e1) {
		      e1.printStackTrace();
			   }
		 BufferedReader reader = new BufferedReader(inputStreamReader);
		  StringBuffer sb = new StringBuffer("");
		  String line;
		  try {
			     while ((line = reader.readLine()) != null) {
				          sb.append(line);
				      }
			  inputStream.close();
			  inputStreamReader.close();
			  } catch (IOException e) {
			     e.printStackTrace();
			  }

		  return sb.toString();
		}

}
