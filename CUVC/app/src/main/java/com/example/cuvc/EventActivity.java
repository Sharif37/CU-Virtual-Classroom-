package com.example.cuvc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class EventActivity extends AppCompatActivity {

    TextView noEventsTextView;
    private CountDownTimer countdownTimer;
    private ProgressBar countdownProgress;
    private TextView timeTextView, eName;
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private List<Event> eventList;
    private View progressBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);


        countdownProgress = findViewById(R.id.countdown_progress_bar);
        timeTextView = findViewById(R.id.countdown_text_view);
        progressBarLayout = findViewById(R.id.progressBarLayout);
        eName = findViewById(R.id.name_text_view);


        //set Recyclerview
        recyclerView = findViewById(R.id.upcoming_events_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        eventList = new ArrayList<>();


        retrieveEventData();


        // adapter
        eventAdapter = new EventAdapter(eventList);
        recyclerView.setAdapter(eventAdapter);

        //if no event ( array list empty)
        noEventsTextView = findViewById(R.id.noeventmessage);


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
            Intent intent = new Intent(this, AdminFormActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void startCountdownTimer(long timeLeftInMillis, long total, String eventId) {
        countdownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long remainingTimeInMillis = millisUntilFinished;

                int progress = (int) ((total - remainingTimeInMillis) * 100 / total);

                //Toast.makeText(EventActivity.this, progress+"", Toast.LENGTH_SHORT).show();
                countdownProgress.setProgress(100 - progress);

                long days = TimeUnit.MILLISECONDS.toDays(remainingTimeInMillis);
                long hours = TimeUnit.MILLISECONDS.toHours(remainingTimeInMillis) % 24;
                long minutes = TimeUnit.MILLISECONDS.toMinutes(remainingTimeInMillis) % 60;

                String timeLeft = String.format(Locale.getDefault(), "%02d:%02d:%02d", days, hours, minutes);
                timeTextView.setText(timeLeft);
            }

            @Override
            public void onFinish() {
                // if progress bar finished
                deleteEvent(eventId);
            }
        };
        countdownTimer.start();
    }


    private void deleteEvent(String eventId) {
        DatabaseReference eventRef = FirebaseDatabase.getInstance().getReference().child("events").child(eventId);
        eventRef.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Event deleted successfully
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to delete the event
                    }
                });
    }


    public void retrieveEventData() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference eventRef = rootRef.child("events");

        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventList.clear();
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    Event event = eventSnapshot.getValue(Event.class);
                    String eventId = eventSnapshot.getKey();
                    event.setId(eventId); // set event id for delete
                    eventList.add(event);
                }

                // Sort the event
                Collections.sort(eventList);

                eventAdapter.notifyDataSetChanged();

                if (eventList.isEmpty()) {

                    noEventsTextView.setVisibility(View.VISIBLE);
                } else {

                    noEventsTextView.setVisibility(View.GONE);

                    Event firstEvent = eventList.get(0);
                    String eventDate = firstEvent.getDate();
                    String eventTime = firstEvent.getTime();
                    String creationDate = firstEvent.getCreationDate();
                    String creationTime = firstEvent.getCreationTime();
                    String eventId = firstEvent.getId();
                    String name = firstEvent.getName();

                    //set event name
                    eName.setText(name);
                    calculateTimeLeft(eventDate, eventTime, creationDate, creationTime, eventId);
                }

                progressBar.hideProgressBar(progressBarLayout);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void calculateTimeLeft(String eventDate, String eventTime, String creationDate, String creationTime, String eventId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        try {

            //parse date and time
            Date eventDateTime = sdf.parse(eventDate + " " + eventTime);
            Date creationDateTime = sdf.parse(creationDate + " " + creationTime);

            // current date and time
            Calendar currentDateTime = Calendar.getInstance();

            // Calculate the remaining time based on the creation time
            long timeDifference = eventDateTime.getTime() - currentDateTime.getTimeInMillis();
            long total = eventDateTime.getTime() - creationDateTime.getTime();

            //if time behind
            if (timeDifference < 0) {

                countdownProgress.setProgress(0);
                deleteEvent(eventId);
                Toast.makeText(this, "Event has occurred" + "", Toast.LENGTH_SHORT).show();
            } else {
                startCountdownTimer(timeDifference, total, eventId);
            }

        } catch (ParseException e) {
            Toast.makeText(this, "Error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        progressBar.showProgressBar(progressBarLayout);
    }


}

