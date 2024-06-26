package com.ssinfomate.warehousemanagement.webservices.grn;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IGRN {
    @Headers("Content-Type: application/json")
    @POST("api/User/GRNWithoutPurOrder")
    Call<RequestGRNWithoutPurOrder> setSaveWithoutPurOrderData(@Body JsonObject saveWithoutPoDetails);

    @Headers("Content-Type: application/json")
    @POST("api/User/GRNWithPurOrder")
    Call<ArrayList<GrnWithPurOrderModel>> listGrnWithPurOrders(@Body RequestGrnWithPurOrderModel requestGrnWithPurOrderModel );

    @Headers("Content-Type: application/json")
    @POST("api/User/GRNLIst")
    Call<ArrayList<HeldGrnListModel>> getListPickHeldGrn(@Body RequestHeldGrn requestHeldGrn );

}
