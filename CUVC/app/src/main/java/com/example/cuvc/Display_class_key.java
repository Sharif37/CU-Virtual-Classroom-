package com.example.cuvc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Display_class_key extends AppCompatActivity {


    SharedPreferences preferences ;
    TextView id,key_ ;
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_class_key);




       //finding class key in sqlite database

//        DatabaseHelper databaseHelper = new DatabaseHelper(this);
//        SQLiteDatabase db=databaseHelper.getReadableDatabase() ;
//        String k=" key " ;
//
//        String query = "SELECT classID ,"+ k + "FROM Class LIMIT 1";
//        Cursor cursor = db.rawQuery(query, null);
//        String ID =null;
//        String Key=null ;
//       // Toast.makeText(this, cursor+"", Toast.LENGTH_SHORT).show();
//        if (cursor != null && cursor.moveToFirst()) {
//
//            ID = cursor.getString(cursor.getColumnIndex("classID"));
//            Key = cursor.getString(cursor.getColumnIndex("key"));
//            cursor.close();
//        }

        preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String code = preferences.getString("classCode", "");

        TextView classKeyTextView = findViewById(R.id.class_key_textview);
         classKeyTextView.setText(classKeyTextView.getText()+code);
        classKeyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("class_key", classKeyTextView.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "Class key copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });



    }
}