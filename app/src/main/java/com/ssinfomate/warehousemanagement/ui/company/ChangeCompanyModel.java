package com.ssinfomate.warehousemanagement.ui.company;

import androidx.lifecycle.ViewModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ChangeCompanyModel extends ViewModel {

    @SerializedName("Co_id")
    @Expose
    private String coId;
    @SerializedName("Co_Name")
    @Expose
    private String coName;
    @SerializedName("RegdAdd")
    @Expose
    private String regdAdd;

    public ChangeCompanyModel(String coId, String coName, String regdAdd) {
        this.coId = coId;
        this.coName = coName;
        this.regdAdd = regdAdd;
    }

    public String getCoId() {
        return coId;
    }

    public void setCoId(String coId) {
        this.coId = coId;
    }

    public String getCoName() {
        return coName;
    }

    public void setCoName(String coName) {
        this.coName = coName;
    }

    public String getRegdAdd() {
        return regdAdd;
    }

    public void setRegdAdd(String regdAdd) {
        this.regdAdd = regdAdd;
    }

}