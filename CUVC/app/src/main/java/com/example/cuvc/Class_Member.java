package com.example.cuvc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cuvc.Member;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Class_Member extends AppCompatActivity {

    int n=0 ;
    private ListView memberListView;
    private List<Member> memberList;
    private MemberListAdapter memberListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_member);
        memberListView = findViewById(R.id.listview);




//        // Retrieve the list of members from the database
//        SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT ID, Name FROM user ", null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                String memberId = cursor.getString(0);
//                String memberName = cursor.getString(1);
//                Member member = new Member(memberId, memberName);
//                memberList.add(member);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        db.close();

           getMemberInfirebase();

        // Create a new adapter to display the member list
        memberListAdapter = new MemberListAdapter(this, memberList);

        // Set the adapter for the member list view
        memberListView.setAdapter(memberListAdapter);


    }


    public void getMemberInfirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        memberList = new ArrayList<>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String memberId = snapshot.child("id").getValue(String.class);
                    String memberName = snapshot.child("name").getValue(String.class);
                    Member member = new Member(memberId, memberName);

                    memberList.add(member);
                }

                memberListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Class_Member", "Error while fetching data from Firebase", databaseError.toException());
            }
        });
    }



}