package com.app.apprfid.asciiprotocol.parameters;



import com.app.apprfid.asciiprotocol.Constants;
import com.app.apprfid.asciiprotocol.enumerations.Databank;
import com.app.apprfid.b.a;

public final class DatabankParameters {
    public static void appendToCommandLine(IDatabankParameters iDatabankParameters, StringBuilder sb) {
        if (iDatabankParameters.getBank() != Databank.NOT_SPECIFIED) {
            sb.append(String.format(Constants.COMMAND_LOCALE, "-db%s", new Object[]{iDatabankParameters.getBank().getArgument()}));
        }
        if (iDatabankParameters.getOffset() >= 0) {
            sb.append(String.format(Constants.COMMAND_LOCALE, "-do%04x", new Object[]{Integer.valueOf(iDatabankParameters.getOffset())}));
        }
        if (iDatabankParameters.getLength() >= 0) {
            sb.append(String.format(Constants.COMMAND_LOCALE, "-dl%02x", new Object[]{Integer.valueOf(iDatabankParameters.getLength())}));
        }
        if (iDatabankParameters.getData() == null) {
            return;
        }
        if (iDatabankParameters.getData().length > 64) {
            throw new IllegalArgumentException(String.format(Constants.COMMAND_LOCALE, "Data block too big (%d bytes) should be up to 32 words.", new Object[]{Integer.valueOf(iDatabankParameters.getData().length)}));
        }
        sb.append("-da");
        sb.append(a.a(iDatabankParameters.getData()));
    }

    public static boolean parseParameterFor(IDatabankParameters iDatabankParameters, String str) {
        if (str.length() >= 2 && str.charAt(0) == 'd') {
            switch (str.charAt(1)) {
                case 'a':
                    return true;
                case 'b':
                    iDatabankParameters.setBank(Databank.Parse(str.substring(2)));
                    return true;
                case 'l':
                    iDatabankParameters.setLength(Integer.parseInt(str.substring(2).trim(), 16));
                    return true;
                case 'o':
                    iDatabankParameters.setOffset(Integer.parseInt(str.substring(2).trim(), 16));
                    return true;
            }
        }
        return false;
    }

    public static void setDefaultParametersFor(IDatabankParameters iDatabankParameters) {
        iDatabankParameters.setBank(Databank.NOT_SPECIFIED);
        iDatabankParameters.setOffset(-1);
        iDatabankParameters.setLength(-1);
    }
}
