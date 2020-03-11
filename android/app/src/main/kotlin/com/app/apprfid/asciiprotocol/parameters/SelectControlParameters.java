package com.app.apprfid.asciiprotocol.parameters;

import com.app.apprfid.asciiprotocol.enumerations.SelectAction;
import com.app.apprfid.asciiprotocol.enumerations.SelectTarget;

public final class SelectControlParameters {
    public static void appendToCommandLine(ISelectControlParameters iSelectControlParameters, StringBuilder sb) {
        if (iSelectControlParameters.getSelectTarget() != SelectTarget.NOT_SPECIFIED) {
            sb.append(String.format("-st%s", new Object[]{iSelectControlParameters.getSelectTarget().getArgument()}));
        }
        if (iSelectControlParameters.getSelectAction() != SelectAction.NOT_SPECIFIED) {
            sb.append(String.format("-sa%s", new Object[]{iSelectControlParameters.getSelectAction().getArgument()}));
        }
    }

    public static boolean parseParameterFor(ISelectControlParameters iSelectControlParameters, String str) {
        if (str.length() >= 2 && str.charAt(0) == 's') {
            switch (str.charAt(1)) {
                case 'a':
                    iSelectControlParameters.setSelectAction(SelectAction.Parse(str.substring(2)));
                    return true;
                case 't':
                    iSelectControlParameters.setSelectTarget(SelectTarget.Parse(str.substring(2)));
                    return true;
            }
        }
        return false;
    }

    public static void setDefaultParametersFor(ISelectControlParameters iSelectControlParameters) {
        iSelectControlParameters.setSelectAction(SelectAction.NOT_SPECIFIED);
        iSelectControlParameters.setSelectTarget(SelectTarget.NOT_SPECIFIED);
    }
}
