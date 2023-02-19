package com.example.cuvc;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.UUID;

public class sessionManager {

    public static final int version=1 ;
    public static final String tableName="sessions";
    public static final String col1="ID" ;
    public static final String col2="session_id" ;
    public static final String col3="login_time" ;
    public static final long   session_timeout=30*60*60 ;
    private DatabaseHelper dbHelper ;



   private Context mcontext ;
    public sessionManager(Context context) {
        mcontext = context;
        dbHelper=new DatabaseHelper(mcontext);
    }




  public void createSession(String  userid)
  {
      SQLiteDatabase db=dbHelper.getWritableDatabase();
      String sessionId=generateSessionId();

      //insert new session into database
      ContentValues values=new ContentValues();

      values.put(col1,userid);
      values.put(col2,sessionId);
      values.put(col3,System.currentTimeMillis());
       db.insert(tableName,null,values);
  }


    public boolean isSessionValid(long sessionId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {col2};
        String selection = col2+"=?";
        String[] selectionArgs = {String.valueOf(sessionId)};

        Cursor cursor = db.query(tableName, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();

        return count > 0;
    }

    private String generateSessionId()
    {
        return UUID.randomUUID().toString();
    }


    public boolean getSessionId() {
       // Toast.makeText(mcontext, "getSessionId Method", Toast.LENGTH_SHORT).show();
      SQLiteDatabase db =dbHelper.getReadableDatabase() ;
      String query="SELECT COUNT(*) FROM "+tableName;
      Cursor cursor=db.rawQuery(query,null);
        cursor.moveToFirst();
      return cursor.getInt(0)>0 ;



    }

    public void clearSessionData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(tableName, null, null);
        db.close();
    }

}
