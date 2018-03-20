package com.example.zlp.activity;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.zlp.R;

public class SoftPanActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_pan);

        LinearLayout llMain = (LinearLayout) findViewById(R.id.main);
        Button bt = (Button) findViewById(R.id.bt_login);

        addLayoutListener(llMain, bt);
    }

    private void addLayoutListener(final View main, final View scroll) {

        main.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rectF = new Rect();
                //1,获取main在窗体的可视区域
                main.getWindowVisibleDisplayFrame(rectF);
                //2,获取main在窗体的不可视区域高度，在键盘没有弹起时 main.getRootView().getHeight()调节高度应该和rectF.bottom高度一样
                int mainInvisibleHeight = main.getRootView().getHeight() - rectF.bottom;
                //3,不可见区域大于100说明软键盘弹起了
                if (mainInvisibleHeight > 100) {
                    int[] location = new int[2];
                    //4,获取scroll的窗体坐标，算出main需要滚动的高度
                    scroll.getLocationInWindow(location);
                    int scrollHeight = (location[1] + scroll.getHeight() - rectF.bottom);
                    //5，让界面整体上移键盘的高度
                    main.scrollTo(0, scrollHeight);
                } else {
                    //3，不可见区域小于100，说明键盘隐藏，把界面下移，移回原有高度
                    main.scrollTo(0,0);
                }
            }
        });
    }
}
