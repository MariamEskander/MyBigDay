package com.android.mybigday.ui.fragment.firstFragment.presenter;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.android.mybigday.MyBigDayApplication;
import com.android.mybigday.alBawabaServices.GetTipsInteractor;
import com.android.mybigday.alBawabaServices.GetToDosInteractor;
import com.android.mybigday.alBawabaServices.MyBigDayCallbackResponse;
import com.android.mybigday.data.database.DataBaseUtil;
import com.android.mybigday.data.database.MyBigDayDbHelper;
import com.android.mybigday.data.database.MyBigDaySharedPreference;
import com.android.mybigday.data.model.Plan;
import com.android.mybigday.data.model.TipsList;
import com.android.mybigday.data.model.ToDoList;
import com.android.mybigday.myBigDayNotifications.NotificationMessageEVENT;
import com.android.mybigday.ui.activity.ChangeToolBarInterface;
import com.android.mybigday.ui.activity.MainActivity;
import com.android.mybigday.ui.activity.MainActivityViewModel;
import com.android.mybigday.ui.fragment.firstFragment.FirstFragment;
import com.android.mybigday.ui.fragment.firstFragment.FirstFragmentViewModel;
import com.android.mybigday.util.Log;
import com.android.mybigday.util.interactor.Interactor;
import com.android.mybigday.util.interactor.InteractorListener;
import com.android.mybigday.util.interactor.Result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import nucleus.presenter.Presenter;

/**
 * Created by Mariam on 11/20/2017.
 */

public class FirstFragmentPresenter extends Presenter<FirstFragment> implements InteractorListener {

    private FirstFragmentViewModel viewModel;
    public ChangeToolBarInterface toolBarInterface;
    private MyBigDaySharedPreference sharedPreference;
    private long startTime = 0L;

