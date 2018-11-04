package com.example.capstone.mathnote_capstone.model;

public class UserChoice {
    private int id;
    private Question question;
    private QuestionChoice choice;

    public UserChoice() {
    }

    public UserChoice(int id, Question question, QuestionChoice choice) {
        this.id = id;
        this.question = question;
        this.choice = choice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question questionId) {
        this.question = question;
    }

    public QuestionChoice getChoice() {
        return choice;
    }

    public void setChoice(QuestionChoice choiceId) {
        this.choice = choice;
    }
}
