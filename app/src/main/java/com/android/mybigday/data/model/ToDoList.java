package com.android.mybigday.data.model;

import android.database.Cursor;

import com.android.mybigday.data.database.MyBigDayContract;
import com.android.mybigday.data.database.MyBigDayDbHelper;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mariam on 11/20/2017.
 */

public class ToDoList {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    private boolean check ;

    public ToDoList(Integer id, String title) {
        this.id = id;
        this.title = title;
        this.check = false;
    }

    public ToDoList(Cursor cursor) {
        this(cursor.getInt(cursor.getColumnIndex(MyBigDayContract.TodoEntry.COLUMN_TODO_ID)),
                cursor.getString(cursor.getColumnIndex(MyBigDayContract.TodoEntry.COLUMN_TODO_TEXT)));
    }

    public ToDoList(Integer id, String title, boolean check) {
        this.id = id;
        this.title = title;
        this.check = check;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}

