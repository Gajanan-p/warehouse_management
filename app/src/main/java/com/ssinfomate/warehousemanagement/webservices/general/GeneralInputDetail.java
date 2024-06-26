package com.ssinfomate.warehousemanagement.webservices.general;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeneralInputDetail {
    @SerializedName("GeneralDetailIdID")
    @Expose
    private String generalDetailIdID;
    @SerializedName("Cobr_ID")
    @Expose
    private String cobrID;
    @SerializedName("Item_id")
    @Expose
    private String itemId;
    @SerializedName("Remark1")
    @Expose
    private String remark1;
    @SerializedName("Remark2")
    @Expose
    private String remark2;

    /**
     * No args constructor for use in serialization
     *
     */
    public GeneralInputDetail() {
    }

    /**
     *
     * @param generalDetailIdID
     * @param remark1
     * @param cobrID
     * @param itemId
     * @param remark2
     */
    public GeneralInputDetail(String generalDetailIdID, String cobrID, String itemId, String remark1, String remark2) {
        super();
        this.generalDetailIdID = generalDetailIdID;
        this.cobrID = cobrID;
        this.itemId = itemId;
        this.remark1 = remark1;
        this.remark2 = remark2;
    }

    public String getGeneralDetailIdID() {
        return generalDetailIdID;
    }

    public void setGeneralDetailIdID(String generalDetailIdID) {
        this.generalDetailIdID = generalDetailIdID;
    }

    public String getCobrID() {
        return cobrID;
    }

    public void setCobrID(String cobrID) {
        this.cobrID = cobrID;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

}