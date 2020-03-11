package com.app.apprfid.findmodel;

import android.os.Handler;
import android.util.Log;


import com.app.apprfid.asciiprotocol.AsciiCommander;
import com.app.apprfid.asciiprotocol.commands.SwitchStateCommand;
import com.app.apprfid.util.h;

import java.util.Date;

public class ModelBase {
    public static final int BUSY_STATE_CHANGED_NOTIFICATION = 1;
    private static final boolean D = false;
    public static final int DEVICE_INITIALISATION_FAILED_NOTIFICATION = 3;
    public static final int MESSAGE_NOTIFICATION = 2;
    private static final String TAG = "ModelBase";
    protected boolean mBusy = false;
    protected AsciiCommander mCommander = null;
    /* access modifiers changed from: private */
    public Exception mException;
    protected Handler mHandler = null;
    protected double mLastTaskExecutionDuration = -1.0d;
    protected Runnable mTaskRunner;
    /* access modifiers changed from: private */
    public Date mTaskStartTime;

    public Exception error() {
        return this.mException;
    }

    public AsciiCommander getCommander() {
        return this.mCommander;
    }

    public Handler getHandler() {
        return this.mHandler;
    }

    public final double getTaskExecutionDuration() {
        return this.mLastTaskExecutionDuration >= 0.0d ? this.mLastTaskExecutionDuration : ((double) (new Date().getTime() - this.mTaskStartTime.getTime())) / 1000.0d;
    }

    public boolean initialiseDevice() {
        boolean z = false;
        if (getCommander().isConnected()) {
            int i = 0;
            while (true) {
                if (i >= 5) {
                    break;
                }
                i++;
                try {
                    SwitchStateCommand synchronousCommand = SwitchStateCommand.synchronousCommand();
                    getCommander().executeCommand(synchronousCommand);
                    if (synchronousCommand.isSuccessful()) {
                        z = true;
                        break;
                    }
                } catch (Exception e) {
                }
            }
        }
        if (!z && this.mHandler != null) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(3));
        }
        return z;
    }

    public boolean isBusy() {
        return this.mBusy;
    }

    public boolean isTaskRunning() {
        return this.mTaskRunner != null;
    }

    public void performSilentTask(final Runnable runnable) {
        h.a().a(new Runnable() {
            public void run() {
                ModelBase.this.mLastTaskExecutionDuration = -1.0d;
                ModelBase.this.mTaskStartTime = new Date();
                try {
                    runnable.run();
                } catch (Exception e) {
                    Log.e(ModelBase.TAG, String.format("Unhandled Exception (silent): %s", new Object[]{e.getMessage()}));
                }
                Date date = new Date();
                ModelBase.this.mLastTaskExecutionDuration = ((double) (date.getTime() - ModelBase.this.mTaskStartTime.getTime())) / 1000.0d;
            }
        });
    }

    public void performTask(final Runnable runnable) throws ModelException {
        if (this.mCommander == null) {
            throw new ModelException("There is no AsciiCommander set for this model!");
        } else if (this.mTaskRunner != null) {
            throw new ModelException("Task is already running!");
        } else {
            this.mTaskRunner = new Runnable() {
                public void run() {
                    ModelBase.this.mLastTaskExecutionDuration = -1.0d;
                    ModelBase.this.mTaskStartTime = new Date();
                    try {
                        ModelBase.this.setBusy(true);
                        ModelBase.this.mException = null;
                        runnable.run();
                    } catch (Exception e) {
                        ModelBase.this.mException = e;
                    }
                    ModelBase.this.setBusy(false);
                    Date date = new Date();
                    ModelBase.this.mLastTaskExecutionDuration = ((double) (date.getTime() - ModelBase.this.mTaskStartTime.getTime())) / 1000.0d;
                    ModelBase.this.mTaskRunner = null;
                }
            };
            try {
                h.a().a(this.mTaskRunner);
            } catch (Exception e) {
                this.mException = e;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void sendMessageNotification(String str) {
        if (this.mHandler != null) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(2, str));
        }
    }

    /* access modifiers changed from: protected */
    public void setBusy(boolean z) {
        if (this.mBusy != z) {
            this.mBusy = z;
            if (this.mHandler != null) {
                this.mHandler.sendMessage(this.mHandler.obtainMessage(1, Boolean.valueOf(z)));
            }
        }
    }

    public void setCommander(AsciiCommander asciiCommander) {
        this.mCommander = asciiCommander;
    }

    /* access modifiers changed from: protected */
    public void setError(Exception exc) {
        this.mException = exc;
    }

    public void setHandler(Handler handler) {
        this.mHandler = handler;
    }
}
