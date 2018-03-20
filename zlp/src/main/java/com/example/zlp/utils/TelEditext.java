package com.example.zlp.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by zlp on 2017/8/14.
 */

public class TelEditext extends EditText {

        public boolean isBankCard=true;
        private String addString=" ";
        private boolean isRun=false;

        public TelEditext(Context context){
            super(context);
        }
        public TelEditext(Context context,AttributeSet attributes){
            super(context,attributes);
            init();
        }
        private void init() {
            addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(isRun){//这几句要加，不然每输入一个值都会执行两次onTextChanged()，导致堆栈溢出，原因不明
                        isRun = false;
                        return;
                    }
                    isRun = true;
                    if (isBankCard) {
                        String finalString="";
                        int index=0;
                        String telString=s.toString().replace(" ", "");
                        if ((index+3)<telString.length()) {
                            finalString+=(telString.substring(index, index+3)+addString);
                            index+=3;
                        }
                        while ((index+4)<telString.length()) {
                            finalString+=(telString.substring(index, index+4)+addString);
                            index+=4;
                        }
                        finalString+=telString.substring(index,telString.length());
                        TelEditext.this.setText(finalString);
                        //此语句不可少，否则输入的光标会出现在最左边，不会随输入的值往右移动
                        TelEditext.this.setSelection(finalString.length());
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
}
