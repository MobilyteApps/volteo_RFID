package com.app.apprfid.asciiprotocol.parameters;

public final class AntennaParameters {
    public static final int MaximumCarrierPower = 30;
    public static final int MinimumCarrierPower = 2;
    public static final int OutputPowerNotSpecified = -1;

    public static void appendToCommandLine(IAntennaParameters iAntennaParameters, StringBuilder sb) {
        if (iAntennaParameters.getOutputPower() == -1) {
            return;
        }
        if (iAntennaParameters.getOutputPower() < 2 || iAntennaParameters.getOutputPower() > 30) {
            throw new IllegalArgumentException(String.format("Output power argument (%s) is invalid", new Object[]{Integer.valueOf(iAntennaParameters.getOutputPower())}));
        }
        sb.append(String.format("-o%02d", new Object[]{Integer.valueOf(iAntennaParameters.getOutputPower())}));
    }

    public static boolean parseParameterFor(IAntennaParameters iAntennaParameters, String str) {
        if (str.length() <= 1 || !str.startsWith("o")) {
            return false;
        }
        iAntennaParameters.setOutputPower(Integer.parseInt(String.format(str.substring(1).trim(), new Object[0])));
        return true;
    }

    public static void setDefaultParametersFor(IAntennaParameters iAntennaParameters) {
        iAntennaParameters.setOutputPower(-1);
    }
}
