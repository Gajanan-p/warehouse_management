package com.ssinfomate.warehousemanagement.webservices.reducedbarcode;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RateCatListModel {

    @SerializedName("ratecat_id")
    @Expose
    private String ratecatId;
    @SerializedName("ratecat_Name")
    @Expose
    private String ratecatName;

    /**
     * No args constructor for use in serialization
     *
     */
    public RateCatListModel() {
    }

    /**
     *
     * @param ratecatId
     * @param ratecatName
     */
    public RateCatListModel(String ratecatId, String ratecatName) {
        super();
        this.ratecatId = ratecatId;
        this.ratecatName = ratecatName;
    }

    public String getRatecatId() {
        return ratecatId;
    }

    public void setRatecatId(String ratecatId) {
        this.ratecatId = ratecatId;
    }

    public String getRatecatName() {
        return ratecatName;
    }

    public void setRatecatName(String ratecatName) {
        this.ratecatName = ratecatName;
    }

}