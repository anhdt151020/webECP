package com.edu.service;

import com.edu.entity.History;
import com.edu.entity.Invoice;
import com.edu.model.Paging;

import java.util.List;

public interface HistoryService {
    List<History> getAllHistory();

    List<History> getAllHistory(History history, Paging paging);

    void createHistory(Invoice invoice);
}
