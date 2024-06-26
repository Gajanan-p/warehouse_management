package com.ssinfomate.warehousemanagement.ui.stockcorrection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.stock_correction.AdjustmentTypeModel;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;


public class HeldStockCorrectionAdapter extends RecyclerView.Adapter<HeldStockCorrectionAdapter.ViewHolder> {
    ArrayList<HeldStockCorrectionModel>heldStockCorrectionModels;
    Context context;
    List<AdjustmentTypeModel> adjustmentTypeModels;

//    public HeldStockCorrectionAdapter(ArrayList<HeldStockCorrectionModel> heldStockCorrectionModels,
//                                      Context context) {
//        this.heldStockCorrectionModels = heldStockCorrectionModels;
//        this.context=context;
//      //  damageReasonModels=AppPreference.getDamageReasonDataPreferences(context);
//    }

    public HeldStockCorrectionAdapter(ArrayList<HeldStockCorrectionModel> heldStockCorrectionModels,
                                       Context context) {
        this.heldStockCorrectionModels = heldStockCorrectionModels;
        this.context = context;
        adjustmentTypeModels= new ArrayList<>();
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_held_stock_correction_list,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        HeldStockCorrectionModel heldStockCorrectionModel = heldStockCorrectionModels.get(position);
        holder.textViewFromWarehouse.setText(heldStockCorrectionModel.getWarehouse());
        holder.textViewItemName.setText(heldStockCorrectionModel.getItemName());
        holder.textViewDocNo.setText(heldStockCorrectionModel.getStockId());
        holder.textViewDamageReason.setText(heldStockCorrectionModel.getStkType());
        holder.textViewRemark.setText(heldStockCorrectionModel.getRemark());
        holder.textViewScanQty.setText(heldStockCorrectionModel.getUpdatetdQty());
        holder.textViewUnit.setText(heldStockCorrectionModel.getUnitName());
        holder.textViewTransferQty.setText(heldStockCorrectionModel.getClqty());
        holder.textViewCreatedBy.setText(heldStockCorrectionModel.getUserName());
        holder.textViewCreatedDate.setText(heldStockCorrectionModel.getCreatedDate());
    }

    @Override
    public int getItemCount() {
        return heldStockCorrectionModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView textViewFromWarehouse;
        AppCompatTextView textViewItemName;
        AppCompatTextView textViewUnit;
        AppCompatTextView textViewTransferQty;
        AppCompatTextView textViewScanQty;
        AppCompatTextView textViewCreatedBy;
        AppCompatTextView textViewDamageReason;
        AppCompatTextView textViewRemark;
        AppCompatTextView textViewCreatedDate;
        AppCompatTextView textViewDocNo;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewFromWarehouse = itemView.findViewById(R.id.text_phsc_from_warehouse_name);
             textViewItemName = itemView.findViewById(R.id.text_phsc_item_name);
             textViewUnit = itemView.findViewById(R.id.text_phsc_unit);
             textViewScanQty = itemView.findViewById(R.id.text_phsc_scan_qty);
             textViewTransferQty = itemView.findViewById(R.id.text_phsc_transfer_qty_name);
             textViewDamageReason = itemView.findViewById(R.id.text_phsc_damage_reason);
             textViewRemark = itemView.findViewById(R.id.text_phsc_damage_remark);
             textViewCreatedBy = itemView.findViewById(R.id.text_phsc_created_by);
             textViewCreatedDate = itemView.findViewById(R.id.text_phsc_created_date);
             textViewDocNo = itemView.findViewById(R.id.text_phsc_from_doc_no);
        }
    }
}
