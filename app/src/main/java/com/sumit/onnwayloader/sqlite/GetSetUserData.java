package com.sumit.onnwayloader.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
*This class is created to store the user data in the sqlite database.
* This is done to fetch the user data at the time of login from the remote database and store it into the local DB
* Also, by using this data of the local DB, we are checking if the user is already logged in or not.
* Here SharedPreference is not used.
*/
public class GetSetUserData extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "userinfo.db";
    public static final String TABLE_NAME = "userinfo_table";
    public static final String COL_1 = "MOBILE"; // to store user mobile number
    public static final String COL_2 = "TYPE"; // to  store user type i.e. Individual or Company
    public static final String COL_3 = "NAME"; //name of the user
    public static final String COL_4 = "ADDRESS"; // exact address of the user
    public static final String COL_5 = "CITY"; // city of the user
    public static final String COL_6 = "EMAIL"; // email of the user

    public GetSetUserData(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //creating a table in the database
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " (MOBILE TEXT, TYPE TEXT, NAME TEXT, ADDRESS TEXT, CITY TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String mobile, String type, String name, String address, String city, String email) {
        //insert data into the table
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, mobile);
        contentValues.put(COL_2, type);
        contentValues.put(COL_3, name);
        contentValues.put(COL_4, address);
        contentValues.put(COL_5, city);
        contentValues.put(COL_5, email);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1) {
            //data not inserted
            return false;
        } else {
            //data inserted in the table
            return true;
        }

    }

    public Cursor getAllData() {
        //return the data of the database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public boolean updateData(String mobile, String type, String name, String address, String city, String email) {
        //update the data of the database using the mobile number as the conditional statement
        //used when the user requests the admin to update his/her name or mobile or address or anything else
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, mobile);
        contentValues.put(COL_2, type);
        contentValues.put(COL_3, name);
        contentValues.put(COL_4, address);
        contentValues.put(COL_5, city);
        contentValues.put(COL_6, email);
        db.update(TABLE_NAME, contentValues, "MOBILE = ?", new String[] { mobile });
        return true;
    }

    public Integer deleteData (String mobile) {
        //delete data from the DB. This is used to delete the data from the table whenever the user logs out.
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "MOBILE = ?", new String[] { mobile });
    }
}
