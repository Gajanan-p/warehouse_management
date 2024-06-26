package com.ssinfomate.warehousemanagement.webservices.grn;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestGRNWithoutPurOrderDetails {

        @SerializedName("TotQty")
        @Expose
        private Integer totQty;
        @SerializedName("item_id")
        @Expose
        private String itemId;
        @SerializedName("barcode")
        @Expose
        private String barcode;

        /**
         * No args constructor for use in serialization
         *
         */
        public RequestGRNWithoutPurOrderDetails() {
        }

        /**
         *
         * @param itemId
         * @param totQty
         */
        public RequestGRNWithoutPurOrderDetails(Integer totQty, String itemId,String barcode) {
            super();
            this.totQty = totQty;
            this.itemId = itemId;
            this.barcode = barcode;
        }

        public Integer getTotQty() {
            return totQty;
        }

        public void setTotQty(Integer totQty) {
            this.totQty = totQty;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getBarcode() {
        return barcode;
    }

        public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    }


