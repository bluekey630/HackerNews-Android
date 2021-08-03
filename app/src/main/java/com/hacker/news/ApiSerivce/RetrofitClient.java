package com.hacker.news.ApiSerivce;

import com.hacker.news.Utils.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private APIInterface myApi;

    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApi = retrofit.create(APIInterface.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public APIInterface getMyApi() {
        return myApi;
    }
}
