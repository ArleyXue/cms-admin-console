package com.arley.cms.console.util;

import java.util.List;

/**
 * @author XueXianlei
 * @Description: layui 分页返回数据结构
 * @date 2018/8/19 11:17
 */
public class Pagination<T> {

    private long total;

    private List<T> data;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
