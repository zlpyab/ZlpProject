package com.example.zlp.activity;


import android.os.Bundle;
import android.view.View;

import com.example.zlp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImmersionActivity extends BaseActivity {


    @Bind(R.id.top_view)
    View mTopView;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_immersion;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
    }


    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected void setListener() {
        super.setListener();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
