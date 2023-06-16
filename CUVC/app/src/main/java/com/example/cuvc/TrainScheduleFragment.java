package com.example.cuvc;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
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

        ArrayList<TrainSchedule> CityToCampusList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        String[] scheduleTimes1 = {"07:30 AM", "08:00 AM", "09:45 AM", "10:30 AM", "2:50 PM", "03:50 PM", "08:30 PM"};
        String[] scheduleTimes2 = {"08:45 AM", "09:20 AM", "01:30 PM", "2:30 PM", "4:00 PM", "05:30 PM", "09:30 PM"};
        String[] scheduleHoliday1 = {"07:50 AM", "02:50 PM", "08:30 PM"};
        String[] scheduleHoliday2 = {"09:00 AM", "04:00 PM", "09:30 PM"};

        if (dayOfWeek == Calendar.FRIDAY || dayOfWeek == Calendar.SATURDAY) {
            for (String time : scheduleHoliday1) {
                TrainSchedule schedule = new TrainSchedule(time);
                CityToCampusList.add(schedule);
            }
            for (String time : scheduleHoliday2) {
                TrainSchedule schedule = new TrainSchedule(time);
                CampusToCityList.add(schedule);
            }
        } else {
            for (String time : scheduleTimes1) {
                TrainSchedule schedule = new TrainSchedule(time);
                CityToCampusList.add(schedule);
            }
            for (String time : scheduleTimes2) {
                TrainSchedule schedule = new TrainSchedule(time);
                CampusToCityList.add(schedule);
            }
        }


        UpcomingTrain(CityToCampusList);
        UpcomingTrain(CampusToCityList);



        trainFromCampusAdapter = new TrainAdapter(CampusToCityList);
        trainFromCityAdapter = new TrainAdapter(CityToCampusList);
        trainFromCampusRecyclerView.setAdapter(trainFromCampusAdapter);
        trainFromCityRecyclerView.setAdapter(trainFromCityAdapter);

        return view;
    }

    private void UpcomingTrain(List<TrainSchedule> scheduleList) {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMin = calendar.get(Calendar.MINUTE);
        int amPm = calendar.get(Calendar.AM_PM);

        TrainSchedule closestUpcomingTrain = null;
        int closestHour = 24;
        int closestMinute = 60;

        for (TrainSchedule schedule : scheduleList) {
            String time = schedule.getTime(); // Get the time string of the train schedule
            String[] parts = time.split(":");
            int hour = Integer.parseInt(parts[0].trim()); // Extract the hour from the time string
            String[] minuteParts = parts[1].split(" ");
            int minute = Integer.parseInt(minuteParts[0].trim());
            String amOrPm = minuteParts[1].trim();
            System.out.println(amOrPm);
            if(amOrPm.equals("PM"))
            {
                hour+=12;
                System.out.println(hour);
            }


            if ((hour > currentHour || (hour == currentHour && minute >= currentMin))
                    && (hour < closestHour || (hour == closestHour && minute < closestMinute))) {
                closestUpcomingTrain = schedule;
                closestHour = hour;
                closestMinute = minute;
            }
        }

        if (closestUpcomingTrain != null) {
            closestUpcomingTrain.setBackgroundColor("#90EE90");
        }
    }

}