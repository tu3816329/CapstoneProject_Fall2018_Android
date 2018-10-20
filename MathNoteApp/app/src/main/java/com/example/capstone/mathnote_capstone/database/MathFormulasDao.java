package com.example.capstone.mathnote_capstone.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.capstone.mathnote_capstone.model.Category;
import com.example.capstone.mathnote_capstone.model.Division;
import com.example.capstone.mathnote_capstone.model.Exercise;
import com.example.capstone.mathnote_capstone.model.Grade;
import com.example.capstone.mathnote_capstone.model.Lesson;
import com.example.capstone.mathnote_capstone.model.Mathform;
import com.example.capstone.mathnote_capstone.model.Question;
import com.example.capstone.mathnote_capstone.model.QuestionChoice;
import com.example.capstone.mathnote_capstone.model.QuestionLevel;
import com.example.capstone.mathnote_capstone.model.ResponseData;
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

    public void saveUserNote(UserNote userNote) {
        SQLiteDatabase wdb = null;

        try {
            wdb = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MathFormulasContract.COLUMN_ID, userNote.getId());
            values.put(MathFormulasContract.UserNoteEntry.COLUMN_TITLE, userNote.getTitle());
            values.put(MathFormulasContract.UserNoteEntry.COLUMN_CONTENT, userNote.getContent());
            wdb.insert(MathFormulasContract.UserNoteEntry.TABLE_NAME, null, values);
        } catch (SQLiteException e) {
            Log.i("Dao_saveUserNote", e.getLocalizedMessage());
        } finally {
            if (wdb != null) {
                wdb.close();
            }
        }
    }

    public void removeUserNote(int userNoteId) {
        SQLiteDatabase wdb = null;

        try {
            wdb.delete(
                    MathFormulasContract.UserNoteEntry.TABLE_NAME,
                    MathFormulasContract.COLUMN_ID + " = ?",
                    new String[] {userNoteId + ""}
            );
        } catch (SQLiteException e) {
            Log.i("Dao_saveUserNote", e.getLocalizedMessage());
        } finally {
            if (wdb != null) {
                wdb.close();
            }
        }
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
                    new String[] {lessonId + ""}, null, null, null
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

    public boolean addFavoriteLesson(int lessonId) {
        SQLiteDatabase wdb = null;
        boolean check = false;

        try {
            wdb = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MathFormulasContract.LessonEntry.COLUMN_IS_FAVORITE, true);
            wdb.update(MathFormulasContract.LessonEntry.TABLE_NAME, values,
                    MathFormulasContract.COLUMN_ID + " = ?", new String[] {lessonId + ""}
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

    public boolean removeFavoriteLesson(int lessonId) {
        SQLiteDatabase wdb = null;
        boolean check = false;

        try {
            wdb = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MathFormulasContract.LessonEntry.COLUMN_IS_FAVORITE, false);
            wdb.update(MathFormulasContract.LessonEntry.TABLE_NAME, values,
                    MathFormulasContract.COLUMN_ID + " = ?", new String[] {lessonId + ""}
            );
        } catch (SQLiteException e) {
            Log.i("Dao_removeFavorite", e.getLocalizedMessage());
        } finally {
            if (wdb != null) {
                wdb.close();
            }
        }
        return check;
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
                values.put(MathFormulasContract.VersionEntry.COLUMN_DB_VERSION, version.getDatabaseVersion());
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

    private Division getDivisionById(int divisionId) {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        Division division = null;
        try {
            rdb = dbHelper.getReadableDatabase();
            cursor = rdb.query(
                    MathFormulasContract.DivisionEntry.TABLE_NAME, null,
                    MathFormulasContract.COLUMN_ID + " = ?",
                    new String[] {divisionId + ""}, null, null, null, null
            );
            if (cursor != null && cursor.moveToFirst()) {
                division = new Division(
                        cursor.getInt(0), cursor.getString(1)
                );
            }
        } catch (SQLiteException e) {
            Log.i("Dao_getDivisionById", e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return division;
    }

    public Category getCategoryById(int categoryId) {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        Category category = null;
        try {
            rdb = dbHelper.getReadableDatabase();
            cursor = rdb.query(
                    MathFormulasContract.CategoryEntry.TABLE_NAME, null,
                    MathFormulasContract.COLUMN_ID + " = ?",
                    new String[] {categoryId + ""}, null, null, null, null
            );
            if (cursor != null && cursor.moveToFirst()) {
                category = new Category(
                        cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), null
                );
            }
        } catch (SQLiteException e) {
            Log.i("Dao_getCategoryById", e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return category;
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
                    MathFormulasContract.COLUMN_ID + " = ?", new String[] {gradeId + ""}
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
                    new String[] {"1"}, null, null, null, null
            );
            if (cursor != null && cursor.moveToFirst()) {
                grade = new Grade(cursor.getInt(0), cursor.getString(1), 0);
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

    private int getCategoriesCountByGrade(int gradeId) {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        int count = 0;

        try {
            rdb = dbHelper.getReadableDatabase();
            cursor = rdb.query(MathFormulasContract.CategoryEntry.TABLE_NAME,
                    null,
                    MathFormulasContract.CategoryEntry.COLUMN_GRADE_ID + " = ?",
                    new String[] {gradeId + ""}, null, null, null);
            count = cursor.getCount();
        } catch (SQLiteException e) {
            Log.i("Dao_getCatsCountByGrade", e.getLocalizedMessage());
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

    public List<Category> getCategoriesByGradeAndDivision(int gradeId, int divisionId) {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        List<Category> categories = new ArrayList<>();
        try {
            rdb = dbHelper.getReadableDatabase();
            String selection = MathFormulasContract.CategoryEntry.COLUMN_DIVISION_ID + " = ? AND " +
                    MathFormulasContract.CategoryEntry.COLUMN_GRADE_ID + " = ?";
            cursor = rdb.query(
                    MathFormulasContract.CategoryEntry.TABLE_NAME,
                    null, selection, new String[] {divisionId + "", gradeId + ""},
                    null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Division division = getDivisionById(divisionId);
                    Category category = new Category(
                            cursor.getInt(0), cursor.getString(1), cursor.getString(2), division
                    );
                    categories.add(category);
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
        return categories;
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
                    int gradeId = cursor.getInt(0);
                    int numOfChapters = getCategoriesCountByGrade(gradeId);
                    Grade grade = new Grade(gradeId, cursor.getString(1), numOfChapters);
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

    public List<Division> getAllDivisions() {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        List<Division> divisions = new ArrayList<>();

        try {
            rdb = dbHelper.getReadableDatabase();
            cursor = rdb.query(
                    MathFormulasContract.DivisionEntry.TABLE_NAME,
                    null, null, null, null, null, null
            );
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Division division = new Division(cursor.getInt(0), cursor.getString(1));
                    divisions.add(division);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.i("Dao_getAllDivisions", e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return divisions;
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
                   Category category = getCategoryById(cursor.getInt(3));
                    Lesson lesson = new Lesson(
                            cursor.getInt(0), cursor.getString(1),
                            cursor.getString(2), category, cursor.getInt(5) > 0
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

    public List<Lesson> getLessonsByCategory(int categoryId) {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        List<Lesson> lessons = new ArrayList<>();

        try {
            rdb = dbHelper.getWritableDatabase();
            Category category = getCategoryById(categoryId);
            cursor = rdb.query(
                    MathFormulasContract.LessonEntry.TABLE_NAME, null,
                    MathFormulasContract.LessonEntry.COLUMN_CATEGORY_ID + " = ?",
                    new String[]{categoryId + ""}, null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Lesson lesson = new Lesson(
                            cursor.getInt(0), cursor.getString(1),
                            cursor.getString(2), category, cursor.getInt(5) > 0
                    );
                    lessons.add(lesson);
                } while (cursor.moveToNext());
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
        return lessons;
    }

    public List<Mathform> getMathformByLesson(int lessonId) {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        List<Mathform> mathforms = new ArrayList<>();

        try {
            rdb = dbHelper.getWritableDatabase();
            cursor = rdb.query(
                    MathFormulasContract.MathformEntry.TABLE_NAME, null,
                    MathFormulasContract.MathformEntry.COLUMN_LESSON_ID + " = ?",
                    new String[] {lessonId + ""}, null, null, null
            );
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Mathform mathform = new Mathform(
                            cursor.getInt(0), cursor.getString(1), cursor.getString(2)
                    );
                    mathforms.add(mathform);
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
        return mathforms;
    }

    public List<Exercise> getExercisesByMathform(int mathformId) {
        SQLiteDatabase rdb = null;
        Cursor cursor = null;
        List<Exercise> exercises = new ArrayList<>();

        try {
            rdb = dbHelper.getWritableDatabase();
            cursor = rdb.query(
                    MathFormulasContract.ExerciseEntry.TABLE_NAME, null,
                    MathFormulasContract.ExerciseEntry.COLUMN_MATHFORM_ID + " = ?",
                    new String[] {mathformId + ""}, null, null, null
            );
            if(cursor != null && cursor.moveToFirst()) {
                do {
                    Exercise exercise = new Exercise(
                            cursor.getString(1), cursor.getString(2), null
                    );
                    exercises.add(exercise);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.i("Dao_getExsByMathform", e.getLocalizedMessage());
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
            // Divisions
            List<Division> divisions = data.getDivisions();
            if (divisions != null && !divisions.isEmpty()) {
                for (Division division : divisions) {
                    ContentValues values = new ContentValues();
                    values.put(MathFormulasContract.COLUMN_ID, division.getId());
                    values.put(MathFormulasContract.DivisionEntry.COLUMN_NAME, division.getDivisionName());
                    values.put(MathFormulasContract.COLUMN_VERSION, division.getVersion().getId());
                    wdb.insert(MathFormulasContract.DivisionEntry.TABLE_NAME, null, values);
                }
            }
            // Categories
            List<Category> categories = data.getCategories();
            if (categories != null && !categories.isEmpty()) {
                for (Category category : categories) {
                    ContentValues values = new ContentValues();
                    if (checkExistRecord(MathFormulasContract.CategoryEntry.TABLE_NAME, category.getId())) {
                        // Edit category
                        values.put(MathFormulasContract.CategoryEntry.COLUMN_NAME, category.getCategoryName());
                        values.put(MathFormulasContract.CategoryEntry.COLUMN_ICON, category.getCategoryIcon());
                        values.put(MathFormulasContract.CategoryEntry.COLUMN_GRADE_ID, category.getGrade().getId());
                        values.put(MathFormulasContract.CategoryEntry.COLUMN_DIVISION_ID, category.getDivision().getId());
                        values.put(MathFormulasContract.COLUMN_VERSION, category.getVersion().getId());
                        wdb.update(
                                MathFormulasContract.CategoryEntry.TABLE_NAME, values,
                                MathFormulasContract.COLUMN_ID + " = ?",
                                new String[]{category.getId() + ""}
                        );
                    } else {
                        // Insert Categories
                        values.put(MathFormulasContract.COLUMN_ID, category.getId());
                        values.put(MathFormulasContract.CategoryEntry.COLUMN_NAME, category.getCategoryName());
                        values.put(MathFormulasContract.CategoryEntry.COLUMN_ICON, category.getCategoryIcon());
                        values.put(MathFormulasContract.CategoryEntry.COLUMN_GRADE_ID, category.getGrade().getId());
                        values.put(MathFormulasContract.CategoryEntry.COLUMN_DIVISION_ID, category.getDivision().getId());
                        values.put(MathFormulasContract.COLUMN_VERSION, category.getVersion().getId());
                        wdb.insert(MathFormulasContract.CategoryEntry.TABLE_NAME, null, values);
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
                        values.put(MathFormulasContract.LessonEntry.COLUMN_CATEGORY_ID, lesson.getCategory().getId());
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
                        values.put(MathFormulasContract.LessonEntry.COLUMN_CATEGORY_ID, lesson.getCategory().getId());
                        values.put(MathFormulasContract.COLUMN_VERSION, lesson.getVersion().getId());
                        wdb.insert(MathFormulasContract.LessonEntry.TABLE_NAME, null, values);
                    }
                }
            }
            // Math forms
            List<Mathform> mathforms = data.getMathforms();
            if (mathforms != null && !mathforms.isEmpty()) {
                for (Mathform mathform : mathforms) {
                    ContentValues values = new ContentValues();
                    if (checkExistRecord(MathFormulasContract.MathformEntry.TABLE_NAME, mathform.getId())) {
                        // Edit math form
                        values.put(MathFormulasContract.MathformEntry.COLUMN_TITLE, mathform.getMathformTitle());
                        values.put(MathFormulasContract.MathformEntry.COLUMN_CONTENT, mathform.getMathformContent());
                        values.put(MathFormulasContract.MathformEntry.COLUMN_LESSON_ID, mathform.getLesson().getId());
                        values.put(MathFormulasContract.COLUMN_VERSION, mathform.getVersion().getId());
                        wdb.update(
                                MathFormulasContract.MathformEntry.TABLE_NAME, values,
                                MathFormulasContract.COLUMN_ID + " = ?",
                                new String[]{mathform.getId() + ""}
                        );
                    } else {
                        // Insert math form
                        values.put(MathFormulasContract.COLUMN_ID, mathform.getId());
                        values.put(MathFormulasContract.MathformEntry.COLUMN_TITLE, mathform.getMathformTitle());
                        values.put(MathFormulasContract.MathformEntry.COLUMN_CONTENT, mathform.getMathformContent());
                        values.put(MathFormulasContract.MathformEntry.COLUMN_LESSON_ID, mathform.getLesson().getId());
                        values.put(MathFormulasContract.COLUMN_VERSION, mathform.getVersion().getId());
                        wdb.insert(MathFormulasContract.MathformEntry.TABLE_NAME, null, values);
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
                        values.put(MathFormulasContract.ExerciseEntry.COLUMN_MATHFORM_ID, exercise.getMathform().getId());
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
                        values.put(MathFormulasContract.ExerciseEntry.COLUMN_MATHFORM_ID, exercise.getMathform().getId());
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
                        values.put(MathFormulasContract.QuestionEntry.COLUMN_CATEGORY_ID, question.getCategory().getId());
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
                        values.put(MathFormulasContract.QuestionEntry.COLUMN_CATEGORY_ID, question.getCategory().getId());
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
            // Question Levels
            List<QuestionLevel> levels = data.getLevels();
            if (levels != null && !levels.isEmpty()) {
                for (QuestionLevel level : levels) {
                    ContentValues values = new ContentValues();
                    if (checkExistRecord(MathFormulasContract.QuestionLevelEntry.TABLE_NAME, level.getId())) {
                        // Edit question level
                        values.put(MathFormulasContract.QuestionLevelEntry.COLUMN_LEVEL, level.getLevel());
                        values.put(MathFormulasContract.COLUMN_VERSION, level.getVersion().getId());
                        wdb.update(
                                MathFormulasContract.QuestionLevelEntry.TABLE_NAME, values,
                                MathFormulasContract.COLUMN_ID + " = ?",
                                new String[]{level.getId() + ""}
                        );
                    } else {
                        // Insert question level
                        values.put(MathFormulasContract.COLUMN_ID, level.getId());
                        values.put(MathFormulasContract.QuestionLevelEntry.COLUMN_LEVEL, level.getLevel());
                        values.put(MathFormulasContract.COLUMN_VERSION, level.getVersion().getId());
                        wdb.insert(MathFormulasContract.QuestionLevelEntry.TABLE_NAME, null, values);
                    }
                }
            }
        } catch (SQLiteException e) {
            Log.i("Dao_SaveResponseData", e.getLocalizedMessage());
        }
    }
}
