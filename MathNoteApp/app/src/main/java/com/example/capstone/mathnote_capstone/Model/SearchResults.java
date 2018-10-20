package com.example.capstone.mathnote_capstone.model;

import java.io.Serializable;
import java.util.List;

public class SearchResults implements Serializable {
    private List<Lesson> lessons;

    public SearchResults(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }
}
