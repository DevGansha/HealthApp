package com.example.healthapp.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthapp.R;
import com.example.healthapp.data.entities.UserGulcose;
import com.example.healthapp.databinding.LiGulcoseLevelsBinding;
import com.example.healthapp.ui.viewmodels.GulcoseLevelViewModel;
import com.example.healthapp.ui.viewmodels.InfoViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class GulcoseLevelsFragment extends Fragment {

    LiGulcoseLevelsBinding liGulcoseLevelsBinding;
    GulcoseLevelViewModel gulcoseLevelViewModel;
    ArrayList<IBarDataSet> dataSets = null;
    MaterialDatePicker<Long> datePicker;
    ArrayList<BarEntry> valueSet1 = new ArrayList<>();
    ArrayList<BarEntry> valueSet2 = new ArrayList<>();
    ArrayList<BarEntry> valueSet3 = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        liGulcoseLevelsBinding = DataBindingUtil.inflate(inflater, R.layout.li_gulcose_levels, container, false);
        gulcoseLevelViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(GulcoseLevelViewModel.class);

        BarEntry v1e1 = new BarEntry(10.0f, 0); // Jan
        valueSet3.add(v1e1);

        BarEntry v1e2 = new BarEntry(10.0f, 1); // Jan
        valueSet3.add(v1e2);

        BarEntry v1e3 = new BarEntry(10.0f, 2); // Jan
        valueSet3.add(v1e3);

        return liGulcoseLevelsBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        liGulcoseLevelsBinding.datePickerTxt.setOnClickListener(new View.OnClickListener() {
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
                        liGulcoseLevelsBinding.datePickerTxt.setText(date);

                        UserGulcose data = gulcoseLevelViewModel.getGulcoseDataByDate(date);

                        if(data != null) {
                            valueSet1.clear();
                            valueSet2.clear();

                            BarEntry v1e1 = new BarEntry(data.gulcose_breakfast.floatValue(), 0); // Jan
                            valueSet1.add(v1e1);

                            BarEntry v1e2 = new BarEntry(data.gulcose_lunch.floatValue(), 1); // Jan
                            valueSet1.add(v1e2);

                            BarEntry v1e3 = new BarEntry(data.gulcose_dinner.floatValue(), 2); // Jan
                            valueSet1.add(v1e3);

                            BarEntry v2e1 = new BarEntry(data.gulcose_breakfast_b.floatValue(), 0); // Jan
                            valueSet2.add(v2e1);

                            BarEntry v2e2 = new BarEntry(data.gulcose_lunch_b.floatValue(), 1); // Jan
                            valueSet2.add(v2e2);

                            BarEntry v2e3 = new BarEntry(data.gulcose_dinner_b.floatValue(), 2); // Jan
                            valueSet2.add(v2e3);

                            BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Before");
                            barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

                            BarDataSet barDataSet2 = new BarDataSet(valueSet2, "After");
                            barDataSet2.setColor(Color.rgb(211, 155, 121));

                            BarDataSet barDataSet3 = new BarDataSet(valueSet3, "Recommended Levels");
                            barDataSet3.setColor(Color.rgb(0, 155, 0));

                            dataSets = new ArrayList<>();
                            dataSets.add(barDataSet1);
                            dataSets.add(barDataSet2);
                            dataSets.add(barDataSet3);

                            BarData data2 = new BarData(getXAxisValues(), dataSets);
                            liGulcoseLevelsBinding.chart.setData(data2);
                            liGulcoseLevelsBinding.chart.setDescription("");
                            liGulcoseLevelsBinding.chart.animateXY(1000, 1000);
                            liGulcoseLevelsBinding.chart.invalidate();
                        }else{
                            resetChart(liGulcoseLevelsBinding.chart);
                        }
                    }
                });
            }
        });

    }

    private ArrayList getXAxisValues() {
        ArrayList xAxis = new ArrayList();
        xAxis.add("BREAKFAST");
        xAxis.add("LUNCH");
        xAxis.add("DINNER");
        return xAxis;
    }
    private void resetChart(BarChart barChart) {
        if(barChart.getBarData() != null) {
            barChart.fitScreen();
            barChart.getBarData().clearValues();
            barChart.getXAxis().setValueFormatter(null);
            barChart.notifyDataSetChanged();
            barChart.clear();
            barChart.invalidate();
        }
    }
}

