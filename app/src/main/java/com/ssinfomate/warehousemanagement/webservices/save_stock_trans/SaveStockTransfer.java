package com.ssinfomate.warehousemanagement.webservices.save_stock_trans;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SaveStockTransfer {

    @SerializedName("stocktransferdetail")
    @Expose
    private List<Stocktransferdetail> stocktransferdetail = null;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("Cobr_id")
    @Expose
    private String cobrId;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("MachName")
    @Expose
    private String machName;
    @SerializedName("Stk_Type")
    @Expose
    private String stkType;
    @SerializedName("CorrectedBy_ID")
    @Expose
    private String correctedByID;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("tocobrid")
    @Expose
    private String tocobrid;

    /**
     * No args constructor for use in serialization
     *
     */
    public SaveStockTransfer() {
    }

    /**
     *
     * @param msg
     * @param correctedByID
     * @param tocobrid
     * @param createdBy
     * @param stocktransferdetail
     * @param cobrId
     * @param userid
     * @param machName
     * @param stkType
     * @param status
     */
    public SaveStockTransfer(List<Stocktransferdetail> stocktransferdetail, String status, String createdBy, String cobrId, String userid, String machName, String stkType, String correctedByID, String msg, String tocobrid) {
        super();
        this.stocktransferdetail = stocktransferdetail;
        this.status = status;
        this.createdBy = createdBy;
        this.cobrId = cobrId;
        this.userid = userid;
        this.machName = machName;
        this.stkType = stkType;
        this.correctedByID = correctedByID;
        this.msg = msg;
        this.tocobrid = tocobrid;
    }

    public List<Stocktransferdetail> getStocktransferdetail() {
        return stocktransferdetail;
    }

    public void setStocktransferdetail(List<Stocktransferdetail> stocktransferdetail) {
        this.stocktransferdetail = stocktransferdetail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCobrId() {
        return cobrId;
    }

    public void setCobrId(String cobrId) {
        this.cobrId = cobrId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMachName() {
        return machName;
    }

    public void setMachName(String machName) {
        this.machName = machName;
    }

    public String getStkType() {
        return stkType;
    }

    public void setStkType(String stkType) {
        this.stkType = stkType;
    }

    public String getCorrectedByID() {
        return correctedByID;
    }

    public void setCorrectedByID(String correctedByID) {
        this.correctedByID = correctedByID;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTocobrid() {
        return tocobrid;
    }

    public void setTocobrid(String tocobrid) {
        this.tocobrid = tocobrid;
    }

}