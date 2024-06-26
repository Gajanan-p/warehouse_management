package com.ssinfomate.warehousemanagement.webservices.company;



import com.ssinfomate.warehousemanagement.ui.company.ChangeCompanyModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CompanyService {

    @GET("api/User/CompanyList")
    Call<ArrayList<ChangeCompanyModel>> getCompanyList();
}