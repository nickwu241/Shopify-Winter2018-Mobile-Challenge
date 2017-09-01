package com.nwu.shopifywinter2018challenge;

import com.nwu.shopifywinter2018challenge.shopifyapi.SimpleShopifyAPI;

import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton for application related constants, structures and functionality.
 */
public class App {
    //----------------------------------------------------------------------------------------------
    public static final String API_HOST = "https://shopicruit.myshopify.com/";
    public static final String ACCESS_TOKEN = "c32313df0d0ef512ca64d5b336a0d7c6";

    private static final int DEFAULT_TIMEOUT = 15;

    private static App instance;
    private SimpleShopifyAPI mShopify;
    private NumberFormat mCurrencyFormatter;

    //----------------------------------------------------------------------------------------------
    private App() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        mShopify = new Retrofit.Builder()
                .baseUrl(API_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(SimpleShopifyAPI.class);

        mCurrencyFormatter = NumberFormat.getCurrencyInstance();
    }

    //----------------------------------------------------------------------------------------------
    public static App instance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    //----------------------------------------------------------------------------------------------
    public SimpleShopifyAPI getShopifyAPI() {
        return mShopify;
    }

    //----------------------------------------------------------------------------------------------
    public NumberFormat getCurrencyFormatter() {
        return mCurrencyFormatter;
    }
}
