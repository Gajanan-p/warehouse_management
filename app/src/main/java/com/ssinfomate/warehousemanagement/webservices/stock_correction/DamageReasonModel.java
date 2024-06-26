package com.ssinfomate.warehousemanagement.webservices.stock_correction;

import java.util.ArrayList;

public class DamageReasonModel {
    int id;
    String reason;

    public DamageReasonModel() {

    }

    public DamageReasonModel(int id, String reason) {
        this.id = id;
        this.reason = reason;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public static ArrayList<DamageReasonModel> getDamageReasonList(){

        ArrayList list=new ArrayList();

        list.add(new DamageReasonModel(1,"Adjustment"));

        list.add(new DamageReasonModel(2,"Damage"));

        return list;

    }

}
