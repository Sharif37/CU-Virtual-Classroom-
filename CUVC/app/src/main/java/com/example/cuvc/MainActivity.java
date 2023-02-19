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

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db ;
    sessionManager sManager ;
    private boolean sessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sManager=new sessionManager(this);

             if (sManager.getSessionId()) {
                 Intent intent = new Intent(this, HomeActivity.class);
                 startActivity(intent);


             } else {
                 Intent intent = new Intent(this, LoginActivity.class);
                 startActivity(intent);
             }




    }




}
