package com.example.ebillestimator;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    ListView listViewHistory;
    ArrayList<String> displayList;
    ArrayList<Integer> idList;
    ArrayAdapter<String> adapter;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listViewHistory = findViewById(R.id.listViewHistory);
        displayList = new ArrayList<>();
        idList = new ArrayList<>();
        dbHelper = new DBHelper(this);

        loadSummary();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        listViewHistory.setAdapter(adapter);

        listViewHistory.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(HistoryActivity.this, DetailActivity.class);
            intent.putExtra("recordIndex", idList.get(position));
            startActivity(intent);
        });

    }

    private void loadSummary() {
        displayList.clear();
        idList.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, month, final_cost FROM bills ORDER BY timestamp DESC", null);

        if (cursor.moveToFirst()) {
            do {
                int recordId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String month = cursor.getString(cursor.getColumnIndexOrThrow("month"));
                double finalCost = cursor.getDouble(cursor.getColumnIndexOrThrow("final_cost"));

                displayList.add("📅 " + month + " | 💸 RM " + String.format("%.2f", finalCost));
                idList.add(recordId);
            } while (cursor.moveToNext());
        }

        cursor.close();
    }
}
