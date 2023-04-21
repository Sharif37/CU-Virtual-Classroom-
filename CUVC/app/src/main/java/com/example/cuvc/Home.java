package com.example.cuvc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class Home extends BaseActivity implements View.OnClickListener {

    private DrawerLayout drawerLayout;
    private CardView classRoomCardView,ResourceCardView,EventCardView,TransportCardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Find the DrawerLayout in the layout
        drawerLayout = findViewById(R.id.drawerLayout);
        classRoomCardView = findViewById(R.id.ClassroomCardview);
        ResourceCardView = findViewById(R.id.ResourceCardView);
        EventCardView= findViewById(R.id.EventCardView);
        TransportCardView = findViewById(R.id.TransportCardView);


        classRoomCardView.setOnClickListener(this);
        ResourceCardView.setOnClickListener(this);
        EventCardView.setOnClickListener(this);
        TransportCardView.setOnClickListener(this);


    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_home;
    }

    @Override
    protected int getMenuResourceId() {
        return R.menu.refresh ;
    }


    @Override
    public void onClick(View v) {
           Intent intent ;
        switch(v.getId())
        {
            case R.id.ClassroomCardview:
                 intent = new Intent(Home.this, classroomActivity.class);
                startActivity(intent);
                break ;
            case R.id.ResourceCardView:
                 intent = new Intent(Home.this, ResourceActivity.class);
                startActivity(intent);
                break ;
            case R.id.EventCardView:
                intent = new Intent(Home.this, EventActivity.class);
                startActivity(intent);
                break ;
            case R.id.TransportCardView:
                intent = new Intent(Home.this, TransportActivity.class);
                startActivity(intent);

        }

    }
}
