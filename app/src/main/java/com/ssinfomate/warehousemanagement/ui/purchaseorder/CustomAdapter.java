package com.ssinfomate.warehousemanagement.ui.purchaseorder;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.supplier.SupplierModel;
import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> implements Filterable {

    ArrayList<SupplierModel> userList;
    ArrayList<SupplierModel> filteredUserList;
    Context context;
    CustomItemClickListener customItemClickListener;
     int position;
    public CustomAdapter(Context context, ArrayList<SupplierModel> userArrayList,
                         CustomItemClickListener customItemClickListener
    ) {
        this.context = context;
        this.userList = userArrayList;
        this.filteredUserList = userArrayList;
        this.customItemClickListener = customItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_dialog_warehouse, viewGroup, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // for click item listener
                customItemClickListener.onItemClick(filteredUserList.get(myViewHolder.getAdapterPosition()),myViewHolder.getAdapterPosition());
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        SupplierModel model = filteredUserList.get(position);
        viewHolder.userName.setText(model.getLedName());
        viewHolder.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customItemClickListener.onItemClick(model, position);
            }
        });

    }
    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String searchString = charSequence.toString();

                if (searchString.isEmpty()) {

                    filteredUserList = userList;

                } else {

                    ArrayList<SupplierModel> tempFilteredList = new ArrayList<>();

                    for (SupplierModel model : userList) {
                        if (model.getLedName().length() > 0) {
                            // search for user title
                            if (model.getLedName().length() < searchString.length()) {

                            }
                            else {
                                if (model.getLedName().substring(0, searchString.length()).toLowerCase().equals(searchString)) {
                                    tempFilteredList.add(model);
                                }
                            }
                        }
                    }
                    filteredUserList = tempFilteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredUserList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredUserList = (ArrayList<SupplierModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView userName;
        private AppCompatTextView textViewId;

        public MyViewHolder(View view) {
            super(view);
            userName = (TextView)view.findViewById(R.id.row_dialog_warehouse_name);
          //  textViewId = view.findViewById(R.id.row_dialog_item_code);
        }
    }
}

