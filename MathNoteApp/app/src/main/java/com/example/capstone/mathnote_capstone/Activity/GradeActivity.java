package com.example.capstone.mathnote_capstone.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.capstone.mathnote_capstone.adapter.GradeAdapter;
import com.example.capstone.mathnote_capstone.database.MathFormulasDao;
import com.example.capstone.mathnote_capstone.model.Grade;
import com.example.capstone.mathnote_capstone.R;

import java.util.List;

public class GradeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        RecyclerView recyclerView = findViewById(R.id.rvGrade);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Get All Grades
        MathFormulasDao dao = new MathFormulasDao(this);
        List<Grade> grades = dao.getAllGrades();
        GradeAdapter adapter = new GradeAdapter(this, grades);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_OK);
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}