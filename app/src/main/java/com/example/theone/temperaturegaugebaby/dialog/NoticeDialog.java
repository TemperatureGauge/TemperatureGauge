package com.example.theone.temperaturegaugebaby.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.theone.temperaturegaugebaby.R;

/**
 * Created by yeah on 2016/4/25.
 */
public class NoticeDialog extends Dialog implements View.OnClickListener {
    public NoticeDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_notice);
    }

    @Override
    public void onClick(View v) {

    }
}
