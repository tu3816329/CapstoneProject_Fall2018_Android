package com.example.capstone.mathnote_capstone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Question {

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("content")
    private String content;

    @Expose
    @SerializedName("categoryId")
    private Category category;

    @Expose
    @SerializedName("versionId")
    private Version version;

    public Question() {}

    public Question(int id, String content, Category category, Version version) {
        this.id = id;
        this.content = content;
        this.category = category;
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
}
