package com.example.jeudusimon;

import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button boutonPlay, boutonQuit;
    TextView tvHighScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        boutonPlay = findViewById(R.id.buttonPlay);
        boutonQuit = findViewById(R.id.buttonQuit);
        tvHighScores = findViewById(R.id.tvScores);

        loadHighScores();
    }

    public void Quitter(View v) {
        finish();
    }

    public void Jouer(View v) {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(intent);
    }

    private void loadHighScores() {
        SharedPreferences prefs = getSharedPreferences("highscores", MODE_PRIVATE);

        StringBuilder highScoresText = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            int score = prefs.getInt("score" + i, 0);
            String name = prefs.getString("name" + i, "");
            highScoresText.append((i + 1) + ". " + name + " : " + score + "\n");
        }
        tvHighScores.setText(highScoresText.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadHighScores();
    }
}
