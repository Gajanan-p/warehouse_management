package com.ssinfomate.warehousemanagement.utils;

import com.google.gson.annotations.SerializedName;

public class AppSetting {
    @SerializedName("setting_server_url")
    private String settingServerURL;
    @SerializedName("setting_printer_mac_add")
    private String settingPrinterMACAdd;

    public AppSetting() {}

    public AppSetting(String settingServerURL, String settingPrinterMACAdd) {
        this.settingServerURL = settingServerURL;
        this.settingPrinterMACAdd = settingPrinterMACAdd;
    }

    public String getSettingServerURL() {
        return settingServerURL;
    }

    public void setSettingServerURL(String settingServerURL) {
        this.settingServerURL = settingServerURL;
    }

    public String getSettingPrinterMACAdd() {
        return settingPrinterMACAdd;
    }

    public void setSettingPrinterMACAdd(String settingPrinterMACAdd) {
        this.settingPrinterMACAdd = settingPrinterMACAdd;
    }
}
