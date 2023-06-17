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
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class register extends AppCompatActivity implements DatabaseHelper.OnDataInsertedListener {


    FirebaseAuth mAuth;
    FirebaseApp Firebase;
    FirebaseOptions options;
    DatabaseHelper db;
    private EditText userID, userName, userEmail, userPassWord, userRePassWord;
    private TextView userHaveAccount;
    private Button btnRegister, google;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Registration");
        if (FirebaseApp.getApps(this).isEmpty()) {


            Firebase.initializeApp(this, options);
        }
        FirebaseStorage storage = FirebaseStorage.getInstance();



        mAuth = FirebaseAuth.getInstance();
        userID = findViewById(R.id.userid);
        userName = findViewById(R.id.username);
        userEmail = findViewById(R.id.email);
        userPassWord = findViewById(R.id.regPassword);
        btnRegister = findViewById(R.id.btnRegister);
        userHaveAccount = (TextView) findViewById(R.id.haveAccount);
        userRePassWord = findViewById(R.id.ConfirmPassword);
       // google = findViewById(R.id.reg_btnGoogle);
        db = new DatabaseHelper(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {


                // Write the code to open the register activity
                String name = userName.getText().toString();
                String id = userID.getText().toString();
                String email = userEmail.getText().toString();
                String pass = userPassWord.getText().toString();
                String repass = userRePassWord.getText().toString();


                if (id.equals("") || pass.equals("") || name.isEmpty() || email.isEmpty() || repass.isEmpty()) {
                    Toast.makeText(register.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else if (!isEmailValid(email)) {
                    Toast.makeText(register.this, "Email not valid", Toast.LENGTH_SHORT).show();
                    userEmail.requestFocus();
                } else if (pass.length() < 5) {
                    Toast.makeText(register.this, "Minimum length of password is 5", Toast.LENGTH_SHORT).show();
                    userPassWord.requestFocus();
                } else if (pass.equals(repass)) {

                    //boolean checkUser=db.checkUser(id,pass);

                    db.checkIfUserExists(id, register.this);


                } else {
                    Toast.makeText(register.this, "Password not matching", Toast.LENGTH_SHORT).show();

                }

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


       /* google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //userRegister();
                Toast.makeText(register.this, "coming soon!", Toast.LENGTH_SHORT).show();
            }
        });
*/

    }

/*

    public void userRegister() {
        String name = userName.getText().toString();
        String id = userID.getText().toString();
        String email = userEmail.getText().toString();
        String pass = userPassWord.getText().toString();
        String repass = userRePassWord.getText().toString();
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(register.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(register.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(register.this, "Registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        System.out.println("Registration failed: " + e.getMessage());
                    }
                });
    }
*/


    @Override
    public void onDataInserted(boolean isSuccessful) {
        if (isSuccessful) {
            Toast.makeText(register.this, "Registration successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(register.this, "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onUserExistenceCheckComplete(boolean exists) {
        String name = userName.getText().toString();
        String id = userID.getText().toString();
        String email = userEmail.getText().toString();
        String pass = userPassWord.getText().toString();
        String repass = userRePassWord.getText().toString();
        boolean isAdmin=false ;

        if (exists) {
            Toast.makeText(this, "Users already exits ", Toast.LENGTH_SHORT).show();
        } else {
            // User does not exist
            db.insertUser(id, name, email, pass);
            db.InsertUserInFirebaseDatabase(id, name, email, pass, false ,register.this);
        }
    }

    public boolean isEmailValid(String email) {
        // Define a regular expression for the Android email format
        String regex = "^[A-Za-z0-9+_.-]+@[a-zA-Z0-9.-]+$";

        // Use the regular expression to match the email address
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}