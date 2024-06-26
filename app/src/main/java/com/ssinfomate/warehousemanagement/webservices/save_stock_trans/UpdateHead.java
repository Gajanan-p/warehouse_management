package com.ssinfomate.warehousemanagement.webservices.save_stock_trans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateHead {
    @SerializedName("updatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("TransferDataId")
    @Expose
    private String transferDataId;

    /**
     * No args constructor for use in serialization
     *
     */
    public UpdateHead() {
    }

    /**
     *
     * @param transferDataId
     * @param updatedBy
     */
    public UpdateHead(String updatedBy, String transferDataId) {
        super();
        this.updatedBy = updatedBy;
        this.transferDataId = transferDataId;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getTransferDataId() {
        return transferDataId;
    }

    public void setTransferDataId(String transferDataId) {
        this.transferDataId = transferDataId;
    }

}