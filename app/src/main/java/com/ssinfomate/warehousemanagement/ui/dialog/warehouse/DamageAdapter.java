package com.ssinfomate.warehousemanagement.ui.dialog.warehouse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.stock_correction.DamageReasonModel;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class DamageAdapter extends RecyclerView.Adapter<DamageAdapter.ViewHolder> {
    ArrayList<DamageReasonModel>damageReasonModels=new ArrayList<>();


    IDamageReasonItemClick iDamageReasonItemClick;
    public DamageAdapter(  ArrayList<DamageReasonModel>damageReasonModels,
                           IDamageReasonItemClick iDamageReasonItemClick){

        this.damageReasonModels=damageReasonModels;
        this.iDamageReasonItemClick=iDamageReasonItemClick;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.row_dialog_damage_reason,
                parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        DamageReasonModel damageReasonModel=damageReasonModels.get(position);

        holder.appCompatTextViewId.setText(damageReasonModel.getId());
        holder.appCompatTextViewReasonName.setText(damageReasonModel.getReason());
        holder.appCompatTextViewReasonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        iDamageReasonItemClick.onDamageReasonItemClicked(damageReasonModel);

            }
        });
    }

    @Override
    public int getItemCount() {
        return damageReasonModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        AppCompatTextView appCompatTextViewId;
        AppCompatTextView appCompatTextViewReasonName;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            appCompatTextViewId=itemView.findViewById(R.id.row_dialog_damage_reason_id);
            appCompatTextViewReasonName=itemView.findViewById(R.id.row_dialog_damage_reason_name);
        }

    }


}
