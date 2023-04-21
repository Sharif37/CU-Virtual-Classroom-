package com.example.cuvc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class classroomActivity extends AppCompatActivity {

    Toolbar toolbar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_classroom);

        toolbar = findViewById(R.id.CreateClass_toolbar);

        //set banner
        setClassNameAndDescription();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        getMenuInflater().inflate(R.menu.menu_setting, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        Intent intent ;
        if (id==R.id.peopleId) {
            Classmates();
            return true;
        }
        if (id==R.id.action_settings) {
           intent = new Intent(this, Display_class_key.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void Classmates() {

        Intent intent=new Intent(this,Class_Member.class);
        startActivity(intent);
        
    }


    @SuppressLint("Range")
    public void setClassNameAndDescription()
    {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db=databaseHelper.getReadableDatabase() ;

        String query = "SELECT ClassName,Description FROM Class LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        String name =null;
        String description=null ;
        if (cursor != null && cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndex("ClassName"));
            description = cursor.getString(cursor.getColumnIndex("Description"));
            cursor.close();
        }

        toolbar.setTitle(name+"\n"+description);

    }

}
