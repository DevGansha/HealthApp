package com.example.healthapp.ui.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.healthapp.R;
import com.example.healthapp.data.entities.UserGulcose;

import java.util.List;

public class GlucoseHistoryAdapter extends RecyclerView.Adapter<GlucoseHistoryAdapter.ViewHolder> {

    private List<UserGulcose> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    GlucoseHistoryAdapter(Context context, List<UserGulcose> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.li_glucose_history, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserGulcose userGulcose = mData.get(position);
        holder.date.setText(userGulcose.date);
        holder.gulcoseLvlBreakfast.setText("Breakfast -> Before: " + userGulcose.gulcose_breakfast_b + ", After" + userGulcose.gulcose_breakfast);
        holder.gulcoseLvllunch.setText("Lunch -> Before: " + userGulcose.gulcose_lunch_b + ", After" + userGulcose.gulcose_lunch);
        holder.gulcoseLvldinner.setText("Dinner -> Before: " + userGulcose.gulcose_dinner_b + ", After" + userGulcose.gulcose_dinner);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, gulcoseLvlBreakfast, gulcoseLvllunch, gulcoseLvldinner;


        ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            gulcoseLvlBreakfast = itemView.findViewById(R.id.gulcoseLvlBreakfast);
            gulcoseLvllunch = itemView.findViewById(R.id.gulcoseLvllunch);
            gulcoseLvldinner = itemView.findViewById(R.id.gulcoseLvldinner);
        }

    }
}