package com.example.capstone.mathnote_capstone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionChoice {

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("content")
    private String content;

    @Expose
    @SerializedName("isCorrect")
    private boolean isCorrect;

    @Expose
    @SerializedName("questionId")
    private Question question;

    @Expose
    @SerializedName("versionId")
    private Version version;

    public QuestionChoice() {}

    public QuestionChoice(int id, String content, boolean isCorrect, Question question, Version version) {
        this.id = id;
        this.content = content;
        this.isCorrect = isCorrect;
        this.question = question;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }
}
