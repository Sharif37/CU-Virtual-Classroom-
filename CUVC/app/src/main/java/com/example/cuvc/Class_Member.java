package com.example.cuvc;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cuvc.Member;
import java.util.ArrayList;
import java.util.List;

public class Class_Member extends AppCompatActivity {


    private ListView memberListView;
    private List<Member> memberList;
    private MemberListAdapter memberListAdapter;
    ListView lv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_member);
        memberListView = findViewById(R.id.listview);


        // Create a list to hold the member objects
        memberList = new ArrayList<>();

        // Retrieve the list of members from the database
        SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ID, Name FROM user ", null);

        if (cursor.moveToFirst()) {
            do {
                String memberId = cursor.getString(0);
                String memberName = cursor.getString(1);
                Member member = new Member(memberId, memberName);
                memberList.add(member);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // Create a new adapter to display the member list
        memberListAdapter = new MemberListAdapter(this, memberList);

        // Set the adapter for the member list view
        memberListView.setAdapter(memberListAdapter);


    }
}