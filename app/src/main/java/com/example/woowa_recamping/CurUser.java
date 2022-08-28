package com.example.woowa_recamping;

import android.app.Application;

import com.example.woowa_recamping.data.Data;

import java.util.ArrayList;

public class CurUser extends Application {
    private String uid;
    public void setUid(String id) {
        this.uid = id;
    }
    public String getUid() {
        return uid;
    }

    public ArrayList<Item> list = new ArrayList<>();
}