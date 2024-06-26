package com.ssinfomate.warehousemanagement.webservices.grn;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IPurchaseNumber {
    @Headers("Content-Type: application/json")
    @POST("api/User/GetPONumber")
    Call<ArrayList<PONumberListModel>> listPurchaseNumber(@Body JsonObject jsonObject);
}
