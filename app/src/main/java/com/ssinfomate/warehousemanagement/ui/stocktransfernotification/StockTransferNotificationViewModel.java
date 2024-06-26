package com.ssinfomate.warehousemanagement.ui.stocktransfernotification;

import androidx.lifecycle.ViewModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StockTransferNotificationViewModel extends ViewModel{

    @SerializedName("TransferDataID")
    @Expose
    private String transferDataID;
    @SerializedName("BusinessLocation")
    @Expose
    private String businessLocation;
    @SerializedName("FromWarehouse")
    @Expose
    private String fromWarehouse;
    @SerializedName("Stock_Id")
    @Expose
    private String stockId;
    @SerializedName("TransferQty")
    @Expose
    private String transferQty;
    @SerializedName("ToWarehouse")
    @Expose
    private String toWarehouse;
    @SerializedName("TransferID")
    @Expose
    private String transferID;
    @SerializedName("AccQtyFrmWerehouse")
    @Expose
    private Object accQtyFrmWerehouse;
    @SerializedName("createdBy")
    @Expose
    private Object createdBy;
    @SerializedName("updatedBy")
    @Expose
    private Object updatedBy;
    @SerializedName("createdDate")
    @Expose
    private Object createdDate;
    @SerializedName("updatedDate")
    @Expose
    private Object updatedDate;
    @SerializedName("cancelDate")
    @Expose
    private Object cancelDate;
    @SerializedName("Cobr_id")
    @Expose
    private Object cobrId;
    @SerializedName("userid")
    @Expose
    private Object userid;
    @SerializedName("ScanQty")
    @Expose
    private Integer scanQty;
    @SerializedName("Status")
    @Expose
    private String status;

    public String getTransferDataID() {
        return transferDataID;
    }

    public void setTransferDataID(String transferDataID) {
        this.transferDataID = transferDataID;
    }

    public String getBusinessLocation() {
        return businessLocation;
    }

    public void setBusinessLocation(String businessLocation) {
        this.businessLocation = businessLocation;
    }

    public String getFromWarehouse() {
        return fromWarehouse;
    }

    public void setFromWarehouse(String fromWarehouse) {
        this.fromWarehouse = fromWarehouse;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getTransferQty() {
        return transferQty;
    }

    public void setTransferQty(String transferQty) {
        this.transferQty = transferQty;
    }

    public String getToWarehouse() {
        return toWarehouse;
    }

    public void setToWarehouse(String toWarehouse) {
        this.toWarehouse = toWarehouse;
    }

    public String getTransferID() {
        return transferID;
    }

    public void setTransferID(String transferID) {
        this.transferID = transferID;
    }

    public Object getAccQtyFrmWerehouse() {
        return accQtyFrmWerehouse;
    }

    public void setAccQtyFrmWerehouse(Object accQtyFrmWerehouse) {
        this.accQtyFrmWerehouse = accQtyFrmWerehouse;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Object getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Object createdDate) {
        this.createdDate = createdDate;
    }

    public Object getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Object updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Object getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Object cancelDate) {
        this.cancelDate = cancelDate;
    }

    public Object getCobrId() {
        return cobrId;
    }

    public void setCobrId(Object cobrId) {
        this.cobrId = cobrId;
    }

    public Object getUserid() {
        return userid;
    }

    public void setUserid(Object userid) {
        this.userid = userid;
    }

    public Integer getScanQty() {
        return scanQty;
    }

    public void setScanQty(Integer scanQty) {
        this.scanQty = scanQty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}