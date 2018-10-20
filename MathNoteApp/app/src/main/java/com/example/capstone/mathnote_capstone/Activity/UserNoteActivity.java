package com.example.capstone.mathnote_capstone.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class UserNoteActivity extends AppCompatActivity {

    WebView webView = null;
    Button button = null;
    EditText noteTitleEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_note);
        noteTitleEt = findViewById(R.id.note_title_et);
        webView = findViewById(R.id.output);

        webView.setWebViewClient(new MyBrowser());
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        webView.addJavascriptInterface(new WebJsInterface(), "Android");
        webView.loadUrl(APIUtils.API_URL + "math-editor");

        button = findViewById(R.id.save_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.evaluateJavascript("document.getElementById('btn-save').click();", null);
//                webView.loadUrl("javascript:document.getElementById('btn-save').click()");
            }
        });
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
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
                UserNote userNote = new UserNote(0, title, content);
                dao.saveUserNote(userNote);
                Toast.makeText(UserNoteActivity.this,
                        "Đã thêm ghi chú", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserNoteActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(UserNoteActivity.this,
                        "Xin vui lòng nhập tiêu đề", Toast.LENGTH_SHORT).show();
            }
        }
    }
}