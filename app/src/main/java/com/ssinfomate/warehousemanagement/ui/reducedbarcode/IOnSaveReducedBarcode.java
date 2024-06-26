package com.ssinfomate.warehousemanagement.ui.reducedbarcode;

public interface IOnSaveReducedBarcode {
    void onStockItemUpdate(int position);
    void onStockItemRemove(int position);
    void onRemarkClicked(int position);
}
