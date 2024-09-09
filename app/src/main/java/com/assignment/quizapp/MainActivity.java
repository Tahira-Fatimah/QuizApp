package com.assignment.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Question [] questions ;
    private Integer score = 0;
    private Integer currentQuestion = 0;
    private Integer totalQuestion = 20;
    private CountDownTimer countDownTimer;
    private long timeLeft = 300000;
    private Integer correctAnswers = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        loadQuestions();
        displayQuestion();
        setUpTimer();

        Button prevButton = findViewById(R.id.prev_button);
        Button nextButton = findViewById(R.id.next_button);
        Button finishButton = findViewById(R.id.finish_button);
        Button showAnswerButton = findViewById(R.id.show_answer_button);

        prevButton.setOnClickListener(v -> {
            checkAnswer();
            currentQuestion--;
            displayQuestion();
            updateButtonStates();
        });

        nextButton.setOnClickListener(v -> {
            checkAnswer();
            currentQuestion++;
            displayQuestion();
            if (currentQuestion == totalQuestion - 1) {
                goToFinalScoreScreen();
            }
            updateButtonStates();
        });

        finishButton.setOnClickListener(v -> {
            goToFinalScoreScreen();
        });

        updateButtonStates();

        showAnswerButton.setOnClickListener(v-> {
            String correctAnswer = questions[currentQuestion].getCorrectAnswer();
            Toast.makeText(MainActivity.this, "Correct Answer: " + correctAnswer, Toast.LENGTH_LONG).show();
        });


    }

    private void updateButtonStates() {
        Button prevButton = findViewById(R.id.prev_button);
        Button nextButton = findViewById(R.id.next_button);
        prevButton.setEnabled(currentQuestion != 0);
        nextButton.setEnabled(currentQuestion != totalQuestion - 1);
    }

    private void loadQuestions () {
        String[] questionsFromFile = getResources().getStringArray(R.array.questions);
        String[] correctAnswers = getResources().getStringArray(R.array.correct_answers);

        String[][] allOptions = new String[][] {
                getResources().getStringArray(R.array.options_1),
                getResources().getStringArray(R.array.options_2),
                getResources().getStringArray(R.array.options_3),
                getResources().getStringArray(R.array.options_4),
                getResources().getStringArray(R.array.options_5),
                getResources().getStringArray(R.array.options_6),
                getResources().getStringArray(R.array.options_7),
                getResources().getStringArray(R.array.options_8),
                getResources().getStringArray(R.array.options_9),
                getResources().getStringArray(R.array.options_10),
                getResources().getStringArray(R.array.options_11),
                getResources().getStringArray(R.array.options_12),
                getResources().getStringArray(R.array.options_13),
                getResources().getStringArray(R.array.options_14),
                getResources().getStringArray(R.array.options_15),
                getResources().getStringArray(R.array.options_16),
                getResources().getStringArray(R.array.options_17),
                getResources().getStringArray(R.array.options_18),
                getResources().getStringArray(R.array.options_19),
                getResources().getStringArray(R.array.options_20)
        };
        questions = new Question[totalQuestion];
        for(int i = 0; i< totalQuestion; i++) {
            questions[i] = new Question(questionsFromFile[i],allOptions[i], correctAnswers[i]);
        }
    }

    private void displayQuestion () {
        if (currentQuestion < totalQuestion) {
            TextView textView = findViewById(R.id.question);
            RadioGroup radioGroup = findViewById(R.id.answer);
            RadioButton radioButton1 = findViewById(R.id.option1);
            RadioButton radioButton2 = findViewById(R.id.option2);
            RadioButton radioButton3 = findViewById(R.id.option3);
            RadioButton radioButton4 = findViewById(R.id.option4);

            textView.setText(questions[currentQuestion].getQuestion());
            String[] options = questions[currentQuestion].getOptions();

            radioButton1.setText(options[0]);
            radioButton2.setText(options[1]);
            radioButton3.setText(options[2]);
            radioButton4.setText(options[3]);

            TextView scoreTextView = findViewById(R.id.score);
            TextView questionNumberTextView = findViewById(R.id.question_no);

            scoreTextView.setText("Score " + (score));
            questionNumberTextView.setText("Question " + (currentQuestion + 1));
            radioGroup.clearCheck();
        }
    }

    public void checkAnswer () {
        RadioGroup radioGroup = findViewById(R.id.answer);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedId);
            String selectedText = selectedRadioButton.getText().toString();
            if (selectedText.equals(questions[currentQuestion].getCorrectAnswer())) {
                score += 5;
                correctAnswers ++;
                Toast.makeText(this, "Correct !! ", Toast.LENGTH_SHORT).show();
            } else {
                score -= 1;
                Toast.makeText(this, "Wrong !! ", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show();
        }
    }

    private void setUpTimer () {
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                goToFinalScoreScreen();
            }
         }.start();

    }
    private void updateTimer() {
        TextView timerTextView = findViewById(R.id.time);
        int minutes = (int) (timeLeft / 1000) / 60;
        int seconds = (int) (timeLeft / 1000) % 60;

        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        timerTextView.setText("Time Left: " + timeLeftFormatted);
    }

    private void goToFinalScoreScreen() {
        Intent intent = new Intent(MainActivity.this, EndActivity.class);
        intent.putExtra("FINAL_SCORE", score);
        intent.putExtra("TOTAL_QUESTIONS", totalQuestion);
        intent.putExtra("CORRECT_ANSWERS", correctAnswers);
        startActivity(intent);
        finish();
    }
}