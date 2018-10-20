package com.example.capstone.mathnote_capstone.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.capstone.mathnote_capstone.R;
import com.example.capstone.mathnote_capstone.database.MathFormulasDBHelper;
import com.example.capstone.mathnote_capstone.database.MathFormulasDao;
import com.example.capstone.mathnote_capstone.model.ResponseData;
import com.example.capstone.mathnote_capstone.model.Version;
import com.example.capstone.mathnote_capstone.remote.APIUtils;
import com.example.capstone.mathnote_capstone.remote.MathFormulasService;
import com.example.capstone.mathnote_capstone.utils.AppUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    MathFormulasDBHelper dbHelper = null;
    int currentVersionId = 9999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        dbHelper = new MathFormulasDBHelper(this);
        dbHelper.createDatabase();
        //
        final MathFormulasDao dao = new MathFormulasDao(this);
        currentVersionId = dao.getCurrentVersionId();
        if (AppUtils.checkInternetConnection(this)) {
            final MathFormulasService mathFormulasService = APIUtils.getMathFormulasService();
            Call<List<Version>> versionCall = mathFormulasService.checkDatabaseVersion(currentVersionId);
            versionCall.enqueue(new Callback<List<Version>>() {
                @Override
                public void onResponse(Call<List<Version>> call, Response<List<Version>> response) {
                    List<Version> versions = response.body();
                    if (versions != null && versions.size() != 0) { // if server release new version
                        Toast.makeText(SplashActivity.this, "Updating...", Toast.LENGTH_SHORT).show();
                        // Get response versions
                        dao.setCurrentVersion(versions);
                        // Get response data
                        Call<ResponseData> newDataCall = mathFormulasService.updateNewData(currentVersionId);
                        newDataCall.enqueue(new Callback<ResponseData>() {
                            @Override
                            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                                ResponseData data = response.body();
                                dao.saveResponseData(data);
                            }

                            @Override
                            public void onFailure(Call<ResponseData> call, Throwable t) {
                                Log.e("Splash_updateDataCall", t.getLocalizedMessage());
                            }
                        });
                    } else {
                        Toast.makeText(SplashActivity.this, "Your version is up to date", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Version>> call, Throwable t) {
                    Toast.makeText(SplashActivity.this, "Checking version: No response from server", Toast.LENGTH_SHORT).show();
                }
            });
        }

        //
        setContentView(R.layout.activity_splash);
        ImageView logo = findViewById(R.id.logo);

        int splashTimeOut = 2000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                if (AppUtils.isFirstUsed(SplashActivity.this)) {
                    i = new Intent(SplashActivity.this, InstructionActivity.class);
                }
                startActivity(i);
                finish();
            }
        }, splashTimeOut);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.mysplashanimation);
        logo.startAnimation(animation);
    }
}