package com.zly.viewstatuslayout.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ScrollingView;
import androidx.fragment.app.Fragment;

import com.zly.viewstatuslayout.R;
import com.zly.viewstatuslayout.ViewStatus;
import com.zly.viewstatuslayout.widget.ViewStatusLayout;

/**
 * Cerated by xiaoyehai
 * Create date : 2020/11/11 17:58
 * description : 自定义一个View来切换布局。
 */
public class BaseFragment2 extends Fragment {

    protected ViewStatusLayout mViewStatusLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewStatusLayout(getView());
    }

    /***
     * 是否需要全局空数据页
     */
    protected boolean needViewStatus(){
        return true;
    }

    private void initViewStatusLayout(View view){
        if(!needViewStatus()) {
            return;
        }
        if(mViewStatusLayout != null ) {
            return;
        }
        ViewStatusLayout.Builder builder = createViewStatusBuilder();
        if(builder.getContainer() == null){
            ViewGroup vp = getViewStatusParent(view);
            builder.setContainer(vp);
        }
        if (builder.getContainer() != null) {
            mViewStatusLayout = builder.attach();
        }
    }

    protected ViewStatusLayout.Builder createViewStatusBuilder(){
        return new ViewStatusLayout.Builder()
                .setNoDataTip("没有找到相关内容~")
                .setNoDataResId(R.mipmap.empty_order_list)
                .setNoNetworkTip("网络不稳定，请重试")
                .setNoNetworkResId(R.mipmap.no_network);
    }



    protected ViewGroup getViewStatusParent(View view) {
        //如果指定了具体容器，则直接加载到此容器中
        ViewGroup viewStatusContainer = view.findViewById(R.id.view_status_container);
        if (viewStatusContainer != null) {
            return viewStatusContainer;
        }
        if(!(view instanceof ViewGroup) || view instanceof ScrollingView) {
            return null;
        }

        viewStatusContainer = (ViewGroup)view;
        //如果跟布局下没有别的控件，则加载到跟布局中
        if (viewStatusContainer.getChildCount() == 0) {
            return viewStatusContainer;
        }
        ViewParent ctb = viewStatusContainer.findViewById(R.id.ctb_title);
        if (ctb != null) {
            return createStatusView(viewStatusContainer);
        } else {
            return viewStatusContainer;
        }
    }
    private FrameLayout createStatusView(ViewGroup rootV) {
        if(getContext() == null) {
            return null;
        }
        FrameLayout container = new FrameLayout(getContext());
        container.setId(R.id.view_status_container);
        ViewGroup.LayoutParams lp;
        if (rootV instanceof ConstraintLayout) {
            ConstraintLayout.LayoutParams clp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
            clp.topToBottom = R.id.ctb_title;
            clp.startToStart = rootV.getId();
            lp = clp;
        } else if (rootV instanceof RelativeLayout) {
            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            rlp.addRule(RelativeLayout.BELOW, R.id.ctb_title);
            lp = rlp;
        } else {
            lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        rootV.addView(container, lp);
        return container;
    }




    public void showDialog(String title) {
        if (mViewStatusLayout != null) {
            mViewStatusLayout.switchView(ViewStatus.LOADING_START);
        }
    }

    public void dismissDialog() {
        if (mViewStatusLayout != null) {
            mViewStatusLayout.switchView(ViewStatus.LOADING_END);
        }
    }

}
