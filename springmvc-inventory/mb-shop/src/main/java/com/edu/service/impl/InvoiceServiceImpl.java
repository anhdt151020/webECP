package com.edu.service.impl;

import com.edu.dao.InvoiceDAO;
import com.edu.entity.Invoice;
import com.edu.entity.ProductDetail;
import com.edu.model.Paging;
import com.edu.service.HistoryService;
import com.edu.service.InvoiceService;
import com.edu.service.ProductDetailService;
import com.edu.service.ProductInStockService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    final static Logger log = Logger.getLogger(InvoiceServiceImpl.class);
    @Autowired
    private InvoiceDAO<Invoice> invoiceDAO;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private ProductInStockService productInStockService;
    @Autowired
    private ProductDetailService productDetailService;

    @Override
    public List<Invoice> getAllInvoice() {
        log.info("[Service::" + "find all Invoice]");
        return invoiceDAO.findAll();
    }

    @Override
    public List<Invoice> getAllInvoice(Invoice invoice, Paging paging) {
        log.info("[Service::" + "find Invoice by params]");
        Map<String, Object> mapParams = new HashMap<>();
        StringBuilder sql = new StringBuilder("");
        try {
            if (invoice.getId() != 0) {
                sql.append(" and model.id=:id");
                mapParams.put("id", invoice.getId());
            }
            if (invoice.getProductInfoByProductId()!=null && !StringUtils.isBlank(invoice.getProductInfoByProductId().getCode())) {
                sql.append(" and model.productInfoByProductId.code like :productcode");
                mapParams.put("productcode", "%" + invoice.getProductInfoByProductId().getCode() + "%");
            }
            if (!StringUtils.isBlank(invoice.getCode())) {
                sql.append(" and model.code like :code");
                mapParams.put("code", "%" + invoice.getCode() + "%");
            }
            if (invoice.getType() != null && invoice.getType() != 0) {
                sql.append(" and model.type=:type");
                mapParams.put("type", invoice.getType());
            }
            if (invoice.getQty() != null && invoice.getQty() >= 0) {
                sql.append(" and model.qty >= :qty");
                mapParams.put("qty", invoice.getQty());
            }
            if (invoice.getPrice() != null && invoice.getPrice().compareTo(BigDecimal.ZERO) >= 0) {
                sql.append(" and model.price >= :price");
                mapParams.put("price", invoice.getPrice());
            }
            if (invoice.getFromDate() != null) {
                sql.append(" and model.modifiedDate >= :fromDate");
                mapParams.put("fromDate", invoice.getFromDate());
            }
            if (invoice.getToDate() != null) {
                sql.append(" and model.modifiedDate <= :toDate");
                mapParams.put("toDate", invoice.getToDate());
            }

        } catch (Exception e) {
            mapParams = null;
        }
        return invoiceDAO.findAll(sql.toString(), mapParams, paging);
    }

    @Override
    public List<Invoice> findInvoiceByProperty(String property, Object value) {
        log.info("[Service::" + "find Invoice by property]");
        return invoiceDAO.findByProperty(property, value);
    }

    @Override
    public Invoice findInvoiceById(int id) {
        log.info("[Service::" + "find Invoice by id]");
        return invoiceDAO.findById(Invoice.class, id);
    }

    @Override
    public void createInvoice(Invoice invoice) throws Exception {
        log.info("[Service::" + "create Invoice]");
        invoice.setActiveFlag(2); // in process not yet declare productDetail
        invoice.setCreatedDate(new Date());
        invoice.setModifiedDate(new Date());
        invoiceDAO.save(invoice); //create success

        //create lichsu va update stock
        historyService.createHistory(invoice);
        //productInStockService.createOrUpdate(invoice); // dont update stock
    }

    @Override
    public void updateInvoice(Invoice invoice) throws Exception {
        log.info("[Service::" + "update Invoice]");
        invoice.setModifiedDate(new Date());

        Invoice oldInvoice = invoiceDAO.findById(Invoice.class, invoice.getId());
        invoice.setCreatedDate(oldInvoice.getCreatedDate());
        if (invoice.getActiveFlag() == null) {
            invoice.setActiveFlag(oldInvoice.getActiveFlag());
        }
        invoiceDAO.update(invoice); //update success

        //create lichsu va update stock
        historyService.createHistory(invoice);
        if (oldInvoice.getActiveFlag() != invoice.getActiveFlag()) {
            //invoice.setQty(invoice.getQty() - oldInvoice.getQty()); //old 15, update 10 >> stock= inStock + (-5)
            productInStockService.createOrUpdate(invoice);
        }

    }

    @Override
    public String checkQty(Invoice invoice) {
        String message = "";
        String property = (invoice.getType() == 1) ? "receiptcode" : "issuecode";
        List<ProductDetail> list = productDetailService.findProductDetailByProperty(property, invoice.getCode());

        // check receipt(active) has issue(active) -> false
        int countDetailExistInIssue = 0;
        if (invoice.getActiveFlag() == 1 && invoice.getType() == 1) { //receipt(active) exist
            for (ProductDetail item : list) {
                try {
                    if (findInvoiceByProperty("code", item.getIssuecode()).get(0).getActiveFlag() != 2){
                        countDetailExistInIssue++;
                        message = countDetailExistInIssue + " Goods-Issue in use, you must process its,";
                    }
                } catch (Exception e) { // error if code empty || flag = 0
                }
            }
        }
        // check issue(process) has qty > stock: false
        if (invoice.getActiveFlag() == 2 && invoice.getType() == -1) {
            int qtyInStock = productInStockService.findProductInStockByProperty("productId", invoice.getProductId()).get(0).getQty();
            if (invoice.getQty() > qtyInStock) {
                message += "Stock hasn't enough,";
            }
        }

        // check qty == realQty of ProductDetail
        int countDetailByInvoiceCode = list.size();
        if (invoice.getQty() != countDetailByInvoiceCode) {
            message += "Qty available: "+ countDetailByInvoiceCode + "/" + invoice.getQty();
        }
        return message;
    }
}
