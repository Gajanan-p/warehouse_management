package com.ssinfomate.warehousemanagement.ui.dialog.warehouse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class PrinterSizeAdapter  extends RecyclerView.Adapter<PrinterSizeAdapter.ViewHolder> {
    ArrayList<String> labelSizesList;
    PrinterSizeListener pinterSizeListener;

    public PrinterSizeAdapter(ArrayList<String>labelSizesList, PrinterSizeListener pinterSizeListener){
        this.labelSizesList=labelSizesList;
        this.pinterSizeListener=pinterSizeListener;

    }
    @NonNull
    @NotNull
    @Override
    public PrinterSizeAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dialog_warehouse,
                parent,false);

        return new PrinterSizeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PrinterSizeAdapter.ViewHolder holder, int position) {
        String PrinterSize= labelSizesList.get(position);
        holder.appCompatTextView.setText(PrinterSize);
        holder.appCompatTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinterSizeListener.onItemClicked(PrinterSize);
            }
        });
    }

    @Override
    public int getItemCount() {
        return labelSizesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        AppCompatTextView appCompatTextView;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            appCompatTextView=itemView.findViewById(R.id.row_dialog_warehouse_name);
        }

    }


}