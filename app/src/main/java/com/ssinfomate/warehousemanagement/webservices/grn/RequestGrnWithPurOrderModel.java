package com.ssinfomate.warehousemanagement.webservices.grn;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RequestGrnWithPurOrderModel {

    @SerializedName("CoBr_Id")
    @Expose
    private String coBrId;
    @SerializedName("DocNumber")
    @Expose
    private String docNumber;

    /**
     * No args constructor for use in serialization
     *
     */
    public RequestGrnWithPurOrderModel() {
    }

    /**
     *
     * @param docNumber
     * @param coBrId
     */
    public RequestGrnWithPurOrderModel(String coBrId, String docNumber) {
        super();
        this.coBrId = coBrId;
        this.docNumber = docNumber;
    }

    public String getCoBrId() {
        return coBrId;
    }

    public void setCoBrId(String coBrId) {
        this.coBrId = coBrId;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

}