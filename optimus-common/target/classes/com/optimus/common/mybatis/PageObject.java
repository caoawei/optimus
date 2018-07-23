package com.optimus.common.mybatis;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页查询 面向 前端的结果对象
 * @author caoawei
 */
public class PageObject<E> implements Serializable {

    /**
     * 数据列表
     */
    private List<E> data;

    /**
     * 开始行号
     */
    private Integer start;

    /**
     * 每次获取数据条数
     */
    private Integer limit;

    /**
     * 目标页数
     */
    private Integer page;

    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * 数据总量
     */
    private Integer totalCount;

    /**
     * 总页数
     */
    private Integer pageNumber;

    private PageObject(){}

    public static <E> PageObject<E> createPageObject(PageList<E> pageList){
        PageObject<E> pageObject = new PageObject<>();
        if(pageList == null){
            pageObject.data = Collections.emptyList();
        }else {
            pageObject.data = pageList;
            pageObject.start = pageList.getStart();
            pageObject.limit = pageList.getLimit();
            pageObject.page = pageList.getPage();
            pageObject.pageNumber = pageList.getPageNumber();
            pageObject.pageSize = pageList.getPageSize();
            pageObject.totalCount = pageList.getTotalCount();
        }

        return pageObject;
    }

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
}
