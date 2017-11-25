package com.android.mybigday.alBawabaServices;

import android.content.Context;

import com.android.mybigday.data.model.Tips;
import com.android.mybigday.data.model.TipsList;
import com.android.mybigday.data.remote.GetTipsService;
import com.android.mybigday.myBigDayNotifications.NotificationMessageEVENT;
import com.android.mybigday.util.ApiUtils;
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


public class GetTipsInteractor extends Interactor {

    private final MyBigDayCallbackResponse myCallBack;
    private GetTipsService service;


    public GetTipsInteractor(InteractorListener listener, Context context, MyBigDayCallbackResponse callback) {
        super(listener, context);
        this.myCallBack = callback;

    }

    @Override
    protected Result onTaskWork() {
        final Map<String, Object> data = new LinkedHashMap<>();

        service = ApiUtils.getTipsService();
        service.getTips()
                .enqueue(new Callback<Tips>() {
                    @Override
                    public void onResponse(Call<Tips> call, Response<Tips> response) {

                        if (response.isSuccessful()) {
                            if (response.body().getCode() == 200) {
                                Log.i("getTipsService", "onResponse successful");
                                data.put("error", 0);
                                data.put("data", response.body().getData());
                                myCallBack.onSuccess(new Result(NotificationMessageEVENT.GET_TIPS, data));
                            }else  if (response.body().getCode() == 404) {
                                Log.i("getTipsService", "onResponse fail");
                                data.put("error", 1);
                                if (response.body().getMessage() != null)
                                    data.put("data", response.body().getMessage());
                                else
                                    data.put("data", "An error occurred ,Try again later!!");
                                myCallBack.onError(new Result(NotificationMessageEVENT.GET_TIPS, data));
                            } else {
                                Log.i("getTipsService", "onResponse fail");
                                data.put("error", 1);
                                data.put("data", "An error occurred ,Try again later!!");
                                myCallBack.onError(new Result(NotificationMessageEVENT.GET_TIPS, data));
                            }
                        } else {
                            Log.i("getTipsService", "onResponse fail");
                            data.put("error", 1);
                            data.put("data", "An error occurred ,Try again later!!");
                            myCallBack.onError(new Result(NotificationMessageEVENT.GET_TIPS, data));
                        }
                    }

                    @Override
                    public void onFailure(Call<Tips> call, Throwable t) {
                        Log.i("getTipsService", "onFailure" + t.getMessage());
                        data.put("error", 1);
                        data.put("data", "An error occurred ,Try again later!!");
                        myCallBack.onError(new Result(NotificationMessageEVENT.GET_TIPS, data));
                    }
                });


        return null;
    }


}

//
//    ArrayList<TipsList> tipsLists = new ArrayList<>();
//        tipsLists.add(new TipsList( 1,"ttttt",
//                ApiUtils.IMAGE_BASE+"22ab8b46843a3835c38ce3a8eba771d7--wedding-day-tips-budget-wedding.jpga45cd880ffa1eb187a9a7b21a2249076",
//                "", "", ""));
//                tipsLists.add(new TipsList( 2,"sssss",
//                ApiUtils.IMAGE_BASE+"ddfad863c121146272290041470820f2--wedding-guest-list-online-wedding-rsvp.jpg71fa31599a749dd7327515e96a75b1db",
//
//                "", "", ""));
//                tipsLists.add(new TipsList( 3,"nnnnn", ApiUtils.IMAGE_BASE+"ddfad863c121146272290041470820f2--wedding-guest-list-online-wedding-rsvp.jpg71fa31599a749dd7327515e96a75b1db", "", "", ""));
//                data.put("error", 0);
//                data.put("data", tipsLists);
//                myCallBack.onSuccess(new Result(NotificationMessageEVENT.GET_TIPS, data));