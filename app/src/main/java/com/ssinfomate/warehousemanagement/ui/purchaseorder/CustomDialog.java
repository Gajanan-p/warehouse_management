package com.ssinfomate.warehousemanagement.ui.purchaseorder;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.supplier.SupplierModel;

import java.util.ArrayList;

public class CustomDialog extends Dialog implements CustomItemClickListener {

    ArrayList<SupplierModel> models;
    RecyclerView recyclerView;
    CustomAdapter adapter;
    CustomItemClickListener customItemClickListener;
    AppCompatEditText editTextSearchName;
    public CustomDialog(@NonNull Context context, ArrayList<SupplierModel> models,
                        CustomItemClickListener customItemClickListener)
    {
        super(context);
        this.models = models;
        this.customItemClickListener = customItemClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_warehouse);
        recyclerView = (RecyclerView) findViewById(R.id.list_item_warehouse);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        editTextSearchName = (AppCompatEditText) findViewById(R.id.edit_search_supplier_name);
        openList();
        editTextSearchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                adapter.getFilter().filter(arg0);
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
    }

    @Override
    public void onItemClick(SupplierModel model, int position) {

    }
    public void openList() {
        adapter = new CustomAdapter(getContext(),models,customItemClickListener);
        recyclerView.setAdapter(adapter);
    }

}
