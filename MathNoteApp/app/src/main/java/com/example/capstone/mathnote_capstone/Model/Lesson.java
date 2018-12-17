package com.example.capstone.mathnote_capstone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Lesson implements Serializable{

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("title")
    @Expose
    private String lessonTitle;

    @SerializedName("content")
    @Expose
    private String lessonContent;

    @SerializedName("chapterId")
    @Expose
    private Chapter chapter;

    @SerializedName("versionId")
    @Expose
    private Version version;

    private boolean isFavorite;
    private int isFinished;
    private int score;

    public Lesson(){}

    public Lesson(int id, String lessonTitle, String lessonContent, Chapter chapter, boolean isFavorite) {
        this.id = id;
        this.lessonTitle = lessonTitle;
        this.lessonContent = lessonContent;
        this.chapter = chapter;
        this.isFavorite = isFavorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    public String getLessonContent() {
        return lessonContent;
    }

    public void setLessonContent(String lessonContent) {
        this.lessonContent = lessonContent;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int isFinished() {
        return isFinished;
    }

    public void setFinished(int finished) {
        isFinished = finished;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
