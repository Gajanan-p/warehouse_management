package com.ssinfomate.warehousemanagement.ui.setting;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.ui.dialog.DialogListener;
import com.ssinfomate.warehousemanagement.utils.AppPreference;
import com.ssinfomate.warehousemanagement.utils.AppSetting;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingFragment extends Fragment implements DialogListener, View.OnClickListener {

    ZebraPrinterDialog zebraPrinterDialog;
    LinearLayoutCompat linearLayoutCompatSelectZebraPrinter;
    AppCompatTextView appCompatTextViewZebraPrinterMACAddress;
    LinearLayoutCompat linearLayoutCompatSelectServerUrl;
    AppCompatTextView appCompatTextViewServerUrl;
    AppCompatTextView textViewVersion;
    AppCompatTextView textViewDeviceId;
    AppCompatTextView textViewLicenseKey;
    AppCompatTextView textViewContactUs;
    AppCompatTextView textViewUserAgreement;
    AppSetting appSetting;
    private ProgressDialog progressDialog;
    private UserAgreementDialog userAgreementDialog;
    JSONObject jsonObject=new JSONObject();
    static String TAG_SERVER_URL="TAG_SERVER_URL";
    static String TAG_PRINTER_MAC="TAG_PRINTER_MAC";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        textViewVersion = root.findViewById(R.id.text_setting_version);
        textViewVersion.setText("3.2.1(GP)");
        textViewDeviceId = root.findViewById(R.id.text_setting_device_id);
        String id = Settings.Secure.getString(getActivity().getContentResolver(),Settings.Secure.ANDROID_ID);
        textViewDeviceId.setText(id);
        textViewLicenseKey = root.findViewById(R.id.text_setting_license_key);
        textViewUserAgreement = root.findViewById(R.id.text_setting_user_agreement);
        textViewUserAgreement.setOnClickListener(this);
        textViewContactUs = root.findViewById(R.id.text_setting_contact_us);
        textViewContactUs.setClickable(true);
        textViewContactUs.setMovementMethod(LinkMovementMethod.getInstance());
        String string  = "<a href='https://www.vrssoftwares.com/contact-us/'> www.vrssoftwares.com</a>";
        textViewContactUs.setText(Html.fromHtml(string));

        linearLayoutCompatSelectZebraPrinter=root.findViewById(R.id.setting_select_printer_mac_address);
        linearLayoutCompatSelectZebraPrinter.setOnClickListener(this);

        appCompatTextViewZebraPrinterMACAddress=root.findViewById(R.id.setting_printer_mac_address);

        linearLayoutCompatSelectServerUrl=root.findViewById(R.id.setting_select_server_url);
        linearLayoutCompatSelectServerUrl.setOnClickListener(this);

        appCompatTextViewServerUrl=root.findViewById(R.id.setting_server_url);
        appSetting=AppPreference.getSettingDataPreferences(getContext());
        if(appSetting==null){
            appSetting =new AppSetting();
        }else{
            appCompatTextViewZebraPrinterMACAddress.setText(appSetting.getSettingPrinterMACAdd());
            appCompatTextViewServerUrl.setText(appSetting.getSettingServerURL());
        }
        return root;
    }

    @Override
    public void initDialog(JSONObject jsonObject) {
        zebraPrinterDialog =new ZebraPrinterDialog(getContext(),this,jsonObject);
        zebraPrinterDialog.show();
    }

    @Override
    public void onCancelClicked() {
        dismissDialog();
    }

    @Override
    public void onOkClicked(String s) {
        try {
            jsonObject=new JSONObject(s);
            if(TAG_PRINTER_MAC.equals(jsonObject.getString("type"))){
                appSetting.setSettingPrinterMACAdd(jsonObject.getString("value"));
                appCompatTextViewZebraPrinterMACAddress.setText(appSetting.getSettingPrinterMACAdd());
                AppPreference.setSettingDataPreferences(getContext(),appSetting);
            }
            if(TAG_SERVER_URL.equals(jsonObject.getString("type"))){
                appSetting.setSettingServerURL(jsonObject.getString("value"));
                appCompatTextViewServerUrl.setText(appSetting.getSettingServerURL());
                AppPreference.setSettingDataPreferences(getContext(),appSetting);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dismissDialog();
    }

    @Override
    public void dismissDialog() {
        zebraPrinterDialog.dismiss();
       // userAgreementDialog.dismiss();
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.setting_select_printer_mac_address){
            try {
                jsonObject.put("type",TAG_PRINTER_MAC);
                jsonObject.put("title","Enter Printer MAC Address");
                initDialog(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        };

        if(v.getId()==R.id.setting_select_server_url){
            AppPreference.clearSettingDataPreferences(getContext());
            try {
                jsonObject.put("type",TAG_SERVER_URL);
                jsonObject.put("title","Enter Server URL");
                initDialog(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        };
        if (v.getId()==R.id.text_setting_user_agreement){
            openUserAgreementDialog();
        }
    }

    private void openUserAgreementDialog() {
        userAgreementDialog = new UserAgreementDialog();
        userAgreementDialog.show(getActivity().getSupportFragmentManager(), "User Agreement");
    }


}