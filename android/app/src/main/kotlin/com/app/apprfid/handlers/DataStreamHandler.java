package com.app.apprfid.handlers;


import java.util.List;
import java.util.Map;

public class DataStreamHandler extends BaseStreamHandler {

    public void sendData(String data) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if (DataStreamHandler.super.sink != null)
                    DataStreamHandler.super.sink.success(data);
            }
        });

    }

    public void sendMapData(Map<String, Object> data) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if (DataStreamHandler.super.sink != null)
                    DataStreamHandler.super.sink.success(data);
            }
        });

    }

    public void sendListMapData(List<Map<String, Object>> data) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if (DataStreamHandler.super.sink != null) {
                    DataStreamHandler.super.sink.success(data);
                } else {
                    System.out.println(DataStreamHandler.super.sink);
                }

            }
        });

    }


}
