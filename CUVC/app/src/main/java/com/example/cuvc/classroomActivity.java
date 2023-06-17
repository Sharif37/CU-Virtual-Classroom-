package com.example.cuvc;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class classroomActivity extends AppCompatActivity {

    Toolbar toolbar;
    private PostViewModel postViewModel;
    TextView inputPost;
     SharedPreferences preferences;
    private List<Post> postList;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    static String classKey ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setTitle("ClassRoom");
        setContentView(R.layout.activity_classroom);
        toolbar = findViewById(R.id.CreateClass_toolbar);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        inputPost = findViewById(R.id.post_input);
        //set banner
        //setClassNameAndDescription();
        //set banner from firebase

        preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String className = preferences.getString("className", "");
        String classDescription = preferences.getString("classDescription", "");
        String currentUser = preferences.getString("currentUser", "");
         classKey=preferences.getString("classCode","");


        //method to check is it admin or not
        isYouAdmin(currentUser);
        toolbarTitle.setText(className + "\n        " + classDescription);

        // initialize RecyclerView and PostAdapter
        recyclerView = findViewById(R.id.post_recycler_view_in_ClassActivity);
        postAdapter = new PostAdapter(postList,this);

        // set up RecyclerView

        recyclerView.setAdapter(postAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));







        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        postViewModel = new PostViewModel(sharedPreferences);

        // initialize PostViewModel
        // postViewModel = new ViewModelProvider(this).get(PostViewModel.class);


        // observe the LiveData object in the PostViewModel to retrieve the posts from the Firebase Realtime Database


        postViewModel.getPosts().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                postAdapter.setPostList(posts);
            }
        });


        inputPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(classroomActivity.this,Post_Activity.class );
                startActivity(intent);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);

        // Check if isAdmin is true
        boolean isAdmin = preferences.getBoolean("isAdmin", false);
        //Toast.makeText(this, isAdmin+"", Toast.LENGTH_SHORT).show();
        if (isAdmin) {
            // If user is an admin, show the menu_setting option
            getMenuInflater().inflate(R.menu.menu_setting, menu);
        }

        return true;
    }


    public void isYouAdmin(String id) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");
        Query query = userRef.orderByChild("id").equalTo(id);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        boolean isAdmin = false;
                        if (snapshot.child("admin").getValue() != null) {
                            isAdmin = snapshot.child("admin").getValue(Boolean.class);
                        }

                        editor.putBoolean("isAdmin", isAdmin);
                        editor.apply();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "isYouAdmin:onCancelled", error.toException());
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        if (id == R.id.peopleId) {
            Classmates();
            return true;
        }
        if (id == R.id.admin_action_settings) {
            intent = new Intent(this, Display_class_key.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void Classmates() {


        Intent intent = new Intent(this, Class_Member.class);
        startActivity(intent);

    }

// set classname and description using sqlite

  /*  @SuppressLint("Range")
    public void setClassNameAndDescription() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String query = "SELECT ClassName,Description FROM Class LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        String name = null;
        String description = null;
        if (cursor != null && cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndex("ClassName"));
            description = cursor.getString(cursor.getColumnIndex("Description"));
            cursor.close();
        }

        toolbar.setTitle(name + "\n" + description);

    }

   */


}
