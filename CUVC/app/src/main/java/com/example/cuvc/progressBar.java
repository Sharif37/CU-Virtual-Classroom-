package com.example.cuvc;

import android.view.View;

public class progressBar {

    public static void showProgressBar(View progressBarLayout) {
        progressBarLayout.setVisibility(View.VISIBLE);
        progressBarLayout.bringToFront();
    }

    public static void hideProgressBar(View progressBarLayout) {
        progressBarLayout.setVisibility(View.GONE);
    }
}
