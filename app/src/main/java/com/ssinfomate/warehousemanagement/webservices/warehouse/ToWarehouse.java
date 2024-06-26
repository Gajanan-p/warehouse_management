package com.ssinfomate.warehousemanagement.webservices.warehouse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class ToWarehouse {

    @SerializedName("ApplyId")
    @Expose
    private String applyId;
    @SerializedName("CoBrId")
    @Expose
    private String coBrId;
    @SerializedName("Co_Id")
    @Expose
    private String coId;
    @SerializedName("CoBr_Name")
    @Expose
    private String coBrName;
    @SerializedName("Address")
    @Expose
    private String address;

    /**
     * No args constructor for use in serialization
     *
     */
    public ToWarehouse() {
    }

    /**
     *
     * @param applyId
     * @param address
     * @param coBrName
     * @param coId
     * @param coBrId
     */
    public ToWarehouse(String applyId, String coBrId, String coId, String coBrName, String address) {
        super();
        this.applyId = applyId;
        this.coBrId = coBrId;
        this.coId = coId;
        this.coBrName = coBrName;
        this.address = address;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getCoBrId() {
        return coBrId;
    }

    public void setCoBrId(String coBrId) {
        this.coBrId = coBrId;
    }

    public String getCoId() {
        return coId;
    }

    public void setCoId(String coId) {
        this.coId = coId;
    }

    public String getCoBrName() {
        return coBrName;
    }

    public void setCoBrName(String coBrName) {
        this.coBrName = coBrName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}