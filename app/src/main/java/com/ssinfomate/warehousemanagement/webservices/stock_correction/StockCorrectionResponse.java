package com.ssinfomate.warehousemanagement.webservices.stock_correction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
 public class StockCorrectionResponse {

        @SerializedName("TransferDataID")
        @Expose
        private String transferDataID;
        @SerializedName("FromLocationName")
        @Expose
        private String fromLocationName;
        @SerializedName("FromWarehouse")
        @Expose
        private String fromWarehouse;
        @SerializedName("BusinessName")
        @Expose
        private String businessName;
        @SerializedName("item_Key")
        @Expose
        private String itemKey;
        @SerializedName("company_id")
        @Expose
        private String companyId;
        @SerializedName("item_name")
        @Expose
        private String itemName;
        @SerializedName("TransferQty")
        @Expose
        private String transferQty;
        @SerializedName("scanQty")
        @Expose
        private String scanQty;
        @SerializedName("createdDate")
        @Expose
        private String createdDate;
        @SerializedName("createdby")
        @Expose
        private String createdby;
        @SerializedName("ToLocationName")
        @Expose
        private String toLocationName;
        @SerializedName("ToWarehouse")
        @Expose
        private String toWarehouse;

     @SerializedName("Status")
     @Expose
     private String status;

        /**
         * No args constructor for use in serialization
         *
         */
        public StockCorrectionResponse() {
        }

     public StockCorrectionResponse(String transferDataID, String fromLocationName, String fromWarehouse, String businessName, String itemKey, String companyId, String itemName, String transferQty, String scanQty, String createdDate, String createdby, String toLocationName, String toWarehouse, String status) {
         this.transferDataID = transferDataID;
         this.fromLocationName = fromLocationName;
         this.fromWarehouse = fromWarehouse;
         this.businessName = businessName;
         this.itemKey = itemKey;
         this.companyId = companyId;
         this.itemName = itemName;
         this.transferQty = transferQty;
         this.scanQty = scanQty;
         this.createdDate = createdDate;
         this.createdby = createdby;
         this.toLocationName = toLocationName;
         this.toWarehouse = toWarehouse;
         this.status = status;
     }




        public String getTransferDataID() {
            return transferDataID;
        }

        public void setTransferDataID(String transferDataID) {
            this.transferDataID = transferDataID;
        }

        public String getFromLocationName() {
            return fromLocationName;
        }

        public void setFromLocationName(String fromLocationName) {
            this.fromLocationName = fromLocationName;
        }

        public String getFromWarehouse() {
            return fromWarehouse;
        }

        public void setFromWarehouse(String fromWarehouse) {
            this.fromWarehouse = fromWarehouse;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public String getItemKey() {
            return itemKey;
        }

        public void setItemKey(String itemKey) {
            this.itemKey = itemKey;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getTransferQty() {
            return transferQty;
        }

        public void setTransferQty(String transferQty) {
            this.transferQty = transferQty;
        }

        public String getScanQty() {
            return scanQty;
        }

        public void setScanQty(String scanQty) {
            this.scanQty = scanQty;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getCreatedby() {
            return createdby;
        }

        public void setCreatedby(String createdby) {
            this.createdby = createdby;
        }

        public String getToLocationName() {
            return toLocationName;
        }

        public void setToLocationName(String toLocationName) {
            this.toLocationName = toLocationName;
        }

        public String getToWarehouse() {
            return toWarehouse;
        }

        public void setToWarehouse(String toWarehouse) {
            this.toWarehouse = toWarehouse;
        }

     public String getStatus() {
         return status;
     }

     public void setStatus(String status) {
         this.status = status;
     }
 }