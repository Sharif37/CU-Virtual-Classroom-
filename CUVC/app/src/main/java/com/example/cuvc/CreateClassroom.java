package com.example.cuvc;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CreateClassroom extends AppCompatActivity {

    private static SecureRandom random;
    String adminId;
    private EditText mClassNameEditText;
    private EditText mClassDescriptionEditText;
    private DatabaseHelper dbHelper;
    TextView setClassKey ;
    private Context context;

    public CreateClassroom() {



    }

    public CreateClassroom(Context context) {
        this.context = context;


    }

    public static String generateClassKey(int length) {
        random = new SecureRandom();
        return new BigInteger(length * 5, random).toString(32).substring(0, length);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createclassroom);
        mClassNameEditText = findViewById(R.id.class_name);
        mClassDescriptionEditText = findViewById(R.id.class_description);
        dbHelper = new DatabaseHelper(context);
        setClassKey=findViewById(R.id.classKey_create);

        Button createClassButton = findViewById(R.id.Class_button);
        createClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(exitClassroom())
                {
                    clearClassroomData();
                }
                createClass();
                mClassNameEditText.setText("");
                mClassDescriptionEditText.setText("");
            }
        });


    }


    public boolean exitClassroom() {
        // Toast.makeText(mcontext, "getSessionId Method", Toast.LENGTH_SHORT).show();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db =databaseHelper.getReadableDatabase() ;
        String query="SELECT COUNT(*) FROM "+"Class";
        Cursor cursor=db.rawQuery(query,null);
        cursor.moveToFirst();
        return cursor.getInt(0)>0 ;

    }
    public void clearClassroomData() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete("Class", null, null);
        db.close();
    }
    @SuppressLint("Range")
    private void createClass() {

        String ClassId = generateClassKey(8);
        String className = mClassNameEditText.getText().toString();
        String classDescription = mClassDescriptionEditText.getText().toString();

        if (className.isEmpty() || classDescription.isEmpty()) {
            Toast.makeText(this, "Please enter all the required fields", Toast.LENGTH_SHORT).show();
            return;
        }


        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        /*
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String query = "SELECT ID FROM sessions LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        adminId = null;
        if (cursor != null && cursor.moveToFirst()) {
            adminId = cursor.getString(cursor.getColumnIndex("ID"));
            cursor.close();
        }*/

        SharedPrefUtils s=new SharedPrefUtils(this);
        adminId=s.getCurrentUser();


        databaseHelper.createClassroom(ClassId, className, classDescription, adminId, generateClassKey(6));
        createClassroomInFirebase(ClassId, className, classDescription, adminId, generateClassKey(6));




    }

    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }

    public void createClassroomInFirebase(String classId, String className, String classDescription, String adminId, String classKey) {

        updateAdminData(adminId);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        Classroom classroom = new Classroom(classId, className, classDescription, adminId, classKey);

        databaseReference.child("classroom").child(classId).setValue(classroom);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("classId", classId);
        editor.apply();

        String text=setClassKey.getText().toString();
        setClassKey.setText(text+classKey);
        setClassKey.setVisibility(View.VISIBLE);

        ImageView copy = findViewById(R.id.copyIcon);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Copy classKey to clipboard
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Class Key", classKey);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(getApplicationContext(), "Class Key copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });


    }


    public static void updateAdminData(String userId)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");


        DatabaseReference currentUserRef = usersRef.child(userId);

        Map<String, Object> updates = new HashMap<>();
        updates.put("admin", true);

        currentUserRef.updateChildren(updates);

    }


}
