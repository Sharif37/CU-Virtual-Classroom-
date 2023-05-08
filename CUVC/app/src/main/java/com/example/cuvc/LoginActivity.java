package com.example.cuvc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText userInputID, userInputPassWord;
    Button btnLogin;
    TextView signup;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userInputID = findViewById(R.id.inputUserID);
        userInputPassWord = findViewById(R.id.LogInpassword);
        btnLogin = findViewById(R.id.btnLogin);
        signup = findViewById(R.id.textViewSignUp);
        db = new DatabaseHelper(this);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            public final sessionManager sm = new sessionManager(LoginActivity.this);

            @Override
            public void onClick(View v) {

                String id = userInputID.getText().toString();
                String password = userInputPassWord.getText().toString();
                SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("currentUser", id);
                editor.apply();
                sm.createSession(id);

                checkUserNameAndPassword(id, password);

             /*boolean check=db.checkUserNameAndPassword(id,password);

             if(check)
             {
                 Toast.makeText(LoginActivity.this, "Log in successfully.", Toast.LENGTH_SHORT).show();
                 Intent intent=new Intent(LoginActivity.this,Home.class);
                 startActivity(intent);
                 finish();
             }else
             {
                 Toast.makeText(LoginActivity.this, "ID or Password incorret.", Toast.LENGTH_SHORT).show();
             } */
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, register.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void checkUserNameAndPassword(String id, String password) {
        // Get a reference to the Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        // Check if a child node with the given user ID exists under the "users" node
        databaseReference.child("users").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // If the snapshot exists, the user already exists
                if (dataSnapshot.exists()) {
                    // Get the user object from the snapshot
                    User user = dataSnapshot.getValue(User.class);

                    // Check if the password matches
                    if (user.getPassword().equals(password)) {
                        // Login successful
                        Intent intent = new Intent(LoginActivity.this, Home.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Password doesn't match
                        Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // User doesn't exist
                    Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
                Toast.makeText(LoginActivity.this, "Login failed: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }









}
