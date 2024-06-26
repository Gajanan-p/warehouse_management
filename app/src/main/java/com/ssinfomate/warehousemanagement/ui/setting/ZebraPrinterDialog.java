package com.ssinfomate.warehousemanagement.ui.setting;

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
import com.ssinfomate.warehousemanagement.ui.dialog.DialogListener;

import org.json.JSONException;
import org.json.JSONObject;

public class ZebraPrinterDialog extends Dialog implements View.OnClickListener {
    AppCompatEditText appCompatEditTextPrinterMACAddress;
    AppCompatTextView appCompatTextViewTitle;
    AppCompatButton appCompatButtonCancel;
    AppCompatButton appCompatButtonOk;
    DialogListener dialogListener;
    JSONObject jsonObject;
    public ZebraPrinterDialog(@NonNull Context context,DialogListener dialogListener,JSONObject jsonObject) {
        super(context);
        this.dialogListener=dialogListener;
        this.jsonObject=jsonObject;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_zebra_printer_address);
        appCompatEditTextPrinterMACAddress=findViewById(R.id.dialog_edit_zebra_printer_mac_address);
        appCompatTextViewTitle=findViewById(R.id.dialog_text_title);
        try {
            appCompatTextViewTitle.setText(jsonObject.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        appCompatButtonCancel=findViewById(R.id.dialog_button_zebra_printer_cancel);
        appCompatButtonCancel.setOnClickListener(this);
        appCompatButtonOk=findViewById(R.id.dialog_button_zebra_printer_ok);
        appCompatButtonOk.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.dialog_button_zebra_printer_ok){
            onOkClicked();
        }
        if(v.getId()==R.id.dialog_button_zebra_printer_cancel){
            dialogListener.onCancelClicked();
        }

    }
    void onOkClicked(){
        if(!TextUtils.isEmpty(appCompatEditTextPrinterMACAddress.getText())){
            try {
                jsonObject.put("value",appCompatEditTextPrinterMACAddress.getText().toString());
                dialogListener.onOkClicked(jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
