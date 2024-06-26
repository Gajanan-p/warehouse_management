package com.ssinfomate.warehousemanagement.ui.dialog.warehouse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.ui.util.Constants;
import com.ssinfomate.warehousemanagement.webservices.warehouse.ToWarehouse;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class ToWarehouseAdapter extends RecyclerView.Adapter<ToWarehouseAdapter.ViewHolder> {
    ArrayList<ToWarehouse>warehouseModels=new ArrayList<>();


ListItemClick listItemClick;
String TYPE;

    public ToWarehouseAdapter(ArrayList<ToWarehouse> warehouseModels,
                              ListItemClick listItemClick,
                              String TYPE){
        this.TYPE=TYPE;
        this.warehouseModels=warehouseModels;
        this.listItemClick=listItemClick;
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
        ToWarehouse warehouseModel= warehouseModels.get(position);
        holder.appCompatTextView.setText(warehouseModel.getCoBrName());
        holder.appCompatTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TYPE!=null){

                    if(TYPE.equals(Constants.TO_WAREHOUSE)){
                        listItemClick.onItemClickedToLocation(warehouseModel);
                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return warehouseModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        AppCompatTextView appCompatTextView;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            appCompatTextView=itemView.findViewById(R.id.row_dialog_warehouse_name);
        }

    }


}
