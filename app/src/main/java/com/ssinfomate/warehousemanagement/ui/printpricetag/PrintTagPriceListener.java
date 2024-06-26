package com.ssinfomate.warehousemanagement.ui.printpricetag;

import com.ssinfomate.warehousemanagement.webservices.check_stock.CheckStockModel;

public interface PrintTagPriceListener {
    void onPrintTagClicked(CheckStockModel checkStockModel);
}
