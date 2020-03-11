package com.app.apprfid.asciiprotocol;

public class DeviceProperties {
    public static final DeviceProperties DEVICE_DEFAULTS = new DeviceProperties();
    private static final int MAXIMUM_ANTENNA_OUTPUT_POWER_1128 = 29;
    private static final int MAXIMUM_ANTENNA_OUTPUT_POWER_1128_JP = 27;
    private static final int MAXIMUM_ANTENNA_OUTPUT_POWER_1153 = 25;
    private static final int MAXIMUM_ANTENNA_OUTPUT_POWER_1166 = 30;
    private static final int MAXIMUM_ANTENNA_OUTPUT_POWER_DEFAULT = 29;
    public static final int MAXIMUM_ANTENNA_OUTPUT_POWER_LIMIT = 30;
    private static final int MINIMUM_ANTENNA_OUTPUT_POWER_1128 = 10;
    private static final int MINIMUM_ANTENNA_OUTPUT_POWER_1153 = 10;
    private static final int MINIMUM_ANTENNA_OUTPUT_POWER_1166 = 2;
    private static final int MINIMUM_ANTENNA_OUTPUT_POWER_DEFAULT = 10;
    public static final int MINIMUM_ANTENNA_OUTPUT_POWER_LIMIT = 2;
    private int mMaximumCarrierPower;
    private int mMinimumCarrierPower;

    public DeviceProperties() {
        setDefaults();
    }

    public DeviceProperties(String str) {
        if (str == null || str.length() < 4) {
            setDefaults();
            return;
        }
        String upperCase = str.toUpperCase(Constants.COMMAND_LOCALE);
        if (upperCase.startsWith("1128")) {
            this.mMinimumCarrierPower = 10;
            if (upperCase.length() < 7 || !upperCase.startsWith("1128-JP")) {
                this.mMaximumCarrierPower = 29;
            } else {
                this.mMaximumCarrierPower = 27;
            }
        } else if (upperCase.startsWith("1153")) {
            this.mMinimumCarrierPower = 10;
            this.mMaximumCarrierPower = 25;
        } else if (upperCase.startsWith("1166")) {
            this.mMinimumCarrierPower = 2;
            this.mMaximumCarrierPower = 30;
        } else {
            setDefaults();
        }
    }

    private void setDefaults() {
        this.mMinimumCarrierPower = 10;
        this.mMaximumCarrierPower = 29;
    }

    public final int getMaximumCarrierPower() {
        return this.mMaximumCarrierPower;
    }

    public final int getMinimumCarrierPower() {
        return this.mMinimumCarrierPower;
    }
}
