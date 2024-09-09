package com.assignment.quizapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_end);

        TextView finalScoreTextView = findViewById(R.id.finalScore);
        TextView questionAttemptedTextView = findViewById(R.id.questionsAttempted);

        int finalScore = getIntent().getIntExtra("FINAL_SCORE", 0);
        finalScoreTextView.setText("Final Score: " + finalScore);

        int questionAttempted = getIntent().getIntExtra("CORRECT_ANSWERS", 0);
        int totalQuestions = getIntent().getIntExtra("TOTAL_QUESTIONS", 0);
        questionAttemptedTextView.setText("Correct Answers: " + questionAttempted + "/" + totalQuestions);
    }
}