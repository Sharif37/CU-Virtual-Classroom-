package com.example.cuvc;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TransportActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);

        ActionBar actionBar = getSupportActionBar();
        // Disable the ActionBar divider
        if (actionBar != null) {
            actionBar.setElevation(0);
        }

        tabLayout = findViewById(R.id.Transfort_tabLayout);
        viewPager = findViewById(R.id.Tranfort_viewPager);

        TransportPagerAdapter adapter = new TransportPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Train");
                    break;
                case 1:
                    tab.setText("Bus");
                    break;
                default:
                    break;
            }
        }).attach();
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);

    }
}