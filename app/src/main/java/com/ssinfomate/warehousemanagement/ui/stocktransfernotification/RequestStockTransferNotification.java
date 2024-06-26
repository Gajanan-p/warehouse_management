package com.ssinfomate.warehousemanagement.ui.stocktransfernotification;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestStockTransferNotification {

    @SerializedName("CoBrID")
    @Expose
    private String coBrID;
    @SerializedName("UserType")
    @Expose
    private String userType;

    public String getCoBrID() {
        return coBrID;
    }

    public void setCoBrID(String coBrID) {
        this.coBrID = coBrID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}