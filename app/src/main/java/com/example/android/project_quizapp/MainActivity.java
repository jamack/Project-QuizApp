package com.example.android.project_quizapp;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import static android.util.Log.v;

public class MainActivity extends AppCompatActivity {

    protected LinearLayout iconBar; // Holds a reference to the LinearLayout container for the question icons
    protected CardView aiCard; // Holds a reference to the answer/instructions CardView container
    protected EditText aiEditText; // Holds a reference to the EditText view in the answer/instructions card text
    protected View aiCheckBoxContainer; // Holds a reference to the answer/instructions card LinearLayout that contains the CheckBox views.
    protected CheckBox aiCheckBox01; // Holds a reference to first CheckBox answer in the answer/instructions card LinearLayout container group.
    protected CheckBox aiCheckBox02; // Holds a reference to second CheckBox answer in the answer/instructions card LinearLayout container group.
    protected CheckBox aiCheckBox03; // Holds a reference to third CheckBox answer in the answer/instructions card LinearLayout container group.
    protected CheckBox aiCheckBox04; // Holds a reference to fourth CheckBox answer in the answer/instructions card LinearLayout container group.
    // The following code creates an array of TriviaEntry objects (superclass) and initializes the array with (10) trivia questions & answers
    // by instantiating one of (3) different subclass objects, via their constructors.
    // Each one of these objects (TextTrivia, RadiobutTrivia, & CheckboxTrivia) corresponds to a different answer type.
    // These subclasses all inherit from the TriviaEntry superclass.
    TriviaEntry[] questionArray = new TriviaEntry[]{
            new TextTrivia("Who is the best wife in the entire world?", "Liz"),
            new RadiobutTrivia("Which river is the longest?", "Tigris", "Congo", "Danube", "Colorado", 2),
            new CheckboxTrivia("Which of the following are fruit?", "carrot", "kiwi", "tomato", "buddha's claw", false, true, true, true)
    };
    // These global variables will serve as shortcuts to the various Views. (Instead of using findViewById every time).
    // will be initialized in the onCreate method, once the layout has been inflated.
    private TextView qmText; // Holds a reference to the question/message card text
    private TextView aiText; // Holds a reference to the answer/instructions card text
    private RadioGroup aiRadioGroup; // Holds a reference to the answer/instructions card RadioGroup (container for the Radio Buttons).
    private RadioButton aiRadioButton01; // Holds a reference to the first RadioButton answer in the answer/instructions card RadioGroup.
    private RadioButton aiRadioButton02; // Holds a reference to the second RadioButton answer in the answer/instructions card RadioGroup.
    private RadioButton aiRadioButton03; // Holds a reference to the third RadioButton answer in the answer/instructions card RadioGroup.
    private RadioButton aiRadioButton04; // Holds a reference to the fourth RadioButton answer in the answer/instructions card RadioGroup.

    // This global variable will track whether it is a user's first time through the questions ('true') or whether they are revisiting skipped questions ('false').
    private boolean isFirstPass = true;

