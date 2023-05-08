package com.example.sqlitepractice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

     EditText id,name,email,pass ;
     Button register,showDatabase,Update ;
    dbHelper db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=new dbHelper(this);


        id=findViewById(R.id.Id);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.password);
        register=findViewById(R.id.register);
        showDatabase=findViewById(R.id.showData);
        Update=findViewById(R.id.Update);
        register.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                String ID= id.getText().toString();
                String Name=name.getText().toString();
                String Email=email.getText().toString();
                String Pass=pass.getText().toString() ;


             long rowID= db.insertData(ID,Name,Email,Pass);
                if(rowID==-1)
                {
                    Toast.makeText(getApplicationContext(), "Registered failed", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Registered successfully", Toast.LENGTH_SHORT).show();
                    id.setText("");
                    name.setText("");
                    pass.setText("");
                    email.setText("");

                    Intent intent=new Intent(MainActivity.this,Home.class);
                    startActivity((intent));
                }
            }
        });
        showDatabase.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                 Cursor cursor=db.displayAlldata();
                if(cursor.getCount()==0)
                {
                    //Toast.makeText(MainActivity.this, "Empty Database", Toast.LENGTH_SHORT).show();
                    showData("Error","Empty database");
                }
                else
                {
                    StringBuffer sb =new StringBuffer();
                    while(cursor.moveToNext())
                    {
                        sb.append("Id : "+cursor.getString(0)+"\n");
                        sb.append("Name : "+cursor.getString(1)+"\n");
                        sb.append("Email : "+cursor.getString(2)+"\n");
                        sb.append("Password : "+cursor.getString(3)+"\n\n\n");
                    }
                    this.showData("Database: ",sb.toString());
                }
            }

            private void showData(String title, String message) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(title);
                builder.setMessage(message);
                builder.setCancelable(true);
                builder.show();
            }
        });



        Update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String ID= id.getText().toString();
                String Name=name.getText().toString();
                String Email=email.getText().toString();
                String Pass=pass.getText().toString() ;

                //Toast.makeText(MainActivity.this, Name, Toast.LENGTH_SHORT).show();
               boolean check =db.updateDate(ID,Name,Email,Pass);
               if(check)
               {
                   Toast.makeText(MainActivity.this, "Update row successfully.", Toast.LENGTH_SHORT).show();
               }
               else
               {
                   Toast.makeText(MainActivity.this, "Error in update row", Toast.LENGTH_SHORT).show();
               }
            }
        });



    }
}