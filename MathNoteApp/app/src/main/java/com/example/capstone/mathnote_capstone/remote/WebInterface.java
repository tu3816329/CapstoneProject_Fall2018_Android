package com.example.capstone.mathnote_capstone.remote;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.example.capstone.mathnote_capstone.activity.UserNoteActivity;

public class WebInterface {
    private Context context;
    public String data;

    public WebInterface(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void sendData(String data) {
        this.data = data;
        Intent intent = new Intent(context, UserNoteActivity.class);
        intent.putExtra("note", this.data);
    }
}
