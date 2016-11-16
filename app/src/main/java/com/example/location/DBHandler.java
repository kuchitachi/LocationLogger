package com.example.location;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by eon on 2016. 11. 16..
 */

public class DBHandler {
    private DBHelper helper;
    private SQLiteDatabase db;



    private DBHandler(Context context) {
        this.helper = new DBHelper(context);
        this.db = helper.getWritableDatabase();
    }

    public static DBHandler open(Context context) throws SQLException {
        DBHandler handler = new DBHandler(context);

        return handler;
    }

    public void close() { helper.close(); }

    public long insert(String car_name) {
        ContentValues values = new ContentValues();
        values.put("car_name", car_name);

        return db.insert("cars", null, values);
    }

    public Cursor select(int id) throws SQLException {
        Cursor cursor = db.query(true, "cars", new String[] {"_id", "car_name" }, "_id" +
        "=" + id, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

}
