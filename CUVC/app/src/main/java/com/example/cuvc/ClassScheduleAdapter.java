package com.example.cuvc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ClassScheduleAdapter extends RecyclerView.Adapter<ClassScheduleAdapter.ViewHolder> {

    private List<ClassScheduleItem> mClassScheduleItems;



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mCourseName;
        public TextView mStartTime;
        public TextView mEndTime;
        public TextView mTeacher;

        public ViewHolder(View itemView) {
            super(itemView);
            mCourseName = (TextView) itemView.findViewById(R.id.courseNameTextView);
            mStartTime = (TextView) itemView.findViewById(R.id.startTimeTextView);
            mEndTime = (TextView) itemView.findViewById(R.id.endTimeTextView);
            mTeacher = (TextView) itemView.findViewById(R.id.teacherTextView);
        }
    }




    public ClassScheduleAdapter(List<ClassScheduleItem> classScheduleItems) {
        mClassScheduleItems = classScheduleItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View classScheduleView = inflater.inflate(R.layout.class_schedule_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(classScheduleView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        ClassScheduleItem classScheduleItem = mClassScheduleItems.get(position);

        // Set item views based on your views and data model
        holder.mCourseName.setText(classScheduleItem.getCourseName());
        holder.mStartTime.setText(classScheduleItem.getStartTime());
        holder.mEndTime.setText(classScheduleItem.getEndTime());
        holder.mTeacher.setText(classScheduleItem.getTeacher());
    }

    @Override
    public int getItemCount() {
        return mClassScheduleItems.size();
    }
}
