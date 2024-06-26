package com.ssinfomate.warehousemanagement.ui.general;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.general.GeneralRemarkModel;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class GeneralRemarkAdapter  extends RecyclerView.Adapter<GeneralRemarkAdapter.ViewHolder> {
    ArrayList<GeneralRemarkModel>generalRemarkModels = new ArrayList<>();
    IGeneralRemarkListener iGeneralRemarkListener;

    public GeneralRemarkAdapter(ArrayList<GeneralRemarkModel> generalRemarkModels,
                                IGeneralRemarkListener iGeneralRemarkListener)
    {
        this.generalRemarkModels = generalRemarkModels;
        this.iGeneralRemarkListener = iGeneralRemarkListener;
    }

    @NonNull
    @NotNull
    @Override
    public GeneralRemarkAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_general_remark,
                parent,false);

        return new GeneralRemarkAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GeneralRemarkAdapter.ViewHolder holder, int position) {

        GeneralRemarkModel generalRemarkModel = generalRemarkModels.get(position);
        holder.textViewName.setText(generalRemarkModel.getRemarkName());
        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iGeneralRemarkListener.onRemarkClicked(generalRemarkModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return generalRemarkModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView textViewName;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.row_dialog_general_remark_name);
        }
    }
}
