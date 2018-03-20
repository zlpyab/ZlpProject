package com.example.zlp.activity;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zlp.R;
import com.example.zlp.wight.ArcSeekBarParent;
import com.example.zlp.wight.CircleSeekBar;

public class SeekBarActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_seek_bar);

//        CircleSeekBar circleSeekBar = (CircleSeekBar) findViewById(R.id.circle_seekbar);
//        circleSeekBar.setProgress(100);
//        circleSeekBar.setProgressFrontColor(Color.RED);
//        circleSeekBar.setOnSeekBarChangeListener(new CircleSeekBarOnChangeListener());

        final TextView textView = (TextView) findViewById(R.id.seek_bar_progress);
        ArcSeekBarParent seekBar = (ArcSeekBarParent) findViewById(R.id.seek_bar);
        seekBar.setListener(new ArcSeekBarParent.OnProgressChangedListener() {
            @Override
            public void OnProgressChanged(int level) {
                textView.setText(String.valueOf(level));
            }
        });

    }

    private class CircleSeekBarOnChangeListener implements CircleSeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(int progress) {
             Log.d("YiLog", "onProgressChanged progress = " + progress);
        }

        @Override
        public void onStartTrackingTouch() {
            Log.d("YiLog", "onStartTrackingTouch");
        }

        @Override
        public void onStopTrackingTouch() {
            Log.d("YiLog", "onStopTrackingTouch");
        }

    }
}
