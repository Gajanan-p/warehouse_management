package com.ssinfomate.warehousemanagement.ui.stockcorrection;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.utils.AppPreference;
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.SaveStockTransferModel;
import com.ssinfomate.warehousemanagement.ui.stocktransfer.IOnSaveStockQuantity;
import com.ssinfomate.warehousemanagement.webservices.stock_correction.AdjustmentTypeModel;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SaveStockCorrectionQuantityDialog extends Dialog {
    SaveStockTransferModel saveStockTransferModel;
    IOnSaveStockQuantity iOnSaveStockQuantity;
    int position=0;
    int quantity=0;
    String compareA="A";
    AppCompatTextView appCompatTextViewItemName;
    AppCompatEditText appCompatEditText;
    AppCompatButton appCompatButtonUpdate;
    AppCompatButton appCompatButtonCancel;
    AppCompatButton appCompatButtonPlus;
    AppCompatButton appCompatButtonMinus;

    public SaveStockCorrectionQuantityDialog(@NonNull Context context,
                                             SaveStockTransferModel saveStockTransferModel,
                                             IOnSaveStockQuantity iOnSaveStockQuantity,
                                             int position)
    {
        super(context);
        this.saveStockTransferModel=saveStockTransferModel;
        this.iOnSaveStockQuantity=iOnSaveStockQuantity;
        this.position=position;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_save_check_stock_correction_quantity);
        if(saveStockTransferModel.getScan_quantity()!=null){
            quantity=saveStockTransferModel.getScan_quantity();

        }

        appCompatEditText = findViewById(R.id.dialog_item_quantity);
        appCompatEditText.setText("-");

        appCompatButtonUpdate=findViewById(R.id.dialog_button_update);
        appCompatButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(appCompatEditText.getText())){
                    onClickedUpdate();
                }else {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("Quantity Should be proper !").show();
                    appCompatEditText.setText("1");
                    return;
                }
            }
        });

        appCompatButtonCancel=findViewById(R.id.dialog_button_cancel);
        appCompatButtonCancel.setOnClickListener(this::onClickedCancel);

        appCompatButtonPlus=findViewById(R.id.dialog_button_plus);
        appCompatButtonPlus.setOnClickListener(this::onClickedPlus);

        appCompatButtonMinus=findViewById(R.id.dialog_button_minus);
        appCompatButtonMinus.setOnClickListener(this::onClickedMinus);

        appCompatTextViewItemName=findViewById(R.id.dialog_item_name);
        appCompatTextViewItemName.setText(saveStockTransferModel.getItemName());

        updateUIQuantity(quantity);

    }

    void updateUIQuantity(int quantity){
        appCompatEditText.setText(String.valueOf(quantity));
    }

    void onClickedPlus(View view){
            quantity=Integer.parseInt(appCompatEditText.getText().toString());
            quantity=quantity+1;
            updateUIQuantity(quantity);
    }
    void onClickedMinus(View view){
            quantity=Integer.parseInt(appCompatEditText.getText().toString());
            quantity = quantity - 1;
            if (quantity>0) {
                updateUIQuantity(quantity);
            }else {
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("Quantity Should be proper !").show();
                return;
            }

    }
    void onClickedUpdate(){
        int clQtyPlus= (int)Integer.parseInt(saveStockTransferModel.getAllowNegStk());
        Double plusClQty = (double)Double.parseDouble(saveStockTransferModel.getClqty());
        quantity=Integer.parseInt(appCompatEditText.getText().toString());
        AdjustmentTypeModel adjModel = AppPreference.getAdjustmentTypeDataPreferences(getContext());
//        TODO Compare to Adjacement Stock--------------------------------------------------------------------
        if(compareA.equals(adjModel.getId())) {
            saveStockTransferModel.setScan_quantity(quantity);
        }else {
            if (clQtyPlus == 0) {
                if (plusClQty > 0) {
                    if (quantity>0) {
                        saveStockTransferModel.setScan_quantity(quantity);
                    }else {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("Quantity Should be proper !").show();
                        return;
                    }
                } else {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("Scan Quantity cannot be greater than System Qty.!").show();
                    return;
                }
            }else {
                saveStockTransferModel.setScan_quantity(quantity);
            }
        }
        iOnSaveStockQuantity.onStockItemChange(saveStockTransferModel,position);
    }
    void onClickedCancel(View view){
        this.dismiss();
    }
}
