package com.water.greendaotest.daobean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by pc on 2018/7/20.
 */

@Entity
public class DayStep {

    @Id(autoincrement = true)
    private Long id;
    private String date;
    private int step;
    private Long sportId;
    @Generated(hash = 1562605780)
    public DayStep(Long id, String date, int step, Long sportId) {
        this.id = id;
        this.date = date;
        this.step = step;
        this.sportId = sportId;
    }
    @Generated(hash = 121003456)
    public DayStep() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getStep() {
        return this.step;
    }
    public void setStep(int step) {
        this.step = step;
    }
    public Long getSportId() {
        return this.sportId;
    }
    public void setSportId(Long sportId) {
        this.sportId = sportId;
    }


}
