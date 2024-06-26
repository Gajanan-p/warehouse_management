package com.ssinfomate.warehousemanagement.webservices.grn;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RequestGRNAginstPurOrderDetail {

@SerializedName("TotQty")
@Expose
private String totQty;
@SerializedName("item_id")
@Expose
private String itemId;

/**
* No args constructor for use in serialization
*
*/
public RequestGRNAginstPurOrderDetail() {
}

/**
*
* @param itemId
* @param totQty
*/
public RequestGRNAginstPurOrderDetail(String totQty, String itemId) {
super();
this.totQty = totQty;
this.itemId = itemId;
}

public String getTotQty() {
return totQty;
}

public void setTotQty(String totQty) {
this.totQty = totQty;
}

public String getItemId() {
return itemId;
}

public void setItemId(String itemId) {
this.itemId = itemId;
}

}