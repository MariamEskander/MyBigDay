package com.android.mybigday.alBawabaServices;

import android.content.Context;

import com.android.mybigday.MyBigDayApplication;
import com.android.mybigday.data.database.MyBigDayDbHelper;
import com.android.mybigday.data.model.ToDoList;
import com.android.mybigday.data.model.ToDos;
import com.android.mybigday.data.remote.GetToDosService;
import com.android.mybigday.myBigDayNotifications.NotificationMessageEVENT;
import com.android.mybigday.util.ApiUtils;
import com.android.mybigday.util.GlobalVariables;
import com.android.mybigday.util.Log;
import com.android.mybigday.util.interactor.Interactor;
import com.android.mybigday.util.interactor.InteractorListener;
import com.android.mybigday.util.interactor.Result;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GetToDosInteractor extends Interactor {

    private final MyBigDayCallbackResponse myCallBack;
    private GetToDosService service;


    public GetToDosInteractor(InteractorListener listener, Context context, MyBigDayCallbackResponse callback) {
        super(listener, context);
        this.myCallBack = callback;

    }

    @Override
    protected Result onTaskWork() {
        final Map<String, Object> data = new LinkedHashMap<>();

        service = ApiUtils.getToDosService();
        service.getToDos()
                .enqueue(new Callback<ToDos>() {
                    @Override
                    public void onResponse(Call<ToDos> call, Response<ToDos> response) {

                        if (response.isSuccessful()) {
                            if (response.body().getCode() == 200) {
                                Log.i("getToDosService", "onResponse successful");

                                ArrayList<ToDoList> toDoLists = (ArrayList<ToDoList>) response.body().getData();
                                MyBigDayDbHelper db = MyBigDayDbHelper.GetFor(MyBigDayApplication.getAppContext());
                                ArrayList<ToDoList> todos = GlobalVariables.GetTodos(db, MyBigDayApplication.getAppContext());


                                if (todos != null && todos.size() != 0) {
                                    for (int i = 0; i < todos.size(); i++) {
                                        for (int j = 0; j < toDoLists.size(); j++) {
                                            if (todos.get(i).getId() == toDoLists.get(j).getId()) {
                                                toDoLists.get(j).setCheck(true);
                                                break;
                                            }
                                        }
                                    }


                                    data.put("error", 0);
                                    data.put("data", toDoLists);
                                    myCallBack.onSuccess(new Result(NotificationMessageEVENT.GET_TODOS, data));
                                } else {
                                    data.put("error", 0);
                                    data.put("data", toDoLists);
                                    myCallBack.onSuccess(new Result(NotificationMessageEVENT.GET_TODOS, data));
                                }

                            } else if (response.body().getCode() == 404) {
                                Log.i("getToDosService", "onResponse fail");
                                data.put("error", 1);
                                if (response.body().getMessage() != null)
                                    data.put("data", response.body().getMessage());
                                else
                                    data.put("data", "An error occurred ,Try again later!!");
                                myCallBack.onError(new Result(NotificationMessageEVENT.GET_TODOS, data));
                            } else {
                                Log.i("getToDosService", "onResponse fail");
                                data.put("error", 1);
                                data.put("data", "An error occurred ,Try again later!!");
                                myCallBack.onError(new Result(NotificationMessageEVENT.GET_TODOS, data));
                            }
                        } else {
                            Log.i("getToDosService", "onResponse fail");
                            data.put("error", 1);
                            data.put("data", "An error occurred ,Try again later!!");
                            myCallBack.onError(new Result(NotificationMessageEVENT.GET_TODOS, data));
                        }
                    }

                    @Override
                    public void onFailure(Call<ToDos> call, Throwable t) {
                        Log.i("getToDosService", "onFailure" + t.getMessage());
                        data.put("error", 1);
                        data.put("data", "An error occurred ,Try again later!!");
                        myCallBack.onError(new Result(NotificationMessageEVENT.GET_TODOS, data));
                    }
                });


        return null;
    }


}


