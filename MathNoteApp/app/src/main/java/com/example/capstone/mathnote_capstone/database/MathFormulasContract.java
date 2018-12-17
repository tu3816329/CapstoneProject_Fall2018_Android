package com.example.capstone.mathnote_capstone.database;

public final class MathFormulasContract {

    static final String COLUMN_ID = "id";
    public static final String COLUMN_VERSION = "version_id";

    private MathFormulasContract(){}

    public static class VersionEntry {
        public static final String TABLE_NAME = "version";
        public static final String COLUMN_VERSION_NAME = "version_name";
        public static final String COLUMN_IS_CURRENT = "is_current";
        public static final String COLUMN_UPDATE_DAY = "update_day";
    }

    public static class GradeEntry {
        public static final String TABLE_NAME = "grade";
        public static final String COLUMN_NAME = "grade_name";
        public static final String COLUMN_IS_CHOSEN = "is_chosen";
    }

    public static class SubjectEntry {
        public static final String TABLE_NAME = "subject";
        public static final String COLUMN_NAME = "subject_name";
    }

    public static class ChapterEntry {
        public static final String TABLE_NAME = "chapter";
        public static final String COLUMN_NAME = "chapter_name";
        public static final String COLUMN_ICON = "chapter_icon";
        public static final String COLUMN_GRADE_ID = "grade_id";
        public static final String COLUMN_SUBJECT_ID = "subject_id";
        public static final String COLUMN_PROGRESS = "progress";
    }

    public static class LessonEntry {
        public static final String TABLE_NAME = "lesson";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_CHAPTER_ID = "chapter_id";
        public static final String COLUMN_IS_FAVORITE = "is_favorite";
        public static final String COLUMN_IS_FINISHED = "is_finished";
        public static final String COLUMN_SCORE = "score";
    }

    public static class SolutionEntry {
        public static final String TABLE_NAME = "solution";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_LESSON_ID = "lesson_id";
    }

    public static class ExerciseEntry {
        public static final String TABLE_NAME = "exercise";
        public static final String COLUMN_TOPIC = "topic";
        public static final String COLUMN_ANSWER = "answer";
        public static final String COLUMN_SOLUTION_ID = "solution_id";
    }

    public static class QuestionEntry {
        public static final String TABLE_NAME = "question";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_LESSON_ID = "lesson_id";
        public static final String COLUMN_IS_ANSWERED = "is_answered";
    }

    public static class QuestionChoiceEntry {
        public static final String TABLE_NAME = "question_choice";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_IS_CORRECT = "is_correct";
        public static final String COLUMN_QUESTION_ID = "question_id";
    }

    public static class UserNoteEntry {
        public static final String TABLE_NAME = "user_note";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_DATE = "date";
    }

    public static class UserChoiceEntry {
        public static final String TABLE_NAME = "user_choice";
        public static final String COLUMN_QUESTION_ID = "question_id";
        public static final String COLUMN_CHOICE_ID = "choice_id";
    }
}