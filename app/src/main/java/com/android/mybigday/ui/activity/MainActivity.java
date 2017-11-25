package com.android.mybigday.ui.activity;

import android.app.Dialog;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mybigday.MyBigDayApplication;
import com.android.mybigday.R;
import com.android.mybigday.data.database.DataBaseUtil;
import com.android.mybigday.data.database.MyBigDayDbHelper;
import com.android.mybigday.data.model.Plan;
import com.android.mybigday.ui.OnPassData;
import com.android.mybigday.ui.activity.presenter.MainActivityPresenter;
import com.android.mybigday.ui.fragment.OnDialogDismiss;
import com.android.mybigday.ui.fragment.firstFragment.FirstFragment;
import com.android.mybigday.ui.fragment.secondFragment.SecondFragment;
import com.android.mybigday.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

@RequiresPresenter(MainActivityPresenter.class)
public class MainActivity extends NucleusAppCompatActivity<MainActivityPresenter> implements ChangeToolBarInterface , OnPassData,OnDialogDismiss {

    @BindView(R.id.textView)
    TextView textView1;

    @BindView(R.id.imageView1)
    ImageView imageView1;

    @BindView(R.id.imageView2)
    ImageView imageView2;

    @BindView(R.id.textView2)
    TextView textView2;


    String plan ="", guest ="" , cost ="";
    char[] name  = new char[26];

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        for(int i = 'A'; i <= 'Z'; i++) {
            name[(i - 'A')] = (char) i;
        }

    }



    private void showMessage(String message) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.message);
        TextView success_body = dialog.findViewById(R.id.message);
        success_body.setText(message);

        TextView dialogButton = dialog.findViewById(R.id.btn_ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    public void updateView(MainActivityViewModel viewModel) {
        if (viewModel.isFirstScreen()){
           firstScreenIsOpen();
           getPresenter().showFirstFragment(this);
        }else {
           secondScreenIsOpen();
            getPresenter().showSecondFragment(this);
        }
        if (!viewModel.getError().equals("")){
            showMessage(viewModel.getError());
            getPresenter().setError("");
        }
    }

    private void firstScreenIsOpen() {
        imageView1.setVisibility(View.VISIBLE);
        imageView1.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.icon));
        imageView1.setOnClickListener(null);

        imageView2.setVisibility(View.VISIBLE);
        textView1.setVisibility(View.GONE);
        textView2.setVisibility(View.GONE);
    }

    private void secondScreenIsOpen() {
        imageView1.setVisibility(View.VISIBLE);
        imageView1.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.back));
        imageView1.setOnClickListener(new GetMeBack());


        imageView2.setVisibility(View.GONE);
        textView1.setVisibility(View.VISIBLE);

        textView2.setVisibility(View.VISIBLE);
        textView2.setOnClickListener(new GoNext());
    }



    @Override
    public void screenNumber(int i) {
        if (i == 1){
            getPresenter().setFirstScreen(true);
        }else {
            getPresenter().setFirstScreen(false);
        }
    }

    @Override
    public void onDataPass(String p, String g, String c) {
        plan = p ;
        guest = g;
        cost = c;


    }

    @Override
    public void onDialogDismiss() {
        FirstFragment fragment = (FirstFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout);
        fragment.startHandler();
    }


    private class GetMeBack implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            screenNumber(1);
        }
    }

    private class GoNext implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            SecondFragment fragment = (SecondFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout);
            fragment.passData();



            if (plan.equals("") || guest.equals("")|| cost.equals("")) {
               showMessage("Please fill all fields to add plan");
           }else {

               ExecutorService executor = Executors.newFixedThreadPool(5);
               executor.execute(new Runnable() {
                   @Override
                   public void run() {
                       MyBigDayDbHelper db = MyBigDayDbHelper.GetFor(MyBigDayApplication.getAppContext());
                       db.open();
                       ArrayList<Plan> plans = DataBaseUtil.getPlans(db);
                       if (plans != null)
                       DataBaseUtil.insertOrUpdatePlan(db,new Plan("Plan "+name[plans.size()] ,plan,guest,cost));
                       else
                           DataBaseUtil.insertOrUpdatePlan(db,new Plan("Plan "+name[0],plan,guest,cost));
                       db.close();
                   }

               });
               executor.shutdown();

                screenNumber(1);
           }
        }
    }
}


