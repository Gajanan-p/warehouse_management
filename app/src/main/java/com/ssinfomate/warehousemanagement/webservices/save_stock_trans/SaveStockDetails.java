package com.ssinfomate.warehousemanagement.webservices.save_stock_trans;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class SaveStockDetails {

    @SerializedName("Details")
    @Expose
    private List<Detail> details = null;
    @SerializedName("cancelDate")
    @Expose
    private String cancelDate;
    @SerializedName("Cobr_id")
    @Expose
    private String cobrId;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("msg")
    @Expose
    private String msg;

    /**
     * No args constructor for use in serialization
     *
     */
    public SaveStockDetails() {
    }

    /**
     *
     * @param msg
     * @param cancelDate
     * @param createdBy
     * @param details
     * @param cobrId
     * @param userid
     */
    public SaveStockDetails(List<Detail> details, String cancelDate, String cobrId, String createdBy, String userid, String msg) {
        super();
        this.details = details;
        this.cancelDate = cancelDate;
        this.cobrId = cobrId;
        this.createdBy = createdBy;
        this.userid = userid;
        this.msg = msg;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    public String getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getCobrId() {
        return cobrId;
    }

    public void setCobrId(String cobrId) {
        this.cobrId = cobrId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}