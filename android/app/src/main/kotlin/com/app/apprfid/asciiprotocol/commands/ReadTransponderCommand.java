package com.app.apprfid.asciiprotocol.commands;

import com.app.apprfid.asciiprotocol.enumerations.QtMode;
import com.app.apprfid.asciiprotocol.enumerations.QuerySelect;
import com.app.apprfid.asciiprotocol.enumerations.QuerySession;
import com.app.apprfid.asciiprotocol.enumerations.QueryTarget;
import com.app.apprfid.asciiprotocol.enumerations.SelectAction;
import com.app.apprfid.asciiprotocol.enumerations.SelectTarget;
import com.app.apprfid.asciiprotocol.enumerations.TriState;
import com.app.apprfid.asciiprotocol.parameters.IQAlgorithmParameters;
import com.app.apprfid.asciiprotocol.parameters.IQueryParameters;
import com.app.apprfid.asciiprotocol.parameters.ISelectControlParameters;
import com.app.apprfid.asciiprotocol.parameters.QAlgorithmParameters;
import com.app.apprfid.asciiprotocol.parameters.QueryParameters;
import com.app.apprfid.asciiprotocol.parameters.SelectControlParameters;
import com.app.apprfid.asciiprotocol.responders.ITransponderReceivedDelegate;
import com.app.apprfid.asciiprotocol.responders.TransponderData;

public class ReadTransponderCommand extends TransponderMemoryCommandBase implements IQAlgorithmParameters, IQueryParameters, ISelectControlParameters {
    private ITransponderReceivedDelegate mTransponderReceivedDelegate = null;
    private TriState privateFilterStrongest = TriState.NOT_SPECIFIED;
    private TriState privateIncludeEpc = TriState.NOT_SPECIFIED;
    private TriState privateInventoryOnly = TriState.NOT_SPECIFIED;
    private QtMode privateQtMode = QtMode.NOT_SPECIFIED;
    private QuerySelect privateQuerySelect;
    private QuerySession privateQuerySession;
    private QueryTarget privateQueryTarget;
    private SelectAction privateSelectAction;
    private SelectTarget privateSelectTarget;

    public ReadTransponderCommand() {
        super(".rd");
        SelectControlParameters.setDefaultParametersFor(this);
        QueryParameters.setDefaultParametersFor(this);
        QAlgorithmParameters.setDefaultParametersFor(this);
    }

    public static ReadTransponderCommand synchronousCommand() {
        ReadTransponderCommand readTransponderCommand = new ReadTransponderCommand();
        readTransponderCommand.setSynchronousCommandResponder(readTransponderCommand);
        return readTransponderCommand;
    }

    /* access modifiers changed from: protected */
    public void buildCommandLine(StringBuilder sb) {
        super.buildCommandLine(sb);
        SelectControlParameters.appendToCommandLine(this, sb);
        QueryParameters.appendToCommandLine(this, sb);
        QAlgorithmParameters.appendToCommandLine(this, sb, false);
        if (getIncludeEpc() != TriState.NOT_SPECIFIED) {
            sb.append(String.format("-ie%s", new Object[]{getIncludeEpc().getArgument()}));
        }
        if (getInventoryOnly() != TriState.NOT_SPECIFIED) {
            sb.append(String.format("-io%s", new Object[]{getInventoryOnly().getArgument()}));
        }
        if (getQtMode() != QtMode.NOT_SPECIFIED) {
            sb.append(String.format("-qm%s", new Object[]{getQtMode().getArgument()}));
        }
        if (getFilterStrongest() != TriState.NOT_SPECIFIED) {
            sb.append(String.format("-fs%s", new Object[]{getFilterStrongest().getArgument()}));
        }
    }

    public final TriState getFilterStrongest() {
        return this.privateFilterStrongest;
    }

    public final TriState getIncludeEpc() {
        return this.privateIncludeEpc;
    }

    public final TriState getInventoryOnly() {
        return this.privateInventoryOnly;
    }

    public QtMode getQtMode() {
        return this.privateQtMode;
    }

    public final QuerySelect getQuerySelect() {
        return this.privateQuerySelect;
    }

    public final QuerySession getQuerySession() {
        return this.privateQuerySession;
    }

    public final QueryTarget getQueryTarget() {
        return this.privateQueryTarget;
    }

    public final SelectAction getSelectAction() {
        return this.privateSelectAction;
    }

    public final SelectTarget getSelectTarget() {
        return this.privateSelectTarget;
    }

    public final ITransponderReceivedDelegate getTransponderReceivedDelegate() {
        return this.mTransponderReceivedDelegate;
    }

    /* access modifiers changed from: protected */
    public boolean responseDidReceiveParameter(String str) {
        if (!SelectControlParameters.parseParameterFor(this, str) && !QueryParameters.parseParameterFor(this, str) && !QAlgorithmParameters.parseParameterFor(this, str)) {
            if (str.length() >= 2 && str.startsWith("io")) {
                setInventoryOnly(TriState.Parse(str.substring(2)));
            } else if (str.length() >= 2 && str.startsWith("ie")) {
                setIncludeEpc(TriState.Parse(str.substring(2)));
            } else if (str.length() >= 2 && str.startsWith("qm")) {
                setQtMode(QtMode.Parse(str.substring(2)));
            } else if (str.length() < 2 || !str.startsWith("fs")) {
                return super.responseDidReceiveParameter(str);
            } else {
                setFilterStrongest(TriState.Parse(str.substring(2)));
            }
        }
        return true;
    }

    public final void setFilterStrongest(TriState triState) {
        this.privateFilterStrongest = triState;
    }

    public final void setIncludeEpc(TriState triState) {
        this.privateIncludeEpc = triState;
    }

    public final void setInventoryOnly(TriState triState) {
        this.privateInventoryOnly = triState;
    }

    public void setQtMode(QtMode qtMode) {
        this.privateQtMode = qtMode;
    }

    public final void setQuerySelect(QuerySelect querySelect) {
        this.privateQuerySelect = querySelect;
    }

    public final void setQuerySession(QuerySession querySession) {
        this.privateQuerySession = querySession;
    }

    public final void setQueryTarget(QueryTarget queryTarget) {
        this.privateQueryTarget = queryTarget;
    }

    public final void setSelectAction(SelectAction selectAction) {
        this.privateSelectAction = selectAction;
    }

    public final void setSelectTarget(SelectTarget selectTarget) {
        this.privateSelectTarget = selectTarget;
    }

    public final void setTransponderReceivedDelegate(ITransponderReceivedDelegate iTransponderReceivedDelegate) {
        this.mTransponderReceivedDelegate = iTransponderReceivedDelegate;
    }

    public void transponderReceived(TransponderData transponderData, boolean z) {
        if (this.mTransponderReceivedDelegate != null) {
            this.mTransponderReceivedDelegate.transponderReceived(transponderData, z);
        }
    }
}
