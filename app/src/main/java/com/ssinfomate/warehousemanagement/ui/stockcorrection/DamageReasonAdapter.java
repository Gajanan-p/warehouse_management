package com.ssinfomate.warehousemanagement.ui.stockcorrection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.stock_correction.DamageReasonModel;
import com.ssinfomate.warehousemanagement.webservices.stock_correction.DamageReasonModels;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DamageReasonAdapter extends RecyclerView.Adapter<DamageReasonAdapter.ViewHolder> {

    List<DamageReasonModels> reasonModels=new ArrayList<>();
    IDamageReason iDamageReason;
    int position;

    public DamageReasonAdapter(List<DamageReasonModels> damageReasonModels,
                               IDamageReason iDamageReason,
                               int position
    )
    {
        this.reasonModels=damageReasonModels;
        this.iDamageReason=iDamageReason;
        this.position=position;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dialog_damage_reason,
                parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        DamageReasonModels damageReasonModel=reasonModels.get(position);

        holder.appCompatTextViewId.setText(damageReasonModel.getStockcorrereasonId());
        holder.appCompatTextViewName.setText(damageReasonModel.getReasonName());
        holder.appCompatTextViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DamageReasonModel damageReasonModel1=new DamageReasonModel();

                damageReasonModel1.setId(Integer.parseInt(damageReasonModel.getStockcorrereasonId()));
                damageReasonModel1.setReason(damageReasonModel.getReasonName());
                onClickedName(damageReasonModel1);
//                        iDamageReason.onDamageReasonClicked(damageReasonModel, this.position);
            }
        });
    }

    void onClickedName(DamageReasonModel damageReasonModel){
        iDamageReason.onDamageReasonClicked(damageReasonModel, this.position);
    };
    @Override
    public int getItemCount() {
        return reasonModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        AppCompatTextView appCompatTextViewId;
        AppCompatTextView appCompatTextViewName;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            appCompatTextViewId=itemView.findViewById(R.id.row_dialog_damage_reason_id);
            appCompatTextViewName=itemView.findViewById(R.id.row_dialog_damage_reason_name);
        }

    }


}
