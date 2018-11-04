package com.example.capstone.mathnote_capstone.model;

import java.io.Serializable;
import java.util.List;

public class Quiz implements Serializable {

    private Question question;
    private List<QuestionChoice> choices;

    public Quiz() {}

    public Quiz(Question question, List<QuestionChoice> choices) {
        this.question = question;
        this.choices = choices;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<QuestionChoice> getChoices() {
        return choices;
    }

    public void setChoices(List<QuestionChoice> choices) {
        this.choices = choices;
    }
}
