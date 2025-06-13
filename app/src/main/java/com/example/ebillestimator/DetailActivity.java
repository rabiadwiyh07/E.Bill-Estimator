package com.example.ebillestimator;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    TextView textMonthYear, textKwh, textRate, textRebate, textTotal, textFinalCost;
    DBHelper dbHelper;
    int recordIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textMonthYear = findViewById(R.id.textMonthYear);
        textKwh = findViewById(R.id.textKwh);
        textRate = findViewById(R.id.textRate);
        textRebate = findViewById(R.id.textRebate);
        textTotal = findViewById(R.id.textTotal);
        textFinalCost = findViewById(R.id.textFinalCost);


        recordIndex = getIntent().getIntExtra("recordIndex", -1);
        dbHelper = new DBHelper(this);

        if (recordIndex != -1) {
            loadRecord(recordIndex);
        }

    }

    private void loadRecord(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM bills WHERE id = ?", new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            String month = cursor.getString(cursor.getColumnIndexOrThrow("month"));
            String year = cursor.getString(cursor.getColumnIndexOrThrow("year"));
            double kwh = cursor.getDouble(cursor.getColumnIndexOrThrow("kwh"));
            double rate = cursor.getDouble(cursor.getColumnIndexOrThrow("rate"));
            double rebate = cursor.getDouble(cursor.getColumnIndexOrThrow("rebate"));
            double total = cursor.getDouble(cursor.getColumnIndexOrThrow("total"));
            double finalCost = cursor.getDouble(cursor.getColumnIndexOrThrow("final_cost"));

            textMonthYear.setText("📅 " + month + " " + year);
            textKwh.setText("⚡ kWh Used: " + kwh);
            textRate.setText("💰 Rate: RM " + rate);
            textRebate.setText("🏷️ Rebate: " + rebate + "%");
            textTotal.setText("📈 Total Charges: RM " + String.format("%.2f", total));
            textFinalCost.setText("✅ Final Cost After Rebate: RM " + String.format("%.2f", finalCost));
        }

        cursor.close();
    }
}
