package com.ssinfomate.warehousemanagement.ui.reducedbarcode;


import com.ssinfomate.warehousemanagement.webservices.check_stock.PriceListModel;
import com.ssinfomate.warehousemanagement.webservices.reducedbarcode.RequestSaveReducedBarcodeDetail;

public interface IUpdateRateQuantity {
    void onUpdateRateChange(PriceListModel checkStockModel, int position);
    void onRemarkClicked(int position);
    void onChangeNewRate(RequestSaveReducedBarcodeDetail requestSaveReducedBarcodeDetail, int position);
}
