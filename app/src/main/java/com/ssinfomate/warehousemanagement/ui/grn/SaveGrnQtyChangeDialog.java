package com.ssinfomate.warehousemanagement.ui.grn;

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
import com.ssinfomate.warehousemanagement.webservices.grn.GrnWithPurOrderModel;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SaveGrnQtyChangeDialog extends Dialog {
    GrnWithPurOrderModel grnWithPurOrderModel;
    IOnSaveGrnQty iOnSaveGrnQty;
    int position = 0;
    Double quantity =0d;
    Double calPOAmount=0d;
    Double calPoRate=0d;
    Double clQty = 0d;
    Double taxAmt = 0d;
    Double netAmt = 0d;
    Double totalTax = 0d;
    AppCompatTextView appCompatTextViewItemName;
    AppCompatEditText appCompatEditText;
    AppCompatButton appCompatButtonUpdate;
    AppCompatButton appCompatButtonCancel;
    AppCompatButton appCompatButtonPlus;
    AppCompatButton appCompatButtonMinus;

    public SaveGrnQtyChangeDialog(@NonNull @NotNull Context context,
                                  GrnWithPurOrderModel grnWithPurOrderModel,
                                  IOnSaveGrnQty iOnSaveGrnQty,
                                  int position) {
        super(context);
        this.grnWithPurOrderModel = grnWithPurOrderModel;
        this.iOnSaveGrnQty = iOnSaveGrnQty;
        this.position = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_save_stock_quantity);
        if(grnWithPurOrderModel.getQty()!=null){
         quantity =Double.parseDouble(grnWithPurOrderModel.getBalqty());
        }

        appCompatEditText=findViewById(R.id.dialog_item_quantity);

        appCompatButtonUpdate=findViewById(R.id.dialog_button_update);
        appCompatButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(appCompatEditText.getText())){
                    onClickedUpdate();

                }else{
                    new SweetAlertDialog(getContext(),SweetAlertDialog.WARNING_TYPE).setTitleText("Scan Quantity cannot be greater than Purchase Qty.!").show();
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
        appCompatTextViewItemName.setText(grnWithPurOrderModel.getItemName());
        clQty = Double.parseDouble(grnWithPurOrderModel.getQty());
        updateUIQuantity(quantity);

    }

    void updateUIQuantity(Double quantity){
        appCompatEditText.setText(String.valueOf(quantity));
    }

    void onClickedPlus(View view){
        quantity=Double.parseDouble(appCompatEditText.getText().toString());
        quantity=quantity + 1;
        if (clQty>=quantity) {
            updateUIQuantity(quantity);
        }else{
            new SweetAlertDialog(getContext(),SweetAlertDialog.WARNING_TYPE).setTitleText("Scan Quantity cannot be greater than Purchase Qty.!").show();
            return;
        }


    }
    void onClickedMinus(View view){
            quantity =Double.parseDouble( appCompatEditText.getText().toString());
            quantity = quantity - 1;
            if (quantity>0) {
                updateUIQuantity(quantity);
            }else {
                new SweetAlertDialog(getContext(),SweetAlertDialog.WARNING_TYPE).setTitleText("Quantity Should be proper .!").show();
                return;
            }
    }
    private static DecimalFormat df = new DecimalFormat("0.00");

    void onClickedUpdate(){
        quantity=Double.parseDouble(appCompatEditText.getText().toString());
        calPoRate = Double.parseDouble(grnWithPurOrderModel.getPurrate());
        taxAmt = Double.parseDouble(grnWithPurOrderModel.getTaxperc());
        calPOAmount = calPoRate * quantity;
        totalTax = calPOAmount*taxAmt/100;
       // totalTax = calPOAmount+taxAmt;
        netAmt = calPOAmount+totalTax;
            if (clQty >= quantity) {
                if (quantity > 0) {
                    grnWithPurOrderModel.setBalqty(String.valueOf(quantity));
                } else {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("Quantity Should be proper .!").show();
                    return;
                }
            } else {
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("Scan Quantity cannot be greater than Purchase Qty.!").show();
                return;
            }
            grnWithPurOrderModel.setTaxAmt(String.valueOf(df.format(totalTax)));
            grnWithPurOrderModel.setAmount(String.valueOf(df.format(calPOAmount)));
            grnWithPurOrderModel.setNetamt(String.valueOf(df.format(netAmt)));
            iOnSaveGrnQty.onStockItemChange(grnWithPurOrderModel,position);
    }
    void onClickedCancel(View view){
        this.dismiss();
    }

}
