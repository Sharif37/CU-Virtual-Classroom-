package com.example.cuvc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db ;
    sessionManager sManager ;
    private boolean sessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FirebaseApp.initializeApp(this);


        sManager=new sessionManager(this);

             if (sManager.exitSession()) {
                 Intent intent = new Intent(this, Home.class);
                 startActivity(intent);


             } else {
                 Intent intent = new Intent(this, LoginActivity.class);
                 startActivity(intent);
             }




    }




}
