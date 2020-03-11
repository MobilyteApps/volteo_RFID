package com.app.apprfid.handlers;


public class RSSIStreamHandler extends BaseStreamHandler {

    public void sendRSSI(double rssi) {

        System.out.println("stream in" + rssi);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if (RSSIStreamHandler.super.sink != null) {
                    RSSIStreamHandler.super.sink.success(rssi);
                } else {
                    System.out.println("RSSIStreamHandler is null");
                }

            }
        });

    }
}
