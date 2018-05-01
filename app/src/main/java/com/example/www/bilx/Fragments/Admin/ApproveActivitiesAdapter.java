package com.example.www.bilx.Fragments.Admin;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.www.bilx.R;

import java.util.ArrayList;
import java.util.List;

public class ApproveActivitiesAdapter extends RecyclerView.Adapter<ApproveActivitiesAdapter.MyViewHolder> {
    private List<ApproveActivitiesObject> list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView activityName, ge, time,date, location, language, description, clubName;

        public MyViewHolder(View view) {
            super(view);
            activityName = (TextView) view.findViewById(R.id.approve_actName);
            ge = (TextView) view.findViewById(R.id.approve_ge);
            time = (TextView) view.findViewById(R.id.approve_time);
            date = (TextView) view.findViewById(R.id.approve_date);
            location = (TextView) view.findViewById(R.id.approve_loc);
            language = (TextView) view.findViewById(R.id.approve_lang);
            description = (TextView) view.findViewById(R.id.approve_desc);
            clubName = (TextView) view.findViewById(R.id.approve_clubName);
        }
    }


    public ApproveActivitiesAdapter(List<ApproveActivitiesObject> list) {
        this.list = list;
    }

    @Override
    public ApproveActivitiesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.approve_activities_list, parent, false);

        return new ApproveActivitiesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ApproveActivitiesAdapter.MyViewHolder holder, int position) {

        ApproveActivitiesObject s = list.get(position);
        holder.activityName.setText(s.getActivityName());
        holder.ge.setText(s.getGe());
        holder.time.setText(s.getTime());
        holder.date.setText(s.getDate());
        holder.location.setText(s.getLocation());
        holder.language.setText(s.getLanguage());
        holder.description.setText(s.getDescription());
        holder.clubName.setText(s.getClubName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeAdapter(int i){
        list.remove(i);
    }
}
