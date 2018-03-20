package com.example.zlp.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.zlp.R;
import com.example.zlp.modle.PieData;
import com.example.zlp.wight.CircleProgressView;
import com.example.zlp.wight.CustomBinTuView;

import java.util.ArrayList;

public class CompassViewActivity extends Activity {

    private CustomBinTuView customBintuview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass_view);
      //  initBinTu();
        initCircleProgressView();
    }

    private void initCircleProgressView() {
        CircleProgressView circleProgressView = (CircleProgressView) findViewById(R.id.circleProgressView);
        circleProgressView.startAnimation();

    }

    private void initBinTu() {
        customBintuview = (CustomBinTuView) findViewById(R.id.customBintuview);

        ArrayList<PieData> mData = new ArrayList<>();
        PieData pieData = new PieData("大米",90);
        PieData pieData2 = new PieData("大米",20);
        PieData pieData3 = new PieData("大米",30);
        PieData pieData4 = new PieData("大米",20);
        PieData pieData5 = new PieData("大米",70);
        PieData pieData6 = new PieData("大米",80);

        mData.add(pieData);
        mData.add(pieData2);
        mData.add(pieData3);
        mData.add(pieData4);
        mData.add(pieData5);
        mData.add(pieData6);

        customBintuview.setmData(mData);
        customBintuview.setmStartAngle(0);
    }
}
