package com.app.apprfid.handlers;

import java.util.concurrent.Executor;

import io.flutter.plugin.common.EventChannel;

public class BaseStreamHandler implements EventChannel.StreamHandler {
    public EventChannel.EventSink sink;
    public Executor executor = new MainThreadExecutor();

    @Override
    public void onListen(Object o, EventChannel.EventSink eventSink) {
        this.sink = eventSink;
    }

    @Override
    public void onCancel(Object o) {
        this.sink = null;
    }
}
