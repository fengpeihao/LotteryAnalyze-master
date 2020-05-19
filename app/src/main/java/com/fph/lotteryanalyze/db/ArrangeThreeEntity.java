package com.fph.lotteryanalyze.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class ArrangeThreeEntity {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String periods;
    private String number;
    private String firstNumber;
    private String secondNumber;
    private String threeNumber;
    private String date;
    @Generated(hash = 596553389)
    public ArrangeThreeEntity(Long id, String periods, String number,
            String firstNumber, String secondNumber, String threeNumber,
            String date) {
        this.id = id;
        this.periods = periods;
        this.number = number;
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.threeNumber = threeNumber;
        this.date = date;
    }
    @Generated(hash = 280657785)
    public ArrangeThreeEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPeriods() {
        return this.periods;
    }
    public void setPeriods(String periods) {
        this.periods = periods;
    }
    public String getNumber() {
        return this.number;
    }
    public void setNumber(String number) {
        this.number = number;
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
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }

}
