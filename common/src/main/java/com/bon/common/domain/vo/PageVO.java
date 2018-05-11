package com.bon.common.domain.vo;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @program: dubbo-wxmanage
 * @description: 分页bean
 * @author: Bon
 * @create: 2018-05-11 15:24
 **/
public class PageVO<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int pageNum;
    private int pageSize;
    private int size;
    private long total;
    private List<T> list;

    public PageVO() {

    }

    public PageVO(List<T> list) {
        this(list, 1);
    }

    public PageVO(List<T> list, int navigatePages) {
        if(list instanceof Page) {
            Page page = (Page)list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.list = page;
            this.size = page.size();
            this.total = page.getTotal();
        } else if(list instanceof Collection) {
            this.pageNum = 1;
            this.pageSize = list.size();
            this.list = list;
            this.size = list.size();
            this.total = (long)list.size();
        }

    }

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }


    public String toString() {
        StringBuffer sb = new StringBuffer("PageVO{");
        sb.append("pageNum=").append(this.pageNum);
        sb.append(", pageSize=").append(this.pageSize);
        sb.append(", size=").append(this.size);
        sb.append(", total=").append(this.total);
        sb.append(", list=").append(this.list);
        sb.append('}');
        return sb.toString();
    }

}
