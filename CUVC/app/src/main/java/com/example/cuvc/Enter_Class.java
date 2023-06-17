package com.example.cuvc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Enter_Class extends AppCompatActivity {


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    EditText classCode ;
    Button joinButton ;
    SharedPreferences preferences;
    Intent intent ;
    classroomActivity obj ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_class);

        classCode =findViewById(R.id.editTextClassCode);
        joinButton=findViewById(R.id.buttonJoinClass);
        obj=new classroomActivity();

        preferences = getSharedPreferences("MyPrefs",MODE_PRIVATE);
        String currentUser = preferences.getString("currentUser", "");




        joinButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String Code = classCode.getText().toString();
               checkClassKey(Code);
               databaseReference.child("users").child(currentUser).child("classkey").setValue(Code);

            }
        });

    }



    public void checkClassKey(String code) {

        DatabaseReference classRef = FirebaseDatabase.getInstance().getReference("classroom");
        Query query = classRef.orderByChild("classKey").equalTo(code);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            String className ;
            String classDescription ;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Save the class code to shared preferences
                    SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        className = snapshot.child("className").getValue(String.class);
                         classDescription = snapshot.child("classDescription").getValue(String.class);

                    }

                    //Toast.makeText(Enter_Class.this, className+"", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("classCode", code);
                    editor.putString("className", className);
                    editor.putString("classDescription", classDescription);
                    editor.apply();


                    // Start the classroom activity
                    Intent intent = new Intent(Enter_Class.this, classroomActivity.class);
                    intent.putExtra("className", className);
                    intent.putExtra("classDescription", classDescription);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Enter_Class.this, "Please enter a valid code", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }

}