package com.example.theone.temperaturegaugebaby.adapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.theone.temperaturegaugebaby.R;
import com.example.theone.temperaturegaugebaby.bean.User;
import com.example.theone.temperaturegaugebaby.utils.ImageTools;
import com.example.theone.temperaturegaugebaby.views.CircleImageView;

import java.util.List;

/**
 * 用户列表adapter
 * Created by xiongxing on 2016/4/17.
 */
public class UserListAdapter extends BaseAdapter {

    private Context context;
    private List<User> list;

    public UserListAdapter(Context context, List<User> list) {
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
        UserListViewHolder UserViewHolder = null;
        if (convertView == null) {
            UserViewHolder = new UserListViewHolder();
            convertView = View.inflate(context, R.layout.userlist_item, null);
            convertView.setTag(UserViewHolder);
        } else {
            UserViewHolder = (UserListViewHolder) convertView.getTag();
        }
        UserViewHolder.head = (CircleImageView) convertView.findViewById(R.id.iv_baby);
        UserViewHolder.cb_ble = (CheckBox) convertView.findViewById(R.id.cb_ble);
        UserViewHolder.tv_babyname = (TextView) convertView.findViewById(R.id.tv_babyname);
        ImageTools.getImageLoader().displayImage(list.get(position).getHeadUrl(), UserViewHolder.head, ImageTools.getDefaultOptions());
        if ("1".equals(list.get(position).getState())) {
            UserViewHolder.cb_ble.setChecked(true);
        } else if ("0".equals(list.get(position).getState())) {
            UserViewHolder.cb_ble.setChecked(false);
        }
        return convertView;
    }
}

class UserListViewHolder {
    CircleImageView head;
    TextView tv_babyname;
    CheckBox cb_ble;
}