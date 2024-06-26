package com.ssinfomate.warehousemanagement.webservices.save_stock_trans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateDetail {
    @SerializedName("TransferDataDetailID")
    @Expose
    private String transferDataDetailID;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("AccQtyFrmWerehouse")
    @Expose
    private String accQtyFrmWerehouse;
    @SerializedName("Stock_Id")
    @Expose
    private String stockId;

    /**
     * No args constructor for use in serialization
     *
     */
    public UpdateDetail() {
    }

    /**
     *
     * @param transferDataDetailID
     * @param accQtyFrmWerehouse
     * @param stockId
     * @param status
     */
    public UpdateDetail(String transferDataDetailID, String status, String accQtyFrmWerehouse, String stockId) {
        super();
        this.transferDataDetailID = transferDataDetailID;
        this.status = status;
        this.accQtyFrmWerehouse = accQtyFrmWerehouse;
        this.stockId = stockId;
    }

    public String getTransferDataDetailID() {
        return transferDataDetailID;
    }

    public void setTransferDataDetailID(String transferDataDetailID) {
        this.transferDataDetailID = transferDataDetailID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccQtyFrmWerehouse() {
        return accQtyFrmWerehouse;
    }

    public void setAccQtyFrmWerehouse(String accQtyFrmWerehouse) {
        this.accQtyFrmWerehouse = accQtyFrmWerehouse;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

}