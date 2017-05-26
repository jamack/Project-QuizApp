package com.example.android.project_quizapp;

/**
 * Created by James on 5/22/2017.
 */

class TriviaEntry {

    // Used as an identifier & to facilitate stepping through trivia entries.
    // Assigned a unique, sequential value at time of instantiation.
    int entryNum;

    // Text of the question.
    String questionText;

    // Integer representing which format the answers are provided in.
    // 1 = Radio buttons (best answer), 2 = CheckBoxes (multiple choice), & 3 = TextEdit (type the answer)
    int answerType = 0; // will produce an error unless properly assigned at time of instantiation.

    //TODO - figure out how to incorporate a structure for Radio buttons.

    //TODO - figure out how to incorporate a structure for CheckBoxes.

    //TODO - figure out structure for a TextEdit answer. (String?)

    // False until a TriviaEntry object is viewed in MainActivity.java. (booleans are False by default).
    // This will be used to style the icons in the icon bar.
    private boolean wasViewed;

    // False until user input is entered with the "Confirm answer!" button. (booleans are False by default).
    // This will used to identify "skipped" answers that the user can return to.
    private boolean wasAnswered;

    // The TriviaEntry class has (1) constructor.
    // In practice, this will never be called, b/c it does not contain an answer field.
    // One of its (3) subclasses - each corresponding to an answer type - will be used instead.
    public TriviaEntry(int entryNum, String questionText) {
        this.entryNum = entryNum;
        this.questionText = questionText;
    }

    // Method to store state of question as having been viewed.
    public void questionViewed() {
        this.wasViewed = true;
    }

    // Method to store state of question as having been answered (as opposed to skipped).
    public void questionAnswered() {
        this.wasAnswered = true;
    }

}
