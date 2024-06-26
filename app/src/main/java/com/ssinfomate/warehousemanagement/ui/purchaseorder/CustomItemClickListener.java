package com.ssinfomate.warehousemanagement.ui.purchaseorder;


import com.ssinfomate.warehousemanagement.webservices.supplier.SupplierModel;

public interface CustomItemClickListener {
   void onItemClick(SupplierModel model, int position);
}
