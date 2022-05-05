package com.example.healthapp.ui.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthapp.R;
import com.example.healthapp.data.entities.UserGulcose;
import com.example.healthapp.databinding.LiInfoFragmentBinding;
import com.example.healthapp.ui.viewmodels.InfoViewModel;
import com.example.healthapp.ui.viewmodels.LoginViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.datepicker.MaterialStyledDatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InfoFragment extends Fragment {

    LiInfoFragmentBinding liInfoFragmentBinding;
    InfoViewModel infoViewModel;
    MaterialDatePicker<Long> datePicker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        liInfoFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.li_info_fragment, container, false);
        infoViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(InfoViewModel.class);

        return liInfoFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        liInfoFragmentBinding.datePickerTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datePicker = MaterialDatePicker
                        .Builder
                        .datePicker()
                        .setTitleText("Select date")
                        .build();

                datePicker.show(getChildFragmentManager(), "DATE_PICKER");

                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override public void onPositiveButtonClick(Long selection) {
                        // parse the value from millis to formatted date, you can use ZonedDateTime

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        String date = sdf.format(selection);
                        liInfoFragmentBinding.datePickerTxt.setText(date);
                    }
                });
            }
        });

        liInfoFragmentBinding.submitInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPrequisite()){
                    SharedPreferences sharedpreferences = getActivity().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
                    int user_id = Integer.parseInt(sharedpreferences.getString("user_id", "-1"));
                    long result = infoViewModel.insertGulcoseData(new UserGulcose(user_id, liInfoFragmentBinding.datePickerTxt.getText().toString(),
                            Double.parseDouble(liInfoFragmentBinding.gulcoseLvlBreakfastTxt.getText().toString()),
                            Double.parseDouble(liInfoFragmentBinding.gulcoseLvlLunchTxt.getText().toString()),
                            Double.parseDouble(liInfoFragmentBinding.gulcoseLvlDinnerTxt.getText().toString()),
                            Double.parseDouble(liInfoFragmentBinding.gulcoseLvlBreakfastTxtB.getText().toString()),
                            Double.parseDouble(liInfoFragmentBinding.gulcoseLvlLunchTxtB.getText().toString()),
                            Double.parseDouble(liInfoFragmentBinding.gulcoseLvlDinnerTxtB.getText().toString()),
                            Integer.parseInt(liInfoFragmentBinding.dailyFootStepsTxt.getText().toString())));

                    if(result != -1){
                        clear();
                        Toast.makeText(getContext(), "Record Inserted successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void clear(){
        liInfoFragmentBinding.gulcoseLvlBreakfastTxt.setText("");
        liInfoFragmentBinding.gulcoseLvlLunchTxt.setText("");
        liInfoFragmentBinding.gulcoseLvlDinnerTxt.setText("");
        liInfoFragmentBinding.gulcoseLvlBreakfastTxtB.setText("");
        liInfoFragmentBinding.gulcoseLvlLunchTxtB.setText("");
        liInfoFragmentBinding.gulcoseLvlDinnerTxtB.setText("");
        liInfoFragmentBinding.dailyFootStepsTxt.setText("");
    }

    public boolean isPrequisite(){
        if(liInfoFragmentBinding.datePickerTxt.getText() == null || liInfoFragmentBinding.datePickerTxt.getText().toString().equals("")){
            return false;
        }
        if(liInfoFragmentBinding.gulcoseLvlBreakfastTxt.getText() == null || liInfoFragmentBinding.gulcoseLvlBreakfastTxt.getText().toString().equals("")){
            return false;
        }
        if(liInfoFragmentBinding.gulcoseLvlLunchTxt.getText() == null || liInfoFragmentBinding.gulcoseLvlLunchTxt.getText().toString().equals("")){
            return false;
        }

        if(liInfoFragmentBinding.gulcoseLvlDinnerTxt.getText() == null || liInfoFragmentBinding.gulcoseLvlDinnerTxt.getText().toString().equals("")){
            return false;
        }
        return true;
    }
}
