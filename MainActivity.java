package com.example.homeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {
    RadioGroup timeGroup;
    Button startBtn;
    Switch themeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeGroup = findViewById(R.id.timeGroup);
        startBtn = findViewById(R.id.startBtn);
        themeSwitch = findViewById(R.id.themeSwitch);

        startBtn.setOnClickListener(v -> {
            int selectedId = timeGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Please select a test duration!", Toast.LENGTH_SHORT).show();
                return;
            }

            int duration = 15;
            if (selectedId == R.id.time15) duration = 15;
            else if (selectedId == R.id.time30) duration = 30;
            else if (selectedId == R.id.time60) duration = 60;

            Intent intent = new Intent(MainActivity.this, TypingActivity.class);
            intent.putExtra("duration", duration);
            startActivity(intent);
        });

        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            AppCompatDelegate.setDefaultNightMode(
                    isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
            );
        });
    }
}
