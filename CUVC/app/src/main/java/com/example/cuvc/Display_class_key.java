package com.example.cuvc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Display_class_key extends AppCompatActivity {



    TextView id,key_ ;
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_class_key);

       id=findViewById(R.id.class_id_text);
       key_=findViewById(R.id.class_key_text);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db=databaseHelper.getReadableDatabase() ;
        String k=" key " ;

        String query = "SELECT classID ,"+ k + "FROM Class LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        String ID =null;
        String Key=null ;
       // Toast.makeText(this, cursor+"", Toast.LENGTH_SHORT).show();
        if (cursor != null && cursor.moveToFirst()) {

            ID = cursor.getString(cursor.getColumnIndex("classID"));
            Key = cursor.getString(cursor.getColumnIndex("key"));
            cursor.close();
        }

        id.setText(id.getText()+ID);
        key_.setText(key_.getText()+Key);

    }
}