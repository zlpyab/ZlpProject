package com.example.zlp.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.example.zlp.R;
import com.example.zlp.wight.SetPolyToPoly;

public class SetPolytoPolyActivity extends Activity {

    private SetPolyToPoly polyToPoly;
    private RadioGroup group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_polyto_poly);

        polyToPoly = (SetPolyToPoly) findViewById(R.id.poly);
        group = (RadioGroup) findViewById(R.id.radioGroup);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.radio0:
                        polyToPoly.setTestPoint(0);
                        break;
                    case R.id.radio1:
                        polyToPoly.setTestPoint(1);
                        break;
                    case R.id.radio2:
                        polyToPoly.setTestPoint(2);
                        break;
                    case R.id.radio3:
                        polyToPoly.setTestPoint(3);
                        break;
                    case R.id.radio4:
                        polyToPoly.setTestPoint(4);
                        break;
                }
            }
        });
    }
}
