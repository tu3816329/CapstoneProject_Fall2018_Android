package com.example.capstone.mathnote_capstone.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUtils {
    public static final String MATHJAX1 = "<script src='file:///android_asset/jquery/jquery-3.2.1.js'></script>\n" +
        "<script src='file:///android_asset/jquery/jquery-3.2.1.min.js'></script>\n" +
        "<script type=\"text/javascript\" src=\"file:///android_asset/mathjax/MathJax.js?config=TeX-AMS-MML_HTMLorMML\"></script>\n" +
        "\n" +
        "<html>\n" +
        "\t<head>\n" +
        "\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, user-scalable=yes\" />\n" +
        "\t\t<style>\n" +
        "@font-face {\n" +
        "\t\t\t\tfont-family: 'mathjax';\n" +
        "\t\t\t\tsrc: url('file:///android_asset/mathjax/fonts/HTML-CSS/TeX/otf/MathJax_Main-Regular.otf');\n" +
        "\t\t\t}" +
        "\t\t\timg{width: 100vw; max-width: 100%}\n" +
        "\t\t</style>\n" +
        "\t</head>\n" +
        "\t<body style=\"font-size:18px; width: 100vw; font-family: mathjax;\" >\n" +
        "\t\t<script type=\"text/x-mathjax-config\">\n" +
        "            MathJax.Hub.Config({\n" +
        "\t\t\t\tCommonHTML: { linebreaks: { automatic: true },EqnChunk:(MathJax.Hub.Browser.isMobile?10:50) },\n" +
        "\t\t\t\tdisplayAlign: \"left\",\n" +
        "\t\t\t\t\"HTML-CSS\": { linebreaks: { automatic: true } , preferredFont: \"STIX\"},\n" +
        "\t\t\t\textensions: [\"tex2jax.js\"],\n" +
        "\t\t\t\tmessageStyle:\"none\",\n" +
        "\t\t\t\tjax: [\"input/TeX\", \"input/MathML\",\"output/HTML-CSS\"],\n" +
        "\t\t\t\ttex2jax: {inlineMath: [['$','$'],['\\\\(','\\\\)']]}\n" +
        "            });\n" +
        "\t\t</script>\n" +
        "\t\t\n" +
        "\t\t<div id=\"content\">";
    public static final String MATHJAX2 = "</div>\n" +
            "\t\t<script>\n" +
            "\t\t\t$(\"#content\").css(\"visibility\",\"hidden\");\n" +
            "\n" +
            "\t\t\t$(\"#content\").load('@Url.Action(\"ActionResultMethod\", \"ControllerName\",{controller parameters})', function () {\n" +
            "\t\t\t  MathJax.Hub.Queue(\n" +
            "\t\t\t\t[\"Typeset\",MathJax.Hub,\"content\"],\n" +
            "\t\t\t\tfunction () {\n" +
            "\t\t\t\t  $(\"#content\").css(\"visibility\",\"\"); // may have to be \"visible\" rather than \"\"\n" +
            "\t\t\t\t}\n" +
            "\t\t\t  );\n" +
            "\t\t\t});\n" +
            "\t\t</script>\n" +
            "\t</body>";

    // Multiple choices
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
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return cm.getActiveNetworkInfo() != null && activeNetworkInfo.isConnected();
    }

    public static String getCurrentDateTime() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return format.format(date);
    }

}
