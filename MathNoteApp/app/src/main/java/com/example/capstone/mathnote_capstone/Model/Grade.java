package com.example.capstone.mathnote_capstone.Model;

public class Grade {
    private String gradeName;
    private int numberOfChapter;
    private String img;

    public Grade(String gradeName, int numberOfChapter, String img) {
        this.gradeName = gradeName;
        this.numberOfChapter = numberOfChapter;
        this.img = img;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public int getNumberOfChapter() {
        return numberOfChapter;
    }

    public void setNumberOfChapter(int numberOfChapter) {
        this.numberOfChapter = numberOfChapter;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
