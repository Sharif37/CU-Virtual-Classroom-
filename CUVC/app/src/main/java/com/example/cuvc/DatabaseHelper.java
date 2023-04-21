package com.example.cuvc ;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    //table user
    public static final int version=8 ;
    public static final String databaseName="Classroom";
    public static final String tableName="user";
    public static final String col1="ID" ;
    public static final String col2="Name" ;
    public static final String col3="Email" ;
    public static final String col4="PassWord" ;


// table sessions


    public static final String tableName2="sessions";

    public static final String col11="ID" ;
    public static final String col22="session_id" ;
    public static final String col33="login_time" ;
    public static final long   session_timeout=30*60*60 ;


    //table classroom
    public static final String tableClassroom ="Class";
    public static final String  class_Id ="classID";
    public static final String  Class_Name ="ClassName";
    public static final String  class_description ="Description";
    public static final String  class_adminID ="AdminID";
    public static final String  class_key ="key";



    // table admin
    public static final String tableAdmin="Admin" ;
    public static final String admin_Id="AdminID" ;

    public static final String admin_Name="Name" ;
    public static final String admin_Email="Email" ;


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


    String Create_Class_table="CREATE TABLE "+ tableClassroom + "(" + class_Id + " Text primary key ,"+
             Class_Name + " Text ," + class_description + " Text ," + class_adminID +" Text ," + class_key +"  Text  unique , "+ " FOREIGN Key "+ "(" + class_adminID +")"+
            " REFERENCES  "+ tableName + "("+ col1 + ")" +")" ;


    String Create_Admin_table="CREATE TABLE "+ tableAdmin+ "(" + admin_Id + " Text primary key ,"+
         admin_Name + " Text ," + admin_Email + " Text "+ ")" ;

      DatabaseHelper(@Nullable Context context) {

          super(context, databaseName, null, version);
          this.context=context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateTable);
        db.execSQL(CreateTable2);
        db.execSQL(Create_Class_table);
        db.execSQL(Create_Admin_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+tableName);
        db.execSQL("DROP TABLE IF EXISTS "+tableName2);
        db.execSQL("DROP TABLE IF EXISTS "+tableClassroom);
        db.execSQL("DROP TABLE IF EXISTS "+tableAdmin);

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


    public void createClassroom(String  classId,String className,String classDescription, String adminId,String key)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        //insert new session into database
        ContentValues values=new ContentValues();

        values.put(class_Id,classId);
        values.put(Class_Name,className);
        values.put(class_description,classDescription);
        values.put(class_adminID,adminId);
        values.put(class_key,key);



        long newRowId = db.insert(tableClassroom,null,values);

        if (newRowId == -1) {
            Toast.makeText( context, "Error saving class to database", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(context, "Class created successfully", Toast.LENGTH_SHORT).show();
             // Close the activity and return to the previous one
        }
    }

    public void createAdminrule(String  adminId,String adminName,String adminEmail)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        //insert new session into database
        ContentValues values=new ContentValues();

        values.put(admin_Id,adminId);
        values.put(admin_Name,adminName);
        values.put(admin_Email,adminEmail);

        db.insert(tableAdmin,null,values);
    }









}