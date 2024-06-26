package com.ssinfomate.warehousemanagement.webservices.grn;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestCobrID {

    @SerializedName("CoBrId")
    @Expose
    private String coBrId;

    /**
     * No args constructor for use in serialization
     *
     */
    public RequestCobrID() {
    }

    /**
     *
     * @param coBrId
     */
    public RequestCobrID(String coBrId) {
        super();
        this.coBrId = coBrId;
    }

    public String getCoBrId() {
        return coBrId;
    }

    public void setCoBrId(String coBrId) {
        this.coBrId = coBrId;
    }

}