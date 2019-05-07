package com.fph.lotteryanalyze.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class VerifyBean {

    private String type;
    private String expect;
    private BigDecimal[] aveOmitCountList;
    private BigDecimal[] beforehandFrequencyList;
    private BigDecimal[] anaplerosisFrequencyList;
    private String opencode;
    private BigDecimal[] probabilityList;

    public String getBeforehandCode() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < probabilityList.length; i++) {
            builder.append(probabilityList[i].setScale(3, BigDecimal.ROUND_HALF_UP).toString()).append(",");
        }
        return builder.subSequence(0, builder.length() - 1).toString();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    public BigDecimal[] getAveOmitCountList() {
        return aveOmitCountList;
    }

    public void setAveOmitCountList(BigDecimal[] aveOmitCountList) {
        this.aveOmitCountList = aveOmitCountList;
    }

    public BigDecimal[] getBeforehandFrequencyList() {
        return beforehandFrequencyList;
    }

    public void setBeforehandFrequencyList(BigDecimal[] beforehandFrequencyList) {
        this.beforehandFrequencyList = beforehandFrequencyList;
    }

    public BigDecimal[] getAnaplerosisFrequencyList() {
        return anaplerosisFrequencyList;
    }

    public void setAnaplerosisFrequencyList(BigDecimal[] anaplerosisFrequencyList) {
        this.anaplerosisFrequencyList = anaplerosisFrequencyList;
    }

    public String getOpencode() {
        return opencode;
    }

    public void setOpencode(String opencode) {
        this.opencode = opencode;
    }

    public BigDecimal[] getProbabilityList() {
        return probabilityList;
    }

    public void setProbabilityList(BigDecimal[] probabilityList) {
        this.probabilityList = probabilityList;
    }
}
