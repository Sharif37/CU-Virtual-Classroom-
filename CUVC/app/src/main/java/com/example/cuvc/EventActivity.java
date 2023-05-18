package com.example.cuvc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class EventActivity extends AppCompatActivity {


    private CountDownTimer countdownTimer;
    private ProgressBar countdownProgress;
    private TextView timeTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

            countdownProgress = findViewById(R.id.countdown_progress_bar);
            timeTextView = findViewById(R.id.countdown_text_view);

            RecyclerView upcomingEventsRecyclerView = findViewById(R.id.upcoming_events_recycler_view);

            // Set up your RecyclerView adapter and layout manager here
        long timeLeftInMillis = 60000; // Example: 1 minute (you should replace this with your desired time)
        startCountdownTimer(timeLeftInMillis);
        }

    private void startCountdownTimer(long timeLeftInMillis) {
        countdownProgress.setMax(10000); // Set a large max value for precision
        countdownProgress.setProgress(10000); // Start with full progress

        countdownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long totalTime = timeLeftInMillis;
                long elapsedTime = totalTime - millisUntilFinished;

                int progress = (int) (elapsedTime * 10000 / totalTime);
                countdownProgress.setProgress(10000 - progress);

                long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
                long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 24;
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60;
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60;

                String timeLeft = String.format(Locale.getDefault(), "%02d:%02d:%02d", days * 24 + hours, minutes, seconds);
                timeTextView.setText(timeLeft);
            }

            @Override
            public void onFinish() {
                // Handle the end of the countdown timer
            }
        };
        countdownTimer.start();
    }




}
