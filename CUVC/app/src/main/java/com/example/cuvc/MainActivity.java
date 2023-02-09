package com.example.cuvc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {

    private EditText etid, etEmail, etPassword,repassword, etName;
    private Button btnLogin, btnRegister;
    private ConstraintLayout constraintLayout;
    DatabaseHelper db ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etName = findViewById(R.id.et_name);
        btnRegister = findViewById(R.id.btn_register);
        etid=findViewById(R.id.et_id);
        db=new DatabaseHelper(this);





        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Write the code to open the register activity
                String name=etName.getText().toString();
                String id=etid.getText().toString();
                String email=etEmail.getText().toString();
                String pass=etPassword.getText().toString();
                //String repass=repassword.getText().toString();
                if(id.equals("") || pass.equals("")||name.isEmpty() || email.isEmpty() )
                {
                    Toast.makeText(MainActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else {

                        boolean checkuser=db.checkUser(id,pass);
                        if(checkuser==false)
                        {
                            boolean insert=db.insertUser(id,name,email,pass);
                            if(insert==true)
                            {
                                Toast.makeText(MainActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(),HomeActivity.class );
                                startActivity(intent);
                            }else
                            {
                                Toast.makeText(MainActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "user already exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                    //Toast.makeText(MainActivity.this, "Password not matching", Toast.LENGTH_SHORT).show();
                }

        });

    }
}
