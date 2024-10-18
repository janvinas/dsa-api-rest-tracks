package com.janvinas.ApiRestTracks;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private List<Track> values;

    private MainActivity activityInstance;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView trackName;
        public TextView trackSinger;
        public String trackId;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            trackName = v.findViewById(R.id.firstLine);
            trackSinger = v.findViewById(R.id.secondLine);
        }
    }

    public void setActivityInstance(MainActivity activityInstance){
        this.activityInstance = activityInstance;
    }

    public void add(int position, Track item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerViewAdapter(List<Track> myDataset) {
        values = myDataset;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.trackName.setText(values.get(i).title);
        viewHolder.trackSinger.setText(values.get(i).singer);
        viewHolder.trackId = values.get(i).id;

        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = viewHolder.getAdapterPosition();
                Intent intent = new Intent(activityInstance, SongViewActivity.class);
                intent.putExtra("id", values.get(pos).id);
                intent.putExtra("title", values.get(pos).title);
                intent.putExtra("singer", values.get(pos).singer);
                activityInstance.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}
