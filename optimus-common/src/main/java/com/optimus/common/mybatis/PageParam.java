package com.optimus.common.mybatis;

/**
 * 用于封装分页查询参数
 * @author caoawei
 */
public class PageParam {

    private static final int DEFAULT_LIMIT = 20;

    /**
     * 开始行号
     */
    private int start;

    /**
     * 每次获取数据条数
     */
    private int limit;

    /**
     * 页数
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

    private PageParam(){}

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

    public static PageParam initWithOffset(int start,int limit){
        if(start < 0){
            start = 0;
        }
        if(limit < 0){
            limit = DEFAULT_LIMIT;
        }

        PageParam pageParam = new PageParam();
        pageParam.setStart(start);
        pageParam.setLimit(limit);
        pageParam.setPageSize(limit);

        return pageParam;
    }

    public static PageParam initWithTargetPage(int targetPage,int pageSize){
        int start = 0;
        if(pageSize < 0){
            pageSize = DEFAULT_LIMIT;
        }
        if(targetPage > 0){
            start = (targetPage - 1) * pageSize;
        }
        return initWithOffset(start,pageSize);
    }
}