    // This global variable will keep track of which question/answer pairing is currently loaded or to find another question/answer.
    private int questionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Once the XML layout is inflated, fetch a URI Id for the question/message card and assign it to the qmText global variable
        iconBar = (LinearLayout) findViewById(R.id.icon_bar);
        aiCard = (CardView) findViewById(R.id.answer_instructions_card);
        qmText = (TextView) findViewById(R.id.question_message_card_text);
        aiText = (TextView) findViewById(R.id.answer_instructions_card_text);
        aiEditText = (EditText) findViewById(R.id.answer_instructions_card_edittext);
        aiCheckBoxContainer = findViewById(R.id.answer_instructions_card_checkboxes);
        aiCheckBox01 = (CheckBox) findViewById(R.id.checkbox_answer_01);
        aiCheckBox02 = (CheckBox) findViewById(R.id.checkbox_answer_02);
        aiCheckBox03 = (CheckBox) findViewById(R.id.checkbox_answer_03);
        aiCheckBox04 = (CheckBox) findViewById(R.id.checkbox_answer_04);
        aiRadioGroup = (RadioGroup) findViewById(R.id.answer_instructions_card_radiobuttons);
        aiRadioButton01 = (RadioButton) findViewById(R.id.radiobutton_answer_01);
        aiRadioButton02 = (RadioButton) findViewById(R.id.radiobutton_answer_02);
        aiRadioButton03 = (RadioButton) findViewById(R.id.radiobutton_answer_03);
        aiRadioButton04 = (RadioButton) findViewById(R.id.radiobutton_answer_04);
    }

    // This method is called by the "Got it. Let's Go" button on the introduction screen.
    // It populates the first question & answer(s) and swaps out the buttons.
    public void launchQuiz(View view) {
        // Swap out the buttons
        findViewById(R.id.lets_go_button).setVisibility(View.GONE);
        findViewById(R.id.skip_button).setVisibility(View.VISIBLE);
        findViewById(R.id.confirm_answer_button).setVisibility(View.VISIBLE);

        // Turns off the answer/instructions card TextView to make space for the first question.
        aiText.setVisibility(View.GONE);

        // Call the nextQuestion method to load the first question
        nextQuestion();
    }

    // This method is called by other methods. (launchQuiz, answerQuestion, etc.)
    // It will load the next unanswered question and populate the question & answer(s).
    public void nextQuestion() {
        v("MainActivity.java", "ENTERING THE nextQuestion() method...");

        // Check whether question has already been answered. If question has NOT been answered, proceed with loading the question/answer(s).
        // Operation will then wait until user presses one of the buttons.
        if (questionArray[questionIndex].wasAnswered == false) {

            // Load question text from the current trivia entry into the miCard TextView
            qmText.setText(questionArray[questionIndex].getQuestionText());

            // Determine which type of question has been answered & route the logic accordingly
            if (questionArray[questionIndex] instanceof TextTrivia) { // Process the TextTrivia answer type...
                // Turn on the EditText view in the answer/instructions card.
                // Ensure RadioGroup and CheckBox container views are turned off.
                aiCheckBoxContainer.setVisibility(View.GONE);
                aiRadioGroup.setVisibility(View.GONE);
                aiEditText.setVisibility(View.VISIBLE);
            } else if (questionArray[questionIndex] instanceof CheckboxTrivia) { // Process the CheckboxTrivia answer type...
                // Turn on the LinearLayout container view for the CheckBox views in the answer/instructions card.
                // Ensure EditText and RadioGroup container views are turned off.
                aiEditText.setVisibility(View.GONE);
                aiRadioGroup.setVisibility(View.GONE);
                aiCheckBoxContainer.setVisibility(View.VISIBLE);

                // create local variable that references the current question from the question array
                CheckboxTrivia currentQuestion = (CheckboxTrivia) questionArray[questionIndex];

                // Reference the array of answers and display one in each of the RadioButton views.
                String[] possibleAnswers = currentQuestion.getPossibleAnswers();
                aiCheckBox01.setText(possibleAnswers[0]);
                aiCheckBox02.setText(possibleAnswers[1]);
                aiCheckBox03.setText(possibleAnswers[2]);
                aiCheckBox04.setText(possibleAnswers[3]);
            } else if (questionArray[questionIndex] instanceof RadiobutTrivia) { // Process the RadiobutTrivia answer type...
                // Turn on the RadioButton views in the answer/instructions card.
                // Ensure EditText and CheckBox container views are turned off.
                aiEditText.setVisibility(View.GONE);
                aiCheckBoxContainer.setVisibility(View.GONE);
                aiRadioGroup.setVisibility(View.VISIBLE);

                // create local variable that references the current question from the question array
                RadiobutTrivia currentQuestion = (RadiobutTrivia) questionArray[questionIndex];

                // Reference the array of answers and display one in each of the RadioButton views.
                String[] possibleAnswers = currentQuestion.getPossibleAnswers();
                aiRadioButton01.setText(possibleAnswers[0]);
                aiRadioButton02.setText(possibleAnswers[1]);
                aiRadioButton03.setText(possibleAnswers[2]);
                aiRadioButton04.setText(possibleAnswers[3]);
            }
        } else { // If question HAS already been answered
            checkProgress(); // Call checkProgress method to determine next action
        }
    }


    // This method checks the user's progress through the quiz and directs flow accordingly.
    // It is called by various methods including the nextQuestion, answerQuestion, and skipQuestion.
    private void checkProgress() {
        v("MainActivity.java", "ENTERING THE checkProgress() method...");
        v("MainActivity.java", "The value of questionArray.length is: " + Integer.toString(questionArray.length));
        v("MainActivity.java", "The value of questionIndex at current question is: " + Integer.toString(questionIndex));
        v("MainActivity.java", "the value of isFirstPass at this point is: " + isFirstPass);
        v("MainActivity.java", "the value of Question #1 wasAnswered at this point is: " + questionArray[0].wasAnswered);
        v("MainActivity.java", "the value of Question #2 wasAnswered at this point is: " + questionArray[1].wasAnswered);
        v("MainActivity.java", "the value of Question #3 wasAnswered at this point is: " + questionArray[2].wasAnswered);

        // Triggered if user is on the final question and this is the user's first time through the questions.
        if (isFirstPass == true && questionIndex == questionArray.length - 1) {
            isFirstPass = false; // Change global variable to show user has finished their first pass through the questions.
            // Cycle through the questions, checking to see if any were skipped
            for (int i = 0; i < questionArray.length; i++) {
                if (questionArray[i].wasAnswered == false) {
                    lastChance(); // Since all questions have been viewed and some have been skipped, send user to the Last Chance screen
                    break;
                } else if (i == questionArray.length - 1 && questionArray[i].wasAnswered == true) {
                    // If execution reaches this line, there are no skipped questions and the Grade Quiz screen will be shown.
                    gradeQuiz();
                }

            }

        } else if (isFirstPass == false && questionIndex == questionArray.length - 1) { // Triggered if user is on the final question and it is user's second time through (skipped questions).
            gradeQuiz(); // Grade Quiz screen will be shown
        } else { // If current question is not the final question (whether first pass or revisitig skipped questions), move on to checking/loading the next question.
            questionIndex++; // Increment questionIndex
            nextQuestion(); // Call the this method again to load the next question
        }
    }


    // This method is called by the "Confirm Answer!" button on a typical question screen.
    // It processes the user's answer for the current question & loads the next question & answer(s).
    public void answerQuestion(View view) {
        v("MainActivity.java", "ENTERING THE answerQuestion() method...");
        // Determine which type of question has been answered & route the logic accordingly
        if (questionArray[questionIndex] instanceof TextTrivia) { // Process the TextTrivia answer type...
            String userAnswer = aiEditText.getText().toString();
            TextTrivia castTriviaEntry = (TextTrivia) questionArray[questionIndex]; // Downcast the TriviaEntry object to a TextTrivia object. (Class already checked!).
            castTriviaEntry.submitAnswer(userAnswer);
            aiEditText.setText("");
        } else if (questionArray[questionIndex] instanceof CheckboxTrivia) { // Process the RadiobutTrivia answer type...
            //TODO code to process the CheckboxTrivia answer type...
            CheckboxTrivia castTriviaEntry = (CheckboxTrivia) questionArray[questionIndex]; // Downcast the TriviaEntry object to a CheckboxTrivia object. (Class already checked!)

            // Declare a boolean array that will store whether each CheckBox object is checked. This array will be passed to the CheckboxTrivia object's submitAnswer method.
            boolean[] selectedAnswers = new boolean[4];

            // Check each CheckBox view and add its state to the boolean array we just declared above.
            selectedAnswers[0] = aiCheckBox01.isChecked();
            selectedAnswers[1] = aiCheckBox02.isChecked();
            selectedAnswers[2] = aiCheckBox03.isChecked();
            selectedAnswers[3] = aiCheckBox04.isChecked();

            // Pass the prepared boolean array to the CheckboxTrivia object's submitAnswer method.
            castTriviaEntry.submitAnswer(selectedAnswers);

        } else if (questionArray[questionIndex] instanceof RadiobutTrivia) { // Process the RadiobutTrivia answer type...
            RadiobutTrivia castTriviaEntry = (RadiobutTrivia) questionArray[questionIndex]; // Downcast the TriviaEntry object to a RadiobutTrivia object. (Class already checked!).

            //variable to store the resource id returned as int value by the getCheckedRadioButtonId() method
            int returnedId = aiRadioGroup.getCheckedRadioButtonId();

            if (returnedId == R.id.radiobutton_answer_01) {
                castTriviaEntry.submitAnswer(1);
            } else if (returnedId == R.id.radiobutton_answer_02) {
                castTriviaEntry.submitAnswer(2);
            } else if (returnedId == R.id.radiobutton_answer_03) {
                castTriviaEntry.submitAnswer(3);
            } else if (returnedId == R.id.radiobutton_answer_04) {
                castTriviaEntry.submitAnswer(4);
            }

        }

        // Change icon color to blue
        int myColor = ContextCompat.getColor(this, R.color.blue_700);
        fetchIconViewId().setCardBackgroundColor(myColor);

        // Call the checkProgress() method to determine next action.
        checkProgress();
    }

    // This method is called by the "Skip..." button on a typical question screen.
    // It changes the question's icon to orange and moves on to the next question.
    public void skipQuestion(View view) {
        v("MainActivity.java", "ENTERING THE skipQuestion method...");
        // Change icon color to orange
        int myColor = ContextCompat.getColor(this, R.color.amber_400);
        fetchIconViewId().setCardBackgroundColor(myColor);

        // Call the checkProgress() method to determine next action.
        checkProgress();
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
        return (CardView) findViewById(R.id.icon_q1); // CAN I SOMEHOW RETURN NULL OR CATCH AN EXCEPTION IF NOT ONE OF THE ABOVE VALUES? NOTIFY THE USER?
    }

    protected void lastChance() {
        v("MainActivity.java", "ENTERING THE lastChance() method...");
        // Switch qmCard text to message that questions have been skipped.
        qmText.setText("Nice work! You've made your way through all the questions!");

        // Turn off all the aiCard EditText, RadioGroup, & CheckBox LinearLayout container group views.
        aiEditText.setVisibility(View.GONE);
        aiCheckBoxContainer.setVisibility(View.GONE);
        aiRadioGroup.setVisibility(View.GONE);

        // Turn on the aiCard Text view.
        aiText.setVisibility(View.VISIBLE);

        // Add the instructions text to the aiCard Text view.
        aiText.setText("Looks like you skipped at least one question.\nYou can press 'Revisit' to try those skipped questions again." +
                "\nOtherwise, if you know you still don't know, you can just go ahead and press 'Score my answers!' to see how you did)");

        // Swap out the buttons
        findViewById(R.id.skip_button).setVisibility(View.GONE);
        findViewById(R.id.confirm_answer_button).setVisibility(View.GONE);
        findViewById(R.id.revisit_button).setVisibility(View.VISIBLE);
        findViewById(R.id.score_answers_button).setVisibility(View.VISIBLE);

        // Reset the questionIndex global variable and call the nextQuestion() method. It will start cycling through the questions again, looking for skipped questions. (wasAnswered = 'false').
        questionIndex = 0;
    }

    // This method is used by the 'Revisit...' button. It sets up the Views & Buttons for the second pass through the questions.
    public void revisitQuestions(View view) {
        v("MainActivity.java", "ENTERING THE revisitQuestions() method...");
        // Turn off the Last Chance screen aiCard Text so that the Next Question views can be loaded
        aiText.setVisibility(View.GONE);

        // Swap out the buttons
        findViewById(R.id.revisit_button).setVisibility(View.GONE);
        findViewById(R.id.answer_button).setVisibility(View.VISIBLE);

        // Call the nextQuestion() method to start cycling through all the questions again and load the first unanswered question
        nextQuestion();
    }

    // This method is used by the 'Score my answers!' button. 'Pass-through' to the gradeQuiz() method, since calling it from XML requires a View object for an argument, while Java doesn't need an argument.
    public void launchGradeQuiz(View view) {
        v("MainActivity.java", "ENTERING THE launchGradeQuiz() method...");
        gradeQuiz();
    }

    public void gradeQuiz() {
        v("MainActivity.java", "ENTERING THE gradeQuiz() method...");
        // Turn off the LinearLayout container view for the icon bar
        iconBar.setVisibility(View.GONE);
        // TODO: Turn on the logo ImageView

        int totalCorrect = 0; // Create local variable to hold number of correct answers
        // Loop through each question/answer object in questionArray and increase totalCorrect if the object's wasAnsweredCorrectly variable shows it was answered correctly ('true')
        for (int i = 0; i < questionArray.length; i++) {
            if (questionArray[i].wasAnsweredCorrectly == true) {
                totalCorrect++;
            }
        }
        String totalCorrectString = Integer.toString(totalCorrect);

        // Change out the qmCard TextView text with the results text
        qmText.setText("Wowzers!\nYou got " + totalCorrectString + " answers right\n\nout of " + Integer.toString(questionArray.length) + " questions!" +
                "\n\nThat's ");

        // Turn off all the aiCard EditText, RadioGroup, & CheckBox LinearLayout container group views.
        aiEditText.setVisibility(View.GONE);
        aiCheckBoxContainer.setVisibility(View.GONE);
        aiRadioGroup.setVisibility(View.GONE);

        // Turn on the aiCard Text view.
        aiText.setVisibility(View.VISIBLE);

        // Add the sharing instruction text to the aiCard Text View
        aiText.setText("Ok, smartypants," +
                "\n\nLooks like you've got one more tough choice to make:" +
                "\n\n1) Rest on your laurels." +
                "\n\n2) Share your score with a friend and challenge them to top it!");

        // Swap out the buttons for the Sharing button
        findViewById(R.id.skip_button).setVisibility(View.GONE);
        findViewById(R.id.confirm_answer_button).setVisibility(View.GONE);
        findViewById(R.id.answer_button).setVisibility(View.GONE);
        findViewById(R.id.score_answers_button).setVisibility(View.GONE);
        findViewById(R.id.share_button).setVisibility(View.VISIBLE);


        // TODO: Text message intent
    }

}
