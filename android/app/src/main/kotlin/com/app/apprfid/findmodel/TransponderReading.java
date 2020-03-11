package com.app.apprfid.findmodel;

import java.util.Date;

public class TransponderReading {
    private double mAdjustedNormalisedRssi;
    private double mAdjustedRssi;
    private double mNormalisedRssi;
    private double mRssi;
    private Date mTimestamp;

    public TransponderReading(TransponderReading transponderReading) {
        this.mRssi = transponderReading.mRssi;
        this.mAdjustedRssi = transponderReading.mAdjustedRssi;
        this.mNormalisedRssi = transponderReading.mNormalisedRssi;
        this.mAdjustedNormalisedRssi = transponderReading.mAdjustedNormalisedRssi;
        this.mTimestamp = transponderReading.getTimestamp();
    }

    public TransponderReading(Date date, double d) {
        this.mTimestamp = date;
        this.mRssi = d;
        this.mAdjustedRssi = d;
        this.mNormalisedRssi = -1.0d;
        this.mAdjustedNormalisedRssi = this.mNormalisedRssi;
    }

    public double getAdjustedNormalisedRssi() {
        return this.mAdjustedNormalisedRssi;
    }

    public double getAdjustedRssi() {
        return this.mAdjustedRssi;
    }

    public double getNormalisedRssi() {
        return this.mNormalisedRssi;
    }

    public double getRssi() {
        return this.mRssi;
    }

    public Date getTimestamp() {
        return this.mTimestamp;
    }

    public void setAdjustedNormalisedRssi(double d) {
        this.mAdjustedNormalisedRssi = d;
    }

    public void setAdjustedRssi(double d) {
        this.mAdjustedRssi = d;
    }

    public void setNormalisedRssi(double d) {
        this.mNormalisedRssi = d;
    }

    public void setRssi(double d) {
        this.mRssi = d;
    }

    public void setTimestamp(Date date) {
        this.mTimestamp = date;
    }
}
