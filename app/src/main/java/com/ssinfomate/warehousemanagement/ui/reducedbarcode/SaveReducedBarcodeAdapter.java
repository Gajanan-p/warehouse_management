package com.ssinfomate.warehousemanagement.ui.reducedbarcode;

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
import com.ssinfomate.warehousemanagement.webservices.check_stock.CheckStockModel;
import com.ssinfomate.warehousemanagement.webservices.check_stock.PriceListModel;
import com.ssinfomate.warehousemanagement.webservices.reducedbarcode.RequestSaveReducedBarcodeDetail;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class SaveReducedBarcodeAdapter extends RecyclerView.Adapter<SaveReducedBarcodeAdapter.ViewHolder> {
    ArrayList<PriceListModel> listItemStock;
    IOnSaveReducedBarcode iOnSaveReducedBarcode;
    Context context;
   List<RequestSaveReducedBarcodeDetail> requestSaveReducedBarcodeDetails;
    public SaveReducedBarcodeAdapter(ArrayList<PriceListModel> listItemStock,
                                     IOnSaveReducedBarcode iOnSaveReducedBarcode,
                                     Context context) {
        this.listItemStock = listItemStock;
        this.iOnSaveReducedBarcode = iOnSaveReducedBarcode;
        this.context = context;
        requestSaveReducedBarcodeDetails = new ArrayList<>();
    }

    @NonNull
    @NotNull
    @Override

    public SaveReducedBarcodeAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_save_reduced_barcode_list,parent,false);
        return new SaveReducedBarcodeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SaveReducedBarcodeAdapter.ViewHolder holder, int position) {
        PriceListModel checkStockModel = listItemStock.get(position);
        if (checkStockModel.getItemId()!=null) {
            holder.appCompatTextViewItemName.setText(checkStockModel.getItemName());
            holder.appCompatTextViewItemUnits.setText(checkStockModel.getUnitName());
            holder.appCompatTextViewSaleRate.setText(checkStockModel.getSaleRate());
            holder.appCompatTextViewSaleRateBeforeTax.setText(checkStockModel.getSaleRateBeforeTax());
            holder.appCompatTextViewNewSaleRate.setText(checkStockModel.getNewsalerate());
            holder.appCompatTextViewNewSaleRateBeforeTax.setText(checkStockModel.getNewsaleratebeforetax());
            holder.appCompatTextViewNoOfStickers.setText(checkStockModel.getNoofstrickertoprint());
            holder.appCompatTextViewTotalQty.setText(checkStockModel.getClqty());
        }
        else{
            Toast.makeText(context,"Item not found",Toast.LENGTH_LONG).show();
        }
            holder.appCompatButtonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iOnSaveReducedBarcode.onStockItemUpdate(position);
                }
            });
            holder.appCompatTextViewRemark.setText(checkStockModel.getRemarkName());
            holder.appCompatTextViewRemark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iOnSaveReducedBarcode.onRemarkClicked(position);
                }
            });
    }

    @Override
    public int getItemCount() {
        return listItemStock.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView appCompatTextViewItemName;
        AppCompatTextView appCompatTextViewItemUnits;
        AppCompatTextView appCompatTextViewSaleRate;
        AppCompatTextView appCompatTextViewSaleRateBeforeTax;
        AppCompatTextView appCompatTextViewNewSaleRate;
        AppCompatTextView appCompatTextViewNewSaleRateBeforeTax;
        AppCompatTextView appCompatTextViewNoOfStickers;
        AppCompatTextView appCompatTextViewTotalQty;
        AppCompatButton appCompatButtonUpdate;
        AppCompatTextView appCompatTextViewRemark;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            appCompatTextViewItemName = itemView.findViewById(R.id.row_save_reduced_barcode_item_name);
            appCompatTextViewItemUnits = itemView.findViewById(R.id.row_save_reduced_barcode_item_unit);
            appCompatTextViewSaleRate = itemView.findViewById(R.id.row_save_reduced_barcode_sale_rate);
            appCompatTextViewSaleRateBeforeTax = itemView.findViewById(R.id.row_save_reduced_barcode_sale_rate_before_tax);
            appCompatTextViewNewSaleRate = itemView.findViewById(R.id.row_save_reduced_barcode_new_sale_rate);
            appCompatTextViewNewSaleRateBeforeTax = itemView.findViewById(R.id.row_save_reduced_barcode_new_sale_rate_before_tax);
            appCompatTextViewTotalQty = itemView.findViewById(R.id.row_save_reduced_barcode_total_qty);
            appCompatTextViewNoOfStickers = itemView.findViewById(R.id.row_save_reduced_barcode_no_of_stickers);
            appCompatButtonUpdate = itemView.findViewById(R.id.row_save_reduced_barcode_button_update);
            appCompatTextViewRemark = itemView.findViewById(R.id.row_save_reduced_barcode_remark);
        }
    }

}
