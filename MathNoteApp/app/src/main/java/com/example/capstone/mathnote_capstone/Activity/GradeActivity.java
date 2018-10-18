package com.example.capstone.mathnote_capstone.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.capstone.mathnote_capstone.Adapter.GradeAdapter;
import com.example.capstone.mathnote_capstone.Model.Grade;
import com.example.capstone.mathnote_capstone.R;

import java.util.ArrayList;

public class GradeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GradeAdapter adapter;
    private ArrayList<Grade> grades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        recyclerView = (RecyclerView) findViewById(R.id.rvGrade);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        grades = new ArrayList<>();
        adapter = new GradeAdapter(this, grades);

        recyclerView.setAdapter(adapter);

        createListData();
    }

    private void createListData() {
        Grade grade = new Grade("Lớp 10", 15, String.valueOf(R.drawable.img000));
        grades.add(grade);

        grade = new Grade("Lớp 11", 20, String.valueOf(R.drawable.img000));
        grades.add(grade);

        grade = new Grade("Lớp 12", 28,  String.valueOf(R.drawable.img000));
        grades.add(grade);



        adapter.notifyDataSetChanged();
    }
}
