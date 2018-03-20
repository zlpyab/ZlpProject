package com.example.zlp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.zlp.R;

import java.util.List;

import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;
import cn.aigestudio.datepicker.views.MonthView;

public class DataPicterChoiceActivity extends Activity {

    private DatePicker datePicker;
    private MonthView monthView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_picter_choice);
        datePicker = (DatePicker) findViewById(R.id.datapicker);

        datePicker.setDate(2016,10);
        datePicker.setMode(DPMode.MULTIPLE);

        datePicker.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
            @Override
            public void onDateSelected(List<String> date) {
                Toast.makeText(DataPicterChoiceActivity.this, date.toString(), Toast.LENGTH_SHORT).show();
            }
        });




    }
}
