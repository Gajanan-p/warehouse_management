package com.ssinfomate.warehousemanagement.ui.dialog;

import org.json.JSONObject;

public interface DialogListener {
    void initDialog(JSONObject jsonObject);
    void onCancelClicked();
    void onOkClicked(String s);
    void dismissDialog();
}
