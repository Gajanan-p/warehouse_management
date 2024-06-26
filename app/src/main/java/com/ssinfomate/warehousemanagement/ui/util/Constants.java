package com.ssinfomate.warehousemanagement.ui.util;

import com.ssinfomate.warehousemanagement.webservices.stock_correction.AdjustmentTypeModel;
import com.ssinfomate.warehousemanagement.webservices.warehouse.ToWarehouse;
import com.ssinfomate.warehousemanagement.webservices.warehouse.WarehouseModel;

public class Constants {
//    public static final   String MAC_ADDRESS= "54:6C:0E:01:BB:E8";
public static final String PRINTER_SIZES_SMALL="Print Price Tag 76mm*37.5mm For Small Sel";
    public static final String PRINTER_SIZES_LARGE="Print Price Tag 176mm*75mm For Big Sel";
    public  static final String PRINTER_SIZE_LARGE_OFFER="Print Price Tag 176mm*75mm For Big Offer Sel";
    public  static final String PRINTER_SIZE_GARDENING_SEL="Print Price Tag 220mm*95mm For Gardening sel";
    public static final String PRINTER_SIZES_SMALL_WAS_NOW="Print Price Tag 76mm*37.5mm For Was Now Small Sel";
    public static final String PRINTER_SIZES_LARGE_WAS_NOW="Print Price Tag 176mm*75mm For Was Now Big Sel";

    public static final String BUSINESS_LOCATION="BUSINESS_LOCATION";
    public static final String FROM_WAREHOUSE="FROM_WAREHOUSE";
    public static final String TO_WAREHOUSE="TO_WAREHOUSE";
    public static final String AdjustmentType="AdjustmentType";
    public static final String HOST_URL = "";


    public static WarehouseModel warehouseModelBusinessLocation;
    public static WarehouseModel warehouseModelFromLocation;
    public static ToWarehouse warehouseModelToLocation;
    public static AdjustmentTypeModel adjustmentTypeModel;


}
