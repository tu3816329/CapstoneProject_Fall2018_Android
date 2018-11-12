package com.example.capstone.mathnote_capstone.remote;

public class APIUtils {

    private APIUtils(){
    };

    public static final String API_URL = "http://192.168.1.63:8080/capstone/android/";

    public static MathFormulasService getMathFormulasService() {
        return RetrofitClient.getClient(API_URL).create(MathFormulasService.class);
    }
}