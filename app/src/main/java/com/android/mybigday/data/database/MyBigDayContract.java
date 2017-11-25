package com.android.mybigday.data.database;

import android.provider.BaseColumns;

/**
 * Created by Mariam on 10/11/2017.
 */

public class MyBigDayContract {

    public static final class PlanEntry implements BaseColumns{
        public static final String TABLE_NAME = "plan";
        public static final String COLUMN_PlACE = "plan_place";
        public static final String COLUMN_GUEST = "plan_guest";
        public static final String COLUMN_COST = "plan_cost";
        public static final String COLUMN_NAME = "plan_name";

    }




    public static final class TodoEntry implements BaseColumns{
        public static final String TABLE_NAME = "todo";
        public static final String COLUMN_TODO_ID = "todo_id";
        public static final String COLUMN_TODO_TEXT = "todo_text";


    }
}
