package com.example.android.project_quizapp;

/**
 * Subclass with fields/constructor/methods specific to the Multiple Choice question type.
 * Inherits from the TriviaEntry superclass.
 */

class CheckboxTrivia extends TriviaEntry {

    // Array of (4) Strings, each holding one of the possible answers.
    private String[] possibleAnswers = new String[4];

    // Array representing each of the (4) answers. Correct answers are set to 'true'.
    private boolean[] correctAnswers = new boolean[4];

    /**
     * Constructor for the CheckboxTrivia subclass. Extends TriviaEntry superclass.
     *
     * @param questionText   String. Text of the question. (Inherited).
     * @param answer1Text    String. Text for the first possible answer.
     * @param answer2Text    String. Text for the second possible answer.
     * @param answer3Text    String. Text for the third possible answer.
     * @param answer4Text    String. Text for the fourth possible answer.
     * @param answer1Correct Boolean. Whether the first possible answer is a correct answer.
     * @param answer2Correct Boolean. Whether the second possible answer is a correct answer.
     * @param answer3Correct Boolean. Whether the third possible answer is a correct answer.
     * @param answer4Correct Boolean. Whether the fourth possible answer is a correct answer.
     */
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

    /**
     * "Getter" method to access the encapsulated possibleAnswers field.
     *
     * @return String Array.
     */
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

    }

    /**
     * "Getter" method to access the encapsulated correctAnswer field. Returns correct answer(s) in form of a String.
     *
     * @return String. Returns correct answer(s) as a single string with newlines (concatenated if multiple correct answers).
     */
    public String getAnswerString() {
        String correctAnswerString = "";

        for (int i = 0; i < 4; i++) {
            if (correctAnswers[i] == true) {
                correctAnswerString += possibleAnswers[i] + "\n";
            }
        }
        return correctAnswerString;
    }

}
