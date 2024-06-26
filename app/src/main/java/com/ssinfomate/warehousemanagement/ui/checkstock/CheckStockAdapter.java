package com.ssinfomate.warehousemanagement.ui.checkstock;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.webservices.check_stock.CheckStockModel;
import com.ssinfomate.warehousemanagement.R;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class CheckStockAdapter extends RecyclerView.Adapter<CheckStockAdapter.ViewHolder> {
    ArrayList<CheckStockModel> listCheckStockModel;
    Context context;

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_check_stock_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        CheckStockModel checkStockModel=listCheckStockModel.get(position);
        if(checkStockModel.getItemId()!=null){
        holder.appCompatTextViewItemName.setText(checkStockModel.getItemName());
        holder.appCompatTextViewItemBarcode.setText(checkStockModel.getBarcode());
        holder.appCompatTextViewItemQuantity.setText(checkStockModel.getClqty());
        holder.appCompatTextViewItemUnits.setText(checkStockModel.getUnitName());
        }
        else{
            Toast.makeText(context,"Item not found",Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public int getItemCount() {
        return listCheckStockModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        AppCompatTextView appCompatTextViewItemName;
        AppCompatTextView appCompatTextViewItemBarcode;
        AppCompatTextView appCompatTextViewItemQuantity;
        AppCompatTextView appCompatTextViewItemUnits;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            appCompatTextViewItemName=itemView.findViewById(R.id.row_check_stock_item_name);
            appCompatTextViewItemBarcode=itemView.findViewById(R.id.row_check_stock_barcode);
            appCompatTextViewItemQuantity=itemView.findViewById(R.id.row_check_stock_item_quantity);
            appCompatTextViewItemUnits=itemView.findViewById(R.id.row_check_stock_item_unit);
        }
    }

    public CheckStockAdapter(ArrayList<CheckStockModel> listCheckStockModel, Context context) {
        this.listCheckStockModel = listCheckStockModel;
        this.context = context;
    }
}
