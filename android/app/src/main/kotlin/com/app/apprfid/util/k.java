package com.app.apprfid.util;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;


public abstract class k extends Handler {

    public k() {
    }

    public final void handleMessage(Message message) {
            handleMessage(message,"");

    }

    public abstract void handleMessage(Message message, String anything);
}
