package com.app.apprfid.asciiprotocol.commands;

import com.app.apprfid.asciiprotocol.enumerations.Databank;
import com.app.apprfid.asciiprotocol.enumerations.QAlgorithm;
import com.app.apprfid.asciiprotocol.enumerations.QuerySelect;
import com.app.apprfid.asciiprotocol.enumerations.QuerySession;
import com.app.apprfid.asciiprotocol.enumerations.QueryTarget;
import com.app.apprfid.asciiprotocol.enumerations.SelectAction;
import com.app.apprfid.asciiprotocol.enumerations.SelectTarget;
import com.app.apprfid.asciiprotocol.enumerations.TriState;
import com.app.apprfid.asciiprotocol.parameters.AntennaParameters;
import com.app.apprfid.asciiprotocol.parameters.CommandParameters;
import com.app.apprfid.asciiprotocol.parameters.IAntennaParameters;
import com.app.apprfid.asciiprotocol.parameters.ICommandParameters;
import com.app.apprfid.asciiprotocol.parameters.IQAlgorithmParameters;
import com.app.apprfid.asciiprotocol.parameters.IQueryParameters;
import com.app.apprfid.asciiprotocol.parameters.IResponseParameters;
import com.app.apprfid.asciiprotocol.parameters.ISelectControlParameters;
import com.app.apprfid.asciiprotocol.parameters.ISelectMaskParameters;
import com.app.apprfid.asciiprotocol.parameters.ITransponderParameters;
import com.app.apprfid.asciiprotocol.parameters.QAlgorithmParameters;
import com.app.apprfid.asciiprotocol.parameters.QueryParameters;
import com.app.apprfid.asciiprotocol.parameters.ResponseParameters;
import com.app.apprfid.asciiprotocol.parameters.SelectControlParameters;
import com.app.apprfid.asciiprotocol.parameters.SelectMaskParameters;
import com.app.apprfid.asciiprotocol.parameters.TransponderParameters;
import com.app.apprfid.asciiprotocol.responders.AsciiSelfResponderCommandBase;
import com.app.apprfid.asciiprotocol.responders.ITransponderReceivedDelegate;
import com.app.apprfid.asciiprotocol.responders.TransponderData;
import com.app.apprfid.asciiprotocol.responders.TransponderResponder;

public class InventoryCommand extends AsciiSelfResponderCommandBase implements IAntennaParameters, ICommandParameters, IQAlgorithmParameters, IQueryParameters, IResponseParameters, ISelectControlParameters, ISelectMaskParameters, ITransponderParameters, ITransponderReceivedDelegate {
    private ITransponderReceivedDelegate mTransponderReceivedDelegate = null;
    private String privateAccessPassword;
    private TriState privateFilterStrongest = TriState.NOT_SPECIFIED;
    private TriState privateIncludeChecksum;
    private TriState privateIncludeDateTime;
    private TriState privateIncludeEpc = TriState.NOT_SPECIFIED;
    private TriState privateIncludeIndex;
    private TriState privateIncludePC;
    private TriState privateIncludeTransponderRssi;
    private TriState privateInventoryOnly = TriState.NOT_SPECIFIED;
    private int privateOutputPower;
    private QAlgorithm privateQAlgorithm;
    private int privateQValue;
    private QuerySelect privateQuerySelect;
    private QuerySession privateQuerySession;
    private QueryTarget privateQueryTarget;
    private TriState privateReadParameters;
    private TriState privateResetParameters;
    private SelectAction privateSelectAction;
    private Databank privateSelectBank;
    private String privateSelectData;
    private int privateSelectLength;
    private int privateSelectOffset;
    private SelectTarget privateSelectTarget;
    private TriState privateTakeNoAction;
    private TriState privateUseAlert;
    private TriState privateUseFastId = TriState.NOT_SPECIFIED;
    private TriState privateUseTagFocus = TriState.NOT_SPECIFIED;
    private TransponderResponder transponderResponder = new TransponderResponder();

    public InventoryCommand() {
        super(".iv");
        AntennaParameters.setDefaultParametersFor(this);
        CommandParameters.setDefaultParametersFor(this);
        QAlgorithmParameters.setDefaultParametersFor(this);
        QueryParameters.setDefaultParametersFor(this);
        ResponseParameters.setDefaultParametersFor(this);
        SelectControlParameters.setDefaultParametersFor(this);
        SelectMaskParameters.setDefaultParametersFor(this);
        TransponderParameters.setDefaultParametersFor(this);
        this.transponderResponder.setTransponderReceivedHandler(this);
    }

