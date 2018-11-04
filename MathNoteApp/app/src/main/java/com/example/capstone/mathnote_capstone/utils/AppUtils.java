package com.example.capstone.mathnote_capstone.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUtils {

    public static final String MATHJAX1 = "<html><head>" +
            " <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, user-scalable=yes\" />" +
            "<style>img{width:\" + width + \"px; max-width: 100%}</style></head>" +
            "<body style=\"font-size:18px\" >";
    public static final String MATHJAX2 = "<script type=\"text/x-mathjax-config\">" +
            "  MathJax.Hub.Config({\n" +
            "  CommonHTML: { linebreaks: { automatic: true },EqnChunk:(MathJax.Hub.Browser.isMobile?10:50) },displayAlign: \"left\",\n" +
            "  \"HTML-CSS\": { linebreaks: { automatic: true } ," +
            "\n" +
            "    preferredFont: \"STIX\"}," +
            "extensions: [\"tex2jax.js\"],messageStyle:\"none\"," +
            "jax: [\"input/TeX\", \"input/MathML\",\"output/HTML-CSS\"]," +
            "tex2jax: {inlineMath: [['$','$'],['\\\\(','\\\\)']]}" +
            "});" +
            "</script>" +
            "<script type=\"text/javascript\" async src=\"file:///android_asset/mathjax/MathJax.js?config=TeX-AMS-MML_HTMLorMML\"></script>" +
            "" +
            "</body>" +
            "</html>";
    public static final String[] alphas = new String[]{"A. ","B. ","C. ","D. "};
    public static Boolean firstTime = null;

    public static boolean isFirstUsed(Context context) {
        if(firstTime == null) {
            SharedPreferences preferences = context.getSharedPreferences("first_time", Context.MODE_PRIVATE);
            firstTime = preferences.getBoolean("firstTime", true);

            if (firstTime) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("firstTime", false);
                editor.commit();
            }
        }
        return firstTime;
    }

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public static String getCurrentDateTime() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return format.format(date);
    }

}
