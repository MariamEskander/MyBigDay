package com.android.mybigday.data.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.android.mybigday.data.model.Plan;
import com.android.mybigday.data.model.ToDoList;
import com.android.mybigday.util.Log;

import java.util.ArrayList;

/**
 * Created by Mariam on 10/11/2017.
 */



public class DataBaseUtil {

    public static int insertOrUpdateTodo(MyBigDayDbHelper db, ToDoList toDoList) {
        if (db == null) {
            return -1;
        }
        try {
            ContentValues cv = new ContentValues();
            cv.put(MyBigDayContract.TodoEntry.COLUMN_TODO_ID, toDoList.getId());
            cv.put(MyBigDayContract.TodoEntry.COLUMN_TODO_TEXT,toDoList.getTitle());
            return (int) db.insertOrUpdate(MyBigDayContract.TodoEntry.TABLE_NAME, cv, MyBigDayContract.TodoEntry.COLUMN_TODO_ID + " = ? ", new String[]{String.valueOf(toDoList.getId())});

        } catch (Exception e) {
            System.err.println(e.getMessage());
            Log.i("Database", "insertOrUpdateTodo Error");
            return -1;
        }
    }


    // delete user info using id
    public static boolean deleteTodo(MyBigDayDbHelper AlexDB , int id) {
        try {
            return AlexDB.removeRecord(MyBigDayContract.TodoEntry.TABLE_NAME,MyBigDayContract.TodoEntry.COLUMN_TODO_ID + " = ? ", new String[]{String.valueOf(id)});

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }


    public static ArrayList<ToDoList> getTodos(MyBigDayDbHelper db) {
        Cursor cursor = null;
        ArrayList<ToDoList> toDoLists = new ArrayList<>();
        ToDoList toDoList = null;
        try {
            cursor = db.getAllRecord(MyBigDayContract.TodoEntry.TABLE_NAME);
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    toDoList = new ToDoList(cursor);
                    toDoLists.add(toDoList);
                }
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
            Log.i("database", "getTodos");
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return toDoLists;
    }








    public static int insertOrUpdatePlan(MyBigDayDbHelper db, Plan plan) {
        if (db == null) {
            return -1;
        }
        try {
            ContentValues cv = new ContentValues();
            cv.put(MyBigDayContract.PlanEntry.COLUMN_NAME, plan.getName());
            cv.put(MyBigDayContract.PlanEntry.COLUMN_PlACE, plan.getPlace());
            cv.put(MyBigDayContract.PlanEntry.COLUMN_GUEST, plan.getGuests());
            cv.put(MyBigDayContract.PlanEntry.COLUMN_COST, plan.getCost());


            return (int) db.insertOrUpdate(MyBigDayContract.PlanEntry.TABLE_NAME, cv, MyBigDayContract.PlanEntry.COLUMN_NAME + " = ? ", new String[]{String.valueOf(plan.getName())});

        } catch (Exception e) {
            System.err.println(e.getMessage());
            Log.i("Database", "insertOrUpdatePlan Error");
            return -1;
        }
    }


    // delete user info using id
    public static boolean deletePlan(MyBigDayDbHelper AlexDB , String name) {
        try {
            return AlexDB.removeRecord(MyBigDayContract.PlanEntry.TABLE_NAME,MyBigDayContract.PlanEntry.COLUMN_NAME + " = ? ", new String[]{String.valueOf(name)});

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }


    public static ArrayList<Plan> getPlans(MyBigDayDbHelper db) {
        Cursor cursor = null;
        ArrayList<Plan> plans = new ArrayList<>();
        Plan plan = null;
        try {
            cursor = db.getAllRecord(MyBigDayContract.PlanEntry.TABLE_NAME);
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    plan = new Plan(cursor);
                    plans.add(plan);
                }
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
            Log.i("database", "getPlans");
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return plans;
    }

}