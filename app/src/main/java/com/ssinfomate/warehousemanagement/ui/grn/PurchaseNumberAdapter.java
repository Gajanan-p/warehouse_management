package com.ssinfomate.warehousemanagement.ui.grn;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.grn.PONumberListModel;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class PurchaseNumberAdapter extends RecyclerView.Adapter<PurchaseNumberAdapter.ViewHolder> {
    ArrayList<PONumberListModel> poNumberListModels;
    IPurchaseNumber iPurchaseNumber;

    public PurchaseNumberAdapter(ArrayList<PONumberListModel> poNumberListModels,
                                 IPurchaseNumber iPurchaseNumber)
    {
        this.poNumberListModels = poNumberListModels;
        this.iPurchaseNumber = iPurchaseNumber;
    }

    @NonNull
    @NotNull
    @Override
    public PurchaseNumberAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dialog_po_no,
                parent,false);

        return new PurchaseNumberAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PurchaseNumberAdapter.ViewHolder holder, int position) {
        PONumberListModel poNumberListModel= poNumberListModels.get(position);
        if (poNumberListModel.getDocKey()!=null) {
            holder.appCompatTextViewPONumber.setText(poNumberListModel.getDocNumber());
        }else{

        }
        holder.appCompatTextViewPONumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iPurchaseNumber.onPoNumberClicked(poNumberListModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return poNumberListModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView appCompatTextViewPONumber;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            appCompatTextViewPONumber = itemView.findViewById(R.id.row_dialog_pur_no);
        }
    }
}
