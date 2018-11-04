package com.example.capstone.mathnote_capstone.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.capstone.mathnote_capstone.R;
import com.example.capstone.mathnote_capstone.adapter.QuizReviewAdapter;
import com.example.capstone.mathnote_capstone.database.MathFormulasDao;
import com.example.capstone.mathnote_capstone.model.UserChoice;

import java.util.List;

public class ReviewQuizActivity extends AppCompatActivity {

    private RecyclerView quizReviewRv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_review_quiz);

        ProgressBar quizScorePb = findViewById(R.id.quiz_score_pb);
        TextView quizScoreTv = findViewById(R.id.quiz_score_tv);
        TextView correctsNumTv = findViewById(R.id.corrects_num_tv);
        ImageView closeReviewIv = findViewById(R.id.close_review_iv);
        quizReviewRv = findViewById(R.id.quiz_review_rv);
        quizReviewRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(quizReviewRv);

        int lessonId = getIntent().getExtras().getInt("lessonid");
        MathFormulasDao dao = new MathFormulasDao(this);
        List<UserChoice> userChoices = dao.getUserChoicesByLesson(lessonId);
        QuizReviewAdapter adapter = new QuizReviewAdapter(this, userChoices);
        quizReviewRv.setAdapter(adapter);

        dao = new MathFormulasDao(this);
        userChoices = dao.getUserChoicesByLesson(lessonId);
        int correctsNum = 0;
        for (UserChoice userChoice : userChoices) {
            if (userChoice.getChoice().isCorrect()) {
                correctsNum++;
            }
        }

        int score = dao.getQuizScore(lessonId);
        int quizSize = dao.countQuestionsByLesson(lessonId);
        String ratio = correctsNum + "/" + quizSize;
        quizScorePb.setProgress(score);
        quizScoreTv.setText(score + "%");
        correctsNumTv.setText("Bạn trả lời đúng " + ratio + " câu hỏi");

        closeReviewIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_OK);
                finish();
                overridePendingTransition(R.anim.up_to_down, R.anim.down_to_up);
            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_OK);
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}