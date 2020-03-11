package com.app.apprfid.asciiprotocol.parameters;

import com.app.apprfid.asciiprotocol.enumerations.TriState;

public final class ResponseParameters {
    public static void appendToCommandLine(IResponseParameters iResponseParameters, StringBuilder sb) {
        appendToCommandLine(iResponseParameters, sb, true);
    }

    public static void appendToCommandLine(IResponseParameters iResponseParameters, StringBuilder sb, boolean z) {
        if (z && iResponseParameters.getUseAlert() != TriState.NOT_SPECIFIED) {
            sb.append(String.format("-al%s", new Object[]{iResponseParameters.getUseAlert().getArgument()}));
        }
        if (iResponseParameters.getIncludeDateTime() != TriState.NOT_SPECIFIED) {
            sb.append(String.format("-dt%s", new Object[]{iResponseParameters.getIncludeDateTime().getArgument()}));
        }
    }

    public static boolean parseParameterFor(IResponseParameters iResponseParameters, String str) {
        if (str.length() > 3) {
            if (str.startsWith("al")) {
                iResponseParameters.setUseAlert(TriState.Parse(str.substring(2)));
                return true;
            } else if (str.startsWith("dt")) {
                iResponseParameters.setIncludeDateTime(TriState.Parse(str.substring(2)));
                return true;
            }
        }
        return false;
    }

    public static void setDefaultParametersFor(IResponseParameters iResponseParameters) {
        iResponseParameters.setUseAlert(TriState.NOT_SPECIFIED);
        iResponseParameters.setIncludeDateTime(TriState.NOT_SPECIFIED);
    }
}
