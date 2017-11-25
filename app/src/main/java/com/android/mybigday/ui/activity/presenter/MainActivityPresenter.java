package com.android.mybigday.ui.activity.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.android.mybigday.R;
import com.android.mybigday.ui.activity.MainActivity;
import com.android.mybigday.ui.activity.MainActivityViewModel;
import com.android.mybigday.ui.fragment.firstFragment.FirstFragment;
import com.android.mybigday.ui.fragment.secondFragment.SecondFragment;
import com.android.mybigday.util.interactor.Interactor;
import com.android.mybigday.util.interactor.InteractorListener;
import com.android.mybigday.util.interactor.Result;

import nucleus.presenter.Presenter;

/**
 * Created by Mariam on 11/20/2017.
 */

public class MainActivityPresenter extends Presenter<MainActivity> implements InteractorListener {

    private MainActivityViewModel viewModel;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
        if (viewModel == null) {
            viewModel = new MainActivityViewModel();
        }

    }

    @Override
    protected void onTakeView(MainActivity activity) {
        super.onTakeView(activity);
        activity.updateView(viewModel);

    }

    public void showFirstFragment(MainActivity activity) {
        FirstFragment firstFragment = new FirstFragment();
        try {
            ft = activity.getSupportFragmentManager().beginTransaction();
            if (activity.getSupportFragmentManager().findFragmentById(R.id.frame_layout) == null) {
                ft.add(R.id.frame_layout, firstFragment);
            } else {
                ft.replace(R.id.frame_layout, firstFragment);
            }
            ft.addToBackStack(null);
            ft.commit();
        } catch (Exception e) {
            viewModel.setError("An error occurred, Tey again later! ");
            activity.updateView(viewModel);
        }

    }

    public void showSecondFragment(MainActivity activity) {
        SecondFragment secondFragment = new SecondFragment();
        try {
            ft = activity.getSupportFragmentManager().beginTransaction();
            if (activity.getSupportFragmentManager().findFragmentById(R.id.frame_layout) == null) {
                ft.add(R.id.frame_layout, secondFragment);
            } else {
                ft.replace(R.id.frame_layout, secondFragment);
            }
            ft.addToBackStack(null);
            ft.commit();
        } catch (Exception e) {
            viewModel.setError("An error occurred, Tey again later! ");
            activity.updateView(viewModel);
        }

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

    public void setFirstScreen(boolean firstOpen) {
        viewModel.setFirstScreen(firstOpen);
        if (getView() != null)
            getView().updateView(viewModel);
    }
    public void setError(String error) {
        viewModel.setError(error);
    }
}


