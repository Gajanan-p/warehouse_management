package com.ssinfomate.warehousemanagement.ui.dialog.warehouse;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.warehouse.ToWarehouse;
import java.util.ArrayList;

public class ToWarehouseDialog extends Dialog {
    ArrayList<ToWarehouse> toWarehouseArrayList;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ListItemClick listItemClick;
    String TYPE;

    public ToWarehouseDialog(@NonNull Context context,
                             ArrayList<ToWarehouse> warehouseModelArrayList,
                             ListItemClick listItemClick,
                             String TYPE
                           ) {
        super(context);
        this.toWarehouseArrayList=warehouseModelArrayList;
        this.listItemClick=listItemClick;
        this.TYPE=TYPE;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_warehouse);
        adapter=new ToWarehouseAdapter(toWarehouseArrayList,listItemClick,TYPE);
        recyclerView=findViewById(R.id.list_item_warehouse);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
