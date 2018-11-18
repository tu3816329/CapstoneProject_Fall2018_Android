package com.example.capstone.mathnote_capstone.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.mathnote_capstone.R;
import com.example.capstone.mathnote_capstone.adapter.MathformListAdapter;
import com.example.capstone.mathnote_capstone.database.MathFormulasDao;
import com.example.capstone.mathnote_capstone.model.Exercise;
import com.example.capstone.mathnote_capstone.model.Lesson;
import com.example.capstone.mathnote_capstone.model.Mathform;
import com.example.capstone.mathnote_capstone.utils.AppUtils;

import java.util.List;
import java.util.Objects;

public class WebviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Lesson info
        final Lesson lesson = (Lesson) getIntent().getExtras().getSerializable("lesson");
        String activity = getIntent().getExtras().getString("activity", "");
        final ImageView favoriteIv = findViewById(R.id.favorite_iv2);
        if(activity.equals("favorite")) {
            favoriteIv.setVisibility(View.INVISIBLE);
        }
        TextView titleTv = findViewById(R.id.webview_title_tv);
        titleTv.setText(lesson.getLessonTitle());
        // Favorite icon
        final MathFormulasDao dao = new MathFormulasDao(this);

        boolean isFavorite = dao.isFavoriteLesson(lesson.getId());
        if (isFavorite) {
            favoriteIv.setBackgroundResource(R.drawable.ic_heart_white);
        }
        favoriteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isFavorite = dao.isFavoriteLesson(lesson.getId());
                if (isFavorite) {
                    dao.setFavoriteLesson(lesson.getId(), false);
                    view.setBackgroundResource(R.drawable.ic_favorite_border_white);
                    Toast.makeText(WebviewActivity.this, "Đã xoá khỏi yêu thích", Toast.LENGTH_SHORT).show();
                } else {
                    dao.setFavoriteLesson(lesson.getId(), true);
                    view.setBackgroundResource(R.drawable.ic_heart_white);
                    Toast.makeText(WebviewActivity.this, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Mathform recyclerview
        RecyclerView mathformRv = findViewById(R.id.mathform_rv);
        mathformRv.setLayoutManager(new LinearLayoutManager(this));
        List<Mathform> mathforms = dao.getMathformByLesson(lesson.getId());
        MathformListAdapter adapter = new MathformListAdapter(this, mathforms);
        mathformRv.setAdapter(adapter);

        // WebView configuration
        WebView webView = findViewById(R.id.webview);
        webView.setWebViewClient(new MyBrowser());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);

        // Get mathforms
        String data = AppUtils.MATHJAX1 + lesson.getLessonContent() + AppUtils.MATHJAX2;
        webView.loadDataWithBaseURL(null, data.toString(), "text/html", "utf-8", "");
        webView.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
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

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}