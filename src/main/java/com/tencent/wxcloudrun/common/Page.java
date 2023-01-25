package com.tencent.wxcloudrun.common;

import java.io.Serializable;
import java.util.List;


/**
 * @description: 分页查询列表结果类
 * @author: taohe
 * @create: 2018-07-08 16:13
 */
public class Page<T> implements Serializable {

    private static final long serialVersionUID = -6308146609054378595L;

    public static final String SORT_ASC = "asc";
    public static final String SORT_DESC = "desc";

    public static final int FIRST_PAGE = 1;

    private int start = 0;
    private int currentPage = 1;
    private int pageSize = 10;
    private long totalRecords;
    private String sortType;
    private String sortId;

    private int totalPage;
    private List<T> entityList;

    public Page() {
        super();
    }

    public Page(int currentPage) {
        super();
        this.currentPage = currentPage;
        this.start = this.getListStartIndex();
    }

    public Page(int currentPage, int pageSize) {
        super();
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.start = this.getStartIndex();
    }

    public Page(int start, int pageSize, int currentPage) {
        super();
        this.start = start;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
        this.start = this.getListStartIndex();
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getSortId() {
        return sortId;
    }

    public void setSortId(String sortId) {
        this.sortId = sortId;
    }


    public List<T> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<T> entityList) {
        this.entityList = entityList;
    }

    public int getTotalPage() {
        if (this.totalPage == 0) {
            if (this.pageSize == 0) {
                return 0;
            }

            this.totalPage = (int)this.totalRecords / this.pageSize +
                    (this.totalRecords % this.pageSize == 0 ? 0 : 1);
        }

        return this.totalPage;
    }

    public boolean isPrePage() {
        return this.currentPage > FIRST_PAGE;
    }

    public boolean isNextPage() {
        return this.currentPage < this.getTotalPage();
    }


    public int getStartIndex() {
        if (this.currentPage < 1) {
            this.currentPage = 1;
        }
        return (this.currentPage - 1) * this.pageSize;
    }

    public int getListStartIndex() {
        if (this.currentPage > this.getTotalPage()) {
            this.currentPage = Math.max(1, this.getTotalPage());
        }
        return (this.currentPage - 1) * this.pageSize;
    }

    @Override
    public String toString() {
        return "Page{" +
                "start=" + start +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", totalRecords=" + totalRecords +
                ", sortType='" + sortType + '\'' +
                ", sortId='" + sortId + '\'' +
                ", totalPage=" + totalPage +
                ", entityList=" + entityList +
                '}';
    }
}
