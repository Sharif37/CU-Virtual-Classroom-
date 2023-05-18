package com.example.cuvc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle mDrawerToggle;
    sessionManager sm ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        //Toast.makeText(this, "hello ", Toast.LENGTH_SHORT).show();
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
        getMenuInflater().inflate(getMenuResourceId(), menu);
        return true;
    }


    protected abstract int getLayoutResourceId();
    protected abstract int getMenuResourceId();

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
        if(item.getItemId()==R.id.updateClassTime)
        {
            intent = new Intent(this, setClassUpdate.class);
            startActivity(intent);

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}