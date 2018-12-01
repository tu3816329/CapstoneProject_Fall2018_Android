package com.example.capstone.mathnote_capstone.model;

public class Subject {
    private Grade grade;
    private Division division;

    public Subject() {}

    public Subject(Grade grade, Division division) {
        this.grade = grade;
        this.division = division;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }
}
