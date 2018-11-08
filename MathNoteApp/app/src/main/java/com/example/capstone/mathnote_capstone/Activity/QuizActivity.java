package com.example.capstone.mathnote_capstone.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.capstone.mathnote_capstone.R;
import com.example.capstone.mathnote_capstone.database.MathFormulasDao;
import com.example.capstone.mathnote_capstone.model.Quiz;
import com.example.capstone.mathnote_capstone.model.UserChoice;
import com.example.capstone.mathnote_capstone.utils.AppUtils;

import java.util.List;
import java.util.Objects;

public class QuizActivity extends AppCompatActivity {

    private static int lessonId = 0;
    private static int categoryId = 0;
    private static List<Quiz> quizzes = null;
    private int score = 0;
    private static int questionCount = 0;
    private static int progress = 0;
    private static Quiz quiz = null;

    private WebView webView;
    private ProgressBar quizPb;
    Button answerBtn1, answerBtn2, answerBtn3, answerBtn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_quiz);

        answerBtn1 = findViewById(R.id.answer_btn_1);
        answerBtn2 = findViewById(R.id.answer_btn_2);
        answerBtn3 = findViewById(R.id.answer_btn_3);
        answerBtn4 = findViewById(R.id.answer_btn_4);
        ImageView closeIv = findViewById(R.id.quiz_close_iv);

        categoryId = getIntent().getExtras().getInt("categoryid");
        final MathFormulasDao dao = new MathFormulasDao(this);
        lessonId = dao.getNextQuizId(categoryId);
        questionCount = dao.countQuestionsByLesson(lessonId);
        quizzes = dao.getUnansweredQuestion(lessonId);
        progress = questionCount - quizzes.size();

        quizPb = findViewById(R.id.quiz_pb);
        quizPb.setMax(questionCount);
        quizPb.setScaleY(2f);
        quizPb.setProgress(progress);
        //
        webView = findViewById(R.id.quiz_content_wv);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);

        moveToNextQuestion();

        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_OK, new Intent().putExtra("score", -2));
                finish();
            }
        });

        answerBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseAnswer(0);
            }
        });

        answerBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseAnswer(1);
            }
        });

        answerBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseAnswer(2);
            }
        });

        answerBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseAnswer(3);
            }
        });
    }

    private void chooseAnswer(int choice) {
        MathFormulasDao dao = new MathFormulasDao(this);
        dao.setQuestionStatus(quiz.getQuestion().getId());
        dao.saveUserChoice(quiz.getQuestion().getId(), quiz.getChoices().get(choice).getId());
        quizzes = dao.getUnansweredQuestion(lessonId);
        int unansweredCount = quizzes.size();
        int progress = questionCount - unansweredCount;
        int corrects = 0;
        for (UserChoice userChoice : dao.getUserChoicesByLesson(lessonId)) {
            if (userChoice.getChoice().isCorrect()) {
                corrects++;
            }
        }
        if (quiz.getChoices().get(choice).isCorrect()) {
            score = corrects * 100 / questionCount;
        }
        //
        if (progress == questionCount) {
            dao.setLessonFinish(lessonId);
            dao.updateChapterProgress(categoryId);
            dao.saveQuizScore(lessonId, score);
            reviewQuiz();
        } else {
            moveToNextQuestion();
            quizPb.setProgress(progress);
        }
    }

    private void moveToNextQuestion() {
        quiz = quizzes.get(0);
        String content = AppUtils.MATHJAX1 + quiz.getQuestion().getContent() +
                "<p>A. " + quiz.getChoices().get(0).getContent().substring(3) +
                "<p>B. " + quiz.getChoices().get(1).getContent().substring(3) +
                "<p>C. " + quiz.getChoices().get(2).getContent().substring(3) +
                "<p>D. " + quiz.getChoices().get(3).getContent().substring(3) + AppUtils.MATHJAX2;
        webView.loadDataWithBaseURL(null, content, "text/html",
                "utf-8", "");
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.enter);
        webView.startAnimation(animation);
        webView.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
    }

    private void reviewQuiz() {
        answerBtn1.setEnabled(false);
        answerBtn2.setEnabled(false);
        answerBtn3.setEnabled(false);
        answerBtn4.setEnabled(false);
        Intent intent = new Intent(QuizActivity.this, ReviewQuizActivity.class);
        intent.putExtra("lessonid", lessonId);
        startActivityForResult(intent, 2);
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_OK);
        finish();
    }
}