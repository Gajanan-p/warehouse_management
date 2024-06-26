package com.ssinfomate.warehousemanagement.webservices.grn;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HeldGrnListModel {

    @SerializedName("doc_Key")
    @Expose
    private String docKey;
    @SerializedName("doc_no")
    @Expose
    private String docNo;
    @SerializedName("doc_dt")
    @Expose
    private String docDt;
    @SerializedName("dlvplace")
    @Expose
    private String dlvplace;
    @SerializedName("itemid")
    @Expose
    private String itemid;
    @SerializedName("actQty")
    @Expose
    private String actQty;
    @SerializedName("PurRate")
    @Expose
    private String purRate;
    @SerializedName("taxable")
    @Expose
    private String taxable;
    @SerializedName("Tax_amt")
    @Expose
    private String taxAmt;
    @SerializedName("netamt")
    @Expose
    private String netamt;
    @SerializedName("pur_from")
    @Expose
    private String purFrom;
    @SerializedName("Item_Name")
    @Expose
    private String itemName;

    /**
     * No args constructor for use in serialization
     *
     */
    public HeldGrnListModel() {
    }

    /**
     *
     * @param itemid
     * @param netamt
     * @param itemName
     * @param purRate
     * @param taxable
     * @param dlvplace
     * @param docKey
     * @param actQty
     * @param docDt
     * @param taxAmt
     * @param purFrom
     */
    public HeldGrnListModel(String docKey, String docNo,String docDt, String dlvplace, String itemid, String actQty,
                            String purRate, String taxable, String taxAmt, String netamt, String purFrom,
                            String itemName) {
        super();
        this.docKey = docKey;
        this.docNo = docNo;
        this.docDt = docDt;
        this.dlvplace = dlvplace;
        this.itemid = itemid;
        this.actQty = actQty;
        this.purRate = purRate;
        this.taxable = taxable;
        this.taxAmt = taxAmt;
        this.netamt = netamt;
        this.purFrom = purFrom;
        this.itemName = itemName;
    }

    public String getDocKey() {
        return docKey;
    }

    public void setDocKey(String docKey) {
        this.docKey = docKey;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getDocDt() {
        return docDt;
    }

    public void setDocDt(String docDt) {
        this.docDt = docDt;
    }

    public String getDlvplace() {
        return dlvplace;
    }

    public void setDlvplace(String dlvplace) {
        this.dlvplace = dlvplace;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getActQty() {
        return actQty;
    }

    public void setActQty(String actQty) {
        this.actQty = actQty;
    }

    public String getPurRate() {
        return purRate;
    }

    public void setPurRate(String purRate) {
        this.purRate = purRate;
    }

    public String getTaxable() {
        return taxable;
    }

    public void setTaxable(String taxable) {
        this.taxable = taxable;
    }

    public String getTaxAmt() {
        return taxAmt;
    }

    public void setTaxAmt(String taxAmt) {
        this.taxAmt = taxAmt;
    }

    public String getNetamt() {
        return netamt;
    }

    public void setNetamt(String netamt) {
        this.netamt = netamt;
    }

    public String getPurFrom() {
        return purFrom;
    }

    public void setPurFrom(String purFrom) {
        this.purFrom = purFrom;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

}