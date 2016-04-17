package com.example.theone.temperaturegaugebaby.activity;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.theone.temperaturegaugebaby.R;
import com.example.theone.temperaturegaugebaby.adapter.TestListAdapter;
import com.example.theone.temperaturegaugebaby.base.BaseActivity;
import com.example.theone.temperaturegaugebaby.bean.TestDataList;
import com.nostra13.universalimageloader.utils.L;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xiongxing on 2016/4/17.
 */
public class TestActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.tv_back)
    TextView tv_back;
    @Bind(R.id.id_swipe_ly)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.list_test)
    ListView mTestListView;
    private static final int REFRESH_COMPLETE = 0X110;
    private TestListAdapter mTestAdapter;
    List<TestDataList> testList = new ArrayList<TestDataList>();

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    TestDataList testDataList = new TestDataList();
                    testDataList.setHeadUrl(null);
                    testDataList.setTime("2016/03/09 18:00");
                    testDataList.setUserName("Baby");
                    testDataList.setTp("38.5℃");
                    testList.add(testDataList);
                    mTestAdapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }

        ;
    };

    @Override
    protected void initView() {
        setContentView(R.layout.activity_testlist);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorScheme(android.R.color.black, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        TestDataList testDataList = new TestDataList();
        testDataList.setHeadUrl(null);
        testDataList.setTime("2016/03/09 18:00");
        testDataList.setUserName("Baby");
        testDataList.setTp("38.5℃");
        testList.add(testDataList);
        mTestAdapter = new TestListAdapter(this, testList);
        mTestListView.setAdapter(mTestAdapter);
        mTestListView.setOnTouchListener(listTouchListener);
        mTestListView.setOnItemClickListener(itemClickListener);
        mTestListView.setOnScrollListener(listScrollListener);
    }


    private View.OnTouchListener listTouchListener = new View.OnTouchListener() {

        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN: {
                    //按住事件发生后执行代码的区域
                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    //移动事件发生后执行代码的区域
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    //松开事件发生后执行代码的区域
                    break;
                }

                default:

                    break;
            }
            return false;
        }
    };
    private ListView.OnItemClickListener itemClickListener = new ListView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

        }
    };

        @Override
        protected  void initData() {

        }


        ListView.OnScrollListener listScrollListener = new ListView.OnScrollListener() {
            boolean isLastRow = false;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (isLastRow && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    //加载元素
//                    Page++;
//                    initvedio();
                    isLastRow = false;
                }
                switch (scrollState) {
                    case SCROLL_STATE_TOUCH_SCROLL:     //滑动中

                        break;
                    case SCROLL_STATE_IDLE:        //空闲的时候
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                //判断是否滚到最后一行
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
                    isLastRow = true;
                }


                //判断是否滚到最后一行
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
                    isLastRow = true;
                }

                if (firstVisibleItem == 0) {
//					gridview_button.setVisibility(View.VISIBLE);
                }
            }

        };

        @Override
        protected void onResume() {
            super.onResume();
            EventBus.getDefault().register(this);
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            EventBus.getDefault().unregister(this);
        }

        @OnClick({R.id.tv_back})
        void onClickBack() {
            finishActivity();
        }

        @Override
        public void onRefresh() {
            mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
        }
    }
