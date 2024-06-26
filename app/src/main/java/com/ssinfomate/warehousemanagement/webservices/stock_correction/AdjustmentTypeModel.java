package com.ssinfomate.warehousemanagement.webservices.stock_correction;

import java.util.ArrayList;

public class AdjustmentTypeModel {
    String id;
    String reason;

    public AdjustmentTypeModel(String id, String reason) {
        this.id = id;
        this.reason = reason;
    }

    public AdjustmentTypeModel() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public static ArrayList<AdjustmentTypeModel> getAdjustmentTypeList() {

        ArrayList list = new ArrayList();

        list.add(new AdjustmentTypeModel( "A","Adjustment"));

        list.add(new AdjustmentTypeModel( "D","Damage"));

        return list;

    }
}
