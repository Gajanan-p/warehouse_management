package com.ssinfomate.warehousemanagement.ui.dialog.warehouse;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.warehouse.WarehouseModel;
import java.util.ArrayList;

public class WarehouseDialog extends Dialog {
    ArrayList<WarehouseModel> warehouseModelArrayList;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ListItemClick listItemClick;
    String TYPE;
    public WarehouseDialog(@NonNull Context context,
                           ArrayList<WarehouseModel> warehouseModelArrayList,
                           ListItemClick listItemClick,
                           String TYPE
                           ) {
        super(context);
         this.warehouseModelArrayList=warehouseModelArrayList;
        this.listItemClick=listItemClick;
        this.TYPE=TYPE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_warehouse);
        adapter=new WarehouseAdapter(warehouseModelArrayList,listItemClick,TYPE);
        recyclerView=findViewById(R.id.list_item_warehouse);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
