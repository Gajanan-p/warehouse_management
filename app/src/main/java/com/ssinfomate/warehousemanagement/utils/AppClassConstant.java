package com.ssinfomate.warehousemanagement.utils;

public class AppClassConstant {
    public static boolean classPresent() {
        try {
            ClassLoader.getSystemClassLoader().loadClass("com.symbol.emdk.EMDKManager$EMDKListener");
            return true;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }
}
