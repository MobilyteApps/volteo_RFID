package com.app.apprfid.asciiprotocol;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Observable;
import java.util.UUID;

public class BluetoothReaderService extends Observable {
    public static final String DEVICE_NAME_KEY = "device_name";
    public static final int MESSAGE_STATE_CHANGE = 1;
    /* access modifiers changed from: private */
    public static final UUID MY_UUID_INSECURE = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    /* access modifiers changed from: private */
    public static final UUID MY_UUID_SECURE = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    public static final String REASON_KEY = "reason";
    public static final int STATE_CONNECTED = 3;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_DISCONNECTED = 1;
    public static final int STATE_NONE = 0;
    private static final boolean D = true;
    private static final String TAG = "BluetoothReaderService";
    /* access modifiers changed from: private */
    public final BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
    private final Handler mHandler;
    /* access modifiers changed from: private */
    public ConnectThread mConnectThread;
    /* access modifiers changed from: private */
    public ConnectedThread mConnectedThread;
    private int mState = 1;

    public BluetoothReaderService(Handler handler) {
        this.mHandler = handler;
        this.mConnectThread = null;
        this.mConnectedThread = null;
    }

    private synchronized void setState(int i, Bundle bundle) {
        int i2 = this.mState;
        this.mState = i;
        if (i2 != i) {
            Message obtainMessage = this.mHandler.obtainMessage(1, i, -1);
            if (bundle != null) {
                obtainMessage.setData(bundle);
            }
            this.mHandler.sendMessage(obtainMessage);
        }
    }

    /* access modifiers changed from: private */
    public synchronized void setStateForReason(int i, String str) {
        Bundle bundle = new Bundle();
        bundle.putString(REASON_KEY, str);
        setState(i, bundle);
    }

    public synchronized void connect(BluetoothDevice bluetoothDevice, boolean z) {
        Log.d(TAG, "connect to: " + bluetoothDevice);
        if (this.mState == 2 && this.mConnectThread != null) {
            this.mConnectThread.cancel();
            this.mConnectThread = null;
        }
        if (this.mConnectedThread != null) {
            this.mConnectedThread.cancel();
            this.mConnectedThread = null;
        }
        this.mConnectThread = new ConnectThread(bluetoothDevice, z);
        this.mConnectThread.start();
        setState(2, null);
    }

    public synchronized void connected(BluetoothSocket bluetoothSocket, BluetoothDevice bluetoothDevice, String str) {
        Log.d(TAG, "connected, Socket Type:" + str);
        if (this.mConnectThread != null) {
            this.mConnectThread.cancel();
            this.mConnectThread = null;
        }
        if (this.mConnectedThread != null) {
            this.mConnectedThread.cancel();
            this.mConnectedThread = null;
        }
        this.mConnectedThread = new ConnectedThread(this, bluetoothSocket, str);
        this.mConnectedThread.start();
        Bundle bundle = new Bundle();
        bundle.putString(DEVICE_NAME_KEY, bluetoothDevice.getName());
        bundle.putString(REASON_KEY, "Device connected.");
        setState(3, bundle);
    }

    public synchronized int getState() {
        return this.mState;
    }

    private synchronized void setState(int i) {
        this.mState = i;
    }

    public synchronized void stop() {
        Log.d(TAG, "stop");
        if (this.mConnectThread != null) {
            Log.d(TAG, "cancelling connect thread");
            this.mConnectThread.cancel();
            Log.d(TAG, "cancelled connect thread");
            this.mConnectThread = null;
        }
        if (this.mConnectedThread != null) {
            Log.d(TAG, "cancelling connected thread");
            this.mConnectedThread.cancel();
            Log.d(TAG, "cancelled connected thread");
            this.mConnectedThread = null;
        }
        setState(1);
    }

    public void write(byte[] bArr) {
        synchronized (this) {
            if (this.mState == 3) {
                ConnectedThread connectedThread = this.mConnectedThread;
                connectedThread.write(bArr);
            }
        }
    }

    class ConnectThread extends Thread {
        private final BluetoothDevice mmDevice;
        private String mSocketType;
        private BluetoothSocket mmSocket;

