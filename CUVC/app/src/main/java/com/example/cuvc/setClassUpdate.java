package com.example.cuvc;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class setClassUpdate extends AppCompatActivity {

    TextView time;
    private LinearLayout formLayout;
    FloatingActionButton addButton;
    Button saveBtn ;
    List<ClassScheduleItem> scheduleItemList ;
    View templateRow;
    Intent intent ;
    public int i=2 ;
    EditText course1,course2,course3,course4,course5 ;
    EditText startTime1,startTime2,startTime3,startTime4,startTime5;
    EditText endTime1,endTime2,endTime3,endTime4,endTime5 ;
    EditText teacher1,teacher2,teacher3,teacher4,teacher5 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_class_update);
        time = findViewById(R.id.todaysDateTextView);
        time.setText(new java.text.SimpleDateFormat("EEE, MMM d").format(new java.util.Date()));

        saveBtn=findViewById(R.id.btnSaveData) ;
        course1=findViewById(R.id.courseName1);
        course2=findViewById(R.id.courseName2);
        course3=findViewById(R.id.courseName3);
        course4=findViewById(R.id.courseName4);
        course5=findViewById(R.id.courseName5);

        startTime1=findViewById(R.id.startTime1);
        startTime2=findViewById(R.id.startTime2);
        startTime3=findViewById(R.id.startTime3);
        startTime4=findViewById(R.id.startTime4);
        startTime5=findViewById(R.id.startTime5);

        endTime1=findViewById(R.id.endTime1);
        endTime2=findViewById(R.id.endTime2);
        endTime3=findViewById(R.id.endTime3);
        endTime4=findViewById(R.id.endTime4);
        endTime5=findViewById(R.id.endTime5);

        teacher1=findViewById(R.id.teacher1);
        teacher2=findViewById(R.id.teacher2);
        teacher3=findViewById(R.id.teacher3);
        teacher4=findViewById(R.id.teacher4);
        teacher5=findViewById(R.id.teacher5);



        scheduleItemList=new ArrayList<>();



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create a bundle and add the ArrayList to it
                Bundle bundle = new Bundle();
                bundle.putSerializable("scheduleItemList", (Serializable) scheduleItemList);






                String course1Value = course1.getText().toString();
                String course2Value = course2.getText().toString();
                String course3Value = course3.getText().toString();
                String course4Value = course4.getText().toString();
                String course5Value = course5.getText().toString();

                String startTime1Value = startTime1.getText().toString();
                String startTime2Value = startTime2.getText().toString();
                String startTime3Value = startTime3.getText().toString();
                String startTime4Value = startTime4.getText().toString();
                String startTime5Value = startTime5.getText().toString();

                String endTime1Value = endTime1.getText().toString();
                String endTime2Value = endTime2.getText().toString();
                String endTime3Value = endTime3.getText().toString();
                String endTime4Value = endTime4.getText().toString();
                String endTime5Value = endTime5.getText().toString();

                String teacher1Value = teacher1.getText().toString();
                String teacher2Value = teacher2.getText().toString();
                String teacher3Value = teacher3.getText().toString();
                String teacher4Value = teacher4.getText().toString();
                String teacher5Value = teacher5.getText().toString();

                // Collect the schedules

                CollectSchedule(course1Value, startTime1Value, endTime1Value, teacher1Value);
                CollectSchedule(course2Value, startTime2Value, endTime2Value, teacher2Value);
                CollectSchedule(course3Value, startTime3Value, endTime3Value, teacher3Value);
                CollectSchedule(course4Value, startTime4Value, endTime4Value, teacher4Value);
                CollectSchedule(course5Value, startTime5Value, endTime5Value, teacher5Value);

                saveDataToFirebase(scheduleItemList);

                intent = new Intent(getApplicationContext(), ResourceActivity.class);
                startActivity(intent);
            }
        });





    }


    private void saveDataToFirebase(List<ClassScheduleItem> scheduleItemList) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Class schedule");

        databaseReference.setValue(scheduleItemList).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Data saved successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed to save data", Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void CollectSchedule(String courseName, String start, String end, String teacher) {
        if (courseName != null && !courseName.isEmpty()) {
            ClassScheduleItem item = new ClassScheduleItem(courseName, start, end, teacher);
            scheduleItemList.add(item);
        }
    }






}