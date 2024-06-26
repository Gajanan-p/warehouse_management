package com.ssinfomate.warehousemanagement.ui.printpricetag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.check_stock.PriceListModel;
import org.jetbrains.annotations.NotNull;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PrintPriceAdapter extends RecyclerView.Adapter<PrintPriceAdapter.ViewHolder> {
    ArrayList<PriceListModel> listCheckStockModel;
    Context context;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    String date = simpleDateFormat.format(calendar.getTime());
    String a="1";
    String labelName;
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.row_print_price_tag_list,
                        parent,
                        false
                );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        PriceListModel checkStockModel=listCheckStockModel.get(position);
        double a1= Double.parseDouble(checkStockModel.getMrp());
        double b= Double.parseDouble(checkStockModel.getdRSRate());
        double deposit = Double.parseDouble(checkStockModel.getdRSRate());

        if (checkStockModel.getItemId()!=null) {
            holder.appCompatTextViewItemName.setText(checkStockModel.getItemName());
            holder.appCompatTextViewItemBarcode.setText(checkStockModel.getBarcode());
            holder.appCompatTextViewItemPrice.setText(checkStockModel.getMrp());
            holder.appCompatTextViewItemCode.setText(checkStockModel.getItemCode());
            holder.appCompatTextViewCaseSize.setText(checkStockModel.getCasesizeQty());
            holder.appCompatTextViewTagVatCode.setText(checkStockModel.getVatRate().toString());
            holder.appCompatTextViewTagDepartmentName.setText(checkStockModel.getItemsubgrpName());
            holder.appCompatTextViewTagDepartmentCode.setText(checkStockModel.getItemsubgrpId());
            holder.appCompatTextViewTagProduct.setText(" ");
            holder.appCompatTextViewTagCurrentDate.setText(date);
            if (labelName=="Print Price Tag 176mm*75mm For Was Now Big Sel" || labelName=="Print Price Tag 76mm*37.5mm For Was Now Small Sel"){
                holder.layoutCompatWasSaleRate.setVisibility(View.VISIBLE);
                holder.textViewWasSaleRate.setText(checkStockModel.getWasSaleRate());
                holder.textBoxViewNowSaleRate.setText("Now Sale Price");
            } else if (labelName=="Print Price Tag 176mm*75mm For Big Offer Sel") {
                holder.layoutCompatWasSaleRate.setVisibility(View.VISIBLE);
                holder.textViewWasPriceText.setText("Offer Name");
                holder.textViewWasSaleRate.setText(checkStockModel.getUdWeekImported());
            }
        }else {
            Toast.makeText(context,"Item not found",Toast.LENGTH_LONG).show();
        }
        if (a.equals(checkStockModel.getStatus())) {
            holder.appCompatTextViewProductStatus.setText("Available");
        } else {
            holder.appCompatTextViewProductStatus.setText("Not Available");
        }
        if (deposit>0.0){
            holder.layoutCompatDepositPrice.setVisibility(View.VISIBLE);
            holder.textViewDepositPrice.setText(checkStockModel.getdRSRate());
        }

    }

    @Override
    public int getItemCount() {
        return listCheckStockModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        AppCompatTextView appCompatTextViewItemName;
        AppCompatTextView appCompatTextViewItemBarcode;
        AppCompatTextView appCompatTextViewItemPrice;
        AppCompatTextView appCompatTextViewItemCode;
        AppCompatTextView appCompatTextViewCaseSize;
        AppCompatTextView appCompatTextViewTagVatCode;
        AppCompatTextView appCompatTextViewTagDepartmentName;
        AppCompatTextView appCompatTextViewTagDepartmentCode;
        AppCompatTextView appCompatTextViewTagProduct;
        AppCompatTextView appCompatTextViewProductStatus;
        AppCompatTextView appCompatTextViewTagCurrentDate;
        AppCompatTextView textViewWasSaleRate;
        LinearLayoutCompat layoutCompatWasSaleRate;
        LinearLayoutCompat layoutCompatDepositPrice;
        AppCompatTextView textBoxViewNowSaleRate;
        AppCompatTextView textViewWasPriceText;
        AppCompatTextView textViewDepositPrice;

//--------------------// TODO: ViewHolder()----------------------------------------------------------------------

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            appCompatTextViewItemName=itemView.findViewById(R.id.row_print_price_tag_item_name);
            appCompatTextViewItemBarcode=itemView.findViewById(R.id.row_print_price_tag_barcode);
            appCompatTextViewItemPrice=itemView.findViewById(R.id.row_print_price_tag_item_price);
            appCompatTextViewItemCode=itemView.findViewById(R.id.row_print_price_tag_item_code);
            appCompatTextViewCaseSize=itemView.findViewById(R.id.row_print_price_tag_case_size);
            appCompatTextViewTagVatCode=itemView.findViewById(R.id.row_print_price_tag_vat_code);
            appCompatTextViewTagDepartmentName=itemView.findViewById(R.id.row_print_price_tag_department_name);
            appCompatTextViewTagDepartmentCode=itemView.findViewById(R.id.row_print_price_tag_department_code);
            appCompatTextViewTagProduct=itemView.findViewById(R.id.row_print_price_tag_product_status);
            appCompatTextViewTagCurrentDate=itemView.findViewById(R.id.row_print_price_tag_current_date);
            appCompatTextViewProductStatus = itemView.findViewById(R.id.row_print_price_tag_product_status);
            textBoxViewNowSaleRate = itemView.findViewById(R.id.text_box_now_sale_rate);
            textViewWasSaleRate = itemView.findViewById(R.id.row_print_price_tag_was_item_price);
            layoutCompatWasSaleRate = itemView.findViewById(R.id.layout_was_price);
            layoutCompatDepositPrice = itemView.findViewById(R.id.layout_deposit);
            textViewDepositPrice = itemView.findViewById(R.id.row_print_deposit_price);
            textViewWasPriceText = itemView.findViewById(R.id.text_box_was_sale_rate);
        }
    }

    public PrintPriceAdapter(ArrayList<PriceListModel> listCheckStockModel,String labelName,
                             Context context)
    {
        this.listCheckStockModel = listCheckStockModel;
        this.context = context;
        this.labelName=labelName;
    }
}
