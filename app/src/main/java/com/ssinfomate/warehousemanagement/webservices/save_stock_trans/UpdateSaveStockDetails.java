package com.ssinfomate.warehousemanagement.webservices.save_stock_trans;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


    public class UpdateSaveStockDetails {

        @SerializedName("head")
        @Expose
        private List<UpdateHead> head = null;
        @SerializedName("details")
        @Expose
        private List<UpdateDetail> details = null;
        @SerializedName("msg")
        @Expose
        private String msg;

        /**
         * No args constructor for use in serialization
         *
         */
        public UpdateSaveStockDetails() {
        }

        /**
         *
         * @param head
         * @param msg
         * @param details
         */
        public UpdateSaveStockDetails(List<UpdateHead> head, List<UpdateDetail> details, String msg) {
            super();
            this.head = head;
            this.details = details;
            this.msg = msg;
        }

        public List<UpdateHead> getHead() {
            return head;
        }

        public void setHead(List<UpdateHead> head) {
            this.head = head;
        }

        public List<UpdateDetail> getDetails() {
            return details;
        }

        public void setDetails(List<UpdateDetail> details) {
            this.details = details;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

    }