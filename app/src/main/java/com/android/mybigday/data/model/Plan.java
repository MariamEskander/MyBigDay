package com.android.mybigday.data.model;

import android.database.Cursor;

import com.android.mybigday.data.database.MyBigDayContract;

/**
 * Created by Mariam on 11/25/2017.
 */

public class Plan {
    private String name;
    private String place;
    private String guests;
    private String cost;

    public Plan(String name, String place, String guests, String cost) {
        this.name = name;
        this.place = place;
        this.guests = guests;
        this.cost = cost;
    }

    public Plan(Cursor cursor) {
        this(cursor.getString(cursor.getColumnIndex(MyBigDayContract.PlanEntry.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(MyBigDayContract.PlanEntry.COLUMN_PlACE)),
                cursor.getString(cursor.getColumnIndex(MyBigDayContract.PlanEntry.COLUMN_GUEST)),
                cursor.getString(cursor.getColumnIndex(MyBigDayContract.PlanEntry.COLUMN_COST)));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getGuests() {
        return guests;
    }

    public void setGuests(String guests) {
        this.guests = guests;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
