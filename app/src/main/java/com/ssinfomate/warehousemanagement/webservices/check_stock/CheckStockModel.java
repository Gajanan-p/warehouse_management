package com.ssinfomate.warehousemanagement.webservices.check_stock;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckStockModel {

    @SerializedName("_CoBr_Id")
    @Expose
    private String coBrId;
    @SerializedName("_item_id")
    @Expose
    private String itemId;
    @SerializedName("_clqty")
    @Expose
    private String clqty;
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
    @SerializedName("_allow_neg_stk")
    @Expose
    private String allowNegStk;
    @SerializedName("_itemsubgrp_name")
    @Expose
    private String itemsubgrpName;
    @SerializedName("_item_code")
    @Expose
    private String itemCode;
    @SerializedName("_itemsubgrp_id")
    @Expose
    private String itemsubgrpId;
    @SerializedName("_item_status")
    @Expose
    private String itemStatus;
    @SerializedName("_conv_qty")
    @Expose
    private String convQty;
    @SerializedName("_vat_rate")
    @Expose
    private String vatRate;
    @SerializedName("_SaleRateBeforeTax")
    @Expose
    private String saleRateBeforeTax;
    @SerializedName("_SaleRate")
    @Expose
    private String saleRate;
    @SerializedName("newsaleratebeforetax")
    @Expose
    private String newsaleratebeforetax;
    @SerializedName("newsalerate")
    @Expose
    private String newsalerate;
    @SerializedName("noofstrickertoprint")
    @Expose
    private String noofstrickertoprint;
    @SerializedName("_casesize_qty")
    @Expose
    private String casesizeQty;
    @SerializedName("reteper")
    @Expose
    private String rateper;
    @SerializedName("msg")
    @Expose
    private Object msg;
    @SerializedName("Remark_Name")
    @Expose
    private String remarkName;
    @SerializedName("remark_Id")
    @Expose
    private String remarkId;
    @SerializedName("_Stk_From")
    @Expose
    private String stkFrom;
    /**
     * No args constructor for use in serialization
     *
     */
    public CheckStockModel() {
    }

    /**
     * @param vatRate
     * @param msg
     * @param itemsubgrpId
     * @param unitName
     * @param itemCode
     * @param clqty
     * @param mrp
     * @param itemId
     * @param itemName
     * @param convQty
     * @param itemStatus
     * @param coBrId
     * @param barcode
     * @param itemsubgrpName
     * @param altbarcode1
     *
     */
    public CheckStockModel(String coBrId, String itemId, String clqty, String barcode,
                           String altbarcode1, String itemName, String unitName, String mrp,
                           String itemsubgrpName, String itemCode, String itemsubgrpId,
                           String itemStatus, String convQty, String vatRate,
                           String casesizeQty, String saleRateBeforeTax, String saleRate,
                           String newsaleratebeforetax, String allowNegStk,String newsalerate,String rateper,
                           String noofstrickertoprint,Object msg,String remarkName, String stkFrom, String remarkId) {
        super();
        this.coBrId = coBrId;
        this.itemId = itemId;
        this.clqty = clqty;
        this.barcode = barcode;
        this.altbarcode1 = altbarcode1;
        this.itemName = itemName;
        this.unitName = unitName;
        this.mrp = mrp;
        this.itemsubgrpName = itemsubgrpName;
        this.itemCode = itemCode;
        this.allowNegStk = allowNegStk;
        this.itemsubgrpId = itemsubgrpId;
        this.itemStatus = itemStatus;
        this.convQty = convQty;
        this.vatRate = vatRate;
        this.casesizeQty = casesizeQty;
        this.saleRateBeforeTax = saleRateBeforeTax;
        this.saleRate = saleRate;
        this.newsaleratebeforetax = newsaleratebeforetax;
        this.newsalerate = newsalerate;
        this.noofstrickertoprint = noofstrickertoprint;
        this.rateper = rateper;
        this.msg = msg;
        this.remarkName = remarkName;
        this.remarkId = remarkId;
        this.stkFrom = stkFrom;
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

    public void setItemId(String itemKey) {
        this.itemId = itemId;
    }

    public String getClqty() {
        return clqty;
    }

    public void setClqty(String clqty) {
        this.clqty = clqty;
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

    public String getAllowNegStk() {
        return allowNegStk;
    }

    public void setAllowNegStk(String allowNegStk) {
        this.allowNegStk = allowNegStk;
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

    public String getConvQty() {
        return convQty;
    }

    public void setConvQty(String convQty) {
        this.convQty = convQty;
    }

    public String getVatRate() {
        return vatRate;
    }

    public void setVatRate(String vatRate) {
        this.vatRate = vatRate;
    }

    public String getCasesizeQty() {
        return casesizeQty;
    }

    public void setCasesizeQty(String casesizeQty) {
        this.casesizeQty = casesizeQty;
    }

    public String getSaleRateBeforeTax() {
        return saleRateBeforeTax;
    }

    public void setSaleRateBeforeTax(String saleRateBeforeTax) {
        this.saleRateBeforeTax = saleRateBeforeTax;
    }

    public String getSaleRate() {
        return saleRate;
    }

    public void setSaleRate(String saleRate) {
        this.saleRate = saleRate;
    }

    public String getNewsaleratebeforetax() {
        return newsaleratebeforetax;
    }

    public void setNewsaleratebeforetax(String newsaleratebeforetax) {
        this.newsaleratebeforetax = newsaleratebeforetax;
    }

    public String getNewsalerate() {
        return newsalerate;
    }

    public void setNewsalerate(String newsalerate) {
        this.newsalerate = newsalerate;
    }

    public String getNoofstrickertoprint() {
        return noofstrickertoprint;
    }

    public void setNoofstrickertoprint(String noofstrickertoprint) {
        this.noofstrickertoprint = noofstrickertoprint;
    }

    public String getRateper() {
        return rateper;
    }

    public void setRateper(String rateper) {
        this.rateper = rateper;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }

    public String getRemarkId() {
        return remarkId;
    }

    public String getStkFrom() {
        return stkFrom;
    }

    public void setStkFrom(String stkFrom) {
        this.stkFrom = stkFrom;
    }

    public void setRemarkId(String remarkId) {
        this.remarkId = remarkId;
    }
}