package com.example.cuvc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;


public class AboutUs extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DeveloperAdapter developerAdapter;
    private List<Developer> developerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        setTitle("About Us");
        recyclerView = findViewById(R.id.developerRecyclerView);

        // Set up RecyclerView with a LinearLayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the developerList
        developerList = new ArrayList<>();
        developerList.add(new Developer("Kazi Omar Sharif", "Android Developer,CSE20,CU", R.drawable.sharif, "https://www.facebook.com/sharif7364/", "https://www.linkedin.com/in/kazi-omar-sharif-ba13351b8/"));
        developerList.add(new Developer("Mohammad Fazly Rabby", "Android Developer,CSE20,CU", R.drawable.rabby, "", ""));
        developerList.add(new Developer("Saiyed Mohammad Hasan", "Android Developer,CSE20,CU", R.drawable.rakib, "", ""));
        developerList.add(new Developer("Priya Barua", "Android Developer,CSE20,CU", R.drawable.baseline_account_circle_24, "", ""));


        // Create and set the adapter
        developerAdapter = new DeveloperAdapter(developerList);
        recyclerView.setAdapter(developerAdapter);
    }
}



