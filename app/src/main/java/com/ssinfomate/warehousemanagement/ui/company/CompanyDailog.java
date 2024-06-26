package com.ssinfomate.warehousemanagement.ui.company;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import java.util.ArrayList;

public class CompanyDailog extends Dialog {
    ArrayList<ChangeCompanyModel>changeCompanyViewModels;
    RecyclerView recyclerViewCompany;
    CompanyAdapter companyAdapter;
    ICompanyListener iCompanyListener;

    public CompanyDailog(@NonNull Context context,
                         ArrayList<ChangeCompanyModel>changeCompanyViewModels,
                        ICompanyListener iCompanyListener
    ) {
        super(context);
        this.changeCompanyViewModels = changeCompanyViewModels;
        this.iCompanyListener = iCompanyListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dailog_change_company);
        recyclerViewCompany = findViewById(R.id.list_company_dialog);
        companyAdapter = new CompanyAdapter(changeCompanyViewModels,iCompanyListener);
        recyclerViewCompany.setAdapter(companyAdapter);
        recyclerViewCompany.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}


