package com.ssinfomate.warehousemanagement.webservices.stock_correction;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TemplItemStockDetail {

    @SerializedName("BusinessLocation")
    @Expose
    private String businessLocation;
    @SerializedName("FromWarehouse")
    @Expose
    private String fromWarehouse;
    @SerializedName("Stock_Id")
    @Expose
    private String stockId;
    @SerializedName("Qty")
    @Expose
    private String qty;
    @SerializedName("ScanQty")
    @Expose
    private String scanQty;
    @SerializedName("StockUpdateOptionStatus")
    @Expose
    private Integer stockUpdateOptionStatus;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("DamageReason")
    @Expose
    private String damageReason;
    @SerializedName("remark")
    @Expose
    private String remark;

    /**
     * No args constructor for use in serialization
     *
     */
    public TemplItemStockDetail() {
    }

    /**
     *
     * @param businessLocation
     * @param qty
     * @param stockId
     * @param fromWarehouse
     * @param damageReason
     * @param stockUpdateOptionStatus
     * @param scanQty
     */
    public TemplItemStockDetail(String businessLocation, String fromWarehouse,
                                String stockId, String qty, String scanQty,
                                Integer stockUpdateOptionStatus,
                                String damageReason, String barcode, String remark) {
        super();
        this.businessLocation = businessLocation;
        this.fromWarehouse = fromWarehouse;
        this.stockId = stockId;
        this.qty = qty;
        this.scanQty = scanQty;
        this.stockUpdateOptionStatus = stockUpdateOptionStatus;
        this.barcode = barcode;
        this.damageReason = damageReason;
        this.remark = remark;

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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getScanQty() {
        return scanQty;
    }

    public void setScanQty(String scanQty) {
        this.scanQty = scanQty;
    }

    public Integer getStockUpdateOptionStatus() {
        return stockUpdateOptionStatus;
    }

    public void setStockUpdateOptionStatus(Integer stockUpdateOptionStatus) {
        this.stockUpdateOptionStatus = stockUpdateOptionStatus;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDamageReason() {
        return damageReason;
    }

    public void setDamageReason(String damageReason) {
        this.damageReason = damageReason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}