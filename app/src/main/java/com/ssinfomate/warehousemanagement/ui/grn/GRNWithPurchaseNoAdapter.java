package com.ssinfomate.warehousemanagement.ui.grn;

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
import com.ssinfomate.warehousemanagement.webservices.grn.GrnWithPurOrderModel;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class GRNWithPurchaseNoAdapter extends RecyclerView.Adapter<GRNWithPurchaseNoAdapter.ViewHolder> {
   ArrayList<GrnWithPurOrderModel>listGrnWithPurOrderModels;
   IOnSaveGrnWithPur iOnSaveGrnWithPur;
   Context context;

    public GRNWithPurchaseNoAdapter(ArrayList<GrnWithPurOrderModel> listGrnWithPurOrderModels,
                                    IOnSaveGrnWithPur iOnSaveGrnWithPur,
                                    Context context) {
        this.listGrnWithPurOrderModels = listGrnWithPurOrderModels;
        this.iOnSaveGrnWithPur = iOnSaveGrnWithPur;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public GRNWithPurchaseNoAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_grn_with_purchase_order_list,parent,false);
        return new GRNWithPurchaseNoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GRNWithPurchaseNoAdapter.ViewHolder holder, int position) {

        GrnWithPurOrderModel grnWithPurOrderModel = listGrnWithPurOrderModels.get(position);
        if (grnWithPurOrderModel.getItemId()!=null) {
            holder.textViewProductName.setText(grnWithPurOrderModel.getItemName());
            holder.textViewScanQty.setText(grnWithPurOrderModel.getBalqty());
            holder.textViewPurAmount.setText(grnWithPurOrderModel.getPurrate());
            holder.textViewAmount.setText(grnWithPurOrderModel.getAmount());
            holder.textViewTaxAmt.setText(grnWithPurOrderModel.getTaxAmt());
            holder.textViewNetAmt.setText(grnWithPurOrderModel.getNetamt());
        }else {
            Toast.makeText(context,"Item not found",Toast.LENGTH_LONG).show();

        }
        holder.appCompatButtonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnSaveGrnWithPur.onQuantityChange(position);
            }
        });
        holder.appCompatButtonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnSaveGrnWithPur.onQuantityRemove(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listGrnWithPurOrderModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView textViewProductName;
        AppCompatTextView textViewScanQty;
        AppCompatTextView textViewPurAmount;
        AppCompatTextView textViewAmount;
        AppCompatTextView textViewTaxAmt;
        AppCompatTextView textViewNetAmt;
        AppCompatButton appCompatButtonChange;
        AppCompatButton appCompatButtonRemove;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewProductName = itemView.findViewById(R.id.row_save_GrnWithPo_item_name);
            textViewScanQty = itemView.findViewById(R.id.row_save_GrnWithPo_scan_quantity);
            textViewPurAmount = itemView.findViewById(R.id.row_save_GrnWithPo_pur_rate);
            textViewAmount = itemView.findViewById(R.id.row_save_GrnWithPo_pur_amt);
            textViewTaxAmt = itemView.findViewById(R.id.row_save_GrnWithPo_pur_TaxAmt);
            textViewNetAmt = itemView.findViewById(R.id.row_save_GrnWithPo_net_amt);
            appCompatButtonChange = itemView.findViewById(R.id.row_save_GrnWithPo_button_change);
            appCompatButtonRemove = itemView.findViewById(R.id.row_save_GrnWithPo_button_remove);

        }
    }
}
