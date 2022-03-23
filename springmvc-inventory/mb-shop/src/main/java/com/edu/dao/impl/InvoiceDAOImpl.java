package com.edu.dao.impl;

import com.edu.dao.InvoiceDAO;
import com.edu.entity.Invoice;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public class InvoiceDAOImpl extends BaseDAOImpl<Invoice> implements InvoiceDAO<Invoice> {
}
