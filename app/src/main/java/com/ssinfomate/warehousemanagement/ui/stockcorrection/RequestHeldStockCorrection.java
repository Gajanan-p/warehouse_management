package com.ssinfomate.warehousemanagement.ui.stockcorrection;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestHeldStockCorrection {

    @SerializedName("cobr_id")
    @Expose
    private String cobrId;
    @SerializedName("UserType")
    @Expose
    private String userType;
    @SerializedName("StockUpdateOptionStatus")
    @Expose
    private String stockUpdateOptionStatus;

    /**
     * No args constructor for use in serialization
     *
     */
    public RequestHeldStockCorrection() {
    }

    /**
     *
     * @param userType
     * @param cobrId
     * @param stockUpdateOptionStatus
     */
    public RequestHeldStockCorrection(String cobrId, String userType,
                                      String stockUpdateOptionStatus) {
        super();
        this.cobrId = cobrId;
        this.userType = userType;
        this.stockUpdateOptionStatus = stockUpdateOptionStatus;
    }

    public String getCobrId() {
        return cobrId;
    }

    public void setCobrId(String cobrId) {
        this.cobrId = cobrId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getStockUpdateOptionStatus() {
        return stockUpdateOptionStatus;
    }

    public void setStockUpdateOptionStatus(String stockUpdateOptionStatus) {
        this.stockUpdateOptionStatus = stockUpdateOptionStatus;
    }


}