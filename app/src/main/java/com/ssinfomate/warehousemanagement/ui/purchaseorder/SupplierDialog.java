package com.ssinfomate.warehousemanagement.ui.purchaseorder;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.supplier.SupplierModel;

import java.util.ArrayList;

public class SupplierDialog extends Dialog {
    ArrayList<SupplierModel> supplierModels;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ISupplierInterface iSupplierInterface;

    public SupplierDialog(@NonNull Context context,
                          ArrayList<SupplierModel> supplierModels,
                          ISupplierInterface iSupplierInterface)
    {
        super(context);
        this.supplierModels=supplierModels;
        this.iSupplierInterface=iSupplierInterface;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_warehouse);
        adapter=new SupplierAdapter(supplierModels,iSupplierInterface);
        recyclerView=findViewById(R.id.list_item_warehouse);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
