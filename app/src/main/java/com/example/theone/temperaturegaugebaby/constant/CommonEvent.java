package com.example.theone.temperaturegaugebaby.constant;

/**
 * 
 *
 * 说明：使用EventBus传递一个字符串和2个标记字符，需要传递其他类型的数据得重新写一个类
 * 
 * EventBus使用参考网站：http://blog.csdn.net/harvic880925/article/details/40660137
 * 
 * 例如：在TennisHttp请求服务器发送消息，EventBus.getDefault().post(new
 * CommonEvent(result,1));
 * 
 * 在LoginActivity中接收消息，在onCreate中注册EventBus,在onDestory中反注册 在public void
 * onEventMainThread(CommonEvent event) 方法中接收处理消息
 * 
 *
 * 
 */
public class CommonEvent {
	public String msg; // 接收的消息内容
	public int what; // 标记请求服务的状态
	// 1表示请求成功，0表示请求失败，-1表示连接服务器失败
	public static final int REQUESTSUEECCD = 1;
	public static final int REQUESTFAILUE = 0;
	public static final int LINKFAILUE = -1;
	public int type;//消息类型


	
	
	
	public CommonEvent(String msg, int what, int type) {
		this.msg = msg;
		this.what = what;
		this.type = type;
	}
}
