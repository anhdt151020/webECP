package com.edu.hutech.paging;

import com.edu.hutech.sort.Sorter;

public interface Pageble {
    Sorter getSorter();
    Integer getPage();
    Integer getOffset();
    Integer getLimit();
}
