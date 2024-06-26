package com.ssinfomate.warehousemanagement.webservices.supplier;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface SupplierService {

    @Headers("Content-Type: application/json")
    @GET("api/User/SupplierList")
    Call<ArrayList<SupplierModel>> getSupplierList();

}
