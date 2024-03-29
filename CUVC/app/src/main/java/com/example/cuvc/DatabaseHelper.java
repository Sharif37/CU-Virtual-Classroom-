package com.example.cuvc;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;




public class DatabaseHelper extends SQLiteOpenHelper {


    //table user
    public static final int version = 9;
    public static final String databaseName = "Classroom";
    public static final String tableName = "user";
    public static final String col1 = "ID";
    public static final String col2 = "Name";
    public static final String col3 = "Email";
    public static final String col4 = "PassWord";
    public static final String tableName2 = "sessions";
    public static final String col11 = "ID";


    // table sessions
    public static final String col22 = "session_id";
    public static final String col33 = "login_time";
    public static final long session_timeout = 30 * 60 * 60;
    //table classroom
    public static final String tableClassroom = "Class";
    public static final String class_Id = "classID";
    public static final String Class_Name = "ClassName";
    public static final String class_description = "Description";
    public static final String class_adminID = "AdminID";
    public static final String class_key = "key";
    // table admin
    public static final String tableAdmin = "Admin";
    public static final String admin_Id = "AdminID";
    public static final String admin_Name = "Name";
    public static final String admin_Email = "Email";


    //table train schedule

    private static final String TRAIN_SCHEDULE_fromCampus = "fromCampus_regular_train_schedule";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TRAIN_SHIFT = "train_shift";
    private static final String FromCampus = "from campus";


    private static final String TRAIN_SCHEDULE_fromCity = "fromCity_train_schedule";
    private static final String COLUMN_ID2 = "_id";
    private static final String COLUMN_TRAIN_SHIFT2 = "train_shift";
    private static final String FromCity = "from city";

    private static final String TRAIN_SCHEDULE_holiday_fromCampus = "holiday1_train_schedule";
    private static final String COLUMN_ID3 = "_id";
    private static final String COLUMN_TRAIN_SHIFT3 = "train_shift";
    private static final String FromCampusHoliday = "from campus";

    private static final String TRAIN_SCHEDULE_holiday_fromCity = "holiday2_train_schedule";
    private static final String COLUMN_ID4 = "_id";
    private static final String COLUMN_TRAIN_SHIFT4 = "train_shift";
    private static final String FromCityHoliday = "from city";


    private final Context context;
    Intent intent;
    String CreateTable = "CREATE TABLE " + tableName + "(" + col1 + " INTEGER PRIMARY KEY ," + col2 + " TEXT," + col3 + " TEXT," + col4 + " TEXT" + ")";
    String CreateTable2 = "CREATE TABLE " + tableName2 + "(" + col11 + " INTEGER  ," + col22 + " TEXT ," + col33 + " INTEGER " + ")";
    String Create_Class_table = "CREATE TABLE " + tableClassroom + "(" + class_Id + " Text primary key ," + Class_Name + " Text ," + class_description + " Text ," + class_adminID + " Text ," + class_key + "  Text  unique , " + " FOREIGN Key " + "(" + class_adminID + ")" + " REFERENCES  " + tableName + "(" + col1 + ")" + ")";
    String Create_Admin_table = "CREATE TABLE " + tableAdmin + "(" + admin_Id + " Text primary key ," + admin_Name + " Text ," + admin_Email + " Text " + ")";

    DatabaseHelper(@Nullable Context context) {

        super(context, databaseName, null, version);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateTable);
        db.execSQL(CreateTable2);
        db.execSQL(Create_Class_table);
        db.execSQL(Create_Admin_table);

        // Create the train schedule table
        String trainScheduleFromCampus = "CREATE TABLE " + TRAIN_SCHEDULE_fromCampus + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TRAIN_SHIFT + " TEXT, " +
                FromCampus + " TEXT)";

        String trainScheduleFromCity = "CREATE TABLE " + TRAIN_SCHEDULE_fromCity + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TRAIN_SHIFT + " TEXT, " +
                FromCity + " TEXT)";

        String trainScheduleHolyDayFromCampus = "CREATE TABLE " + TRAIN_SCHEDULE_holiday_fromCampus + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TRAIN_SHIFT + " TEXT," +
                FromCampusHoliday + " TEXT)";
        String trainScheduleHolidayFromCity = "CREATE TABLE " + TRAIN_SCHEDULE_fromCity + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TRAIN_SHIFT + " TEXT," +
               FromCityHoliday + " TEXT)";

        //db.execSQL(trainScheduleFromCampus);
        //db.execSQL(trainScheduleFromCity);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        db.execSQL("DROP TABLE IF EXISTS " + tableName2);
        db.execSQL("DROP TABLE IF EXISTS " + tableClassroom);
        db.execSQL("DROP TABLE IF EXISTS " + tableAdmin);


