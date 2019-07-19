package com.bjpowernode.crm.vo;

import java.util.List;

/**
 * Author: 甘明波
 * 2019-07-16
 */
public class PageVo<T> {
    private Integer pageCount;//总记录数
    private List<T> dataList;

    public PageVo() {
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public PageVo<T> setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
        return this;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public PageVo<T> setDataList(List<T> dataList) {
        this.dataList = dataList;
        return this;
    }

    @Override
    public String toString() {
        return "PageVo{" +
                "pageCount=" + pageCount +
                ", dataList=" + dataList +
                '}';
    }
}
