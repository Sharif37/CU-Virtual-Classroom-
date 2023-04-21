package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;



public class Re_cycleView extends AppCompatActivity {

    RecyclerView recycleView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);
        recycleView=findViewById(R.id.recycler_view);


    }
}