package com.example.android.project_quizapp;

/**
 * Created by James on 5/22/2017.
 */

class TriviaEntry {

    // Text of the question.
    protected String questionText;

    // False until a TriviaEntry object is viewed in MainActivity.java. (booleans are False by default).
    // This will be used to style the icons in the icon bar.
    protected boolean wasViewed;

    // False until user input is entered with the "Confirm answer!" button. (booleans are False by default).
    // This will used to identify "skipped" answers that the user can return to.
    protected boolean wasAnswered;

    // Stores whether question was answered correctly. 'True' if correct, 'False' if incorrect.
    // Correct answers will be tallied at the end of the quiz.
    protected boolean wasAnsweredCorrectly;

    // The TriviaEntry class has (1) constructor.
    // In practice, this will never be called, b/c it does not contain an answer field.
    // One of its (3) subclasses - each corresponding to an answer type - will be used instead.
    public TriviaEntry(String questionText) {
        this.questionText = questionText;
    }

    // "Getter" method to access the encapsulated questionText field
    public String getQuestionText() {
        return questionText;
    }

    // Method to store state of question as having been viewed.
    public void questionViewed() {
        wasViewed = true;
    }

    // Method to store state of question as having been answered (as opposed to skipped).
    public void questionAnswered() {
        this.wasAnswered = true;
    }

//    // THIS IS JUST A TEMPORARY TEST!! (METHOD WILL BE IN THE SUBCLASSES)
//    protected void submitAnswer(String userAnswer) {
//        //set wasAnswered to 'true'
//        wasAnswered = true;
//
//        //check user's answer against correct answer & store whether or not question was answered correctly.
//        if(userAnswer == correctAnswer) {
//            wasAnsweredCorrectly = true;
//        } else {
//            wasAnsweredCorrectly = false;
//        }
//    }

}
