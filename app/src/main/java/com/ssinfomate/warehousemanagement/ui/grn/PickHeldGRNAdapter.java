
package com.ssinfomate.warehousemanagement.ui.grn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.grn.HeldGrnListModel;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;


public class PickHeldGRNAdapter extends RecyclerView.Adapter<PickHeldGRNAdapter.ViewHolder> {

    ArrayList<HeldGrnListModel> heldGrnListModels;
    Context context;

    public PickHeldGRNAdapter(ArrayList<HeldGrnListModel> heldGrnListModels)
    {
        this.heldGrnListModels = heldGrnListModels;

    }

    @NonNull
    @NotNull
    @Override
    public PickHeldGRNAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_held_grn_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PickHeldGRNAdapter.ViewHolder holder, int position) {

        HeldGrnListModel heldGrnListModel = heldGrnListModels.get(position);
        holder.textViewItemName.setText(heldGrnListModel.getItemName());
        holder.textViewDockKey.setText(heldGrnListModel.getDocNo());
        holder.textViewActQty.setText(heldGrnListModel.getActQty());
//        holder.textViewPurRate.setText(heldGrnListModel.getPurRate());
        holder.textViewTaxable.setText(heldGrnListModel.getTaxable());
//        holder.textViewTaxAmt.setText(heldGrnListModel.getTaxAmt());
//        holder.textViewNetAmt.setText(heldGrnListModel.getNetamt());
        holder.textViewCreatedDate.setText(heldGrnListModel.getDocDt());
    }

    @Override
    public int getItemCount() {
        return heldGrnListModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView textViewItemName;
        AppCompatTextView textViewDockKey;
        AppCompatTextView textViewActQty;
//        AppCompatTextView textViewPurRate;
        AppCompatTextView textViewTaxable;
//        AppCompatTextView textViewTaxAmt;
//        AppCompatTextView textViewNetAmt;
        AppCompatTextView textViewCreatedDate;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewItemName  = itemView.findViewById(R.id.text_phgrn_item_name);
            textViewDockKey = itemView.findViewById(R.id.text_phgrn_doc_key);
            textViewActQty = itemView.findViewById(R.id.text_phgrn_act_qty);
//            textViewPurRate = itemView.findViewById(R.id.text_phgrn_pur_rate);
            textViewTaxable = itemView.findViewById(R.id.text_phgrn_taxable);
//            textViewTaxAmt = itemView.findViewById(R.id.text_phgrn_tax_amt);
//            textViewNetAmt = itemView.findViewById(R.id.text_phgrn_net_amt);
            textViewCreatedDate = itemView.findViewById(R.id.text_phgrn_created_date);
        }
    }
}
