package com.ssinfomate.warehousemanagement.webservices.save_stock_trans;


import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface StockTransferService {
    @Headers("Content-Type: application/json")
    @POST("api/User/StockTransfer")
    Call<SaveStockTransfer> setSaveStockData(@Body JsonObject saveStockTransfer);

    @Headers("Content-Type: application/json")
    @POST("api/User/StockTransferNotificationUpdate")
    Call<SaveStockDetails> setUpdateStatusStockData(@Body JsonObject saveStockDetails);

    @Headers("Content-Type: application/json")
    @POST("api/User/AcceptQtyUpdateToWerehouse")
    Call<UpdateSaveStockDetails> setUpdateQuantityStockData(@Body JsonObject saveStockDetails);


    @POST("api/User/HeldStockTransfer")
    Call<ArrayList<PickHeldStockTransferModel>> getPickHeldStockData(@Body RequestPickHeldStockTransfer requestPickHeldStockTransfer);
}
