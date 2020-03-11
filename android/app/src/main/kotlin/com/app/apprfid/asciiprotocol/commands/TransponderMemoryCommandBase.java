package com.app.apprfid.asciiprotocol.commands;

import android.util.Log;

import com.app.apprfid.asciiprotocol.enumerations.Databank;
import com.app.apprfid.asciiprotocol.enumerations.QAlgorithm;
import com.app.apprfid.asciiprotocol.enumerations.TriState;
import com.app.apprfid.asciiprotocol.parameters.AntennaParameters;
import com.app.apprfid.asciiprotocol.parameters.CommandParameters;
import com.app.apprfid.asciiprotocol.parameters.DatabankParameters;
import com.app.apprfid.asciiprotocol.parameters.IAntennaParameters;
import com.app.apprfid.asciiprotocol.parameters.ICommandParameters;
import com.app.apprfid.asciiprotocol.parameters.IDatabankParameters;
import com.app.apprfid.asciiprotocol.parameters.IQAlgorithmParameters;
import com.app.apprfid.asciiprotocol.parameters.IResponseParameters;
import com.app.apprfid.asciiprotocol.parameters.ISelectMaskParameters;
import com.app.apprfid.asciiprotocol.parameters.ITransponderParameters;
import com.app.apprfid.asciiprotocol.parameters.QAlgorithmParameters;
import com.app.apprfid.asciiprotocol.parameters.ResponseParameters;
import com.app.apprfid.asciiprotocol.parameters.SelectMaskParameters;
import com.app.apprfid.asciiprotocol.parameters.TransponderParameters;
import com.app.apprfid.asciiprotocol.responders.AsciiSelfResponderCommandBase;
import com.app.apprfid.asciiprotocol.responders.ITransponderReceivedDelegate;
import com.app.apprfid.asciiprotocol.responders.TransponderData;
import com.app.apprfid.asciiprotocol.responders.TransponderResponder;

public abstract class TransponderMemoryCommandBase extends AsciiSelfResponderCommandBase implements IAntennaParameters, ICommandParameters, IDatabankParameters, IQAlgorithmParameters, IResponseParameters, ISelectMaskParameters, ITransponderParameters, ITransponderReceivedDelegate {
    private String privateAccessPassword;
    private Databank privateBank;
    private byte[] privateData;
    private TriState privateIncludeChecksum;
    private TriState privateIncludeDateTime;
    private TriState privateIncludeIndex;
    private TriState privateIncludePC;
    private TriState privateIncludeTransponderRssi;
    private int privateLength;
    private int privateOffset;
    private int privateOutputPower;
    private QAlgorithm privateQAlgorithm;
    private int privateQValue;
    private TriState privateReadParameters;
    private TriState privateResetParameters;
    private Databank privateSelectBank;
    private String privateSelectData;
    private int privateSelectLength;
    private int privateSelectOffset;
    private TriState privateTakeNoAction;
    private TriState privateUseAlert;
    private TransponderResponder transponderResponder = new TransponderResponder();

    protected TransponderMemoryCommandBase(String str) {
        super(str);
        CommandParameters.setDefaultParametersFor(this);
        DatabankParameters.setDefaultParametersFor(this);
        AntennaParameters.setDefaultParametersFor(this);
        ResponseParameters.setDefaultParametersFor(this);
        SelectMaskParameters.setDefaultParametersFor(this);
        TransponderParameters.setDefaultParametersFor(this);
        QAlgorithmParameters.setDefaultParametersFor(this);
        this.transponderResponder.setTransponderReceivedHandler(this);
    }

    /* access modifiers changed from: protected */
    public void buildCommandLine(StringBuilder sb) {
        super.buildCommandLine(sb);
        CommandParameters.appendToCommandLine(this, sb);
        AntennaParameters.appendToCommandLine(this, sb);
        TransponderParameters.appendToCommandLine(this, sb);
        ResponseParameters.appendToCommandLine(this, sb);
        DatabankParameters.appendToCommandLine(this, sb);
        SelectMaskParameters.appendToCommandLine(this, sb);
    }

    public void clearLastResponse() {
        super.clearLastResponse();
        this.transponderResponder.clearLastResponse();
    }

    public final String getAccessPassword() {
        return this.privateAccessPassword;
    }

    public final Databank getBank() {
        return this.privateBank;
    }

    public final byte[] getData() {
        return this.privateData;
    }

    public final TriState getIncludeChecksum() {
        return this.privateIncludeChecksum;
    }

    public final TriState getIncludeDateTime() {
        return this.privateIncludeDateTime;
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

    public final int getLength() {
        return this.privateLength;
    }

    public final int getOffset() {
        return this.privateOffset;
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

    public final TriState getReadParameters() {
        return this.privateReadParameters;
    }

    public final TriState getResetParameters() {
        return this.privateResetParameters;
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

    public final TriState getTakeNoAction() {
        return this.privateTakeNoAction;
    }

    public final TriState getUseAlert() {
        return this.privateUseAlert;
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
        if (AntennaParameters.parseParameterFor(this, str) || ResponseParameters.parseParameterFor(this, str) || TransponderParameters.parseParameterFor(this, str) || DatabankParameters.parseParameterFor(this, str) || SelectMaskParameters.parseParameterFor(this, str) || CommandParameters.parseParameterFor(this, str)) {
            return true;
        }
        return super.responseDidReceiveParameter(str);
    }

    public final void setAccessPassword(String str) {
        this.privateAccessPassword = str;
    }

    public final void setBank(Databank databank) {
        this.privateBank = databank;
    }

    public final void setData(byte[] bArr) {
        this.privateData = bArr;
    }

    public final void setIncludeChecksum(TriState triState) {
        this.privateIncludeChecksum = triState;
    }

    public final void setIncludeDateTime(TriState triState) {
        this.privateIncludeDateTime = triState;
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

    public final void setLength(int i) {
        this.privateLength = i;
    }

    public final void setOffset(int i) {
        this.privateOffset = i;
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

    public final void setReadParameters(TriState triState) {
        this.privateReadParameters = triState;
    }

    public final void setResetParameters(TriState triState) {
        this.privateResetParameters = triState;
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

    public final void setTakeNoAction(TriState triState) {
        this.privateTakeNoAction = triState;
    }

    public final void setUseAlert(TriState triState) {
        this.privateUseAlert = triState;
    }

    public void transponderReceived(TransponderData transponderData, boolean z) {
        Log.d("TMCB", "Base implementation of transponderReceived(): " + transponderData.getEpc());
    }
}
