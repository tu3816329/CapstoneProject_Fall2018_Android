package com.example.capstone.mathnote_capstone.database;

import android.content.Context;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MathFormulasDBHelper extends SQLiteOpenHelper {

    private String DATABASE_PATH;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "math_formulas_user";
    private SQLiteDatabase database;
    private final Context context;

    public MathFormulasDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.DATABASE_PATH = "data/data/" + context.getPackageName() + "/databases/";
    }

    public void createDatabase() {
        boolean isDbExist = checkDatabase();
        if (!isDbExist) {
            this.getReadableDatabase();
        }
    }

    private void copyDatabase() throws IOException {
        InputStream is = context.getAssets().open(DATABASE_NAME);
        File dir = new File(DATABASE_PATH);
        if (!dir.exists()) {
             dir.mkdirs();
        }
        String outputFile = DATABASE_PATH + DATABASE_NAME;
        OutputStream os = new FileOutputStream(outputFile);

        byte[] buffer = new byte[10];
        int length;
        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
        }
        os.flush();
        os.close();
        is.close();
    }

    private boolean checkDatabase() {

        SQLiteDatabase db = null;
        try {
            String path = DATABASE_PATH + DATABASE_NAME;
            File file = new File(path);
            if (file.exists()) {
                db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
            } else {
                copyDatabase();
            }
        } catch (SQLiteCantOpenDatabaseException e) {
            Log.e("DBHelper_checkDatabase", "Cannot find the database file");
        } catch (IOException e) {
            Log.i("DBHelper_checkDatabase", "Cannot find the database file");
        }

        if (db != null) {
            db.close();
        }
        return db != null;
    }

    public SQLiteDatabase getReadableDatabase() throws SQLiteException {
        String path = DATABASE_PATH + DATABASE_NAME;
        database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        return database;
    }

    public SQLiteDatabase getWritableDatabase() throws SQLiteException {
        String path = DATABASE_PATH + DATABASE_NAME;
        database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        return database;
    }

    @Override
    public synchronized void close() {
        if (database != null) {
            database.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}