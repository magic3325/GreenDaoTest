package com.water.greendaotest.daobean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by pc on 2018/7/21.
 */

@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;
    //用户名
    private String userName;
    //年龄
    private int age;
    //学习成绩
    private double score;
    //排名
    private String random;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", score=" + score +
                ", random='" + random + '\'' +
                '}';
    }

    @Generated(hash = 1487427874)
    public User(Long id, String userName, int age, double score, String random) {
        this.id = id;
        this.userName = userName;
        this.age = age;
        this.score = score;
        this.random = random;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getScore() {
        return this.score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getRandom() {
        return this.random;
    }

    public void setRandom(String random) {
        this.random = random;
    }


}
