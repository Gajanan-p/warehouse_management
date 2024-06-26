package com.ssinfomate.warehousemanagement.webservices.save_stock_trans;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stocktransferdetail {

    @SerializedName("Stock_Id")
    @Expose
    private String stockId;
    @SerializedName("ScanQty")
    @Expose
    private Integer scanQty;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("remark")
    @Expose
    private String remark;

    /**
     * No args constructor for use in serialization
     *
     */
    public Stocktransferdetail() {
    }

    /**
     *
     * @param stockId
     * @param remark
     * @param barcode
     * @param scanQty
     */
    public Stocktransferdetail(String stockId, Integer scanQty, String barcode, String remark) {
        super();
        this.stockId = stockId;
        this.scanQty = scanQty;
        this.barcode = barcode;
        this.remark = remark;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public Integer getScanQty() {
        return scanQty;
    }

    public void setScanQty(Integer scanQty) {
        this.scanQty = scanQty;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}
