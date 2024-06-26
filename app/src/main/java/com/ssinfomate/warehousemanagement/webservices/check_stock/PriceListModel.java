package com.ssinfomate.warehousemanagement.webservices.check_stock;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PriceListModel {

    @SerializedName("_CoBr_Id")
    @Expose
    private String coBrId;
    @SerializedName("_item_id")
    @Expose
    private String itemId;
    @SerializedName("_mian_barcode")
    @Expose
    private Object mianBarcode;
    @SerializedName("_item_Name")
    @Expose
    private String itemName;
    @SerializedName("_allow_neg_stk")
    @Expose
    private String allowNegStk;
    @SerializedName("_Unit_Name")
    @Expose
    private String unitName;
    @SerializedName("_MRP")
    @Expose
    private String mrp;
    @SerializedName("_clqty")
    @Expose
    private String clqty;
    @SerializedName("_altbarcode1")
    @Expose
    private String altbarcode1;
    @SerializedName("_altbarcode2")
    @Expose
    private Object altbarcode2;
    @SerializedName("_itemsubgrp_name")
    @Expose
    private String itemsubgrpName;
    @SerializedName("_item_code")
    @Expose
    private String itemCode;
    @SerializedName("_vat_rate")
    @Expose
    private Object vatRate;
    @SerializedName("_barcode")
    @Expose
    private String barcode;
    @SerializedName("_itemsubgrp_id")
    @Expose
    private String itemsubgrpId;
    @SerializedName("_item_status")
    @Expose
    private String itemStatus;
    @SerializedName("_casesize_qty")
    @Expose
    private String casesizeQty;
    @SerializedName("_Grading")
    @Expose
    private String grading;
    @SerializedName("_Stk_From")
    @Expose
    private Object stkFrom;
    @SerializedName("_Stk_Key")
    @Expose
    private Object stkKey;
    @SerializedName("_conv_qty")
    @Expose
    private String convQty;
    @SerializedName("_SaleRateBeforeTax")
    @Expose
    private String saleRateBeforeTax;
    @SerializedName("_WasSaleRate")
    @Expose
    private String wasSaleRate;
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
    @SerializedName("Remark_Name")
    @Expose
    private String remarkName;
    @SerializedName("remark_Id")
    @Expose
    private String remarkId;
    @SerializedName("reteper")
    @Expose
    private String rateper;
    @SerializedName("_Stkcatrt_Id")
    @Expose
    private String stkcatrtId;
    @SerializedName("_status")
    @Expose
    private String status;
    @SerializedName("_ud_weekImported")
    @Expose
    private String udWeekImported;
    @SerializedName("_DRSRate")
    @Expose
    private String dRSRate;
    @SerializedName("msg")
    @Expose
    private Object msg;

    /**
     * No args constructor for use in serialization
     *
     */
    public PriceListModel() {
    }

    /**
     *
     * @param msg
     * @param saleRate
     * @param unitName
     * @param grading
     * @param stkKey
     * @param itemsubgrpId
     * @param itemCode
     * @param vatRate
     * @param clqty
     * @param mrp
     * @param stkFrom
     * @param itemId
     * @param mianBarcode
     * @param itemName
     * @param convQty
     * @param itemStatus
     * @param saleRateBeforeTax
     * @param coBrId
     * @param casesizeQty
     * @param itemsubgrpName
     * @param barcode
     * @param altbarcode1
     * @param altbarcode2
     * @param status
     * @param msg
     * @param stkKey
     * @param itemCode
     * @param vatRate
     * @param clqty
     * @param stkFrom
     * @param itemName
     * @param convQty
     * @param itemStatus
     * @param casesizeQty
     * @param itemsubgrpName
     * @param barcode
     * @param altbarcode1
     * @param altbarcode2
     * @param allowNegStk
     * @param saleRate
     * @param unitName
     * @param grading
     * @param itemsubgrpId
     * @param mrp
     * @param stkcatrtId
     * @param itemId
     * @param mianBarcode
     * @param saleRateBeforeTax
     * @param coBrId
     * @param status
     *
     */
    public PriceListModel(String coBrId, String itemId, Object mianBarcode, String itemName,
                          String unitName, String mrp, String clqty, String altbarcode1,
                          Object altbarcode2,String allowNegStk, String itemsubgrpName, String itemCode, Object vatRate,
                          String barcode, String itemsubgrpId, String itemStatus, String casesizeQty,
                          String grading, Object stkFrom, Object stkKey, String convQty,
                          String saleRateBeforeTax, String wasSaleRate,String saleRate, String newsaleratebeforetax,
                          String newsalerate,String noofstrickertoprint, String remarkName,String rateper,
                          String remarkId, String stkcatrtId, String status,String udWeekImported,String dRSRate, Object msg) {
        super();
        this.coBrId = coBrId;
        this.itemId = itemId;
        this.mianBarcode = mianBarcode;
        this.itemName = itemName;
        this.unitName = unitName;
        this.mrp = mrp;
        this.clqty = clqty;
        this.altbarcode1 = altbarcode1;
        this.altbarcode2 = altbarcode2;
        this.allowNegStk = allowNegStk;
        this.itemsubgrpName = itemsubgrpName;
        this.itemCode = itemCode;
        this.vatRate = vatRate;
        this.barcode = barcode;
        this.itemsubgrpId = itemsubgrpId;
        this.itemStatus = itemStatus;
        this.casesizeQty = casesizeQty;
        this.grading = grading;
        this.stkFrom = stkFrom;
        this.stkKey = stkKey;
        this.convQty = convQty;
        this.saleRateBeforeTax = saleRateBeforeTax;
        this.wasSaleRate=wasSaleRate;
        this.saleRate = saleRate;
        this.newsaleratebeforetax = newsaleratebeforetax;
        this.newsalerate = newsalerate;
        this.noofstrickertoprint = noofstrickertoprint;
        this.remarkName = remarkName;
        this.remarkId = remarkId;
        this.rateper = rateper;
        this.stkcatrtId = stkcatrtId;
        this.status = status;
        this.udWeekImported = udWeekImported;
        this.dRSRate =dRSRate;
        this.msg = msg;
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

    public Object getMianBarcode() {
        return mianBarcode;
    }

    public void setMianBarcode(Object mianBarcode) {
        this.mianBarcode = mianBarcode;
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

    public String getClqty() {
        return clqty;
    }

    public void setClqty(String clqty) {
        this.clqty = clqty;
    }

    public String getAltbarcode1() {
        return altbarcode1;
    }

    public void setAltbarcode1(String altbarcode1) {
        this.altbarcode1 = altbarcode1;
    }

    public Object getAltbarcode2() {
        return altbarcode2;
    }

    public void setAltbarcode2(Object altbarcode2) {
        this.altbarcode2 = altbarcode2;
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

    public Object getVatRate() {
        return vatRate;
    }

    public void setVatRate(Object vatRate) {
        this.vatRate = vatRate;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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

    public String getCasesizeQty() {
        return casesizeQty;
    }

    public void setCasesizeQty(String casesizeQty) {
        this.casesizeQty = casesizeQty;
    }

    public String getGrading() {
        return grading;
    }

    public void setGrading(String grading) {
        this.grading = grading;
    }

    public Object getStkFrom() {
        return stkFrom;
    }

    public void setStkFrom(Object stkFrom) {
        this.stkFrom = stkFrom;
    }

    public Object getStkKey() {
        return stkKey;
    }

    public void setStkKey(Object stkKey) {
        this.stkKey = stkKey;
    }

    public String getConvQty() {
        return convQty;
    }

    public void setConvQty(String convQty) {
        this.convQty = convQty;
    }

    public String getSaleRateBeforeTax() {
        return saleRateBeforeTax;
    }

    public void setSaleRateBeforeTax(String saleRateBeforeTax) {
        this.saleRateBeforeTax = saleRateBeforeTax;
    }

    public String getWasSaleRate() {
        return wasSaleRate;
    }

    public void setWasSaleRate(String wasSaleRate) {
        this.wasSaleRate = wasSaleRate;
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

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }

    public String getRemarkId() {
        return remarkId;
    }

    public void setRemarkId(String remarkId) {
        this.remarkId = remarkId;
    }

    public String getStkcatrtId() {
        return stkcatrtId;
    }

    public void setStkcatrtId(String stkcatrtId) {
        this.stkcatrtId = stkcatrtId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUdWeekImported() {
        return udWeekImported;
    }

    public void setUdWeekImported(String udWeekImported) {
        this.udWeekImported = udWeekImported;
    }

    public String getdRSRate() {
        return dRSRate;
    }

    public void setdRSRate(String dRSRate) {
        this.dRSRate = dRSRate;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

}