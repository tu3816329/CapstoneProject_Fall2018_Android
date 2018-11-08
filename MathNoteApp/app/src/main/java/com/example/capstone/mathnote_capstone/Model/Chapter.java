package com.example.capstone.mathnote_capstone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Chapter implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("chapterName")
    @Expose
    private String chapterName;

    @SerializedName("chapterIcon")
    @Expose
    private String chapterIcon;

    @SerializedName("gradeId")
    @Expose
    private Grade grade;

    @SerializedName("divisionId")
    @Expose
    private Division division;

    @SerializedName("versionId")
    @Expose
    private Version version;

    private float progress;

    public Chapter() {
    }

    public Chapter(int id, String categoryName, String categoryIcon, Division division) {
        this.id = id;
        this.chapterName = categoryName;
        this.chapterIcon = categoryIcon;
        this.division = division;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getChapterIcon() {
        return chapterIcon;
    }

    public void setChapterIcon(String chapterIcon) {
        this.chapterIcon = chapterIcon;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return id + " | " + chapterName + " | " + grade.getGradeName() + " | " + division.getDivisionName() + " | " + chapterIcon;
    }
}
