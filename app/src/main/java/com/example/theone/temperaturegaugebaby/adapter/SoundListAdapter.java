package com.example.theone.temperaturegaugebaby.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.theone.temperaturegaugebaby.R;
import com.example.theone.temperaturegaugebaby.bean.Sound;

import java.util.List;

/**
 * 测试列表adapter
 * Created by xiongxing on 2016/4/17.
 */
public class SoundListAdapter extends BaseAdapter {

    private Context context;
    private List<Sound> list;

    public SoundListAdapter(Context context, List<Sound> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SoundListViewHolder soundListViewHolder = null;
        if (convertView == null) {
            soundListViewHolder = new SoundListViewHolder();
            convertView = View.inflate(context, R.layout.soundlist_item, null);
            convertView.setTag(soundListViewHolder);
        } else {
            soundListViewHolder = (SoundListViewHolder) convertView.getTag();
        }
        soundListViewHolder.cb_ble = (CheckBox) convertView.findViewById(R.id.cb_ble);
        soundListViewHolder.tv_soundname = (TextView) convertView.findViewById(R.id.tv_soundname);
        if ("1".equals(list.get(position).getState())) {
            soundListViewHolder.cb_ble.setChecked(true);
        } else if ("0".equals(list.get(position).getState())) {
            soundListViewHolder.cb_ble.setChecked(false);
        }
        soundListViewHolder.tv_soundname.setText(list.get(position).getName());
        return convertView;
    }
}

class SoundListViewHolder {
    TextView tv_soundname;
    CheckBox cb_ble;
}
