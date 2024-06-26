package com.ssinfomate.warehousemanagement.webservices.save_stock_trans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Detail {

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
    @SerializedName("AccQtyFrmWerehouse")
    @Expose
    private String accQtyFrmWerehouse;
    @SerializedName("TransferDataDetailID")
    @Expose
    private String transferDataDetailID;
    @SerializedName("updatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("ScanQty")
    @Expose
    private Integer scanQty;
    @SerializedName("Status")
    @Expose
    private String status;

    /**
     * No args constructor for use in serialization
     *
     */
    public Detail() {
    }

    /**
     *
     * @param transferDataDetailID
     * @param transferDataID
     * @param toWarehouse
     * @param accQtyFrmWerehouse
     * @param updatedBy
     * @param businessLocation
     * @param stockId
     * @param transferQty
     * @param fromWarehouse
     * @param scanQty
     * @param status
     */
    public Detail(String transferDataID, String businessLocation, String fromWarehouse, String stockId, String transferQty, String toWarehouse, String accQtyFrmWerehouse, String transferDataDetailID, String updatedBy, Integer scanQty, String status) {
        super();
        this.transferDataID = transferDataID;
        this.businessLocation = businessLocation;
        this.fromWarehouse = fromWarehouse;
        this.stockId = stockId;
        this.transferQty = transferQty;
        this.toWarehouse = toWarehouse;
        this.accQtyFrmWerehouse = accQtyFrmWerehouse;
        this.transferDataDetailID = transferDataDetailID;
        this.updatedBy = updatedBy;
        this.scanQty = scanQty;
        this.status = status;
    }

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

    public String getAccQtyFrmWerehouse() {
        return accQtyFrmWerehouse;
    }

    public void setAccQtyFrmWerehouse(String accQtyFrmWerehouse) {
        this.accQtyFrmWerehouse = accQtyFrmWerehouse;
    }

    public String getTransferDataDetailID() {
        return transferDataDetailID;
    }

    public void setTransferDataDetailID(String transferDataDetailID) {
        this.transferDataDetailID = transferDataDetailID;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
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