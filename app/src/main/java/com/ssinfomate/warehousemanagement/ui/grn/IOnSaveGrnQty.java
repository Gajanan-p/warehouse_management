package com.ssinfomate.warehousemanagement.ui.grn;


import com.ssinfomate.warehousemanagement.webservices.grn.GrnWithPurOrderModel;

public interface IOnSaveGrnQty {
    void onStockItemChange(GrnWithPurOrderModel grnWithPurOrderModel, int position);
}
