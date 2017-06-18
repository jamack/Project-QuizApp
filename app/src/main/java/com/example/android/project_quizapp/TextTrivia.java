package com.example.android.project_quizapp;

import android.util.Log;

/**
 * Subclass with fields/constructor/methods specific to the Type-your-answer question type.
 * Inherits from the TriviaEntry superclass.
 */

public class TextTrivia extends TriviaEntry {

    // Text of the correct answer.
    private String correctAnswer;

    // the TextTrivia subclass has (1) constructor

    /**
     * Constructor for the TextTrivia subclass. Extends TriviaEntry superclass.
     *
     * @param questionText String. Text of the question. (Inherited).
     * @param answer       String. Text of the answer.
     */
    public TextTrivia(String questionText, String answer) {
        super(questionText);
        correctAnswer = answer;
    }

    /**
     * This method is called by the answerQuestion method in MainActivity.java.
     * It processes the user's answer by first updating the global variable to show the question was answered (i.e. not skipped),
     * then it checks whether the user's answer text matches the correct answer text and accordingly
     * updates the global variable for whether the question was answered correctly,
     * @param userAnswer Text that user input in the answer's EditText view.
     */
    protected void submitAnswer(String userAnswer) {
        //set wasAnswered to 'true'
        wasAnswered = true;

        // Check user's answer against correct answer & store whether or not question was answered correctly.
        // In order to not nitpick capitalization, we make both the user's & actual answers lowercase.
        // LEARNING MOMENT: Comparing CONTENTS of the String objects. Using '==' compares the Objects themselves.
        if (userAnswer.toLowerCase().equals(correctAnswer.toLowerCase())) {
            wasAnsweredCorrectly = true;
            Log.v("TextTrivia.java", "HAND-OFF TO TEXTRIVIA.JAVA SUCCESSFUL. Question was also answered correctly!");
        }
    }

    /**
     * "Getter" method to access the encapsulated correctAnswer field.
     * @return String.
     */
    public String getAnswerString() {
        return correctAnswer;
    }


}
