package com.example.capstone.mathnote_capstone.remote;

public class APIUtils {

    private APIUtils(){
    };

    public static final String API_URL = "http://mathformulas-env.svazsv2bhc.ap-southeast-1.elasticbeanstalk.com/android/";

    public static MathFormulasService getMathFormulasService() {
        return RetrofitClient.getClient(API_URL).create(MathFormulasService.class);
    }
}