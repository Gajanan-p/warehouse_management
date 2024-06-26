package com.ssinfomate.warehousemanagement.ui.reducedbarcode;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.reducedbarcode.ReducedBarcodeRemarkList;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class RemarkAdapter extends RecyclerView.Adapter<RemarkAdapter.ViewHolder>{
   List<ReducedBarcodeRemarkList> reducedBarcodeRemarkLists = new ArrayList<>();
   IRemarkListener iRemarkListener;
   int position;

    public RemarkAdapter(List<ReducedBarcodeRemarkList> reducedBarcodeRemarkLists,
                         IRemarkListener iRemarkListener,
                         int position)
    {
        this.reducedBarcodeRemarkLists = reducedBarcodeRemarkLists;
        this.iRemarkListener = iRemarkListener;
        this.position = position;
    }

    @NonNull
    @NotNull
    @Override
    public RemarkAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dialog_reduced_barcode_remark,
                parent,false);

        return new RemarkAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RemarkAdapter.ViewHolder holder, int position) {

        ReducedBarcodeRemarkList reducedBarcodeRemarkList = reducedBarcodeRemarkLists.get(position);
        holder.appCompatTextViewId.setText(reducedBarcodeRemarkList.getRemarkId());
        holder.appCompatTextViewName.setText(reducedBarcodeRemarkList.getRemarkName());
        holder.appCompatTextViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iRemarkListener.onRemarkNameClicked(reducedBarcodeRemarkList,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reducedBarcodeRemarkLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView appCompatTextViewId;
        AppCompatTextView appCompatTextViewName;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            appCompatTextViewId=itemView.findViewById(R.id.row_dialog_remark);
            appCompatTextViewName=itemView.findViewById(R.id.row_dialog_remark_name);
        }
    }
}
