package com.ssinfomate.warehousemanagement.ui.stocktransfer;


import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.SaveStockTransferModel;

public interface  IOnSaveStockQuantity {
    void onStockItemChange(SaveStockTransferModel saveStockTransferModel, int position);
}
