package com.ssinfomate.warehousemanagement.webservices.reducedbarcode;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IReducedBarcode {

    @Headers("Content-Type: application/json")
    @POST("api/User/ReduceBarcode")
    Call <ArrayList<SaveReducedBarcodeModel>> setSaveReducedBarcodeData(@Body JsonObject saveReducedBarcodeData);


    @Headers("Content-Type: application/json")
    @POST("api/User/ReduceBarcode")
    Call<SaveReducedBarcodeModel> getReducedBarcodeData(@Body RequestSaveReducedBarcode requestSaveReducedBarcode);


}
