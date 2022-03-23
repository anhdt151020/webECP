package com.edu.hutech.paging;

import com.edu.hutech.sort.Sorter;

public class PageRequest implements Pageble {
    private Integer page;
    private Integer maxPageItem;
    private Sorter sorter;

    public PageRequest(Integer page, Integer maxPageItem, Sorter sorter) {
        this.page = page;
        this.maxPageItem = maxPageItem;
        this.sorter = sorter;
    }
    @Override
    public Sorter getSorter() {
        if (this.sorter != null) {
            return this.sorter;
        }
        return null;
    }

    @Override
    public Integer getPage() {
        return this.page;
    }


    @Override
    public Integer getLimit() {
        return this.maxPageItem;
    }

    @Override
    public Integer getOffset() {
        if (this.page != null && this.maxPageItem != null) {
            return (this.page - 1) * this.maxPageItem;
        }
        return null;
    }
}
