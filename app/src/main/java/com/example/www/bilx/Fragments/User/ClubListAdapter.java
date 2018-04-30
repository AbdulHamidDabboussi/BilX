package com.example.www.bilx.Fragments.User;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.www.bilx.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ClubListAdapter extends RecyclerView.Adapter<ClubListAdapter.MyViewHolder> {

    private List<ClubListObject> list;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView icon;
        public TextView content;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.clubTitle);
            icon = (ImageView) view.findViewById(R.id.clubIcon);
            content = (TextView) view.findViewById(R.id.content);
        }
    }


    public ClubListAdapter(List<ClubListObject> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_club_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ClubListObject s = list.get(position);
        holder.title.setText(s.getSentBy());
        holder.content.setText(s.getSubject());
        Picasso.get().load(s.getClubIcon()).into(holder.icon);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
