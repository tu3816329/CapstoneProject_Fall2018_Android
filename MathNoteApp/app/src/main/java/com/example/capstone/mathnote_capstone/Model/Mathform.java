package com.example.capstone.mathnote_capstone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Mathform implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("mathformTitle")
    @Expose
    private String mathformTitle;

    @SerializedName("mathformContent")
    @Expose
    private String mathformContent;

    @Expose
    @SerializedName("lessonId")
    private Lesson lesson;

    @Expose
    @SerializedName("versionId")
    private Version version;

    public Mathform() {}

    public Mathform(int id, String mathformTitle, String mathformContent) {
        this.id = id;
        this.mathformTitle = mathformTitle;
        this.mathformContent = mathformContent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMathformTitle() {
        return mathformTitle;
    }

    public void setMathformTitle(String mathformTitle) {
        this.mathformTitle = mathformTitle;
    }

    public String getMathformContent() {
        return mathformContent;
    }

    public void setMathformContent(String mathformContent) {
        this.mathformContent = mathformContent;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }
}
