package com.future.zhh.ticket.domain.model;


import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */

public class ListResult<T> {
    private int total;
    private List<T>  rows;


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
