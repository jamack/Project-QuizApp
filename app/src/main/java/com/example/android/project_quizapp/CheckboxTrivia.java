package com.example.android.project_quizapp;

import android.util.Log;

/**
 * Created by James on 5/23/2017.
 */

class CheckboxTrivia extends TriviaEntry {

    // Array of (4) Strings, each holding one of the possible answers.
    private String[] possibleAnswers = new String[4];

    // Array representing each of the (4) answers. Correct answers are set to 'true'.
    private boolean[] correctAnswers = new boolean[4];

    // the CheckboxTrivia subclass has (1) constructor
    public CheckboxTrivia(String questionText, String answer1Text, String answer2Text, String answer3Text, String answer4Text,
                          boolean answer1Correct, boolean answer2Correct, boolean answer3Correct, boolean answer4Correct) {
        super(questionText);
        // Take Strings from arguments and store them in array
        possibleAnswers[0] = answer1Text;
        possibleAnswers[1] = answer2Text;
        possibleAnswers[2] = answer3Text;
        possibleAnswers[3] = answer4Text;
        // Take booleans from arguments and store them in array. Indeces correspond with the possibleAnswers array.
        correctAnswers[0] = answer1Correct;
        correctAnswers[1] = answer2Correct;
        correctAnswers[2] = answer3Correct;
        correctAnswers[3] = answer4Correct;
    }

    // "Getter" method to access the encapsulated possibleAnswers field
    public String[] getPossibleAnswers() {
        return possibleAnswers;
    }

    /**
     * This method is called by the answerQuestion method in MainActivity.java.
     * It first processes the user's answer by first updating the global variable to show the question was answered (i.e. not skipped).
     * It receives an argument in the form of an array of booleans wherein each entry represents an answer and whether it is correct (sequentially).
     * It then it runs through a loop, checking if the corresponding boolean arrays match between the user's answers and those from the constructor.
     * (For example, selectedAnswers[0] should have the same value as correctAnswers[0] and so forth). If any of the (4) pairings to NOT match,
     * then the user's answer is incorrect, the method 'breaks', and the global wasAnsweredCorrectly variable is NOT updated to 'true'.
     * Otherwise - if all user answers match the correct answers, the global variable IS updated to 'true'.
     *
     * @param selectedAnswers Integer indicating which one of the (4) answers the user selected.
     */
    protected void submitAnswer(boolean[] selectedAnswers) {
        //set wasAnswered to 'true'
        wasAnswered = true;

        for (int i = 0; i < correctAnswers.length; i++) {
            if (selectedAnswers[i] != correctAnswers[i]) {
                return; // exits the submitAnswer method WITHOUT executing the code below that sets wasAnswered to 'true'
            }
        }

        // If all the user's answers correspond to the correct answers, the 'break' code in the above "if" statement is never executed and the following code runs.
        wasAnsweredCorrectly = true;
        Log.v("CheckboxTrivia.java", "HAND-OFF TO CHECKBOXTRIVIA.JAVA SUCCESSFUL. Question was also answered correctly!");

//        for (int i = 0; i < correctAnswers.length; i++) {
//            if (selectedAnswers[i] != correctAnswers[i]) {
//                break;
//            }
//        }
//
//        // If all the user's answers correspond to the correct answers, the 'break' code in the above "if" statement is never executed and the following code runs.
//        wasAnsweredCorrectly = true;
//        Log.v("CheckboxTrivia.java", "HAND-OFF TO CHECKBOXTRIVIA.JAVA SUCCESSFUL. Question was also answered correctly!");
    }

}
