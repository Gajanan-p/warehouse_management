package com.ssinfomate.warehousemanagement.webservices.purchaseorder;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

        public class RequestPurchaseOrderDetails {

            @SerializedName("TotQty")
            @Expose
            private Integer totQty;
            @SerializedName("item_id")
            @Expose
            private String itemId;

            /**
             * No args constructor for use in serialization
             *
             */
            public RequestPurchaseOrderDetails() {
            }

            /**
             *
             * @param itemId
             * @param totQty
             */
            public RequestPurchaseOrderDetails(Integer totQty, String itemId) {
                super();
                this.totQty = totQty;
                this.itemId = itemId;
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

        }