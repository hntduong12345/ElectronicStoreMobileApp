package com.example.electronicstoremobileapp.apiClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
   //private static String BASE_URL = "http://10.0.2.2:7181/";
    private static String BASE_URL = "http://10.0.3.2:5191/";
    private static Retrofit retrofit;
    private static Gson gson = new GsonBuilder()
            .create();

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static <T> T getServiceClient(Class<T> serviceClient) {
        return ApiClient.getClient().create(serviceClient);
    }
}
