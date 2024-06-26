package com.ssinfomate.warehousemanagement.ui.company;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import com.ssinfomate.warehousemanagement.R;
public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ViewHolder> {
    ArrayList<ChangeCompanyModel> changeCompanyViewModels;
    ICompanyListener iCompanyListener;

    public CompanyAdapter(ArrayList<ChangeCompanyModel> changeCompanyViewModels, ICompanyListener iCompanyListener) {
        this.changeCompanyViewModels = changeCompanyViewModels;
        this.iCompanyListener = iCompanyListener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_company_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
     ChangeCompanyModel changeCompanyViewModel = changeCompanyViewModels.get(position);
     holder.textViewCompanyName.setText(changeCompanyViewModel.getCoName());
     holder.textViewCompanyName.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             iCompanyListener.onCompanySelect(changeCompanyViewModel);
         }
     });
    }

    @Override
    public int getItemCount() {
        return changeCompanyViewModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView textViewCompanyName;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewCompanyName = itemView.findViewById(R.id.text_company_name);
        }
    }
}