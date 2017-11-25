package com.android.mybigday.ui;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.android.mybigday.R;
import com.android.mybigday.data.database.MyBigDaySharedPreference;
import com.android.mybigday.ui.fragment.OnDialogDismiss;
import com.android.mybigday.util.Log;

import java.util.Calendar;

/**
 * Created by Mariam on 11/22/2017.
 */
public class DatePickerFragment extends DialogFragment {
    private int year, month, day, hour, min   ;
    private MyBigDaySharedPreference sharedPreference;

    OnDialogDismiss onDialogDismiss;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            onDialogDismiss = (OnDialogDismiss) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
       // mDate = (Date)getArguments().getSerializable(EXTRA_DATE);

        Calendar calendar = Calendar.getInstance();
       // calendar.setTime(mDate);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);


        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_datetime, null);

        DatePicker datePicker = (DatePicker)v.findViewById(R.id.datePicker);
        TimePicker timePicker = (TimePicker)v.findViewById(R.id.timePicker);
        Button button = (Button) v.findViewById(R.id.ok);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                //dd/M/yyyy hh:mm:ss
                sharedPreference = new MyBigDaySharedPreference(getContext());
                sharedPreference.saveDate(formatNumberToTwoDigits(day)+"/"+
                        formatNumberToTwoDigits(month)+"/"+
                        formatNumberToTwoDigits(year)+" "+ formatNumberToTwoDigits(hour)+":"+
                        formatNumberToTwoDigits(min)+":00");


                onDialogDismiss.onDialogDismiss();
                Log.i("daaaateee",sharedPreference.getDate());

            }
        });

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                DatePickerFragment.this.year = year;
                DatePickerFragment.this.month = month+1;
                DatePickerFragment.this.day = day;
            }
        });

        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(min);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            public void onTimeChanged(TimePicker view, int hour, int min) {
                DatePickerFragment.this.hour = hour;
                DatePickerFragment.this.min = min;
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Save The Date")
                .create();
    }

    public static String formatNumberToTwoDigits(int number) {
        return (number < 10 ? "0" : "") + number;
    }


}