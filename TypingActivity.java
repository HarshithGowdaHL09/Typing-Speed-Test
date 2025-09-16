package com.example.homeactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class TypingActivity extends AppCompatActivity {

    TextView timerText, sampleText;
    EditText inputBox;
    CountDownTimer timer;
    int duration;

    String[] typingSamples = {
           "Success doesn’t happen overnight. It’s built through hard work, dedication, and learning from failures. Every challenge is a chance to grow. Stay focused and patient. Keep showing up daily. In time, your consistent efforts will lead to success you’ll be proud of. Trust the process and keep moving forward.",
            "Typing faster comes with consistent practice. Focus on posture and finger placement. Avoid looking at the keyboard. Over time, muscle memory builds speed and accuracy. Practice with different texts and take breaks to stay fresh. Accuracy matters as much as speed. Track your progress and enjoy improving every day.\n" +
                    "\n",
            "Small steps lead to big changes. Progress might feel slow, but consistency matters. Keep going, even when results aren't instant. Your dedication builds habits, and habits shape your future. Don’t wait for perfect conditions. Start now, adjust as you go, and trust that effort will bring results over time.\n" +
                    "\n",
            "Confidence grows through action. The more you try, the less you fear. Failure teaches better than success. Take that first step, no matter how unsure. You don’t need to be perfect to begin. Just begin. Growth isn’t instant, but every effort adds up to something meaningful and lasting."
    };

    String originalText;
    int totalTypedWords = 0;
    int totalCorrectWords = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typing);

        timerText = findViewById(R.id.timerText);
        sampleText = findViewById(R.id.sampleText);
        inputBox = findViewById(R.id.inputBox);

        duration = getIntent().getIntExtra("duration", 30);
        loadNewQuote();

        inputBox.setEnabled(true);

        inputBox.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateColoredText(s.toString());

                // Check if quote is finished
                if (s.toString().length() >= originalText.length()) {
                    processQuote(s.toString());
                    loadNewQuote();
                    inputBox.setText("");
                }
            }
        });

        startCountdown();
    }

    private void loadNewQuote() {
        originalText = typingSamples[new Random().nextInt(typingSamples.length)];
        sampleText.setText(originalText);
    }

    private void updateColoredText(String typedText) {
        SpannableStringBuilder colored = new SpannableStringBuilder(originalText);
        int minLength = Math.min(typedText.length(), originalText.length());

        for (int i = 0; i < minLength; i++) {
            if (typedText.charAt(i) == originalText.charAt(i)) {
                colored.setSpan(new ForegroundColorSpan(Color.GREEN), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                colored.setSpan(new ForegroundColorSpan(Color.RED), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        if (typedText.length() > originalText.length()) {
            SpannableStringBuilder combined = new SpannableStringBuilder(colored);
            int extraStart = originalText.length();
            String extra = typedText.substring(extraStart);
            SpannableStringBuilder extraSpannable = new SpannableStringBuilder(extra);
            extraSpannable.setSpan(new ForegroundColorSpan(Color.YELLOW), 0, extra.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            combined.append(extraSpannable);
            sampleText.setText(combined);
        } else {
            sampleText.setText(colored);
        }
    }

    private void processQuote(String typed) {
        String[] typedWords = typed.trim().split("\\s+");
        String[] originalWords = originalText.trim().split("\\s+");

        int correct = 0;
        for (int i = 0; i < Math.min(typedWords.length, originalWords.length); i++) {
            if (typedWords[i].equals(originalWords[i])) {
                correct++;
            }
        }

        totalTypedWords += typedWords.length;
        totalCorrectWords += correct;
    }

    private void startCountdown() {
        timer = new CountDownTimer(duration * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerText.setText("Time Left: " + millisUntilFinished / 1000 + "s");
            }

            public void onFinish() {
                inputBox.setEnabled(false);

                // ✅ Process whatever is typed even if quote isn't finished
                String typed = inputBox.getText().toString();
                if (!typed.isEmpty()) {
                    processQuote(typed);
                }

                calculateResults();
            }
        }.start();
    }


    private void calculateResults() {
        int errors = totalTypedWords - totalCorrectWords;
        int wpm = (int) ((totalTypedWords * 60.0) / duration);
        int accuracy = totalTypedWords == 0 ? 0 : (totalCorrectWords * 100 / totalTypedWords);

        Intent resultIntent = new Intent(this, Resultq1.class);
        resultIntent.putExtra("wpm", wpm);
        resultIntent.putExtra("errors", errors);
        resultIntent.putExtra("accuracy", accuracy);
        startActivity(resultIntent);
        finish();
    }
}
