package com.ssinfomate.warehousemanagement.webservices.grn;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ISaveGrnAgainstPO {
    @Headers("Content-Type: application/json")
    @POST("api/User/GRNAginstPurOrder")
    Call<RequestGRNAginstPurOrder> setRequestGRNAgainstPurOrder(@Body JsonObject saveAgainstPODetails);

}
