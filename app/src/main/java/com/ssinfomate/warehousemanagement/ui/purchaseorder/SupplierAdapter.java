package com.ssinfomate.warehousemanagement.ui.purchaseorder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.supplier.SupplierModel;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class SupplierAdapter extends RecyclerView.Adapter<SupplierAdapter.ViewHolder> {
    ArrayList<SupplierModel>supplierModels=new ArrayList<>();
    ISupplierInterface iSupplierInterface;


    public SupplierAdapter(ArrayList<SupplierModel>supplierModels,
                           ISupplierInterface iSupplierInterface
                           ){
        this.iSupplierInterface=iSupplierInterface;
        this.supplierModels=supplierModels;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dialog_warehouse,
                parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        SupplierModel supplierModel= supplierModels.get(position);
        holder.appCompatTextView.setText(supplierModel.getLedName());
        holder.appCompatTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           iSupplierInterface.onSupplierClicked(supplierModel);

            }
        });
    }

    @Override
    public int getItemCount() {
        return supplierModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        AppCompatTextView appCompatTextView;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            appCompatTextView=itemView.findViewById(R.id.row_dialog_warehouse_name);
        }

    }


}
