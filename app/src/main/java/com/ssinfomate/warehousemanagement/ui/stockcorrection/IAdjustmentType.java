package com.ssinfomate.warehousemanagement.ui.stockcorrection;

import com.ssinfomate.warehousemanagement.webservices.stock_correction.AdjustmentTypeModel;

public interface IAdjustmentType {
    void onAdjustmentTypeClicked(AdjustmentTypeModel adjustmentTypeModel, int position);
}
