package com.example.android.project_quizapp;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public EditText aiEditText; // Holds a reference to the EditText view in the answer/instructions card text
    // These global variables will serve as shortcuts to the various Views. (Instead of using findViewById every time).
    // will be initialized in the onCreate method, once the layout has been inflated.
    protected CardView aiCard; // Holds a reference to the answer/instructions CardView container
    // The following code creates an array of TriviaEntry objects (superclass) and initializes the array with (10) trivia questions & answers
    // by instantiating one of (3) different subclass objects, via their constructors.
    // Each one of these objects (TextTrivia, RadiobutTrivia, & CheckboxTrivia) corresponds to a different answer type.
    // These subclasses all inherit from the TriviaEntry superclass.
    TriviaEntry[] questionArray = new TriviaEntry[]{
            new TextTrivia("Who is the coolest kid in the entire world?", "Colin"),
            new TextTrivia("Who is the best wife in the entire world?", "Liz"),
            new TextTrivia("Who is the greatest pooch in the entire world?", "Super")
    };
    private TextView qmText; // Holds a reference to the question/message card text
    private TextView aiText; // Holds a reference to the answer/instructions card text
    // This global variable will keep track of which question/answer pairing is currently loaded or to find another question/answer.
    private int questionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Once the XML layout is inflated, fetch a URI Id for the question/message card and assign it to the qmText global variable
        aiCard = (CardView) findViewById(R.id.answer_instructions_card);
        qmText = (TextView) findViewById(R.id.question_message_card_text);
        aiText = (TextView) findViewById(R.id.answer_instructions_card_text);
        aiEditText = (EditText) findViewById(R.id.answer_instructions_card_edittext);


    }

    // This method is called by the "Got it. Let's Go" button on the introduction screen.
    // It populates the first question & answer(s) and swaps out the buttons.
    public void launchQuiz(View view) {
        // Swap out the buttons
        findViewById(R.id.lets_go_button).setVisibility(View.GONE);
        findViewById(R.id.skip_button).setVisibility(View.VISIBLE);
        findViewById(R.id.confirm_answer_button).setVisibility(View.VISIBLE);

        // Switch visibility of the instructions TextView to GONE, to make room for the first question
        aiText.setVisibility(View.GONE);

        // Call the nextQuestion method to load the first question
        nextQuestion();
    }

    // This method is called by the "Confirm Answer!" button on a typical question screen.
    // It processes the user's answer for the current question & loads the next question & answer(s).
    public void answerQuestion(View view) {
        //TODO determine which type of question has been answered & route the logic accordingly
        if (questionArray[questionIndex] instanceof TextTrivia) {
            // Process the TextTrivia answer type...
            String userAnswer = aiEditText.getText().toString();
            TextTrivia castTriviaEntry = (TextTrivia) questionArray[questionIndex]; // Downcast the TriviaEntry object to a TextTrivia object. (Class already checked!).
            castTriviaEntry.submitAnswer(userAnswer);
            aiEditText.setText("");
        }
//        else if(questionArray[questionIndex] instanceof CheckboxTrivia) {
//            //TODO code to process the CheckboxTrivia answer type...
//        }

//        else if(questionArray[questionIndex] instanceof RadiobutTrivia) {
//            //TODO code to process the RadiobutTrivia answer type...
//        }

        // Change icon color to blue
        int myColor = ContextCompat.getColor(this, R.color.blue_700);
//                int myColor = getResources().getColor(R.color.blue_700);
        fetchIconViewId().setCardBackgroundColor(myColor);

        //TODO check whether all questions have now been viewed
//         TEMP - for testing. Remove once rest of code is working...
        Log.v("MainActivity.java", "The value of questionArray.length is: " + Integer.toString(questionArray.length));
        Log.v("MainActivity.java", "The value of questionIndex at current question is: " + Integer.toString(questionIndex));
//      TESTING - CHANGE THE COMPARISON OPERATOR BACK TO "=="
        if (questionIndex > questionArray.length - 1) {
            //TODO Launch the Submit Answers screen/method/etc.
        } else {
            // Increment questionIndex
            questionIndex += 1;
            // Call the nextQuestion method to load the next question
            nextQuestion();

        }
    }

    // This method is called by other methods. (launchQuiz, answerQuestion, etc.)
    // It will load the next unanswered question and populate the question & answer(s).
    public void nextQuestion() {
        // Mark question as viewed. Sets the TriviaEntry object's wasViewed variable to 'true' via its associated method
        questionArray[questionIndex].questionViewed();

        // Load question text from the current trivia entry into the miCard TextView
        qmText.setText(questionArray[questionIndex].questionText);

        //TODO load answer struxture/text from the current trivia entry into the aiCard container
        //TODO determine which type of question has been answered & route the logic accordingly
        // Process the TextTrivia answer type...
        if (questionArray[questionIndex] instanceof TextTrivia) {
            aiEditText.setVisibility(View.VISIBLE);
        }
//        else if(questionArray[questionIndex] instanceof CheckboxTrivia) {
//            //TODO code to process the CheckboxTrivia answer type...
//        }

//        else if(questionArray[questionIndex] instanceof RadiobutTrivia) {
//            //TODO code to process the RadiobutTrivia answer type...
//        }

    }

    protected CardView fetchIconViewId() {
        // questionIndex is targeted at an array and starts with 0. To be clearer which icon we're targeting, add 1 to the value for this switch
        switch (questionIndex + 1) {
            case 1:
                return (CardView) findViewById(R.id.icon_q1);
            case 2:
                return (CardView) findViewById(R.id.icon_q2);
            case 3:
                return (CardView) findViewById(R.id.icon_q3);
            case 4:
                return (CardView) findViewById(R.id.icon_q4);
            case 5:
                return (CardView) findViewById(R.id.icon_q5);
            case 6:
                return (CardView) findViewById(R.id.icon_q6);
            case 7:
                return (CardView) findViewById(R.id.icon_q7);
            case 8:
                return (CardView) findViewById(R.id.icon_q8);
            case 9:
                return (CardView) findViewById(R.id.icon_q9);
            case 10:
                return (CardView) findViewById(R.id.icon_q10);
        }
        return (CardView) findViewById(R.id.icon_q1); // WHY DO I NEED THIS LINE IF ALL ITEMS ABOVE RETURN A VALUE?
    }

}
