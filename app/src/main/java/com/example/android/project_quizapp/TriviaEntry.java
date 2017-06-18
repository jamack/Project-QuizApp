package com.example.android.project_quizapp;

/**
 * Superclass for the (3) types of answers:
 * 1) Multiple choice (CheckboxTrivia subclass)
 * 2) Pick-best-answer (RadiobutTrivia subclass)
 * 3) Type-your-answer (TextTrivia subclass).
 * This superclass is never used on its own; it is simply the common fields/methods
 * the (3) subclasses all inherit.
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

    /**
     * Constructor for the TriviaEntry class.
     * In practice, this will never be called, b/c it does not contain an answer field.
     * One of its (3) subclasses will be used instead, extending this superclass.
     *
     * @param questionText String. The question's text.
     */
    public TriviaEntry(String questionText) {
        this.questionText = questionText;
    }

    // "Getter" method to access the encapsulated questionText field
    public String getQuestionText() {
        return questionText;
    }

}
