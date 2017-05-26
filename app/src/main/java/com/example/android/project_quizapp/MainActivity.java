package com.example.android.project_quizapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // The following code creates the trivia questions & answers by instantiating one of (3) different objects.
    // Each one of these objects (TextTrivia, RadiobutTrivia, & CheckboxTrivia) corresponds to a different answer type.
    // These subclasses all inherit from the TriviaEntry superclass.

    TextTrivia questionNo01 = new TextTrivia(1, "Who is the coolest kid in the entire world?", "Colin");

    // This method is called by the "Got it. Let's Go" button on the introduction screen.
    // It populates the first question & answer and swaps out the buttons.
    public void launchQuiz(View view) {

        findViewById(R.id.lets_go_button).setVisibility(View.GONE);
        findViewById(R.id.skip_button).setVisibility(View.VISIBLE);
        findViewById(R.id.confirm_answer_button).setVisibility(View.VISIBLE);
    }




}
