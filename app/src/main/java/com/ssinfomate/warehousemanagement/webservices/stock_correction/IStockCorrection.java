package com.ssinfomate.warehousemanagement.webservices.stock_correction;



import com.ssinfomate.warehousemanagement.ui.stockcorrection.HeldStockCorrectionModel;
import com.ssinfomate.warehousemanagement.ui.stockcorrection.RequestHeldStockCorrection;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IStockCorrection {
    @POST("api/User/HeldStockCorrection")
    Call<ArrayList<HeldStockCorrectionModel>> getHeldStockCorrectionModel(@Body  RequestHeldStockCorrection requestHeldStockCorrection);

    @POST("api/User/StockCorrection")
    Call<StockCorrectionRequest> setStockCorrectionModel(@Body JsonObject stockCorrectionRequest);

    @POST("api/User/StockCorrectionReasonList")
    Call<ArrayList<DamageReasonModels>> setDamageReasonModels(@Body JsonObject damageReasonModels);
}