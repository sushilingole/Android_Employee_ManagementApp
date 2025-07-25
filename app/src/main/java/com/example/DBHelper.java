package com.example;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    // DB & Table Info
    public static final String DBName = "EmpLoginSystem";
    public static final String TABLE_NAME = "employees";
    public static final String ADMIN_TABLE_NAME = "admin_credentials";
    public static final int Version = 3;

    public DBHelper(Context context) {
        super(context, DBName, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Employee Table
        String query = "CREATE TABLE " + TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "address TEXT," +
                "mobile TEXT," +
                "username TEXT UNIQUE," +
                "password TEXT," +
                "status TEXT DEFAULT 'Pending')";

        db.execSQL(query);

        // Create Admin Table
        String query1 = "CREATE TABLE " + ADMIN_TABLE_NAME +
                "(adminId TEXT," +
                "adminPassword TEXT)";
        db.execSQL(query1);

        // Insert default admin credentials
        ContentValues values = new ContentValues();
        values.put("adminId", "saurabh@123");
        values.put("adminPassword", "123");
        db.insert(ADMIN_TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ADMIN_TABLE_NAME);
        onCreate(db);
    }

    // Insert Employee with default status 'Pending'
    public boolean insertEmployee(String name, String address, String mobile, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("address", address);
        values.put("mobile", mobile);
        values.put("username", username);
        values.put("password", password);
        values.put("status", "Pending"); // default status
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    // Employee Login Validation
    public boolean checkEmployeeLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE username=? AND password=?", new String[]{username, password});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    // Admin Login Validation
    public boolean checkAdminLogin(String AdminUsername, String AdminPassword) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ADMIN_TABLE_NAME + " WHERE adminId=? AND adminPassword=?", new String[]{AdminUsername, AdminPassword});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    // Get Employee by Username
    public Cursor getEmployeeByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE username=?", new String[]{username});
    }

    // Update Employee Profile Info (name, address, mobile, password)
    public boolean updateEmployee(String username, String name, String address, String mobile, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("address", address);
        values.put("mobile", mobile);
        values.put("password", password);
        int result = db.update(TABLE_NAME, values, "username = ?", new String[]{username});
        return result > 0;
    }

    // Update Employee Status (Approve or Reject)
    public boolean updateEmployeeStatus(int id, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", newStatus);
        int result = db.update(TABLE_NAME, values, "id = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    // Get All Employees (including status)
    public Cursor getAllEmployees() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    //Updating Employee Status :
    public boolean updateStatus(int id, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("status", newStatus);
        int result = db.update("employees", cv, "id=?", new String[]{String.valueOf(id)});
        return result > 0;
    }


    // Delete employee
    public boolean deleteEmployeeById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }
}
