package com.ssinfomate.warehousemanagement.ui.stockcorrection;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.stock_correction.DamageReasonModels;

import java.util.List;

public class DamageReasonDialog extends Dialog {
    List<DamageReasonModels> tDamageReasonModels;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    IDamageReason iDamageReason;
    int position;


    public DamageReasonDialog(@NonNull Context context,
                              List<DamageReasonModels> tDamageReasonModels,
                              IDamageReason iDamageReason,
                              int position
    ) {
        super(context);
        this.tDamageReasonModels=tDamageReasonModels;
        this.iDamageReason=iDamageReason;
        this.position=position;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_damage_reason);
        adapter=new DamageReasonAdapter(tDamageReasonModels,iDamageReason,position);
        recyclerView=findViewById(R.id.list_item_damage_reason);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
