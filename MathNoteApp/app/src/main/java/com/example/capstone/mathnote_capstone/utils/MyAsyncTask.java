package com.example.capstone.mathnote_capstone.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.capstone.mathnote_capstone.R;
import com.example.capstone.mathnote_capstone.activity.GradeActivity;
import com.example.capstone.mathnote_capstone.activity.InstructionActivity;
import com.example.capstone.mathnote_capstone.activity.MainActivity;
import com.example.capstone.mathnote_capstone.database.MathFormulasDBHelper;
import com.example.capstone.mathnote_capstone.database.MathFormulasDao;
import com.example.capstone.mathnote_capstone.model.Grade;
import com.example.capstone.mathnote_capstone.model.ResponseData;
import com.example.capstone.mathnote_capstone.model.Version;
import com.example.capstone.mathnote_capstone.remote.APIUtils;
import com.example.capstone.mathnote_capstone.remote.MathFormulasService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

    private Activity activity;
    private Context context;
    private MathFormulasDBHelper dbHelper = null;
    private static int currentVersionId = 9999;
    private MathFormulasDao dao;
    private ProgressBar progressBar;
    private TextView updateDataTv;
    private static Boolean isFirstUsed = null;

    public MyAsyncTask(Activity activity) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        dbHelper = new MathFormulasDBHelper(context);
        dao = new MathFormulasDao(context);
        isFirstUsed = AppUtils.isFirstUsed(context);
        progressBar = activity.findViewById(R.id.update_data_pb);
        progressBar.setScaleY(3f);
        progressBar.setProgressTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorWhite)));
        updateDataTv = activity.findViewById(R.id.update_data_tv);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (isFirstUsed) {
            publishProgress(10);
            dbHelper.createDatabase();
        }
        currentVersionId = dao.getCurrentVersionId();
        if (AppUtils.checkInternetConnection(context)) {
            publishProgress(20);
            final MathFormulasService service = APIUtils.getMathFormulasService();
            Call<List<Version>> versionCall = service.checkDatabaseVersion(currentVersionId);
            versionCall.enqueue(new Callback<List<Version>>() {
                @Override
                public void onResponse(Call<List<Version>> call, Response<List<Version>> response) {
                    List<Version> versions = response.body();
                    if (versions != null && !versions.isEmpty()) {
                        publishProgress(50);
                        // Get response versions
                        dao.setCurrentVersion(versions);
                        // Get response data
                        Call<ResponseData> dataCall = service.updateNewData(currentVersionId);
                        dataCall.enqueue(new Callback<ResponseData>() {
                            @Override
                            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                                ResponseData data = response.body();
                                // Save new data
                                publishProgress(70);
                                dao.saveResponseData(data);
                                publishProgress(100);
                            }

                            @Override
                            public void onFailure(Call<ResponseData> call, Throwable t) {
                                Log.e("Task_updateData", t.getLocalizedMessage());
                                publishProgress(100);
                            }
                        });
                    } else {
                        publishProgress(90);
                    }
                }

                @Override
                public void onFailure(Call<List<Version>> call, Throwable t) {
                    Log.e("Task_checkingversion", t.getLocalizedMessage());
                    publishProgress(100);
                }
            });
        } else {
            publishProgress(100);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int number = values[0];
        Intent i = new Intent(activity, InstructionActivity.class);
        if (!isFirstUsed) {
            Grade grade = dao.getChosenGrade();
            i = new Intent(activity, MainActivity.class);
            if (grade == null) {
                i = new Intent(activity, GradeActivity.class);
            }
        }
        i.putExtra("activity", "progress");
        switch (number) {
            case 10:
                updateDataTv.setText("Initialize data ...");
                break;
            case 20:
                updateDataTv.setText("Checking versions ...");
                break;
            case 50:
                updateDataTv.setText("Updating ...");
                break;
            case 70:
                updateDataTv.setText("Saving data ...");
                break;
            case 90:
                updateDataTv.setText("Your version is up to date");
                activity.startActivity(i);
                activity.overridePendingTransition(R.anim.enter, R.anim.exit);
                break;
            case 100:
                updateDataTv.setText("App starting ...");
                activity.startActivity(i);
                activity.overridePendingTransition(R.anim.enter, R.anim.exit);
                break;
        }
        progressBar.setProgress(number);
    }
}