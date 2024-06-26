package com.ssinfomate.warehousemanagement.ui.reducedbarcode;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.reducedbarcode.RateCatListModel;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class RateCateListAdapter extends RecyclerView.Adapter<RateCateListAdapter.ViewHolder>{

     ArrayList<RateCatListModel> rateCatListModels;
     IRateCatList iRateCatList;

    public RateCateListAdapter(ArrayList<RateCatListModel> rateCatListModels,
                               IRateCatList iRateCatList)
    {
        this.rateCatListModels = rateCatListModels;
        this.iRateCatList = iRateCatList;
    }

    @NonNull
    @NotNull
    @Override
    public RateCateListAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dialog_rate_cat,
                parent,false);

        return new RateCateListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RateCateListAdapter.ViewHolder holder, int position) {
        RateCatListModel rateCatListModel= rateCatListModels.get(position);
        holder.appCompatTextView.setText(rateCatListModel.getRatecatName());
        holder.appCompatTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iRateCatList.onRateCatClicked(rateCatListModel);
            }
        });
    }

    @Override
    public int getItemCount() {

        return rateCatListModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView appCompatTextView;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            appCompatTextView =itemView.findViewById(R.id.row_dialog_rate_cat_name);
        }


    }
}
