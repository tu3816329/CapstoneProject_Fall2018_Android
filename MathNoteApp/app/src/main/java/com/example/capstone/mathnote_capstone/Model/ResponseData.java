package com.example.capstone.mathnote_capstone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResponseData implements Serializable {

    @Expose
    @SerializedName("grades")
    private List<Grade> grades;

    @Expose
    @SerializedName("divisions")
    private List<Division> divisions;

    @Expose
    @SerializedName("chapters")
    private List<Chapter> chapters;

    @Expose
    @SerializedName("lessons")
    private List<Lesson> lessons;

    @Expose
    @SerializedName("mathforms")
    private List<Mathform> mathforms;

    @Expose
    @SerializedName("exercises")
    private List<Exercise> exercises;

    @Expose
    @SerializedName("questions")
    private List<Question> questions;

    @Expose
    @SerializedName("choices")
    private List<QuestionChoice> choices;

    @Expose
    @SerializedName("levels")
    private List<QuestionLevel> levels;

    public ResponseData() {}

    public ResponseData(
            List<Grade> grades, List<Division> divisions, List<Chapter> chapters, List<Lesson> lessons,
            List<Mathform> mathforms, List<Exercise> exercises, List<Question> questions,
            List<QuestionChoice> choices, List<QuestionLevel> levels) {
        this.grades = grades;
        this.divisions = divisions;
        this.chapters = chapters;
        this.lessons = lessons;
        this.mathforms = mathforms;
        this.exercises = exercises;
        this.questions = questions;
        this.choices = choices;
        this.levels = levels;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public List<Division> getDivisions() {
        return divisions;
    }

    public void setDivisions(List<Division> divisions) {
        this.divisions = divisions;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public List<Mathform> getMathforms() {
        return mathforms;
    }

    public void setMathforms(List<Mathform> mathforms) {
        this.mathforms = mathforms;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<QuestionChoice> getChoices() {
        return choices;
    }

    public void setChoices(List<QuestionChoice> choices) {
        this.choices = choices;
    }

    public List<QuestionLevel> getLevels() {
        return levels;
    }

    public void setLevels(List<QuestionLevel> levels) {
        this.levels = levels;
    }
}
