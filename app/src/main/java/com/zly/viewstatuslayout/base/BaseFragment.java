package com.zly.viewstatuslayout.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zly.viewstatuslayout.R;


/**
 * Cerated by xiaoyehai
 * Create date : 2019/12/24 10:21
 * description :
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    protected Context mContext;

    private View mViews;
    private FrameLayout mRootView;
    private View mViewProgress;
    private FrameLayout mViewContent;
    private View mViewEmpty;
    private TextView mTextTip;
    private TextView mTvTitleBar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViews = inflater.inflate(R.layout.view_base_state_layout, container, false);
        findViews();
        return mViews;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRealContentView();

        init();

    }

    public void initTitleBar(boolean isShow, String title) {
        mTvTitleBar.setText(title);
        if (isShow) {
            mTvTitleBar.setVisibility(View.VISIBLE);
        } else {
            mTvTitleBar.setVisibility(View.GONE);
        }
    }


    private void findViews() {
        mRootView = mViews.findViewById(R.id.rootView);
        mTvTitleBar = mViews.findViewById(R.id.title_bar);
        mViewProgress = mViews.findViewById(R.id.view_progress);
        mViewContent = (FrameLayout) mViews.findViewById(R.id.view_content);
        mViewEmpty = mViews.findViewById(R.id.view_empty);
        mTextTip = (TextView) mViews.findViewById(R.id.text_tip);

        mViewEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReload();
            }
        });

    }

    protected void onReload() {
    }

    private void setRealContentView() {
        View realContentView = LayoutInflater.from(getActivity()).inflate(getLayoutId(), mViewContent, true);
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


    protected abstract int getLayoutId();

    protected abstract void init();

}
