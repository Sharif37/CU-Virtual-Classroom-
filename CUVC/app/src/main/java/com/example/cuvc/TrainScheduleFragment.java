package com.example.cuvc;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TrainScheduleFragment extends Fragment {

    List<TrainSchedule> CityToCampusList;
    List<TrainSchedule> CampusToCityList;
    private RecyclerView trainFromCampusRecyclerView;
    private RecyclerView trainFromCityRecyclerView;
    private TrainAdapter trainFromCampusAdapter, trainFromCityAdapter;
    private DatabaseHelper databaseHelper;

    public TrainScheduleFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseHelper = new DatabaseHelper(getContext());

        storeTrainSchedules(CityToCampusList, CampusToCityList);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_train_schedule, container, false);


        trainFromCampusRecyclerView = view.findViewById(R.id.recyclerViewTrainFromCampus);
        trainFromCityRecyclerView = view.findViewById(R.id.recyclerViewTrainFromCity);

        trainFromCampusRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        trainFromCityRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        CityToCampusList = new ArrayList<>();
        CampusToCityList = new ArrayList<>();


        trainFromCampusAdapter = new TrainAdapter(CampusToCityList);
        trainFromCityAdapter = new TrainAdapter(CityToCampusList);
        trainFromCampusRecyclerView.setAdapter(trainFromCampusAdapter);
        trainFromCityRecyclerView.setAdapter(trainFromCityAdapter);

        return view;
    }

    private void storeTrainSchedules(List<TrainSchedule> CityToCampusList, List<TrainSchedule> CampusToCityList) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());

        // Create TrainSchedule objects for regular and holiday schedules
        TrainSchedule schedule1 = new TrainSchedule("morning", "07:30 AM");
        TrainSchedule schedule2 = new TrainSchedule("morning", "08:00 AM");
        TrainSchedule schedule3 = new TrainSchedule("morning", "09:45 AM");
        TrainSchedule schedule4 = new TrainSchedule("morning", "10:30 PM");
        TrainSchedule schedule5 = new TrainSchedule("noon", "2:50 PM");
        TrainSchedule schedule6 = new TrainSchedule("afternoon", "3:50 PM");
        TrainSchedule schedule7 = new TrainSchedule("night", "8:30 PM");

        databaseHelper.addTrainScheduleFromCity(schedule1);
        databaseHelper.addTrainScheduleFromCity(schedule2);
        databaseHelper.addTrainScheduleFromCity(schedule3);
        databaseHelper.addTrainScheduleFromCity(schedule4);
        databaseHelper.addTrainScheduleFromCity(schedule5);
        databaseHelper.addTrainScheduleFromCity(schedule6);
        databaseHelper.addTrainScheduleFromCity(schedule7);


    }
}