package com.optimus.common.mybatis;

import java.util.ArrayList;

/**
 * 分页查询结果封装
 * @author caoawei
 */
public class PageList<E> extends ArrayList<E> {

    /**
     * 开始行号
     */
    private int start;

    /**
     * 每次获取数据条数
     */
    private int limit;

    /**
     * 目标页数
     */
    private int page;

    /**
     * 每页大小
     */
    private int pageSize;

    /**
     * 数据总量
     */
    private int totalCount;

    /**
     * 总页数
     */
    private int pageNumber;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
