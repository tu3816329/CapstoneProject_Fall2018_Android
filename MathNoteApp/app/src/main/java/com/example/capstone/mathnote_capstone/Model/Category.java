package com.example.capstone.mathnote_capstone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Category implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("categoryName")
    @Expose
    private String categoryName;

    @SerializedName("categoryIcon")
    @Expose
    private String categoryIcon;

    @SerializedName("gradeId")
    @Expose
    private Grade grade;

    @SerializedName("divisionId")
    @Expose
    private Division division;

    @SerializedName("versionId")
    @Expose
    private Version version;

    public Category() {
    }

    public Category(int id, String categoryName, String categoryIcon, Division division) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryIcon = categoryIcon;
        this.division = division;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String name) {
        this.categoryName = categoryName;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
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

    @Override
    public String toString() {
        return id + " | " + categoryName + " | " + grade.getGradeName() + " | " + division.getDivisionName() + " | " + categoryIcon;
    }
}
