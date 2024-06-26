package com.ssinfomate.warehousemanagement.webservices.save_stock_trans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestPickHeldStockTransfer {

    @SerializedName("Cobr_id")
    @Expose
    private String cobrId;
    @SerializedName("userid")
    @Expose
    private String userid;

    public String getCobrId() {
        return cobrId;
    }

    public void setCobrId(String cobrId) {
        this.cobrId = cobrId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}