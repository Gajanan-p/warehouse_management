
package com.ssinfomate.warehousemanagement.ui.company;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import com.ssinfomate.warehousemanagement.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;
import com.ssinfomate.warehousemanagement.utils.AppPreference;
import com.ssinfomate.warehousemanagement.utils.AppSetting;
import com.ssinfomate.warehousemanagement.webservices.WebServiceClient;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CompanyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompanyFragment extends Fragment implements View.OnClickListener, ICompanyListener {

    private ChangeCompanyModel mViewModel;
    AppCompatTextView textViewSelectCompany;

    ArrayList<ChangeCompanyModel> companylist;
    private ProgressDialog progressDialog;

    AppSetting appSetting;
    ICompanyListener iCompanyListener;
    ArrayList<ChangeCompanyModel> changeCompanyModels;
    CompanyDailog companyDailog;
    ChangeCompanyModel changeCompanyModel;
    String equalURL = "http://";

    public static CompanyFragment newInstance() {
        return new CompanyFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company, container, false);

        textViewSelectCompany = view.findViewById(R.id.text_company_name);
        textViewSelectCompany.setOnClickListener(this);

        appSetting = AppPreference.getSettingDataPreferences(getContext());

        ChangeCompanyModel changeCompanyModel = AppPreference.getCompanyDataPreferences(getContext());
        if(changeCompanyModel!=null){
            textViewSelectCompany.setText(changeCompanyModel.getCoName());
        }


        return view;
    }

    public void onClick(View v) {
        if (v.getId() == R.id.text_company_name) {
            if (appSetting.getSettingServerURL()!=null) {
                showCompanyDialog();
            }else {
                Toast.makeText(getContext(),"Please select server url !",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ChangeCompanyModel changeCompanyModel = AppPreference.getCompanyDataPreferences(getContext());
        if(changeCompanyModel!=null){
            textViewSelectCompany.setText(changeCompanyModel.getCoName());
        }
        getCompany();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    void showCompanyDialog() {
        if (companylist != null) {
            if (companylist.size() > 0) {
                companyDailog = new CompanyDailog(getContext(), companylist, this);
                companyDailog.show();
            } else {
                Toast.makeText(getActivity(), "No Company Available ", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "No Company Available ", Toast.LENGTH_SHORT).show();
        }
    }

    public void getCompany() {
        progressDialog = createProgressDialog(getContext());
        Call<ArrayList<ChangeCompanyModel>> arrayListCall = WebServiceClient.getCompanyService(appSetting.getSettingServerURL()).getCompanyList();
        arrayListCall.enqueue(new Callback<ArrayList<ChangeCompanyModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ChangeCompanyModel>> call, Response<ArrayList<ChangeCompanyModel>> response) {
                if (response.isSuccessful()) {
                    changeCompanyModels = response.body();
                    if (changeCompanyModels.get(0).getCoId()!=null) {
                        updateCompany(changeCompanyModels);
                    }else{ Toast.makeText(getContext(), "Please Check The Server URL!", Toast.LENGTH_SHORT).show();}
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<ChangeCompanyModel>> call, Throwable t) {
                Log.e("Error", t.getMessage());
                progressDialog.dismiss();
            }
        });
    }


    void updateCompany(ArrayList<ChangeCompanyModel> changeCompanyViewModels) {
        companylist = changeCompanyViewModels;
    }

    @Override
    public void onCompanySelect(ChangeCompanyModel changeCompanyModel) {

        AppPreference.setCompanyDataPreferences(getContext(),changeCompanyModel);
        Log.i("Company", changeCompanyModel.getCoName());
        this.changeCompanyModel = changeCompanyModel;
        textViewSelectCompany.setText(changeCompanyModel.getCoName());
        companyDailog.dismiss();
    }

    public ProgressDialog createProgressDialog(Context mContext) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {

        }
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.blue(100)));
        dialog.setContentView(R.layout.dialog_progress_layout);
        return dialog;
    }

}