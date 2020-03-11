package com.app.apprfid.handlers;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.app.apprfid.FlutterVolteoPlugin;

import java.lang.ref.WeakReference;
import java.util.Map;

import io.flutter.plugin.common.PluginRegistry;

public class TSLChangeHandler extends Handler {

    private final WeakReference activity;
    private  final RSSIStreamHandler rssiStreamHandler;

    public TSLChangeHandler(PluginRegistry.Registrar activity,RSSIStreamHandler rssiStreamHandler) {
        this.activity = new WeakReference<>(activity);
        this.rssiStreamHandler=rssiStreamHandler;
    }

    @Override
    public void handleMessage(Message msg) {
        PluginRegistry.Registrar activity = (PluginRegistry.Registrar) this.activity.get();
        if (activity == null) {
           // Log.e(TAG, "Activity is null TSLChangeHandler.handleMessage()!");
            return;
        }
        try {
            switch (msg.what) {

                case 3:

                    return;
                case 100:
//                    FindFragment.this.mCurrentReading = (TransponderReading) message.obj;
//                    FindFragment.this.displayRssiValue();
//                    FindFragment.this.displayDebugValue(
//                            Integer.toString(FindFragment.this.mModel.getTranspondersReadInLastSecond()));
                    return;
                case 101:
//                    FindFragment.this.updateMultipleTargetView();
                    return;
                case 102:
//                    FindFragment.this.updateScanMessage();
//                    FindFragment.this.updateMultipleTargetView();
                    return;
                case 103:
//                    FlutterVolteoPlugin.mModel.setEnabled(false);
//                    FindFragment.this.mAllParametersValid = false;
//                    FindFragment.this.updateView();
//                    FindFragment.this.mTargetIdentifierEditText.setText(FindFragment.this.mModel.getBarcodeValue());
//                    FindFragment.this.updateModelEnabledState();
//                    FindFragment.this.updateView();
                    return;
                default:
                    return;



//                case ModelBase.BUSY_STATE_CHANGED_NOTIFICATION:
//
//                    break;
//
//                case ModelBase.MESSAGE_NOTIFICATION:
//                     String message = (String)msg.obj;
//
//
//                      if(message!=null && message.trim().startsWith("RSSI:")) {
//
//                          //System.out.println(""+Integer.parseInt(message.substring(4).trim()));
//                          rssiStreamHandler.sendRSSI(Integer.parseInt(message.trim().substring(5).trim()));
//
//                          // scrollBarcodeListViewToBottom();
//                      } else {
//                          System.out.println("---------------------");
//                          System.out.println(message);
//
//                          System.out.println("---------------------");
//                          // scrollResultsListViewToBottom();
//                      }
////                      // UpdateUI();
//                    break;
//
//                default:
//                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
