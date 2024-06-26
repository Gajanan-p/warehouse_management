package com.ssinfomate.warehousemanagement.ui.stockcorrection;

import com.ssinfomate.warehousemanagement.webservices.stock_correction.DamageReasonModel;

public interface IDamageReason {
    void onDamageReasonClicked(DamageReasonModel damageReasonModel, int position);
}
