package com.ssinfomate.warehousemanagement.webservices.check_stock;



import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CheckStockService {

    @Headers("Content-Type: application/json")
    @POST("api/User/itemstock")
    Call<ArrayList<CheckStockModel>> listCheckStock(@Body CheckStock checkStock);

    @Headers("Content-Type: application/json")
    @POST("api/User/pricelist")
    Call<ArrayList<PriceListModel>> listPriceList(@Body CheckStock checkStock);
}
