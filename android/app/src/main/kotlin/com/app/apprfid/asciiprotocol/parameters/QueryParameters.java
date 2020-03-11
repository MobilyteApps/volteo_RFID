package com.app.apprfid.asciiprotocol.parameters;

import com.app.apprfid.asciiprotocol.enumerations.QuerySelect;
import com.app.apprfid.asciiprotocol.enumerations.QuerySession;
import com.app.apprfid.asciiprotocol.enumerations.QueryTarget;

public final class QueryParameters {
    public static void appendToCommandLine(IQueryParameters iQueryParameters, StringBuilder sb) {
        if (iQueryParameters.getQuerySelect() != QuerySelect.NOT_SPECIFIED) {
            sb.append(String.format("-ql%s", new Object[]{iQueryParameters.getQuerySelect().getArgument()}));
        }
        if (iQueryParameters.getQuerySession() != QuerySession.NOT_SPECIFIED) {
            sb.append(String.format("-qs%s", new Object[]{iQueryParameters.getQuerySession().getArgument()}));
        }
        if (iQueryParameters.getQueryTarget() != QueryTarget.NOT_SPECIFIED) {
            sb.append(String.format("-qt%s", new Object[]{iQueryParameters.getQueryTarget().getArgument()}));
        }
    }

    public static boolean parseParameterFor(IQueryParameters iQueryParameters, String str) {
        if (str.length() > 2 && str.charAt(0) == 'q') {
            switch (str.charAt(1)) {
                case 'l':
                    iQueryParameters.setQuerySelect(QuerySelect.Parse(str.substring(2)));
                    return true;
                case 's':
                    iQueryParameters.setQuerySession(QuerySession.Parse(str.substring(2)));
                    return true;
                case 't':
                    iQueryParameters.setQueryTarget(QueryTarget.Parse(str.substring(2)));
                    return true;
            }
        }
        return false;
    }

    public static void setDefaultParametersFor(IQueryParameters iQueryParameters) {
        iQueryParameters.setQuerySelect(QuerySelect.NOT_SPECIFIED);
        iQueryParameters.setQuerySession(QuerySession.NOT_SPECIFIED);
        iQueryParameters.setQueryTarget(QueryTarget.NOT_SPECIFIED);
    }
}
