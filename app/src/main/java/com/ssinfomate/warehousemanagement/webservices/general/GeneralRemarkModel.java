package com.ssinfomate.warehousemanagement.webservices.general;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeneralRemarkModel {

    @SerializedName("Remark_Name")
    @Expose
    private String remarkName;
    @SerializedName("remark_Id")
    @Expose
    private String remarkId;

    /**
     * No args constructor for use in serialization
     *
     */
    public GeneralRemarkModel() {
    }

    /**
     *
     * @param remarkName
     * @param remarkId
     */
    public GeneralRemarkModel(String remarkName, String remarkId) {
        super();
        this.remarkName = remarkName;
        this.remarkId = remarkId;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }

    public String getRemarkId() {
        return remarkId;
    }

    public void setRemarkId(String remarkId) {
        this.remarkId = remarkId;
    }

}