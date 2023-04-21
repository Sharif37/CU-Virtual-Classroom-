package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class listViewActivity extends AppCompatActivity {


    ListView list ;
    String[] subject;
    ArrayAdapter<String> adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        list=findViewById(R.id.list_view);
        subject=getResources().getStringArray(R.array.data);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,subject);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String sub=parent.getItemAtPosition(position).toString();
                Toast.makeText(listViewActivity.this, sub+"", Toast.LENGTH_SHORT).show();
            }
        });
    }
}