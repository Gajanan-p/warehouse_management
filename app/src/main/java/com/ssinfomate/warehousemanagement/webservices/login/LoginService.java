package com.ssinfomate.warehousemanagement.webservices.login;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginService {
    @Headers("Content-Type: application/json")
    @POST("api/User/Login")
    Call<ArrayList<UserModel>> listUser(@Body User user);
}
