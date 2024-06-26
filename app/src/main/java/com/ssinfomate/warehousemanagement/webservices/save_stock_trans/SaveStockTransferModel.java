package com.ssinfomate.warehousemanagement.webservices.save_stock_trans;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveStockTransferModel {

    @SerializedName("_CoBr_Id")
    @Expose
    private String coBrId;

    @SerializedName("_item_Id")
    @Expose
    private String itemId;
    @SerializedName("_clqty")
    @Expose
    private String clqty;
    @SerializedName("_allow_neg_stk")
    @Expose
    private String allowNegStk;
    @SerializedName("scan_quantity")
    @Expose
    private Integer scan_quantity;

    @SerializedName("_barcode")
    @Expose
    private String barcode;
    @SerializedName("_altbarcode1")
    @Expose
    private String altbarcode1;
    @SerializedName("_item_Name")
    @Expose
    private String itemName;
    @SerializedName("_Unit_Name")
    @Expose
    private String unitName;
    @SerializedName("_MRP")
    @Expose
    private String mrp;
    @SerializedName("_itemsubgrp_name")
    @Expose
    private String itemsubgrpName;
    @SerializedName("_item_code")
    @Expose
    private String itemCode;
    @SerializedName("_itemsubgrp_Id")
    @Expose
    private String itemsubgrpId;
    @SerializedName("_item_status")
    @Expose
    private String itemStatus;
    @SerializedName("_conv_qty")
    @Expose
    private String convQty;
    @SerializedName("adj_status")
    @Expose
    private String adjStatus;
    @SerializedName("msg")
    @Expose
    private Object msg;
    @SerializedName("damage_reason_name")
    @Expose
    private String damageReasonName;
    @SerializedName("damage_reason_id")
    @Expose
    private String damageReasonId;

    /**
     * No args constructor for use in serialization
     *
     */
    public SaveStockTransferModel() {
    }

    public SaveStockTransferModel(String coBrId, String itemId, String clqty, Integer scan_quantity,
                                  String barcode, String altbarcode1, String itemName, String unitName,
                                  String mrp, String itemsubgrpName, String itemCode, String itemsubgrpId,
                                  String itemStatus, String convQty, Object msg, String damageReasonName,
                                  String damageReasonId,String allowNegStk,String adjStatus) {
        this.coBrId = coBrId;
        this.itemId = itemId;
        this.clqty = clqty;
        this.scan_quantity = scan_quantity;
        this.barcode = barcode;
        this.altbarcode1 = altbarcode1;
        this.itemName = itemName;
        this.unitName = unitName;
        this.mrp = mrp;
        this.allowNegStk = allowNegStk;
        this.itemsubgrpName = itemsubgrpName;
        this.itemCode = itemCode;
        this.itemsubgrpId = itemsubgrpId;
        this.itemStatus = itemStatus;
        this.convQty = convQty;
        this.msg = msg;
        this.damageReasonName = damageReasonName;
        this.damageReasonId = damageReasonId;
        this.adjStatus = adjStatus;
    }

    public String getCoBrId() {

        return coBrId;
    }

    public void setCoBrId(String coBrId) {
        this.coBrId = coBrId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getClqty() {
        return clqty;
    }

    public void setClqty(String clqty) {
        this.clqty = clqty;
    }

    public Integer getScan_quantity() {
        return scan_quantity;
    }

    public void setScan_quantity(Integer scan_quantity) {
        this.scan_quantity = scan_quantity;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getAltbarcode1() {
        return altbarcode1;
    }

    public void setAltbarcode1(String altbarcode1) {
        this.altbarcode1 = altbarcode1;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getItemsubgrpName() {
        return itemsubgrpName;
    }

    public void setItemsubgrpName(String itemsubgrpName) {
        this.itemsubgrpName = itemsubgrpName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemsubgrpId() {
        return itemsubgrpId;
    }

    public void setItemsubgrpId(String itemsubgrpId) {
        this.itemsubgrpId = itemsubgrpId;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getAllowNegStk() {
        return allowNegStk;
    }

    public void setAllowNegStk(String allowNegStk) {
        this.allowNegStk = allowNegStk;
    }

    public String getConvQty() {
        return convQty;
    }

    public void setConvQty(String convQty) {
        this.convQty = convQty;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public String getDamageReasonName() {
        return damageReasonName;
    }

    public void setDamageReasonName(String damageReasonName) {
        this.damageReasonName = damageReasonName;
    }

    public String getDamageReasonId() {
        return damageReasonId;
    }

    public void setDamageReasonId(String damageReasonId) {
        this.damageReasonId = damageReasonId;
    }

    public String getAdjStatus() {
        return adjStatus;
    }

    public void setAdjStatus(String adjStatus) {
        this.adjStatus = adjStatus;
    }


}