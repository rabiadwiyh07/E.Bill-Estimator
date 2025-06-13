package com.example.ebillestimator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AboutPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);

        // Set the clickable GitHub URL
        TextView urlTextView = findViewById(R.id.textGithubUrl);
        urlTextView.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/yourusername/ebill-estimator"));
            startActivity(browserIntent);
        });

    }
}
