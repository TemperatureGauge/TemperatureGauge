package com.example.theone.temperaturegaugebaby.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.theone.temperaturegaugebaby.R;
import com.example.theone.temperaturegaugebaby.bean.TestDataList;
import com.example.theone.temperaturegaugebaby.utils.ImageTools;
import com.example.theone.temperaturegaugebaby.views.CircleImageView;

import java.util.List;

/**
 * 测试列表adapter
 * Created by xiongxing on 2016/4/17.
 */
public class TestListAdapter extends BaseAdapter {

    private Context context;
    private List<TestDataList> list;

    public TestListAdapter(Context context,List<TestDataList> list) {
        this.context=context;
        this.list=list;
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
        TestListViewHolder viewHolder = null;
        if(convertView==null){
            viewHolder= new TestListViewHolder();
            convertView=View.inflate(context, R.layout.testlist_item, null);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(TestListViewHolder)convertView.getTag();
        }
        viewHolder.head = (CircleImageView) convertView.findViewById(R.id.iv_baby);
        viewHolder.tv_tp = (TextView) convertView.findViewById(R.id.tv_tp);
        viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
        viewHolder.tv_babyname = (TextView) convertView.findViewById(R.id.tv_babyname);
        ImageTools.getImageLoader().displayImage(list.get(position).getHeadUrl(), viewHolder.head, ImageTools.getDefaultOptions());
        viewHolder.tv_tp.setText(list.get(position).getTp());
        viewHolder.tv_time.setText(list.get(position).getTime());
        viewHolder.tv_babyname.setText(list.get(position).getUserName());
        return convertView;
    }
}

class TestListViewHolder {
    CircleImageView head;
    TextView tv_babyname;
    TextView tv_tp;
    TextView tv_time;
}
