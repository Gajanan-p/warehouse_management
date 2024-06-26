package com.ssinfomate.warehousemanagement.ui.acceptstocktransferList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AcceptStockTransferListAdapter extends RecyclerView.Adapter<AcceptStockTransferListAdapter.ViewHolder> {

    ArrayList<AcceptStockTransferListModel> acceptStockTransferListModels;
    IAcceptStock iAcceptStock;

    public AcceptStockTransferListAdapter(ArrayList<AcceptStockTransferListModel> acceptStockTransferListModels,IAcceptStock iAcceptStock) {
        this.acceptStockTransferListModels = acceptStockTransferListModels;
        this.iAcceptStock=iAcceptStock;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_accept_stock_transfer_list,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        AcceptStockTransferListModel acceptStockTransferListModel = acceptStockTransferListModels.get(position);
        holder.textViewBusinessLocation.setText(acceptStockTransferListModel.getBusinessName());
        holder.textViewFromWarehouse.setText(acceptStockTransferListModel.getFromWarehouse());
        holder.textViewToWarehouse.setText(acceptStockTransferListModel.getToLocationName());
        holder.textViewScanQty.setText(String.valueOf(acceptStockTransferListModel.getScanQty()));
        holder.textToItemName.setText(acceptStockTransferListModel.getItemName());
        holder.textViewTransferQty.setText(acceptStockTransferListModel.getTransferQty());
        holder.textViewScanQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iAcceptStock.onStockItemQuantityClicked(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return acceptStockTransferListModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView textViewBusinessLocation;
        AppCompatTextView textViewFromWarehouse;
        AppCompatTextView textViewToWarehouse;
        AppCompatTextView textViewScanQty;
        AppCompatTextView textViewTransferQty;
        AppCompatTextView textToItemName;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewBusinessLocation = itemView.findViewById(R.id.text_business_location_name);
            textViewFromWarehouse = itemView.findViewById(R.id.text_ASTL_from_warehouse_name);
            textViewToWarehouse = itemView.findViewById(R.id.text_ASTL_to_warehouse_name);
            textViewScanQty = itemView.findViewById(R.id.text_ASTL_scan_qty_name);
            textViewTransferQty = itemView.findViewById(R.id.text_ASTL_transfer_qty_name);
            textToItemName=itemView.findViewById(R.id.text_ASTL_to_item_name);
        }
    }
}
