package com.zly.viewstatuslayout;

import android.os.Handler;

import com.zly.viewstatuslayout.base.BaseActivity;

public class SecondActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_second;
    }

    @Override
    protected void init() {
        initTitleBar(true, "SecondActivity");
        loadData();
    }

    //模拟加载数据
    private void loadData() {

        showProgressView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int code = 202;
                switch (code) {
                    case 200:
                        showContentView();
                        break;
                    case 201:
                        showEmptyView("暂无数据");
                        break;
                    case 202:
                        showEmptyView("加载失败");
                        break;
                    case 203:
                        showEmptyView("无网络");
                        break;
                }
            }
        }, 2000);

    }

    @Override
    public void onReload() {
        super.onReload();
        loadData();
    }
}