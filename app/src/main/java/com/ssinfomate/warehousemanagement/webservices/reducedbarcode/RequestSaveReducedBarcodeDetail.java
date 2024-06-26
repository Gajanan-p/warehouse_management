package com.ssinfomate.warehousemanagement.webservices.reducedbarcode;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestSaveReducedBarcodeDetail {

    @SerializedName("newsaleratebeforetax")
    @Expose
    private String newsaleratebeforetax;
    @SerializedName("newsalerate")
    @Expose
    private String newsalerate;
    @SerializedName("oldSaleRateBeforeTax")
    @Expose
    private String oldSaleRateBeforeTax;
    @SerializedName("oldSaleRate")
    @Expose
    private String oldSaleRate;
    @SerializedName("noofstrickertoprint")
    @Expose
    private String noofstrickertoprint;
    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("RatePerc")
    @Expose
    private String ratePerc;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("stkcatrt_ids")
    @Expose
    private String stkcatrtIds;
    /**
     * No args constructor for use in serialization
     *
     */
    public RequestSaveReducedBarcodeDetail() {
    }

    /**
     *
     * @param itemId
     * @param newsaleratebeforetax
     * @param noofstrickertoprint
     * @param newsalerate
     * @param ratePerc
     */
    public RequestSaveReducedBarcodeDetail(String newsaleratebeforetax, String newsalerate,String oldSaleRateBeforeTax, String oldSaleRate,
                                           String noofstrickertoprint, String itemId, String ratePerc,
                                           String barcode, String stkcatrtIds, String remark) {
        super();
        this.newsaleratebeforetax = newsaleratebeforetax;
        this.newsalerate = newsalerate;
        this.noofstrickertoprint = noofstrickertoprint;
        this.itemId = itemId;
        this.ratePerc = ratePerc;
        this.barcode = barcode;
        this.remark = remark;
        this.oldSaleRateBeforeTax = oldSaleRateBeforeTax;
        this.oldSaleRate = oldSaleRate;
        this.stkcatrtIds = stkcatrtIds;
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

    public String getOldSaleRateBeforeTax() {
        return oldSaleRateBeforeTax;
    }

    public void setOldSaleRateBeforeTax(String oldSaleRateBeforeTax) {
        this.oldSaleRateBeforeTax = oldSaleRateBeforeTax;
    }

    public String getOldSaleRate() {
        return oldSaleRate;
    }

    public void setOldSaleRate(String oldSaleRate) {
        this.oldSaleRate = oldSaleRate;
    }

    public String getNoofstrickertoprint() {
        return noofstrickertoprint;
    }

    public void setNoofstrickertoprint(String noofstrickertoprint) {
        this.noofstrickertoprint = noofstrickertoprint;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getRatePerc() {
        return ratePerc;
    }

    public void setRatePerc(String ratePerc) {
        this.ratePerc = ratePerc;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getStkcatrtIds() {
        return stkcatrtIds;
    }

    public void setStkcatrtIds(String stkcatrtIds) {
        this.stkcatrtIds = stkcatrtIds;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}