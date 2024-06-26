package com.ssinfomate.warehousemanagement.ui.stocktransfernotification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.stock_notification.StockTransferNotificationResponse;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class StockTransferNotificationAdapter extends RecyclerView.Adapter<StockTransferNotificationAdapter.ViewHolder> {
ArrayList<StockTransferNotificationResponse> stockTransferNotificationResponses;
IStockStatus iStockStatus;
Context context;

    public StockTransferNotificationAdapter(
            ArrayList<StockTransferNotificationResponse> stockTransferNotificationResponses,
            IStockStatus iStockStatus,
            Context context) {
        this.stockTransferNotificationResponses = stockTransferNotificationResponses;
        this.iStockStatus=iStockStatus;
        this.context=context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_stock_transfer_notification_list,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        StockTransferNotificationResponse stockTransferNotificationViewModel = stockTransferNotificationResponses.get(position);
        holder.textViewFromWarehouse.setText(stockTransferNotificationViewModel.getFromWarehouse());
        holder.textViewToWarehouse.setText(stockTransferNotificationViewModel.getToLocationName());
        holder.textViewScanQty.setText(String.valueOf(stockTransferNotificationViewModel.getItemName()));
        holder.textViewTransferQty.setText(stockTransferNotificationViewModel.getScanQty());
        holder.appCompatButtonCanceled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iStockStatus.onCanceledClicked(position);
                holder.appCompatButtonCanceled
                        .setBackgroundDrawable(
                                context.getResources()
                                        .getDrawable(R.drawable.button_stock_canceled_background));
                holder.appCompatButtonAccepted
                        .setBackgroundDrawable(
                                context.getResources()
                                        .getDrawable(R.drawable.button_stock_accept_background));
                holder.appCompatButtonAccepted.setTextColor(context.getResources().getColor(R.color.white));
                holder.appCompatButtonAccepted.setTextColor(context.getResources().getColor(R.color.black));
            }
        });

        holder.appCompatButtonAccepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iStockStatus.onAcceptedClicked(position);
                holder.appCompatButtonCanceled
                        .setBackgroundDrawable(
                                context.getResources()
                                        .getDrawable(R.drawable.button_stock_cancel_background));

                holder.appCompatButtonAccepted
                        .setBackgroundDrawable(
                                context.getResources()
                                        .getDrawable(R.drawable.button_stock_accepted_background));
                holder.appCompatButtonAccepted.setTextColor(context.getResources().getColor(R.color.black));
                holder.appCompatButtonAccepted.setTextColor(context.getResources().getColor(R.color.white));
            }
        });
    }

    @Override
    public int getItemCount() {
        return stockTransferNotificationResponses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView textViewFromWarehouse;
        AppCompatTextView textViewToWarehouse;
        AppCompatTextView textViewScanQty;
        AppCompatTextView textViewTransferQty;
        AppCompatButton appCompatButtonCanceled;
        AppCompatButton appCompatButtonAccepted;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewFromWarehouse = itemView.findViewById(R.id.text_stn_from_warehouse_name);
            textViewToWarehouse = itemView.findViewById(R.id.text_stn_to_warehouse_name);
            textViewScanQty = itemView.findViewById(R.id.text_stn_scan_qty_name);
            textViewTransferQty = itemView.findViewById(R.id.text_stn_transfer_qty_name);
            appCompatButtonAccepted=itemView.findViewById(R.id.button_stock_tranfer_notification_accept);
            appCompatButtonCanceled=itemView.findViewById(R.id.button_stock_tranfer_notification_cancel);
        }
    }
}
