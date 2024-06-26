package com.ssinfomate.warehousemanagement.ui.util;


import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.printer.ZebraPrinter;

public interface ListenerZebraPrinter {
    void initZPConnection(String BLUETOOTH_MAC_ADDRESS);
    void onZPConnection(Connection connection);
    void initZebraPrinterConnection(Connection connection);
    void onZebraPrinterConnection(ZebraPrinter zebraPrinter);


}
