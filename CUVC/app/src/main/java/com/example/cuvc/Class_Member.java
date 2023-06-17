package com.example.cuvc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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



             SharedPrefUtils s=new SharedPrefUtils(this);
           getMemberFirebase(s.getClassKey());
       // Toast.makeText(this, s.getClassKey()+"", Toast.LENGTH_SHORT).show();


        memberListAdapter = new MemberListAdapter(this, memberList);
        memberListView.setAdapter(memberListAdapter);


        memberListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Member clickedMember = memberList.get(position);
                SharedPrefUtils s = new SharedPrefUtils(getApplicationContext());


                if (s.isAdmin() && !clickedMember.isAdmin()) {
                    showMakeAdminMenu(clickedMember);
                }
            }
        });




    }


    public void getMemberFirebase(final String key) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        memberList = new ArrayList<>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String memberId = snapshot.child("id").getValue(String.class);
                    String memberName = snapshot.child("name").getValue(String.class);
                    String memberClassId = snapshot.child("classkey").getValue(String.class);
                    boolean isAdmin=snapshot.child("admin").getValue(boolean.class);

                    if (memberClassId != null && memberClassId.equals(key)) {
                        Member member = new Member(memberId, memberName,isAdmin);
                        memberList.add(member);
                    }
                }

                memberListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Class_Member", "Error while fetching data from Firebase", databaseError.toException());
            }
        });
    }

    private void showMakeAdminMenu(final Member clickedMember) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Make Admin")
                .setMessage("Do you want to make this member an admin?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        makeMemberAdmin(clickedMember);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void makeMemberAdmin(Member member) {
        member.setAdmin(true);
        CreateClassroom.updateAdminData(member.getId());
        memberListAdapter.notifyDataSetChanged();
    }








}