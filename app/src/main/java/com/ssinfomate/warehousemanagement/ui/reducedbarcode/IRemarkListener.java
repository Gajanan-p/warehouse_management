package com.ssinfomate.warehousemanagement.ui.reducedbarcode;

import com.ssinfomate.warehousemanagement.webservices.reducedbarcode.ReducedBarcodeRemarkList;

public interface IRemarkListener {
    void onRemarkNameClicked(ReducedBarcodeRemarkList reducedBarcodeRemarkList, int position);
}
