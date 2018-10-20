package com.example.capstone.mathnote_capstone.remote;

import com.example.capstone.mathnote_capstone.model.ResponseData;
import com.example.capstone.mathnote_capstone.model.Version;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MathFormulasService {

    @GET("check-database-version/")
    Call<List<Version>> checkDatabaseVersion(@Query("currentVersionId") int currentVersionId);

    @GET("update-new-data/")
    Call<ResponseData> updateNewData(@Query("currentVersionId") int currentVersionId);
}