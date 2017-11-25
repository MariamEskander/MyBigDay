package com.android.mybigday.data.remote;


import com.android.mybigday.data.model.Tips;
import com.android.mybigday.data.model.ToDos;

import retrofit2.Call;
import retrofit2.http.POST;


public interface GetToDosService {


    @POST("guest_todos")
    Call<ToDos> getToDos();

}