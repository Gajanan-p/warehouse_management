package com.ssinfomate.warehousemanagement.ui.stockcorrection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class HeldStockCorrectionModel {

    @SerializedName("Stock_Id")
    @Expose
    private String stockId;
    @SerializedName("Item_Name")
    @Expose
    private String itemName;
    @SerializedName("clqty")
    @Expose
    private String clqty;
    @SerializedName("unit_name")
    @Expose
    private String unitName;
    @SerializedName("stk_type")
    @Expose
    private String stkType;
    @SerializedName("warehouse")
    @Expose
    private String warehouse;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("User_name")
    @Expose
    private String userName;
    @SerializedName("updatetdQty")
    @Expose
    private String updatetdQty;
    @SerializedName("remark")
    @Expose
    private String remark;

    /**
     * No args constructor for use in serialization
     *
     */
    public HeldStockCorrectionModel() {
    }

    /**
     *
     * @param itemName
     * @param createdDate
     * @param unitName
     * @param stockId
     * @param clqty
     * @param warehouse
     * @param userName
     * @param stkType
     * @param updatetdQty
     */
    public HeldStockCorrectionModel(String stockId, String itemName, String clqty, String unitName, String stkType, String warehouse, String createdDate, String userName, String updatetdQty,String remark) {
        super();
        this.stockId = stockId;
        this.itemName = itemName;
        this.clqty = clqty;
        this.unitName = unitName;
        this.stkType = stkType;
        this.warehouse = warehouse;
        this.createdDate = createdDate;
        this.userName = userName;
        this.updatetdQty = updatetdQty;
        this.remark = remark;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getClqty() {
        return clqty;
    }

    public void setClqty(String clqty) {
        this.clqty = clqty;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getStkType() {
        return stkType;
    }

    public void setStkType(String stkType) {
        this.stkType = stkType;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUpdatetdQty() {
        return updatetdQty;
    }

    public void setUpdatetdQty(String updatetdQty) {
        this.updatetdQty = updatetdQty;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}