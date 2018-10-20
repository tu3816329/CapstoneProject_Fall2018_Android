package com.example.capstone.mathnote_capstone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.mathnote_capstone.R;
import com.example.capstone.mathnote_capstone.database.MathFormulasDao;
import com.example.capstone.mathnote_capstone.model.Exercise;
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
        final int lessonId = Objects.requireNonNull(getIntent().getExtras()).getInt("lessonid");
        String lessonContent = getIntent().getExtras().getString("lessoncontent");
        TextView titleTv = findViewById(R.id.webview_title_tv);
        titleTv.setText(Objects.requireNonNull(getIntent().getExtras()).getString("lessontitle"));
        // Favorite icon
        final MathFormulasDao dao = new MathFormulasDao(this);
        final ImageView favoriteIv = findViewById(R.id.favorite_iv2);

        boolean isFavorite = dao.isFavoriteLesson(lessonId);
        if (isFavorite) {
            favoriteIv.setBackgroundResource(R.drawable.ic_favorites_white);
        }
        favoriteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isFavorite = dao.isFavoriteLesson(lessonId);
                if (isFavorite) {
                    dao.removeFavoriteLesson(lessonId);
                    view.setBackgroundResource(R.drawable.ic_favorite_border_white);
                    Toast.makeText(WebviewActivity.this, "Đã xoá khỏi yêu thích", Toast.LENGTH_SHORT).show();
                } else {
                    dao.addFavoriteLesson(lessonId);
                    view.setBackgroundResource(R.drawable.ic_favorites_white);
                    Toast.makeText(WebviewActivity.this, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Webview configuration
        WebView webView = findViewById(R.id.webview);
        webView.setWebViewClient(new MyBrowser());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        // Get mathforms
        List<Mathform> mathforms = dao.getMathformByLesson(lessonId);
        StringBuilder data = new StringBuilder(AppUtils.MATHJAX1 + lessonContent + "<hr>");
        data.append("<h4 style=\"text-align: center\">Các dạng bài</h5>");
        int mfCount = 1;
        for (Mathform mathform : mathforms) {
            data.append("<h5 style=\"text-decoration: underline\">")
                    .append(mfCount++)
                    .append("/ ")
                    .append(mathform.getMathformTitle())
                    .append("</h5>")
                    .append(mathform.getMathformContent());
            // Get exercises
            List<Exercise> exercises = dao.getExercisesByMathform(mathform.getId());
            int exCount = 1;
            for (Exercise exercise : exercises) {
                data.append("<span style=\"font-weight: bold\">Bài ")
                        .append(exCount++).append(": </span>")
                        .append(exercise.getTopic());
                data.append("Đáp án: ")
                        .append(exercise.getAnswer())
                        .append("<br>");
            }
        }
        // End of data
        data.append(AppUtils.MATHJAX2);
        webView.loadDataWithBaseURL(null, data.toString(), "text/html", "utf-8", "");
        webView.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}