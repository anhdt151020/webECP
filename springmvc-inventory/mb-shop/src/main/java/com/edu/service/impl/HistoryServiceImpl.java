package com.edu.service.impl;

import com.edu.dao.HistoryDAO;
import com.edu.entity.History;
import com.edu.entity.Invoice;
import com.edu.model.Paging;
import com.edu.service.HistoryService;
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
public class HistoryServiceImpl implements HistoryService {
    final static Logger log = Logger.getLogger(HistoryServiceImpl.class);
    @Autowired
    private HistoryDAO<History> historyDAO;
    @Override
    public List<History> getAllHistory() {
        log.info("[Service::" + "find all History]");
        return historyDAO.findAll();
    }

    @Override
    public List<History> getAllHistory(History history, Paging paging) {
        log.info("[Service::" + "find History by params]");
        Map<String, Object> mapParams = new HashMap<>();
        StringBuilder sql = new StringBuilder("");
        try {
            if (history.getId() != 0) {
                sql.append(" and model.id=:id");
                mapParams.put("id", history.getId());
            }
            if (!StringUtils.isBlank(history.getProductInfoByProductId().getCode())) {
                sql.append(" and model.productInfoByProductId.code like :productcode");
                mapParams.put("productcode", "%" + history.getProductInfoByProductId().getCode() + "%");
            }
            if (!StringUtils.isBlank(history.getCode())) {
                sql.append(" and model.code like :code");
                mapParams.put("code", "%" + history.getCode() + "%");
            }
            if (history.getType() != null && history.getType() != 0) {
                sql.append(" and model.type=:type");
                mapParams.put("type", history.getType());
            }
            if (!StringUtils.isBlank(history.getAction())) {
                sql.append(" and model.action like :action");
                mapParams.put("action", "%" + history.getAction() + "%");
            }
            if (history.getQty() != null && history.getQty() >= 0) {
                sql.append(" and model.qty >= :qty");
                mapParams.put("qty", history.getQty());
            }
            if (history.getPrice() != null && history.getPrice().compareTo(BigDecimal.ZERO) >= 0) {
                sql.append(" and model.price >= :price");
                mapParams.put("price", history.getPrice());
            }
        } catch (Exception e) {
            mapParams = null;
        }
        return historyDAO.findAll(sql.toString(), mapParams, paging);
    }
    @Override
    public void createHistory(Invoice invoice) {
        History history = new History();
        history.setCode(invoice.getCode());
        history.setType(invoice.getType());
        history.setProductId(invoice.getProductId());
        history.setQty(invoice.getQty());
        history.setPrice(invoice.getPrice());
        if (invoice.getActiveFlag() != null) {
            history.setActiveFlag(invoice.getActiveFlag());
        }else {
            history.setActiveFlag(1);
        }
        history.setCreatedDate(new Date());
        history.setModifiedDate(new Date());
        history.setAction(invoice.getAction());
        history.setDescription(invoice.getDescription());
        history.setProductInfoByProductId(invoice.getProductInfoByProductId());
        historyDAO.save(history);
    }
}
