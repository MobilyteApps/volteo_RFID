package com.app.apprfid.asciiprotocol.parameters;

import com.app.apprfid.asciiprotocol.Constants;
import com.app.apprfid.asciiprotocol.enumerations.QAlgorithm;

public final class QAlgorithmParameters {
    public static void appendToCommandLine(IQAlgorithmParameters iQAlgorithmParameters, StringBuilder sb) {
        appendToCommandLine(iQAlgorithmParameters, sb, true);
    }

    public static void appendToCommandLine(IQAlgorithmParameters iQAlgorithmParameters, StringBuilder sb, boolean z) {
        if (z && iQAlgorithmParameters.getQAlgorithm() != QAlgorithm.NOT_SPECIFIED) {
            sb.append(String.format(Constants.COMMAND_LOCALE, "-qa%s", new Object[]{iQAlgorithmParameters.getQAlgorithm().getArgument()}));
        }
        if (iQAlgorithmParameters.getQValue() < 0) {
            return;
        }
        if (iQAlgorithmParameters.getQValue() >= 16) {
            throw new IllegalArgumentException(String.format(Constants.ERROR_LOCALE, "Q value argument (%s) should be in the range [%s - %s].", new Object[]{Integer.valueOf(iQAlgorithmParameters.getQValue()), Integer.valueOf(0), Integer.valueOf(15)}));
        }
        sb.append(String.format(Constants.COMMAND_LOCALE, "-qv%d", new Object[]{Integer.valueOf(iQAlgorithmParameters.getQValue())}));
    }

    public static boolean parseParameterFor(IQAlgorithmParameters iQAlgorithmParameters, String str) {
        if (str.length() > 2 && str.charAt(0) == 'q') {
            switch (str.charAt(1)) {
                case 'a':
                    iQAlgorithmParameters.setQAlgorithm(QAlgorithm.Parse(str.substring(2)));
                    return true;
                case 'v':
                    iQAlgorithmParameters.setQValue(Integer.parseInt(String.format(Constants.COMMAND_LOCALE, str.substring(2).trim(), new Object[0])));
                    return true;
            }
        }
        return false;
    }

    public static void setDefaultParametersFor(IQAlgorithmParameters iQAlgorithmParameters) {
        iQAlgorithmParameters.setQAlgorithm(QAlgorithm.NOT_SPECIFIED);
        iQAlgorithmParameters.setQValue(-1);
    }
}
