package com.example.cuvc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText repassword,password, etid;
    private Button btnLogin;
    DatabaseHelper db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etid=findViewById(R.id.et_id1);
        password=findViewById(R.id.et_password1);
        repassword=findViewById(R.id.re_password1);
        btnLogin=findViewById(R.id.btn_login1);

        db=new DatabaseHelper(this);







        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String id=etid.getText().toString();
              String pass=password.getText().toString();
              if(id.isEmpty() || pass.isEmpty())
              {
                  Toast.makeText(LoginActivity.this, "please enter all the fields", Toast.LENGTH_SHORT).show();
              }
              else
              {
                  boolean checkuser=db.checkuserNameandPassword(id,pass);
                  if(checkuser==true)
                  {
                      Toast.makeText(LoginActivity.this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                      Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                      startActivity(intent);
                  }else
                  {
                      Toast.makeText(LoginActivity.this, "Invalid id or password", Toast.LENGTH_SHORT).show();
                  }
              }
            }
        });



    }
}