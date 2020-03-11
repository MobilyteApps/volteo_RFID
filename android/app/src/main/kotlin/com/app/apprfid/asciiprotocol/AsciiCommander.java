package com.app.apprfid.asciiprotocol;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.app.apprfid.asciiprotocol.commands.IAsciiCommand;
import com.app.apprfid.asciiprotocol.commands.IAsciiCommandExecuting;
import com.app.apprfid.asciiprotocol.commands.VersionInformationCommand;
import com.app.apprfid.asciiprotocol.responders.IAsciiCommandResponder;
import com.app.apprfid.asciiprotocol.responders.SynchronousDispatchResponder;
import com.app.apprfid.util.General;

import java.lang.ref.WeakReference;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class AsciiCommander implements IAsciiCommandExecuting, Observer {
    public static final String CONNECTED_MESSAGE_PREFIX = "Connected to: ";
    public static final String CONNECTING_MESSAGE_PREFIX = "Reader connecting...";
    private static final boolean D = false;
    public static final String DISCONNECTED_MESSAGE_PREFIX = "Reader not connected";
    public static final String REASON_KEY = "reason_key";
    public static final String STATE_CHANGED_NOTIFICATION = "TSLAsciiCommanderStateChangedNotification";
    private static final String TAG = "AsciiCommander";
    public static final String USER_DISCONNECTED_MESSAGE_PREFIX = "Disconnected";
    private boolean awaitingCommandResponse;
    private Object commandCondition = new Object();
    private Object commandSync = new Object();
    /* access modifiers changed from: private */
    public ConnectionState connectionState = ConnectionState.UNDEFINED;
    /* access modifiers changed from: private */
    public Context context;
    /* access modifiers changed from: private */
    public String deviceName;
    /* access modifiers changed from: private */
    public DeviceProperties deviceProperties;
    private VersionInformationCommand informationCommand;
    /* access modifiers changed from: private */
    public BluetoothDevice lastSuccessfullyConnectedReader;
    private final Handler mHandler = new InnerHandler(this);
    private boolean privateIsResponsive;
    private Date privateLastActivityTime = new Date(0);
    private String privateLastCommandLine;
    /* access modifiers changed from: private */
    public BluetoothDevice reader;
    private BluetoothReaderService readerService;
    private ArrayList responderChain;
    private Object responderLock = new Object();
    private boolean responseReceived;
    private SynchronousDispatchResponder synchronousResponder;

    public enum ConnectionState {
        UNDEFINED,
        DISCONNECTED,
        CONNECTING,
        CONNECTED
    }

    class InnerHandler extends Handler {
        WeakReference weakCommander;

        public InnerHandler(AsciiCommander asciiCommander) {
            this.weakCommander = new WeakReference(asciiCommander);
        }

        public void handleMessage(Message message) {
            try {
                AsciiCommander asciiCommander = (AsciiCommander) this.weakCommander.get();
                if (asciiCommander != null) {
                    switch (message.what) {
                        case 1:
                            String str = "";
                            switch (message.arg1) {
                                case 1:
                                    Log.d(AsciiCommander.TAG, "Disconnected: " + message.getData().getString(BluetoothReaderService.REASON_KEY));
                                    String str2 = AsciiCommander.DISCONNECTED_MESSAGE_PREFIX;
                                    asciiCommander.connectionState = ConnectionState.DISCONNECTED;
                                    asciiCommander.deviceProperties = DeviceProperties.DEVICE_DEFAULTS;
                                    asciiCommander.sendStateChangeNotification(str2);
                                    return;
                                case 2:
                                    String str3 = AsciiCommander.CONNECTING_MESSAGE_PREFIX;
                                    asciiCommander.connectionState = ConnectionState.CONNECTING;
                                    asciiCommander.sendStateChangeNotification(str3);
                                    return;
                                case 3:
                                    asciiCommander.deviceName = message.getData().getString(BluetoothReaderService.DEVICE_NAME_KEY);
                                    String str4 = AsciiCommander.CONNECTED_MESSAGE_PREFIX + asciiCommander.deviceName;
                                    Log.d(AsciiCommander.TAG, str4);
                                    asciiCommander.lastSuccessfullyConnectedReader = asciiCommander.reader;
                                    Editor edit = asciiCommander.context.getSharedPreferences("AsciiCommanderPreferences", 0).edit();
                                    edit.putString("lastConnectedReaderAddress", asciiCommander.reader.getAddress());
                                    edit.commit();
                                    asciiCommander.updateDeviceProperties();
                                    asciiCommander.connectionState = ConnectionState.CONNECTED;
                                    asciiCommander.sendStateChangeNotification(str4);
                                    return;
                                default:
                                    return;
                            }
                        default:
                            return;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public AsciiCommander(Context context2) {
        this.context = context2;
        this.awaitingCommandResponse = false;
        this.responseReceived = false;
        this.responderChain = new ArrayList();
        this.connectionState = ConnectionState.DISCONNECTED;
        this.informationCommand = VersionInformationCommand.synchronousCommand();
        this.deviceProperties = DeviceProperties.DEVICE_DEFAULTS;
        this.readerService = new BluetoothReaderService(this.mHandler);
        this.readerService.addObserver(this);
        String string = context2.getSharedPreferences("AsciiCommanderPreferences", 0).getString("lastConnectedReaderAddress", null);
        if (string != null) {
            this.lastSuccessfullyConnectedReader = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(string);
        } else {
            this.lastSuccessfullyConnectedReader = null;
        }
    }

    private void commandDidFinish() {
        synchronized (this.responderLock) {
            if (this.synchronousResponder != null) {
                this.synchronousResponder.setSynchronousCommandResponder(null);
            }
        }
    }

    private void executeCommand(IAsciiCommand iAsciiCommand, double d) {
        try {
            synchronized (this.commandSync) {
                setIsResponsive(true);
                if (d <= 1.0E-4d) {
                    throw new InvalidParameterException("Timeout must be greater than 0.0001s");
                } else if (this.awaitingCommandResponse) {
                    throw new UnsupportedOperationException("Already executing a command");
                } else {
                    this.awaitingCommandResponse = iAsciiCommand.getSynchronousCommandResponder() != null;
                    if (!this.awaitingCommandResponse || getHasSynchronousResponder()) {
                        this.responseReceived = iAsciiCommand.getSynchronousCommandResponder() == null;
                        boolean z = false;
                        try {
                            synchronized (this.commandCondition) {
                                setLastCommandLine(iAsciiCommand.getCommandLine());
                                send(getLastCommandLine());
                                Date date = new Date();
                                while (!this.responseReceived && !z) {
                                    long currentTimeMillis = System.currentTimeMillis();
                                    try {
                                        this.commandCondition.wait((long) (1000.0d * d));
                                    } catch (InterruptedException e) {
                                    }
                                    z = ((double) (System.currentTimeMillis() - currentTimeMillis)) / 1000.0d >= d;
                                    Date date2 = new Date();
                                    double time = ((double) (date2.getTime() - date.getTime())) / 1000.0d;
                                    if (!this.responseReceived && z) {
                                        if (((double) (date2.getTime() - getLastActivityTime().getTime())) / 1000.0d < d) {
                                            z = false;
                                        } else {
                                            Log.e(TAG, "Command timed out!" + String.format(" (%.2fs)", new Object[]{Double.valueOf(time)}));
                                        }
                                    }
                                }
                            }
                            if (z) {
                                setIsResponsive(false);
                            }
                            this.awaitingCommandResponse = false;
                        } catch (RuntimeException e2) {
                            try {
                                Log.e(TAG, "Command failed", e2);
                                throw e2;
                            } catch (Throwable th) {
                                this.awaitingCommandResponse = false;
                                throw th;
                            }
                        }
                    } else {
                        Log.e(TAG, "!!! No synchronous responder in the responder chain !!!");
                        throw new UnsupportedOperationException("No synchronous responder in the responder chain");
                    }
                }
            }
            commandDidFinish();
        } catch (RuntimeException e3) {
            try {
                Log.e(TAG, "executeCommand failed", e3);
                throw e3;
            } catch (Throwable th2) {
                commandDidFinish();
                throw th2;
            }
        }
    }

    /* access modifiers changed from: private */
    public void sendStateChangeNotification(String str) {
        Intent intent = new Intent(STATE_CHANGED_NOTIFICATION);
        intent.putExtra(REASON_KEY, str);
        LocalBroadcastManager.getInstance(this.context).sendBroadcast(intent);

    }

    private void setIsResponsive(boolean z) {
        this.privateIsResponsive = z;
    }

    private void setLastCommandLine(String str) {
        this.privateLastCommandLine = str;
    }

    /* access modifiers changed from: private */
    public void updateDeviceProperties() {
        int i=0;
        boolean z = !getHasSynchronousResponder();
        if (z) {
            addSynchronousResponder();
        }
        int i2 = 3;
        while (true) {
            try {
                i = i2;
                executeCommand(this.informationCommand);
                if (this.informationCommand.isSuccessful()) {
                    this.deviceProperties = new DeviceProperties(this.informationCommand.getSerialNumber());
                    i2 = 0;
                } else {
                    i2 = i - 1;
                    if (i2 <= 0) {
                        this.deviceProperties = DeviceProperties.DEVICE_DEFAULTS;
                    } else {
                        try {
                            Thread.sleep(100);
                        } catch (Exception e) {
                        }
                    }
                }
            } catch (Exception e2) {
                if (this.informationCommand.isSuccessful()) {
                    this.deviceProperties = new DeviceProperties(this.informationCommand.getSerialNumber());
                    i2 = 0;
                } else {
                    i2 = i - 1;
                    if (i2 <= 0) {
                        this.deviceProperties = DeviceProperties.DEVICE_DEFAULTS;
                    } else {
                        try {
                            Thread.sleep(100);
                        } catch (Exception e3) {
                        }
                    }
                }
            } catch (Throwable th) {
                if (this.informationCommand.isSuccessful()) {
                    this.deviceProperties = new DeviceProperties(this.informationCommand.getSerialNumber());
                } else if (i - 1 <= 0) {
                    this.deviceProperties = DeviceProperties.DEVICE_DEFAULTS;
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (Exception e4) {
                    }
                }
                throw th;
            }
            if (i2 <= 0) {
                break;
            }
        }
        if (z) {
            removeSynchronousResponder();
        }
    }

    public final void addResponder(IAsciiCommandResponder iAsciiCommandResponder) {
        synchronized (this.responderLock) {
            this.responderChain.add(iAsciiCommandResponder);
        }
    }

    public final void addSynchronousResponder() {
        synchronized (this.responderLock) {
            if (this.synchronousResponder == null) {
                this.synchronousResponder = new SynchronousDispatchResponder();
                addResponder(this.synchronousResponder);
            }
        }
    }

    public final void clearResponders() {
        synchronized (this.responderLock) {
            this.responderChain.clear();
            this.synchronousResponder = null;
        }
    }

    public final void connect(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            bluetoothDevice = this.lastSuccessfullyConnectedReader;
        }
        if (bluetoothDevice != null) {
            if (isConnected()) {
                disconnect();
            }
            this.reader = bluetoothDevice;
            this.readerService.connect(bluetoothDevice, false);
        } else {
            Log.w("AsciiCommander.Connect", "Atempted to connect to a null reader");
        }
        setLastActivityTime(new Date());
    }

    public final void disconnect() {
        this.readerService.stop();
        this.reader = null;
        this.connectionState = ConnectionState.DISCONNECTED;
        sendStateChangeNotification(USER_DISCONNECTED_MESSAGE_PREFIX);
        setLastActivityTime(new Date());
    }

    public void executeCommand(IAsciiCommand iAsciiCommand) {
        if (iAsciiCommand == null) {
            throw new IllegalArgumentException("command is null");
        }
        if (iAsciiCommand.getSynchronousCommandResponder() != null) {
            synchronized (this.responderLock) {
                if (this.synchronousResponder == null) {
                    throw new UnsupportedOperationException("No synchronous command relay in chain");
                } else if (this.synchronousResponder.getSynchronousCommandResponder() != null) {
                    throw new UnsupportedOperationException("There is already a synchronous command executing");
                } else {
                    this.synchronousResponder.setSynchronousCommandResponder(iAsciiCommand.getSynchronousCommandResponder());
                    iAsciiCommand.getSynchronousCommandResponder().clearLastResponse();
                }
            }
        }
        executeCommand(iAsciiCommand, iAsciiCommand.getMaxSynchronousWaitTime());
    }

    public String getConnectedDeviceName() {
        return this.deviceName;
    }

    public ConnectionState getConnectionState() {
        return this.connectionState;
    }

    public final DeviceProperties getDeviceProperties() {
        return this.deviceProperties;
    }

    public final boolean getHasSynchronousResponder() {
        return this.synchronousResponder != null;
    }

    public final Date getLastActivityTime() {
        return this.privateLastActivityTime;
    }

    public final String getLastCommandLine() {
        return this.privateLastCommandLine;
    }

    public final Iterable getResponderChain() {
        return this.responderChain;
    }

    public final boolean hasConnectedSuccessfully() {
        return this.lastSuccessfullyConnectedReader != null;
    }

    public final boolean isConnected() {
        return this.reader != null && this.readerService.getState() == 3;
    }

    public final boolean isResponsive() {
        return this.privateIsResponsive;
    }

    public final void permanentlyDisconnect() {
        if (isConnected()) {
            send(".sl");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            disconnect();
        }
    }

    /* access modifiers changed from: protected */
    public void processReceivedLine(String str, int i, boolean z) {
        for (Object iAsciiCommandResponder : getResponderChain()) {
            try {
                boolean processReceivedLine = ((IAsciiCommandResponder)iAsciiCommandResponder).processReceivedLine(str, z);
                if (!this.responseReceived && ((IAsciiCommandResponder)iAsciiCommandResponder).isResponseFinished() && (iAsciiCommandResponder instanceof SynchronousDispatchResponder)) {
                    synchronized (this.commandCondition) {
                        this.responseReceived = true;
                        this.commandCondition.notify();
                    }
                    continue;
                }
                if (processReceivedLine) {
                    return;
                }
            } catch (Exception e) {
                Log.e(TAG, "Exception while processing response line", e);
                throw e;
            }
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Collection, code=java.util.Collection<java.lang.String>, for r7v0, types: [java.util.Collection, java.util.Collection<java.lang.String>] */
    public synchronized void processReceivedLines(Collection<String> collection) {
        int i = 0;
        for (String processReceivedLine : collection) {
            int i2 = i + 1;
            processReceivedLine(processReceivedLine, i, i2 < collection.size() + -1);
            i = i2;
        }
    }

    public final void removeResponder(IAsciiCommandResponder iAsciiCommandResponder) {
        synchronized (this.responderLock) {
            this.responderChain.remove(iAsciiCommandResponder);
        }
    }

    public final void removeSynchronousResponder() {
        synchronized (this.responderLock) {
            if (this.synchronousResponder != null) {
                removeResponder(this.synchronousResponder);
                this.synchronousResponder = null;
            }
        }
    }

    public void send(String str) {
        if (!isConnected()) {
            throw new UnsupportedOperationException(DISCONNECTED_MESSAGE_PREFIX);
        }
        if (!(str.codePointBefore(str.length()) == 13 || str.codePointBefore(str.length()) == 10)) {
            str = str + "\r\n";
        }
        this.readerService.write(str.getBytes());
    }

    /* access modifiers changed from: protected */
    public final void setLastActivityTime(Date date) {
        this.privateLastActivityTime = date;
    }

    public void update(Observable observable, Object obj) {
        if (observable.equals(this.readerService)) {
            ArrayList arrayList = new ArrayList();
            arrayList.add((String) obj);
            try {
                processReceivedLines(arrayList);
            } catch (Exception e) {
                Log.e(TAG, "Unhandled exception: " + e.getMessage());
            }
            setLastActivityTime(new Date());
        }
    }
}
