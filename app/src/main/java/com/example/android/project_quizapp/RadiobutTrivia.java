package com.example.android.project_quizapp;

import android.util.Log;

/**
 * Subclass with fields/constructor/methods specific to the Pick-the-best-answer question type.
 * Inherits from the TriviaEntry superclass.
 */

class RadiobutTrivia extends TriviaEntry {

    // Array of (4) Strings, each holding one of the possible answers.
    private String[] possibleAnswers = new String[4];

    // Array representing each of the (4) answers. Correct answers are set to 'true'.
    private int correctAnswer;

    /**
     * Constructor for the RadiobutTrivia subclass. Extends TriviaEntry superclass.
     *
     * @param questionText  String. Text of the question. (Inherited).
     * @param answer1Text   String. Text for the first possible answer.
     * @param answer2Text   String. Text for the second possible answer.
     * @param answer3Text   String. Text for the third possible answer.
     * @param answer4Text   String. Text for the fourth possible answer.
     * @param correctAnswer Integer. Number of the correct answer, from #1 - #4. (NOT corresponding array index position!).
     *                      Code will display answers text based on array index position, but will compare/score answers
     *                      based on numbering them 1 - 4.
     */
    public RadiobutTrivia(String questionText, String answer1Text, String answer2Text, String answer3Text, String answer4Text,
                          int correctAnswer) {
        super(questionText);
        // Take Strings from arguments and store them in array
        possibleAnswers[0] = answer1Text;
        possibleAnswers[1] = answer2Text;
        possibleAnswers[2] = answer3Text;
        possibleAnswers[3] = answer4Text;
        // Take booleans from arguments and store them in array. Indices correspond with the possibleAnswers array.
        this.correctAnswer = correctAnswer;
    }

    /**
     * "Getter" method to access the encapsulated possibleAnswers field.
     * @return String Array.
     */
    public String[] getPossibleAnswers() {
        return possibleAnswers;
    }

    /**
     * This method is called by the answerQuestion method in MainActivity.java.
     * It processes the user's answer by first updating the global variable to show the question was answered (i.e. not skipped),
     * then it checks UPDATE THIS ONCE I FIGURE OUT HOW TO PROCESS THE ANSWER!
     * updates the global variable for whether the question was answered correctly,
     * @param selectedAnswer Integer indicating which one of the (4) answers the user selected.
     */
    protected void submitAnswer(int selectedAnswer) {
        Log.v("RadiobutTrivia.java", "ENTERING submitAnswer method for RadiobutTrivia object...");
        //set wasAnswered to 'true'
        wasAnswered = true;

        // This checks the number of the selected RadioButton against the number indicated by the question constructor as being the correct answer.
        if (selectedAnswer == correctAnswer) {
            wasAnsweredCorrectly = true;
            Log.v("RadiobutTrivia.java", "HAND-OFF TO RADIOBUTTRIVIA.JAVA SUCCESSFUL. Question was also answered correctly!");
        }
    }

    /**
     * "Getter" method to access the encapsulated correctAnswer field.
     * @return String.
     */
    public String getAnswerString() {
        return possibleAnswers[correctAnswer - 1];
    }

}
