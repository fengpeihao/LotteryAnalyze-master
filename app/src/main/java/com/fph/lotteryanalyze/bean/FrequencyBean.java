package com.fph.lotteryanalyze.bean;

import android.util.SparseArray;

import java.math.BigDecimal;
import java.util.List;

public class FrequencyBean {
    /**
     * 号码
     */
    private String number;

    /**
     * 出现的总次数
     */
    private int totalCount;

    /**
     * 历史最大遗漏
     */
    private int maxOmit;

    /**
     * 历史最大连续长度
     */
    private int maxContinuous;

    /**
     * 出现的连续长度
     */
    private int continuous;

    /**
     * 历史出现连续长度的次数
     */
    private SparseArray<Integer> continuousCount;

    /**
     * 当前出现遗漏的期数
     */
    private int currentOmit;

    /**
     * 历史遗漏长度次数
     */
    private SparseArray<Integer> omitCount;

    /**
     * 遗漏期数
     */
    private List<Integer> omitPeriods;
    /**
     * 总遗漏次数
     */
    private int totalOmitCount;

    public int getContinuous() {
        return continuous;
    }

    public void setContinuous(int continuous) {
        this.continuous = continuous;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getMaxOmit() {
        return maxOmit;
    }

    public void setMaxOmit(int maxOmit) {
        this.maxOmit = maxOmit;
    }

    public int getMaxContinuous() {
        return maxContinuous;
    }

    public void setMaxContinuous(int maxContinuous) {
        this.maxContinuous = maxContinuous;
    }

    public SparseArray<Integer> getContinuousCount() {
        return continuousCount;
    }

    public void setContinuousCount(SparseArray<Integer> continuousCount) {
        this.continuousCount = continuousCount;
    }

    public int getCurrentOmit() {
        return currentOmit;
    }

    public void setCurrentOmit(int currentOmit) {
        this.currentOmit = currentOmit;
    }

    public SparseArray<Integer> getOmitCount() {
        return omitCount;
    }

    public void setOmitCount(SparseArray<Integer> omitCount) {
        this.omitCount = omitCount;
    }

    public List<Integer> getOmitPeriods() {
        return omitPeriods;
    }

    public void setOmitPeriods(List<Integer> omitPeriods) {
        this.omitPeriods = omitPeriods;
    }

    /**
     * 平均遗漏
     *
     * @return 平均遗漏
     */
    public BigDecimal getAveOmitCount() {
        return BigDecimal.valueOf(totalOmitCount * 1f / omitPeriods.size());
    }

    /**
     * 出现频率
     *
     * @return 出现频率
     */
    public BigDecimal getAriseFrequency() {
        return BigDecimal.valueOf(totalCount * 1f / (totalOmitCount + totalCount));
    }

    /**
     * 预出几率
     *
     * @return 预出几率
     */
    public BigDecimal getBeforehandFrequency() {
        return BigDecimal.valueOf(currentOmit * 1f / getAveOmitCount().doubleValue());
    }

    /**
     * 回补几率
     * @return
     */
    public BigDecimal getAnaplerosisFrequency() {
        return BigDecimal.valueOf((currentOmit - getOmitPeriods().get(getOmitPeriods().size() - 2)) / 5.5f);
    }

    public int getTotalOmitCount() {
        return totalOmitCount;
    }

    public void setTotalOmitCount(int totalOmitCount) {
        this.totalOmitCount = totalOmitCount;
    }

    @Override
    public String toString() {
        return "蓝球'" + number + '\'' +
                "： 出现的总次数：" + totalCount +
                " ，历史最大遗漏：" + maxOmit +
                " ，历史最大连续长度：" + maxContinuous +
                " ，当前出现的连续长度：" + continuous +
                " ，当前出现遗漏的期数：" + currentOmit +
                " ，历史出现连续长度的次数：" + continuousCount +
                " ，历史遗漏长度次数：" + omitCount +
                " ，历史遗漏期数：" + omitPeriods +
                "，总遗漏次数：" + totalOmitCount;
    }
}
