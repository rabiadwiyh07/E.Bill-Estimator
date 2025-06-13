package com.example.ebillestimator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    Button btnToEstimator, btnToHistory, btnToAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize buttons
        btnToEstimator = findViewById(R.id.btnToEstimator);
        btnToHistory = findViewById(R.id.btnToHistory);
        btnToAbout = findViewById(R.id.btnToAbout);

        // Set click listeners
        btnToEstimator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent estimatorIntent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(estimatorIntent);
            }
        });

        btnToHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent historyIntent = new Intent(HomeActivity.this, HistoryActivity.class);
                startActivity(historyIntent);
            }
        });

        btnToAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aboutIntent = new Intent(HomeActivity.this, AboutPageActivity.class);
                startActivity(aboutIntent);
            }
        });
    }
}
