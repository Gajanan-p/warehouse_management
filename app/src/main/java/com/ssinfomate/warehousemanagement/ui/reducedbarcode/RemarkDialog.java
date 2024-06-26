package com.ssinfomate.warehousemanagement.ui.reducedbarcode;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.reducedbarcode.ReducedBarcodeRemarkList;

import org.jetbrains.annotations.NotNull;
import java.util.List;

public class RemarkDialog extends Dialog {
    List<ReducedBarcodeRemarkList> reducedBarcodeRemarkLists;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    IRemarkListener iRemarkListener;
    int position;

    public RemarkDialog(@NonNull @NotNull Context context, List<ReducedBarcodeRemarkList> reducedBarcodeRemarkLists,
                        IRemarkListener iRemarkListener,
                        int position)
    {
        super(context);
        this.reducedBarcodeRemarkLists = reducedBarcodeRemarkLists;
        this.iRemarkListener = iRemarkListener;
        this.position = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_reduced_barcode_remark);
        adapter=new RemarkAdapter(reducedBarcodeRemarkLists,iRemarkListener,position);
        recyclerView=findViewById(R.id.list_item_reduced_barcode_remark);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
