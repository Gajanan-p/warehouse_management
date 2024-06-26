package com.ssinfomate.warehousemanagement.webservices.save_stock_trans;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PickHeldStockTransferModel {

    @SerializedName("fromwarehouse")
    @Expose
    private String fromwarehouse;
    @SerializedName("Towarehouse")
    @Expose
    private String towarehouse;
    @SerializedName("doc_key")
    @Expose
    private String docKey;
    @SerializedName("barcode_no")
    @Expose
    private String barcodeNo;
    @SerializedName("clqty")
    @Expose
    private String clqty;
    @SerializedName("adjqty")
    @Expose
    private String adjqty;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("created_dt")
    @Expose
    private String createdDt;
    @SerializedName("item_name")
    @Expose
    private String itemName;



    /**
     * No args constructor for use in serialization
     *
     */
    public PickHeldStockTransferModel() {
    }

    /**
     *
     * @param towarehouse
     * @param createdDt
     * @param barcodeNo
     * @param createdBy
     * @param adjqty
     * @param fromwarehouse
     * @param clqty
     * @param docKey
     */
    public PickHeldStockTransferModel(String fromwarehouse, String towarehouse, String docKey,
                                      String barcodeNo, String clqty, String adjqty, String createdBy,
                                      String createdDt , String itemName) {
        super();
        this.fromwarehouse = fromwarehouse;
        this.towarehouse = towarehouse;
        this.docKey = docKey;
        this.barcodeNo = barcodeNo;
        this.clqty = clqty;
        this.adjqty = adjqty;
        this.createdBy = createdBy;
        this.createdDt = createdDt;
        this.itemName = itemName;
    }

    public String getFromwarehouse() {
        return fromwarehouse;
    }

    public void setFromwarehouse(String fromwarehouse) {
        this.fromwarehouse = fromwarehouse;
    }

    public String getTowarehouse() {
        return towarehouse;
    }

    public void setTowarehouse(String towarehouse) {
        this.towarehouse = towarehouse;
    }

    public String getDocKey() {
        return docKey;
    }

    public void setDocKey(String docKey) {
        this.docKey = docKey;
    }

    public String getBarcodeNo() {
        return barcodeNo;
    }

    public void setBarcodeNo(String barcodeNo) {
        this.barcodeNo = barcodeNo;
    }

    public String getClqty() {
        return clqty;
    }

    public void setClqty(String clqty) {
        this.clqty = clqty;
    }

    public String getAdjqty() {
        return adjqty;
    }

    public void setAdjqty(String adjqty) {
        this.adjqty = adjqty;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(String createdDt) {
        this.createdDt = createdDt;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

}