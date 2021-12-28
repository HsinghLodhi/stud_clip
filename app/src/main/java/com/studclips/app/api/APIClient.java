package com.studclips.app.api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.studclips.app.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static Retrofit retrofit = null;
    public static String BASE_URl = "https://studclips.gridironstuds.com/public/index.php/api/";

    public static Retrofit getClient() {

        Retrofit retrofit = null;
        try {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(300, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS)
                    .build();
            Gson gson = new GsonBuilder().serializeNulls().setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                   // .baseUrl(BuildConfig.SERVER_URL)
                      .baseUrl(BASE_URl)
                    .client(client)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return retrofit;
    }

    public static Retrofit getClient1() {

        Retrofit retrofit = null;
        try {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(300, TimeUnit.MINUTES)
                    .readTimeout(300, TimeUnit.MINUTES)
                    .build();
            Gson gson = new GsonBuilder().serializeNulls().setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    // .baseUrl(BuildConfig.SERVER_URL)
                    .baseUrl(BASE_URl)
                    .client(client)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return retrofit;
    }

}