        public ConnectThread(BluetoothDevice bluetoothDevice, boolean z) {
            BluetoothSocket bluetoothSocket = null;
            Log.d(BluetoothReaderService.TAG, "- ConnectThread Created");
            this.mmDevice = bluetoothDevice;
            this.mSocketType = z ? "Secure" : "Insecure";
            if (z) {
                try {
                    bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(BluetoothReaderService.MY_UUID_SECURE);
                } catch (IOException e) {
                    Log.e(BluetoothReaderService.TAG, "Socket Type: " + this.mSocketType + "create() failed", e);
                    bluetoothSocket = null;
                }
            } else {
                try {
                    bluetoothSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(BluetoothReaderService.MY_UUID_INSECURE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            this.mmSocket = bluetoothSocket;
        }

        public synchronized void cancel() {
            Log.d(BluetoothReaderService.TAG, "connect thread cancel() BEGAN");
            try {
                synchronized (this) {
                    this.mmSocket.close();
                    Log.d(BluetoothReaderService.TAG, "connect thread cancel() ENDED");
                }
            } catch (IOException e) {
                Log.e(BluetoothReaderService.TAG, "close() of connect " + this.mSocketType + " socket failed", e);
            }
            return;
        }

        public void run() {
            Log.d(BluetoothReaderService.TAG, "BEGIN mConnectThread SocketType:" + this.mSocketType);
            setName("ConnectThread" + this.mSocketType);
            BluetoothReaderService.this.mAdapter.cancelDiscovery();
            try {
                this.mmSocket.connect();
            } catch (IOException e) {
                Log.e(BluetoothReaderService.TAG, e.toString());
                try {
                    this.mmSocket = (BluetoothSocket) this.mmDevice.getClass().getMethod("createRfcommSocket", new Class[]{Integer.TYPE}).invoke(this.mmDevice, new Object[]{Integer.valueOf(1)});
                    this.mmSocket.connect();
                } catch (Exception e2) {
                    try {
                        synchronized (this) {
                            this.mmSocket.close();
                            BluetoothReaderService.this.setStateForReason(1, "Connection failed");
                            return;
                        }
                    } catch (IOException e3) {
                        Log.e(BluetoothReaderService.TAG, "unable to close() " + this.mSocketType + " socket during connection failure", e3);
                    }
                }
            }
            synchronized (BluetoothReaderService.this) {
                BluetoothReaderService.this.mConnectThread = null;
            }
            BluetoothReaderService.this.connected(this.mmSocket, this.mmDevice, this.mSocketType);
            Log.d(BluetoothReaderService.TAG, "END mConnectThread");
        }
    }

    class ConnectedThread extends Thread {
        BluetoothReaderService this$0;
        private volatile boolean mCancelled = false;
        private InputStream mmInStream;
        private OutputStream mmOutStream;
        private BluetoothSocket mmSocket;


        public ConnectedThread(BluetoothReaderService bluetoothReaderService, BluetoothSocket bluetoothSocket, String str) {
            IOException e = null;
            InputStream r1 = null;
            InputStream r12 = null;
            OutputStream r0 = null;
            OutputStream r02 = null;
            this$0 = bluetoothReaderService;
            Log.d(BluetoothReaderService.TAG, "create ConnectedThread: " + str);
            mmSocket = bluetoothSocket;
            try {
                InputStream inputStream = bluetoothSocket.getInputStream();
                try {
                    r12 = inputStream;
                    r0 = bluetoothSocket.getOutputStream();
                } catch (IOException e2) {
                    e = e2;
                    r1 = inputStream;
                }
            } catch (IOException e3) {
                e = e3;
                // TODO: 03-03-2020 Here
//                r1 = r02;
            }
            this.mmInStream = r12;
            this.mmOutStream = r0;
            Log.e(BluetoothReaderService.TAG, "temp sockets not created", e);
//            r0 = r02;
//            r12 = r1;
            this.mmInStream = r12;
            this.mmOutStream = r0;
        }

        public synchronized void cancel() {
            Log.d(BluetoothReaderService.TAG, "connected thread cancel() BEGAN");
            try {
                synchronized (this) {
                    this.mCancelled = BluetoothReaderService.D;
                    this.mmSocket.close();
                    Log.d(BluetoothReaderService.TAG, "connected thread cancel() ENDED");
                }
            } catch (IOException e) {
                Log.e(BluetoothReaderService.TAG, "close() of connect socket failed", e);
            }
            return;
        }

        public void run() {
            this.mCancelled = false;
            Log.d(BluetoothReaderService.TAG, "BEGIN mConnectedThread");
            InputStreamReader inputStreamReader = new InputStreamReader(this.mmInStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while (!this.mCancelled) {
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        this.this$0.setChanged();
                        this.this$0.notifyObservers(readLine);
                    } catch (Exception e) {
                        Log.d(BluetoothReaderService.TAG, AsciiCommander.USER_DISCONNECTED_MESSAGE_PREFIX, e);
                        this.this$0.setStateForReason(1, "Connection lost");
                    }
                }
                if (!this.mCancelled) {
                    Log.d(BluetoothReaderService.TAG, "Connection appears to be lost");
                    this.this$0.setStateForReason(1, "Connection appears to be lost");
                    cancel();
                    this.this$0.mConnectedThread = null;
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e2) {
                    Log.d(BluetoothReaderService.TAG, "Failed to close input stream reader (mConnectedThread)");
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.d(BluetoothReaderService.TAG, "END mConnectedThread");
        }

        public void write(byte[] bArr) {
            try {
                this.mmOutStream.write(bArr);
            } catch (IOException e) {
                Log.e(BluetoothReaderService.TAG, "Exception during write", e);
                this.this$0.setStateForReason(1, "Connection lost");
                cancel();
                this.this$0.mConnectedThread = null;
            }
        }
    }
}
