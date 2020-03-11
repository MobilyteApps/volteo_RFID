package com.app.apprfid;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Message;
import android.preference.PreferenceManager;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.app.apprfid.asciiprotocol.AsciiCommander;
import com.app.apprfid.asciiprotocol.parameters.AntennaParameters;
import com.app.apprfid.broadcastreceivers.BTListBR;
import com.app.apprfid.config.Constants;
import com.app.apprfid.findmodel.EncodingType;
import com.app.apprfid.findmodel.FindModel;
import com.app.apprfid.findmodel.FindModelV2;
import com.app.apprfid.findmodel.SearchType;
import com.app.apprfid.findmodel.TargetIdentifiers.AbstractGs1TargetIdentifier;
import com.app.apprfid.findmodel.TargetIdentifiers.ITargetIdentifier;
import com.app.apprfid.findmodel.TargetIdentifiers.TargetIdentifierFactory;
import com.app.apprfid.findmodel.TransponderReading;
import com.app.apprfid.handlers.DataStreamHandler;
import com.app.apprfid.handlers.MethodHandler;
import com.app.apprfid.handlers.RSSIStreamHandler;
import com.app.apprfid.util.General;
import com.app.apprfid.util.k;

import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry.Registrar;


public class FlutterVolteoPlugin {
    public static AsciiCommander commander;
    public static RSSIStreamHandler rssiStreamHandler = new RSSIStreamHandler();
    public static DataStreamHandler dataStreamHandler = new DataStreamHandler();
    public static Registrar pluginRegistrar;
    public static FlutterVolteoPlugin pluginInstance;
    public static int mPowerLevel = AntennaParameters.MaximumCarrierPower;
    public static FindModelV2 mModel;
    private static BluetoothAdapter mBtAdapter;
    public static TransponderReading mCurrentReading;
   static String TAG = "FlutterVolteoPlugin";
    //    private static TSLChangeHandler customHandler;
    @SuppressLint("HandlerLeak")
    private static final k mFindModelHandler = new k() {
        public void handleMessage(Message message, String anything) {
            try {
                switch (message.what) {
                    case 3:
                        General.easyPrinter("handleMessage", TAG, "3");
                        return;
                    case 100:
                        General.easyPrinter("handleMessage", TAG, "100");
                    mCurrentReading = (TransponderReading) message.obj;
                        FlutterVolteoPlugin.rssiStreamHandler.sendRSSI(mCurrentReading.getNormalisedRssi());
                        return;
                    case 101:
                        General.easyPrinter("handleMessage", TAG, "101");
//                    updateMultipleTargetView();
                        return;
                    case 102:
                        General.easyPrinter("handleMessage", TAG, "102");
//                    updateScanMessage();
//                    updateMultipleTargetView();
                        return;
                    case 103:
                        General.easyPrinter("handleMessage", TAG, "103");
                        mModel.setEnabled(false);
//                    mAllParametersValid = false;
//                    updateView();
//                    mTargetIdentifierEditText.setText( mModel.getBarcodeValue());
//                    updateModelEnabledState();
//                    updateView();
                        return;
                    default:
                        return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private static boolean mAllParametersValid;

    FlutterVolteoPlugin(Registrar registrar) {
        this.pluginRegistrar = registrar;
        setUpCommander();
    }
    private static BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (mModel!=null&&mModel.getCommander().isConnected()) {
                General.easyPrinter("setUpModel",TAG,"connected");
                mModel.initialiseDevice();
            }
            updateModelEnabledState("ABC000999888777666555444");
        }
    };
    public static void registerWith(Registrar registrar) {
        MethodChannel channel = new MethodChannel(registrar.messenger(), Constants.METHOD_CHANNEL_NAME);
        new EventChannel(registrar.messenger(), Constants.STREAM_CHANNEL_NAME).setStreamHandler(dataStreamHandler);
        new EventChannel(registrar.messenger(), Constants.RSSI_STREAM_CHANNEL_NAME).setStreamHandler(rssiStreamHandler);
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        //set commander
        ((TSLBluetoothDeviceApplication) registrar.activity().getApplication()).setCommander(new AsciiCommander(registrar.activity().getApplicationContext()));
        //get commander
        commander = ((TSLBluetoothDeviceApplication) registrar.activity().getApplication()).getCommander();
        channel.setMethodCallHandler(new MethodHandler(commander, mBtAdapter, registrar.activity()));
        pluginInstance = new FlutterVolteoPlugin(registrar);
        LocalBroadcastManager.getInstance(registrar.activity()).registerReceiver(mMessageReceiver, new IntentFilter(AsciiCommander.STATE_CHANGED_NOTIFICATION));//TODO STEP 6
    }

    /**
     * 1
     */
    public static void setUpModel() {

        General.easyPrinter("setUpModel",TAG,"setup");
        if (mModel == null) {
            General.easyPrinter("setUpModel",TAG,"done");

            mModel = new FindModelV2();
            mModel.setSearchType(SearchType.UNIQUE);
            mModel.setCommander(getCommander());
            mModel.setHandler(mFindModelHandler);
            mModel.initialise();

            mModel.setBarcodeEnabled(true);
            mModel.setToneVolume(100 / 100.0d);



            setUpTarget("ABC000999888777666555444");
//            mModel.setPreferences(PreferenceManager.getDefaultSharedPreferences(getActivity()));
        }
    }

    /**
     * 2
     * @param tagId
     */
    public static void setUpTarget(String tagId){
        General.easyPrinter("setUpTarget",TAG,tagId);

        updateModelEnabledState(tagId);
//        updateUIState();
//        updateView();
    }

    private static void updateModelEnabledState(String tagId) {
        mAllParametersValid = extractParametersFromUI(tagId);
        mModel.setEnabled( mAllParametersValid &&  mModel.getCommander().isConnected());
    }

    private static boolean extractParametersFromUI(String tagId) {
        boolean z;
        boolean z2 =  mModel.getTargetIdentifier() instanceof AbstractGs1TargetIdentifier;
        try {
            ITargetIdentifier createInstance = TargetIdentifierFactory.createInstance(EncodingType.HEX);//identifier_type_v1.3
            if (z2) {
                AbstractGs1TargetIdentifier abstractGs1TargetIdentifier = (AbstractGs1TargetIdentifier) createInstance;
                abstractGs1TargetIdentifier.setGs1CompanyPrefix(null);//pref_key_gs1_company_prefix
                abstractGs1TargetIdentifier.setEpcFilter("1");//pref_key_epc_filter
                z = abstractGs1TargetIdentifier.isGs1CompanyPrefixValid();
            } else {
                z = true;
            }
            String obj =  tagId;
            createInstance.setValue(obj);
            mModel.setTargetIdentifier(createInstance);
            return createInstance.isValueValid() && z;
        } catch (Exception e) {
            return false;
        }
    }
    public static AsciiCommander getCommander() {
        return commander;
    }


    private void setUpCommander() {
        getCommander().addSynchronousResponder();
    }

}


