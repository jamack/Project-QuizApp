package com.example.android.project_quizapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static android.util.Log.v;

public class MainActivity extends AppCompatActivity {

    // The following code creates an array of TriviaEntry objects (superclass) and initializes the array with (10) trivia questions & answers
    // by instantiating one of (3) different subclass objects, via their constructors.
    // Each one of these objects (TextTrivia, RadiobutTrivia, & CheckboxTrivia) corresponds to a different answer type.
    // These subclasses all inherit from the TriviaEntry superclass.
    TriviaEntry[] questionArray = new TriviaEntry[]{
            new CheckboxTrivia("Which of the following are fruit?", "carrot", "kiwi", "tomato", "buddha's claw", false, true, true, true),
            new RadiobutTrivia("Which river is the longest?", "Tigris", "Congo", "Danube", "Colorado", 2),
            new TextTrivia("Which of the visible colors has the shortest wavelength?", "violet"),
            new RadiobutTrivia("Which of these is the hardiest, toughest animal?", "Cockroach", "Hippopotamus", "Tardigrade", "Camel", 3),
            new CheckboxTrivia("Which of the following are among the world's 5 largest cities (per city proper, NOT metropolitan area)?", "Karachi", "Tokyo", "Mumbai", "Lagos", true, false, false, true),
            new RadiobutTrivia("Which of these companies is the oldest?", "CIGNA", "Dupont", "Colgate", "Jim Beam", 1),
            new TextTrivia("What is the highest grossing movie of all time (adjusted for inflation)?", "gone with the wind"),
            new CheckboxTrivia("Which of the following are among the world's 5 most widely spoken languages?", "Bengali", "English", "Portuguese", "Arabic", false, true, false, true),
            new RadiobutTrivia("Which of these lakes is the largest - by volume?", "Lake Baikal", "Lake Michigan", "Lake Tanganikya", "Lake Superior", 1),
            new CheckboxTrivia("Which of the following are classes/categories of rock?", "Metamorphic", "Obsidian", "Igneous", "Sedimentary", true, false, true, true)
    };

    // This global variable holds the current app display state, in the form of an integer. (I.e. which Views are on/offf, what text is shown, etc.).
    // 0) Welcome 1) Q&A, first pass 2) Last chance 3) Q&A, revisit 4) Score
    private int currentDisplay = 0;


    // These global variables will serve as shortcuts to the various Views. (Instead of using findViewById every time).
    // will be initialized in the onCreate method (or onConfigurationChanged method, if device has been rotated to another orientation)
    // once the layout has been inflated.
    private LinearLayout iconBar; // Holds a reference to the LinearLayout container for the question icons
    private CardView aiCard; // Holds a reference to the answer/instructions CardView container
    private EditText aiEditText; // Holds a reference to the EditText view in the answer/instructions card text
    private View aiCheckBoxScrollView; // Holds a reference to the question/messaage card ScrollView that contains the LinearLayout container for the checkboxes.
    private View aiCheckBoxContainer; // Holds a reference to the answer/instructions card LinearLayout that contains the CheckBox views.
    private CheckBox aiCheckBox01; // Holds a reference to first CheckBox answer in the answer/instructions card LinearLayout container group.
    private CheckBox aiCheckBox02; // Holds a reference to second CheckBox answer in the answer/instructions card LinearLayout container group.
    private CheckBox aiCheckBox03; // Holds a reference to third CheckBox answer in the answer/instructions card LinearLayout container group.
    private CheckBox aiCheckBox04; // Holds a reference to fourth CheckBox answer in the answer/instructions card LinearLayout container group.
    private TextView qmText; // Holds a reference to the question/message card text
    private View qmScoreMessage; // Holds a reference to the question/message card container view for the Score Message.
    private TextView qmScoreMessageNumCorrect; // Holds a reference to the Score Message's number of correct answers for user.
    private TextView qmScoreMessageTotalNumQuestions; // Holds a reference to the Score Message's total number of questions.
    private TextView qmScoreMessagePercentCorrect; // Holds a reference to the Score Message's percentage of correct answers for user.
    private View aiTextScrollView; // Holds a reference to the question/message card ScrollView that contains the Text View.
    private TextView aiText; // Holds a reference to the answer/instructions card text
    private View aiRadioGroupScrollView; // Holds a reference to the question/messaage card ScrollView that contains the RadioGroup container for the radio buttons.
    private RadioGroup aiRadioGroup; // Holds a reference to the answer/instructions card RadioGroup (container for the Radio Buttons).
    private RadioButton aiRadioButton01; // Holds a reference to the first RadioButton answer in the answer/instructions card RadioGroup.
    private RadioButton aiRadioButton02; // Holds a reference to the second RadioButton answer in the answer/instructions card RadioGroup.
    private RadioButton aiRadioButton03; // Holds a reference to the third RadioButton answer in the answer/instructions card RadioGroup.
    private RadioButton aiRadioButton04; // Holds a reference to the fourth RadioButton answer in the answer/instructions card RadioGroup.
    private View iconQ1; // Holds a reference to the 1st icon.

