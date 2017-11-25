package com.android.mybigday.util.interactor;

import android.content.Context;
import android.os.AsyncTask;


public abstract class Interactor extends AsyncTask<Void, Integer, Result> {

    protected InteractorListener listener;
    protected Context context;

    protected abstract Result onTaskWork();

    public Interactor(InteractorListener listener, Context context) {

        this.listener = listener;
        this.context = context;
    }
    public Interactor(InteractorListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        if (listener != null)
            listener.onTaskStarted(this);
    }

    @Override
    protected Result doInBackground(Void... params) {

        return onTaskWork();
    }

    @Override
    protected void onPostExecute(Result result) {

        super.onPostExecute(result);

        if (listener != null)
            listener.onTaskFinished(this, result);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

        if (listener != null)
            listener.onTaskProgress(this, values[0]);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();

        if (listener != null)
            listener.onTaskCanceled(this);
    }

    public void updateView(int progress) {

        publishProgress(progress);
    }

    public Result executeSync() {

        return onTaskWork();
    }

}