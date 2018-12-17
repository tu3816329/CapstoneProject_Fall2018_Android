package com.example.capstone.mathnote_capstone.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.capstone.mathnote_capstone.model.Chapter;
import com.example.capstone.mathnote_capstone.model.Subject;
import com.example.capstone.mathnote_capstone.model.Exercise;
import com.example.capstone.mathnote_capstone.model.Grade;
import com.example.capstone.mathnote_capstone.model.Lesson;
import com.example.capstone.mathnote_capstone.model.Solution;
import com.example.capstone.mathnote_capstone.model.Question;
import com.example.capstone.mathnote_capstone.model.QuestionChoice;
import com.example.capstone.mathnote_capstone.model.Quiz;
import com.example.capstone.mathnote_capstone.model.ResponseData;
import com.example.capstone.mathnote_capstone.model.UserChoice;
import com.example.capstone.mathnote_capstone.model.UserNote;
import com.example.capstone.mathnote_capstone.model.Version;
import com.example.capstone.mathnote_capstone.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class MathFormulasDao {

    private MathFormulasDBHelper dbHelper;

    public MathFormulasDao(Context context) {
        this.dbHelper = new MathFormulasDBHelper(context);
    }

    public void resetQuizByLesson(int lessonId) {
        SQLiteDatabase wdb = null;
        try {
            wdb = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MathFormulasContract.QuestionEntry.COLUMN_IS_ANSWERED, false);
            wdb.update(
                    MathFormulasContract.QuestionEntry.TABLE_NAME, values,
                    MathFormulasContract.QuestionEntry.COLUMN_LESSON_ID + " = ?",
                    new String[]{lessonId + ""}
            );
            removeUserChoiceByLesson(lessonId);
        } catch (SQLiteException e) {
            Log.i("Dao_resetQuestionStatus", e.getLocalizedMessage());
        } finally {
            if (wdb != null) {
                wdb.close();
            }
        }
    }

    public void setQuestionStatus(int questionId) {
        SQLiteDatabase wdb = null;
        try {
            wdb = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MathFormulasContract.QuestionEntry.COLUMN_IS_ANSWERED, true);
            wdb.update(
                    MathFormulasContract.QuestionEntry.TABLE_NAME, values,
                    MathFormulasContract.COLUMN_ID + " = ?", new String[]{questionId + ""}
            );
        } catch (SQLiteException e) {
            Log.i("Dao_setQuestionStatus", e.getLocalizedMessage());
        } finally {
            if (wdb != null) {
                wdb.close();
            }
        }
    }

    public void resetQuizByChapter(int chapterId) {
        SQLiteDatabase wdb = null;
        try {
            wdb = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MathFormulasContract.LessonEntry.COLUMN_SCORE, 0);
            values.put(MathFormulasContract.LessonEntry.COLUMN_IS_FINISHED, false);
            int row = wdb.update(
                    MathFormulasContract.LessonEntry.TABLE_NAME, values,
                    MathFormulasContract.LessonEntry.COLUMN_CHAPTER_ID + " = ?",
                    new String[]{chapterId + ""}
            );
            resetQuestionsByChapter(chapterId);
            removeUserChoiceByChapter(chapterId);
        } catch (SQLiteException e) {
            Log.i("Dao_resetLesson", e.getLocalizedMessage());
        } finally {
            if (wdb != null) {
                wdb.close();
            }
        }
    }

    private void removeUserChoiceByLesson(int lessonId) {
        SQLiteDatabase wdb = null;
        try {
            wdb = dbHelper.getWritableDatabase();
            List<Quiz> questions = getUnansweredQuestion(lessonId);
            for (Quiz q : questions) {
                wdb.delete(
                        MathFormulasContract.UserChoiceEntry.TABLE_NAME,
                        MathFormulasContract.UserChoiceEntry.COLUMN_QUESTION_ID + " = ?",
                        new String[]{q.getQuestion().getId() + ""}
                );
            }
        } catch (SQLiteException e) {
            Log.i("Dao_removeChoiceLesson", e.getLocalizedMessage());
        } finally {
            if (wdb != null) {
                wdb.close();
            }
        }
    }

    private void removeUserChoiceByChapter(int chapterId) {
        SQLiteDatabase wdb = null;
        try {
            wdb = dbHelper.getWritableDatabase();
            List<Lesson> lessons = getLessonsByChapter(chapterId);
            for (Lesson lesson : lessons) {
                List<Quiz> quizzes = getUnansweredQuestion(lesson.getId());
                for (Quiz q : quizzes) {
                    wdb.delete(
                            MathFormulasContract.UserChoiceEntry.TABLE_NAME,
                            MathFormulasContract.UserChoiceEntry.COLUMN_QUESTION_ID + " = ?",
                            new String[]{q.getQuestion().getId() + ""}
                    );
                }
            }
        } catch (SQLiteException e) {
            Log.i("Dao_removeChoiceCate", e.getLocalizedMessage());
        } finally {
            if (wdb != null) {
                wdb.close();
            }
        }
    }

    private void resetQuestionsByChapter(int chapterId) {
        SQLiteDatabase wdb = null;
        try {
            wdb = dbHelper.getWritableDatabase();
            List<Lesson> lessons = getLessonsByChapter(chapterId);
            for (Lesson lesson : lessons) {
                ContentValues values = new ContentValues();
                values.put(MathFormulasContract.QuestionEntry.COLUMN_IS_ANSWERED, false);
                int row = wdb.update(
                        MathFormulasContract.QuestionEntry.TABLE_NAME, values,
                        MathFormulasContract.QuestionEntry.COLUMN_LESSON_ID + " = ?",
                        new String[]{lesson.getId() + ""}
                );
            }
        } catch (SQLiteException e) {
            Log.i("Dao_resetQuesByCate", e.getLocalizedMessage());
        } finally {
            if (wdb != null) {
                wdb.close();
            }
        }
    }

    public void saveUserChoice(int questionId, int choiceId) {
        SQLiteDatabase wdb = null;
        try {
            wdb = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MathFormulasContract.UserChoiceEntry.COLUMN_QUESTION_ID, questionId);
            values.put(MathFormulasContract.UserChoiceEntry.COLUMN_CHOICE_ID, choiceId);
            wdb.insert(MathFormulasContract.UserChoiceEntry.TABLE_NAME, null, values);
        } catch (SQLiteException e) {
            Log.i("Dao_resetLesson", e.getLocalizedMessage());
        } finally {
            if (wdb != null) {
                wdb.close();
            }
        }
    }

    public List<Question> getQuestionsByLesson(int lessonId) {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        List<Question> questions = new ArrayList<>();
        try {
            rdb = dbHelper.getReadableDatabase();
            cursor = rdb.query(
                    MathFormulasContract.QuestionEntry.TABLE_NAME, null,
                    MathFormulasContract.QuestionEntry.COLUMN_LESSON_ID + " = ?",
                    new String[]{lessonId + ""}, null, null, null
            );
            Lesson lesson = getLessonById(lessonId);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Question question = new Question(cursor.getInt(0), cursor.getString(1), lesson);
                    question.setAnswered(cursor.getInt(4) > 0);
                    questions.add(question);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.i("Dao_nextQuiz", e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return questions;
    }

    public List<UserChoice> getUserChoicesByLesson(int lessonId) {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        List<UserChoice> userChoices = new ArrayList<>();
        try {
            rdb = dbHelper.getReadableDatabase();
            Lesson lesson = getLessonById(lessonId);
            List<Question> questions = getQuestionsByLesson(lessonId);
            for (Question question : questions) {
                cursor = rdb.query(
                        MathFormulasContract.UserChoiceEntry.TABLE_NAME, null,
                        MathFormulasContract.UserChoiceEntry.COLUMN_QUESTION_ID + " = ?",
                        new String[]{question.getId() + ""}, null, null, null
                );
                if (cursor != null && cursor.moveToFirst()) {
                    QuestionChoice choice = getQuestionChoiceById(cursor.getInt(2));
                    UserChoice userChoice = new UserChoice(cursor.getInt(0), question, choice);
                    userChoices.add(userChoice);
                }
            }
        } catch (SQLiteException e) {
            Log.i("Dao_getUserChoicesByLes", e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return userChoices;
    }

    private int countFinishedLesson(int chapterId) {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        try {
            rdb = dbHelper.getReadableDatabase();
            cursor = rdb.query(
                    MathFormulasContract.LessonEntry.TABLE_NAME, null,
                    MathFormulasContract.LessonEntry.COLUMN_IS_FINISHED + " = 1 AND " +
                            MathFormulasContract.LessonEntry.COLUMN_CHAPTER_ID + " = ?",
                    new String[]{chapterId + ""}, null, null, null
            );
            int count = cursor.getCount();
            if (cursor.moveToFirst() && cursor != null) {
                do {
                    int questionNum = getQuestionsByLesson(cursor.getInt(0)).size();
                    if(questionNum == 0) {
                        count--;
                    }
                } while (cursor.moveToNext());
            }
            return count;
        } catch (SQLiteException e) {
            Log.i("Dao_nextQuiz", e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return 0;
    }

    public void updateChapterProgress(int chapterId) {
        SQLiteDatabase wdb = null;
        try {
            wdb = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            List<Lesson> lessons = getLessonsByChapter(chapterId);
            int quizSize = lessons.size();
            for(Lesson lesson : lessons) {
                if(getQuestionsByLesson(lesson.getId()).isEmpty()) {
                    quizSize--;
                }
            }
            int progress = countFinishedLesson(chapterId) * 100 / quizSize;
            values.put(MathFormulasContract.ChapterEntry.COLUMN_PROGRESS, progress);
            wdb.update(
                    MathFormulasContract.ChapterEntry.TABLE_NAME, values,
                    MathFormulasContract.COLUMN_ID + " = ?", new String[]{chapterId + ""}
            );
        } catch (SQLiteException e) {
            Log.i("Dao_updateProgress", e.getLocalizedMessage());
        } finally {
            if (wdb != null) {
                wdb.close();
            }
        }
    }

    public void setLessonFinish(int lessonId) {
        SQLiteDatabase wdb = null;
        try {
            wdb = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MathFormulasContract.LessonEntry.COLUMN_IS_FINISHED, true);
            wdb.update(
                    MathFormulasContract.LessonEntry.TABLE_NAME, values,
                    MathFormulasContract.COLUMN_ID + " = ?", new String[]{lessonId + ""}
            );
        } catch (SQLiteException e) {
            Log.i("Dao_setLessonFinish", e.getLocalizedMessage());
        } finally {
            if (wdb != null) {
                wdb.close();
            }
        }
    }

    /**
     * Return next lesson id which has not been finished
     **/
    public int getNextQuizId(int chapterId) {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        int quizId = 0;
        try {
            rdb = dbHelper.getReadableDatabase();
            cursor = rdb.query(
                    MathFormulasContract.LessonEntry.TABLE_NAME, null,
                    MathFormulasContract.LessonEntry.COLUMN_IS_FINISHED + " = ? AND " +
                            MathFormulasContract.LessonEntry.COLUMN_CHAPTER_ID + " = ?",
                    new String[]{"0", chapterId + ""}, null, null, "id ASC", "1"
            );
            if (cursor != null && cursor.moveToFirst()) {
                List<Quiz> quizzes = getUnansweredQuestion(cursor.getInt(0));
                if(quizzes.isEmpty()) {
                    setLessonFinish(cursor.getInt(0));
                    quizId = getNextQuizId(chapterId);
                } else {
                    quizId = cursor.getInt(0);
                }
            }
        } catch (SQLiteException e) {
            Log.i("Dao_nextQuiz", e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return quizId;
    }

    public int getQuestionsByChapter(int chapterId) {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        int count = 0;
        try {
            rdb = dbHelper.getReadableDatabase();
            List<Lesson> lessons = getLessonsByChapter(chapterId);

            for(Lesson lesson : lessons) {
                count += getQuestionsByLesson(lesson.getId()).size();
            }
        } catch (SQLiteException e) {
            Log.i("Dao_getQuestionByChap", e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return count;
    }

    public int getQuizScore(int lessonId) {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        List<Quiz> quizzes = new ArrayList<>();
        try {
            rdb = dbHelper.getReadableDatabase();
            cursor = rdb.query(
                    MathFormulasContract.LessonEntry.TABLE_NAME, null,
                    MathFormulasContract.COLUMN_ID + " = ?",
                    new String[]{lessonId + ""}, null, null, null
            );
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getInt(7);
            }
        } catch (SQLiteException e) {
            Log.i("Dao_isFavoriteLesson", e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return -1;
    }

    public void saveQuizScore(int lessonId, int score) {
        SQLiteDatabase wdb = null;
        try {
            wdb = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MathFormulasContract.LessonEntry.COLUMN_SCORE, score);
            wdb.update(
                    MathFormulasContract.LessonEntry.TABLE_NAME, values,
                    MathFormulasContract.COLUMN_ID + " = ?", new String[]{lessonId + ""}
            );
        } catch (SQLiteException e) {
            Log.i("Dao_saveQuizScore", e.getLocalizedMessage());
        } finally {
            if (wdb != null) {
                wdb.close();
            }
        }
    }

    /**
     * Check if the user has not finished the previous quiz
     **/
    public boolean isQuizOnGoing(int lessonId) {
        try {
            int unansweredCount = getUnansweredQuestion(lessonId).size();
            int allQuestionCount = countQuestionsByLesson(lessonId);
            return unansweredCount != allQuestionCount;
        } catch (SQLiteException e) {
            Log.i("Dao_isQuizOnGoing", e.getLocalizedMessage());
        }
        return false;
    }

    public int countQuestionsByLesson(int lessonId) {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        try {
            rdb = dbHelper.getReadableDatabase();
            cursor = rdb.query(
                    MathFormulasContract.QuestionEntry.TABLE_NAME, null,
                    MathFormulasContract.QuestionEntry.COLUMN_LESSON_ID + " = ?",
                    new String[]{lessonId + ""}, null, null, null
            );
            return cursor.getCount();
        } catch (SQLiteException e) {
            Log.i("Dao_countQuesByLesson", e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return -1;
    }

    /**
     * Get all question has not been answered in a lesson
     **/
    public List<Quiz> getUnansweredQuestion(int lessonId) {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        List<Quiz> quizzes = new ArrayList<>();
        try {
            rdb = dbHelper.getReadableDatabase();
            cursor = rdb.query(
                    MathFormulasContract.QuestionEntry.TABLE_NAME, null,
                    MathFormulasContract.QuestionEntry.COLUMN_LESSON_ID + " = ? AND " +
                            MathFormulasContract.QuestionEntry.COLUMN_IS_ANSWERED + " = ?",
                    new String[]{lessonId + "", "0"}, null, null, null
            );
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Lesson lesson = getLessonById(lessonId);
                    Question question = new Question(cursor.getInt(0), cursor.getString(1), lesson);
                    List<QuestionChoice> choices = getQuestionChoices(question);
                    Quiz quiz = new Quiz(question, choices);
                    quizzes.add(quiz);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.i("Dao_getUnanswerQuestion", e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return quizzes;
    }

    public QuestionChoice getQuestionChoiceById(int choiceId) {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        QuestionChoice choice = null;
        try {
            rdb = dbHelper.getReadableDatabase();
            cursor = rdb.query(
                    MathFormulasContract.QuestionChoiceEntry.TABLE_NAME, null,
                    MathFormulasContract.COLUMN_ID + " = ?",
                    new String[]{choiceId + ""}, null, null, null
            );
            if (cursor != null && cursor.moveToFirst()) {
                choice = new QuestionChoice(
                        cursor.getInt(0), cursor.getString(1), cursor.getInt(2) > 0, null
                );
            }
        } catch (SQLiteException e) {
            Log.i("Dao_getChoiceById", e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return choice;
    }

    public List<QuestionChoice> getQuestionChoices(Question question) {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        List<QuestionChoice> choices = new ArrayList<>();
        try {
            rdb = dbHelper.getReadableDatabase();
            cursor = rdb.query(
                    MathFormulasContract.QuestionChoiceEntry.TABLE_NAME, null,
                    MathFormulasContract.QuestionChoiceEntry.COLUMN_QUESTION_ID + " = ?",
                    new String[]{question.getId() + ""}, null, null, null
            );
            if (cursor != null && cursor.moveToFirst()) {
                int count = 0;
                do {
                    QuestionChoice choice = new QuestionChoice(
                            cursor.getInt(0), cursor.getString(1), cursor.getInt(2) > 0,
                            question
                    );
                    choices.add(choice);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.i("Dao_isFavoriteLesson", e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return choices;
    }

    public List<UserNote> getAllNotes() {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        List<UserNote> notes = new ArrayList<>();
        try {
            rdb = dbHelper.getReadableDatabase();
            cursor = rdb.query(
                    MathFormulasContract.UserNoteEntry.TABLE_NAME, null,
                    null, null, null, null, null
            );
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    UserNote note = new UserNote(
                            cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)
                    );
                    notes.add(note);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.i("Dao_isFavoriteLesson", e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return notes;
    }

    public int editUserNote(UserNote userNote) {
        SQLiteDatabase wdb = null;
        int row = 0;

        try {
            wdb = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MathFormulasContract.UserNoteEntry.COLUMN_TITLE, userNote.getTitle());
            values.put(MathFormulasContract.UserNoteEntry.COLUMN_CONTENT, userNote.getContent());
            values.put(MathFormulasContract.UserNoteEntry.COLUMN_DATE, userNote.getDate());
            row = wdb.update(
                    MathFormulasContract.UserNoteEntry.TABLE_NAME, values,
                    MathFormulasContract.COLUMN_ID + " = ?", new String[]{userNote.getId() + ""}
            );
        } catch (SQLiteException e) {
            Log.i("Dao_saveUserNote", e.getLocalizedMessage());
        } finally {
            if (wdb != null) {
                wdb.close();
            }
        }
        return row;
    }

    public long saveUserNote(UserNote userNote) {
        SQLiteDatabase wdb = null;
        long row = 0;

        try {
            wdb = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MathFormulasContract.UserNoteEntry.COLUMN_TITLE, userNote.getTitle());
            values.put(MathFormulasContract.UserNoteEntry.COLUMN_CONTENT, userNote.getContent());
            values.put(MathFormulasContract.UserNoteEntry.COLUMN_DATE, userNote.getDate());
            row = wdb.insert(MathFormulasContract.UserNoteEntry.TABLE_NAME, null, values);
        } catch (SQLiteException e) {
            Log.i("Dao_saveUserNote", e.getLocalizedMessage());
        } finally {
            if (wdb != null) {
                wdb.close();
            }
        }
        return row;
    }

    public int removeUserNote(int userNoteId) {
        SQLiteDatabase wdb = null;
        int row = 0;
        try {
            wdb = dbHelper.getWritableDatabase();
            row = wdb.delete(
                    MathFormulasContract.UserNoteEntry.TABLE_NAME,
                    MathFormulasContract.COLUMN_ID + " = ?",
                    new String[]{userNoteId + ""}
            );
        } catch (SQLiteException e) {
            Log.i("Dao_saveUserNote", e.getLocalizedMessage());
        } finally {
            if (wdb != null) {
                wdb.close();
            }
        }
        return row;
    }

    public boolean isFavoriteLesson(int lessonId) {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        boolean check = false;

        try {
            rdb = dbHelper.getReadableDatabase();
            cursor = rdb.query(
                    MathFormulasContract.LessonEntry.TABLE_NAME,
                    null, MathFormulasContract.COLUMN_ID + " = ?",
                    new String[]{lessonId + ""}, null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                check = cursor.getInt(5) > 0;
            }
        } catch (SQLiteException e) {
            Log.i("Dao_isFavoriteLesson", e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return check;
    }

    public List<Lesson> getFavoriteLessons() {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        List<Lesson> lessons = new ArrayList<>();

        try {
            rdb = dbHelper.getReadableDatabase();
            cursor = rdb.query(
                    MathFormulasContract.LessonEntry.TABLE_NAME, null,
                    MathFormulasContract.LessonEntry.COLUMN_IS_FAVORITE + " = ?",
                    new String[]{"1"}, null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Lesson lesson = new Lesson(
                            cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                            null, true
                    );
                    lessons.add(lesson);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.i("Dao_getFavoriteLessons", e.getLocalizedMessage());
        } finally {
            if (null != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return lessons;
    }

    public boolean setFavoriteLesson(int lessonId, boolean isFavorite) {
        SQLiteDatabase wdb = null;
        boolean check = false;

        try {
            wdb = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MathFormulasContract.LessonEntry.COLUMN_IS_FAVORITE, isFavorite);
            wdb.update(MathFormulasContract.LessonEntry.TABLE_NAME, values,
                    MathFormulasContract.COLUMN_ID + " = ?", new String[]{lessonId + ""}
            );
        } catch (SQLiteException e) {
            Log.i("Dao_addFavoriteLesson", e.getLocalizedMessage());
        } finally {
            if (wdb != null) {
                wdb.close();
            }
        }
        return check;
    }

    public int removeFavoriteLesson(int lessonId) {
        SQLiteDatabase wdb = null;
        int row = 0;

        try {
            wdb = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MathFormulasContract.LessonEntry.COLUMN_IS_FAVORITE, false);
            row = wdb.update(MathFormulasContract.LessonEntry.TABLE_NAME, values,
                    MathFormulasContract.COLUMN_ID + " = ?", new String[]{lessonId + ""}
            );
        } catch (SQLiteException e) {
            Log.i("Dao_removeFavorite", e.getLocalizedMessage());
        } finally {
            if (wdb != null) {
                wdb.close();
            }
        }
        return row;
    }

    public int getCurrentVersionId() {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        int currentVersionId = 0;

        try {
            rdb = dbHelper.getReadableDatabase();
            String whereClause = MathFormulasContract.VersionEntry.COLUMN_IS_CURRENT + " = ?";
            String[] whereArgs = {"1"};
            cursor = rdb.query(
                    MathFormulasContract.VersionEntry.TABLE_NAME,
                    null, whereClause, whereArgs, null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                currentVersionId = cursor.getInt(0);
            }
        } catch (SQLiteException e) {
            Log.i("Dao_getCurrentVersionId", e.getLocalizedMessage());
        } finally {
            if (null != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return currentVersionId;
    }

    public void setCurrentVersion(List<Version> versions) {
        try (SQLiteDatabase wdb = dbHelper.getWritableDatabase()) {
            // Deactivate old versions
            ContentValues values = new ContentValues();
            values.put(MathFormulasContract.VersionEntry.COLUMN_IS_CURRENT, false);
            wdb.update(
                    MathFormulasContract.VersionEntry.TABLE_NAME, values, null, null
            );
            // Insert new versions
            for (Version version : versions) {
                values = new ContentValues();
                values.put(MathFormulasContract.COLUMN_ID, version.getId());
                values.put(MathFormulasContract.VersionEntry.COLUMN_VERSION_NAME, version.getVersionName());
                values.put(MathFormulasContract.VersionEntry.COLUMN_IS_CURRENT, version.isCurrent());
                values.put(MathFormulasContract.VersionEntry.COLUMN_UPDATE_DAY, AppUtils.getCurrentDateTime());
                wdb.insert(MathFormulasContract.VersionEntry.TABLE_NAME, null, values);
            }
        } catch (SQLiteException e) {
            Log.i("Dao_setCurrentVersion", e.getLocalizedMessage());
        }
    }

    private boolean checkExistRecord(String tableName, int id) {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        boolean flag = false;

        try {
            rdb = dbHelper.getReadableDatabase();
            String selection = MathFormulasContract.COLUMN_ID + " = ?";
            String[] selectionArgs = {id + ""};
            cursor = rdb.query(tableName, null,
                    selection, selectionArgs, null, null, null
            );
            flag = cursor.getCount() != 0;
        } catch (SQLiteException e) {
            Log.i("Dao_checkExistRecord", e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return flag;
    }

    private Subject getSubjectById(int subjectId) {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        Subject subject = null;
        try {
            rdb = dbHelper.getReadableDatabase();
            cursor = rdb.query(
                    MathFormulasContract.SubjectEntry.TABLE_NAME, null,
                    MathFormulasContract.COLUMN_ID + " = ?",
                    new String[]{subjectId + ""}, null, null, null, null
            );
            if (cursor != null && cursor.moveToFirst()) {
                subject = new Subject(
                        cursor.getInt(0), cursor.getString(1)
                );
            }
        } catch (SQLiteException e) {
            Log.i("Dao_getSubjectById", e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return subject;
    }

    public Chapter getChapterById(int chapterId) {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        Chapter chapter = null;
        try {
            rdb = dbHelper.getReadableDatabase();
            cursor = rdb.query(
                    MathFormulasContract.ChapterEntry.TABLE_NAME, null,
                    MathFormulasContract.COLUMN_ID + " = ?",
                    new String[]{chapterId + ""}, null, null, null, null
            );
            if (cursor != null && cursor.moveToFirst()) {
                chapter = new Chapter(
                        cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), null
                );
            }
        } catch (SQLiteException e) {
            Log.i("Dao_getChapterById", e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return chapter;
    }

    public void setChosenGrade(int gradeId) {
        try (SQLiteDatabase wdb = dbHelper.getWritableDatabase()) {
            //
            ContentValues values = new ContentValues();
            values.put(MathFormulasContract.GradeEntry.COLUMN_IS_CHOSEN, false);
            wdb.update(MathFormulasContract.GradeEntry.TABLE_NAME, values, null, null);
            //
            values = new ContentValues();
            values.put(MathFormulasContract.GradeEntry.COLUMN_IS_CHOSEN, true);
            wdb.update(
                    MathFormulasContract.GradeEntry.TABLE_NAME, values,
                    MathFormulasContract.COLUMN_ID + " = ?", new String[]{gradeId + ""}
            );
        } catch (SQLiteException e) {
            Log.i("Dao_setChosenGrade", e.getLocalizedMessage());
        }
    }

    public Grade getChosenGrade() {
        Grade grade = null;
        SQLiteDatabase rdb = null;
        Cursor cursor = null;

        try {
            rdb = dbHelper.getReadableDatabase();
            cursor = rdb.query(
                    MathFormulasContract.GradeEntry.TABLE_NAME, null,
                    MathFormulasContract.GradeEntry.COLUMN_IS_CHOSEN + " = ?",
                    new String[]{"1"}, null, null, null, null
            );
            if (cursor != null && cursor.moveToFirst()) {
                grade = new Grade(cursor.getInt(0), cursor.getString(1));
            }
        } catch (SQLiteException e) {
            Log.i("Dao_getChosenGrade", e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return grade;
    }

    public int countChaptersByGrade(int gradeId) {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        int count = 0;
        try {
            rdb = dbHelper.getReadableDatabase();
            String selection = MathFormulasContract.ChapterEntry.COLUMN_GRADE_ID + " = ?";
            cursor = rdb.query(
                    MathFormulasContract.ChapterEntry.TABLE_NAME,
                    null, selection, new String[]{gradeId + ""},
                    null, null, null
            );
            count = cursor != null ? cursor.getCount() : 0;
        } catch (SQLiteException e) {
            Log.i("Dao_countChapByGrade", e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return count;
    }

    public List<Chapter> getChaptersByGradeAndSubject(int gradeId, int subjectId) {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        List<Chapter> chapters = new ArrayList<>();
        try {
            rdb = dbHelper.getReadableDatabase();
            String selection = MathFormulasContract.ChapterEntry.COLUMN_SUBJECT_ID + " = ? AND " +
                    MathFormulasContract.ChapterEntry.COLUMN_GRADE_ID + " = ?";
            cursor = rdb.query(
                    MathFormulasContract.ChapterEntry.TABLE_NAME,
                    null, selection, new String[]{subjectId + "", gradeId + ""},
                    null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Subject subject = getSubjectById(subjectId);
                    Chapter chapter = new Chapter(
                            cursor.getInt(0), cursor.getString(1), cursor.getString(2), subject
                    );
                    chapter.setProgress(cursor.getInt(6));
                    chapters.add(chapter);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.i("Dao_chapByGradeAndSub", e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return chapters;
    }

    public List<Grade> getAllGrades() {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        List<Grade> grades = new ArrayList<>();

        try {
            rdb = dbHelper.getReadableDatabase();
            cursor = rdb.query(
                    MathFormulasContract.GradeEntry.TABLE_NAME,
                    null, null, null, null, null, null
            );
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Grade grade = new Grade(cursor.getInt(0), cursor.getString(1));
                    grades.add(grade);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.i("Dao_getAllGrades", e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return grades;
    }

    public List<Subject> getAllSubjects() {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        List<Subject> subjects = new ArrayList<>();

        try {
            rdb = dbHelper.getReadableDatabase();
            cursor = rdb.query(
                    MathFormulasContract.SubjectEntry.TABLE_NAME,
                    null, null, null, null, null, null
            );
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Subject subject = new Subject(cursor.getInt(0), cursor.getString(1));
                    subjects.add(subject);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.i("Dao_getAllSubjects", e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return subjects;
    }

    public List<Lesson> getAllLessons() {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        List<Lesson> lessons = new ArrayList<>();

        try {
            rdb = dbHelper.getReadableDatabase();
            cursor = rdb.query(
                    MathFormulasContract.LessonEntry.TABLE_NAME,
                    null, null, null, null, null, null
            );
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Chapter chapter = getChapterById(cursor.getInt(3));
                    Lesson lesson = new Lesson(
                            cursor.getInt(0), cursor.getString(1),
                            cursor.getString(2), chapter, cursor.getInt(5) > 0
                    );
                    lessons.add(lesson);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.i("Dao_getAllLessons", e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return lessons;
    }

    public Lesson getLessonById(int lessonId) {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;

        try {
            rdb = dbHelper.getWritableDatabase();
            cursor = rdb.query(
                    MathFormulasContract.LessonEntry.TABLE_NAME, null,
                    MathFormulasContract.COLUMN_ID + " = ?",
                    new String[]{lessonId + ""}, null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                Chapter chapter = getChapterById(cursor.getInt(3));
                Lesson lesson = new Lesson(
                        cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), chapter, cursor.getInt(5) > 0
                );
                return lesson;
            }
        } catch (SQLiteException e) {
            Log.i("Dao_getLessonsByCat", e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return null;
    }

    public List<Lesson> getLessonsByChapter(int chapterId) {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        List<Lesson> lessons = new ArrayList<>();

        try {
            rdb = dbHelper.getWritableDatabase();
            Chapter chapter = getChapterById(chapterId);
            cursor = rdb.query(
                    MathFormulasContract.LessonEntry.TABLE_NAME, null,
                    MathFormulasContract.LessonEntry.COLUMN_CHAPTER_ID + " = ?",
                    new String[]{chapterId + ""}, null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Lesson lesson = new Lesson(
                            cursor.getInt(0), cursor.getString(1),
                            cursor.getString(2), chapter, cursor.getInt(5) > 0
                    );
                    lesson.setFinished(cursor.getInt(6));
                    lessons.add(lesson);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.i("Dao_getLessonsByChap", e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return lessons;
    }

    public List<Solution> getSolutionsByLesson(int lessonId) {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        List<Solution> solutions = new ArrayList<>();

        try {
            rdb = dbHelper.getWritableDatabase();
            cursor = rdb.query(
                    MathFormulasContract.SolutionEntry.TABLE_NAME, null,
                    MathFormulasContract.SolutionEntry.COLUMN_LESSON_ID + " = ?",
                    new String[]{lessonId + ""}, null, null, null
            );
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Solution solution = new Solution(
                            cursor.getInt(0), cursor.getString(1), cursor.getString(2)
                    );
                    solutions.add(solution);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.i("Dao_getMathformByLesson", e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return solutions;
    }

    public List<Exercise> getExercisesBySolution(int solutionId) {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        List<Exercise> exercises = new ArrayList<>();

        try {
            rdb = dbHelper.getWritableDatabase();
            cursor = rdb.query(
                    MathFormulasContract.ExerciseEntry.TABLE_NAME, null,
                    MathFormulasContract.ExerciseEntry.COLUMN_SOLUTION_ID + " = ?",
                    new String[]{solutionId + ""}, null, null, null
            );
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Exercise exercise = new Exercise(
                            cursor.getString(1), cursor.getString(2), null
                    );
                    exercises.add(exercise);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.i("Dao_getExsBySolution", e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return exercises;
    }

    public void saveResponseData(ResponseData data) {
        try (SQLiteDatabase wdb = dbHelper.getWritableDatabase()) {
            // Grades
            List<Grade> grades = data.getGrades();
            if (grades != null && !grades.isEmpty()) {
                for (Grade grade : grades) {
                    ContentValues values = new ContentValues();
                    values.put(MathFormulasContract.COLUMN_ID, grade.getId());
                    values.put(MathFormulasContract.GradeEntry.COLUMN_NAME, grade.getGradeName());
                    values.put(MathFormulasContract.COLUMN_VERSION, grade.getVersion().getId());
                    wdb.insert(MathFormulasContract.GradeEntry.TABLE_NAME, null, values);
                }
            }
            // Subjects
            List<Subject> subjects = data.getSubjects();
            if (subjects != null && !subjects.isEmpty()) {
                for (Subject subject : subjects) {
                    ContentValues values = new ContentValues();
                    values.put(MathFormulasContract.COLUMN_ID, subject.getId());
                    values.put(MathFormulasContract.SubjectEntry.COLUMN_NAME, subject.getSubjectName());
                    values.put(MathFormulasContract.COLUMN_VERSION, subject.getVersion().getId());
                    wdb.insert(MathFormulasContract.SubjectEntry.TABLE_NAME, null, values);
                }
            }
            // Chapters
            List<Chapter> chapters = data.getChapters();
            if (chapters != null && !chapters.isEmpty()) {
                for (Chapter chapter : chapters) {
                    ContentValues values = new ContentValues();
                    if (checkExistRecord(MathFormulasContract.ChapterEntry.TABLE_NAME, chapter.getId())) {
                        // Edit chapter
                        values.put(MathFormulasContract.ChapterEntry.COLUMN_NAME, chapter.getChapterName());
                        values.put(MathFormulasContract.ChapterEntry.COLUMN_ICON, chapter.getChapterIcon());
                        values.put(MathFormulasContract.ChapterEntry.COLUMN_GRADE_ID, chapter.getGrade().getId());
                        values.put(MathFormulasContract.ChapterEntry.COLUMN_SUBJECT_ID, chapter.getSubject().getId());
                        values.put(MathFormulasContract.COLUMN_VERSION, chapter.getVersion().getId());
                        wdb.update(
                                MathFormulasContract.ChapterEntry.TABLE_NAME, values,
                                MathFormulasContract.COLUMN_ID + " = ?",
                                new String[]{chapter.getId() + ""}
                        );
                    } else {
                        // Insert chapters
                        values.put(MathFormulasContract.COLUMN_ID, chapter.getId());
                        values.put(MathFormulasContract.ChapterEntry.COLUMN_NAME, chapter.getChapterName());
                        values.put(MathFormulasContract.ChapterEntry.COLUMN_ICON, chapter.getChapterIcon());
                        values.put(MathFormulasContract.ChapterEntry.COLUMN_GRADE_ID, chapter.getGrade().getId());
                        values.put(MathFormulasContract.ChapterEntry.COLUMN_SUBJECT_ID, chapter.getSubject().getId());
                        values.put(MathFormulasContract.COLUMN_VERSION, chapter.getVersion().getId());
                        wdb.insert(MathFormulasContract.ChapterEntry.TABLE_NAME, null, values);
                    }
                }
            }
            // Lessons
            List<Lesson> lessons = data.getLessons();
            if (lessons != null && !lessons.isEmpty()) {
                for (Lesson lesson : lessons) {
                    ContentValues values = new ContentValues();
                    if (checkExistRecord(MathFormulasContract.LessonEntry.TABLE_NAME, lesson.getId())) {
                        // Edit lesson
                        values.put(MathFormulasContract.LessonEntry.COLUMN_TITLE, lesson.getLessonTitle());
                        values.put(MathFormulasContract.LessonEntry.COLUMN_CONTENT, lesson.getLessonContent());
                        values.put(MathFormulasContract.LessonEntry.COLUMN_CHAPTER_ID, lesson.getChapter().getId());
                        values.put(MathFormulasContract.COLUMN_VERSION, lesson.getVersion().getId());
                        wdb.update(
                                MathFormulasContract.LessonEntry.TABLE_NAME, values,
                                MathFormulasContract.COLUMN_ID + " = ?",
                                new String[]{lesson.getId() + ""}
                        );
                    } else {
                        // Insert lesson
                        values.put(MathFormulasContract.COLUMN_ID, lesson.getId());
                        values.put(MathFormulasContract.LessonEntry.COLUMN_TITLE, lesson.getLessonTitle());
                        values.put(MathFormulasContract.LessonEntry.COLUMN_CONTENT, lesson.getLessonContent());
                        values.put(MathFormulasContract.LessonEntry.COLUMN_CHAPTER_ID, lesson.getChapter().getId());
                        values.put(MathFormulasContract.COLUMN_VERSION, lesson.getVersion().getId());
                        wdb.insert(MathFormulasContract.LessonEntry.TABLE_NAME, null, values);
                    }
                }
            }
            // Math forms
            List<Solution> solutions = data.getSolutions();
            if (solutions != null && !solutions.isEmpty()) {
                for (Solution solution : solutions) {
                    ContentValues values = new ContentValues();
                    if (checkExistRecord(MathFormulasContract.SolutionEntry.TABLE_NAME, solution.getId())) {
                        // Edit math form
                        values.put(MathFormulasContract.SolutionEntry.COLUMN_TITLE, solution.getTitle());
                        values.put(MathFormulasContract.SolutionEntry.COLUMN_CONTENT, solution.getContent());
                        values.put(MathFormulasContract.SolutionEntry.COLUMN_LESSON_ID, solution.getLesson().getId());
                        values.put(MathFormulasContract.COLUMN_VERSION, solution.getVersion().getId());
                        wdb.update(
                                MathFormulasContract.SolutionEntry.TABLE_NAME, values,
                                MathFormulasContract.COLUMN_ID + " = ?",
                                new String[]{solution.getId() + ""}
                        );
                    } else {
                        // Insert math form
                        values.put(MathFormulasContract.COLUMN_ID, solution.getId());
                        values.put(MathFormulasContract.SolutionEntry.COLUMN_TITLE, solution.getTitle());
                        values.put(MathFormulasContract.SolutionEntry.COLUMN_CONTENT, solution.getContent());
                        values.put(MathFormulasContract.SolutionEntry.COLUMN_LESSON_ID, solution.getLesson().getId());
                        values.put(MathFormulasContract.COLUMN_VERSION, solution.getVersion().getId());
                        wdb.insert(MathFormulasContract.SolutionEntry.TABLE_NAME, null, values);
                    }
                }
            }
            // Exercises
            List<Exercise> exercises = data.getExercises();
            if (exercises != null && !exercises.isEmpty()) {
                for (Exercise exercise : exercises) {
                    ContentValues values = new ContentValues();
                    if (checkExistRecord(MathFormulasContract.ExerciseEntry.TABLE_NAME, exercise.getId())) {
                        // Edit exercise
                        values.put(MathFormulasContract.ExerciseEntry.COLUMN_TOPIC, exercise.getTopic());
                        values.put(MathFormulasContract.ExerciseEntry.COLUMN_ANSWER, exercise.getAnswer());
                        values.put(MathFormulasContract.ExerciseEntry.COLUMN_SOLUTION_ID, exercise.getSolution().getId());
                        values.put(MathFormulasContract.COLUMN_VERSION, exercise.getVersion().getId());
                        wdb.update(
                                MathFormulasContract.ExerciseEntry.TABLE_NAME, values,
                                MathFormulasContract.COLUMN_ID + " = ?",
                                new String[]{exercise.getId() + ""}
                        );
                    } else {
                        // Insert exercise
                        values.put(MathFormulasContract.COLUMN_ID, exercise.getId());
                        values.put(MathFormulasContract.ExerciseEntry.COLUMN_TOPIC, exercise.getTopic());
                        values.put(MathFormulasContract.ExerciseEntry.COLUMN_ANSWER, exercise.getAnswer());
                        values.put(MathFormulasContract.ExerciseEntry.COLUMN_SOLUTION_ID, exercise.getSolution().getId());
                        values.put(MathFormulasContract.COLUMN_VERSION, exercise.getVersion().getId());
                        wdb.insert(MathFormulasContract.ExerciseEntry.TABLE_NAME, null, values);
                    }
                }
            }
            // Questions
            List<Question> questions = data.getQuestions();
            if (questions != null && !questions.isEmpty()) {
                for (Question question : questions) {
                    ContentValues values = new ContentValues();
                    if (checkExistRecord(MathFormulasContract.QuestionEntry.TABLE_NAME, question.getId())) {
                        // Edit question
                        values.put(MathFormulasContract.QuestionEntry.COLUMN_CONTENT, question.getContent());
                        values.put(MathFormulasContract.QuestionEntry.COLUMN_LESSON_ID, question.getLesson().getId());
                        values.put(MathFormulasContract.COLUMN_VERSION, question.getVersion().getId());
                        wdb.update(
                                MathFormulasContract.QuestionEntry.TABLE_NAME, values,
                                MathFormulasContract.COLUMN_ID + " = ?",
                                new String[]{question.getId() + ""}
                        );
                    } else {
                        // Insert question
                        values.put(MathFormulasContract.COLUMN_ID, question.getId());
                        values.put(MathFormulasContract.QuestionEntry.COLUMN_CONTENT, question.getContent());
                        values.put(MathFormulasContract.QuestionEntry.COLUMN_LESSON_ID, question.getLesson().getId());
                        values.put(MathFormulasContract.COLUMN_VERSION, question.getVersion().getId());
                        wdb.insert(MathFormulasContract.QuestionEntry.TABLE_NAME, null, values);
                    }
                }
            }
            // Question Choices
            List<QuestionChoice> choices = data.getChoices();
            if (choices != null && !choices.isEmpty()) {
                for (QuestionChoice choice : choices) {
                    ContentValues values = new ContentValues();
                    if (checkExistRecord(MathFormulasContract.QuestionChoiceEntry.TABLE_NAME, choice.getId())) {
                        // Edit question choice
                        values.put(MathFormulasContract.QuestionChoiceEntry.COLUMN_CONTENT, choice.getContent());
                        values.put(MathFormulasContract.QuestionChoiceEntry.COLUMN_QUESTION_ID, choice.getQuestion().getId());
                        values.put(MathFormulasContract.COLUMN_VERSION, choice.getVersion().getId());
                        wdb.update(
                                MathFormulasContract.QuestionChoiceEntry.TABLE_NAME, values,
                                MathFormulasContract.COLUMN_ID + " = ?",
                                new String[]{choice.getId() + ""}
                        );
                    } else {
                        // Insert question choice
                        values.put(MathFormulasContract.COLUMN_ID, choice.getId());
                        values.put(MathFormulasContract.QuestionChoiceEntry.COLUMN_CONTENT, choice.getContent());
                        values.put(MathFormulasContract.QuestionChoiceEntry.COLUMN_IS_CORRECT, choice.isCorrect());
                        values.put(MathFormulasContract.QuestionChoiceEntry.COLUMN_QUESTION_ID, choice.getQuestion().getId());
                        values.put(MathFormulasContract.COLUMN_VERSION, choice.getVersion().getId());
                        wdb.insert(MathFormulasContract.QuestionChoiceEntry.TABLE_NAME, null, values);
                    }
                }
            }
        } catch (SQLiteException e) {
            Log.i("Dao_SaveResponseData", e.getLocalizedMessage());
        }
    }
}