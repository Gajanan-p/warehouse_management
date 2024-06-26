package com.ssinfomate.warehousemanagement.ui.dialog.warehouse;

import com.ssinfomate.warehousemanagement.webservices.warehouse.ToWarehouse;
import com.ssinfomate.warehousemanagement.webservices.warehouse.WarehouseModel;

public interface ListItemClick {
    void onItemClicked(WarehouseModel warehouseModel);
    void onItemClickedBusinessLocation(WarehouseModel warehouseModel);
    void onItemClickedFromLocation(WarehouseModel warehouseModel);
    void onItemClickedToLocation(ToWarehouse toWarehouse);

}
