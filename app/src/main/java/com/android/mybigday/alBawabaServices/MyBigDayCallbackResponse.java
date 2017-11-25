package com.android.mybigday.alBawabaServices;


import com.android.mybigday.util.interactor.Result;

/**interface to handle response**/
public interface MyBigDayCallbackResponse {
    void onSuccess(Result result);
    void onError(Result result);
}
