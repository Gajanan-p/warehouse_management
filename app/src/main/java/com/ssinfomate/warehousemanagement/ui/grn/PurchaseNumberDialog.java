package com.ssinfomate.warehousemanagement.ui.grn;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.grn.PONumberListModel;

import java.util.ArrayList;

public class PurchaseNumberDialog extends Dialog {
    ArrayList<PONumberListModel>poNumberListModels;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    IPurchaseNumber iPurchaseNumber;

    public PurchaseNumberDialog(@NonNull  Context context,
                                ArrayList<PONumberListModel> poNumberListModels,
                                IPurchaseNumber iPurchaseNumber) {
        super(context);
        this.poNumberListModels = poNumberListModels;
        this.iPurchaseNumber = iPurchaseNumber;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_pur_no);
        adapter=new PurchaseNumberAdapter(poNumberListModels,iPurchaseNumber);
        recyclerView=findViewById(R.id.list_item_pur_no);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
