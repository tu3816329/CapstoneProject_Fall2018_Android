package com.example.capstone.mathnote_capstone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.capstone.mathnote_capstone.R;
import com.example.capstone.mathnote_capstone.utils.MyAsyncTask;


public class ProgressActivity extends AppCompatActivity {

    MyAsyncTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        task = new MyAsyncTask(ProgressActivity.this);
        task.execute();
    }
}