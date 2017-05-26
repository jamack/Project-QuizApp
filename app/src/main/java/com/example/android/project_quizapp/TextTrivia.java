package com.example.android.project_quizapp;

/**
 * Created by James on 5/23/2017.
 */

public class TextTrivia extends TriviaEntry {

    // Text of the correct answer.
    protected String correctAnswer;

    // Text of user's answer.
    private String userAnswer;

    // the TextTrivia subclass has (1) constructor
    public TextTrivia(int entryNum, String questionText, String answer) {
        super(entryNum, questionText);
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


}
