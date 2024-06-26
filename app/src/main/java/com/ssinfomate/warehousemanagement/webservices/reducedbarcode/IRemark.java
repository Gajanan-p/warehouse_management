package com.ssinfomate.warehousemanagement.webservices.reducedbarcode;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface IRemark {

    @Headers("Content-Type: application/json")
    @GET("api/User/reducedbarcoderemarklist")
    Call<ArrayList<ReducedBarcodeRemarkList>> getRemarkList();

    }
