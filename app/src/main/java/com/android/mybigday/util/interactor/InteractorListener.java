package com.android.mybigday.util.interactor;



public interface InteractorListener {

    public void onTaskStarted(Interactor interactor);

    public void onTaskFinished(Interactor interactor, Result result);

    public void onTaskProgress(Interactor interactor, int progress);

    public void onTaskCanceled(Interactor interactor);


}
