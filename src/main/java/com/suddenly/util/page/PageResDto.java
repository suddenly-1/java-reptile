package com.suddenly.util.page;

import java.util.List;

/**
 * @Author: zzw
 */
public class PageResDto<T> extends BaseDto {

    private List<T> dateList;
    private Integer pageNo;
    private Integer pageSize;
    private Long totalCount;
    private Integer pageCount;

    public PageResDto() {
    }

    public List<T> getDateList() {
        return this.dateList;
    }

    public void setDateList(List<T> dateList) {
        this.dateList = dateList;
    }

    public Integer getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }
}
