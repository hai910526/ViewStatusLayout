package com.zly.viewstatuslayout.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zly.viewstatuslayout.R;

/**
 * Cerated by xiaoyehai
 * Create date : 2020/11/11 12:14
 * description :加载进度条，请求成功，请求数据为空，请求失败，无网络等状态不同界面的切换
 */
public abstract class BaseActivity extends AppCompatActivity {

    private RelativeLayout mRootView;
    private View mViewProgress;
    private FrameLayout mViewContent;
    private View mViewEmpty;
    private TextView mTextTip;
    private TextView mTvTitleBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_base_state_layout);

        findViews();

        init();
    }


    public void initTitleBar(boolean isShow, String title) {
        mTvTitleBar.setText(title);
        if(isShow) {
            mTvTitleBar.setVisibility(View.VISIBLE);
        }else {
            mTvTitleBar.setVisibility(View.GONE);
        }
    }

    public void showProgressView() {
        showView(R.id.view_progress);
    }

    public void showContentView() {
        showView(R.id.view_content);
    }

    public void showEmptyView() {
        showView(R.id.view_empty);
    }

    public void showEmptyView(int resId) {
        showEmptyView();
        mTextTip.setText(resId);
    }

    /**
     * 请求数据为空，请求失败，无网络等状态都用同一个布局
     *
     * @param msg
     */
    public void showEmptyView(String msg) {
        showEmptyView();
        mTextTip.setText(msg);
    }


    public void showView(int viewId) {
        for (int i = 1; i < mRootView.getChildCount(); i++) {
            if (mRootView.getChildAt(i).getId() == viewId) {
                mRootView.getChildAt(i).setVisibility(View.VISIBLE);
            } else {
                mRootView.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }


    private void findViews() {
        mRootView = findViewById(R.id.rootView);
        mTvTitleBar = findViewById(R.id.title_bar);
        mViewProgress = findViewById(R.id.view_progress);
        mViewContent = (FrameLayout) findViewById(R.id.view_content);
        mViewEmpty = findViewById(R.id.view_empty);
        mTextTip = (TextView) findViewById(R.id.text_tip);

        mViewEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReload();
            }
        });

        //将当前界面的布局添加到BaseActivity中去
        View realContentView = LayoutInflater.from(this).inflate(getLayoutId(), mViewContent, true);
    }

    public void onReload() {

    }

    protected abstract int getLayoutId();

    protected abstract void init();

}
