package com.ssinfomate.warehousemanagement.webservices.grn;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GrnWithPurOrderModel {

    @SerializedName("doc_dt")
    @Expose
    private String docDt;
    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("balqty")
    @Expose
    private String balqty;
    @SerializedName("purrate")
    @Expose
    private String purrate;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("tax_amt")
    @Expose
    private String taxAmt;
    @SerializedName("netamt")
    @Expose
    private String netamt;
    @SerializedName("Supl_ID")
    @Expose
    private String suplID;
    @SerializedName("msg")
    @Expose
    private Object msg;
    @SerializedName("docKey")
    @Expose
    private String docKey;
    @SerializedName("itmit")
    @Expose
    private String itmit;
    @SerializedName("taxperc")
    @Expose
    private String taxperc;
    @SerializedName("itemName")
    @Expose
    private String itemName;

    /**
     * No args constructor for use in serialization
     *
     */
    public GrnWithPurOrderModel() {
    }

    /**
     *
     * @param msg
     * @param amount
     * @param docKey
     * @param balqty
     * @param itemId
     * @param purrate
     * @param netamt
     * @param itemName
     * @param itmit
     * @param qty
     * @param taxperc
     * @param suplID
     * @param docDt
     * @param taxAmt
     */
    public GrnWithPurOrderModel(String docDt, String itemId, String qty, String balqty, String purrate, String amount, String taxAmt, String netamt, String suplID, Object msg, String docKey, String itmit, String taxperc, String itemName) {
        super();
        this.docDt = docDt;
        this.itemId = itemId;
        this.qty = qty;
        this.balqty = balqty;
        this.purrate = purrate;
        this.amount = amount;
        this.taxAmt = taxAmt;
        this.netamt = netamt;
        this.suplID = suplID;
        this.msg = msg;
        this.docKey = docKey;
        this.itmit = itmit;
        this.taxperc = taxperc;
        this.itemName = itemName;
    }

    public String getDocDt() {
        return docDt;
    }

    public void setDocDt(String docDt) {
        this.docDt = docDt;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getBalqty() {
        return balqty;
    }

    public void setBalqty(String balqty) {
        this.balqty = balqty;
    }

    public String getPurrate() {
        return purrate;
    }

    public void setPurrate(String purrate) {
        this.purrate = purrate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getSuplID() {
        return suplID;
    }

    public void setSuplID(String suplID) {
        this.suplID = suplID;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public String getDocKey() {
        return docKey;
    }

    public void setDocKey(String docKey) {
        this.docKey = docKey;
    }

    public String getItmit() {
        return itmit;
    }

    public void setItmit(String itmit) {
        this.itmit = itmit;
    }

    public String getTaxperc() {
        return taxperc;
    }

    public void setTaxperc(String taxperc) {
        this.taxperc = taxperc;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }


}