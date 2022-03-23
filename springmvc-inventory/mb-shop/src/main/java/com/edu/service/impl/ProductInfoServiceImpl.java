package com.edu.service.impl;

import com.edu.dao.ProductInfoDAO;
import com.edu.entity.ProductInfo;
import com.edu.model.Paging;
import com.edu.service.ProductInfoService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    final static Logger log = Logger.getLogger(ProductInfoServiceImpl.class);
    @Autowired
    private ProductInfoDAO<ProductInfo> productInfoDAO;

    @Override
    public List<ProductInfo> getAllProductInfo() {
        log.info("[Service::" + "find all ProductInfo]");
        return productInfoDAO.findAll();
    }

    @Override
    public List<ProductInfo> getAllProductInfo(ProductInfo productInfo, Paging paging) {
        log.info("[Service::" + "find ProductInfo by params]");
        Map<String, Object> mapParams = new HashMap<>();
        StringBuilder sql = new StringBuilder("");
        try {
            if (productInfo.getId() != 0) {
                sql.append(" and model.id=:id");
                mapParams.put("id", productInfo.getId());
            }
            if (!StringUtils.isBlank(productInfo.getCode())) {
                sql.append(" and model.code like :code");
                mapParams.put("code", "%" + productInfo.getCode() + "%");
            }
            if (!StringUtils.isBlank(productInfo.getName())) {
                sql.append(" and model.name like :name");
                mapParams.put("name", "%" + productInfo.getName() + "%");
            }
        } catch (Exception e) {
            mapParams = null;
        }
        return productInfoDAO.findAll(sql.toString(), mapParams, paging);
    }

    @Override
    public List<ProductInfo> findProductInfoByProperty(String property, Object value) {
        log.info("[Service::" + "find ProductInfo by property]");
        return productInfoDAO.findByProperty(property, value);
    }

    @Override
    public ProductInfo findProductInfoById(int id) {
        log.info("[Service::" + "find ProductInfo by id]");
        return productInfoDAO.findById(ProductInfo.class, id);
    }

    @Override
    public void createProductInfo(ProductInfo productInfo) throws Exception {
        log.info("[Service::" + "create ProductInfo]");
        productInfo.setActiveFlag(1);
        productInfo.setCreatedDate(new Date());
        productInfo.setModifiedDate(new Date());
        productInfoDAO.save(productInfo);
    }

    @Override
    public void updateProductInfo(ProductInfo productInfo) throws Exception {
        log.info("[Service::" + "update ProductInfo]");
        productInfo.setModifiedDate(new Date());

        ProductInfo oldProductInfo = productInfoDAO.findById(ProductInfo.class, productInfo.getId());
        productInfo.setCreatedDate(oldProductInfo.getCreatedDate());
        productInfo.setActiveFlag(oldProductInfo.getActiveFlag());
        productInfoDAO.update(productInfo);
    }

    @Override
    public void deleteProductInfo(ProductInfo productInfo) throws Exception {
        log.info("[Service::" + "delete ProductInfo]");

        ProductInfo oldProductInfo = productInfoDAO.findById(ProductInfo.class, productInfo.getId());
        oldProductInfo.setModifiedDate(new Date());
        oldProductInfo.setActiveFlag(0);
        productInfoDAO.update(oldProductInfo);
    }
}
