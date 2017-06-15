package com.example.android.project_quizapp;

/**
 * Created by James on 5/22/2017.
 */

class TriviaEntry {

    // False until user input is entered with the "Confirm answer!" button. (booleans are False by default).
    // This will used to identify "skipped" answers that the user can return to.
    protected boolean wasAnswered = false;

    // Stores whether question was answered correctly. 'True' if correct, 'False' if incorrect.
    // Correct answers will be tallied at the end of the quiz.
    protected boolean wasAnsweredCorrectly;

    protected boolean wasViewed;

    // Text of the question.
    private String questionText;

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

    // Method to store state of question as having been answered (as opposed to skipped).
    public void questionAnswered() {
        this.wasAnswered = true;
    }

}