    // This global variable will track whether it is a user's first time through the questions ('true') or whether they are revisiting skipped questions ('false').
    private boolean isFirstPass = true;

    // This global variable will keep track of which question/answer pairing is currently loaded or to find another question/answer.
    private int questionIndex = 0;

    private int totalCorrect = 0; // Create global variable to hold number of correct answers. Will be used in grading quiz and sending score to a friend.
    private String totalCorrectString; // Create global variable to hold the String version of the variable above. (Used at the same time, too).

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v("MainActivity.java", "EXECUTING THE onCreate METHOD!!");

        // TODO: Once the XML layout is inflated, determine the screen width & adjust the icon spacing accordingly to maintain true circles
        Context context = this;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        // Once the XML layout is inflated, create global reference variables for those Views that will be altered programatically.
        createViewIdReferences();
    }

    private void createViewIdReferences() {
        // Once the XML layout is inflated, fetch a URI Id for Views that will be altered programatically and assign them to global variables.
        iconBar = (LinearLayout) findViewById(R.id.icon_bar);
        aiCard = (CardView) findViewById(R.id.answer_instructions_card);
        qmText = (TextView) findViewById(R.id.question_message_card_text);
        qmScoreMessage = findViewById(R.id.question_message_card_score_message);
        qmScoreMessageNumCorrect = (TextView) findViewById(R.id.score_message_number_correct_answers);
        qmScoreMessageTotalNumQuestions = (TextView) findViewById(R.id.score_message_total_number_question);
        qmScoreMessagePercentCorrect = (TextView) findViewById(R.id.score_message_percent_correct);
        aiTextScrollView = findViewById(R.id.answer_instructions_card_scrollview_textview);
        aiText = (TextView) findViewById(R.id.answer_instructions_card_text);
        aiEditText = (EditText) findViewById(R.id.answer_instructions_card_edittext);
        aiCheckBoxScrollView = findViewById(R.id.answer_instructions_card_scrollview_checkbox);
        aiCheckBoxContainer = findViewById(R.id.answer_instructions_card_checkboxes);
        aiCheckBox01 = (CheckBox) findViewById(R.id.checkbox_answer_01);
        aiCheckBox02 = (CheckBox) findViewById(R.id.checkbox_answer_02);
        aiCheckBox03 = (CheckBox) findViewById(R.id.checkbox_answer_03);
        aiCheckBox04 = (CheckBox) findViewById(R.id.checkbox_answer_04);
        aiRadioGroupScrollView = findViewById(R.id.answer_instructions_card_scrollview_radiobuttons);
        aiRadioGroup = (RadioGroup) findViewById(R.id.answer_instructions_card_radiobuttons);
        aiRadioButton01 = (RadioButton) findViewById(R.id.radiobutton_answer_01);
        aiRadioButton02 = (RadioButton) findViewById(R.id.radiobutton_answer_02);
        aiRadioButton03 = (RadioButton) findViewById(R.id.radiobutton_answer_03);
        aiRadioButton04 = (RadioButton) findViewById(R.id.radiobutton_answer_04);
        iconQ1 = findViewById(R.id.icon_q1);
    }

    // NOTE: SINCE I CAN'T YET FIGURE OUT THE CODE FOR SAVING & RELOADING OBJECTS (PARCELABLE, ETC.),
    // THIS APP MANUALLY OVERRIDES THE ORIENTATION CONFIGURATION CHANGE IN THE MANIFEST FILE AND
    // MANUALLY HANDLES THAT EVENT.
    // The method below is triggered upon a change in orientation. Depending on the new orientation, it loads the corresponding
    // XML layout. The Activity's state is still intact, but references to the Views have changed. They are refreshed and current
    // content is then reloaded.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main_landscape_manually_set);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main);
        }

        // Once the XML layout is inflated, fetch fresh global reference variables for those Views that will be altered programatically.
        createViewIdReferences();
        Log.v("***TESTING***", "THE VALUE OF currentDisplay is : " + Integer.toString(currentDisplay));
        Log.v("MainActivity.java", "VARIABLE CHECK: questionIndex is currently: " + Integer.toString(questionIndex));

        // Restore the progress indicated by the icons
        int i = 0;
        while (questionArray[i].wasViewed == true) {
            if (questionArray[i].wasAnswered == true) {
                fetchIconViewId(i).setBackground(getResources().getDrawable(R.drawable.icon_answered));
            } else if (questionArray[i].wasAnswered == false) {
                fetchIconViewId(i).setBackground(getResources().getDrawable(R.drawable.icon_skipped));
            } else {
                fetchIconViewId(i).setBackground(getResources().getDrawable(R.drawable.icon_current));
            }
            i++;
            if (i == questionArray.length) {
                break;
            }
        }

        // Restore previously displayed set of Views.
        switch (currentDisplay) {
            case 0:
                break;

            case 1:
                startQuiz();
                break;

            case 2:
                Log.v("TESTING", "UPDATING RE-ORIENTED SCREEN - EXCUTING CODE IN CASE 2...");
                findViewById(R.id.lets_go_button).setVisibility(View.GONE);
                lastChance();
                break;
            case 3:
                Log.v("TESTING", "UPDATING RE-ORIENTED SCREEN - EXCUTING CODE IN CASE 3...");
                findViewById(R.id.lets_go_button).setVisibility(View.GONE);
                revisitQuestions();
                break;
            case 4:
                Log.v("TESTING", "UPDATING RE-ORIENTED SCREEN - EXCUTING CODE IN CASE 4...");
                findViewById(R.id.lets_go_button).setVisibility(View.GONE);
                totalCorrect = 0;
                gradeQuiz();
                break;
        }

    }

    // This method is used by the 'Got it. Let's Go!' button. 'Pass-through' to the startQuiz() method, since calling it from XML requires a View object for an argument, while Java doesn't need an argument.
    public void launchStartQuiz(View view) {
        v("MainActivity.java", "ENTERING THE startGradeQuiz() method...");
        startQuiz();
    }

    // This method populates the first question & answer(s) and swaps out the buttons.
    public void startQuiz() {
        // Swap out the buttons
        findViewById(R.id.lets_go_button).setVisibility(View.GONE);
        findViewById(R.id.skip_button).setVisibility(View.VISIBLE);
        findViewById(R.id.confirm_answer_button).setVisibility(View.VISIBLE);

        // Turns off the answer/instructions card TextView to make space for the first question.
        aiTextScrollView.setVisibility(View.GONE);
        aiText.setVisibility(View.GONE);

        // Update variable holding display state.
        currentDisplay = 1;

        // Call the nextQuestion method to load the first question
        nextQuestion();
    }

    // This method is called by other methods. (startQuiz, answerQuestion, etc.)
    // It will load the next unanswered question and populate the question & answer(s).
    public void nextQuestion() {
        v("MainActivity.java", "ENTERING THE nextQuestion() method...");

        // Check whether question has already been answered. If question has NOT been answered, proceed with loading the question/answer(s).
        // Operation will then wait until user presses one of the buttons.
        if (questionArray[questionIndex].wasAnswered == false) {
            Log.v("*** TESTING ***", "Entering the wasAnswered == false conditional code...");

            // Show current question's icon as active
            fetchIconViewId(questionIndex).setBackground(getResources().getDrawable(R.drawable.icon_current));

            // Update the trivia entry Object's wasViewed field (inherited from TriviaEntry superclass) to 'true'
            questionArray[questionIndex].wasViewed = true;

            // TODO: TROUBLESHOOT RELOADING Q&A'S IN LANDSCAPE ORIENTATION...
            if (qmText != null) {
                Log.v("*** TESTING ***", "The original qmText reference is still valid (not null)");
            }

            // Load question text from the current trivia entry into the miCard TextView
            qmText.setText(questionArray[questionIndex].getQuestionText());

            // Determine which type of question has been answered & route the logic accordingly
            if (questionArray[questionIndex] instanceof TextTrivia) { // Process the TextTrivia answer type...
                // Turn on the EditText view in the answer/instructions card.
                // Ensure RadioGroup and CheckBox container views are turned off.
                aiCheckBoxScrollView.setVisibility(View.GONE);
                aiCheckBoxContainer.setVisibility(View.GONE);
                aiRadioGroupScrollView.setVisibility(View.GONE);
                aiRadioGroup.setVisibility(View.GONE);
                aiEditText.setVisibility(View.VISIBLE);
            } else if (questionArray[questionIndex] instanceof CheckboxTrivia) { // Process the CheckboxTrivia answer type...
                // Clear any checked CheckBoxes from previous Checkbox question.
                // Do it before making visible, so user doesn't see them being cleared. UNCHECKING IS STILL APPARENT TO USER...WHY?
                if (aiCheckBox01.isChecked()) {
                    aiCheckBox01.toggle();
                }
                if (aiCheckBox02.isChecked()) {
                    aiCheckBox02.toggle();
                }
                if (aiCheckBox03.isChecked()) {
                    aiCheckBox03.toggle();
                }
                if (aiCheckBox04.isChecked()) {
                    aiCheckBox04.toggle();
                }

                // Turn on the LinearLayout container view for the CheckBox views in the answer/instructions card.
                // Ensure EditText and RadioGroup container views are turned off.
                aiEditText.setVisibility(View.GONE);
                aiRadioGroupScrollView.setVisibility(View.GONE);
                aiRadioGroup.setVisibility(View.GONE);
                aiCheckBoxScrollView.setVisibility(View.VISIBLE);
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
                // Clear any checked RadioButtons from previous RadioButton question
                // Do it before making visible, so user doesn't see them being cleared. UNCHECKING IS STILL APPARENT TO USER...WHY?
                aiRadioGroup.clearCheck();


                // Turn on the RadioButton views in the answer/instructions card.
                // Ensure EditText and CheckBox container views are turned off.
                aiEditText.setVisibility(View.GONE);
                aiCheckBoxScrollView.setVisibility(View.GONE);
                aiCheckBoxContainer.setVisibility(View.GONE);
                aiRadioGroupScrollView.setVisibility(View.VISIBLE);
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


    // TODO: ADD A TOAST METHOD TO LET USER KNOW WHETHER THEY'VE ANSWERED THE QUESTION CORRECTLY. IF NOT, SHOW CORRECT ANSWER.
    // This method is called by the "Confirm Answer!" button on a typical question screen.
    // It processes the user's answer for the current question & loads the next question & answer(s).
    public void answerQuestion(View view) {
        v("MainActivity.java", "ENTERING THE answerQuestion() method...");
        // Determine which type of question has been answered & route the logic accordingly
        if (questionArray[questionIndex] instanceof TextTrivia) { // Process the TextTrivia answer type...
            String userAnswer = aiEditText.getText().toString();
            TextTrivia castTriviaEntry = (TextTrivia) questionArray[questionIndex]; // Downcast the TriviaEntry object to a TextTrivia object. (Class already checked!).
            castTriviaEntry.submitAnswer(userAnswer);

            // Make a toast that either congratulates the user or shows what the answer actually is
            if (questionArray[questionIndex].wasAnsweredCorrectly == true) {
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Hmmm, almost. The correct answer is:\n" + castTriviaEntry.getAnswerString(), Toast.LENGTH_LONG).show();
            }

            aiEditText.setText("");
        } else if (questionArray[questionIndex] instanceof CheckboxTrivia) { // Process the RadiobutTrivia answer type...

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

            // Make a toast that either congratulates the user or shows what the answer actually is
            if (questionArray[questionIndex].wasAnsweredCorrectly == true) {
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Hmmm, almost. The correct answer is:\n" + castTriviaEntry.getAnswerString(), Toast.LENGTH_LONG).show();
            }

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

            // Make a toast that either congratulates the user or shows what the answer actually is
            if (questionArray[questionIndex].wasAnsweredCorrectly == true) {
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Hmmm, almost. The correct answer is:\n" + castTriviaEntry.getAnswerString(), Toast.LENGTH_LONG).show();
            }

        }

        // Change icon color to blue
        fetchIconViewId(questionIndex).setBackground(getResources().getDrawable(R.drawable.icon_answered));

        // Call the checkProgress() method to determine next action.
        checkProgress();
    }

    // This method is called by the "Skip..." button on a typical question screen.
    // It changes the question's icon to orange and moves on to the next question.
    public void skipQuestion(View view) {
        v("MainActivity.java", "ENTERING THE skipQuestion method...");
        // Change icon color to orange
        Drawable iconDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.icon_skipped, null);
        fetchIconViewId(questionIndex).setBackground(iconDrawable);

        // Call the checkProgress() method to determine next action.
        checkProgress();
    }

    private View fetchIconViewId(int arrayIndex) {
        // questionIndex is targeted at an array and starts with 0. To be clearer which icon we're targeting, add 1 to the value for this switch
        switch (arrayIndex + 1) {
            case 1:
                return findViewById(R.id.icon_q1);
            case 2:
                return findViewById(R.id.icon_q2);
            case 3:
                return findViewById(R.id.icon_q3);
            case 4:
                return findViewById(R.id.icon_q4);
            case 5:
                return findViewById(R.id.icon_q5);
            case 6:
                return findViewById(R.id.icon_q6);
            case 7:
                return findViewById(R.id.icon_q7);
            case 8:
                return findViewById(R.id.icon_q8);
            case 9:
                return findViewById(R.id.icon_q9);
            case 10:
                return findViewById(R.id.icon_q10);
        }
        return findViewById(R.id.icon_q1); // CAN I SOMEHOW RETURN NULL OR CATCH AN EXCEPTION IF NOT ONE OF THE ABOVE VALUES? NOTIFY THE USER?
    }

    private void lastChance() {
        Log.v("MainActivity.java", "ENTERING THE lastChance() method...");
        // Switch qmCard text to message that questions have been skipped.
        qmText.setText(R.string.main_activity_last_chance_first_pass_completed);

        // Turn off all the aiCard EditText, RadioGroup, & CheckBox LinearLayout container group views.
        aiEditText.setVisibility(View.GONE);
        aiCheckBoxScrollView.setVisibility(View.GONE);
        aiCheckBoxContainer.setVisibility(View.GONE);
        aiRadioGroupScrollView.setVisibility(View.GONE);
        aiRadioGroup.setVisibility(View.GONE);

        // Turn on the aiCard Text view.
        aiTextScrollView.setVisibility(View.VISIBLE);
        aiText.setVisibility(View.VISIBLE);

        // Add the instructions text to the aiCard Text view.
        aiText.setText(getString(R.string.main_activity_last_chance_revisit_or_score));

        // Swap out the buttons
        findViewById(R.id.skip_button).setVisibility(View.GONE);
        findViewById(R.id.confirm_answer_button).setVisibility(View.GONE);
        findViewById(R.id.revisit_button).setVisibility(View.VISIBLE);
        findViewById(R.id.score_answers_button_last_chance).setVisibility(View.VISIBLE);

        // Update variable holding display state.
        currentDisplay = 2;

        // Reset the questionIndex global variable and call the nextQuestion() method. It will start cycling through the questions again, looking for skipped questions. (wasAnswered = 'false').
        questionIndex = 0;
    }

    // This method is used by the 'Revisit...' button. 'Pass-through' to the revisitQuestions() method, since calling it from XML requires a View object for an argument, while Java doesn't need an argument.
    public void launchRevisitQuestions(View view) {
        v("MainActivity.java", "ENTERING THE launchRevisitQuestions() method...");
        revisitQuestions();
    }

    // This method is used by the 'Revisit...' button. It sets up the Views & Buttons for the second pass through the questions.
    public void revisitQuestions() {
        v("MainActivity.java", "ENTERING THE revisitQuestions() method...");
        // Turn off the Last Chance screen aiCard Text so that the Next Question views can be loaded
        aiTextScrollView.setVisibility(View.GONE);
        aiText.setVisibility(View.GONE);

        // Swap out the buttons
        findViewById(R.id.revisit_button).setVisibility(View.GONE);
        findViewById(R.id.score_answers_button_last_chance).setVisibility(View.GONE);
        findViewById(R.id.confirm_answer_button).setVisibility(View.VISIBLE);
        findViewById(R.id.score_answers_button_revisit).setVisibility(View.VISIBLE);
        // TODO: Reconfiguring these buttons. CURRENTLY, APP CRASHES WHEN THE BUTTON BELOW IS PRESSED...
        //findViewById(R.id.confirm_answer_button).setVisibility(View.VISIBLE);

        // Update variable holding display state.
        currentDisplay = 3;

        // Call the nextQuestion() method to start cycling through all the questions again and load the first unanswered question
        nextQuestion();
    }

    // This method is used by the 'Score my answers!' button. 'Pass-through' to the gradeQuiz() method, since calling it from XML requires a View object for an argument, while Java doesn't need an argument.
    public void launchGradeQuiz(View view) {
        v("MainActivity.java", "ENTERING THE launchGradeQuiz() method...");
        gradeQuiz();
    }

    private void gradeQuiz() {
        v("MainActivity.java", "ENTERING THE gradeQuiz() method...");
        // Turn off the LinearLayout container view for the icon bar
        iconBar.setVisibility(View.GONE);
        // TODO: Turn on the logo ImageView

        // Using the global variable totalCorrect to hold number of correct answers,
        // loop through each question/answer object in questionArray and increase totalCorrect if the object's wasAnsweredCorrectly variable shows it was answered correctly ('true')
        for (int i = 0; i < questionArray.length; i++) {
            if (questionArray[i].wasAnsweredCorrectly == true) {
                totalCorrect++;
            }
        }
        totalCorrectString = Integer.toString(totalCorrect);

        // Swap the question/message TextView out for the scoreMessage view (RelativeLayout w/ multiple LinearLayouts).
        qmText.setVisibility(View.GONE);
        qmScoreMessage.setVisibility(View.VISIBLE);

        // Add specific stats to the Score Message
        qmScoreMessageNumCorrect.setText(totalCorrectString);
        qmScoreMessageTotalNumQuestions.setText(Integer.toString(questionArray.length));
        qmScoreMessagePercentCorrect.setText(Float.toString((int) ((float) totalCorrect / questionArray.length * 100)));


        // Turn off all the aiCard EditText, RadioGroup, & CheckBox LinearLayout container group views.
        aiEditText.setVisibility(View.GONE);
        aiCheckBoxScrollView.setVisibility(View.GONE);
        aiCheckBoxContainer.setVisibility(View.GONE);
        aiRadioGroupScrollView.setVisibility(View.GONE);
        aiRadioGroup.setVisibility(View.GONE);

        // Turn on the aiCard Text view.
        aiTextScrollView.setVisibility(View.VISIBLE);
        aiText.setVisibility(View.VISIBLE);

        // Add the sharing instruction text to the aiCard Text View
        aiText.setText(getString(R.string.main_actitity_rest_on_laurels_or_share_message));

        // Swap out the buttons for the Sharing button
        findViewById(R.id.skip_button).setVisibility(View.GONE);
        findViewById(R.id.confirm_answer_button).setVisibility(View.GONE);
        findViewById(R.id.score_answers_button_last_chance).setVisibility(View.GONE);
        findViewById(R.id.score_answers_button_revisit).setVisibility(View.GONE);
        findViewById(R.id.revisit_button).setVisibility(View.GONE);
        findViewById(R.id.share_button).setVisibility(View.VISIBLE);

        // Update variable holding display state.
        currentDisplay = 4;

        // NOTE: THIS TOAST IS TO SATISFY THE PROJECT RUBRIC. IT IS REDUNDANT & I OTHERWISE WOULD NOT SHOW IT.
        String scoreToast = getString(R.string.activity_main_score_message_line02_pt1of3);
        scoreToast += " " + totalCorrectString + " " + getString(R.string.activity_main_score_message_line02_pt3of3);
        scoreToast += "\n" + getString(R.string.activity_main_score_message_line03_pt1of3);
        scoreToast += " " + Integer.toString(questionArray.length) + " " + getString(R.string.activity_main_score_message_line03_pt3of3);
        Toast.makeText(this, scoreToast, Toast.LENGTH_SHORT).show();

    }

    public void sendScore(View view) {
        // TODO: NEED TO FIGURE OUT HOW TO PASS IN THE USER'S SCORE IF THIS METHOD IS CALLED FROM A BUTTON IN XML...
        Log.v("Mainactivity.java", "ENTERING THE sendScore() method...");
        Intent sendScoreIntent = new Intent(Intent.ACTION_SEND); // Per "JustJava experimentation, this code block should work...
        sendScoreIntent.setType("text/plain");
        sendScoreIntent.putExtra(Intent.EXTRA_TEXT, "I just played \'OK, Smartypants!\' and got " + totalCorrectString + " questions right out of " + questionArray.length + ". \n\n Think you can beat me, smartypants?");

        if (sendScoreIntent.resolveActivity(getPackageManager()) != null) {
            Log.v("MainActivity.java", "Executing the resolveActivity if statement code");
            startActivity(sendScoreIntent);
        } else {
            Log.v("MainActivity.java", "resolveActivity if statement came back as null!");
        }
    }

}
