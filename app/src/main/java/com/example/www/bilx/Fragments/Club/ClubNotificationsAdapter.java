package com.example.www.bilx.Fragments.Club;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.www.bilx.R;

import java.util.ArrayList;
import java.util.List;

public class ClubNotificationsAdapter extends RecyclerView.Adapter<ClubNotificationsAdapter.MyViewHolder> {
    private List<ClubNotificationObject> list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.club_title);
            genre = (TextView) view.findViewById(R.id.club_genre);
            year = (TextView) view.findViewById(R.id.club_year);
        }
    }


    public ClubNotificationsAdapter(List<ClubNotificationObject> list) {
        this.list = list;
    }

    @Override
    public ClubNotificationsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_club_list, parent, false);

        return new ClubNotificationsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ClubNotificationsAdapter.MyViewHolder holder, int position) {

        ClubNotificationObject s = list.get(position);
        holder.title.setText(s.getSentBy());
        holder.genre.setText(s.getSubject());
        holder.year.setText(s.getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}