package com.example.cuvc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    public  DrawerLayout mDrawerLayout;
    public  ActionBarDrawerToggle mDrawerToggle;
       sessionManager sm ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);

        // Find the DrawerLayout in the layout
        mDrawerLayout = findViewById(R.id.drawerLayout);


        NavigationView nav= findViewById(R.id.navigationView);
        nav.setNavigationItemSelectedListener(this);



        // Set up the ActionBarDrawerToggle with the DrawerLayout
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.OpenDrawer, R.string.CloseDrawer);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.refresh, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent ;
        if(item.getItemId()==R.id.opClassroom)
        {
            intent=new Intent(this, CreateClassroom.class) ;
            startActivity(intent);

        }
        if(item.getItemId()==R.id.opProfile)
        {
            intent=new Intent(this,profile.class) ;
            startActivity(intent);

        }

        if(item.getItemId()==R.id.opLogout)
        {
            sm=new sessionManager(this);
             sm.clearSessionData();
             intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();

        }
        return false ;
    }
}

