package com.app.apprfid.asciiprotocol.responders;

import com.app.apprfid.asciiprotocol.enumerations.TransponderAccessErrorCode;
import com.app.apprfid.asciiprotocol.enumerations.TransponderBackscatterErrorCode;

import java.util.Date;

public class TransponderData {
    public static final int NO_WORDS_WRITTEN = -1;
    private TransponderAccessErrorCode privateAccessErrorCode;
    private TransponderBackscatterErrorCode privateBackscatterErrorCode;
    private Integer privateCrc;
    private boolean privateDidKill;
    private boolean privateDidLock;
    private String privateEpc;
    private Integer privateIndex;
    private Integer privatePc;
    private Integer privateQt;
    private byte[] privateReadData;
    private Integer privateRssi;
    private byte[] privateTidData;
    private Date privateTimestamp;
    private int privateWordsWritten;

    public TransponderData() {
    }

    public TransponderData(Integer num, String str, Integer num2, boolean z, boolean z2, Integer num3, Integer num4, byte[] bArr, Integer num5, Date date, TransponderAccessErrorCode transponderAccessErrorCode, TransponderBackscatterErrorCode transponderBackscatterErrorCode, byte[] bArr2, int i) {
        setCrc(num);
        setEpc(str);
        setIndex(num2);
        setKill(z);
        setLock(z2);
        setPc(num3);
        setQt(num4);
        setReadData(bArr);
        setRssi(num5);
        setTimestamp(date);
        setAccessErrorCode(transponderAccessErrorCode);
        setBackscatterErrorCode(transponderBackscatterErrorCode);
        setTidData(bArr2);
        setWordsWritten(i);
    }

    public TransponderData(Integer num, String str, Integer num2, boolean z, boolean z2, Integer num3, byte[] bArr, Integer num4, Date date, TransponderAccessErrorCode transponderAccessErrorCode, TransponderBackscatterErrorCode transponderBackscatterErrorCode, byte[] bArr2, int i) {
        this(num, str, num2, z, z2, num3, null, bArr, num4, date, transponderAccessErrorCode, transponderBackscatterErrorCode, bArr2, i);
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

    private void setTidData(byte[] bArr) {
        this.privateTidData = bArr;
    }

    private void setTimestamp(Date date) {
        this.privateTimestamp = date;
    }

    private void setWordsWritten(int i) {
        this.privateWordsWritten = i;
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

    public final byte[] getTidData() {
        return this.privateTidData;
    }

    public final Date getTimestamp() {
        return this.privateTimestamp;
    }

    public final int getWordsWritten() {
        return this.privateWordsWritten;
    }
}
