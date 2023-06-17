package com.example.cuvc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {


    private CardView profileCardView;
    private ImageView profileImageView;
    private TextView usernameTextView;
    private TextView classNameTextView;
    private TextView classDesTextView;
    TextView Key ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        profileImageView = findViewById(R.id.profileImageView);
        usernameTextView = findViewById(R.id.usernameTextView);
        classNameTextView = findViewById(R.id.classNameTextView);
        classDesTextView = findViewById(R.id.classdes);
        Key=findViewById(R.id.classKeyTextView);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String className = preferences.getString("className", "");
        String classDescription = preferences.getString("classDescription", "");
        String currentUser = preferences.getString("currentUser", "");
        String classKey=preferences.getString("classCode","");
        boolean isAdmin=preferences.getBoolean("isAdmin",false);


        usernameTextView.setText(currentUser);
        classNameTextView.setText(className);
        classDesTextView.setText(classDescription);
        Key.setText(classKey);



        boolean isClassAvailable = isAdmin ;
        if (isClassAvailable) {
            Key.setVisibility(View.VISIBLE);
        } else {

            Key.setVisibility(View.GONE);
        }
    }



}
