package com.zly.viewstatuslayout;

import android.os.Handler;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zly.viewstatuslayout.base.BaseActivity2;
import com.zly.viewstatuslayout.widget.ViewStatusLayout;


public class ThirdActivity extends BaseActivity2 {

    private FrameLayout mFlContainer;
    private TextView mTvContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_third;
    }

    @Override
    protected void init() {
        //mFlContainer = findViewById(R.id.fl_container);
        mTvContent = findViewById(R.id.tv_content);
    }

    @Override
    protected ViewStatusLayout.Builder createViewStatusBuilder() {
        return super.createViewStatusBuilder()
                //.setContainer(mFlContainer) //设置父容器
                //.setUseStatusView(false) //是否使用状态图，有些页面不需要展示无数据页，无网络页
                .setNoDataTip("暂时没有数据哦")
                .setRetryTip("重新加载")
                .setRetryClickListener(new ViewStatusLayout.IRetryClickListener() {
                    @Override
                    public void click(int viewStatus) {
                        loadData();
                    }
                });
    }

    @Override
    protected void initData() {
        loadData();
    }

    //模拟加载数据
    private void loadData() {
        mViewStatusLayout.switchView(ViewStatus.LOADING_START);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int code = 202;
                switch (code) {
                    case 200:
                        mViewStatusLayout.switchView(ViewStatus.HASDATA);
                        mTvContent.setText("content");
                        break;
                    case 201:
                        mViewStatusLayout.switchView(ViewStatus.NODATA);
                        break;
                    case 202:
                        mViewStatusLayout.switchView(ViewStatus.NONETWORK);
                        break;
                    case 203:
                        mViewStatusLayout.switchView(ViewStatus.EMPTY);
                        break;
                }
            }
        }, 2000);
    }
}