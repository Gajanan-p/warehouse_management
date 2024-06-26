package com.ssinfomate.warehousemanagement.webservices.grn;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SaveGrnWithPOModel {

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
    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("msg")
    @Expose
    private Object msg;

    /**
     * No args constructor for use in serialization
     *
     */
    public SaveGrnWithPOModel() {
    }

    /**
     *
     * @param msg
     * @param itemId
     * @param purrate
     * @param netamt
     * @param amount
     * @param itemName
     * @param qty
     * @param docDt
     * @param balqty
     * @param taxAmt
     */
    public SaveGrnWithPOModel(String docDt, String itemId, String qty, String balqty, String purrate, String amount, String taxAmt, String netamt, String itemName, Object msg) {
        super();
        this.docDt = docDt;
        this.itemId = itemId;
        this.qty = qty;
        this.balqty = balqty;
        this.purrate = purrate;
        this.amount = amount;
        this.taxAmt = taxAmt;
        this.netamt = netamt;
        this.itemName = itemName;
        this.msg = msg;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

}