package com.example.zlp;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.zlp.wight.XuanFuActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/2/7.
 */
public class WindowService extends Service {

    private final String TAG = this.getClass().getSimpleName();
    private WindowManager.LayoutParams windowParms;
    private WindowManager mWindowManager;
    private View mWindowView;
    private TextView tvPercent;

    private int mStartX;
    private int mStartY;
    private int mEndX;
    private int mEndY;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
        initWindowParam();
        initView();
        addWindowView2Window();
        initClick();
    }


    private void initWindowParam() {

        mWindowManager = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        windowParms = new WindowManager.LayoutParams();
        windowParms.type = WindowManager.LayoutParams.TYPE_PHONE;
        windowParms.format = PixelFormat.TRANSLUCENT;
        windowParms.gravity = Gravity.LEFT| Gravity.TOP;
        windowParms.height = WindowManager.LayoutParams.WRAP_CONTENT;
        windowParms.width = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    private void initView() {
        mWindowView = LayoutInflater.from(getApplication()).inflate(R.layout.window_layout, null);
        tvPercent = (TextView) mWindowView.findViewById(R.id.tv_percent);

    }

    private void addWindowView2Window() {
        mWindowManager.addView(mWindowView, windowParms);
    }

    private void initClick() {

        tvPercent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mStartX = (int) event.getRawX();
                        mStartY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mEndX = (int) event.getRawX();
                        mEndY = (int) event.getRawY();

                        if (needIntercept()) {
                            windowParms.x = (int) (event.getRawX() - mWindowView.getMeasuredWidth() / 2);
                            windowParms.y = (int) (event.getRawY() - mWindowView.getMeasuredHeight() / 2);
                            mWindowManager.updateViewLayout(mWindowView, windowParms);
                            return true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (needIntercept()) {
                            return true;
                        }
                        break;
                }
                return false;
            }
        });

        tvPercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAppAtAppBackground(WindowService.this)) {
                    Intent intent = new Intent(WindowService.this, XuanFuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 判读当前应用程序处于前台还是后台
     *
     * @param context
     * @return
     */
    private boolean isAppAtAppBackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否拦击
     *
     * @return true 拦截 false 不拦截
     */
    private boolean needIntercept() {
        if (Math.abs(mStartX - mEndX) > 5 || Math.abs(mStartY - mEndY) > 5) {
            return true;
        }
        return false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
