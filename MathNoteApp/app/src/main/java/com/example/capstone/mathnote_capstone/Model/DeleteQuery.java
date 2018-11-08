package com.example.capstone.mathnote_capstone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DeleteQuery implements Serializable {

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("query")
    private String query;

    @Expose
    @SerializedName("versionId")
    private Version version;

    public DeleteQuery() {}

    public DeleteQuery(int id, String query, Version version) {
        this.id = id;
        this.query = query;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }
}
