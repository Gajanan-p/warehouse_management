package com.ssinfomate.warehousemanagement.webservices.grn;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PONumberListModel {

    @SerializedName("DocNumber")
    @Expose
    private String docNumber;
    @SerializedName("DocKey")
    @Expose
    private String docKey;

    /**
     * No args constructor for use in serialization
     *
     */
    public PONumberListModel() {
    }

    /**
     *
     * @param docNumber
     * @param docKey
     */
    public PONumberListModel(String docNumber, String docKey) {
        super();
        this.docNumber = docNumber;
        this.docKey = docKey;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getDocKey() {
        return docKey;
    }

    public void setDocKey(String docKey) {
        this.docKey = docKey;
    }

}