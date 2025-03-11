package com.juego;

public class Question {
    private String questionText;
    private int nextQuestionNumber;

    public Question(String questionText, int nextQuestionNumber) {
        this.questionText = questionText;
        this.nextQuestionNumber = nextQuestionNumber;
    }

    public String getQuestionText() {
        return questionText;
    }

    public int getNextQuestionNumber() {
        return nextQuestionNumber;
    }
}