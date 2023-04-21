package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;


public class Re_cycleView extends AppCompatActivity {

    RecyclerView recycleView ;
    private ArrayList<String>name =new ArrayList<>();
    private ArrayList<Integer>img =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);
        recycleView=findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(layoutManager);
        RecycleViewAdapter adapter=new RecycleViewAdapter(name,img,Re_cycleView.this);
        recycleView.setAdapter(adapter);
        name.add("nature1");
        name.add("nature2") ;
        name.add("nature3") ;

        img.add(R.drawable.downloa);
        img.add(R.drawable.download);
        img.add(R.drawable.pic);





    }
}