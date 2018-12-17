package com.example.capstone.mathnote_capstone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Subject implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("subjectName")
    @Expose
    private String subjectName;

    @SerializedName("versionId")
    @Expose
    private Version version;

    public Subject() {
    }

    public Subject(int id, String subjectName) {
        this.id = id;
        this.subjectName = subjectName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return id + "-" + subjectName + "-" + version;
    }
}