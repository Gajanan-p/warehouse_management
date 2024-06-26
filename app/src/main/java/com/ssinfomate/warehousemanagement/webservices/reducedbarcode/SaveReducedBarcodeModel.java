package com.ssinfomate.warehousemanagement.webservices.reducedbarcode;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SaveReducedBarcodeModel {

    @SerializedName("oldsalerate")
    @Expose
    private String oldsalerate;
    @SerializedName("newsalerate")
    @Expose
    private String newsalerate;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("NoOfstickerstoprint")
    @Expose
    private String noOfstickerstoprint;
    @SerializedName("barcode")
    @Expose
    private String barcode;

    /**
     * No args constructor for use in serialization
     *
     */
    public SaveReducedBarcodeModel() {
    }

    /**
     *
     * @param itemName
     * @param newsalerate
     * @param oldsalerate
     * @param noOfstickerstoprint
     * @param barcode
     */
    public SaveReducedBarcodeModel(String oldsalerate, String newsalerate, String itemName, String noOfstickerstoprint, String barcode) {
        super();
        this.oldsalerate = oldsalerate;
        this.newsalerate = newsalerate;
        this.itemName = itemName;
        this.noOfstickerstoprint = noOfstickerstoprint;
        this.barcode = barcode;
    }

    public String getOldsalerate() {
        return oldsalerate;
    }

    public void setOldsalerate(String oldsalerate) {
        this.oldsalerate = oldsalerate;
    }

    public String getNewsalerate() {
        return newsalerate;
    }

    public void setNewsalerate(String newsalerate) {
        this.newsalerate = newsalerate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getNoOfstickerstoprint() {
        return noOfstickerstoprint;
    }

    public void setNoOfstickerstoprint(String noOfstickerstoprint) {
        this.noOfstickerstoprint = noOfstickerstoprint;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

}
