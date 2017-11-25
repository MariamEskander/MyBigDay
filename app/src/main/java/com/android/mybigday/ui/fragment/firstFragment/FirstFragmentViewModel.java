package com.android.mybigday.ui.fragment.firstFragment;

import com.android.mybigday.data.model.Plan;
import com.android.mybigday.data.model.TipsList;
import com.android.mybigday.data.model.TipsListView;
import com.android.mybigday.data.model.ToDoList;
import com.android.mybigday.data.model.ToDoListView;
import com.android.mybigday.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mariam on 11/20/2017.
 */

public class FirstFragmentViewModel {
    private String days = "", hours = "", mins = "", secs = "" , coverPhoto = "";
    private ArrayList<TipsListView> tipsListView = new ArrayList<>();
    private boolean notifyTipsChanged = false;
    private ArrayList<ToDoListView> toDoListView = new ArrayList<>();
    private boolean notifyTodosChanged = false;
    private String error = "" , errorTips = "" , errorTodos="";
    private boolean loadTips = true , loadTodos = true;
    private ArrayList<Plan> plans = new ArrayList<>();
    private boolean notifyPlansChanged = false;

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getMins() {
        return mins;
    }

    public void setMins(String mins) {
        this.mins = mins;
    }

    public String getSecs() {
        return secs;
    }

    public void setSecs(String secs) {
        this.secs = secs;
    }

    public boolean isNotifyTipsChanged() {
        return notifyTipsChanged;
    }

    public void setNotifyTipsChanged(boolean notifyTipsChanged) {
        this.notifyTipsChanged = notifyTipsChanged;
    }

    public boolean isNotifyTodosChanged() {
        return notifyTodosChanged;
    }

    public void setNotifyTodosChanged(boolean notifyTodosChanged) {
        this.notifyTodosChanged = notifyTodosChanged;
    }

    public ArrayList<TipsListView> getTipsListView() {
        return tipsListView;
    }


    public ArrayList<ToDoListView> getToDoListView() {
        return toDoListView;
    }

    public void setTipsListViewFromDataModel(ArrayList<TipsList> tipsList) {
        tipsListView.clear();
        Log.i("data", "tips size "+tipsList.size());
        for (TipsList dta : tipsList) {
            this.tipsListView.add(new TipsListView(dta.getTitle(),dta.getImage()));
        }
    }


    public void setTodosListViewFromDataModel(List<ToDoList> toDoList) {
        toDoListView.clear();
        Log.i("data", "todos size "+toDoList.size());
        for (ToDoList dta : toDoList) {
            this.toDoListView.add(new ToDoListView(dta.getId(),dta.getTitle(),dta.isCheck()));
        }
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isLoadTips() {
        return loadTips;
    }

    public void setLoadTips(boolean loadTips) {
        this.loadTips = loadTips;
    }

    public boolean isLoadTodos() {
        return loadTodos;
    }

    public void setLoadTodos(boolean loadTodos) {
        this.loadTodos = loadTodos;
    }

    public String getErrorTips() {
        return errorTips;
    }

    public void setErrorTips(String errorTips) {
        this.errorTips = errorTips;
    }

    public String getErrorTodos() {
        return errorTodos;
    }

    public void setErrorTodos(String errorTodos) {
        this.errorTodos = errorTodos;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public boolean isNotifyPlansChanged() {
        return notifyPlansChanged;
    }

    public void setNotifyPlansChanged(boolean notifyPlansChanged) {
        this.notifyPlansChanged = notifyPlansChanged;
    }

    public ArrayList<Plan> getPlans() {
        return plans;
    }

    public void setPlans(ArrayList<Plan> plans) {
        this.plans = plans;
    }
}
