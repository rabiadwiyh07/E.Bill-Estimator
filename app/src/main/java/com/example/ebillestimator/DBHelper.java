package com.example.ebillestimator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ebill.db";
    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table to store all bill information
        String createTable = "CREATE TABLE bills (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "month TEXT," +
                "year TEXT," +
                "kwh REAL," +
                "rate REAL," +
                "rebate REAL," +              // percentage of rebate (0–5%)
                "total REAL," +               // total before rebate
                "final_cost REAL," +          // cost after rebate
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ")";
        db.execSQL(createTable); // Execute the SQL command to create the table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old table if it exists and recreate
        db.execSQL("DROP TABLE IF EXISTS bills");
        onCreate(db);
    }
}
