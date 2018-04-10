package com.future.zhh.ticket.data.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/11/19.
 */

@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;
    @Transient
    private int tempUsageCount; // not persisted
    @Generated(hash = 1248599927)
    public User(Long id) {
        this.id = id;
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
}