package com.edu.service;

import com.edu.entity.Invoice;
import com.edu.model.Paging;

import java.util.List;

public interface InvoiceService {
    List<Invoice> getAllInvoice();

    List<Invoice> getAllInvoice(Invoice invoice, Paging paging);

    List<Invoice> findInvoiceByProperty(String property, Object value);

    Invoice findInvoiceById(int id);

    void createInvoice(Invoice invoice) throws Exception;

    void updateInvoice(Invoice invoice) throws Exception;

    String checkQty(Invoice invoice);
}
