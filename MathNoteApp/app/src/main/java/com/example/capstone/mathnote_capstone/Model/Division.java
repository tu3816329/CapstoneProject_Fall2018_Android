package com.example.capstone.mathnote_capstone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Division implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("divisionName")
    @Expose
    private String divisionName;

    @SerializedName("versionId")
    @Expose
    private Version version;

    public Division() {
    }

    public Division(int id, String divisionName) {
        this.id = id;
        this.divisionName = divisionName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return id + "-" + divisionName + "-" + version;
    }
}