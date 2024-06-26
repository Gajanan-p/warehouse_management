package com.ssinfomate.warehousemanagement.webservices.save_stock_trans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RequestBusinessLocation {

    @SerializedName("coID")
    @Expose
    private String coID;

    /**
     * No args constructor for use in serialization
     *
     */
    public RequestBusinessLocation() {
    }

    /**
     *
     * @param coID
     */
    public RequestBusinessLocation(String coID) {
        super();
        this.coID = coID;
    }

    public String getCoID() {
        return coID;
    }

    public void setCoID(String coID) {
        this.coID = coID;
    }

}