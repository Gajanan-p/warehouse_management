package com.ssinfomate.warehousemanagement.webservices.warehouse;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface WarehouseService {

    @Headers("Content-Type: application/json")
    @POST("api/User/CoBrList")
    Call<ArrayList<WarehouseModel>> listWarehouse(@Body JsonObject jsonObject);

    @GET("api/User/GetListForTowerehouse")
    Call<ArrayList<ToWarehouse>> listToWarehouse();
}
