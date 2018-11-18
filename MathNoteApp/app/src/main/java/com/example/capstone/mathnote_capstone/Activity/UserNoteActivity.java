package com.example.capstone.mathnote_capstone.activity;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstone.mathnote_capstone.R;
import com.example.capstone.mathnote_capstone.database.MathFormulasDao;
import com.example.capstone.mathnote_capstone.model.UserNote;
import com.example.capstone.mathnote_capstone.remote.APIUtils;
import com.example.capstone.mathnote_capstone.utils.AppUtils;

public class UserNoteActivity extends AppCompatActivity {

    WebView webView = null;
    Button saveBtn;
    EditText noteTitleEt;

    private UserNote note = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Ghi chú");
        //
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            note = (UserNote) bundle.getSerializable("usernote");
        }
        //
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_left);
        setContentView(R.layout.activity_user_note);

        noteTitleEt = findViewById(R.id.note_title_et);
        webView = findViewById(R.id.output);
        saveBtn = findViewById(R.id.save_btn);

        webView.setWebViewClient(new MyBrowser());
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        if(AppUtils.checkInternetConnection(this)) {
            webView.addJavascriptInterface(new WebJsInterface(), "Android");
            webView.loadUrl(APIUtils.API_URL + "math-editor");
        } else {
            webView.loadDataWithBaseURL(
                    null, AppUtils.MATHJAX1 + note.getContent() + AppUtils.MATHJAX2,
                    "text/html", "utf-8", ""
            );
            webView.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
        }

        if (note != null) {
            noteTitleEt.setText(note.getTitle());
        }
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.evaluateJavascript(
                        "document.getElementById('btn-save').click();", null
                );
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(Activity.RESULT_OK);
                finish();
        }
        return true;
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(note != null) {
                webView.evaluateJavascript(
                        "editor.setMathML('" + note.getContent() + "');", null
                );
            }
        }
    }

    private class WebJsInterface {

        private WebJsInterface() {
        }

        @JavascriptInterface
        public void sendData(String d) {
            String title = noteTitleEt.getText().toString().trim();
            MathFormulasDao dao = new MathFormulasDao(UserNoteActivity.this);
            String content = d.trim();
            if (!title.isEmpty()) {
                if (note == null) {
                    UserNote userNote = new UserNote(0, title, content, AppUtils.getCurrentDateTime());
                    long id = dao.saveUserNote(userNote);
                    if (id > 0) {
//                        note = null;
                        Toast.makeText(UserNoteActivity.this,
                                "Đã thêm ghi chú", Toast.LENGTH_SHORT).show();
                        setResult(Activity.RESULT_OK);
                        finish();
                        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                    }
                } else {
                    UserNote userNote = new UserNote(note.getId(), title, content, AppUtils.getCurrentDateTime());
                    int row = dao.editUserNote(userNote);
                    if (row > 0) {
//                        note = null;
                        Toast.makeText(UserNoteActivity.this,
                                "Đã lưu thay đổi", Toast.LENGTH_SHORT).show();
                        setResult(Activity.RESULT_OK);
                        finish();
                        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                    }
                }
            } else {
                Toast.makeText(UserNoteActivity.this,
                        "Xin vui lòng nhập tiêu đề", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}