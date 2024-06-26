package com.ssinfomate.warehousemanagement.webservices.grn;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


      public class RequestHeldGrn {

        @SerializedName("cobr_id")
        @Expose
        private String cobrId;
        @SerializedName("UserType")
        @Expose
        private String userType;
        @SerializedName("UserId")
        @Expose
        private String userId;

        /**
        * No args constructor for use in serialization
        *
        */
        public RequestHeldGrn() {
        }

        /**
        *
        * @param userType
        * @param cobrId
        * @param userId
        */
        public RequestHeldGrn(String cobrId, String userType, String userId) {
        super();
        this.cobrId = cobrId;
        this.userType = userType;
        this.userId = userId;
        }

        public String getCobrId() {
        return cobrId;
        }

        public void setCobrId(String cobrId) {
        this.cobrId = cobrId;
        }

        public String getUserType() {
        return userType;
        }

        public void setUserType(String userType) {
        this.userType = userType;
        }

        public String getUserId() {
        return userId;
        }

        public void setUserId(String userId) {
        this.userId = userId;
        }

        }
