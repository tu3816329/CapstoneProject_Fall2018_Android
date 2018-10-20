package com.example.capstone.mathnote_capstone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.capstone.mathnote_capstone.adapter.AlgebraDetailAdapter;
import com.example.capstone.mathnote_capstone.R;
import com.example.capstone.mathnote_capstone.database.MathFormulasDao;
import com.example.capstone.mathnote_capstone.model.Lesson;
import com.example.capstone.mathnote_capstone.model.SearchResults;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class AlgebraDetailActivity extends AppCompatActivity implements Serializable {


    @BindView(R.id.listAlgebraDetail)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algebra_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);

        int categoryId = Objects.requireNonNull(getIntent().getExtras()).getInt("categoryid");
        toolbar.setTitle(getIntent().getExtras().getString("categoryname", ""));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.listAlgebraDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final AlgebraDetailAdapter adapter = new AlgebraDetailAdapter(this);
        recyclerView.setAdapter(adapter);

        // Get lesson items
        MathFormulasDao dao = new MathFormulasDao(this);
        List<Lesson> lessons = dao.getLessonsByCategory(categoryId);
        if (categoryId == -1) {
            SearchResults searchResults = (SearchResults) getIntent().getExtras().getSerializable("results");
            if(searchResults != null) {
                lessons = searchResults.getLessons();
            }
        }
        adapter.setLessons(lessons);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}