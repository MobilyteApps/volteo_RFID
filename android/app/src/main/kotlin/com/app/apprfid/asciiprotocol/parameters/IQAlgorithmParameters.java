package com.app.apprfid.asciiprotocol.parameters;

import com.app.apprfid.asciiprotocol.enumerations.QAlgorithm;

public interface IQAlgorithmParameters {
    QAlgorithm getQAlgorithm();

    int getQValue();

    void setQAlgorithm(QAlgorithm qAlgorithm);

    void setQValue(int i);
}
