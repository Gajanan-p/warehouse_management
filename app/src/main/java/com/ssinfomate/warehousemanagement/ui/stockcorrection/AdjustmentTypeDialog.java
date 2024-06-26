package com.ssinfomate.warehousemanagement.ui.stockcorrection;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.stock_correction.AdjustmentTypeModel;

import java.util.ArrayList;

public class AdjustmentTypeDialog extends Dialog {

    ArrayList<AdjustmentTypeModel> adjustmentTypeModels;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    IAdjustmentType iAdjustmentType;
    int position;

    public AdjustmentTypeDialog(@NonNull Context context,
                                ArrayList<AdjustmentTypeModel> adjustmentTypeModels,
                                IAdjustmentType iAdjustmentType,
                                int position)
    {
        super(context);
        this.adjustmentTypeModels = adjustmentTypeModels;
        this.iAdjustmentType = iAdjustmentType;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_adjustment_type);
        adapter = new AdjustmentTypeAdapter(adjustmentTypeModels,iAdjustmentType,position);
        recyclerView = findViewById(R.id.list_item_adjustment_type);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}
