package com.app.apprfid.asciiprotocol.parameters;


import com.app.apprfid.b.b;
import com.app.apprfid.b.b;
import com.app.apprfid.asciiprotocol.Constants;
import com.app.apprfid.asciiprotocol.enumerations.TriState;

public final class TransponderParameters {
    public static void appendToCommandLine(ITransponderParameters iTransponderParameters, StringBuilder sb) {
        if (iTransponderParameters.getIncludeChecksum() != TriState.NOT_SPECIFIED) {
            sb.append(String.format("-c%s", new Object[]{iTransponderParameters.getIncludeChecksum().getArgument()}));
        }
        if (iTransponderParameters.getIncludePC() != TriState.NOT_SPECIFIED) {
            sb.append(String.format("-e%s", new Object[]{iTransponderParameters.getIncludePC().getArgument()}));
        }
        if (iTransponderParameters.getIncludeTransponderRssi() != TriState.NOT_SPECIFIED) {
            sb.append(String.format("-r%s", new Object[]{iTransponderParameters.getIncludeTransponderRssi().getArgument()}));
        }
        if (iTransponderParameters.getIncludeIndex() != TriState.NOT_SPECIFIED) {
            sb.append(String.format("-ix%s", new Object[]{iTransponderParameters.getIncludeIndex().getArgument()}));
        }
        if (b.a(iTransponderParameters.getAccessPassword())) {
            return;
        }
        if (iTransponderParameters.getAccessPassword().length() != 8) {
            throw new IllegalArgumentException(String.format(Constants.ERROR_LOCALE, "Invalid access password length (%s). Must be 8 Ascii hex chars.", new Object[]{Integer.valueOf(iTransponderParameters.getAccessPassword().length())}));
        }
        sb.append(String.format("-ap%s", new Object[]{iTransponderParameters.getAccessPassword()}));
    }

    public static boolean parseParameterFor(ITransponderParameters iTransponderParameters, String str) {
        if (str.length() > 0) {
            switch (str.charAt(0)) {
                case 'a':
                    if (str.length() > 1) {
                        switch (str.charAt(1)) {
                            case 'p':
                                iTransponderParameters.setAccessPassword(str.substring(2).trim());
                                return true;
                        }
                    }
                    break;
                case 'c':
                    iTransponderParameters.setIncludeChecksum(TriState.Parse(str.substring(1)));
                    return true;
                case 'e':
                    iTransponderParameters.setIncludePC(TriState.Parse(str.substring(1)));
                    return true;
                case 'i':
                    if (str.length() > 1) {
                        switch (str.charAt(1)) {
                            case 'x':
                                iTransponderParameters.setIncludeIndex(TriState.Parse(str.substring(2)));
                                return true;
                        }
                    }
                    break;
                case 'r':
                    iTransponderParameters.setIncludeTransponderRssi(TriState.Parse(str.substring(1)));
                    return true;
            }
        }
        return false;
    }

    public static void setDefaultParametersFor(ITransponderParameters iTransponderParameters) {
        iTransponderParameters.setIncludeChecksum(TriState.NOT_SPECIFIED);
        iTransponderParameters.setIncludePC(TriState.NOT_SPECIFIED);
        iTransponderParameters.setIncludeTransponderRssi(TriState.NOT_SPECIFIED);
        iTransponderParameters.setIncludeIndex(TriState.NOT_SPECIFIED);
        iTransponderParameters.setAccessPassword("");
    }
}
