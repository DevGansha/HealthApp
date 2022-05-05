package com.example.healthapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthapp.R;
import com.example.healthapp.data.entities.UserGulcose;
import com.example.healthapp.databinding.FrGulcoseHistoryBinding;
import com.example.healthapp.ui.viewmodels.GulcoseHistoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class GulcoseHistoryFragment extends Fragment {

    FrGulcoseHistoryBinding frGulcoseHistoryBinding;
    GulcoseHistoryViewModel gulcoseHistoryViewModel;
    List<UserGulcose> glucoseLevels= new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        gulcoseHistoryViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(GulcoseHistoryViewModel.class);
        frGulcoseHistoryBinding = DataBindingUtil.inflate(inflater, R.layout.fr_gulcose_history, container, false);
        return frGulcoseHistoryBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] Countries = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

        ArrayAdapter adapter = new ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line,Countries);
        frGulcoseHistoryBinding.spinnerValues.setAdapter(adapter);

        frGulcoseHistoryBinding.spinnerValues.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(requireContext(), i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        frGulcoseHistoryBinding.spinnerValues.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int month = 0;

                switch (i){
                    case 0:
                        month = 1;
                        break;
                    case 1:
                        month = 2;
                        break;
                    case 2:
                        month = 3;
                        break;
                    case 3:
                        month = 4;
                        break;
                    case 4:
                        month = 5;
                        break;
                    case 5:
                        month = 6;
                        break;
                    case 6:
                        month = 7;
                        break;
                    case 7:
                        month = 8;
                        break;
                    case 8:
                        month = 9;
                        break;
                    case 9:
                        month = 10;
                        break;
                    case 10:
                        month = 11;
                        break;
                    case 11:
                        month = 12;
                        break;
                }

                glucoseLevels.clear();
                if(month >= 1 && month <= 7) {
                    glucoseLevels = gulcoseHistoryViewModel.getGulcoseDataByMonths("0" + String.valueOf(month));
                }else{
                    glucoseLevels = gulcoseHistoryViewModel.getGulcoseDataByMonths(String.valueOf(month));
                }

                GlucoseHistoryAdapter glucoseHistoryAdapter = new GlucoseHistoryAdapter(requireContext(), glucoseLevels);
                frGulcoseHistoryBinding.historyRv.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
                frGulcoseHistoryBinding.historyRv.setAdapter(glucoseHistoryAdapter);

                Toast.makeText(requireContext(), adapterView.getAdapter().getItem(i).toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
