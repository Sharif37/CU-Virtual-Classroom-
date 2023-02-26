package com.example.cuvc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText userInputID,userInputPassWord ;
    Button btnLogin ;
    TextView signup;

    DatabaseHelper db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userInputID=findViewById(R.id.inputUserID);
        userInputPassWord=findViewById(R.id.LogInpassword);
        btnLogin=findViewById(R.id.btnLogin);
        signup=findViewById(R.id.textViewSignUp);
        db=new DatabaseHelper(this);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            public sessionManager sm=new sessionManager(LoginActivity.this);
            @Override
            public void onClick(View v) {

                String id=userInputID.getText().toString() ;
                String password=userInputPassWord.getText().toString() ;
                sm.createSession(id);


             boolean check=db.checkUserNameAndPassword(id,password);

             if(check)
             {
                 Toast.makeText(LoginActivity.this, "Log in successfully.", Toast.LENGTH_SHORT).show();
                 Intent intent=new Intent(LoginActivity.this,Home.class);
                 startActivity(intent);
                 finish();
             }else
             {
                 Toast.makeText(LoginActivity.this, "ID or Password incorret.", Toast.LENGTH_SHORT).show();
             }
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
}