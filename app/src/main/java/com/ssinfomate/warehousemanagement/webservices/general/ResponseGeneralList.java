package com.ssinfomate.warehousemanagement.webservices.general;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseGeneralList {

    @SerializedName("GeneralID")
    @Expose
    private String generalID;
    @SerializedName("CoBr_Name")
    @Expose
    private String coBrName;
    @SerializedName("Item_Name")
    @Expose
    private String itemName;
    @SerializedName("Remark1")
    @Expose
    private String remark1;
    @SerializedName("Remark2")
    @Expose
    private String remark2;
    @SerializedName("Unit_Name")
    @Expose
    private String unitName;
    @SerializedName("CreatedBy")
    @Expose
    private String createdBy;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("UpdatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("UpdatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("ClQty")
    @Expose
    private String clQty;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResponseGeneralList() {
    }

    /**
     *
     * @param itemName
     * @param createdDate
     * @param updatedBy
     * @param coBrName
     * @param unitName
     * @param createdBy
     * @param clQty
     * @param generalID
     * @param updatedDate
     * @param remark1
     * @param remark2
     */
    public ResponseGeneralList(String generalID, String coBrName, String itemName, String remark1, String remark2, String unitName, String createdBy, String createdDate, String updatedBy, String updatedDate, String clQty) {
        super();
        this.generalID = generalID;
        this.coBrName = coBrName;
        this.itemName = itemName;
        this.remark1 = remark1;
        this.remark2 = remark2;
        this.unitName = unitName;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.clQty = clQty;
    }

    public String getGeneralID() {
        return generalID;
    }

    public void setGeneralID(String generalID) {
        this.generalID = generalID;
    }

    public String getCoBrName() {
        return coBrName;
    }

    public void setCoBrName(String coBrName) {
        this.coBrName = coBrName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getClQty() {
        return clQty;
    }

    public void setClQty(String clQty) {
        this.clQty = clQty;
    }

}