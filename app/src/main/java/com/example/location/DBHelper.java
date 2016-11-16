package com.example.location;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by eon on 2016. 11. 16..
 */

public class DBHelper extends SQLiteOpenHelper{

    public DBHelper(Context context) { super(context, "pkpk.db", null, 1);}

    public void onCreate(SQLiteDatabase db) {
        String table = "CREATE TABLE cars (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "car_name TEXT NOT NULL)";

        db.execSQL(table);

    }

    //개발자는 기존 데이터베이스를 변경하는 코드를 작성해서 기존 사용자의 데이터를 유지할수 있도록 해야합니다. 이렇게 처리할 수 있도록 해주는 것이 바로 onUpgrade() 메소드입니다.
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS cars");
        onCreate(db);
    }
}
