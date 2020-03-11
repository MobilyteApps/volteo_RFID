package com.app.apprfid.findmodel;

import android.util.Log;

import com.app.apprfid.asciiprotocol.AsciiCommander;
import com.app.apprfid.asciiprotocol.commands.InventoryCommand;
import com.app.apprfid.asciiprotocol.enumerations.TriState;
import com.app.apprfid.util.h;

public class PowerRamp {
    private static final boolean D = false;
    public static final int DEFAULT_POWER_REDUCTION_LOWER_LIMIT = 20;
    public static final double DEFAULT_POWER_REDUCTION_THRESHOLD = 0.5d;
    private static final String TAG = "PowerRamp";
    /* access modifiers changed from: private */
    public AsciiCommander mCommander;
    private int mDeviceMaximumPowerLevel;
    private int mDeviceMinimumPowerLevel;
    /* access modifiers changed from: private */
    public InventoryCommand mInventoryPowerCommand = new InventoryCommand();
    private boolean mIsEnabled;
    private int mMaximumPowerRampLevel;
    private int mMinimumPowerRampLevel;
    private double mPowerReductionThreshold;
    private h mTaskQueue;

    public PowerRamp(AsciiCommander asciiCommander, h hVar) {
        this.mCommander = asciiCommander;
        this.mTaskQueue = hVar;
        this.mInventoryPowerCommand.setTakeNoAction(TriState.YES);
        this.mInventoryPowerCommand.setOutputPower(30);
        this.mDeviceMinimumPowerLevel = 2;
        this.mDeviceMaximumPowerLevel = 30;
        setMinimumPowerRampLevel(20);
        setMaximumPowerRampLevel(30);
        setPowerReductionThreshold(0.5d);
    }

    /* access modifiers changed from: private */
    public int validatePowerValue(int i) {
        return (i < this.mDeviceMinimumPowerLevel || i > this.mDeviceMaximumPowerLevel) ? this.mDeviceMaximumPowerLevel : i;
    }

    public int getMaximumPowerRampLevel() {
        return this.mMaximumPowerRampLevel;
    }

    public int getMinimumPowerRampLevel() {
        return this.mMinimumPowerRampLevel;
    }

    public double getPowerReductionThreshold() {
        return this.mPowerReductionThreshold;
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public void rampPowerDown(double d) {
        if (this.mIsEnabled && d >= this.mPowerReductionThreshold) {
            this.mTaskQueue.a(new Runnable() {
                public void run() {
                    PowerRamp.this.mInventoryPowerCommand.setOutputPower(PowerRamp.this.validatePowerValue(PowerRamp.this.mInventoryPowerCommand.getOutputPower()));
                    if (PowerRamp.this.mInventoryPowerCommand.getOutputPower() > PowerRamp.this.getMinimumPowerRampLevel()) {
                        try {
                            PowerRamp.this.mInventoryPowerCommand.setOutputPower(PowerRamp.this.mInventoryPowerCommand.getOutputPower() - 1);
                            PowerRamp.this.mCommander.executeCommand(PowerRamp.this.mInventoryPowerCommand);
                        } catch (Exception e) {
                            Log.e(PowerRamp.TAG, String.format("Failed ramping power DOWN: %s", new Object[]{e.getMessage()}));
                        }
                    }
                }
            });
        }
    }

    public void rampPowerUp(double d) {
        if (this.mIsEnabled && d < this.mPowerReductionThreshold) {
            this.mTaskQueue.a(new Runnable() {
                public void run() {
                    PowerRamp.this.mInventoryPowerCommand.setOutputPower(PowerRamp.this.validatePowerValue(PowerRamp.this.mInventoryPowerCommand.getOutputPower()));
                    if (PowerRamp.this.mInventoryPowerCommand.getOutputPower() < PowerRamp.this.getMaximumPowerRampLevel()) {
                        try {
                            PowerRamp.this.mInventoryPowerCommand.setOutputPower(PowerRamp.this.mInventoryPowerCommand.getOutputPower() + 1);
                            PowerRamp.this.mCommander.executeCommand(PowerRamp.this.mInventoryPowerCommand);
                        } catch (Exception e) {
                            Log.e(PowerRamp.TAG, String.format("Failed ramping power UP: %s", new Object[]{e.getMessage()}));
                        }
                    }
                }
            });
        }
    }

    public void resetToMaximum() {
        this.mInventoryPowerCommand.setOutputPower(getMaximumPowerRampLevel());
        if (!isEnabled()) {
            this.mTaskQueue.a(new Runnable() {
                public void run() {
                    PowerRamp.this.mInventoryPowerCommand.setOutputPower(PowerRamp.this.validatePowerValue(PowerRamp.this.mInventoryPowerCommand.getOutputPower()));
                    try {
                        PowerRamp.this.mCommander.executeCommand(PowerRamp.this.mInventoryPowerCommand);
                    } catch (Exception e) {
                    }
                }
            });
        }
    }

    public void setDevicePowerRangeLimits() {
        this.mDeviceMaximumPowerLevel = this.mCommander.getDeviceProperties().getMaximumCarrierPower();
        this.mDeviceMinimumPowerLevel = this.mCommander.getDeviceProperties().getMinimumCarrierPower();
        setMaximumPowerRampLevel(validatePowerValue(getMaximumPowerRampLevel()));
    }

    public void setEnabled(boolean z) {
        this.mIsEnabled = z;
    }

    public void setMaximumPowerRampLevel(int i) {
        if (i > this.mDeviceMaximumPowerLevel) {
            this.mMaximumPowerRampLevel = this.mDeviceMaximumPowerLevel;
        } else {
            this.mMaximumPowerRampLevel = i;
        }
    }

    public void setMinimumPowerRampLevel(int i) {
        if (i < this.mDeviceMinimumPowerLevel) {
            this.mMinimumPowerRampLevel = this.mDeviceMinimumPowerLevel;
        } else {
            this.mMinimumPowerRampLevel = i;
        }
    }

    public void setPowerReductionThreshold(double d) {
        this.mPowerReductionThreshold = d;
    }
}
