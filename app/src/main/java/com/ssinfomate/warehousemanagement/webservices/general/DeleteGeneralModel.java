package com.ssinfomate.warehousemanagement.webservices.general;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteGeneralModel {

    @SerializedName("GeneralID")
    @Expose
    private String generalID;
    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("Updated_by")
    @Expose
    private String updatedBy;

        /**
         * No args constructor for use in serialization
         *
         */
        public DeleteGeneralModel() {
        }

        /**
         *
         * @param msg
         * @param generalID
         */
    public DeleteGeneralModel(String generalID, String msg, String updatedBy) {
        this.generalID = generalID;
        this.msg = msg;
        this.updatedBy = updatedBy;
    }

    public String getGeneralID() {
        return generalID;
    }

    public void setGeneralID(String generalID) {
        this.generalID = generalID;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}