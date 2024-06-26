package com.ssinfomate.warehousemanagement.ui.grn;

import androidx.lifecycle.ViewModelProvider;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.grn.GrnWithPurOrderModel;
import com.ssinfomate.warehousemanagement.webservices.grn.PONumberListModel;
import com.ssinfomate.warehousemanagement.webservices.grn.RequestGRNAginstPurOrder;
import com.ssinfomate.warehousemanagement.webservices.grn.RequestGRNAginstPurOrderDetail;
import com.ssinfomate.warehousemanagement.webservices.grn.RequestGrnWithPurOrderModel;
import com.ssinfomate.warehousemanagement.webservices.grn.SaveWithPurNoModel;
import com.ssinfomate.warehousemanagement.webservices.login.UserModel;
import com.ssinfomate.warehousemanagement.webservices.supplier.SupplierModel;
import com.ssinfomate.warehousemanagement.webservices.warehouse.WarehouseModel;
import com.ssinfomate.warehousemanagement.utils.AppPreference;
import com.ssinfomate.warehousemanagement.utils.AppSetting;
import com.ssinfomate.warehousemanagement.webservices.WebServiceClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveWithPurNoFragment extends Fragment implements
        View.OnClickListener,
        IOnSaveGrnWithPur,
        IOnSaveGrnQty
            {

    private SaveWithPurNoModel mViewModel;
    Button buttonSaveGrnAgainstPo;
    private ProgressDialog progressDialog;
    NavController navController;

    RecyclerView recyclerView;
    GRNWithPurchaseNoAdapter grnWithPurchaseNoAdapter;
    ArrayList<GrnWithPurOrderModel> listGrnWithPurOrderModel;
    SaveGrnQtyChangeDialog saveGrnQtyChangeDialog;
    RequestGrnWithPurOrderModel requestGrnWithPurOrderModel = new RequestGrnWithPurOrderModel();


    public static SaveWithPurNoFragment newInstance() {
        return new SaveWithPurNoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.save_with_pur_no_fragment, container, false);

        recyclerView = view.findViewById(R.id.recycler_save_GrnWithPo_list);

        listGrnWithPurOrderModel = new ArrayList<>();

       buttonSaveGrnAgainstPo = view.findViewById(R.id.save_GrnWithPo_button);
       buttonSaveGrnAgainstPo.setOnClickListener(this::createGrnAgainstPONumber);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view,
                              @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SaveWithPurNoModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onResume() {
        super.onResume();
        searchSaveStock();
    }

     public void searchSaveStock(){
        progressDialog = createProgressDialog(getContext());
        UserModel userModel= AppPreference.getLoginDataPreferences(getContext());
        AppSetting appSetting=AppPreference.getSettingDataPreferences(getContext());
        PONumberListModel poNumberListModel = AppPreference.getPurchaseNumberPreferences(getContext());

        requestGrnWithPurOrderModel.setCoBrId(userModel.getCobrId());
        requestGrnWithPurOrderModel.setDocNumber(poNumberListModel.getDocNumber());

        Call<ArrayList<GrnWithPurOrderModel>> arrayListCall = WebServiceClient
                .setWithoutPurOrderServices(appSetting.getSettingServerURL())
                .listGrnWithPurOrders(requestGrnWithPurOrderModel);
        arrayListCall.enqueue(new Callback<ArrayList<GrnWithPurOrderModel>>() {
            @Override
            public void onResponse(Call<ArrayList<GrnWithPurOrderModel>> call,
                                   Response<ArrayList<GrnWithPurOrderModel>> response) {
                if (response.body().get(0).getItemName()!=null){
                    listGrnWithPurOrderModel=response.body();
                   changeDataSet(listGrnWithPurOrderModel);}
                else {
                    Toast.makeText(getContext(),"Item not found",Toast.LENGTH_LONG).show();

                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ArrayList<GrnWithPurOrderModel>> call, Throwable t) {
                Log.i("Error",t.getMessage());
            }
        });

    }

    public void changeDataSet(ArrayList<GrnWithPurOrderModel> grnWithPurOrderModels){
        GrnWithPurOrderModel grnWithPurOrderModel = grnWithPurOrderModels.get(0);
        boolean isFound=false;
                 if (grnWithPurOrderModels.size()>0){
                     for (int i=0;i<grnWithPurOrderModels.size();i++){
                         if (grnWithPurOrderModels.get(i).getItemName().equals(grnWithPurOrderModel.getItemName())){
                             grnWithPurOrderModels.get(i).setQty(
                                     grnWithPurOrderModels.get(i).getQty()+0
                             );

                             isFound=true;
                         }
                     }
                 }else{
                     isFound = false;
                 }
                 if (!isFound){
                     updateListModel(grnWithPurOrderModel);
                 }
                 if (grnWithPurOrderModels!=null){

                     grnWithPurchaseNoAdapter = new GRNWithPurchaseNoAdapter(grnWithPurOrderModels,this,getContext());
                   recyclerView.setAdapter(grnWithPurchaseNoAdapter);
                   recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                 }
    }
//    ------------------------------------------------------------------------------------------------------------
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
    void updateListModel(GrnWithPurOrderModel grnWithPurOrderModel){

        GrnWithPurOrderModel grnWithPurOrderModel1 = new GrnWithPurOrderModel();
        grnWithPurOrderModel1.setDocDt(grnWithPurOrderModel.getDocDt());
        grnWithPurOrderModel1.setItemId(grnWithPurOrderModel.getItemId());
        grnWithPurOrderModel1.setQty(grnWithPurOrderModel.getQty());
        grnWithPurOrderModel1.setBalqty(grnWithPurOrderModel.getBalqty());
        grnWithPurOrderModel1.setPurrate(grnWithPurOrderModel.getPurrate());
        grnWithPurOrderModel1.setAmount(grnWithPurOrderModel.getAmount());
        grnWithPurOrderModel1.setTaxAmt(grnWithPurOrderModel.getTaxAmt());
        grnWithPurOrderModel1.setNetamt(grnWithPurOrderModel.getNetamt());
        grnWithPurOrderModel1.setItemName(grnWithPurOrderModel.getItemName());
        grnWithPurOrderModel1.setSuplID(grnWithPurOrderModel.getItemId());

        listGrnWithPurOrderModel.add(grnWithPurOrderModel);

    }
          @Override
          public void onClick(View v) {

           }
                @Override
                public void onQuantityChange(int position) {
                    saveGrnQtyChangeDialog=new SaveGrnQtyChangeDialog(
                            getContext(),
                            listGrnWithPurOrderModel.get(position),
                            this,
                            position
                    );
                    saveGrnQtyChangeDialog.show();
                }
                @Override
                public void onQuantityRemove(int position) {
                    if(listGrnWithPurOrderModel.size()>0){
                        listGrnWithPurOrderModel.remove(position);
                        grnWithPurchaseNoAdapter.notifyDataSetChanged();
                    }
                }


                @Override
                public void onStockItemChange(GrnWithPurOrderModel grnWithPurOrderModel, int position) {
                    saveGrnQtyChangeDialog.dismiss();;
                    if (listGrnWithPurOrderModel.size()>0){
                        listGrnWithPurOrderModel.get(position).setQty(grnWithPurOrderModel.getBalqty());
                        listGrnWithPurOrderModel.get(position).setAmount(grnWithPurOrderModel.getAmount());
                        listGrnWithPurOrderModel.get(position).setNetamt(grnWithPurOrderModel.getNetamt());
                        listGrnWithPurOrderModel.get(position).setTaxAmt(grnWithPurOrderModel.getTaxAmt());
                        grnWithPurchaseNoAdapter.notifyDataSetChanged();
                    }
                }


           void createGrnAgainstPONumber(View view){
               RequestGRNAginstPurOrder requestGRNAginstPurOrder = new RequestGRNAginstPurOrder();
               ArrayList<RequestGRNAginstPurOrderDetail> details = new ArrayList<>();
               PONumberListModel poNumberListModel = AppPreference.getPurchaseNumberPreferences(getContext());
               WarehouseModel warehouseBusinessLocation =AppPreference.getBusinessLocationDataPreferences(getContext());
               UserModel userModel= AppPreference.getLoginDataPreferences(getContext());
               requestGRNAginstPurOrder.setCoBrId(userModel.getCobrId());
               requestGRNAginstPurOrder.setCoBrId(warehouseBusinessLocation.getCobrId());
               requestGRNAginstPurOrder.setUserId(userModel.getUserId());
               requestGRNAginstPurOrder.setSuplID(Integer.parseInt(listGrnWithPurOrderModel.get(0).getSuplID()));
               requestGRNAginstPurOrder.setMachName(Build.PRODUCT);
               requestGRNAginstPurOrder.setDockey(poNumberListModel.getDocKey());

               RequestGRNAginstPurOrderDetail detail;
               if (listGrnWithPurOrderModel!=null){
                   if (listGrnWithPurOrderModel.size()>0){
                       for (int i=0; i<listGrnWithPurOrderModel.size(); i++){
                           detail = new RequestGRNAginstPurOrderDetail();
                           GrnWithPurOrderModel grnWithPurOrderModel = listGrnWithPurOrderModel.get(i);
                           detail.setItemId(grnWithPurOrderModel.getItemId());
                           detail.setTotQty(grnWithPurOrderModel.getBalqty());
                           details.add(detail);
                       }
                       requestGRNAginstPurOrder.setDetail(details);
                       sendGrnAgainstPOServer(requestGRNAginstPurOrder);
                   }
               }
            }


    void sendGrnAgainstPOServer(RequestGRNAginstPurOrder requestGRNAginstPurOrder){
        buttonSaveGrnAgainstPo.setEnabled(true);
        progressDialog = createProgressDialog(getContext());
        Gson gson =new Gson();
        JsonObject data=gson.fromJson(gson.toJson(requestGRNAginstPurOrder),JsonObject.class);

        AppSetting appSetting = AppPreference.getSettingDataPreferences(getContext());
        Call<RequestGRNAginstPurOrder> aginstPurOrderCall = WebServiceClient
                .setSaveGrnAgainstPO(appSetting.getSettingServerURL())
                .setRequestGRNAgainstPurOrder(data);
        aginstPurOrderCall.enqueue(new Callback<RequestGRNAginstPurOrder>() {
            @Override
            public void onResponse(Call<RequestGRNAginstPurOrder> call, Response<RequestGRNAginstPurOrder> response) {
                if(response.isSuccessful()){
                    Log.i("data response",response.body().getMsg());
                    Toast.makeText(getContext(),response.body().getMsg(),Toast.LENGTH_LONG).show();
                    listGrnWithPurOrderModel.clear();
                    grnWithPurchaseNoAdapter.notifyDataSetChanged();
                    buttonSaveGrnAgainstPo.setEnabled(false);
                    progressDialog.dismiss();

                } else{
                    Toast.makeText(getContext(),"Stock Not Saved Successfully",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<RequestGRNAginstPurOrder> call, Throwable t) {

            }
        });
    }
}
