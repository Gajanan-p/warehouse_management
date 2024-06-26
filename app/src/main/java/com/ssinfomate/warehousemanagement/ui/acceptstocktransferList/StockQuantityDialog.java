package com.ssinfomate.warehousemanagement.ui.acceptstocktransferList;

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


public class StockQuantityDialog extends Dialog {
    AcceptStockTransferListModel acceptStockTransferListModel;
    IAcceptStock iAcceptStock;
    int position=0;
    int quantity=0;

    AppCompatTextView appCompatTextViewItemName;
    AppCompatEditText appCompatEditText;
    AppCompatButton appCompatButtonUpdate;
    AppCompatButton appCompatButtonCancel;
    AppCompatButton appCompatButtonPlus;
    AppCompatButton appCompatButtonMinus;

    public StockQuantityDialog(@NonNull Context context,
                               AcceptStockTransferListModel acceptStockTransferListModel,
                               IAcceptStock iAcceptStock,
                               int position
                           ) {
        super(context);
         this.acceptStockTransferListModel=acceptStockTransferListModel;
        this.iAcceptStock=iAcceptStock;
        this.position=position;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_save_stock_quantity);
        if(acceptStockTransferListModel.getScanQty()!=null){
        quantity=Math.round(Math.round(Float.parseFloat(acceptStockTransferListModel.getScanQty())));
        }

        appCompatEditText=findViewById(R.id.dialog_item_quantity);
        appCompatButtonUpdate=findViewById(R.id.dialog_button_update);
        appCompatButtonUpdate.setOnClickListener(this::onClickedUpdate);

        appCompatButtonCancel=findViewById(R.id.dialog_button_cancel);
        appCompatButtonCancel.setOnClickListener(this::onClickedCancel);

        appCompatButtonPlus=findViewById(R.id.dialog_button_plus);
        appCompatButtonPlus.setOnClickListener(this::onClickedPlus);

        appCompatButtonMinus=findViewById(R.id.dialog_button_minus);
        appCompatButtonMinus.setOnClickListener(this::onClickedMinus);

        appCompatTextViewItemName=findViewById(R.id.dialog_item_name);
        appCompatTextViewItemName.setText(acceptStockTransferListModel.getItemName());

        updateUIQuantity(quantity);

    }

    void updateUIQuantity(int quantity){
        appCompatEditText.setText(String.valueOf(quantity));
    }

    void onClickedPlus(View view){
        if(!TextUtils.isEmpty(appCompatEditText.getText())){
            quantity=Integer.parseInt(appCompatEditText.getText().toString());
            quantity=quantity+1;
            updateUIQuantity(quantity);
        }
    }

    void onClickedMinus(View view){
        if(!TextUtils.isEmpty(appCompatEditText.getText())){
            quantity=Integer.parseInt(appCompatEditText.getText().toString());
            quantity=quantity-1;
            updateUIQuantity(quantity);
        }
    }

    void onClickedUpdate(View view){
        quantity=Integer.parseInt(appCompatEditText.getText().toString());
        iAcceptStock.onStockItemQuantityUpdateClicked(position,quantity);

    }

    void onClickedCancel(View view){
        this.dismiss();
    }
}
