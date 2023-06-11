package com.example.cuvc;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ClassScheduleFragment extends Fragment {

    RecyclerView mRecyclerView;
    ClassScheduleAdapter mAdapter;
    TextView time;

    List<ClassScheduleItem> scheduleItemList = new ArrayList<>();


    public ClassScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_class_schedule, container, false);



        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Class schedule");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scheduleItemList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ClassScheduleItem scheduleItem = dataSnapshot.getValue(ClassScheduleItem.class);
                    scheduleItemList.add(scheduleItem);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mRecyclerView = rootView.findViewById(R.id.classScheduleRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ClassScheduleAdapter(scheduleItemList);
        mRecyclerView.setAdapter(mAdapter);

        time = rootView.findViewById(R.id.todaysDateTextView);
        time.setText(new java.text.SimpleDateFormat("EEE, MMM d").format(new java.util.Date()));

        return rootView;
    }







}
