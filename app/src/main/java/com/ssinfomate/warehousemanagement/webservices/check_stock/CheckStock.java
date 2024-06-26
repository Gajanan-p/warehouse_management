package com.ssinfomate.warehousemanagement.webservices.check_stock;

import com.google.gson.annotations.SerializedName;


public class CheckStock {
    @SerializedName("_CoBr_Id")
    private String coBr_Id;

    @SerializedName("_barcode")
    private String barcode;

    @SerializedName("_altbarcode1")
    private String altBarcode1;

    public CheckStock() {

    }

    public CheckStock(String coBr_Id, String barcode, String altBarcode1) {
        this.coBr_Id = coBr_Id;
        this.barcode = barcode;
        this.altBarcode1 = altBarcode1;
    }

    public String getCoBr_Id() {
        return coBr_Id;
    }

    public void setCoBr_Id(String coBr_Id) {
        this.coBr_Id = coBr_Id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getAltBarcode1() {
        return altBarcode1;
    }

    public void setAltBarcode1(String altBarcode1) {
        this.altBarcode1 = altBarcode1;
    }
}
