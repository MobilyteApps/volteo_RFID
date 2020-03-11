package com.app.apprfid.asciiprotocol.commands;

import com.app.apprfid.asciiprotocol.enumerations.ImpinjBlockWriteMode;
import com.app.apprfid.asciiprotocol.enumerations.QtMode;
import com.app.apprfid.asciiprotocol.enumerations.QuerySelect;
import com.app.apprfid.asciiprotocol.enumerations.QuerySession;
import com.app.apprfid.asciiprotocol.enumerations.QueryTarget;
import com.app.apprfid.asciiprotocol.enumerations.SelectAction;
import com.app.apprfid.asciiprotocol.enumerations.SelectTarget;
import com.app.apprfid.asciiprotocol.enumerations.TriState;
import com.app.apprfid.asciiprotocol.enumerations.WriteMode;
import com.app.apprfid.asciiprotocol.parameters.IQAlgorithmParameters;
import com.app.apprfid.asciiprotocol.parameters.IQueryParameters;
import com.app.apprfid.asciiprotocol.parameters.ISelectControlParameters;
import com.app.apprfid.asciiprotocol.parameters.QAlgorithmParameters;
import com.app.apprfid.asciiprotocol.parameters.QueryParameters;
import com.app.apprfid.asciiprotocol.parameters.SelectControlParameters;
import com.app.apprfid.asciiprotocol.responders.ITransponderReceivedDelegate;
import com.app.apprfid.asciiprotocol.responders.TransponderData;

public class WriteTransponderCommand extends TransponderMemoryCommandBase implements IQAlgorithmParameters, IQueryParameters, ISelectControlParameters {
    private ITransponderReceivedDelegate mTransponderReceivedDelegate = null;
    private ImpinjBlockWriteMode privateImpinjBlockWriteMode = ImpinjBlockWriteMode.NOT_SPECIFIED;
    private TriState privateInventoryOnly = TriState.NOT_SPECIFIED;
    private QtMode privateQtMode = QtMode.NOT_SPECIFIED;
    private QuerySelect privateQuerySelect;
    private QuerySession privateQuerySession;
    private QueryTarget privateQueryTarget;
    private SelectAction privateSelectAction;
    private SelectTarget privateSelectTarget;
    private WriteMode privateWriteMode = WriteMode.NOT_SPECIFIED;

    public WriteTransponderCommand() {
        super(".wr");
        SelectControlParameters.setDefaultParametersFor(this);
        QAlgorithmParameters.setDefaultParametersFor(this);
        QueryParameters.setDefaultParametersFor(this);
    }

    public static WriteTransponderCommand synchronousCommand() {
        WriteTransponderCommand writeTransponderCommand = new WriteTransponderCommand();
        writeTransponderCommand.setSynchronousCommandResponder(writeTransponderCommand);
        return writeTransponderCommand;
    }

    /* access modifiers changed from: protected */
    public void buildCommandLine(StringBuilder sb) {
        super.buildCommandLine(sb);
        SelectControlParameters.appendToCommandLine(this, sb);
        QueryParameters.appendToCommandLine(this, sb);
        QAlgorithmParameters.appendToCommandLine(this, sb, false);
        if (getInventoryOnly() != TriState.NOT_SPECIFIED) {
            sb.append(String.format("-io%s", new Object[]{getInventoryOnly().getArgument()}));
        }
        if (getWriteMode() != WriteMode.NOT_SPECIFIED) {
            sb.append(String.format("-wm%s", new Object[]{getWriteMode().getArgument()}));
        }
        if (getQtMode() != QtMode.NOT_SPECIFIED) {
            sb.append(String.format("-qm%s", new Object[]{getQtMode().getArgument()}));
        }
        if (getImpinjBlockWriteMode() != ImpinjBlockWriteMode.NOT_SPECIFIED) {
            sb.append(String.format("-wx%s", new Object[]{getImpinjBlockWriteMode().getArgument()}));
        }
    }

    public final ImpinjBlockWriteMode getImpinjBlockWriteMode() {
        return this.privateImpinjBlockWriteMode;
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

    public final WriteMode getWriteMode() {
        return this.privateWriteMode;
    }

    /* access modifiers changed from: protected */
    public boolean responseDidReceiveParameter(String str) {
        if (!SelectControlParameters.parseParameterFor(this, str) && !QueryParameters.parseParameterFor(this, str) && !QAlgorithmParameters.parseParameterFor(this, str)) {
            if (str.length() >= 2 && str.startsWith("io")) {
                setInventoryOnly(TriState.Parse(str.substring(2)));
            } else if (str.length() >= 2 && str.startsWith("wm")) {
                setWriteMode(WriteMode.Parse(str.substring(2)));
            } else if (str.length() >= 2 && str.startsWith("wx")) {
                setImpinjBlockWriteMode(ImpinjBlockWriteMode.Parse(str.substring(2)));
            } else if (str.length() < 2 || !str.startsWith("qm")) {
                return super.responseDidReceiveParameter(str);
            } else {
                setQtMode(QtMode.Parse(str.substring(2)));
            }
        }
        return true;
    }

    public final void setImpinjBlockWriteMode(ImpinjBlockWriteMode impinjBlockWriteMode) {
        this.privateImpinjBlockWriteMode = impinjBlockWriteMode;
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

    public final void setWriteMode(WriteMode writeMode) {
        this.privateWriteMode = writeMode;
    }

    public void transponderReceived(TransponderData transponderData, boolean z) {
        if (this.mTransponderReceivedDelegate != null) {
            this.mTransponderReceivedDelegate.transponderReceived(transponderData, z);
        }
    }
}
