package com.example.theone.temperaturegaugebaby.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.theone.temperaturegaugebaby.R;

public class UserDialogOne extends Dialog implements
		View.OnClickListener {
	private LinearLayout relative_ok,relative_no;
	private Context context;
	private OnToLoginClickListener mListener;
	private String TOLOGIN = "1";

	public UserDialogOne(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public UserDialogOne(Context context, OnToLoginClickListener listener,
			int theme) {
		super(context, theme);
		this.context = context;
		this.mListener = listener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		this.setContentView(R.layout.tologin);
		this.setContentView(R.layout.pop_devivelist);
		init();
	}

	/**
	 * 
	 * 初始化
	 * */
	public void init() {
//		relative_ok = (LinearLayout) findViewById(R.id.confirm_linear_sure);
//		relative_ok.setOnClickListener(this);
//		relative_no = (LinearLayout) findViewById(R.id.confirm_linear_canel);
//		relative_no.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
//		case R.id.confirm_linear_sure:
//			mListener.getText(TOLOGIN, 102);
//			this.dismiss();
//			break;
//		case R.id.confirm_linear_canel:
//			this.dismiss();
//			break;

		}
	}

	public interface OnToLoginClickListener {
		public void getText(String type, int param);
	}
}