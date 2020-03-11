package com.app.apprfid.asciiprotocol.responders;

import java.util.ArrayList;
import java.util.List;

public abstract class AsciiResponseBase implements IAsciiResponse {
    private ArrayList messages = new ArrayList();
    private ArrayList parameters = new ArrayList();
    private String privateErrorCode;
    private boolean privateIsSuccessful;
    private ArrayList response = new ArrayList();

    protected AsciiResponseBase() {
    }

    /* access modifiers changed from: protected */
    public void appendToMessages(String str) {
        this.messages.add(str);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Iterable, code=java.lang.Iterable<java.lang.String>, for r4v0, types: [java.lang.Iterable<java.lang.String>, java.lang.Iterable] */
    public void appendToParameters(Iterable<String> iterable) {
        for (String add : iterable) {
            this.parameters.add(add);
        }
    }

    /* access modifiers changed from: protected */
    public void appendToResponse(String str) {
        this.response.add(str);
    }

    public void clearLastResponse() {
        setIsSuccessful(false);
        setErrorCode("");
        this.messages = new ArrayList();
        this.parameters = new ArrayList();
        this.response = new ArrayList();
    }

    public final String getErrorCode() {
        return this.privateErrorCode;
    }

    public final List getMessages() {
        return this.messages;
    }

    public final List getParameters() {
        return this.parameters;
    }

    public final List getResponse() {
        return this.response;
    }

    public final boolean isSuccessful() {
        return this.privateIsSuccessful;
    }

    /* access modifiers changed from: protected */
    public final void setErrorCode(String str) {
        this.privateErrorCode = str;
    }

    /* access modifiers changed from: protected */
    public final void setIsSuccessful(boolean z) {
        this.privateIsSuccessful = z;
    }

    /* access modifiers changed from: protected */
    public void setResponse(ArrayList arrayList) {
        this.response = arrayList;
    }
}
