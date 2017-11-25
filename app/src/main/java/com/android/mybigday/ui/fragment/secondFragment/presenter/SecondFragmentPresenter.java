package com.android.mybigday.ui.fragment.secondFragment.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.mybigday.MyBigDayApplication;
import com.android.mybigday.data.database.DataBaseUtil;
import com.android.mybigday.data.database.MyBigDayDbHelper;
import com.android.mybigday.data.model.Plan;
import com.android.mybigday.ui.activity.ChangeToolBarInterface;
import com.android.mybigday.ui.fragment.firstFragment.FirstFragment;
import com.android.mybigday.ui.fragment.firstFragment.FirstFragmentViewModel;
import com.android.mybigday.ui.fragment.secondFragment.SecondFragment;
import com.android.mybigday.util.GlobalVariables;
import com.android.mybigday.util.interactor.Interactor;
import com.android.mybigday.util.interactor.InteractorListener;
import com.android.mybigday.util.interactor.Result;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import nucleus.presenter.Presenter;

/**
 * Created by Mariam on 11/20/2017.
 */

public class SecondFragmentPresenter extends Presenter<SecondFragment> implements InteractorListener {

    private FirstFragmentViewModel viewModel;

    

    @Override
    protected void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
        if (viewModel == null) {
            viewModel = new FirstFragmentViewModel();
        }

    }

    @Override
    protected void onTakeView(SecondFragment fragment) {
        super.onTakeView(fragment);



    }

    @Override
    public void dropView() {
        super.dropView();

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

}


