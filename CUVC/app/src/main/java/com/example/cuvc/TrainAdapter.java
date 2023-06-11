package com.example.cuvc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TrainAdapter extends RecyclerView.Adapter<TrainAdapter.TrainViewHolder> {

    private List<TrainSchedule> trainSchedules;

    public TrainAdapter(List<TrainSchedule> trainSchedules) {
        this.trainSchedules = trainSchedules;
    }

    @NonNull
    @Override
    public TrainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_train, parent, false);
        return new TrainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainViewHolder holder, int position) {
        TrainSchedule trainSchedule = trainSchedules.get(position);
        holder.trainNameTextView.setText(trainSchedule.getName());
        holder.trainScheduleTextView.setText(trainSchedule.getFromCity());
    }

    @Override
    public int getItemCount() {
        return trainSchedules.size();
    }

    public static class TrainViewHolder extends RecyclerView.ViewHolder {

        public TextView trainNameTextView;
        public TextView trainScheduleTextView;

        public TrainViewHolder(@NonNull View itemView) {
            super(itemView);
            trainNameTextView = itemView.findViewById(R.id.trainNameTextView);
            trainScheduleTextView = itemView.findViewById(R.id.trainScheduleTextView);
        }
    }
}
