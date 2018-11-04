package com.example.capstone.mathnote_capstone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Exercise implements Serializable {

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("topic")
    private String topic;

    @Expose
    @SerializedName("answer")
    private String answer;

    @Expose
    @SerializedName("mathformId")
    private Mathform mathform;

    @Expose
    @SerializedName("versionId")
    private Version version;

    public Exercise() {}

    public Exercise(String topic, String answer, Mathform mathform) {
        this.topic = topic;
        this.answer = answer;
        this.mathform = mathform;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Mathform getMathform() {
        return mathform;
    }

    public void setMathform(Mathform mathform) {
        this.mathform = mathform;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }
}
