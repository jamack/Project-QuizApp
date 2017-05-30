package com.example.android.project_quizapp;

import android.util.Log;

/**
 * Created by James on 5/23/2017.
 */

public class TextTrivia extends TriviaEntry {

    // Text of the correct answer.
    protected String correctAnswer;

    // the TextTrivia subclass has (1) constructor
    public TextTrivia(String questionText, String answer) {
        super(questionText);
        correctAnswer = answer;
    }

    /**
     * Returns the value for the private correctAnswer field
     *
     * @return String with the value
     */
    protected String getCorrectAnswer() {
        return correctAnswer;
    }

    protected void skipQuestion() {
        //TODO set wasViewed to 'true'
        //TODO change icon color to orange
        //TODO increment currentQuestionNum
        //TODO call method to load next question
    }

    /**
     * This method is called by the answerQuestion method in MainActivity.java.
     * It processes the user's answer by first updating the global variable to show the question was answered (i.e. not skipped),
     * then it checks whether the user's answer text matches the correct answer text and accordingly
     * updates the global variable for whether the question was answered correctly,
     *
     * @param userAnswer Text that user input in the answer's EditText view.
     */
    protected void submitAnswer(String userAnswer) {
        //set wasAnswered to 'true'
        wasAnswered = true;

        //check user's answer against correct answer & store whether or not question was answered correctly.
        // LEARNING MOMENT: Comparing CONTENTS of the String objects. Using '==' compares the Objects themselves.
        if (userAnswer.equals(correctAnswer)) {
            wasAnsweredCorrectly = true;
            Log.v("TextTrivia.java", "HAND-OFF TO TEXTRIVIA.JAVA SUCCESSFUL. Question was also answered correctly!");
        }
    }


}
