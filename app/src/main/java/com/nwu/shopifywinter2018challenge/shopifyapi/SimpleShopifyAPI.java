package com.nwu.shopifywinter2018challenge.shopifyapi;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface SimpleShopifyAPI {
    String KEY_ACCESS_TOKEN = "access_token";
    String KEY_FIELD = "field";

    @GET("admin/orders.json")
    Call<OrdersResponse> orders(@QueryMap Map<String, String> params);
}
