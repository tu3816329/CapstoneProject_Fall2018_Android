package com.example.capstone.mathnote_capstone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Version implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("databaseVersion")
    @Expose
    private int databaseVersion;

    @SerializedName("versionName")
    @Expose
    private String versionName;

    @SerializedName("isCurrent")
    @Expose
    private boolean isCurrent;

    private String updateDay;

    public Version() {
    }

    public Version(int id, int databaseVersion, String versionName, boolean isCurrent) {
        this.id = id;
        this.databaseVersion = databaseVersion;
        this.versionName = versionName;
        this.isCurrent = isCurrent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDatabaseVersion() {
        return databaseVersion;
    }

    public void setDatabaseVersion(int databaseVersion) {
        this.databaseVersion = databaseVersion;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public String getUpdateDay() {
        return updateDay;
    }

    public void setUpdateDay(String updateDay) {
        this.updateDay = updateDay;
    }

    @Override
    public String toString() {
        return id + "-" + databaseVersion + "-" + versionName + "-" + isCurrent;
    }
}
