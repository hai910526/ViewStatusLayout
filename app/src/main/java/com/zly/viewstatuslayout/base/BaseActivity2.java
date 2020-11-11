package com.zly.viewstatuslayout.base;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.zly.viewstatuslayout.R;
import com.zly.viewstatuslayout.ViewStatus;
import com.zly.viewstatuslayout.widget.ViewStatusLayout;

/**
 * Cerated by xiaoyehai
 * Create date : 2020/11/11 14:38
 * description : 自定义一个View来切换布局。
 */
public abstract class BaseActivity2 extends AppCompatActivity {

    protected ViewStatusLayout mViewStatusLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        init();

        initViewStatusLayout();

        initData();

    }

    /***
     * 是否需要全局空数据页
     */
    protected boolean needViewStatus() {
        return true;
    }

    private void initViewStatusLayout() {
        if (!needViewStatus()) {
            return;
        }
        if (mViewStatusLayout != null) {
            return;
        }

        ViewStatusLayout.Builder builder = createViewStatusBuilder();
        if (builder.getContainer() == null) {
            ViewGroup vp = getViewStatusParent();
            builder.setContainer(vp);  //设置ViewStatusLayout的父容器
        }
        if (builder.getContainer() != null) {
            mViewStatusLayout = builder.attach();
        }
    }

    protected ViewStatusLayout.Builder createViewStatusBuilder() {
        return new ViewStatusLayout.Builder()
                .setNoDataTip("暂无数据")
                .setNoDataResId(R.mipmap.empty_order_list)
                .setNoNetworkTip("无网络，请先检查网络连接")
                .setNoNetworkResId(R.mipmap.no_network)
                .setNoNetworkBtnTip("点击重试");
    }


    /**
     * 获取ViewStatusLayout的父容器
     *
     * @return
     */
    protected ViewGroup getViewStatusParent() {
        //如果子类Activity 指定了具体容器，则直接加载到此容器中
        ViewGroup viewStatusContainer = findViewById(R.id.view_status_container);
        Log.e("xyh", "getViewStatusParent: "+viewStatusContainer );
        if (viewStatusContainer != null) {
            return viewStatusContainer;
        }

        ViewGroup content = findViewById(android.R.id.content);
        if (content.getChildCount() == 0) {
            return content;
        }

        //rootV就是子类 Activity 自己定义的根布局
        View rootV = content.getChildAt(0);  //LinearLayout

        //如果子布局不是viewgroup直接加载到content布局中
        if (!(rootV instanceof ViewGroup)) {
            return content;
        }

        ViewGroup firstVp = (ViewGroup) rootV; //LinearLayout
        //如果根布局下没有别的控件，则加载到跟布局中
        if (firstVp.getChildCount() == 0) {
            return firstVp;
        }

        //标题栏
        ViewParent ctb = firstVp.findViewById(R.id.ctb_title); //RelativeLayout
        if (ctb != null) {
            return createStatusView(firstVp);
        } else {
            return firstVp;
        }
    }

    private FrameLayout createStatusView(ViewGroup rootV) {
        FrameLayout container = new FrameLayout(this);
        container.setId(R.id.view_status_container);
        ViewGroup.LayoutParams lp;
        if (rootV instanceof ConstraintLayout) {
            ConstraintLayout.LayoutParams clp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.MATCH_PARENT);
            clp.topToBottom = R.id.ctb_title;
            clp.bottomToBottom = rootV.getId();
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

    protected abstract int getLayoutId();

    protected abstract void init();

    protected abstract void initData();


}
