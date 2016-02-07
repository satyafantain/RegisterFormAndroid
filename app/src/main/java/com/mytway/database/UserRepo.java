package com.mytway.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class UserRepo {
    private DBHelper dbHelper;

    public UserRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(UserTable userTable) {
        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserTable.KEY_USER_NAME, userTable.userName);
        values.put(UserTable.EMAIL,userTable.email);
        values.put(UserTable.PASSWORD, userTable.password);
        values.put(UserTable.TYPE_WORK, userTable.typeWork);
        values.put(UserTable.LENGTH_TIME_WORK, userTable.lengthTimeWork);
        values.put(UserTable.START_STANDARD_TIME, userTable.startStandardTimeWork);
        values.put(UserTable.WORK_PLACE_LATITUDE, userTable.workPlaceLatitude);
        values.put(UserTable.HOME_PLACE_LATITUDE, userTable.homePlaceLatitude);
        values.put(UserTable.HOME_PLACE_LONGITUDE, userTable.homePlaceLongitude);

        values.put(UserTable.WORK_WEEK, userTable.workWeek);

        // Inserting Row
        long user_Id = db.insert(UserTable.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) user_Id;
    }

    public void delete(int user_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(UserTable.TABLE, UserTable.KEY_ID + "= ?", new String[] { String.valueOf(user_Id) });
        db.close(); // Closing database connection
    }

    public void update(UserTable userTable) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(UserTable.KEY_USER_NAME, userTable.userName);
        values.put(UserTable.EMAIL,userTable.email);
        values.put(UserTable.PASSWORD, userTable.password);
        values.put(UserTable.TYPE_WORK, userTable.typeWork);
        values.put(UserTable.LENGTH_TIME_WORK, userTable.lengthTimeWork);
        values.put(UserTable.START_STANDARD_TIME, userTable.startStandardTimeWork);
        values.put(UserTable.WORK_PLACE_LATITUDE, userTable.workPlaceLatitude);
        values.put(UserTable.HOME_PLACE_LATITUDE, userTable.homePlaceLatitude);
        values.put(UserTable.HOME_PLACE_LONGITUDE, userTable.homePlaceLongitude);
        values.put(UserTable.WORK_WEEK, userTable.workWeek);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(UserTable.TABLE, values, UserTable.KEY_ID + "= ?", new String[] { String.valueOf(userTable.userId) });
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>> getUserList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                UserTable.KEY_ID + "," +
                UserTable.KEY_USER_NAME + "," +
                UserTable.EMAIL + "," +
                UserTable.PASSWORD + "," +
                UserTable.TYPE_WORK + "," +
                UserTable.LENGTH_TIME_WORK + "," +
                UserTable.START_STANDARD_TIME + "," +
                UserTable.WORK_PLACE_LATITUDE + "," +
                UserTable.HOME_PLACE_LATITUDE + "," +
                UserTable.WORK_WEEK + "," +
                " FROM " + UserTable.TABLE;

        //Student student = new Student();
        ArrayList<HashMap<String, String>> userTableList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> userTable = new HashMap<String, String>();
                userTable.put("id", cursor.getString(cursor.getColumnIndex(UserTable.KEY_ID)));
                userTable.put("name", cursor.getString(cursor.getColumnIndex(UserTable.KEY_USER_NAME)));
                userTableList.add(userTable);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return userTableList;
    }

    public UserTable getUserById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                UserTable.KEY_ID + "," +
                UserTable.KEY_USER_NAME + "," +
                UserTable.EMAIL + "," +
                UserTable.PASSWORD + "," +
                UserTable.TYPE_WORK + "," +
                UserTable.LENGTH_TIME_WORK + "," +
                UserTable.START_STANDARD_TIME + "," +
                UserTable.WORK_PLACE_LATITUDE + "," +
                UserTable.WORK_PLACE_LONGITUDE + "," +
                UserTable.HOME_PLACE_LATITUDE + "," +
                UserTable.HOME_PLACE_LONGITUDE + "," +
                UserTable.WORK_WEEK + "," +
                " FROM " + UserTable.TABLE
                + " WHERE " +
                UserTable.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        UserTable userTable = new UserTable();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                userTable.userId = cursor.getInt(cursor.getColumnIndex(UserTable.KEY_ID));
                userTable.userName = cursor.getString(cursor.getColumnIndex(UserTable.KEY_USER_NAME));
                userTable.password  =cursor.getString(cursor.getColumnIndex(UserTable.PASSWORD));
                userTable.typeWork  =cursor.getInt(cursor.getColumnIndex(UserTable.TYPE_WORK));

                userTable.lengthTimeWork  =cursor.getString(cursor.getColumnIndex(UserTable.LENGTH_TIME_WORK));
                userTable.startStandardTimeWork  =cursor.getString(cursor.getColumnIndex(UserTable.START_STANDARD_TIME));
                userTable.workPlaceLatitude = cursor.getDouble(cursor.getColumnIndex(UserTable.WORK_PLACE_LATITUDE));
                userTable.workPlaceLongitude = cursor.getDouble(cursor.getColumnIndex(UserTable.WORK_PLACE_LONGITUDE));
                userTable.homePlaceLatitude  = cursor.getDouble(cursor.getColumnIndex(UserTable.HOME_PLACE_LATITUDE));
                userTable.homePlaceLongitude  = cursor.getDouble(cursor.getColumnIndex(UserTable.HOME_PLACE_LONGITUDE));
                userTable.workWeek  =cursor.getString(cursor.getColumnIndex(UserTable.WORK_WEEK));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return userTable;
    }
}
