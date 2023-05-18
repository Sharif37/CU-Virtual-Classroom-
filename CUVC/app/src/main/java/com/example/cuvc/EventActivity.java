package com.example.cuvc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class EventActivity extends AppCompatActivity {

    private CountDownTimer countdownTimer;
    private ProgressBar countdownProgress;
    private TextView timeTextView;
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private List<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        countdownProgress = findViewById(R.id.countdown_progress_bar);
        timeTextView = findViewById(R.id.countdown_text_view);


        // Set up your RecyclerView adapter and layout manager here
        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.upcoming_events_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a list of events (dummy data for demonstration)
        eventList = new ArrayList<>();
        eventList.add(new Event("","Event 1", "2023-05-20", "09:00 AM"));
        eventList.add(new Event("","Event 2", "2023-05-22", "02:30 PM"));
        eventList.add(new Event("","Event 3", "2023-05-25", "11:15 AM"));

        // Sort the event list based on time
        Collections.sort(eventList);

        // Create and set the adapter
        eventAdapter = new EventAdapter(eventList);
        recyclerView.setAdapter(eventAdapter);

        long timeLeftInMillis = 60000; // Example: 1 minute (you should replace this with your desired time)
        startCountdownTimer(timeLeftInMillis);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_edit) {
            // Handle the Edit menu item click
            openEditEventForm();
            Intent intent=new Intent(this,AdminFormActivity.class);
            startActivity(intent);
            return true;
        } else if (itemId == R.id.menu_upload) {
            // Handle the Upload menu item click
            uploadEventToFirebase();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openEditEventForm() {
        // Implement the logic to open the form for editing the event
        // You can start a new activity or show a dialog fragment, for example
    }

    private void uploadEventToFirebase() {
        // Implement the logic to upload the event to Firebase
        // You can use Firebase APIs to store the event data
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

