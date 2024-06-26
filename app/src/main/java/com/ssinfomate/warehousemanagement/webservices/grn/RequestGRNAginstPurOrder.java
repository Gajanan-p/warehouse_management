package com.ssinfomate.warehousemanagement.webservices.grn;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RequestGRNAginstPurOrder {

    @SerializedName("CoBr_Id")
    @Expose
    private String coBrId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("MachName")
    @Expose
    private String machName;
    @SerializedName("Supl_ID")
    @Expose
    private Integer suplID;
    @SerializedName("dockey")
    @Expose
    private String dockey;
    @SerializedName("Msg")
    @Expose
    private String msg;
    @SerializedName("GRNAginstPurOrderInpputDetail")
    @Expose
    private List<RequestGRNAginstPurOrderDetail> details = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public RequestGRNAginstPurOrder() {
    }

    /**
     *
     * @param msg
     * @param details
     * @param suplID
     * @param coBrId
     * @param userId
     * @param machName
     */
    public RequestGRNAginstPurOrder(String coBrId, Integer userId, String machName,
                                    Integer suplID, String msg, String dockey,
                                    List<RequestGRNAginstPurOrderDetail> details) {
        super();
        this.coBrId = coBrId;
        this.userId = userId;
        this.machName = machName;
        this.suplID = suplID;
        this.msg = msg;
        this.dockey = dockey;
        this.details = details;
    }

    public String getCoBrId() {
        return coBrId;
    }

    public void setCoBrId(String coBrId) {
        this.coBrId = coBrId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMachName() {
        return machName;
    }

    public void setMachName(String machName) {
        this.machName = machName;
    }

    public Integer getSuplID() {
        return suplID;
    }

    public void setSuplID(Integer suplID) {
        this.suplID = suplID;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDockey() {
        return dockey;
    }

    public void setDockey(String dockey) {
        this.dockey = dockey;
    }

    public List<RequestGRNAginstPurOrderDetail> details() {
        return details;
    }

    public void setDetail(List<RequestGRNAginstPurOrderDetail> details) {
        this.details = details;
    }

}