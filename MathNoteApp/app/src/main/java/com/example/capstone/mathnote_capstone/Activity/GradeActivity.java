package com.example.capstone.mathnote_capstone.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.capstone.mathnote_capstone.adapter.GradeAdapter;
import com.example.capstone.mathnote_capstone.database.MathFormulasDao;
import com.example.capstone.mathnote_capstone.model.Division;
import com.example.capstone.mathnote_capstone.model.Grade;
import com.example.capstone.mathnote_capstone.R;
import com.example.capstone.mathnote_capstone.model.Subject;

import java.util.ArrayList;
import java.util.List;

public class GradeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        RecyclerView recyclerView = findViewById(R.id.rvGrade);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        // Get All Grades
        MathFormulasDao dao = new MathFormulasDao(this);
        List<Grade> grades = dao.getAllGrades();
        List<Division> divisions = dao.getAllDivisions();
        List<Subject> subjects = new ArrayList<>();
        for(Grade grade : grades) {
            for(Division division : divisions) {
                subjects.add(new Subject(grade, division));
            }
        }
        GradeAdapter adapter = new GradeAdapter(this, subjects);
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