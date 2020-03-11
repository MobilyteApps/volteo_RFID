package com.app.apprfid.asciiprotocol.responders;


import com.app.apprfid.b.a;
import com.app.apprfid.asciiprotocol.Constants;
import com.app.apprfid.asciiprotocol.enumerations.TransponderAccessErrorCode;
import com.app.apprfid.asciiprotocol.enumerations.TransponderBackscatterErrorCode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class TransponderResponder {
    private HashSet mTransponderHeadersSeen = new HashSet();
    private TransponderAccessErrorCode privateAccessErrorCode;
    private TransponderBackscatterErrorCode privateBackscatterErrorCode;
    private Integer privateCrc;
    private boolean privateDidKill;
    private boolean privateDidLock;
    private String privateEpc;
    private byte[] privateFastIdData;
    private Integer privateIndex;
    private Integer privatePc;
    private Integer privateQt;
    private byte[] privateReadData;
    private Integer privateRssi;
    private Date privateTimestamp;
    private ITransponderReceivedDelegate privateTransponderReceivedHandler;
    private int privateWordsWritten;

    public TransponderResponder() {
        clearLastResponse();
    }

    private void setAccessErrorCode(TransponderAccessErrorCode transponderAccessErrorCode) {
        this.privateAccessErrorCode = transponderAccessErrorCode;
    }

    private void setBackscatterErrorCode(TransponderBackscatterErrorCode transponderBackscatterErrorCode) {
        this.privateBackscatterErrorCode = transponderBackscatterErrorCode;
    }

    private void setCrc(Integer num) {
        this.privateCrc = num;
    }

    private void setEpc(String str) {
        this.privateEpc = str;
    }

    private void setFastIdData(byte[] bArr) {
        this.privateFastIdData = bArr;
    }

    private void setIndex(Integer num) {
        this.privateIndex = num;
    }

    private void setKill(boolean z) {
        this.privateDidKill = z;
    }

    private void setLock(boolean z) {
        this.privateDidLock = z;
    }

    private void setPc(Integer num) {
        this.privatePc = num;
    }

    private void setQt(Integer num) {
        this.privateQt = num;
    }

    private void setReadData(byte[] bArr) {
        this.privateReadData = bArr;
    }

    private void setRssi(Integer num) {
        this.privateRssi = num;
    }

    private void setTimestamp(Date date) {
        this.privateTimestamp = date;
    }

    private void setWordsWritten(int i) {
        this.privateWordsWritten = i;
    }

    public final void clearLastResponse() {
        setEpc("");
        setCrc(null);
        setIndex(null);
        setPc(null);
        setQt(null);
        setLock(false);
        setKill(false);
        setReadData(null);
        setRssi(null);
        setAccessErrorCode(TransponderAccessErrorCode.NOT_SPECIFIED);
        setBackscatterErrorCode(TransponderBackscatterErrorCode.NOT_SPECIFIED);
        setFastIdData(null);
        setWordsWritten(-1);
        this.mTransponderHeadersSeen.clear();
    }

    public final boolean didKill() {
        return this.privateDidKill;
    }

    public final boolean didLock() {
        return this.privateDidLock;
    }

    public final TransponderAccessErrorCode getAccessErrorCode() {
        return this.privateAccessErrorCode;
    }

    public final TransponderBackscatterErrorCode getBackscatterErrorCode() {
        return this.privateBackscatterErrorCode;
    }

    public final Integer getCrc() {
        return this.privateCrc;
    }

    public final String getEpc() {
        return this.privateEpc;
    }

    public final byte[] getFastIdData() {
        return this.privateFastIdData;
    }

    public final Integer getIndex() {
        return this.privateIndex;
    }

    public final Integer getPc() {
        return this.privatePc;
    }

    public final Integer getQt() {
        return this.privateQt;
    }

    public final byte[] getReadData() {
        return this.privateReadData;
    }

    public final Integer getRssi() {
        return this.privateRssi;
    }

    public final Date getTimestamp() {
        return this.privateTimestamp;
    }

    public final ITransponderReceivedDelegate getTransponderReceivedHandler() {
        return this.privateTransponderReceivedHandler;
    }

    public final int getWordsWritten() {
        return this.privateWordsWritten;
    }

    public final boolean processReceivedLine(String str, String str2) {
        if (this.mTransponderHeadersSeen.contains(str)) {
            transponderComplete(true);
        }
        this.mTransponderHeadersSeen.add(str);
        if ("OK".equals(str)) {
            transponderComplete(false);
            return false;
        } else if ("ER".equals(str)) {
            transponderComplete(false);
            return false;
        } else if ("DT".equals(str)) {
            try {
                setTimestamp(new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss", Constants.COMMAND_LOCALE).parse(str2));
            } catch (ParseException e) {
                setTimestamp(null);
            }
            return true;
        } else if ("EP".equals(str)) {
            setEpc(str2);
            return true;
        } else if ("CR".equals(str)) {
            setCrc(Integer.valueOf(Integer.parseInt(str2, 16)));
            return true;
        } else if ("PC".equals(str)) {
            setPc(Integer.valueOf(Integer.parseInt(str2, 16)));
            return true;
        } else if ("QT".equals(str)) {
            setQt(Integer.valueOf(Integer.parseInt(str2, 16)));
            return true;
        } else if ("IX".equals(str)) {
            setIndex(Integer.valueOf(Integer.parseInt(str2, 16)));
            return true;
        } else if ("RI".equals(str)) {
            setRssi(Integer.valueOf(Integer.parseInt(str2)));
            return true;
        } else if ("LS".equals(str)) {
            setLock(str2.contains("Lock Success"));
            return true;
        } else if ("KS".equals(str)) {
            setKill(str2.contains("Kill Success"));
            return true;
        } else if ("RD".equals(str)) {
            setReadData(a.a(str2));
            return true;
        } else if ("TD".equals(str)) {
            setFastIdData(a.a(str2));
            return true;
        } else if ("EA".equals(str)) {
            setAccessErrorCode(TransponderAccessErrorCode.Parse(str2));
            return true;
        } else if ("EB".equals(str)) {
            setBackscatterErrorCode(TransponderBackscatterErrorCode.Parse(str2));
            return true;
        } else if (!"WW".equals(str)) {
            return false;
        } else {
            setWordsWritten(Integer.parseInt(str2));
            return true;
        }
    }

    public final void setTransponderReceivedHandler(ITransponderReceivedDelegate iTransponderReceivedDelegate) {
        this.privateTransponderReceivedHandler = iTransponderReceivedDelegate;
    }

    public final void transponderComplete(boolean z) {
        if (!this.mTransponderHeadersSeen.isEmpty() && this.privateTransponderReceivedHandler != null) {
            this.privateTransponderReceivedHandler.transponderReceived(new TransponderData(getCrc(), getEpc(), getIndex(), didKill(), didLock(), getPc(), getQt(), getReadData(), getRssi(), getTimestamp(), getAccessErrorCode(), getBackscatterErrorCode(), getFastIdData(), getWordsWritten()), z);
        }
        clearLastResponse();
        if (!z) {
            setTimestamp(null);
        }
    }
}
