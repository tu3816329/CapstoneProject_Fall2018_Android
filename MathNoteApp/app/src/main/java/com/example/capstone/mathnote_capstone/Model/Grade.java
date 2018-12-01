package com.example.capstone.mathnote_capstone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Grade implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("gradeName")
    @Expose
    private String gradeName;

    @SerializedName("versionId")
    @Expose
    private Version version;

    private boolean isChosen;

    public Grade() {}

    public Grade(int id, String gradeName) {
        this.id = id;
        this.gradeName = gradeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public boolean isChosen() {
        return isChosen;
    }

    public void setChosen(boolean chosen) {
        isChosen = chosen;
    }

    @Override
    public String toString() {
        return id + " | " + gradeName + " | " + version;
    }

//    private String gradeName;
//    private int numberOfChapter;
//    private String img;
//
//    public Grade(String gradeName, int numberOfChapter, String img) {
//        this.gradeName = gradeName;
//        this.numberOfChapter = numberOfChapter;
//        this.img = img;
//    }
//
//    public String getGradeName() {
//        return gradeName;
//    }
//
//    public void setGradeName(String gradeName) {
//        this.gradeName = gradeName;
//    }
//
//    public int getNumberOfChapter() {
//        return numberOfChapter;
//    }
//
//    public void setNumberOfChapter(int numberOfChapter) {
//        this.numberOfChapter = numberOfChapter;
//    }
//
//    public String getImg() {
//        return img;
//    }
//
//    public void setImg(String img) {
//        this.img = img;
//    }
}
