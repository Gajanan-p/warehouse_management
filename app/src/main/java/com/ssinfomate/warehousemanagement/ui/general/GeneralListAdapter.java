package com.ssinfomate.warehousemanagement.ui.general;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.general.ResponseGeneralList;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GeneralListAdapter extends RecyclerView.Adapter<GeneralListAdapter.ViewHolder> {
    ArrayList<ResponseGeneralList> responseGeneralLists;
    IGeneralItem iGeneralItem;

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.row_general_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ResponseGeneralList responseGeneralList=responseGeneralLists.get(position);
        holder.textViewProductName.setText(responseGeneralList.getItemName());
        holder.textViewSystemQuantity.setText(responseGeneralList.getClQty());
        holder.textViewUnit.setText(responseGeneralList.getUnitName());
        holder.textViewCreatedDate.setText(responseGeneralList.getCreatedDate());
        holder.editTextRemarkOne.setText(responseGeneralList.getRemark1());
        holder.editTextRemarkTwo.setText(responseGeneralList.getRemark2());
        holder.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iGeneralItem.onGeneralItemUpdateClicked(responseGeneralList);
            }
        });

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iGeneralItem.onGeneralItemDeleteClicked(responseGeneralList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return responseGeneralLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView editTextRemarkOne;
        TextView editTextRemarkTwo;
        TextView textViewProductName;
        TextView textViewSystemQuantity;
        TextView textViewCreatedDate;
        TextView textViewUnit;
        Button buttonUpdate;
        Button buttonDelete;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewProductName=itemView.findViewById(R.id.row_text_general_product_name);
            textViewSystemQuantity=itemView.findViewById(R.id.row_text_general_system_quantity);
            textViewUnit=itemView.findViewById(R.id.row_text_general_unit);
            textViewCreatedDate = itemView.findViewById(R.id.row_text_general_created_date);
            editTextRemarkOne=itemView.findViewById(R.id.row_text_general_remark_one);
            editTextRemarkTwo=itemView.findViewById(R.id.row_text_general_remark_two);
            buttonUpdate=itemView.findViewById(R.id.row_button_general_update);
            buttonDelete=itemView.findViewById(R.id.row_button_general_delete);
        }
    }

    public GeneralListAdapter(ArrayList<ResponseGeneralList> responseGeneralLists,
                              IGeneralItem iGeneralItem) {
        this.responseGeneralLists = responseGeneralLists;
        this.iGeneralItem=iGeneralItem;
    }
}
