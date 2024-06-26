package com.ssinfomate.warehousemanagement.webservices.stock_notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StockTransferNotificationResponse {

    @SerializedName("TransferDataDetailID")
    @Expose
    private String transferDataDetailID;
    @SerializedName("TransferDataID")
    @Expose
    private String transferDataID;
    @SerializedName("FromWarehouseId")
    @Expose
    private String fromWarehouseId;
    @SerializedName("FromWarehouse")
    @Expose
    private String fromWarehouse;
    @SerializedName("BusinessName")
    @Expose
    private String businessName;
    @SerializedName("item_Key")
    @Expose
    private String itemKey;
    @SerializedName("company_id")
    @Expose
    private String companyId;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("TransferQty")
    @Expose
    private String transferQty;
    @SerializedName("scanQty")
    @Expose
    private String scanQty;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("createdby")
    @Expose
    private String createdby;
    @SerializedName("ToLocationName")
    @Expose
    private String toLocationName;
    @SerializedName("ToWarehouse")
    @Expose
    private String toWarehouse;
    @SerializedName("AccQtyFrmWerehouse")
    @Expose
    private String accQtyFrmWerehouse;
    @SerializedName("updatedby")
    @Expose
    private String updatedby;
    @SerializedName("updateddate")
    @Expose
    private String updateddate;
    @SerializedName("canceldate")
    @Expose
    private String canceldate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("userName")
    @Expose
    private String userName;

    /**
     * No args constructor for use in serialization
     *
     */
    public StockTransferNotificationResponse() {
    }

    /**
     *
     * @param accQtyFrmWerehouse
     * @param updatedby
     * @param toLocationName
     * @param businessName
     * @param transferQty
     * @param fromWarehouseId
     * @param userName
     * @param itemKey
     * @param transferDataDetailID
     * @param canceldate
     * @param transferDataID
     * @param companyId
     * @param itemName
     * @param toWarehouse
     * @param createdDate
     * @param createdby
     * @param fromWarehouse
     * @param updateddate
     * @param scanQty
     * @param status
     */
    public StockTransferNotificationResponse(String transferDataDetailID, String transferDataID, String fromWarehouseId, String fromWarehouse, String businessName, String itemKey, String companyId, String itemName, String transferQty, String scanQty, String createdDate, String createdby, String toLocationName, String toWarehouse, String accQtyFrmWerehouse, String updatedby, String updateddate, String canceldate, String status, String userName) {
        super();
        this.transferDataDetailID = transferDataDetailID;
        this.transferDataID = transferDataID;
        this.fromWarehouseId = fromWarehouseId;
        this.fromWarehouse = fromWarehouse;
        this.businessName = businessName;
        this.itemKey = itemKey;
        this.companyId = companyId;
        this.itemName = itemName;
        this.transferQty = transferQty;
        this.scanQty = scanQty;
        this.createdDate = createdDate;
        this.createdby = createdby;
        this.toLocationName = toLocationName;
        this.toWarehouse = toWarehouse;
        this.accQtyFrmWerehouse = accQtyFrmWerehouse;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.canceldate = canceldate;
        this.status = status;
        this.userName = userName;
    }

    public String getTransferDataDetailID() {
        return transferDataDetailID;
    }

    public void setTransferDataDetailID(String transferDataDetailID) {
        this.transferDataDetailID = transferDataDetailID;
    }

    public String getTransferDataID() {
        return transferDataID;
    }

    public void setTransferDataID(String transferDataID) {
        this.transferDataID = transferDataID;
    }

    public String getFromWarehouseId() {
        return fromWarehouseId;
    }

    public void setFromWarehouseId(String fromWarehouseId) {
        this.fromWarehouseId = fromWarehouseId;
    }

    public String getFromWarehouse() {
        return fromWarehouse;
    }

    public void setFromWarehouse(String fromWarehouse) {
        this.fromWarehouse = fromWarehouse;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getTransferQty() {
        return transferQty;
    }

    public void setTransferQty(String transferQty) {
        this.transferQty = transferQty;
    }

    public String getScanQty() {
        return scanQty;
    }

    public void setScanQty(String scanQty) {
        this.scanQty = scanQty;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getToLocationName() {
        return toLocationName;
    }

    public void setToLocationName(String toLocationName) {
        this.toLocationName = toLocationName;
    }

    public String getToWarehouse() {
        return toWarehouse;
    }

    public void setToWarehouse(String toWarehouse) {
        this.toWarehouse = toWarehouse;
    }

    public String getAccQtyFrmWerehouse() {
        return accQtyFrmWerehouse;
    }

    public void setAccQtyFrmWerehouse(String accQtyFrmWerehouse) {
        this.accQtyFrmWerehouse = accQtyFrmWerehouse;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public String getUpdateddate() {
        return updateddate;
    }

    public void setUpdateddate(String updateddate) {
        this.updateddate = updateddate;
    }

    public String getCanceldate() {
        return canceldate;
    }

    public void setCanceldate(String canceldate) {
        this.canceldate = canceldate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}