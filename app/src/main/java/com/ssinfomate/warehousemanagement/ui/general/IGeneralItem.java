package com.ssinfomate.warehousemanagement.ui.general;

import com.ssinfomate.warehousemanagement.webservices.general.ResponseGeneralList;

public interface IGeneralItem {
    void onGeneralItemUpdateClicked(ResponseGeneralList responseGeneralList);
    void onGeneralItemDeleteClicked(ResponseGeneralList responseGeneralList);
}
