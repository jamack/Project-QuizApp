package com.example.android.project_quizapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // The following code creates the trivia questions & answers by instantiating one of (3) different objects.
    // Each one of these objects (TextTrivia, RadiobutTrivia, & CheckboxTrivia) corresponds to a different answer type.
    // These subclasses all inherit from the TriviaEntry superclass.

    TextTrivia questionNo01 = new TextTrivia(1, "Who is the coolest kid in the entire world?", "Colin");

    // These global variables will be initialized in the onCreate method, once the layout has been inflated
    private TextView qmText; // Holds a reference to the question/message card text, for easier usage
    private TextView aiText; // Holds a reference to the answer/instructions card text, for easier usage

    // This global variable will keep track of which question/answer pairing is "loaded" at any given moment
    private int currentQuestionNum = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Once the XML layout is inflated, fetch a URI Id for the question/message card and assign it to the qmText global variable
        qmText = (TextView) findViewById(R.id.question_message_card_text);
        aiText = (TextView) findViewById(R.id.answer_instructions_card_text);
    }

    // This method is called by the "Got it. Let's Go" button on the introduction screen.
    // It populates the first question & answer and swaps out the buttons.
    public void launchQuiz(View view) {

        // Swap out the buttons
        findViewById(R.id.lets_go_button).setVisibility(View.GONE);
        findViewById(R.id.skip_button).setVisibility(View.VISIBLE);
        findViewById(R.id.confirm_answer_button).setVisibility(View.VISIBLE);

        // Load the first question & answer
        qmText.setText(questionNo01.questionText);
//        aiText.setText((questionNo01.getCorrectAnswer()));
    }




}
