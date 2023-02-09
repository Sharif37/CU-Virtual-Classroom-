package com.example.cuvc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "user_db";

    // Table Names
    private static final String TABLE_USERS = "users";

    // Column names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    // Create table SQL query
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY ,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_EMAIL + " TEXT,"
                    + COLUMN_PASSWORD + " TEXT"
                    + ")";

    //public DatabaseHelper(Context context) {
        //super(context, DATABASE_NAME, null, DATABASE_VERSION);
    //}
    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating table
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Create tables again
        onCreate(db);
    }


    public boolean insertUser(String id,String name, String email, String password) {
        // Get writeable database
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID,id);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        // Inserting row

        long result= db.insert(TABLE_USERS, null, values);
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
        String[] columns = {
                COLUMN_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // Selection criteria
        String selection = COLUMN_ID + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";

        // Selection arguments
        String[] selectionArgs = {id, password};

        // Query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack
         * **/
        Cursor cursor = db.query(TABLE_USERS, //Table to query
                columns, //columns to return
                selection, //columns for the WHERE clause
                selectionArgs, //The values for the WHERE clause
                null, //group the rows
                null, //filter by row groups
                null); //The sort order
        int cursorCount = cursor.getCount();
        //cursor.close();
       // db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
  public boolean checkuserNameandPassword(String id,String password)
  {
      SQLiteDatabase db=this.getWritableDatabase();
      Cursor cursor=db.rawQuery("select * from users where id=? and password=? ", new String[]{id,password});
      if(cursor.getCount()>0)
      {
          return true ;
      }
      else
          return false ;
  }


}
