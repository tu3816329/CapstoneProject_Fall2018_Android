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
    @SerializedName("subjects")
    private List<Subject> subjects;

    @Expose
    @SerializedName("chapters")
    private List<Chapter> chapters;

    @Expose
    @SerializedName("lessons")
    private List<Lesson> lessons;

    @Expose
    @SerializedName("solutions")
    private List<Solution> solutions;

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
    @SerializedName("queries")
    private List<DeleteQuery> queries;

    public ResponseData() {}

    public ResponseData(
            List<Grade> grades, List<Subject> subjects, List<Chapter> chapters, List<Lesson> lessons,
            List<Solution> solutions, List<Exercise> exercises, List<Question> questions,
            List<QuestionChoice> choices, List<DeleteQuery> queries) {
        this.grades = grades;
        this.subjects = subjects;
        this.chapters = chapters;
        this.lessons = lessons;
        this.solutions = solutions;
        this.exercises = exercises;
        this.questions = questions;
        this.choices = choices;
        this.queries = queries;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
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

    public List<Solution> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<Solution> solutions) {
        this.solutions = solutions;
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

    public List<DeleteQuery> getQueries() {
        return queries;
    }

    public void setQueries(List<DeleteQuery> queries) {
        this.queries = queries;
    }
}