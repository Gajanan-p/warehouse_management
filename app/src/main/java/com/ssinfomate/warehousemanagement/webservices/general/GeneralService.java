package com.ssinfomate.warehousemanagement.webservices.general;


import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GeneralService {

    @Headers("Content-Type: application/json")
    @POST("api/User/GeneralInsertUpdate")
    Call<GeneralModel>setGeneralModelCall(@Body GeneralModel generalModel);


    @Headers("Content-Type: application/json")
    @POST("api/User/GetGeneralList")
    Call<ArrayList<ResponseGeneralList>>getGeneralList(@Body RequestGeneralList requestGeneralList);


    @Headers("Content-Type: application/json")
    @POST("api/User/GeneralDelete")
    Call<DeleteGeneralModel>setDeleteGeneralItem(@Body DeleteGeneralModel deleteGeneralModel);

//    @Headers("Content-Type: application/json")
//    @GET("api/User/generalremarklist")
//    Call<ArrayList<GeneralRemarkModel>> getGeneralRemarkList();

    @Headers("Content-Type: application/json")
    @GET("api/User/generalremarklist")
    Call<ArrayList<GeneralRemarkModel>> getGeneralRemarkList();

}
