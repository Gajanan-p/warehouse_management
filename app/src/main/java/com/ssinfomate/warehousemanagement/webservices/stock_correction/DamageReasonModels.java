package com.ssinfomate.warehousemanagement.webservices.stock_correction;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DamageReasonModels {

    @SerializedName("Reason_Name")
    @Expose
    private String reasonName;
    @SerializedName("stockcorrereason_Id")
    @Expose
    private String stockcorrereasonId;

    /**
     *
     * @param reasonName
     * @param stockcorrereasonId
     */
    public DamageReasonModels(String reasonName, String stockcorrereasonId) {
        super();
        this.reasonName = reasonName;
        this.stockcorrereasonId = stockcorrereasonId;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public String getStockcorrereasonId() {
        return stockcorrereasonId;
    }

    public void setStockcorrereasonId(String stockcorrereasonId) {
        this.stockcorrereasonId = stockcorrereasonId;
    }

}
