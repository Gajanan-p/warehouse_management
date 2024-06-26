package com.ssinfomate.warehousemanagement.webservices.supplier;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SupplierModel {

    @SerializedName("Led_Id")
    @Expose
    private String ledId;
    @SerializedName("Led_Name")
    @Expose
    private String ledName;

    /**
     * No args constructor for use in serialization
     *
     */
    public SupplierModel(){
    }

    /**
     *
     * @param ledName
     * @param ledId
     */
    public SupplierModel(String ledId, String ledName) {
        super();
        this.ledId = ledId;
        this.ledName = ledName;
    }

    public String getLedId() {
        return ledId;
    }

    public void setLedId(String ledId) {
        this.ledId = ledId;
    }

    public String getLedName() {
        return ledName;
    }

    public void setLedName(String ledName) {
        this.ledName = ledName;
    }
}