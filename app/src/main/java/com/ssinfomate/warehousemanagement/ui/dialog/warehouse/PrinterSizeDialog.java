

package com.ssinfomate.warehousemanagement.ui.dialog.warehouse;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;


import java.util.ArrayList;

public class PrinterSizeDialog extends Dialog {
    ArrayList<String> warehouseModelArrayList;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    PrinterSizeListener listItemClick;
    public PrinterSizeDialog(@NonNull Context context,
                             ArrayList<String> warehouseModelArrayList,
                             PrinterSizeListener listItemClick
                           ) {
        super(context);
         this.warehouseModelArrayList=warehouseModelArrayList;
        this.listItemClick=listItemClick;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_printer_size);
        adapter=new PrinterSizeAdapter(warehouseModelArrayList,listItemClick);
        recyclerView=findViewById(R.id.list_item_warehouse);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
