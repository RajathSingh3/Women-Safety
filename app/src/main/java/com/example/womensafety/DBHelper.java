package com.example.womensafety;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "ContactDetails.db",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table ContactDetails(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phoneno INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists ContactDetails");
        onCreate(db);
    }

    public Boolean insertUserData(String name, String phno) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("phoneno",phno);
        long result = db.insert("ContactDetails", null, contentValues);
        if (result == -1){
            return false;
        }
        else{
            return  true;
        }
    }

    public Boolean updateUserData(String oldphno, String name, String phno) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("phoneno",phno);
        Cursor cursor = db.rawQuery("select * from ContactDetails where phoneno=?",new String[]{oldphno});
        if (cursor.getCount()>0){
            long result = db.update("ContactDetails", contentValues, "phoneno=?",new String[]{oldphno});
            if (result == -1){
                return false;
            }
            else{
                return  true;
            }
        }
        else{
            return false;
        }
    }

    public Boolean deleteUserData( String phno) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from ContactDetails where phoneno=?",new String[]{phno});
        if (cursor.getCount()>0){
            long result = db.delete("ContactDetails","phoneno=?",new String[]{phno});
            if (result == -1){
                return false;
            }
            else{
                return  true;
            }
        }
        else{
            return false;
        }
    }

    public Cursor getUserData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from ContactDetails", null);
        return cursor;
        }

    public int getContact(String phoneNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from ContactDetails WHERE phoneNo=" + phoneNo, null);
        int noOfContacts = cursor.getCount();
        cursor.close();
        return noOfContacts;
    }

    public int getName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from ContactDetails WHERE name=" +name,null);
        int noOfContacts = cursor.getCount();
        cursor.close();
        return noOfContacts;
    }
}
