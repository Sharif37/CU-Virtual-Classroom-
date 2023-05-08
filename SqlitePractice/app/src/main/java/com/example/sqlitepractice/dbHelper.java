package com.example.sqlitepractice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class dbHelper  extends SQLiteOpenHelper {

    private static final String dbName = "class";
    private static final String tableName = "student";
    private static final String col1 = "id";
    private static final String col2 = "name";
    private static final String col3 = "email";
    private static final String col4 = "password";


    private static final int version = 6;

    private Context context;

    public dbHelper(@Nullable Context context) {
        super(context, dbName, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Toast.makeText(context, "onCreate method called", Toast.LENGTH_SHORT).show();
            db.execSQL("CREATE TABLE student(id text primary key,name text ,email text ,password text)");
        } catch (Exception e) {
            Toast.makeText(context, "error in create student table", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            Toast.makeText(context, "onUpgrade method called.", Toast.LENGTH_SHORT).show();
            db.execSQL("DROP TABLE IF EXISTS student");
            onCreate(db);
        } catch (Exception e) {
            Toast.makeText(context, "failed table update", Toast.LENGTH_SHORT).show();
        }

    }

    public long insertData(String id, String name, String email, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col1, id);
        values.put(col2, name);
        values.put(col3, email);
        values.put(col4, pass);

        long rowID = db.insert(tableName, null, values);
        return rowID;
    }

    public Cursor displayAlldata() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + tableName, new String[]{});
        return cursor;
    }

    public boolean updateDate(String id, String name, String email, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col2, name);
        values.put(col3, email);
        values.put(col4, pass);
        System.out.println(id);
        System.out.println(name);
        System.out.println(email);
        System.out.println(pass);
        long check = db.update(tableName,values,"id = ? ",new String[]{id});

        if (check > 0)
            return true;

            return false;


    }
}
