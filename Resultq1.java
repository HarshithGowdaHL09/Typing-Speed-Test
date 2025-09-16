package com.example.homeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Resultq1 extends AppCompatActivity {
    TextView resultWPM, resultErrors, resultAccuracy;
    Button restartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultWPM = findViewById(R.id.resultWPM);
        resultErrors = findViewById(R.id.resultErrors);
        resultAccuracy = findViewById(R.id.resultAccuracy);
        restartBtn = findViewById(R.id.restartBtn);

        int wpm = getIntent().getIntExtra("wpm", 0);
        int errors = getIntent().getIntExtra("errors", 0);
        int accuracy = getIntent().getIntExtra("accuracy", 0);

        resultWPM.setText("WPM: " + wpm);
        resultErrors.setText("Errors: " + errors);
        resultAccuracy.setText("Accuracy: " + accuracy + "%");

        restartBtn.setOnClickListener(v -> {
            Intent i = new Intent(Resultq1.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        });
    }
}
