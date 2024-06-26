package com.ssinfomate.warehousemanagement.ui.stocktransfer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.PickHeldStockTransferModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PickHedAdapter extends RecyclerView.Adapter<PickHedAdapter.ViewHolder> {
    ArrayList<PickHeldStockTransferModel>pickHeldStockTransferModels;

    public PickHedAdapter(ArrayList<PickHeldStockTransferModel> pickHeldStockTransferModels) {
        this.pickHeldStockTransferModels = pickHeldStockTransferModels;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
          View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pick_held_list,
                  parent,false);

          return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        PickHeldStockTransferModel pickHeldStockTransferModel=pickHeldStockTransferModels.get(position);
          holder.textViewItemName.setText(pickHeldStockTransferModel.getItemName());
          holder.textViewFromWarehouse.setText(String.valueOf(pickHeldStockTransferModel.getFromwarehouse()));
          holder.textViewToWarehouse.setText(pickHeldStockTransferModel.getTowarehouse());
          holder.textViewScanQty.setText(String.valueOf(pickHeldStockTransferModel.getClqty()));
          holder.textViewTransferQty.setText(String.valueOf(pickHeldStockTransferModel.getAdjqty()));
          holder.textViewDocument.setText(pickHeldStockTransferModel.getDocKey());
          holder.textViewCreatedDate.setText(pickHeldStockTransferModel.getCreatedDt());
          holder.textViewCreatedBy.setText(pickHeldStockTransferModel.getCreatedBy());
         // holder.textViewDocNo.setText(pickHeldStockTransferModel.get);
    }

    @Override
    public int getItemCount() {
        return pickHeldStockTransferModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView textViewItemName;
        private AppCompatTextView textViewFromWarehouse;
        private AppCompatTextView textViewToWarehouse;
        private AppCompatTextView textViewScanQty;
        private AppCompatTextView textViewTransferQty;
        private AppCompatTextView textViewDocument;
        private AppCompatTextView textViewCreatedDate;
        private AppCompatTextView textViewCreatedBy;
       // private AppCompatTextView textViewDocNo;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewItemName = itemView.findViewById(R.id.text_from_item_name);
            textViewFromWarehouse = itemView.findViewById(R.id.text_from_warehouse_name);
            textViewToWarehouse = itemView.findViewById(R.id.text_to_warehouse_name);
            textViewScanQty = itemView.findViewById(R.id.text_scan_qty_name);
            textViewTransferQty = itemView.findViewById(R.id.text_transfer_qty_name);
            textViewCreatedDate = itemView.findViewById(R.id.text_created_date);
            textViewDocument = itemView.findViewById(R.id.text_document_key);
            textViewCreatedBy = itemView.findViewById(R.id.text_created_by);
         //   textViewDocNo = itemView.findViewById(R.id.text_document_number);
        }

    }
}
