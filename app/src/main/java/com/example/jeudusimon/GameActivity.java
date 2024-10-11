package com.example.jeudusimon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    Button boutonRouge, boutonBleu, boutonVert, boutonJaune;
    TextView tvScore;
    List<Integer> sequence = new ArrayList<>();
    int score = 0;
    boolean playerTurn = false;
    int sequenceIndex = 0;

    SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        boutonBleu = findViewById(R.id.buttonBlue);
        boutonRouge = findViewById(R.id.buttonRed);
        boutonVert = findViewById(R.id.buttonGreen);
        boutonJaune = findViewById(R.id.buttonYellow);

        tvScore = findViewById(R.id.textViewScore);

        boutonRouge.setOnClickListener(v -> handleButtonClick(0));
        boutonBleu.setOnClickListener(v -> handleButtonClick(1));
        boutonVert.setOnClickListener(v -> handleButtonClick(2));
        boutonJaune.setOnClickListener(v -> handleButtonClick(3));

        // Initialiser SoundManager et ajouter les sons
        soundManager = new SoundManager();
        soundManager.initSounds(this);
        soundManager.addSound(0, R.raw.son1);
        soundManager.addSound(1, R.raw.son2);
        soundManager.addSound(2, R.raw.son3);
        soundManager.addSound(3, R.raw.son4);
        soundManager.addSound(4, R.raw.error);

        startGame();
    }

    private void startGame() {
        sequence.clear();
        score = 0;
        tvScore.setText(String.valueOf(score));
        playerTurn = false;
        sequenceIndex = 0;
        generateNextSequence();
        playSequence();
    }

    private void generateNextSequence() {
        Random random = new Random();
        sequence.add(random.nextInt(4)); // Ajoutez une couleur aléatoire à la séquence
    }

    private void playSequence() {
        playerTurn = false; // Ne laissez pas le joueur jouer pendant que la séquence est jouée
        new Handler().postDelayed(() -> {
            showSequence(0);
        }, 1000); // Ajoutez un délai avant de jouer la séquence
    }

    private void showSequence(final int index) {
        if (index < sequence.size()) {
            Button button = getButtonByColorIndex(sequence.get(index));
            soundManager.playSound(sequence.get(index)); // Jouer le son associé
            button.setAlpha(0.5f);
            new Handler().postDelayed(() -> {
                button.setAlpha(1f);
                new Handler().postDelayed(() -> {
                    showSequence(index + 1);
                }, 1000); // Délai de 1 seconde entre chaque bouton dans la séquence
            }, 500); // Temps pendant lequel le bouton est éclairé
        } else {
            playerTurn = true;
            Toast.makeText(this, "À votre tour !", Toast.LENGTH_SHORT).show();
        }
    }

    private Button getButtonByColorIndex(int colorIndex) {
        switch (colorIndex) {
            case 0:
                return boutonRouge;
            case 1:
                return boutonBleu;
            case 2:
                return boutonVert;
            case 3:
                return boutonJaune;
            default:
                return null;
        }
    }

    private void handleButtonClick(int colorIndex) {
        if (!playerTurn) return;
        soundManager.playSound(colorIndex); // Jouer le son associé au bouton cliqué
        if (sequence.get(sequenceIndex) == colorIndex) {
            sequenceIndex++;
            if (sequenceIndex >= sequence.size()) {
                score++;
                tvScore.setText(String.valueOf(score));
                generateNextSequence();
                sequenceIndex = 0;
                playSequence();
            }
        } else {
            soundManager.playSound(4); // Jouer le son d'erreur
            showGameOverDialog();
        }
    }

    private void showGameOverDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Over");
        builder.setMessage("Vous avez perdu. Entrez votre nom:");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String playerName = input.getText().toString();
            saveScore(playerName, score);
            sendResultToMainActivity(playerName, score);
        });

        builder.setCancelable(false);
        builder.show();
    }

    private void saveScore(String playerName, int score) {
        SharedPreferences prefs = getSharedPreferences("highscores", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Récupérer les scores actuels
        int[] highScores = new int[3];
        String[] highNames = new String[3];
        for (int i = 0; i < 3; i++) {
            highScores[i] = prefs.getInt("score" + i, 0);
            highNames[i] = prefs.getString("name" + i, "");
        }

        // Insérer le nouveau score si c'est un des trois meilleurs
        for (int i = 0; i < 3; i++) {
            if (score > highScores[i]) {
                for (int j = 2; j > i; j--) {
                    highScores[j] = highScores[j - 1];
                    highNames[j] = highNames[j - 1];
                }
                highScores[i] = score;
                highNames[i] = playerName;
                break;
            }
        }

        // Sauvegarder les scores mis à jour
        for (int i = 0; i < 3; i++) {
            editor.putInt("score" + i, highScores[i]);
            editor.putString("name" + i, highNames[i]);
        }

        editor.apply();
    }

    private void sendResultToMainActivity(String playerName, int score) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("PLAYER_NAME", playerName);
        resultIntent.putExtra("SCORE", score);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
