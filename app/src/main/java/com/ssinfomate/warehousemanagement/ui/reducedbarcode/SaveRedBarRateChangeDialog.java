package com.ssinfomate.warehousemanagement.ui.reducedbarcode;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import org.jetbrains.annotations.NotNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.check_stock.PriceListModel;
import com.ssinfomate.warehousemanagement.webservices.login.UserModel;
import com.ssinfomate.warehousemanagement.utils.AppPreference;
import java.text.DecimalFormat;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class SaveRedBarRateChangeDialog extends Dialog {

    PriceListModel checkStockModels;
    IUpdateRateQuantity iUpdateRateQuantity;

    int position;
    int percentage=0;

    Double salerate = 0d;
    Double saleratebeforetax =0d;

    Double newsalerate=0d;
    Double newsaelratebeforetax=0d;

    Double calnewsrbt=0d;
    Double calnsr= 0d;

    int sticker=0;

    AppCompatEditText editTextPercentageRate;
    AppCompatEditText editTextNoOfStickers;
    AppCompatTextView textViewMaximumPer;
    AppCompatButton buttonUpdate;
    AppCompatButton buttonCancel;

    public SaveRedBarRateChangeDialog(@NonNull @NotNull Context context,
                                      PriceListModel checkStockModels,
                                      IUpdateRateQuantity iUpdateRateQuantity, int position) {
        super(context);
        this.checkStockModels = checkStockModels;
        this.iUpdateRateQuantity = iUpdateRateQuantity;
        this.position = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UserModel userModel = AppPreference.getLoginDataPreferences(getContext());
         setContentView(R.layout.dialog_update_reduced_barcode_rate);
         salerate = Double.parseDouble(checkStockModels.getSaleRate());
         saleratebeforetax = Double.parseDouble(checkStockModels.getSaleRateBeforeTax());
         editTextPercentageRate = findViewById(R.id.dialog_percentage_rate);
         editTextNoOfStickers = findViewById(R.id.dialog_reduced_barcode_no_of_sticker);
         textViewMaximumPer = findViewById(R.id.text_maximum_per);
         textViewMaximumPer.setText(userModel.getMaxReducedPerc()+" "+"%");

         buttonUpdate = findViewById(R.id.dialog_reduced_barcode_button_update);
         buttonUpdate.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(!TextUtils.isEmpty(editTextPercentageRate.getText()) &&
                         !TextUtils.isEmpty(editTextNoOfStickers.getText())
                 ){
                     onClickUpdateUIRate();
                 }else{
                     Toast.makeText(getContext(), "Enter all field", Toast.LENGTH_SHORT).show();
                 }
             }
         });
         buttonCancel = findViewById(R.id.dialog_reduced_barcode_button_cancel);
         buttonCancel.setOnClickListener(this::onClickedCancel);
    }
    private static DecimalFormat df = new DecimalFormat("0.00");

    public void onClickUpdateUIRate(){
        UserModel userModel = AppPreference.getLoginDataPreferences(getContext());
        newsalerate=Double.parseDouble(checkStockModels.getSaleRate());
        newsaelratebeforetax = Double.parseDouble(checkStockModels.getSaleRateBeforeTax());
        int max= (int)Double.parseDouble(userModel.getMaxReducedPerc());
        int totalClQty = Integer.parseInt(checkStockModels.getAllowNegStk());

        Double minusClQty =Double.parseDouble(checkStockModels.getClqty());
        percentage=Integer.parseInt(editTextPercentageRate.getText().toString());

        sticker = Integer.parseInt(editTextNoOfStickers.getText().toString());

        calnewsrbt = ((saleratebeforetax * percentage) /100);
        newsaelratebeforetax= saleratebeforetax-calnewsrbt;

        calnsr =((salerate * percentage) / 100);
        newsalerate= salerate-calnsr;

        checkStockModels.setNewsalerate( String.valueOf(df.format(newsalerate)));
        checkStockModels.setNewsaleratebeforetax( String.valueOf(df.format(newsaelratebeforetax)));

        if (totalClQty==1) {
            if (sticker>0) {
                checkStockModels.setNoofstrickertoprint(String.valueOf(sticker));
            }else {
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("Quantity Should be proper !").show();
                return;
            }
        }else{
            if (minusClQty>=sticker) {
                if (sticker>0) {
                    checkStockModels.setNoofstrickertoprint(String.valueOf(sticker));
                }else {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("Quantity Should be proper !").show();
                    return;
                }
            }else {
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("No. of sticker print cannot be greater than Total Qty.!").show();
                return;
            }
        }
        if (percentage<=max){
            if (percentage>0) {
                checkStockModels.setRateper(String.valueOf(percentage));
            }else {
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("Percentage can not be 0 !").show();
                return;
            }
        }
        else {
            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("Please enter percentage less than or equal to !"+max).show();
            return;
        }
        iUpdateRateQuantity.onUpdateRateChange(checkStockModels,position);
    }
    void onClickedCancel(View view){
        this.dismiss();
    }
}
