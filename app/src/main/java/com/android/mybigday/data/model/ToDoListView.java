package com.android.mybigday.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mariam on 11/20/2017.
 */

public class ToDoListView {

    private int id;
    private String title;
    private boolean check;

    public ToDoListView(int id, String title, boolean check) {
        this.id = id;
        this.title = title;
        this.check = check;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}

