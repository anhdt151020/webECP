package com.edu.model;

public class Paging {
    private long totalRecord;
    private int totalPages;
    private int indexPage;
    private int recordPerPage = 10;
    private int offset;

    public Paging(int recordPerPage) {
        this.recordPerPage = recordPerPage;
    }

    public long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(long totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getTotalPages() {
        if (totalRecord > 0) {
            totalPages = (int) Math.ceil(totalRecord / (double) recordPerPage);
        }
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getIndexPage() {
        return indexPage;
    }

    public void setIndexPage(int indexPage) {
        this.indexPage = indexPage;
    }

    public int getRecordPerPage() {
        return recordPerPage;
    }

    public void setRecordPerPage(int recordPerPage) {
        this.recordPerPage = recordPerPage;
    }

    public int getOffset() {
        if (indexPage > 0) {
            offset = indexPage * recordPerPage - recordPerPage;
        }
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
