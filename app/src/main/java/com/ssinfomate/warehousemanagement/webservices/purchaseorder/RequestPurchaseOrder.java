package com.ssinfomate.warehousemanagement.webservices.purchaseorder;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RequestPurchaseOrder {

    @SerializedName("CoBr_Id")
    @Expose
    private String coBrId;
    @SerializedName("Supl_ID")
    @Expose
    private Integer suplID;
    @SerializedName("MachName")
    @Expose
    private String machName;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("Msg")
    @Expose
    private String msg;
    @SerializedName("DraftPurchaseOrderInputDetail")
    @Expose
    private List<RequestPurchaseOrderDetails> details = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public RequestPurchaseOrder() {
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
    public RequestPurchaseOrder(String coBrId, Integer suplID, String machName, Integer userId, String msg, List<RequestPurchaseOrderDetails> details) {
        super();
        this.coBrId = coBrId;
        this.suplID = suplID;
        this.machName = machName;
        this.userId = userId;
        this.msg = msg;
        this.details = details;
    }

    public String getCoBrId() {
        return coBrId;
    }

    public void setCoBrId(String coBrId) {
        this.coBrId = coBrId;
    }

    public Integer getSuplID() {
        return suplID;
    }

    public void setSuplID(Integer suplID) {
        this.suplID = suplID;
    }

    public String getMachName() {
        return machName;
    }

    public void setMachName(String machName) {
        this.machName = machName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<RequestPurchaseOrderDetails> getDetails() {
        return details;
    }

    public void setDetails(List<RequestPurchaseOrderDetails> details) {
        this.details = details;
    }

}