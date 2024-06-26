package com.ssinfomate.warehousemanagement.webservices.reducedbarcode;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RequestSaveReducedBarcode {

    @SerializedName("CoBr_Id")
    @Expose
    private String coBrId;
    @SerializedName("MachName")
    @Expose
    private String machName;
    @SerializedName("ratecat_id")
    @Expose
    private String ratecatId;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("Msg")
    @Expose
    private String msg;
    @SerializedName("Reducarbarcodedetails")
    @Expose
    private List<RequestSaveReducedBarcodeDetail> details = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public RequestSaveReducedBarcode() {
    }

    /**
     *
     * @param msg
     * @param createdBy
     * @param ratecatId
     * @param details
     * @param coBrId
     * @param machName
     */
    public RequestSaveReducedBarcode(String coBrId, String machName, String ratecatId,
                                     String createdBy, String msg,
                                     List<RequestSaveReducedBarcodeDetail> details) {
        super();
        this.coBrId = coBrId;
        this.machName = machName;
        this.ratecatId = ratecatId;
        this.createdBy = createdBy;
        this.msg = msg;
        this.details = details;
    }

    public String getCoBrId() {
        return coBrId;
    }

    public void setCoBrId(String coBrId) {
        this.coBrId = coBrId;
    }

    public String getMachName() {
        return machName;
    }

    public void setMachName(String machName) {
        this.machName = machName;
    }

    public String getRatecatId() {
        return ratecatId;
    }

    public void setRatecatId(String ratecatId) {
        this.ratecatId = ratecatId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<RequestSaveReducedBarcodeDetail> getDetails() {
        return details;
    }

    public void setDetails(List<RequestSaveReducedBarcodeDetail> details) {
        this.details = details;
    }

}