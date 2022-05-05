package com.example.healthapp.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Pair;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthapp.R;
import com.example.healthapp.data.entities.UserGulcose;
import com.example.healthapp.databinding.LiFitnessFragmentBinding;
import com.example.healthapp.ui.viewmodels.FitnessViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FitnessFragment extends Fragment {

    LiFitnessFragmentBinding liFitnessFragmentBinding;
    FitnessViewModel fitnessViewModel;
    ArrayList<IBarDataSet> dataSets = null;
    ArrayList<BarEntry> valueSet1 = new ArrayList<>();
    ArrayList<String> xAxis = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        liFitnessFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.li_fitness_fragment, container, false);
        fitnessViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(FitnessViewModel.class);
        return liFitnessFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        liFitnessFragmentBinding.datePickerTxt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                MaterialDatePicker<Pair<Long, Long>> dateRangePicker =
                        MaterialDatePicker.Builder.dateRangePicker()
                                .setTitleText("Select dates")
                                .build();

                dateRangePicker.show(getChildFragmentManager(), "DATE_PICKER");

                dateRangePicker.addOnPositiveButtonClickListener(selection -> {
                            Long startDate = selection.first;
                            Long endDate = selection.second;

                            String startDateString = DateFormat.format("dd/MM/yyyy", new Date(startDate)).toString();
                            String endDateString = DateFormat.format("dd/MM/yyyy", new Date(endDate)).toString();

                            long daysDiff = TimeUnit.MILLISECONDS.toDays(endDate - startDate);

                            if(daysDiff > 7){
                                liFitnessFragmentBinding.datePickerTxt.setText("Select Date");
                                resetChart(liFitnessFragmentBinding.chart);
                                Toast.makeText(requireContext(), "7 days can be selected at max", Toast.LENGTH_SHORT).show();
                            }else {
                                List<UserGulcose> userGulcoseList = fitnessViewModel.getWeeklySteps(startDateString, endDateString);
                                liFitnessFragmentBinding.datePickerTxt.setText("Selected Date is : " + dateRangePicker.getHeaderText());

                                if(userGulcoseList != null) {
                                    valueSet1.clear();
                                    int x = 0;

                                    getValues(startDateString, endDateString, daysDiff, userGulcoseList);

//                                    for (UserGulcose userGulcose: userGulcoseList) {
//                                        valueSet1.add(new BarEntry(userGulcose.dailySteps, x));
//                                        x++;
//                                    }

                                    BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Daily Steps");
                                    barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

                                    dataSets = new ArrayList<>();
                                    dataSets.add(barDataSet1);

                                    BarData data2 = new BarData(xAxis, dataSets);
                                    liFitnessFragmentBinding.chart.setData(data2);
                                    liFitnessFragmentBinding.chart.setDescription("");
                                    liFitnessFragmentBinding.chart.animateXY(1000, 1000);
                                    liFitnessFragmentBinding.chart.invalidate();
                                }else{
                                    resetChart(liFitnessFragmentBinding.chart);
                                }
                            }
                 });
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getValues(String startDate, String endDate, long daysDiff, List<UserGulcose> userGulcoseList) {
        List<String> dates = new ArrayList<>();

        for (UserGulcose userglucose: userGulcoseList) {
            dates.add(userglucose.date);
        }

        try {
            Date startD = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(startDate);
            Date endD = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(endDate);

            List<Date> datesBetweenStartandEnd = getDaysBetweenDates(startD, endD);
            boolean dataAdded;
            int i = 0;

            xAxis.clear();
            for (Date date: datesBetweenStartandEnd) {
                dataAdded = false;
                String dayFormat = new SimpleDateFormat("EEE").format(date.getTime());
                xAxis.add(dayFormat);

                String currentDate = DateFormat.format("dd/MM/yyyy", date).toString();
                if(dates.contains(currentDate)){
                    for (UserGulcose userglucose : userGulcoseList) {
                        if (userglucose.date.equals(currentDate)) {
                            valueSet1.add(new BarEntry(userglucose.dailySteps, i));
                            //dataAdded = true;
                            break;
                        }
                    }
                }else{
//                    if(!dataAdded) {
                        valueSet1.add(new BarEntry(0, i));
  //                  }
                }
                i++;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static List<Date> getDaysBetweenDates(Date startdate, Date enddate)
    {
        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);

        while (calendar.getTime().before(enddate))
        {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

    private ArrayList<String> getXAxisValues(String startDateString) {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

        for(int i = 0 ; i < 7 ; i++) {
            c.setTime(new Date(startDateString + i));
            @SuppressLint("SimpleDateFormat")
            String date = new SimpleDateFormat("EEE").format(c.getTime());
            xAxis.add(date);
        }
        return xAxis;
    }
    private void resetChart(BarChart barChart) {
        barChart.fitScreen();
        barChart.getBarData().clearValues();
        barChart.getXAxis().setValueFormatter(null);
        barChart.notifyDataSetChanged();
        barChart.clear();
        barChart.invalidate();
    }
}
