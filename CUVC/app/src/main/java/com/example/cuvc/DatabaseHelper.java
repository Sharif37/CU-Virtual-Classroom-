package com.example.cuvc ;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int version=4 ;
    public static final String databaseName="Classroom";
    public static final String tableName="user";
    public static final String col1="ID" ;
    public static final String col2="Name" ;
    public static final String col3="Email" ;
    public static final String col4="PassWord" ;


// table 2


    public static final int version1=1 ;
    public static final String tableName2="sessions";

    public static final String col11="ID" ;
    public static final String col22="session_id" ;
    public static final String col33="login_time" ;
    public static final long   session_timeout=30*60*60 ;

    String CreateTable="CREATE TABLE " + tableName+ "("
            + col1 + " INTEGER PRIMARY KEY ,"
            + col2 + " TEXT,"
            + col3 + " TEXT,"
            + col4 + " TEXT"
            + ")";

    String CreateTable2="CREATE TABLE " + tableName2 + "("
            + col11 + " INTEGER  ,"
            + col22 + " TEXT ,"
            + col33 + " INTEGER "
            + ")" ;

      DatabaseHelper(@Nullable Context context) {
        super(context, databaseName, null, version);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateTable);
        db.execSQL(CreateTable2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+tableName);
        db.execSQL("DROP TABLE IF EXISTS "+tableName2);
        onCreate(db);

    }

    public boolean insertUser(String id,String name, String email, String password) {
        // Get writeable database
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(col1,id);
        values.put(col2, name);
        values.put(col3, email);
        values.put(col4, password);

        // Inserting row

        long result= db.insert(tableName, null, values);
        db.close();

        if(result==-1)
        {
            return false ;
        }
        else {
            return true ;
        }
        // Close database connection

    }


    public boolean checkUser(String id, String password) {

        // Array of columns to fetch
        String[] projection ={col1};
        SQLiteDatabase db = this.getReadableDatabase();
        // Selection criteria
        String selection = col1 + " = ?" + " AND " + col4 + " = ?";

        // Selection arguments
        String[] selectionArgs = {id, password};


       Cursor cursor=db.query(tableName,projection,selection,selectionArgs,null,null,null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
    public boolean checkUserNameAndPassword(String id,String password)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from user where ID=? and PassWord=? ", new String[]{id,password});
        if(cursor.getCount()>0)
        {
            return true ;
        }
        else
            return false ;
    }














}