package com.app.apprfid.asciiprotocol.parameters;

import com.app.apprfid.asciiprotocol.Constants;
import com.app.apprfid.asciiprotocol.enumerations.Databank;

public final class SelectMaskParameters {
    public static void appendToCommandLine(ISelectMaskParameters iSelectMaskParameters, StringBuilder sb) {
        if (iSelectMaskParameters.getSelectBank() != Databank.NOT_SPECIFIED) {
            sb.append(String.format("-sb%s", new Object[]{iSelectMaskParameters.getSelectBank().getArgument()}));
        }
        if (iSelectMaskParameters.getSelectOffset() >= 0) {
            sb.append(String.format("-so%04x", new Object[]{Integer.valueOf(iSelectMaskParameters.getSelectOffset())}));
        }
        if (iSelectMaskParameters.getSelectLength() >= 0) {
            sb.append(String.format("-sl%02x", new Object[]{Integer.valueOf(iSelectMaskParameters.getSelectLength())}));
        }
        if (iSelectMaskParameters.getSelectData() == null) {
            return;
        }
        if (iSelectMaskParameters.getSelectData().length() > 64) {
            throw new IllegalArgumentException(String.format(Constants.ERROR_LOCALE, "Data block too big (%d ascii hex bytes) should be up to 32 ascii hex .", new Object[]{Integer.valueOf(iSelectMaskParameters.getSelectData().length())}));
        }
        sb.append(String.format("-sd%s", new Object[]{iSelectMaskParameters.getSelectData()}));
    }

    public static boolean parseParameterFor(ISelectMaskParameters iSelectMaskParameters, String str) {
        if (str.length() >= 2 && str.charAt(0) == 's') {
            switch (str.charAt(1)) {
                case 'b':
                    iSelectMaskParameters.setSelectBank(Databank.Parse(str.substring(2)));
                    return true;
                case 'd':
                    return true;
                case 'l':
                    iSelectMaskParameters.setSelectLength(Integer.parseInt(String.format(Constants.COMMAND_LOCALE, str.substring(2).trim(), new Object[0]), 16));
                    return true;
                case 'o':
                    iSelectMaskParameters.setSelectOffset(Integer.parseInt(String.format(Constants.COMMAND_LOCALE, str.substring(2).trim(), new Object[0]), 16));
                    return true;
            }
        }
        return false;
    }

    public static void setDefaultParametersFor(ISelectMaskParameters iSelectMaskParameters) {
        iSelectMaskParameters.setSelectBank(Databank.NOT_SPECIFIED);
        iSelectMaskParameters.setSelectData(null);
        iSelectMaskParameters.setSelectOffset(-1);
        iSelectMaskParameters.setSelectLength(-1);
    }
}
