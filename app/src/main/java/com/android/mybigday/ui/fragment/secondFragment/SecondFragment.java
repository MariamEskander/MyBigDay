package com.android.mybigday.ui.fragment.secondFragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;


import com.android.mybigday.R;
import com.android.mybigday.ui.OnPassData;
import com.android.mybigday.ui.fragment.secondFragment.presenter.SecondFragmentPresenter;
import com.android.mybigday.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;

import nucleus.view.NucleusSupportFragment;

@RequiresPresenter(SecondFragmentPresenter.class)
public class SecondFragment extends NucleusSupportFragment<SecondFragmentPresenter> {


    @BindView(R.id.text)
    TextView text ;


    @BindView(R.id.cost)
    EditText cost ;

    @BindView(R.id.radio1)
    CheckBox radioButton1;

    @BindView(R.id.radio2)
    CheckBox radioButton2;

    @BindView(R.id.seekBar)
    SeekBar seekBar;

    OnPassData onPassData;
    boolean check1  = false, check2 = false;


    @BindView(R.id.autoCompleteTextView1)
    AutoCompleteTextView autoCompleteTextView1;

    String plan = "" , cst="", guest = "100";


    // newInstance constructor for creating fragment with arguments
    public static SecondFragment newInstance(int page, String title) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onPassData = (OnPassData) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.second_fragment, container, false);
        ButterKnife.bind(this, view);



        final String[] countries = getResources().getStringArray(R.array.government_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,countries);
        autoCompleteTextView1.setAdapter(adapter);


        seekBar.setProgress(100);
        final int yourStep = 50;
        seekBar.setMax(1000);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                progress = ((int)Math.round(progress/yourStep ))*yourStep;
                seekBar.setProgress(progress);
                String what_to_say = String.valueOf(progress);
                text.setText(what_to_say);
                int seek_label_pos = (((seekBar.getRight() - seekBar.getLeft()) * seekBar.getProgress()) / seekBar.getMax()) + seekBar.getLeft();
                text.setX(seek_label_pos);
                guest =what_to_say;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        radioButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              check1 = isChecked;
            }
        });

        radioButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                check2 = isChecked;
            }
        });

        cost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                cst = String.valueOf(s);
            }
        });

        return view;
    }

    private void showMessage(String message) {
        final Dialog dialog = new Dialog(getActivity());
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

    public void passData() {
        plan = autoCompleteTextView1.getText().toString();
        Log.i("all data" , plan +"  "+ check1 +"  "+cst +"  "+check2 +"  "+guest );
        if (plan.equals("") &&check1) plan = "0";
        if (cst.equals("") &&check2){ cst = "0";
            onPassData.onDataPass(plan, guest, cst);
        }else if (!cst.equals("") &&Double.parseDouble(cst) >10000&& Double.parseDouble(cst)<100000000){
            onPassData.onDataPass(plan, guest, cst);
        }else if (!check2){
            showMessage("Cost must be between 10,000 and 100,000,000");
        }

    }
}
