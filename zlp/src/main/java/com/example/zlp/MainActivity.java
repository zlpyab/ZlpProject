package com.example.zlp;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zlp.activity.AddHeaderRecyclerViewActivity;
import com.example.zlp.activity.ChenjingshiActivity;
import com.example.zlp.activity.ChoicepicerActivity;
import com.example.zlp.activity.CompassViewActivity;
import com.example.zlp.activity.DataPicterChoiceActivity;
import com.example.zlp.activity.Dian9TuActivity;
import com.example.zlp.activity.FragmentShowHintActivity;
import com.example.zlp.activity.GaoDeLocationActivity;
import com.example.zlp.activity.GetPhoneNumberActivity;
import com.example.zlp.activity.GuideActivity;
import com.example.zlp.activity.ImmersionActivity;
import com.example.zlp.activity.InternatoionalLanguageActivity;
import com.example.zlp.activity.MyPayKeyborActivity;
import com.example.zlp.activity.MyViewPagerActivity;
import com.example.zlp.activity.PermissionActivity;
import com.example.zlp.activity.Rotate3AnimationActivity;
import com.example.zlp.activity.RoundAndCircleImageViewActivity;
import com.example.zlp.activity.RoundProgressBarActivity;
import com.example.zlp.activity.SeekBarActivity;
import com.example.zlp.activity.SetPolytoPolyActivity;
import com.example.zlp.activity.ShowDongHuaActivity;
import com.example.zlp.activity.SoftPanActivity;
import com.example.zlp.activity.StateSavedActivity;
import com.example.zlp.activity.SurfaceViewActivity;
import com.example.zlp.activity.SwipeRefreshActivity;
import com.example.zlp.activity.ViewPagerSlideActivity;
import com.example.zlp.wight.CustomDialot;
import com.example.zlp.wight.RoundAcActivity;
import com.example.zlp.wight.XuanFuActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout rlContainer;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        list.add("自定义对话框");
        list.add("自定义圆形进度条");
        list.add("自定义罗盘");
        list.add("自定义圆弧");
        list.add("获取通讯录");
        list.add("自定义ViewPager");
        list.add("选择照片");
        list.add("悬浮窗");
        list.add("动画");
        list.add("recyclerview");
        list.add("沉浸式状态栏");
        list.add("日历选择日期");
        list.add("引导页");
        list.add("Matrix setPolytoPoly");
        list.add("3D旋转");
        list.add("surfaceview");
        list.add("软键盘遮挡输入框");
        list.add("SwipeRefreshLayout");
        list.add("支付键盘");
        list.add("Activity+Fragment+沉浸式");
        list.add("viewpager缩放");
        list.add("圆形圆角Imageview");
        list.add("Fragment的hint与show");
        list.add("高德地图定位");
        list.add("获取权限");
        list.add("seekBar");
        list.add("点9图");
        list.add("国际化");

        rlContainer = (LinearLayout) findViewById(R.id.rl_container);
        rlContainer.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            Button button = new Button(this);
            button.setText(list.get(i));
            button.setId(i);
            button.setOnClickListener(this);
            rlContainer.addView(button,params);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case 0:
                final CustomDialot dialog = new CustomDialot(MainActivity.this, "你好", "确定", "取消");
                dialog.show();
                dialog.setOnclickCustomDialogListener(new CustomDialot.OnclickCustomDialogListener() {
                    @Override
                    public void doLeftBt() {
                        Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void doRightBt() {
                        Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                break;
            case 1:
                intent = new Intent(MainActivity.this, RoundProgressBarActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(MainActivity.this, CompassViewActivity.class);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(MainActivity.this, RoundAcActivity.class);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(MainActivity.this, GetPhoneNumberActivity.class);
                startActivity(intent);
                break;
            case 5:
                intent = new Intent(MainActivity.this, MyViewPagerActivity.class);
                startActivity(intent);
                break;
            case 6:
                intent = new Intent(MainActivity.this, ChoicepicerActivity.class);
                startActivity(intent);
                break;
            case 7:
                intent = new Intent(MainActivity.this, XuanFuActivity.class);
                startActivity(intent);
                break;
            case 8:
                intent = new Intent(MainActivity.this, ShowDongHuaActivity.class);
                startActivity(intent);
                break;
            case 9:
                intent = new Intent(MainActivity.this, AddHeaderRecyclerViewActivity.class);
                startActivity(intent);
                break;
            case 10:
                intent = new Intent(MainActivity.this, ChenjingshiActivity.class);
                startActivity(intent);
                break;
            case 11:
                intent = new Intent(MainActivity.this, DataPicterChoiceActivity.class);
                startActivity(intent);
                break;
            case 12:
                intent = new Intent(MainActivity.this, GuideActivity.class);
                startActivity(intent);
                break;
            case 13:
                intent = new Intent(MainActivity.this, SetPolytoPolyActivity.class);
                startActivity(intent);
                break;
            case 14:
                intent = new Intent(MainActivity.this, Rotate3AnimationActivity.class);
                startActivity(intent);
                break;
            case 15:
                intent = new Intent(MainActivity.this, SurfaceViewActivity.class);
                startActivity(intent);
                break;
            case 16:
                intent = new Intent(MainActivity.this, SoftPanActivity.class);
                startActivity(intent);
                break;
            case 17:
                intent = new Intent(MainActivity.this, SwipeRefreshActivity.class);
                startActivity(intent);
                break;
            case 18:
                intent = new Intent(MainActivity.this, MyPayKeyborActivity.class);
                startActivity(intent);
                break;
            case 19:
                intent = new Intent(MainActivity.this, ImmersionActivity.class);
                startActivity(intent);
                break;
            case 20:
                intent = new Intent(MainActivity.this, ViewPagerSlideActivity.class);
                startActivity(intent);
                break;
            case 21:
                intent = new Intent(MainActivity.this, RoundAndCircleImageViewActivity.class);
                startActivity(intent);
                break;
            case 22:
                intent = new Intent(MainActivity.this, FragmentShowHintActivity.class);
                startActivity(intent);
                break;
            case 23:
                intent = new Intent(MainActivity.this, GaoDeLocationActivity.class);
                startActivity(intent);
                break;
            case 24:
                intent = new Intent(MainActivity.this, PermissionActivity.class);
                startActivity(intent);
                break;
            case 25:
                intent = new Intent(MainActivity.this, SeekBarActivity.class);
                startActivity(intent);
                break;
            case 26:
                intent = new Intent(MainActivity.this, Dian9TuActivity.class);
                startActivity(intent);
                break;
            case 27:
                intent = new Intent(MainActivity.this, InternatoionalLanguageActivity.class);
                startActivity(intent);
        }
    }
}
