package com.edu.service.impl;

import com.edu.dao.ProductInStockDAO;
import com.edu.entity.Invoice;
import com.edu.entity.ProductInStock;
import com.edu.model.Paging;
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
public class ProductInstockServiceImpl implements ProductInStockService {
    final static Logger log = Logger.getLogger(ProductInstockServiceImpl.class);
    @Autowired
    private ProductInStockDAO<ProductInStock> productInStockDAO;

    @Override
    public List<ProductInStock> getAllProductInStock() {
        log.info("[Service::" + "find all ProductInStock]");
        return productInStockDAO.findAll();
    }

    @Override
    public List<ProductInStock> getAllProductInStock(ProductInStock productInStock, Paging paging) {
        log.info("[Service::" + "find ProductInStock by params]");
        Map<String, Object> mapParams = new HashMap<>();
        StringBuilder sql = new StringBuilder("");
        try {
            if (productInStock.getId() != 0) {
                sql.append(" and model.id=:id");
                mapParams.put("id", productInStock.getId());
            }
            if (productInStock.getProductInfoByProductId() != null) {
                if (!StringUtils.isBlank(productInStock.getProductInfoByProductId().getCode())) {
                    sql.append(" and model.productInfoByProductId.code like :code");
                    mapParams.put("code", "%" + productInStock.getProductInfoByProductId().getCode() + "%");
                }
            }
            if (productInStock.getProductInfoByProductId() != null) {
                if (!StringUtils.isBlank(productInStock.getProductInfoByProductId().getName())) {
                    sql.append(" and model.productInfoByProductId.name like :name");
                    mapParams.put("name", "%" + productInStock.getProductInfoByProductId().getName() + "%");
                }
            }
            if (productInStock.getQty() != null && productInStock.getQty() >= 0) {
                sql.append(" and model.qty >= :qty");
                mapParams.put("qty", productInStock.getQty());
            }
            if (productInStock.getPrice() != null && productInStock.getPrice().compareTo(BigDecimal.ZERO) >= 0) {
                sql.append(" and model.price >= :price");
                mapParams.put("price", productInStock.getPrice());
            }
        } catch (Exception e) {
            mapParams = null;
        }
        return productInStockDAO.findAll(sql.toString(), mapParams, paging);
    }

    @Override
    public List<ProductInStock> findProductInStockByProperty(String property, Object value) {
        log.info("[Service::" + "find ProductInStock by property]");
        return productInStockDAO.findByProperty(property, value);
    }

    @Override
    public void createOrUpdate(Invoice invoice) throws Exception {
        List<ProductInStock> listRS = productInStockDAO.findByProperty("productId", invoice.getProductId());
        if (!listRS.isEmpty()) {
            log.info("[Service::" + "update ProductInStock]");
            //1 productInfo - 1 productInStock
            ProductInStock productInStock = listRS.get(0);
            // type =1 receipt , type =-1 issues
            if (invoice.getType() == 1) {
                productInStock.setQty(productInStock.getQty() + invoice.getQty()*(invoice.getActiveFlag()==1 ? 1 : -1));
                productInStock.setPrice(invoice.getPrice());
            } else if (invoice.getType() == -1) {
                productInStock.setQty(productInStock.getQty() - invoice.getQty() * (invoice.getActiveFlag() == 1 ? 1 : -1));
            }
            productInStock.setModifiedDate(new Date());
            productInStockDAO.update(productInStock);
        } else {
            if (invoice.getType() == 1) {
                log.info("[Service::" + "create ProductInStock]");
                ProductInStock productInStock = new ProductInStock();
                productInStock.setProductId(invoice.getProductId());
                productInStock.setQty(invoice.getQty());
                productInStock.setPrice(invoice.getPrice());
                productInStock.setActiveFlag(1);
                productInStock.setCreatedDate(new Date());
                productInStock.setModifiedDate(new Date());
                productInStock.setProductInfoByProductId(invoice.getProductInfoByProductId());
                productInStockDAO.save(productInStock);
            }
        }
    }
}
