package com.ssinfomate.warehousemanagement.ui.stockcorrection;

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

public class AdjustmentTypeAdapter  extends RecyclerView.Adapter<AdjustmentTypeAdapter.ViewHolder> {
    ArrayList<AdjustmentTypeModel> adjustmentTypeModels = new ArrayList<>();
    IAdjustmentType iAdjustmentType;
    int position;

    public AdjustmentTypeAdapter(ArrayList<AdjustmentTypeModel> adjustmentTypeModels,
                                 IAdjustmentType iAdjustmentType,
                                 int position) {
        this.adjustmentTypeModels = adjustmentTypeModels;
        this.iAdjustmentType = iAdjustmentType;
        this.position = position;
    }

    @NonNull
    @NotNull
    @Override
    public AdjustmentTypeAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dialog_adjustment_type,
                parent,false);

        return new AdjustmentTypeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdjustmentTypeAdapter.ViewHolder holder, int position) {
         AdjustmentTypeModel adjustmentTypeModel = adjustmentTypeModels.get(position);
       //  holder.appCompatTextViewAdjustTypeId.setText(adjustmentTypeModel.getId());
         holder.appCompatTextViewAdjTypName.setText(adjustmentTypeModel.getReason());
         holder.appCompatTextViewAdjTypName.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 AdjustmentTypeModel adjustmentTypeModel1 =new AdjustmentTypeModel();
                 adjustmentTypeModel1.setId(adjustmentTypeModel.getId());
                 adjustmentTypeModel1.setReason(adjustmentTypeModel.getReason());
                 onClickedName(adjustmentTypeModel1);
                 //iAdjustmentType.onAdjustmentTypeClicked(adjustmentTypeModel,position);
             }
         });

    }
    void onClickedName(AdjustmentTypeModel adjustmentTypeModel){
        iAdjustmentType.onAdjustmentTypeClicked(adjustmentTypeModel, this.position);
    };
    @Override
    public int getItemCount() {
        return adjustmentTypeModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView appCompatTextViewAdjustTypeId;
        AppCompatTextView appCompatTextViewAdjTypName;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

         //   appCompatTextViewAdjustTypeId = itemView.findViewById(R.id.row_dialog_Adjustment_type_id);
            appCompatTextViewAdjTypName = itemView.findViewById(R.id.row_dialog_Adjustment_type_name);

        }
    }
}
