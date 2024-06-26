package com.ssinfomate.warehousemanagement.ui.general;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.general.GeneralRemarkModel;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class GeneralRemarkDialog extends Dialog {

    ArrayList<GeneralRemarkModel> generalRemarkModels;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    IGeneralRemarkListener iGeneralRemarkListener;

    public GeneralRemarkDialog(@NonNull @NotNull Context context, ArrayList<GeneralRemarkModel> generalRemarkModels,
                               IGeneralRemarkListener iGeneralRemarkListener)
    {
        super(context);
        this.generalRemarkModels = generalRemarkModels;
        this.iGeneralRemarkListener = iGeneralRemarkListener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_general_remark);
        adapter=new GeneralRemarkAdapter(generalRemarkModels,iGeneralRemarkListener);
        recyclerView=findViewById(R.id.list_item_general_remark);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
