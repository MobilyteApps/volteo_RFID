package com.app.apprfid.asciiprotocol.commands;

import com.app.apprfid.asciiprotocol.enumerations.Databank;
import com.app.apprfid.asciiprotocol.enumerations.SelectAction;
import com.app.apprfid.asciiprotocol.enumerations.SelectTarget;
import com.app.apprfid.asciiprotocol.enumerations.TriState;
import com.app.apprfid.asciiprotocol.parameters.AntennaParameters;
import com.app.apprfid.asciiprotocol.parameters.CommandParameters;
import com.app.apprfid.asciiprotocol.parameters.IAntennaParameters;
import com.app.apprfid.asciiprotocol.parameters.ICommandParameters;
import com.app.apprfid.asciiprotocol.parameters.ISelectControlParameters;
import com.app.apprfid.asciiprotocol.parameters.ISelectMaskParameters;
import com.app.apprfid.asciiprotocol.parameters.SelectControlParameters;
import com.app.apprfid.asciiprotocol.parameters.SelectMaskParameters;
import com.app.apprfid.asciiprotocol.responders.AsciiSelfResponderCommandBase;

public class TransponderSelectCommand extends AsciiSelfResponderCommandBase implements IAntennaParameters, ICommandParameters, ISelectControlParameters, ISelectMaskParameters {
    private int privateOutputPower;
    private TriState privateReadParameters;
    private TriState privateResetParameters;
    private SelectAction privateSelectAction;
    private Databank privateSelectBank;
    private String privateSelectData;
    private int privateSelectLength;
    private int privateSelectOffset;
    private SelectTarget privateSelectTarget;
    private TriState privateTakeNoAction;

    protected TransponderSelectCommand() {
        super(".ts");
        CommandParameters.setDefaultParametersFor(this);
        AntennaParameters.setDefaultParametersFor(this);
        SelectControlParameters.setDefaultParametersFor(this);
        SelectMaskParameters.setDefaultParametersFor(this);
    }

    public static TransponderSelectCommand synchronousCommand() {
        TransponderSelectCommand transponderSelectCommand = new TransponderSelectCommand();
        transponderSelectCommand.setSynchronousCommandResponder(transponderSelectCommand);
        return transponderSelectCommand;
    }

    /* access modifiers changed from: protected */
    public void buildCommandLine(StringBuilder sb) {
        super.buildCommandLine(sb);
        CommandParameters.appendToCommandLine(this, sb);
        AntennaParameters.appendToCommandLine(this, sb);
        SelectControlParameters.appendToCommandLine(this, sb);
        SelectMaskParameters.appendToCommandLine(this, sb);
    }

    public final int getOutputPower() {
        return this.privateOutputPower;
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
    public boolean responseDidReceiveParameter(String str) {
        if (CommandParameters.parseParameterFor(this, str) || AntennaParameters.parseParameterFor(this, str) || SelectControlParameters.parseParameterFor(this, str) || SelectMaskParameters.parseParameterFor(this, str)) {
            return true;
        }
        return super.responseDidReceiveParameter(str);
    }

    public final void setOutputPower(int i) {
        this.privateOutputPower = i;
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
}
