package com.example.cuvc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class register extends AppCompatActivity {


    private EditText userID,userName,userEmail,userPassWord,userRePassWord ;
    private TextView userHaveAccount;
    private Button btnRegister ;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        userID = findViewById(R.id.userid);
        userName = findViewById(R.id.username);
        userEmail = findViewById(R.id.email);
        userPassWord = findViewById(R.id.regPassword);
        btnRegister = findViewById(R.id.btnRegister);
        userHaveAccount = (TextView) findViewById(R.id.haveAccount);
        userRePassWord = findViewById(R.id.ConfirmPassword);
        DatabaseHelper db = new DatabaseHelper(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {


                // Write the code to open the register activity
                String name=userName.getText().toString();
                String id=userID.getText().toString();
                String email=userEmail.getText().toString();
                String pass=userPassWord.getText().toString();
                String repass=userRePassWord.getText().toString();
                if(id.equals("") || pass.equals("")||name.isEmpty() || email.isEmpty()||repass.isEmpty() )
                {
                    Toast.makeText(register.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else if(pass.equals(repass)){

                    boolean checkUser=db.checkUser(id,pass);
                    if(checkUser==false)
                    {
                        boolean insert=db.insertUser(id,name,email,pass);
                        if(insert==true)
                        {
                            Toast.makeText(register.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),LoginActivity.class );
                            startActivity(intent);
                        }else
                        {
                            Toast.makeText(register.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(register.this, "user already exists", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(register.this, "Password not matching", Toast.LENGTH_SHORT).show();
            }

        });


        userHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }
}