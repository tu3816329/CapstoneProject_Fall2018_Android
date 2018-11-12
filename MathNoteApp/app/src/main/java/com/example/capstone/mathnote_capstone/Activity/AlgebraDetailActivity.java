package com.example.capstone.mathnote_capstone.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstone.mathnote_capstone.adapter.AlgebraDetailAdapter;
import com.example.capstone.mathnote_capstone.R;
import com.example.capstone.mathnote_capstone.database.MathFormulasDao;
import com.example.capstone.mathnote_capstone.model.Lesson;
import com.example.capstone.mathnote_capstone.model.Quiz;
import com.example.capstone.mathnote_capstone.model.SearchResults;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class AlgebraDetailActivity extends AppCompatActivity implements Serializable {

    String title = "";
    private MathFormulasDao dao = null;
    private static int categoryId = 0;
    private static final int QUIZ_REQUEST_CODE = 2;
    private boolean isCreate = false;

    @BindView(R.id.listAlgebraDetail)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algebra_detail);
        isCreate = true;
        //
        Toolbar toolbar = findViewById(R.id.toolbar);
        final TextView categoryTitleTv = findViewById(R.id.category_title_tv);
        // Set title
        categoryId = getIntent().getExtras().getInt("categoryid");
        title = getIntent().getExtras().getString("categoryname");
        categoryTitleTv.setText(title);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.listAlgebraDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final AlgebraDetailAdapter adapter = new AlgebraDetailAdapter(this);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        );
        recyclerView.setAdapter(adapter);

        // Get lesson items
        dao = new MathFormulasDao(this);
        List<Lesson> lessons = dao.getLessonsByChapter(categoryId);
        if (categoryId == -1) {
            SearchResults searchResults = (SearchResults) getIntent().getExtras().getSerializable("results");
            if (searchResults != null) {
                lessons = searchResults.getLessons();
            }
        }
        adapter.setLessons(lessons);

        ImageView quizIv = findViewById(R.id.quiz_iv);
        quizIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(AlgebraDetailActivity.this, QuizActivity.class);
                intent.putExtra("categoryid", categoryId);
                final int lessonId = dao.getNextQuizId(categoryId);

                if (lessonId == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AlgebraDetailActivity.this);
                    builder.setTitle("Đặt lại tiến trình học");
                    builder.setMessage("Bạn đã hoàn thành tất cả các bài quiz của chương này.\n" +
                            "Bạn có muốn bắt đầu lại không?");
                    builder.setCancelable(false);
                    builder.setNegativeButton("Không", null);
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dao.resetQuizByChapter(categoryId);
                            dao.updateChapterProgress(categoryId);
                            startActivityForResult(intent, QUIZ_REQUEST_CODE);
                            overridePendingTransition(R.anim.enter, R.anim.exit);
                        }
                    });
                    builder.show();
                } else {
                    if (dao.isQuizOnGoing(lessonId)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AlgebraDetailActivity.this);
                        builder.setTitle("Tiếp tục làm quiz");
                        builder.setMessage("Bạn chưa hoàn thành bài quiz gần nhất.\n" +
                                "Tiếp tục?");
                        builder.setCancelable(false);
                        builder.setNeutralButton("Bắt đầu lại", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dao.resetQuizByLesson(lessonId);
                                startActivityForResult(intent, QUIZ_REQUEST_CODE);
                                overridePendingTransition(R.anim.enter, R.anim.exit);
                            }
                        });
                        builder.setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivityForResult(intent, QUIZ_REQUEST_CODE);
                                overridePendingTransition(R.anim.enter, R.anim.exit);
                            }
                        });
                        builder.show();
                    } else {
                        startActivityForResult(intent, QUIZ_REQUEST_CODE);
                        overridePendingTransition(R.anim.enter, R.anim.exit);
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isCreate) {
            isCreate = false;
            List<Lesson> lessons = dao.getLessonsByChapter(categoryId);
            if (categoryId == -1) {
                SearchResults searchResults = (SearchResults) getIntent().getExtras().getSerializable("results");
                if (searchResults != null) {
                    lessons = searchResults.getLessons();
                }
            }
            AlgebraDetailAdapter adapter = new AlgebraDetailAdapter(this);
            adapter.setLessons(lessons);
            recyclerView.setAdapter(adapter);
        } else {
            finish();
            overridePendingTransition(0, 0);
            startActivity(
                    new Intent(this, AlgebraDetailActivity.class)
                            .putExtra("categoryid", categoryId)
                            .putExtra("categoryname", title)
            );
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if(requestCode == QUIZ_REQUEST_CODE || requestCode == 3) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(
                        new Intent(this, AlgebraDetailActivity.class)
                        .putExtra("categoryid", categoryId)
                        .putExtra("categoryname", title)
                );
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        setResult(Activity.RESULT_OK);
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        return true;
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_OK);
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}