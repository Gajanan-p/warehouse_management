package com.ssinfomate.warehousemanagement.webservices.stock_correction;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StockCorrectionRequest {
    @SerializedName("TemplItemStockDetail")
    @Expose
    private List<TemplItemStockDetail> templItemStockDetail = null;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("updateBy")
    @Expose
    private String updateBy;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("updatedDate")
    @Expose
    private String updatedDate;
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

    /**
     * No args constructor for use in serialization
     *
     */
    public StockCorrectionRequest() {
    }

    /**
     *
     * @param msg
     * @param createdDate
     * @param createdBy
     * @param updateBy
     * @param updatedDate
     * @param cobrId
     * @param userid
     *  @param correctedByID
     *  @param machName
     * * @param stkType
     * @param templItemStockDetail
     * @param status
     */
    public StockCorrectionRequest(List<TemplItemStockDetail> templItemStockDetail, String status,
                                  String createdBy, String updateBy, String createdDate,
                                  String updatedDate, String cobrId, String userid,
                                  String machName, String stkType, String correctedByID,String msg) {
        super();
        this.templItemStockDetail = templItemStockDetail;
        this.status = status;
        this.createdBy = createdBy;
        this.updateBy = updateBy;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.cobrId = cobrId;
        this.userid = userid;
        this.machName = machName;
        this.stkType = stkType;
        this.correctedByID = correctedByID;
        this.msg = msg;
    }

    public List<TemplItemStockDetail> getTemplItemStockDetail() {
        return templItemStockDetail;
    }

    public void setTemplItemStockDetail(List<TemplItemStockDetail> templItemStockDetail) {
        this.templItemStockDetail = templItemStockDetail;
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

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
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

}