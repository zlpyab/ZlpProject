package com.example.zlp.modle;

import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by zlp on 2017/4/17.
 */
public class ListData implements Serializable {

     private  String titile;
     private int type = 1;

    public ListData(String titile, int type) {
        this.titile = titile;
        this.type = type;
    }

    public String getTitile() {
        return titile;
    }

    public int getType() {
        return type;
    }
}
