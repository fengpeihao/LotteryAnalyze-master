package com.fph.lotteryanalyze.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by fengpeihao on 2018/3/27.
 */

@Entity
public class LotteryEntity{
    /**
     * expect : 2018043
     * opencode : 01,04,06,08,21,24+07
     * opentime : 2018-04-17 21:18:20
     * opentimestamp : 1523971100
     */
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String expect;
    private String opencode;
    private String opentime;
    private Long opentimestamp;

    @Generated(hash = 1445329408)
    public LotteryEntity(Long id, String expect, String opencode, String opentime,
                         Long opentimestamp) {
        this.id = id;
        this.expect = expect;
        this.opencode = opencode;
        this.opentime = opentime;
        this.opentimestamp = opentimestamp;
    }

    @Generated(hash = 540224404)
    public LotteryEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    public String getOpencode() {
        return opencode;
    }

    public void setOpencode(String opencode) {
        this.opencode = opencode;
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }

    public Long getOpentimestamp() {
        return opentimestamp;
    }

    public void setOpentimestamp(Long opentimestamp) {
        this.opentimestamp = opentimestamp;
    }
}
