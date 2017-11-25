package com.android.mybigday.util;



import com.android.mybigday.data.remote.GetTipsService;
import com.android.mybigday.data.remote.GetToDosService;
import com.android.mybigday.data.remote.RetrofitClient;


/**
 * Created by Mariam on 10/5/2017.
 */
public class ApiUtils {

    private static final String BASE_URL = "http://www.thejerb.com/jerb/public/api/";
    public static final String IMAGE_BASE = "http://www.thejerb.com/jerb/public/uploads/tips/";

    public static GetTipsService getTipsService() {
        return RetrofitClient.getClient(BASE_URL).create(GetTipsService.class);
    }

    public static GetToDosService getToDosService() {
        return RetrofitClient.getClient(BASE_URL).create(GetToDosService.class);
    }


}