    public static InventoryCommand synchronousCommand() {
        InventoryCommand inventoryCommand = new InventoryCommand();
        inventoryCommand.setSynchronousCommandResponder(inventoryCommand);
        return inventoryCommand;
    }

    /* access modifiers changed from: protected */
    public void buildCommandLine(StringBuilder sb) {
        super.buildCommandLine(sb);
        CommandParameters.appendToCommandLine(this, sb);
        TransponderParameters.appendToCommandLine(this, sb);
        ResponseParameters.appendToCommandLine(this, sb);
        AntennaParameters.appendToCommandLine(this, sb);
        QueryParameters.appendToCommandLine(this, sb);
        QAlgorithmParameters.appendToCommandLine(this, sb);
        SelectControlParameters.appendToCommandLine(this, sb);
        SelectMaskParameters.appendToCommandLine(this, sb);
        if (getIncludeEpc() != TriState.NOT_SPECIFIED) {
            sb.append(String.format("-ie%s", new Object[]{getIncludeEpc().getArgument()}));
        }
        if (getInventoryOnly() != TriState.NOT_SPECIFIED) {
            sb.append(String.format("-io%s", new Object[]{getInventoryOnly().getArgument()}));
        }
        if (getUseTagFocus() != TriState.NOT_SPECIFIED) {
            sb.append(String.format("-tf%s", new Object[]{getUseTagFocus().getArgument()}));
        }
        if (getUsefastId() != TriState.NOT_SPECIFIED) {
            sb.append(String.format("-fi%s", new Object[]{getUsefastId().getArgument()}));
        }
        if (getFilterStrongest() != TriState.NOT_SPECIFIED) {
            sb.append(String.format("-fs%s", new Object[]{getFilterStrongest().getArgument()}));
        }
    }

    public void clearLastResponse() {
        super.clearLastResponse();
        this.transponderResponder.clearLastResponse();
    }

    public final String getAccessPassword() {
        return this.privateAccessPassword;
    }

    public final TriState getFilterStrongest() {
        return this.privateFilterStrongest;
    }

    public final TriState getIncludeChecksum() {
        return this.privateIncludeChecksum;
    }

    public final TriState getIncludeDateTime() {
        return this.privateIncludeDateTime;
    }

    public final TriState getIncludeEpc() {
        return this.privateIncludeEpc;
    }

    public final TriState getIncludeIndex() {
        return this.privateIncludeIndex;
    }

    public final TriState getIncludePC() {
        return this.privateIncludePC;
    }

    public final TriState getIncludeTransponderRssi() {
        return this.privateIncludeTransponderRssi;
    }

    public final TriState getInventoryOnly() {
        return this.privateInventoryOnly;
    }

    public final int getOutputPower() {
        return this.privateOutputPower;
    }

    public final QAlgorithm getQAlgorithm() {
        return this.privateQAlgorithm;
    }

