package com.android.mybigday.ui.activity;

/**
 * Created by Mariam on 11/20/2017.
 */

public class MainActivityViewModel {
    private boolean firstScreen = true;
    private String error = "";


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isFirstScreen() {
        return firstScreen;
    }

    public void setFirstScreen(boolean firstScreen) {
        this.firstScreen = firstScreen;
    }
}
