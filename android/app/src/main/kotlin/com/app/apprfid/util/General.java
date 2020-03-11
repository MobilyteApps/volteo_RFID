package com.app.apprfid.util;

import io.flutter.BuildConfig;

public class General {

    public static void easyPrinter(String message, String className, String obj) {
        if (BuildConfig.DEBUG) {
            System.out.println("Easy_Printer---------------------Start-------------------------------");
            if (obj != null) {
                System.out.println("Easy_Printer-" + className + "----->" + message + "<---->" + obj);
            } else {
                System.out.println("Easy_Printer-" + className + "----->" + message);
            }
            System.out.println("Easy_Printer---------------------End---------------------------------");
        }
    }
}