    public final int getQValue() {
        return this.privateQValue;
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

    public final TriState getReadParameters() {
        return this.privateReadParameters;
    }

    public final TriState getResetParameters() {
        return this.privateResetParameters;
    }

    public final SelectAction getSelectAction() {
        return this.privateSelectAction;
    }

    public final Databank getSelectBank() {
        return this.privateSelectBank;
    }

    public final String getSelectData() {
        return this.privateSelectData;
    }

    public final int getSelectLength() {
        return this.privateSelectLength;
    }

    public final int getSelectOffset() {
        return this.privateSelectOffset;
    }

    public final SelectTarget getSelectTarget() {
        return this.privateSelectTarget;
    }

    public final TriState getTakeNoAction() {
        return this.privateTakeNoAction;
    }

    public final ITransponderReceivedDelegate getTransponderReceivedDelegate() {
        return this.mTransponderReceivedDelegate;
    }

    public final TriState getUseAlert() {
        return this.privateUseAlert;
    }

    public final TriState getUseTagFocus() {
        return this.privateUseTagFocus;
    }

    public final TriState getUsefastId() {
        return this.privateUseFastId;
    }

    public final boolean implementsReadParameters() {
        return true;
    }

    public final boolean implementsResetParameters() {
        return true;
    }

    public final boolean implementsTakeNoAction() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean processReceivedLine(String str, String str2, String str3, boolean z) {
        if (super.processReceivedLine(str, str2, str3, z)) {
            return true;
        }
        if (!getResponseStarted() || !this.transponderResponder.processReceivedLine(str2, str3)) {
            return false;
        }
        appendToResponse(str);
        return true;
    }

    /* access modifiers changed from: protected */
    public void responseDidFinish(boolean z) {
        this.transponderResponder.transponderComplete(false);
        super.responseDidFinish(z);
    }

    /* access modifiers changed from: protected */
    public boolean responseDidReceiveParameter(String str) {
        if (!AntennaParameters.parseParameterFor(this, str) && !ResponseParameters.parseParameterFor(this, str) && !TransponderParameters.parseParameterFor(this, str) && !QueryParameters.parseParameterFor(this, str) && !QAlgorithmParameters.parseParameterFor(this, str) && !SelectControlParameters.parseParameterFor(this, str) && !SelectMaskParameters.parseParameterFor(this, str) && !CommandParameters.parseParameterFor(this, str)) {
            if (str.length() >= 2 && str.startsWith("io")) {
                setInventoryOnly(TriState.Parse(str.substring(2)));
            } else if (str.length() >= 2 && str.startsWith("ie")) {
                setIncludeEpc(TriState.Parse(str.substring(2)));
            } else if (str.length() >= 2 && str.startsWith("fi")) {
                setUsefastId(TriState.Parse(str.substring(2)));
            } else if (str.length() >= 2 && str.startsWith("fs")) {
                setFilterStrongest(TriState.Parse(str.substring(2)));
            } else if (str.length() < 2 || !str.startsWith("tf")) {
                return super.responseDidReceiveParameter(str);
            } else {
                setUseTagFocus(TriState.Parse(str.substring(2)));
            }
        }
        return true;
    }

    public final void setAccessPassword(String str) {
        this.privateAccessPassword = str;
    }

    public final void setFilterStrongest(TriState triState) {
        this.privateFilterStrongest = triState;
    }

    public final void setIncludeChecksum(TriState triState) {
        this.privateIncludeChecksum = triState;
    }

    public final void setIncludeDateTime(TriState triState) {
        this.privateIncludeDateTime = triState;
    }

    public final void setIncludeEpc(TriState triState) {
        this.privateIncludeEpc = triState;
    }

    public final void setIncludeIndex(TriState triState) {
        this.privateIncludeIndex = triState;
    }

    public final void setIncludePC(TriState triState) {
        this.privateIncludePC = triState;
    }

    public final void setIncludeTransponderRssi(TriState triState) {
        this.privateIncludeTransponderRssi = triState;
    }

    public final void setInventoryOnly(TriState triState) {
        this.privateInventoryOnly = triState;
    }

    public final void setOutputPower(int i) {
        this.privateOutputPower = i;
    }

    public final void setQAlgorithm(QAlgorithm qAlgorithm) {
        this.privateQAlgorithm = qAlgorithm;
    }

    public final void setQValue(int i) {
        this.privateQValue = i;
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

    public final void setReadParameters(TriState triState) {
        this.privateReadParameters = triState;
    }

    public final void setResetParameters(TriState triState) {
        this.privateResetParameters = triState;
    }

    public final void setSelectAction(SelectAction selectAction) {
        this.privateSelectAction = selectAction;
    }

    public final void setSelectBank(Databank databank) {
        this.privateSelectBank = databank;
    }

    public final void setSelectData(String str) {
        this.privateSelectData = str;
    }

    public final void setSelectLength(int i) {
        this.privateSelectLength = i;
    }

    public final void setSelectOffset(int i) {
        this.privateSelectOffset = i;
    }

    public final void setSelectTarget(SelectTarget selectTarget) {
        this.privateSelectTarget = selectTarget;
    }

    public final void setTakeNoAction(TriState triState) {
        this.privateTakeNoAction = triState;
    }

    public final void setTransponderReceivedDelegate(ITransponderReceivedDelegate iTransponderReceivedDelegate) {
        this.mTransponderReceivedDelegate = iTransponderReceivedDelegate;
    }

    public final void setUseAlert(TriState triState) {
        this.privateUseAlert = triState;
    }

    public final void setUseTagFocus(TriState triState) {
        this.privateUseTagFocus = triState;
    }

    public final void setUsefastId(TriState triState) {
        this.privateUseFastId = triState;
    }

    public void transponderReceived(TransponderData transponderData, boolean z) {
        if (this.mTransponderReceivedDelegate != null) {
            this.mTransponderReceivedDelegate.transponderReceived(transponderData, z);
        }
    }
}
