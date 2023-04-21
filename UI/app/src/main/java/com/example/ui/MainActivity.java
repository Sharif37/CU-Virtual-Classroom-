package com.example.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

   private ListView lv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv=findViewById(R.id.listview);

        String[] courseName= getResources().getStringArray(R.array.data);
        ArrayAdapter<String>  ad=new ArrayAdapter<String>(this,R.layout.structure_listview,R.id.sample,courseName);
        lv.setAdapter(ad);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String values=courseName[position];
                Toast.makeText(MainActivity.this, values, Toast.LENGTH_SHORT).show();
            }
        });
    }


}