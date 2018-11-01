package com.arley.cms.console.pojo.query;

/**
 * @author XueXianlei
 * @Description:
 * @date 2018/9/30 13:38
 */
public class PageQuery {

    protected Integer page;

    protected Integer limit;

    protected String sortField;

    protected String sortOrder;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
