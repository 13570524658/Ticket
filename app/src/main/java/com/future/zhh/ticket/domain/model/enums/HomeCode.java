package com.future.zhh.ticket.domain.model.enums;

import com.future.zhh.ticket.R;

/**
 * Created by Administrator on 2017/11/21.
 */

public enum  HomeCode {
    //配送管理
    DELIVERY_MANAGER(1, R.string.delivery_manager,R.mipmap.sy_ps),
    //气瓶建档
    SET_UP_DATA(2, R.string.set_up_data,R.mipmap.sy_jd),
   //用户检查
    CHECK_CUSTOMER(3, R.string.check_customer,R.mipmap.sy_jc),
    //用户交付/回收
    JFHS(4, R.string.jf_hs,R.mipmap.sy_jfhs),
    //空格
    BLANK(0,-1,-1);

    int id;
    int description;
    int icon;

    HomeCode(int id,int description,int icon) {
        this.id = id;
        this.description = description;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

}