    private Handler customHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
        if (viewModel == null) {
            viewModel = new FirstFragmentViewModel();
        }


    }

    @Override
    protected void onTakeView(final FirstFragment fragment) {
        super.onTakeView(fragment);
        try {
            toolBarInterface = (ChangeToolBarInterface) fragment.getActivity();
        }catch (ClassCastException e) {
            throw new ClassCastException(this.toString()
                    + " must implement ChangeToolBarInterface");
        }



        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                MyBigDayDbHelper db = MyBigDayDbHelper.GetFor(MyBigDayApplication.getAppContext());
                db.open();
                ArrayList<Plan> plans = DataBaseUtil.getPlans(db);
                db.close();
                if (plans != null)
                {
                    viewModel.setNotifyPlansChanged(true);
                    viewModel.setPlans(plans);
                    fragment.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fragment.updateView(viewModel);
                        }
                    });

                }

            }

        });
        executor.shutdown();

        getTips();
        getTodos();


        sharedPreference = new MyBigDaySharedPreference(fragment.getActivity());
        viewModel.setCoverPhoto(sharedPreference.getCover());


     fragment.updateView(viewModel);
        startHandler();

    }

    public void getTodos() {
        GetToDosInteractor interactor = new GetToDosInteractor(this, getView().getActivity(), new MyBigDayCallbackResponse() {
            @Override
            public void onSuccess(Result result) {
                Log.i("success", result.getData().toString());
                if (result.getEvent() == NotificationMessageEVENT.GET_TODOS) {
                    Map<String, Object> data = (Map<String, Object>) result.getData();
                    viewModel.setLoadTodos(false);
                    viewModel.setErrorTodos("");
                    viewModel.setNotifyTodosChanged(true);
                    viewModel.setTodosListViewFromDataModel((List<ToDoList>) data.get("data"));
                    if (getView() != null)
                        getView().updateView(viewModel);
                }
            }

            @Override
            public void onError(Result result) {
                if (result.getEvent() == NotificationMessageEVENT.GET_TODOS) {
                    Map<String, Object> data = (Map<String, Object>) result.getData();
                    viewModel.setErrorTodos((String) data.get("data"));
                    viewModel.setLoadTodos(false);
                    if (getView() != null)
                        getView().updateView(viewModel);
                }
            }
        });

        interactor.execute();
    }

    public void getTips() {
        GetTipsInteractor interactor = new GetTipsInteractor(this, getView().getActivity(), new MyBigDayCallbackResponse() {
            @Override
            public void onSuccess(Result result) {
                Log.i("success", result.getData().toString());
                if (result.getEvent() == NotificationMessageEVENT.GET_TIPS) {
                    Map<String, Object> data = (Map<String, Object>) result.getData();
                    viewModel.setErrorTips("");
                    viewModel.setLoadTips(false);
                    viewModel.setNotifyTipsChanged(true);
                    viewModel.setTipsListViewFromDataModel((ArrayList<TipsList>) data.get("data"));
                    if (getView() != null)
                        getView().updateView(viewModel);
                }
            }

            @Override
            public void onError(Result result) {
                if (result.getEvent() == NotificationMessageEVENT.GET_TIPS) {
                    Map<String, Object> data = (Map<String, Object>) result.getData();
                    viewModel.setErrorTips((String) data.get("data"));
                    viewModel.setLoadTips(false);
                    if (getView() != null)
                        getView().updateView(viewModel);
                }
            }
        });

        interactor.execute();
    }

    @Override
    public void dropView() {
        super.dropView();
        if (customHandler!= null) {
            customHandler.removeCallbacks(updateTimerThread);
            Log.i("here","here");
        }
    }


    @Override
    public void onTaskStarted(Interactor interactor) {

    }

    @Override
    public void onTaskFinished(Interactor interactor, Result result) {

    }

    @Override
    public void onTaskProgress(Interactor interactor, int progress) {

    }

    @Override
    public void onTaskCanceled(Interactor interactor) {

    }

    public void setNotifyPlansChanged(boolean notifyPlansChanged) {
        viewModel.setNotifyPlansChanged(false);
    }
    public void goToSecondFragment(){
        toolBarInterface.screenNumber(2);
    }

    public void setNotifyTipsChanged(boolean notifyTipsChanged) {
        viewModel.setNotifyTipsChanged(notifyTipsChanged);
    }

    public void setNotifyTodosChanged(boolean notifyTodosChanged) {
        viewModel.setNotifyTodosChanged(notifyTodosChanged);
    }

    public void setLoadTips(boolean loadTips) {
        viewModel.setLoadTips(loadTips);
    }

    public void setLoadTodos(boolean loadTodos) {
        viewModel.setLoadTodos(loadTodos);
    }



    public void startHandler() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df1 = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
        if (sharedPreference.getDate().equals("00/00/00 00:00:00")){
            viewModel.setDays("00");
            viewModel.setHours("00");
            viewModel.setMins("00");
            viewModel.setSecs("00");

            if (customHandler!= null)
                customHandler.removeCallbacks(updateTimerThread);
        }else {
            Log.i("dates" , sharedPreference.getDate() + "  "+date);
            startTime = getView().calculateTime(sharedPreference.getDate() ,df1.format(date) );
            if (customHandler == null)
                customHandler = new Handler();

            customHandler.postDelayed(updateTimerThread, 1000);
        }

    }


    private Runnable updateTimerThread = new Runnable() {

        public void run() {


            startTime = startTime - 1000 ;

            int days = (int) (startTime / (1000*60*60*24));
            int hours = (int) ((startTime - (1000*60*60*24* days)) / (1000*60*60));
            int min = (int) ((startTime - (1000*60*60*24* days) -  (1000*60*60*hours)) / (1000*60));
            int sec = (int) ((startTime - (1000*60*60*24* days)-  (1000*60*60*hours)-(1000*60*min)) / (1000));

            if (days<0) days = days*-1;
            if (hours<0) hours = hours*-1;
            if (min<0) min = min*-1;
            if (sec<0) sec = sec*-1;
            Log.i("startTime1",  startTime
                    +"  " +days+" "+  hours  + ":" + min + ":" + sec);

            viewModel.setDays(""+ (days<10 ? "0"+days :  days));
            viewModel.setHours(""+ (hours<10 ? "0"+hours :  hours));
            viewModel.setMins(""+ (min<10 ? "0"+min :  min));
            viewModel.setSecs(""+ (sec<10 ? "0"+sec :  sec));

            if (getView() != null)
                getView().updateView(viewModel);
            customHandler.postDelayed(this, 1000);
        }

    };

}


