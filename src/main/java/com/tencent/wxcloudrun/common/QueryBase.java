package com.tencent.wxcloudrun.common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @description: 分页查询基础类
 * @author: taohe
 * @create: 2018-07-08 16:13
 */

public class QueryBase implements Serializable {

    private static final long serialVersionUID = 6918165685567623710L;


    private static final Integer defaultPageSize = 20;

    private static final Integer defaultFristPage = 1;

    private static final Integer defaultTotalItem = 0;

    /**
     * 查询结果总数
     * 默认查询总数：0
     */
    private Integer totalItem;
    /**
     * 分页大小
     * 默认分页大小：20
     */
    private Integer pageSize = 40;
    /**
     * 查询当前页
     * 默认分页开始：1
     */
    private Integer currentPage;

    /**
     * 开始行
     */
    private int startRow;
    /**
     * 结束行
     */
    private int endRow;

    /**
     * 是否是第一页
     *
     * @return true：是 false：不是
     */
    public boolean isFirstPage() {
        return this.getCurrentPage() == 1;
    }

    /**
     * 获取前一页
     * 若前一页为第一页，返回第一页
     *
     * @return 前一页页码
     */
    public int getPreviousPage() {
        int back = this.getCurrentPage() - 1;

        if (back <= 0) {
            back = 1;
        }

        return back;
    }

    public boolean isLastPage() {
        return this.getTotalPage() == this.getCurrentPage();
    }

    public int getNextPage() {
        int back = this.getCurrentPage() + 1;

        if (back > this.getTotalPage()) {
            back = this.getTotalPage();
        }

        return back;
    }

    public Integer getCurrentPage() {
        if (currentPage == null) {
            return defaultFristPage;
        }

        return currentPage;
    }


    public void setCurrentPage(Integer cPage) {
        if ((cPage == null) || (cPage <= 0)) {
            this.currentPage = null;
        } else {
            this.currentPage = cPage;
        }
        setStartEndRow();
    }

    private void setStartEndRow() {
        this.startRow = this.getPageSize() * (this.getCurrentPage() - 1);
        this.endRow = this.startRow + this.getPageSize();
    }

    /**
     * @return Returns the pageSize.
     */
    public Integer getPageSize() {
        if (pageSize == null) {
            return defaultPageSize;
        }

        return pageSize;
    }

    public boolean hasSetPageSize() {
        return pageSize != null;
    }

    public void setPageSizeString(String pageSizeString) {
        if (isBlankString(pageSizeString)) {
            return;
        }

        try {
            Integer integer = new Integer(pageSizeString);

            this.setPageSize(integer);
        } catch (NumberFormatException ignore) {
            this.setPageSize(null);
        }
    }

    /**
     * @param pageSizeString
     * @return
     */
    private boolean isBlankString(String pageSizeString) {
        if (pageSizeString == null) {
            return true;
        }

        if (pageSizeString.trim().isEmpty()) {
            return true;
        }

        return false;
    }

    /**
     * @param pSize The pageSize to set.
     */
    public void setPageSize(Integer pSize) {
        if ((pSize == null) || (pSize <= 0)) {
            this.pageSize = null;
        } else {
            this.pageSize = pSize;
        }
        setStartEndRow();
    }

    /**
     * @return Returns the totalItem.
     */
    public Integer getTotalItem() {
        if (totalItem == null) {
            return defaultTotalItem;
        }

        return totalItem;
    }

    /**
     * @param tItem The totalItem to set.
     */
    public void setTotalItem(Integer tItem) {
        if (tItem == null) {
            throw new IllegalArgumentException("TotalItem can't be null.");
        }

        this.totalItem = tItem;

    }

    /**
     * 获取总页数
     *
     * @return 总页数
     */
    public int getTotalPage() {
        int pgSize = this.getPageSize();
        int total = this.getTotalItem();
        int result = total / pgSize;

        if ((total == 0) || ((total % pgSize) != 0)) {
            result++;
        }

        return result;
    }


    /**
     * @return Returns the endRow.
     */
    public int getEndRow() {
        return endRow;
    }

    /**
     * @param endRow The endRow to set.
     */
    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    /**
     * @return Returns the startRow.
     */
    public int getStartRow() {
        return startRow;
    }

    /**
     * @param startRow The startRow to set.
     */
    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    /**
     * 判断是否有下一页, 并且设置当前页码为下一页
     *
     * @return boolean 是否还有下一页
     */
    public boolean nextPage() {
        if (this.currentPage != null && this.currentPage >= this.getTotalPage()) {
            return false;
        }
        if (this.currentPage == null) {
            this.setCurrentPage(defaultFristPage);
        } else {
            this.setCurrentPage(getNextPage());
        }
        return true;
    }

    @Override
    public String toString() {
        return "QueryBase{" +
                "totalItem=" + totalItem +
                ", pageSize=" + pageSize +
                ", currentPage=" + currentPage +
                ", startRow=" + startRow +
                ", endRow=" + endRow +
                '}';
    }
}