        onCreate(db);

    }

    public boolean insertUser(String id, String name, String email, String password) {
        // Get writeable database
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(col1, id);
        values.put(col2, name);
        values.put(col3, email);
        values.put(col4, password);

        // Inserting row

        long result = db.insert(tableName, null, values);
        db.close();

        return result != -1;
        // Close database connection

    }


    public boolean checkUser(String id, String password) {

        // Array of columns to fetch
        String[] projection = {col1};
        SQLiteDatabase db = this.getReadableDatabase();
        // Selection criteria
        String selection = col1 + " = ?" + " AND " + col4 + " = ?";

        // Selection arguments
        String[] selectionArgs = {id, password};


        Cursor cursor = db.query(tableName, projection, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }

    public boolean checkUserNameAndPassword(String id, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user where ID=? and PassWord=? ", new String[]{id, password});
        return cursor.getCount() > 0;
    }


    public void createClassroom(String classId, String className, String classDescription, String adminId, String key) {

        SQLiteDatabase db = this.getWritableDatabase();

        //insert new session into database
        ContentValues values = new ContentValues();

        values.put(class_Id, classId);
        values.put(Class_Name, className);
        values.put(class_description, classDescription);
        values.put(class_adminID, adminId);
        values.put(class_key, key);


        long newRowId = db.insert(tableClassroom, null, values);

        if (newRowId == -1) {
            Toast.makeText(context, "Error saving class to database", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(context, "Class created successfully", Toast.LENGTH_SHORT).show();
            // Close the activity and return to the previous one
        }
    }

    public void createAdminRole(String adminId, String adminName, String adminEmail) {
        SQLiteDatabase db = this.getWritableDatabase();

        //insert new session into database
        ContentValues values = new ContentValues();

        values.put(admin_Id, adminId);
        values.put(admin_Name, adminName);
        values.put(admin_Email, adminEmail);

        db.insert(tableAdmin, null, values);
    }



    public void InsertUserInFirebaseDatabase(String id, String name, String email, String password, boolean admin, OnDataInsertedListener listener) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


        User user = new User(id, name, email, password, false);

        databaseReference.child("users").child(id).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.onDataInserted(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onDataInserted(false);
                    }
                });
    }



    public void checkIfUserExists(String userId, OnDataInsertedListener listener) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // If the snapshot exists, the user already exists
                boolean userExists = dataSnapshot.exists();
                listener.onUserExistenceCheckComplete(userExists);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onUserExistenceCheckComplete(false);
            }
        });
    }

    /*
    public void addTrainScheduleFromCampus (TrainSchedule trainSchedule) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRAIN_SHIFT, trainSchedule.getName());
        values.put(FromCampus, trainSchedule.getFromCampus().toString());
        db.insert(TRAIN_SCHEDULE_fromCampus, null, values);
        db.close();
    }
    public void addTrainScheduleFromCity (TrainSchedule trainSchedule) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRAIN_SHIFT, trainSchedule.getName());
        values.put(FromCity, trainSchedule.getFromCampus().toString());
        db.insert(TRAIN_SCHEDULE_fromCity, null, values);
        db.close();
    }
    public void addTrainScheduleFromCampusHoliday (TrainSchedule trainSchedule) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRAIN_SHIFT, trainSchedule.getName());
        values.put(FromCampusHoliday, trainSchedule.getFromCampus().toString());
        db.insert(TRAIN_SCHEDULE_holiday_fromCampus, null, values);
        db.close();
    }

    public void addTrainScheduleFromCityHoliday (TrainSchedule trainSchedule) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRAIN_SHIFT, trainSchedule.getName());
        values.put(FromCityHoliday, trainSchedule.getFromCampus().toString());
        db.insert(TRAIN_SCHEDULE_holiday_fromCity, null, values);
        db.close();
    }
*/
  /*  @SuppressLint("Range")
    public List<TrainSchedule> getAllTrainSchedules() {
        List<TrainSchedule> trainSchedules = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TRAIN_SCHEDULE_fromCampus, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String trainName = cursor.getString(cursor.getColumnIndex(COLUMN_TRAIN_SHIFT));
                String fromCampus = cursor.getString(cursor.getColumnIndex(FromCampus));
                String fromCity = cursor.getString(cursor.getColumnIndex(FromCity));
                TrainSchedule trainSchedule = new TrainSchedule(trainName, fromCampus);
                trainSchedules.add(trainSchedule);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return trainSchedules;
    }*/


    public interface OnDataInsertedListener {
        void onDataInserted(boolean isSuccessful);

        void onUserExistenceCheckComplete(boolean exists);
    }


}