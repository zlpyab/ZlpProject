package com.example.zlp.activity;

import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zlp.R;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.OSUtils;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    public ImmersionBar mImmersionBar;
    private static final String NAVIGATIONBAR_IS_MIN = "navigationbar_is_min";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());

        //绑定控件
        ButterKnife.bind(this);
        //初始化沉浸式
        if (isImmersionBarEnabled())
            initImmersionBar();
        //初始化数据
        initData();
        //View与数据绑定
        initView();
        //设置监听
        setListener();
        //解决华为emui3.0与3.1手机手动隐藏底部导航栏时，导航栏背景色未被隐藏的问题
        if (OSUtils.isEMUI3_1()) {
            //第一种
            getContentResolver().registerContentObserver(Settings.System.getUriFor
                    (NAVIGATIONBAR_IS_MIN), true, mNavigationStatusObserver);
            //第二种,禁止对导航栏的设置
            //mImmersionBar.navigationBarEnable(false).init();
        }
    }


    protected void setListener() {
    }

    protected void initData() {

    }

    protected abstract int setLayoutId();


    protected void initView() {
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }

    /**
     * 是否可以使用沉浸式
     *
     * @return
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }


    private ContentObserver mNavigationStatusObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            int navigationBarIsMin = Settings.System.getInt(getContentResolver(),
                    NAVIGATIONBAR_IS_MIN, 0);
            if (navigationBarIsMin == 1) {
                //导航键隐藏了
                mImmersionBar.transparentNavigationBar().init();
            } else {
                //导航键显示了
                mImmersionBar.navigationBarColor(android.R.color.black) //隐藏前导航栏的颜色
                        .fullScreen(false)
                        .init();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImmersionBar.destroy();//销毁
    }
}
