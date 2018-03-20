package com.example.zlp.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.example.zlp.R;
import com.example.zlp.base.BaseActivity;
import com.example.zlp.fragment.FragmentOne;
import com.example.zlp.fragment.FragmentThree;
import com.example.zlp.fragment.FragmentTwo;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FragmentShowHintActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.rg_navigation)
    RadioGroup mRgNavigation;

    private FragmentManager fragmentManager;
    private HashMap<Integer, Fragment> fragmentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_show_hint);
        ButterKnife.bind(this);
        initListener();
        initMainFragment();
    }


    private void initMainFragment() {
        fragmentManager = getSupportFragmentManager();
        fragmentMap = new HashMap<>();
        setCurrentFragment(0);
    }

    private void setCurrentFragment(int position) {
        FragmentTransaction mTransaction = fragmentManager.beginTransaction();

        getFragmentManager(position, mTransaction);

        for (Map.Entry<Integer, Fragment> entry : fragmentMap.entrySet()) {
            mTransaction.hide(entry.getValue());
        }
        mTransaction.show(fragmentMap.get(position));
        mTransaction.commitAllowingStateLoss();
    }


    // 检索初始化Fragment
    private void getFragmentManager(int position, FragmentTransaction mTransaction) {
        if (fragmentMap.containsKey(position)) {
            return;
        }
        switch (position) {
            case 0:
                FragmentOne fragment = new FragmentOne();
                fragmentMap.put(position, fragment);
                break;
            case 1:
                FragmentTwo taskFragment = new FragmentTwo();
                fragmentMap.put(position, taskFragment);
                break;
            case 2:
                FragmentThree stockFragment = new FragmentThree();
                fragmentMap.put(position, stockFragment);
                break;
        }
        mTransaction.add(R.id.fl_main, fragmentMap.get(position));

    }

    private void initListener() {
        mRgNavigation.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){
            case R.id.radio1:
                setCurrentFragment(0);
                break;
            case R.id.radio2:
                setCurrentFragment(1);
                break;
            case R.id.radio3:
                setCurrentFragment(2);
                break;
        }
    }
    @Override
    protected int setStatusBarColor() {
        return getResources().getColor(R.color.c_33ccff);
    }
}
