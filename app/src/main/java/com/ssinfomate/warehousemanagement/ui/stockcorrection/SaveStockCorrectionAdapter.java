package com.ssinfomate.warehousemanagement.ui.stockcorrection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.SaveStockTransferModel;
import com.ssinfomate.warehousemanagement.webservices.stock_correction.AdjustmentTypeModel;
import com.ssinfomate.warehousemanagement.ui.stocktransfer.IOnSaveStock;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SaveStockCorrectionAdapter extends RecyclerView.Adapter<SaveStockCorrectionAdapter.ViewHolder> {
    ArrayList<SaveStockTransferModel> listSaveStockModel;
    IOnSaveStock iOnSaveStock;
    Context context;
    AdjustmentTypeModel adjustmentTypeModel;

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_stock_correction_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        SaveStockTransferModel saveStockTransferModel=listSaveStockModel.get(position);
        if (saveStockTransferModel.getItemId()!=null) {
            holder.appCompatTextViewItemName.setText(saveStockTransferModel.getItemName());
            holder.appCompatTextViewItemBarcode.setText(saveStockTransferModel.getBarcode());
            holder.appCompatTextViewItemQuantity.setText(saveStockTransferModel.getClqty());
            holder.appCompatTextViewItemUnits.setText(saveStockTransferModel.getUnitName());
            holder.appCompatTextViewScanQuantity.setText(String.valueOf(saveStockTransferModel.getScan_quantity()));
        }
        else{
            Toast.makeText(context,"Item not found",Toast.LENGTH_LONG).show();
        }

        if(saveStockTransferModel.getDamageReasonName()==null)
        {
            holder.appCompatTextViewDamageReason.setText("Select Stock Correction Reason");
        }

        else
            {
                holder.appCompatTextViewDamageReason.setText(String.valueOf(saveStockTransferModel.getDamageReasonName()));
            }

            holder.appCompatTextViewDamageReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                iOnSaveStock.onStockItemDamageReason(position);
            }
        });
        holder.appCompatButtonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnSaveStock.onStockItemChange(position);
            }
        });
        holder.appCompatButtonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnSaveStock.onStockItemRemove(position);
            }
        });
//
    }

    @Override
    public int getItemCount() {
        return listSaveStockModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        AppCompatTextView appCompatTextViewItemName;
        AppCompatTextView appCompatTextViewItemBarcode;
        AppCompatTextView appCompatTextViewScanQuantity;
        AppCompatTextView appCompatTextViewItemUnits;
        AppCompatTextView appCompatTextViewItemQuantity;
        AppCompatButton appCompatButtonChange;
        AppCompatButton appCompatButtonRemove;
        AppCompatTextView appCompatTextViewDamageReason;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            appCompatTextViewItemName=itemView.findViewById(R.id.row_save_stock_trans_item_name);
            appCompatTextViewItemBarcode=itemView.findViewById(R.id.row_save_stock_trans_barcode);
            appCompatTextViewItemQuantity=itemView.findViewById(R.id.row_save_stock_trans_item_quantity);
            appCompatTextViewItemUnits=itemView.findViewById(R.id.row_save_stock_trans_item_unit);
            appCompatTextViewScanQuantity=itemView.findViewById(R.id.row_save_stock_trans_scan_quantity);
            appCompatButtonChange=itemView.findViewById(R.id.row_save_stock_trans_button_change);
            appCompatButtonRemove=itemView.findViewById(R.id.row_save_stock_trans_button_remove);
            appCompatTextViewDamageReason=itemView.findViewById(R.id.text_damage_reason);
        }
    }

    public SaveStockCorrectionAdapter(
            ArrayList<SaveStockTransferModel> listSaveStockModel,
            IOnSaveStock iOnSaveStock,
            Context context,
            AdjustmentTypeModel adjustmentTypeModel) {
        this.listSaveStockModel = listSaveStockModel;
        this.iOnSaveStock=iOnSaveStock;
        this.context=context;
        this.adjustmentTypeModel=adjustmentTypeModel;
    }
}
