package com.ssinfomate.warehousemanagement.webservices.general;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestGeneralList {

    @SerializedName("CoBrID")
    @Expose
    private String coBrID;

    /**
     * No args constructor for use in serialization
     *
     */
    public RequestGeneralList() {
    }

    /**
     *
     * @param coBrID
     */
    public RequestGeneralList(String coBrID) {
        super();
        this.coBrID = coBrID;
    }

    public String getCoBrID() {
        return coBrID;
    }

    public void setCoBrID(String coBrID) {
        this.coBrID = coBrID;
    }

}