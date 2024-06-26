package com.ssinfomate.warehousemanagement.webservices.save_stock_trans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class PickHeldStockModel {

    @SerializedName("cobr_id")
    @Expose
    private Integer cobrId;
    @SerializedName("userid")
    @Expose
    private Integer userid;

    /**
     * No args constructor for use in serialization
     */
    public PickHeldStockModel() {
    }

    /**
     * @param cobrId
     * @param userid
     */
    public PickHeldStockModel(Integer cobrId, Integer userid) {
        super();
        this.cobrId = cobrId;
        this.userid = userid;
    }

    public Integer getCobrId() {
        return cobrId;
    }

    public void setCobrId(Integer cobrId) {
        this.cobrId = cobrId;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}