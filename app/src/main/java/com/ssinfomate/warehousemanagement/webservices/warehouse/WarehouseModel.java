package com.ssinfomate.warehousemanagement.webservices.warehouse;

import com.google.gson.annotations.SerializedName;



public class WarehouseModel {
    @SerializedName("_cobr_id")
    private String cobrId;

    @SerializedName("_cobr_name")
    private String cobrName;

    @SerializedName("_regdadd")
    private String regdadd;

    public WarehouseModel() {}

    public WarehouseModel(String cobrId, String cobrName, String regdadd) {
        this.cobrId = cobrId;
        this.cobrName = cobrName;
        this.regdadd = regdadd;
    }
    public String getCobrId() {
        return cobrId;
    }

    public void setCobrId(String cobrId) {
        this.cobrId = cobrId;
    }

    public String getCobrName() {
        return cobrName;
    }

    public void setCobrName(String cobrName) {
        this.cobrName = cobrName;
    }

    public String getRegdadd() {
        return regdadd;
    }

    public void setRegdadd(String regdadd) {
        this.regdadd = regdadd;
    }


}
