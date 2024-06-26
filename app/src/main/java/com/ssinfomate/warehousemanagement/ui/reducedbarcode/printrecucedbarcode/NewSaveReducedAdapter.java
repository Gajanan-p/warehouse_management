package com.ssinfomate.warehousemanagement.ui.reducedbarcode.printrecucedbarcode;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.reducedbarcode.SaveReducedBarcodeModel;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class NewSaveReducedAdapter extends RecyclerView.Adapter<NewSaveReducedAdapter.ViewHolder> {

    List<SaveReducedBarcodeModel> newReducedStockModels;
    INewReducedBarcode iNewReducedBarcode;

    public NewSaveReducedAdapter(List<SaveReducedBarcodeModel> newReducedStockModels,
                                 INewReducedBarcode iNewReducedBarcode)
    {
        this.newReducedStockModels = newReducedStockModels;
        this.iNewReducedBarcode = iNewReducedBarcode;
    }

    @NonNull
    @NotNull
    @Override
    public NewSaveReducedAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_reduced_print_price_tag_list,parent,false);
        return new NewSaveReducedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NewSaveReducedAdapter.ViewHolder holder, int position) {
        SaveReducedBarcodeModel newReducedStockModel = newReducedStockModels.get(position);
        holder.appCompatTextViewItemName.setText(newReducedStockModel.getItemName());
        holder.appCompatTextViewBarcode.setText(newReducedStockModel.getBarcode());
        holder.appCompatTextViewOldSaleRate .setText(newReducedStockModel.getOldsalerate());
        holder.appCompatTextViewNewSaleRate.setText(newReducedStockModel.getNewsalerate());
        holder.appCompatTextViewNoOfStickers.setText(newReducedStockModel.getNoOfstickerstoprint());
        holder.appCompatButtonPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iNewReducedBarcode.onNewReducedBarcode(newReducedStockModel);
            }
        });

    }

    @Override
    public int getItemCount() {
        return newReducedStockModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView appCompatTextViewItemName;
        AppCompatTextView appCompatTextViewBarcode;
        AppCompatTextView appCompatTextViewOldSaleRate;
        AppCompatTextView appCompatTextViewNewSaleRate;
        AppCompatTextView appCompatTextViewNoOfStickers;
        AppCompatButton appCompatButtonPrint;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            appCompatTextViewItemName = itemView.findViewById(R.id.print_reduced_barcode_product_name);
            appCompatTextViewBarcode = itemView.findViewById(R.id.print_reduced_barcode_barcode);
            appCompatTextViewOldSaleRate = itemView.findViewById(R.id.print_reduced_barcode_old_sale_rate);
            appCompatTextViewNewSaleRate = itemView.findViewById(R.id.print_reduced_barcode_new_sale_rate);
            appCompatTextViewNoOfStickers = itemView.findViewById(R.id.print_reduced_barcode_no_of_stickers);
            appCompatButtonPrint = itemView.findViewById(R.id.button_print_reduced_barcode);
        }
    }
}
