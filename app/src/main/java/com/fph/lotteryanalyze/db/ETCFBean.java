package com.fph.lotteryanalyze.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 11 选 5
 */
@Entity
public class ETCFBean {
    @Id(autoincrement = true)
    private Long id;
    private String openNumber;
    @Unique
    private String periods;
    private String date;
    private String firstNumber;
    private String secondNumber;
    private String threeNumber;
    private String fourNumber;
    private String fiveNumber;
    @Generated(hash = 1035989588)
    public ETCFBean(Long id, String openNumber, String periods, String date,
            String firstNumber, String secondNumber, String threeNumber,
            String fourNumber, String fiveNumber) {
        this.id = id;
        this.openNumber = openNumber;
        this.periods = periods;
        this.date = date;
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.threeNumber = threeNumber;
        this.fourNumber = fourNumber;
        this.fiveNumber = fiveNumber;
    }
    @Generated(hash = 115205451)
    public ETCFBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getOpenNumber() {
        return this.openNumber;
    }
    public void setOpenNumber(String openNumber) {
        this.openNumber = openNumber;
    }
    public String getPeriods() {
        return this.periods;
    }
    public void setPeriods(String periods) {
        this.periods = periods;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getFirstNumber() {
        return this.firstNumber;
    }
    public void setFirstNumber(String firstNumber) {
        this.firstNumber = firstNumber;
    }
    public String getSecondNumber() {
        return this.secondNumber;
    }
    public void setSecondNumber(String secondNumber) {
        this.secondNumber = secondNumber;
    }
    public String getThreeNumber() {
        return this.threeNumber;
    }
    public void setThreeNumber(String threeNumber) {
        this.threeNumber = threeNumber;
    }
    public String getFourNumber() {
        return this.fourNumber;
    }
    public void setFourNumber(String fourNumber) {
        this.fourNumber = fourNumber;
    }
    public String getFiveNumber() {
        return this.fiveNumber;
    }
    public void setFiveNumber(String fiveNumber) {
        this.fiveNumber = fiveNumber;
    }
}
