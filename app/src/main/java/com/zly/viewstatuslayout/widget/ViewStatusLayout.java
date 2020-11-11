package com.zly.viewstatuslayout.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.zly.viewstatuslayout.R;
import com.zly.viewstatuslayout.ViewStatus;

/**
 * Cerated by xiaoyehai
 * Create date : 2020/11/11 14:31
 * description : 自定义一个View来切换布局。
 */
public class ViewStatusLayout {

    private View mVEmptyContainer;
    private ImageView mIvNoDataView;
    private TextView mTvNoDataTip;
    private TextView mTvRetryBtn;
    public Builder mBuilder;
    public View mRoot;
    private View mClLoadingContainer;
    /***
     * 当前状态
     */
    private @ViewStatus int mCurrentStatus;

    private ViewStatusLayout(Context context, ViewGroup parent, Builder builder) {
        if (builder == null) {
            throw new RuntimeException("you must create commonNoDataLayout with Builder");
        }
        mBuilder = builder;
        mRoot = LayoutInflater.from(context).inflate(R.layout.view_status_layout, parent);

        mVEmptyContainer = parent.findViewById(R.id.ll_empty_container);
        mIvNoDataView = parent.findViewById(R.id.iv_no_data_img);
        mTvNoDataTip = parent.findViewById(R.id.tv_no_data_tip);
        mTvRetryBtn = parent.findViewById(R.id.stv_retry);
        mClLoadingContainer = parent.findViewById(R.id.cl_loading_container);

        if (builder.background != 0) {
            mVEmptyContainer.setBackgroundResource(builder.background);
        }

        switchView(mBuilder.viewStatus);

        if (builder.listener == null) {
            mTvRetryBtn.setVisibility(View.GONE);
        } else {
            mTvRetryBtn.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(builder.retryTip)) {
                mTvRetryBtn.setText(builder.getRetryTip());
            }
            mTvRetryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder.listener.click(mCurrentStatus);
                }
            });
        }
    }

    private void setNoDataView() {
        mTvNoDataTip.setText(mBuilder.getNoDataTip());
        mIvNoDataView.setImageResource(mBuilder.noDataResId);
        if (TextUtils.isEmpty(mBuilder.getRetryTip())) {
            mTvRetryBtn.setVisibility(View.GONE);
        } else {
            mTvRetryBtn.setVisibility(View.VISIBLE);
            mTvRetryBtn.setText(mBuilder.getRetryTip());
        }
    }

    private void setNoNetworkView() {
        mTvNoDataTip.setText(mBuilder.getNoNetworkTip());
        mIvNoDataView.setImageResource(mBuilder.getNoNetworkResId());
        if (TextUtils.isEmpty(mBuilder.getNoNetworkBtnTip())) {
            mTvRetryBtn.setVisibility(View.GONE);
        } else {
            mTvRetryBtn.setVisibility(View.VISIBLE);
            mTvRetryBtn.setText(mBuilder.getNoNetworkBtnTip());
        }
    }

    public void switchView(@ViewStatus int viewStatus) {
        switch (viewStatus) {
            case ViewStatus.HASDATA:
                mVEmptyContainer.setVisibility(View.GONE);
                mCurrentStatus &= ~ViewStatus.NODATA;
                mCurrentStatus &= ~ViewStatus.NONETWORK;
                mCurrentStatus &= ~ViewStatus.EMPTY;
                mClLoadingContainer.setVisibility(View.GONE);
                break;
            case ViewStatus.NODATA:
                if(mBuilder.useStatusView){
                    mVEmptyContainer.setVisibility(View.VISIBLE);
                    setNoDataView();
                }
                mCurrentStatus &= ~ViewStatus.HASDATA;
                mCurrentStatus &= ~ViewStatus.NONETWORK;
                mCurrentStatus &= ~ViewStatus.EMPTY;
                mClLoadingContainer.setVisibility(View.GONE);
                break;
            case ViewStatus.NONETWORK:
                if (((mCurrentStatus & 0xfff) & ViewStatus.HASDATA) == ViewStatus.HASDATA) {
                    return;
                }
                mCurrentStatus &= ~ViewStatus.HASDATA;
                mCurrentStatus &= ~ViewStatus.NODATA;
                mCurrentStatus &= ~ViewStatus.EMPTY;
                mClLoadingContainer.setVisibility(View.GONE);
                if(mBuilder.useStatusView) {
                    mVEmptyContainer.setVisibility(View.VISIBLE);
                }
                setNoNetworkView();
                break;
            case ViewStatus.LOADING_START:
                mCurrentStatus &= ~ViewStatus.LOADING_END;
                mClLoadingContainer.setVisibility(View.VISIBLE);
                break;
            case ViewStatus.LOADING_END:
                mCurrentStatus &= ~ViewStatus.LOADING_START;
                mClLoadingContainer.setVisibility(View.GONE);
                break;
            case ViewStatus.EMPTY:
                mVEmptyContainer.setVisibility(View.GONE);
                mCurrentStatus = viewStatus;
                mClLoadingContainer.setVisibility(View.GONE);
                break;
            default:
                break;
        }
        mCurrentStatus = mCurrentStatus | viewStatus;
    }

    public static class Builder {
        private ViewGroup container;//父容器
        private CharSequence noDataTip ;
        private int noDataResId;
        /***
         * 重试按钮文案
         */
        private CharSequence retryTip;
        private IRetryClickListener listener;

        private int noNetworkResId;
        private int background;
        private CharSequence noNetworkTip;
        /***
         * 无网络时的点击重试
         */
        private CharSequence noNetworkBtnTip;
        private @ViewStatus
        int viewStatus = ViewStatus.EMPTY;
        /***
         * 是否使用状态图，有些页面不需要展示无数据也，无网络页
         */
        private boolean useStatusView = true;

        public Builder setBackground(int dr) {
            this.background = dr;
            return this;
        }

        public CharSequence getNoDataTip() {
            return noDataTip;
        }

        public Builder setNoDataTip(CharSequence noDataTip) {
            this.noDataTip = noDataTip;
            return this;
        }

        public int getNoDataResId() {
            return noDataResId;
        }

        public Builder setNoDataResId(int noDataResId) {
            this.noDataResId = noDataResId;
            return this;
        }

        public int getNoNetworkResId() {
            return noNetworkResId;
        }

        public Builder setNoNetworkResId(int noNetworkResId) {
            this.noNetworkResId = noNetworkResId;
            return this;
        }

        public CharSequence getNoNetworkTip() {
            return noNetworkTip;
        }

        public Builder setNoNetworkTip(CharSequence noNetworkTip) {
            this.noNetworkTip = noNetworkTip;
            return this;
        }

        public int getViewStatus() {
            return viewStatus;
        }

        public Builder setViewStatus(int viewStatus) {
            this.viewStatus = viewStatus;
            return this;
        }

        public ViewGroup getContainer() {
            return container;
        }

        public Builder setContainer(ViewGroup container) {
            this.container = container;
            return this;
        }

        public Builder setRetryClickListener(IRetryClickListener listener) {
            this.listener = listener;
            return this;
        }

        public IRetryClickListener getRetryClickListener() {
            return listener;
        }

        public Builder setRetryTip(CharSequence tip) {
            this.retryTip = tip;
            return this;
        }

        public CharSequence getRetryTip() {
            return retryTip;
        }

        public CharSequence getNoNetworkBtnTip() {
            return noNetworkBtnTip;
        }

        public Builder setNoNetworkBtnTip(CharSequence noNetworkBtnTip) {
            this.noNetworkBtnTip = noNetworkBtnTip;
            return this;
        }

        public boolean isUseStatusView() {
            return useStatusView;
        }

        public Builder setUseStatusView(boolean useStatusView) {
            this.useStatusView = useStatusView;
            return this;
        }

        public ViewStatusLayout attach() {
            if (container == null) throw new RuntimeException("you must set container first");
            return new ViewStatusLayout(container.getContext(), container, this);
        }


    }

    public interface IRetryClickListener {
        void click(@ViewStatus int viewStatus);
    }

}
