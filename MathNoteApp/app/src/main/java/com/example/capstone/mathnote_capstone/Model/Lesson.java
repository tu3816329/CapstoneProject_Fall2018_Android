package com.example.capstone.mathnote_capstone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Lesson implements Serializable{

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("lessonTitle")
    @Expose
    private String lessonTitle;

    @SerializedName("lessonContent")
    @Expose
    private String lessonContent;

    @SerializedName("categoryId")
    @Expose
    private Category category;

    @SerializedName("versionId")
    @Expose
    private Version version;

    private boolean isFavorite;
    private int isFinished;
    private int score;

    public Lesson(){}

    public Lesson(int id, String lessonTitle, String lessonContent, Category category, boolean isFavorite) {
        this.id = id;
        this.lessonTitle = lessonTitle;
        this.lessonContent = lessonContent;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
