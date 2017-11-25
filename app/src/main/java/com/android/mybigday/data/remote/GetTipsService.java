package com.android.mybigday.data.remote;


import com.android.mybigday.data.model.Tips;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface GetTipsService {


    @GET("tips")
    Call<Tips> getTips();

}