package com.android.mybigday.util;



import android.content.Context;

import com.android.mybigday.data.database.DataBaseUtil;
import com.android.mybigday.data.database.MyBigDayDbHelper;
import com.android.mybigday.data.model.ToDoList;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;


/**
 * Created by Mariam on 8/15/2017.
 */


public class GlobalVariables {
    public static Lock lc = null;
    public static int dbcount = 0;

      private static ArrayList<ToDoList> toDoList = null;
    public static ArrayList<ToDoList> GetTodos(MyBigDayDbHelper db, Context context) {
        if (lc == null) {
//            Intent i = new Intent(context, HomeActivity.class);
//            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i);
           return null;
        } else {
            if (toDoList == null) {
                db.open();
                toDoList = DataBaseUtil.getTodos(db);
                db.close();

            }
            return toDoList;
        }

    }

    public static void UpdateTodo(MyBigDayDbHelper db, ArrayList<ToDoList> u) {
        int j = -1 ;
        db.open();
        for (int i = 0 ; i<u.size() ; i++) {
             j = DataBaseUtil.insertOrUpdateTodo(db, u.get(i));
        }
        db.close();
        if (j != -1){
            toDoList = u;
        }
    }


    public static void DeleteTodo(MyBigDayDbHelper db) {
        if (toDoList != null) {
            db.open();
            DataBaseUtil.getTodos(db);
            db.close();
            toDoList = null;
        }
    }

}
