package com.if41.rspalembang.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServer {
//      private static final String baseURL = "https://webapirumahsakitpalembang.000webhostapp.com/";
//      private static final String baseURL = "http://localhost:8000/api/";
        //jika pakai emulator
        //private static final String baseURL = "http://10.0.2.2:8000/api/";
        //jika menggaunakan ngrok
        private static final String baseURL = "https://c04a-116-90-214-75.ap.ngrok.io/api/";    //sesuaikan dengan ngrok
//    private static final String baseURL = "https://cobacobalagi888.000webhostapp.com/";
//    private static final String baseURL = "https://webapirumahsakitpalembang.000webhostapp.com/";
    private static Retrofit retro;

    public static Retrofit konekRetrofit(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if(retro == null){
            retro = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retro;
    }
}