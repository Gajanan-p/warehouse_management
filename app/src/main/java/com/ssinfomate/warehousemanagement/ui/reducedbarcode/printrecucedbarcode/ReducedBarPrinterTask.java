package com.ssinfomate.warehousemanagement.ui.reducedbarcode.printrecucedbarcode;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.ui.util.ListenerZebraPrinter;
import com.ssinfomate.warehousemanagement.webservices.reducedbarcode.SaveReducedBarcodeModel;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.PrinterStatus;
import com.zebra.sdk.printer.SGD;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReducedBarPrinterTask {

    //Connect to zebra Printer
    static String TAG="Zebra Printer";
    static ListenerZebraPrinter listenerZebraPrinter=null;
    //    public ZebraPrinterTask(){}
    public ReducedBarPrinterTask(ListenerZebraPrinter listenerZebraPrinter,
                            String printerMacAddress){
        this.listenerZebraPrinter=listenerZebraPrinter;
        //initializeZPConnection(printerMacAddress);
    }

    public void initializeZPConnection(String PRINTER_MAC_ADDRESS){
        Connection connection=null;
        if(!TextUtils.isEmpty(PRINTER_MAC_ADDRESS)){
            connection=new BluetoothConnection(PRINTER_MAC_ADDRESS);
            try {
                connection.open();
                if(listenerZebraPrinter!=null){
                    listenerZebraPrinter.onZPConnection(connection);
                }
                Log.i(TAG,"Printer Connected");
            } catch (ConnectionException e) {
                Log.i(TAG,"Printer Not Connected");
                e.printStackTrace();
            }
        }
    }

    public void initializeZebraPrinterConnection(Connection connection){
        ZebraPrinter zebraPrinter=null;
        if(connection.isConnected()){
            try {
                zebraPrinter= ZebraPrinterFactory.getInstance(connection);
                Log.i(TAG,"Checking Printer language");
                Log.i(TAG, SGD.GET("device.languages",connection));
                listenerZebraPrinter.onZebraPrinterConnection(zebraPrinter);
//                    Perform Printer Task here
//                sendPrint(zebraPrinter,mConnection);
            } catch (ConnectionException e) {
                Log.i(TAG, "Zebra Printer Not Connected");
                e.printStackTrace();

            } catch (ZebraPrinterLanguageUnknownException e) {
                Log.i(TAG, "Unknown Printer Languages");
                e.printStackTrace();
            }
        }

    }

    // Sees if the printer is ready to print
    public  Boolean checkPrinterStatus(ZebraPrinter printer) {
        Log.i(TAG, "Check Printer Status");
        try {
            if(printer!=null){
                PrinterStatus printerStatus = printer.getCurrentStatus();
                if (printerStatus.isReadyToPrint) {
                    return true;
                }
            }

        } catch (ConnectionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public  void showPrinterStatus(Context context, ZebraPrinter printer) {
        Log.i(TAG, "Checking Printer Status");

        try {
            PrinterStatus printerStatus = printer.getCurrentStatus();
            if (printerStatus.isReadyToPrint) {
                Log.i(TAG, context.getString(R.string.ready_to_print));
            } else if (printerStatus.isPaused) {
                Log.i(TAG, context.getString(R.string.print_failed) + " " + context.getString(R.string.printer_paused));
            } else if (printerStatus.isHeadOpen) {
                Log.i(TAG, context.getString(R.string.print_failed) + " " + context.getString(R.string.head_open));
            } else if (printerStatus.isPaperOut) {
                Log.i(TAG, context.getString(R.string.print_failed) + " " + context.getString(R.string.paper_out));
            } else {
                Log.i(TAG, context.getString(R.string.print_failed) + " " + context.getString(R.string.cannot_print));
            }
        } catch (ConnectionException e) {
            Log.i(TAG, context.getString(R.string.print_failed) + " " + context.getString(R.string.no_printer));
            e.printStackTrace();
        }
    }

    // Takes the size of the pdf and the printer's maximum size and scales the file down
    public  String scalePrint (Connection connection) throws ConnectionException {
        int fileWidth = 0;
        String scale = "dither scale-to-fit";

        if (fileWidth != 0) {
            String printerModel = SGD.GET("device.host_identification",connection).substring(0,5);
            double scaleFactor;

            if (printerModel.equals("iMZ22")||printerModel.equals("QLn22")||printerModel.equals("ZD410")) {
                scaleFactor = 2.0/fileWidth*100;
            } else if (printerModel.equals("iMZ32")||printerModel.equals("QLn32")||printerModel.equals("ZQ510")) {
//                scaleFactor = 3.0/fileWidth*100;
                scaleFactor = 3.0/120;
            } else if (printerModel.equals("QLn42")||printerModel.equals("ZQ520")||
                    printerModel.equals("ZD420")||printerModel.equals("ZD500")||
                    printerModel.equals("ZT220")||printerModel.equals("ZT230")||
                    printerModel.equals("ZT410")) {
                scaleFactor = 4.0/fileWidth*100;
            } else if (printerModel.equals("ZT420")) {
                scaleFactor = 6.5/fileWidth*100;
            } else {
                scaleFactor = 100;
            }

            scale = "dither scale=" + (int) scaleFactor + "x" + (int) scaleFactor;
        }

        return scale;
    }

    //------------- Small Barcode Size Print----------------------------------------------
    public  String getPrintVerticalZPLLabel(SaveReducedBarcodeModel saveReducedBarcodeModel) {
        String pattern = "dd|MM|yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(new Date());
        System.out.println(date);

        String configLabel = null;
//        try {
//            PrinterLanguage printerLanguage = zebraPrinter.getPrinterControlLanguage();
//            SGD.SET("device.languages", "zpl", connection);

//            if (printerLanguage == PrinterLanguage.ZPL) {
        if (saveReducedBarcodeModel!=null) {
//                02-25-190-00

// set height add ^PW400 "^POI^PW400^MNN^LL200^LH0,0" + "\r\n" +
// DRAW THE LINE---- "^FO150,205^GB65,0,3^FS"+
            configLabel =

           " ^XA" +
                   "^PO^PW600" +
                   "^00^MNN^LL310^LH0,0" + "\r\n" +
                   "^FO160,60^A0,50^FD REDUCED^FS"+

                   "^FO50,100^BY2^BCN,60,Y,N,N^A0,15^FD"+saveReducedBarcodeModel.getBarcode()+"^FS"+

                   "^CI0,21,36^FO90,200^A0,25^FD Was $"+saveReducedBarcodeModel.getOldsalerate()+"^FS"+

                   "^FO150,205^GB65,0,3^FS"+

                   "^CI0,21,36^FO100,230^A0,35^FD New $"+saveReducedBarcodeModel.getNewsalerate()+"^FS"+

                   "^FO130,280^ADN,28,12^FD "+saveReducedBarcodeModel.getItemName()+"^FS"+
           "^XZ";
        }
        StringBuilder configLabelTemp = new StringBuilder();
        for(int i=0;i<Integer.parseInt(saveReducedBarcodeModel.getNoOfstickerstoprint());i++){
            configLabelTemp.append(configLabel);
        }
        return configLabelTemp.toString();
    }

}
