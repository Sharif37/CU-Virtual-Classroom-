package com.example.cuvc;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class TransportPagerAdapter extends FragmentStateAdapter {
        private static final int NUM_PAGES = 2;

        public TransportPagerAdapter (FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new TrainScheduleFragment();
                case 1:
                    return new BusScheduleFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }


