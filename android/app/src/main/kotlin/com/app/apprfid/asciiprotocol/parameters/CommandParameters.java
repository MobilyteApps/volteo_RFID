package com.app.apprfid.asciiprotocol.parameters;

import com.app.apprfid.asciiprotocol.enumerations.TriState;

public final class CommandParameters {
    public static void appendToCommandLine(ICommandParameters iCommandParameters, StringBuilder sb) {
        if (iCommandParameters.implementsTakeNoAction() && iCommandParameters.getTakeNoAction() == TriState.YES) {
            sb.append("-n");
        }
        if (iCommandParameters.implementsReadParameters() && iCommandParameters.getReadParameters() == TriState.YES) {
            sb.append("-p");
        }
        if (iCommandParameters.implementsResetParameters() && iCommandParameters.getResetParameters() == TriState.YES) {
            sb.append("-x");
        }
    }

    public static boolean parseParameterFor(ICommandParameters iCommandParameters, String str) {
        if (str.length() == 1) {
            switch (str.charAt(0)) {
                case 'n':
                    iCommandParameters.setTakeNoAction(TriState.YES);
                    return true;
                case 'p':
                    iCommandParameters.setReadParameters(TriState.YES);
                    return true;
                case 'x':
                    iCommandParameters.setResetParameters(TriState.YES);
                    return true;
            }
        }
        return false;
    }

    public static void setDefaultParametersFor(ICommandParameters iCommandParameters) {
        iCommandParameters.setTakeNoAction(TriState.NOT_SPECIFIED);
        iCommandParameters.setReadParameters(TriState.NOT_SPECIFIED);
        iCommandParameters.setResetParameters(TriState.NOT_SPECIFIED);
    }
}
