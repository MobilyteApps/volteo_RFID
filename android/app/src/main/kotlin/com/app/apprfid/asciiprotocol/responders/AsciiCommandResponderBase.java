package com.app.apprfid.asciiprotocol.responders;

import android.util.Log;

import com.app.apprfid.b.b;

import java.util.ArrayList;
import java.util.Collection;

public abstract class AsciiCommandResponderBase extends AsciiResponseBase implements IAsciiCommandResponder {
    private static final String TAG = "Ascii";
    private String privateCommandName;
    private boolean privateIsResponseFinished;
    private ICommandResponseLifecycleDelegate privateResponseLifecycleDelegate;
    private boolean privateResponseStarted;

    protected AsciiCommandResponderBase() {
        this("");
    }

    protected AsciiCommandResponderBase(String str) {
        setCommandName(str);
    }

    private void commandComplete(boolean z) {
        setIsSuccessful(z);
        setResponseStarted(false);
        responseDidFinish(false);
    }

    private static int nextQuoteDash(String str, int i) {
        char[] cArr = {'\'', '\"', '-'};
        int i2 = Integer.MAX_VALUE;
        for (char indexOf : cArr) {
            int indexOf2 = str.indexOf(indexOf, i);
            if (indexOf2 >= 0 && indexOf2 < i2) {
                i2 = indexOf2;
            }
        }
        if (i2 == Integer.MAX_VALUE) {
            return -1;
        }
        return i2;
    }

    private void setIsResponseFinished(boolean z) {
        this.privateIsResponseFinished = z;
    }

    public static Collection splitParameters(String str) {
        int i = 0;
        ArrayList arrayList = new ArrayList();
        if (str != null) {
            if (str.indexOf(34) < 0) {
                String[] split = str.split("[-]", -1);
                int length = split.length;
                while (i < length) {
                    String trim = split[i].trim();
                    if (!b.a(trim)) {
                        arrayList.add(trim);
                    }
                    i++;
                }
            } else {
                String str2 = "";
                int nextQuoteDash = nextQuoteDash(str, 0);
                while (i < str.length()) {
                    if (nextQuoteDash < 0) {
                        String trim2 = str.substring(i).trim();
                        if (!b.a(trim2)) {
                            arrayList.add(trim2);
                        }
                        i = str.length();
                    } else if (str.charAt(nextQuoteDash) == '\"') {
                        nextQuoteDash = str.indexOf(34, nextQuoteDash + 1);
                        if (nextQuoteDash >= 0) {
                            nextQuoteDash = nextQuoteDash(str, nextQuoteDash + 1);
                        }
                    } else if (str.charAt(nextQuoteDash) == '\'') {
                        nextQuoteDash = str.indexOf(39, nextQuoteDash + 1);
                        if (nextQuoteDash >= 0) {
                            nextQuoteDash = nextQuoteDash(str, nextQuoteDash + 1);
                        }
                    } else {
                        String trim3 = str.substring(i, nextQuoteDash).trim();
                        if (!b.a(trim3)) {
                            arrayList.add(trim3);
                        }
                        i = nextQuoteDash + 1;
                        nextQuoteDash = nextQuoteDash(str, i);
                    }
                }
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public void buildCommandLine(StringBuilder sb) {
        sb.append(getCommandName());
        sb.append(" ");
    }

    public void clearLastResponse() {
        super.clearLastResponse();
        setResponseStarted(false);
        setIsResponseFinished(false);
    }

    public String getCommandLine() {
        StringBuilder sb = new StringBuilder();
        buildCommandLine(sb);
        return sb.toString();
    }

    public final String getCommandName() {
        return this.privateCommandName;
    }

    public ICommandResponseLifecycleDelegate getResponseLifecycleDelegate() {
        return this.privateResponseLifecycleDelegate;
    }

    /* access modifiers changed from: protected */
    public final boolean getResponseStarted() {
        return this.privateResponseStarted;
    }

    public final boolean isResponseFinished() {
        return this.privateIsResponseFinished;
    }

    public final Iterable parseParameters(String str) {
        Collection<String> splitParameters = splitParameters(str);
        for (String responseDidReceiveParameter : splitParameters) {
            responseDidReceiveParameter(responseDidReceiveParameter);
        }
        return splitParameters;
    }

    /* access modifiers changed from: protected */
    public boolean processReceivedLine(String str, String str2, String str3, boolean z) {
        if (getResponseStarted()) {
            if ("OK".equals(str2)) {
                appendToResponse(str);
                commandComplete(true);
            } else if ("ER".equals(str2)) {
                setErrorCode(str3.trim());
                appendToResponse(str);
                commandComplete(false);
            } else if ("ME".equals(str2)) {
                appendToMessages(str3);
            } else if (!"PR".equals(str2)) {
                return false;
            } else {
                appendToParameters(parseParameters(str3));
            }
            if (getResponseStarted()) {
                appendToResponse(str);
            }
            return true;
        } else if (!"CS".equals(str2)) {
            return false;
        } else {
            if (!b.a(getCommandName()) && !str3.startsWith(getCommandName())) {
                return false;
            }
            clearLastResponse();
            setResponseStarted(true);
            appendToResponse(str);
            responseDidStart();
            return true;
        }
    }

    public final boolean processReceivedLine(String str, boolean z) {
        String str2;
        String str3;
        try {
            String trim = str.trim();
            if (trim.indexOf(":") == 2) {
                str2 = trim.substring(0, 2);
                str3 = trim.substring(3).trim();
            } else {
                str2 = "";
                str3 = "";
            }
            return processReceivedLine(str, str2, str3, z);
        } catch (Exception e) {
            Log.e(TAG, "processReceivedLine", e);
            appendToResponse(str);
            commandComplete(false);
            throw e;
        }
    }

    /* access modifiers changed from: protected */
    public void responseDidFinish(boolean z) {
        setIsResponseFinished(true);
        if (getResponseLifecycleDelegate() != null) {
            getResponseLifecycleDelegate().responseEnded();
        }
        Log.v(getClass().getSimpleName(), "responseDidFinish");
    }

    /* access modifiers changed from: protected */
    public boolean responseDidReceiveParameter(String str) {
        Log.w(TAG, "ResponseDidReceiveParameter. Unrecognised parameter: " + str);
        return false;
    }

    /* access modifiers changed from: protected */
    public void responseDidStart() {
        Log.v(getClass().getSimpleName(), "ResponseDidStart");
        if (getResponseLifecycleDelegate() != null) {
            getResponseLifecycleDelegate().responseBegan();
        }
    }

    /* access modifiers changed from: protected */
    public final void setCommandName(String str) {
        this.privateCommandName = str;
    }

    public void setResponseLifecycleDelegate(ICommandResponseLifecycleDelegate iCommandResponseLifecycleDelegate) {
        this.privateResponseLifecycleDelegate = iCommandResponseLifecycleDelegate;
    }

    /* access modifiers changed from: protected */
    public final void setResponseStarted(boolean z) {
        this.privateResponseStarted = z;
    }
}
