package com.example.zlp.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.zlp.R;
import com.example.zlp.utils.PreferenceUtils;

import java.util.Locale;

/**
 * 国际化
 */
public class InternatoionalLanguageActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initLocaleLanguage(PreferenceUtils.getString(this,"language"));
        setContentView(R.layout.activity_internatoional_language);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        switch (PreferenceUtils.getString(this,"language")){
            case "zh":
                ((RadioButton)radioGroup.getChildAt(0)) .setChecked(true);
                break;
            case "en":
                ((RadioButton)radioGroup.getChildAt(1)) .setChecked(true);
                break;
            case "ko":
                ((RadioButton)radioGroup.getChildAt(2)) .setChecked(true);
                break;
        }
        radioGroup.setOnCheckedChangeListener(this);
    }


    private void initLocaleLanguage(String language) {
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = new Locale(language,"");
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());//更新配置
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.radio1:
                PreferenceUtils.putString(InternatoionalLanguageActivity.this,"language","zh");
                refresh();
                break;
            case R.id.radio2:
                PreferenceUtils.putString(InternatoionalLanguageActivity.this,"language","en");
                refresh();
                break;
            case R.id.radio3:
                PreferenceUtils.putString(InternatoionalLanguageActivity.this,"language","ko");
                refresh();
                break;
        }
    }

    private void refresh() {
        Intent intent = new Intent(this,InternatoionalLanguageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
