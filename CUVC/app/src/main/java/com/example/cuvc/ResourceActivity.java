package com.example.cuvc;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class ResourceActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);

        ActionBar actionBar = getSupportActionBar();
        // Disable the ActionBar divider
        if (actionBar != null) {
            actionBar.setElevation(0);
        }

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        ResourcePagerAdapter adapter = new ResourcePagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Class Schedule");
                    break;
                case 1:
                    tab.setText("Class Materials");
                    break;
                default:
                    break;
            }
        }).attach();
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
    }




}