package com.app.apprfid.findmodel;

import android.content.SharedPreferences;
import android.util.Log;

import com.app.apprfid.a.a;
import com.app.apprfid.asciiprotocol.commands.BarcodeCommand;
import com.app.apprfid.asciiprotocol.commands.InventoryCommand;
import com.app.apprfid.asciiprotocol.commands.SwitchActionCommand;
import com.app.apprfid.asciiprotocol.commands.VersionInformationCommand;
import com.app.apprfid.asciiprotocol.enumerations.Databank;
import com.app.apprfid.asciiprotocol.enumerations.QAlgorithm;
import com.app.apprfid.asciiprotocol.enumerations.QuerySession;
import com.app.apprfid.asciiprotocol.enumerations.QueryTarget;
import com.app.apprfid.asciiprotocol.enumerations.SelectAction;
import com.app.apprfid.asciiprotocol.enumerations.SelectTarget;
import com.app.apprfid.asciiprotocol.enumerations.SwitchAction;
import com.app.apprfid.asciiprotocol.enumerations.SwitchState;
import com.app.apprfid.asciiprotocol.enumerations.TriState;
import com.app.apprfid.asciiprotocol.parameters.IQAlgorithmParameters;
import com.app.apprfid.asciiprotocol.responders.IBarcodeReceivedDelegate;
import com.app.apprfid.asciiprotocol.responders.ICommandResponseLifecycleDelegate;
import com.app.apprfid.asciiprotocol.responders.ISwitchStateReceivedDelegate;
import com.app.apprfid.asciiprotocol.responders.ITransponderReceivedDelegate;
import com.app.apprfid.asciiprotocol.responders.SwitchResponder;
import com.app.apprfid.asciiprotocol.responders.TransponderData;
import com.app.apprfid.findmodel.TargetIdentifiers.ITargetIdentifier;
import com.app.apprfid.util.h;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class FindModelV2 extends ModelBase {
    //    public static final int MULTIPLE_TARGETS_NOTIFICATION = 101;
//    public static final double NO_TRANSPONDER_RSSI_VALUE = -999.0d;
//    public static final int PREF_KEY_AUDIO_FIND_TONE_VOLUME = 100;
    //    public static final String PREF_KEY_CONFIGURATION_CLOSE_RANGE_DETECTION_RESET = "pref_key_configuration_close_range_detection_reset";
//    public static final String PREF_KEY_CONFIGURATION_LONG_RANGE_SEEKING_RESET = "pref_key_configuration_long_range_seeking_reset";
//    public static final String PREF_KEY_CONFIGURATION_RANGE_TEST_RESET = "pref_key_configuration_range_test_reset";
//    public static final String PREF_KEY_DISPLAY_ALWAYS_SHOW_RAW_VALUE = "pref_key_display_always_show_raw_value";
//    public static final boolean PREF_KEY_DISPLAY_ALWAYS_SHOW_SIGNAL_VALUE = false;
//    public static final boolean PREF_KEY_DISPLAY_USE_BARGRAPH = true;
    //    public static final int BARCODE_RECEIVED_NOTIFICATION = 103;
    //    private static final int DEFAULT_MAX_RSSI = -46;
//    private static final int DEFAULT_MAX_RSSI_1128 = -46;
//    private static final int DEFAULT_MAX_RSSI_1153 = -38;
//    private static final int DEFAULT_MAX_RSSI_1166 = -36;
//    private static final int DEFAULT_MIN_RSSI = -60;
//    private static final int DEFAULT_MIN_RSSI_1128 = -80;
//    private static final int DEFAULT_MIN_RSSI_1153 = -56;
//    private static final int DEFAULT_MIN_RSSI_1166 = -70;
    //    private static final int FIXED_TONE_FREQUENCY = 1760;
//    private static final int FREQUENCY_RANGE = 3300;
    //    private static final int RECENT_READING_ITEM_LIMIT = 1;
//    public static final int RSSI_VALUE_CHANGED_NOTIFICATION = 100;
//    public static final int SCAN_STATE_CHANGED_NOTIFICATION = 102;
    //    private final long mBugWorkaroundInterval = 4800;
    private TimerTask mBugWorkaroundTask = null;
    private static final String TAG = "FindModelV2";
    private static final int DEFAULT_RSSI_REFRESH_INTERVAL = 350;
    private static final int BASE_FREQUENCY = 220;
    public static final boolean PREF_KEY_AUDIO_SWEEPING_TONE_ENABLED = true;
    public static final boolean PREF_KEY_RAMP_ENABLED = false;
    public static final int PREF_KEY_RAMP_MAXIMUM_POWER = 29;
    public static final int PREF_KEY_RAMP_MINIMUM_POWER = 20;
    public static final int PREF_KEY_RAMP_THRESHOLD = 50;
    public static final boolean PREF_KEY_THRESHOLD_ENABLED = false;
    public static final int PREF_KEY_THRESHOLD_LEVEL = 50;
    public static final boolean PREF_KEY_THRESHOLD_LOWER_ENABLED = false;
    public static final int PREF_KEY_THRESHOLD_LOWER_LEVEL = 25;
    private static final boolean D = false;
    private static final double DEFAULT_MAX_HISTORY_INTERVAL = 30.0d;
    private static final double MAX_VALID_RSSI_THRESHOLD = -20.0d;
    private static final double MIN_VALID_RSSI_THRESHOLD = -100.0d;
    private static final double MULTIPLE_TARGETS_DETECTED_INTERVAL = 1.0d;
    private static final double RECENT_READING_AVERAGE_INTERVAL = 0.09d;
    private final Timer mBTLagBugTimer = new Timer();
    private String mBarcodeValue;
    private Date mLastLowLevelSignalTime;
    private boolean mMultipleTargetsDetected;
    private boolean mMultipleTranspondersInRange;

    private ITargetIdentifier mTargetIdentifier = null;
    private Date mTimeOfLastMultipleTargetDetection;
    private a mToneGenerator;
    private double mTransponderRSSI;
    private boolean mUseLowLevelSignalTone;
    private boolean mWaitingForFirstMatchingTransponderInRound;
    PowerRamp mPowerRamp;
    private BarcodeCommand mBarcodeAsyncResponder;
    private boolean mBarcodeScannerEnabled;
    private double mDefaultMaxRssi;
    private double mDefaultMinRssi;
    private boolean mEnabled;
    private double mHistoryInterval;
    private InventoryCommand mInventoryResponder;
    private double mMaxRssiValue;
    private double mMinRssiValue;
    private ArrayList mReadingHistory;
    private int mRefreshInterval;
    private ScanState mScanState;
    private SearchType mSearchType = SearchType.UNIQUE;
    private SwitchResponder mSwitchResponder;
    private int mTranspondersReadInLastSecond;
    private Timer mUpdateTimer;
    private TimerTask mUpdateTimerTask;

    private void cancelRefresh() {
        if (mUpdateTimerTask != null) {
            if (!mUpdateTimerTask.cancel()) {
            }
            mUpdateTimerTask = null;
        }
    }

    private void disableBTLagWorkaround() {

        if (mBugWorkaroundTask != null) {
            mBugWorkaroundTask.cancel();
        }
        mBTLagBugTimer.purge();
    }

    private void enableBTLagWorkaround() {
        mBugWorkaroundTask = new TimerTask() {
            public void run() {
                getCommander().send(".al-n\r\n");
                Log.d(FindModelV2.TAG, "Kick");
            }
        };
        mBTLagBugTimer.schedule(mBugWorkaroundTask, 100, 4800);
    }

    private synchronized void forceUpdateAllReadings() {
        TransponderReading transponderReading = new TransponderReading(new Date(), -999.0d);
        updateReadingsForTransponder(transponderReading);
        mPowerRamp.rampPowerUp(transponderReading.getNormalisedRssi());
    }

    private void init() {
        setScanState(ScanState.STOPPED);
        setSearchType(SearchType.UNIQUE);
        this.mMinRssiValue = -60.0d;
        this.mMaxRssiValue = -46.0d;
        mHistoryInterval = DEFAULT_MAX_HISTORY_INTERVAL;
        setReadingHistory(new ArrayList());
        mRefreshInterval = DEFAULT_RSSI_REFRESH_INTERVAL;
        mTimeOfLastMultipleTargetDetection = new Date();
        mInventoryResponder = new InventoryCommand();
        mInventoryResponder.setCaptureNonLibraryResponses(true);
        mInventoryResponder.setResponseLifecycleDelegate(new ICommandResponseLifecycleDelegate() {
            public void responseBegan() {
                mWaitingForFirstMatchingTransponderInRound = true;
                mMultipleTargetsDetected = false;
                mTransponderRSSI = -999.0d;
            }

            public void responseEnded() {
            }
        });
        mInventoryResponder.setTransponderReceivedDelegate(new ITransponderReceivedDelegate() {
            public void transponderReceived(TransponderData transponderData, boolean z) {
                String epc = transponderData.getEpc();
                if (getScanState() == ScanState.SCANNING) {
                    if (epc != null && mTargetIdentifier.isMatch(epc)) {
                        int intValue = transponderData.getRssi().intValue();
                        if (mWaitingForFirstMatchingTransponderInRound) {
                            mTransponderRSSI = (double) intValue;
                            mWaitingForFirstMatchingTransponderInRound = false;
                        } else {
                            mMultipleTargetsDetected = true;
                            if (((double) intValue) > mTransponderRSSI) {
                                mTransponderRSSI = (double) intValue;
                            }
                        }
                        mPowerRamp.rampPowerDown(normaliseRssiValue(mTransponderRSSI));
                    }
                    if (!z) {
                        if (!mMultipleTranspondersInRange && mMultipleTargetsDetected) {
                            mTimeOfLastMultipleTargetDetection = new Date();
                            mMultipleTranspondersInRange = true;
                            if (mHandler != null) {
                                mHandler.sendMessage(mHandler.obtainMessage(101));
                            }
                        }
                        if (!mWaitingForFirstMatchingTransponderInRound) {
                            updateReadingsForTransponder(new TransponderReading(new Date(), mTransponderRSSI));
                        }
                    }
                }
            }
        });
        mSwitchResponder = new SwitchResponder();
        mSwitchResponder.setSwitchStateReceivedDelegate(new ISwitchStateReceivedDelegate() {
            public void switchStateReceived(SwitchState switchState) {
                if (switchState == SwitchState.DOUBLE || switchState == SwitchState.OFF) {
                    stopScanning();
                } else if (switchState == SwitchState.SINGLE) {
                    startScanning();
                }
            }
        });
        mBarcodeAsyncResponder = new BarcodeCommand();
        mBarcodeAsyncResponder.setBarcodeReceivedDelegate(new IBarcodeReceivedDelegate() {
            public void barcodeReceived(String str) {
                if (str.length() > 0) {
                    mBarcodeValue = str;
                    if (mHandler != null) {
                        mHandler.sendMessage(mHandler.obtainMessage(103));
                    }
                }
            }
        });
        mBarcodeAsyncResponder.setCaptureNonLibraryResponses(true);
        mToneGenerator = new a();
        mUpdateTimer = new Timer();
        mPowerRamp = new PowerRamp(mCommander, h.a());
    }

    private void requestRefresh() {
        cancelRefresh();
        if (mUpdateTimerTask == null) {
            mUpdateTimerTask = new TimerTask() {
                public void run() {
                    forceUpdateAllReadings();
                }
            };
            mUpdateTimer.schedule(mUpdateTimerTask, (long) mRefreshInterval);
        }
    }

    /* access modifiers changed from: private */
    public void setDeviceRssiRange() {
        try {
            VersionInformationCommand synchronousCommand = VersionInformationCommand.synchronousCommand();
            this.mCommander.executeCommand(synchronousCommand);
            if (synchronousCommand.getSerialNumber().startsWith("1128")) {
                this.mDefaultMinRssi = -80.0d;
                this.mDefaultMaxRssi = -46.0d;
            } else if (synchronousCommand.getSerialNumber().startsWith("1153")) {
                this.mDefaultMinRssi = -56.0d;
                this.mDefaultMaxRssi = -38.0d;
            } else if (synchronousCommand.getSerialNumber().startsWith("1166")) {
                this.mDefaultMinRssi = -70.0d;
                this.mDefaultMaxRssi = -36.0d;
            } else {
                this.mDefaultMinRssi = -60.0d;
                this.mDefaultMaxRssi = -46.0d;
            }
            this.mMinRssiValue = this.mDefaultMinRssi;
            this.mMaxRssiValue = this.mDefaultMaxRssi;
        } catch (Exception e) {
            Log.e(TAG, "Failed in setDeviceRssiRange", e);
        }
    }

    /* access modifiers changed from: private */
    public void setDeviceSwitchAction() {
        if (mEnabled) {
            setDeviceSwitchActive();
        } else {
            setDeviceSwitchInactive();
        }
    }

    private void setDeviceSwitchActive() {
        try {
            SwitchActionCommand synchronousCommand = SwitchActionCommand.synchronousCommand();
            synchronousCommand.setResetParameters(TriState.YES);
            synchronousCommand.setSinglePressAction(SwitchAction.INVENTORY);
            synchronousCommand.setDoublePressAction(SwitchAction.BARCODE);
            synchronousCommand.setAsynchronousReportingEnabled(TriState.YES);
            getCommander().executeCommand(synchronousCommand);
            getCommander().send(String.format(".sa-n-rs%d", new Object[]{Integer.valueOf(20)}));
        } catch (Exception e) {
            Log.e(TAG, "Failed in setDeviceSwitchActive", e);
        }
    }

    private void setDeviceSwitchInactive() {
        try {
            SwitchActionCommand synchronousCommand = SwitchActionCommand.synchronousCommand();
            synchronousCommand.setResetParameters(TriState.YES);
            synchronousCommand.setSinglePressAction(SwitchAction.OFF);
            synchronousCommand.setDoublePressAction(SwitchAction.BARCODE);
            synchronousCommand.setAsynchronousReportingEnabled(TriState.NO);
            getCommander().executeCommand(synchronousCommand);
        } catch (Exception e) {
            Log.e(TAG, "Failed in setDeviceSwitchActive", e);
        }
    }

    /* access modifiers changed from: private */
    public void setDeviceToTarget() {
        if (mTargetIdentifier != null && mTargetIdentifier.value() != null && mTargetIdentifier.value().length() > 0 && getCommander().isConnected()) {
            performSilentTask(new Runnable() {
                public void run() {
                    boolean z = false;
                    while (!z) {
                        try {
                            InventoryCommand inventoryCommand = new InventoryCommand();
                            inventoryCommand.setResetParameters(TriState.YES);
                            inventoryCommand.setReadParameters(TriState.YES);
                            inventoryCommand.setTakeNoAction(TriState.YES);
                            inventoryCommand.setIncludeTransponderRssi(TriState.YES);
                            inventoryCommand.setUseAlert(TriState.NO);
                            inventoryCommand.setQuerySession(QuerySession.SESSION_0);
                            inventoryCommand.setQueryTarget(QueryTarget.TARGET_B);
                            inventoryCommand.setInventoryOnly(TriState.NO);
                            inventoryCommand.setSelectAction(SelectAction.DEASSERT_SET_B_NOT_ASSERT_SET_A);
                            inventoryCommand.setSelectBank(Databank.ELECTRONIC_PRODUCT_CODE);
                            inventoryCommand.setSelectData(mTargetIdentifier.epcEncoding());
                            inventoryCommand.setSelectOffset(32);
                            inventoryCommand.setSelectLength(mTargetIdentifier.epcMatchingBitLength());
                            inventoryCommand.setSelectTarget(SelectTarget.SESSION_0);
                            setSearchConfiguration(inventoryCommand);
                            mTransponderRSSI = -999.0d;
                            getCommander().executeCommand(inventoryCommand);
                            mPowerRamp.resetToMaximum();
                            z = true;
                        } catch (Exception e) {
                        }
                    }
                    if (!z) {
                        Log.e(FindModelV2.TAG, "Unable to set target !!!");
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void startScanning() {
        mToneGenerator.c();
        mUseLowLevelSignalTone = false;
        mScanState = ScanState.SCANNING;
        if (mHandler != null) {
            mHandler.sendMessage(mHandler.obtainMessage(102));
        }
    }

    /* access modifiers changed from: private */
    public void stopScanning() {
        mToneGenerator.d();
        mScanState = ScanState.STOPPED;
        mTransponderRSSI = -999.0d;
        mUseLowLevelSignalTone = false;
        if (mHandler != null) {
            mHandler.sendMessage(mHandler.obtainMessage(102));
        }
    }

    /* access modifiers changed from: private */
    public void updateHistory(TransponderReading transponderReading) {
        int i;
        ArrayList arrayList = new ArrayList();
        Date timestamp = transponderReading.getTimestamp();
        arrayList.add(new TransponderReading(transponderReading));
        Iterator it = mReadingHistory.iterator();
        while (it.hasNext()) {
            TransponderReading transponderReading2 = (TransponderReading) it.next();
            if (((double) (timestamp.getTime() - transponderReading2.getTimestamp().getTime())) / 1000.0d < mHistoryInterval) {
                arrayList.add(transponderReading2);
            }
        }
        mReadingHistory = arrayList;
        int i2 = 0;
        Iterator it2 = mReadingHistory.iterator();
        while (true) {
            i = i2;
            if (!it2.hasNext()) {
                break;
            }
            TransponderReading transponderReading3 = (TransponderReading) it2.next();
            if (((double) (timestamp.getTime() - transponderReading3.getTimestamp().getTime())) / 1000.0d >= MULTIPLE_TARGETS_DETECTED_INTERVAL || transponderReading3.getRssi() < MIN_VALID_RSSI_THRESHOLD) {
                break;
            }
            i2 = i + 1;
        }
        mTranspondersReadInLastSecond = i;
    }

    private void updatePowerRampingSettings() {

            mPowerRamp.setEnabled(PREF_KEY_RAMP_ENABLED);
            mPowerRamp.setMinimumPowerRampLevel(PREF_KEY_RAMP_MINIMUM_POWER);
            mPowerRamp.setMaximumPowerRampLevel(PREF_KEY_RAMP_MAXIMUM_POWER);
            mPowerRamp.setPowerReductionThreshold(((double) PREF_KEY_RAMP_THRESHOLD) / 100.0d);
    }

    private synchronized void updateReadingsForTransponder(final TransponderReading transponderReading) {
        if (mEnabled) {
            requestRefresh();
        }
        if (((double) (new Date().getTime() - mTimeOfLastMultipleTargetDetection.getTime())) / 1000.0d > MULTIPLE_TARGETS_DETECTED_INTERVAL) {
            mMultipleTranspondersInRange = false;
            if (mHandler != null) {
                mHandler.sendMessage(mHandler.obtainMessage(101));
            }
        }
        performSilentTask(new Runnable() {
            public void run() {
                double d;
                boolean z = true;
                try {
                    updateRecentReadings(transponderReading);
                    updateHistory(transponderReading);
                    double normalisedRssi = transponderReading.getNormalisedRssi();
                    if (PREF_KEY_THRESHOLD_ENABLED) {
                        double d2 = ((double) (FindModelV2.PREF_KEY_THRESHOLD_LEVEL)) / 100.0d;
                        if (normalisedRssi > 0.0d && normalisedRssi < d2) {
                            if (PREF_KEY_THRESHOLD_LOWER_ENABLED && normalisedRssi >= ((double) (FindModelV2.PREF_KEY_THRESHOLD_LOWER_LEVEL)) / 100.0d) {
                                mUseLowLevelSignalTone = true;
                                mLastLowLevelSignalTime = new Date();
                            }
                            transponderReading.setAdjustedRssi(-999.0d);
                            transponderReading.setAdjustedNormalisedRssi(normaliseRssiValue(transponderReading.getAdjustedRssi()));
                            normalisedRssi = transponderReading.getAdjustedNormalisedRssi();
                        }
                    }
                    if ( PREF_KEY_THRESHOLD_LOWER_ENABLED && mLastLowLevelSignalTime != null && new Date().getTime() - mLastLowLevelSignalTime.getTime() > 800) {
                        mUseLowLevelSignalTone = false;
                    }
                    if (!PREF_KEY_AUDIO_SWEEPING_TONE_ENABLED) {
                        mToneGenerator.c(-1.0d);
                        a access$2700 = mToneGenerator;
                        if (transponderReading.getAdjustedNormalisedRssi() > 0.0d) {
                            d = 1760.0d;
                        } else {
                            d = (double) (mUseLowLevelSignalTone ? 880 : FindModelV2.BASE_FREQUENCY);
                        }
                        access$2700.b(d);
                        mToneGenerator.a(transponderReading.getAdjustedNormalisedRssi() > 0.0d);
                    } else {
                        double log10 = ((FindModelV2.MULTIPLE_TARGETS_DETECTED_INTERVAL - Math.log10(10.0d - (9.0d * normalisedRssi))) * 3300.0d) + 220.0d;
                        mToneGenerator.c(normalisedRssi == 0.0d ? 0.3d : 0.15d);
                        mToneGenerator.b(log10);
                        a access$27002 = mToneGenerator;
                        if (transponderReading.getAdjustedNormalisedRssi() <= 0.8d) {
                            z = false;
                        }
                        access$27002.a(z);
                    }
                    if (mHandler != null) {
                        // TODO: 04-03-2020 here imp part
                        mHandler.sendMessage(mHandler.obtainMessage(100, new TransponderReading(transponderReading)));
                    }
                } catch (Exception e) {
                    Log.e(FindModelV2.TAG, "Failed in UpdateReadingsForTransponder");
                }
            }
        });
    }

    private void updateRecentReadings(TransponderReading transponderReading) {
        double d;
        ArrayList arrayList = new ArrayList(1);
        Date date = new Date();
        if (transponderReading.getRssi() != -999.0d) {
            arrayList.add(transponderReading);
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            TransponderReading transponderReading2 = (TransponderReading) it.next();
            if (((double) (date.getTime() - transponderReading2.getTimestamp().getTime())) / 1000.0d <= RECENT_READING_AVERAGE_INTERVAL && arrayList.size() <= 1) {
                arrayList.add(transponderReading2);
            }
        }
        double d2 = 0.0d;
        Iterator it2 = arrayList.iterator();
        while (true) {
            d = d2;
            if (!it2.hasNext()) {
                break;
            }
            d2 = ((TransponderReading) it2.next()).getRssi() + d;
        }
        if (arrayList.size() > 0) {
            transponderReading.setRssi(d / ((double) arrayList.size()));
        } else {
            transponderReading.setRssi(-999.0d);
        }
        transponderReading.setNormalisedRssi(normaliseRssiValue(transponderReading.getRssi()));
        transponderReading.setAdjustedNormalisedRssi(transponderReading.getNormalisedRssi());
    }

    public boolean areMultipleTranspondersInRange() {
        return mMultipleTranspondersInRange;
    }

    public String getBarcodeValue() {
        return mBarcodeValue;
    }

    public void setBarcodeValue(String str) {
        mBarcodeValue = str;
    }

    public double getHistoryInterval() {
        return mHistoryInterval;
    }

    public void setHistoryInterval(double d) {
        if (d <= 0.0d || d > DEFAULT_MAX_HISTORY_INTERVAL) {
            mHistoryInterval = DEFAULT_MAX_HISTORY_INTERVAL;
        } else {
            mHistoryInterval = d;
        }
    }

    public double getMaxRssiValue() {
        return mMaxRssiValue;
    }

    public double getMinRssiValue() {
        return mMinRssiValue;
    }

    public ArrayList getReadingHistory() {
        return mReadingHistory;
    }

    public void setReadingHistory(ArrayList arrayList) {
        mReadingHistory = arrayList;
    }

    public int getRefreshInterval() {
        return mRefreshInterval;
    }

    public void setRefreshInterval(int i) {
        mRefreshInterval = i;
    }

    public ScanState getScanState() {
        return mScanState;
    }

    public void setScanState(ScanState scanState) {
        mScanState = scanState;
    }

    public SearchType getSearchType() {
        return mSearchType;
    }

    public void setSearchType(SearchType searchType) {
        mSearchType = searchType;
    }

    public ITargetIdentifier getTargetIdentifier() {
        return mTargetIdentifier;
    }

    public void setTargetIdentifier(ITargetIdentifier iTargetIdentifier) {
        mTargetIdentifier = iTargetIdentifier;
        setDeviceToTarget();
    }

    public int getTranspondersReadInLastSecond() {
        return mTranspondersReadInLastSecond;
    }

    public void setTranspondersReadInLastSecond(int i) {
        mTranspondersReadInLastSecond = i;
    }

    public void initialise() {
        init();
    }

    public boolean initialiseDevice() {
        try {
            performTask(new Runnable() {
                public void run() {
                    if (FindModelV2.super.initialiseDevice()) {
                        setDeviceSwitchAction();
                        setDeviceToTarget();
                        setDeviceRssiRange();
                        mPowerRamp.setDevicePowerRangeLimits();
                        updatePowerRampingSettings();
                    }
                }
            });
            return true;
        } catch (ModelException e) {
            return false;
        }
    }


    public void setBusy(boolean z) {
        super.setBusy(isEnabled() || z);
    }

    private boolean isEnabled() {
        return mEnabled;
    }

    public void setEnabled(boolean z) {
        if (z != mEnabled) {
            mEnabled = z;
            if (z) {
                mMinRssiValue = mDefaultMinRssi;
                mMaxRssiValue = mDefaultMaxRssi;
                requestRefresh();
                enableBTLagWorkaround();
                getCommander().addResponder(mInventoryResponder);
                getCommander().addResponder(mSwitchResponder);
                updatePowerRampingSettings();
            } else {
                getCommander().removeResponder(mSwitchResponder);
                getCommander().removeResponder(mInventoryResponder);
                mScanState = ScanState.STOPPED;
                mMultipleTranspondersInRange = false;
                mTransponderRSSI = -999.0d;
                mToneGenerator.d();
                cancelRefresh();
                disableBTLagWorkaround();
            }
            performSilentTask(new Runnable() {
                public void run() {
                    if (getCommander().isConnected()) {
                        setDeviceSwitchAction();
                    }
                }
            });
        }
    }

    public boolean isTransponderInRange(double d) {
        return d >= MIN_VALID_RSSI_THRESHOLD;
    }

    private double normaliseRssiValue(double d) {
        if (d < MIN_VALID_RSSI_THRESHOLD || d > MAX_VALID_RSSI_THRESHOLD) {
            return 0.0d;
        }
        if (d > mMaxRssiValue && d <= MAX_VALID_RSSI_THRESHOLD) {
            mMaxRssiValue = d;
        } else if (d < mMinRssiValue && d >= MIN_VALID_RSSI_THRESHOLD) {
            mMinRssiValue = d;
        }
        return (d - mMinRssiValue) / (mMaxRssiValue - mMinRssiValue);
    }

    public void setBarcodeEnabled(boolean z) {
        mBarcodeScannerEnabled = z;
        if (z) {
            mCommander.addResponder(mBarcodeAsyncResponder);
        } else {
            mCommander.removeResponder(mBarcodeAsyncResponder);
        }
    }

    public void setMultipleTranspondersInRange(boolean z) {
        mMultipleTranspondersInRange = z;
    }


    private void setSearchConfiguration(IQAlgorithmParameters iQAlgorithmParameters) {
        iQAlgorithmParameters.setQAlgorithm(QAlgorithm.FIXED);
        if (mSearchType == SearchType.UNIQUE) {
            iQAlgorithmParameters.setQValue(1);
        } else if (mSearchType == SearchType.FEW) {
            iQAlgorithmParameters.setQValue(3);
        } else if (mSearchType == SearchType.MANY) {
            iQAlgorithmParameters.setQValue(6);
        } else {
            iQAlgorithmParameters.setQValue(1);
        }
    }

    public void setToneVolume(double d) {
        double d2 = MULTIPLE_TARGETS_DETECTED_INTERVAL;
        double d3 = 0.0d;
        if (d >= 0.0d) {
            d3 = d;
        }
        if (d3 <= MULTIPLE_TARGETS_DETECTED_INTERVAL) {
            d2 = d3;
        }
        mToneGenerator.a(Math.pow(d2, 2.5d));
    }

    public void stop() {
        mUpdateTimer.cancel();
        mUpdateTimer = null;
        setEnabled(false);
        setBarcodeEnabled(false);
    }

    public enum ConfigurationType {
        UNDEFINED,
        LONG_RANGE_SEEKING,
        CLOSE_RANGE_DETECTION,
        RANGE_TEST
    }
}
