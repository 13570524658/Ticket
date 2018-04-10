package com.future.zhh.ticket.domain.model;

/**
 * Created by Administrator on 2017/11/21.
 */

public class HomeIconModel {
    private int homeId;
    private int description;
    private int icon;

    public HomeIconModel(int homeId,int description,int icon) {
        this.homeId = homeId;
        this.description = description;
        this.icon = icon;
    }

    public int getHomeId() {
        return homeId;
    }

    public void setHomeId(int homeId) {
        this.homeId = homeId;
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
