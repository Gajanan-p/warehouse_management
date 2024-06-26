package com.ssinfomate.warehousemanagement.webservices.general;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeneralModel {
    @SerializedName("GeneralID")
    @Expose
    private String generalID;
    @SerializedName("CreatedBy")
    @Expose
    private String createdBy;
    @SerializedName("UpdatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("generalInputDetails")
    @Expose
    private List<GeneralInputDetail> generalInputDetails = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public GeneralModel() {
    }

    /**
     *
     * @param msg
     * @param updatedBy
     * @param createdBy
     * @param generalID
     * @param generalInputDetails
     */
    public GeneralModel(String generalID, String createdBy, String updatedBy, String msg, List<GeneralInputDetail> generalInputDetails) {
        super();
        this.generalID = generalID;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.msg = msg;
        this.generalInputDetails = generalInputDetails;
    }

    public String getGeneralID() {
        return generalID;
    }

    public void setGeneralID(String generalID) {
        this.generalID = generalID;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<GeneralInputDetail> getGeneralInputDetails() {
        return generalInputDetails;
    }

    public void setGeneralInputDetails(List<GeneralInputDetail> generalInputDetails) {
        this.generalInputDetails = generalInputDetails;
    }

}