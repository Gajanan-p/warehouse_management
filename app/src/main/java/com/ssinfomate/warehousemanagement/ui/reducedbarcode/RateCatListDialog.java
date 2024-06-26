package com.ssinfomate.warehousemanagement.ui.reducedbarcode;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.reducedbarcode.RateCatListModel;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class RateCatListDialog extends Dialog {
    ArrayList<RateCatListModel> rateCatListModels;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    IRateCatList iRateCatList;

    public RateCatListDialog(@NonNull @NotNull Context context,
                             ArrayList<RateCatListModel> rateCatListModels,
                             IRateCatList iRateCatList)
    {
        super(context);
        this.rateCatListModels = rateCatListModels;
        this.iRateCatList = iRateCatList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_rate_cate_list);
        adapter=new RateCateListAdapter(rateCatListModels,iRateCatList);
        recyclerView=findViewById(R.id.list_item_rate_cat);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
