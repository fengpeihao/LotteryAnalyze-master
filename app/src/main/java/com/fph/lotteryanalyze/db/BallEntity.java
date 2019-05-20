package com.fph.lotteryanalyze.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class BallEntity {
    @Id(autoincrement = true)
    private Long id;

    private Long idForOmit;

    private String number;
    /**
     * 球色 red  or  blue
     */
    private String colorType;
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
     * 当前出现遗漏的期数
     */
    private int currentOmit;
    /**
     * 上期遗漏期数
     */
    private int preOmit;
    /**
     * 总遗漏次数
     */
    private int totalOmitCount;
    /**
     * 预出几率
     */
    private String beforehandFrequency;
    /**
     * 平均遗漏
     */
    private String aveOmitCount;
    /**
     * 出现频率
     */
    private String ariseFrequency;
    /**
     * 回补几率
     */
    private String anaplerosisFrequency;

    @Generated(hash = 291223371)
    public BallEntity(Long id, Long idForOmit, String number, String colorType, int totalCount, int maxOmit,
            int maxContinuous, int currentOmit, int preOmit, int totalOmitCount, String beforehandFrequency,
            String aveOmitCount, String ariseFrequency, String anaplerosisFrequency) {
        this.id = id;
        this.idForOmit = idForOmit;
        this.number = number;
        this.colorType = colorType;
        this.totalCount = totalCount;
        this.maxOmit = maxOmit;
        this.maxContinuous = maxContinuous;
        this.currentOmit = currentOmit;
        this.preOmit = preOmit;
        this.totalOmitCount = totalOmitCount;
        this.beforehandFrequency = beforehandFrequency;
        this.aveOmitCount = aveOmitCount;
        this.ariseFrequency = ariseFrequency;
        this.anaplerosisFrequency = anaplerosisFrequency;
    }

    @Generated(hash = 1763894739)
    public BallEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdForOmit() {
        return this.idForOmit;
    }

    public void setIdForOmit(Long idForOmit) {
        this.idForOmit = idForOmit;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getColorType() {
        return this.colorType;
    }

    public void setColorType(String colorType) {
        this.colorType = colorType;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getMaxOmit() {
        return this.maxOmit;
    }

    public void setMaxOmit(int maxOmit) {
        this.maxOmit = maxOmit;
    }

    public int getMaxContinuous() {
        return this.maxContinuous;
    }

    public void setMaxContinuous(int maxContinuous) {
        this.maxContinuous = maxContinuous;
    }

    public int getCurrentOmit() {
        return this.currentOmit;
    }

    public void setCurrentOmit(int currentOmit) {
        this.currentOmit = currentOmit;
    }

    public int getPreOmit() {
        return this.preOmit;
    }

    public void setPreOmit(int preOmit) {
        this.preOmit = preOmit;
    }

    public int getTotalOmitCount() {
        return this.totalOmitCount;
    }

    public void setTotalOmitCount(int totalOmitCount) {
        this.totalOmitCount = totalOmitCount;
    }

    public String getBeforehandFrequency() {
        return this.beforehandFrequency;
    }

    public void setBeforehandFrequency(String beforehandFrequency) {
        this.beforehandFrequency = beforehandFrequency;
    }

    public String getAveOmitCount() {
        return this.aveOmitCount;
    }

    public void setAveOmitCount(String aveOmitCount) {
        this.aveOmitCount = aveOmitCount;
    }

    public String getAriseFrequency() {
        return this.ariseFrequency;
    }

    public void setAriseFrequency(String ariseFrequency) {
        this.ariseFrequency = ariseFrequency;
    }

    public String getAnaplerosisFrequency() {
        return this.anaplerosisFrequency;
    }

    public void setAnaplerosisFrequency(String anaplerosisFrequency) {
        this.anaplerosisFrequency = anaplerosisFrequency;
    }


}
