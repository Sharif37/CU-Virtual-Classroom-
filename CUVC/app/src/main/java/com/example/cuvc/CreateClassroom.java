package com.example.cuvc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;
import java.security.SecureRandom;
import java.math.BigInteger;

public class CreateClassroom extends AppCompatActivity {

    private EditText mClassNameEditText;
    private EditText mClassDescriptionEditText;
    private DatabaseHelper dbHelper;
    private Context context;
    private static SecureRandom random;

    public CreateClassroom() {
        // Required empty public constructor

    }

    public  CreateClassroom(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createclassroom);

        mClassNameEditText = findViewById(R.id.class_name);
        mClassDescriptionEditText = findViewById(R.id.class_description);

        Button createClassButton = findViewById(R.id.Class_button);
        createClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createClass();
            }
        });

    }

    @SuppressLint("Range")
    private void createClass() {
        String ClassId=generateClassKey(8);
        String className = mClassNameEditText.getText().toString();
        String classDescription = mClassDescriptionEditText.getText().toString();

        if (className.isEmpty() || classDescription.isEmpty()) {
            Toast.makeText(this, "Please enter all the required fields", Toast.LENGTH_SHORT).show();
            return;
        }


        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db=databaseHelper.getReadableDatabase() ;

        String query = "SELECT ID FROM sessions LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        String adminId =null;
        if (cursor != null && cursor.moveToFirst()) {
            adminId = cursor.getString(cursor.getColumnIndex("ID"));
            cursor.close();
        }


        databaseHelper.createClassroom(ClassId,className,classDescription,adminId,generateClassKey(6));


        finish();
    }

    private String generateSessionId() {
         return UUID.randomUUID().toString();
    }





            public static String generateClassKey(int length) {
                random = new SecureRandom();
                return new BigInteger(length * 5, random).toString(32).substring(0, length);

        }





}
