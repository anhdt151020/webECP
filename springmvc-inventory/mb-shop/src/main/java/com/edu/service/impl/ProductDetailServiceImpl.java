package com.edu.service.impl;

import com.edu.dao.ProductDetailDAO;
import com.edu.entity.ProductDetail;
import com.edu.model.Paging;
import com.edu.service.ProductDetailService;
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
public class ProductDetailServiceImpl implements ProductDetailService {
    final static Logger log = Logger.getLogger(ProductDetailServiceImpl.class);
    @Autowired
    private ProductDetailDAO<ProductDetail> productDetailDAO;
    @Autowired
    private ProductInfoService productInfoService;
    @Override
    public List<ProductDetail> getAllProductDetail() {
        log.info("[Service::" + "find all ProductDetail]");
        return productDetailDAO.findAll();
    }

    @Override
    public List<ProductDetail> getAllProductDetail(ProductDetail productDetail, Paging paging) {
        log.info("[Service::" + "find ProductDetail by params]");
        Map<String, Object> mapParams = new HashMap<>();
        StringBuilder sql = new StringBuilder("");
        try {
            if (productDetail.getId() != 0) {
                sql.append(" and model.id=:id");
                mapParams.put("id", productDetail.getId());
            }
            if (!StringUtils.isBlank(productDetail.getSerial())) {
                sql.append(" and model.serial like :serial");
                mapParams.put("serial", "%" + productDetail.getSerial() + "%");
            }
            if (!StringUtils.isBlank(productDetail.getName())) {
                sql.append(" and model.name like :name");
                mapParams.put("name", "%" + productDetail.getName() + "%");
            }
            if (!StringUtils.isBlank(productDetail.getReceiptcode())) {
                sql.append(" and model.receiptcode like :receiptcode");
                mapParams.put("receiptcode", "%" + productDetail.getReceiptcode() + "%");
            }
            if (!StringUtils.isBlank(productDetail.getIssuecode())) {
                sql.append(" and model.issuecode like :issuecode");
                mapParams.put("issuecode", "%" + productDetail.getIssuecode() + "%");
            }
        } catch (Exception e) {
            mapParams = null;
        }
        return productDetailDAO.findAll(sql.toString(), mapParams, paging);
    }

    @Override
    public List<ProductDetail> findProductDetailByProperty(String property, Object value) {
        log.info("[Service::" + "find ProductDetail by property]");
        return productDetailDAO.findByProperty(property, value);
    }

    @Override
    public ProductDetail findProductDetailById(int id) {
        log.info("[Service::" + "find ProductDetail by id]");
        return productDetailDAO.findById(ProductDetail.class, id);
    }

    @Override
    public void createProductDetail(ProductDetail productDetail) throws Exception {
        log.info("[Service::" + "create ProductDetail]");
        productDetail.setActiveFlag(1);
        productDetail.setCreatedDate(new Date());
        productDetail.setModifiedDate(new Date());
        productDetail.setProductInfoByProductId(productInfoService.findProductInfoById(productDetail.getProductId()));
        productDetailDAO.save(productDetail);
    }

    @Override
    public void updateProductDetail(ProductDetail productDetail) throws Exception {
        log.info("[Service::" + "update ProductDetail]");
        productDetail.setModifiedDate(new Date());

        ProductDetail oldProductDetail = productDetailDAO.findById(ProductDetail.class, productDetail.getId());
        productDetail.setCreatedDate(oldProductDetail.getCreatedDate());
        productDetail.setActiveFlag(oldProductDetail.getActiveFlag());
        productDetail.setProductInfoByProductId(productInfoService.findProductInfoById(productDetail.getProductId()));
        productDetailDAO.update(productDetail);
    }

    @Override
    public void deleteProductDetail(ProductDetail productDetail) throws Exception {
        log.info("[Service::" + "delete ProductDetail]");

        ProductDetail oldProductDetail = productDetailDAO.findById(ProductDetail.class, productDetail.getId());
        oldProductDetail.setModifiedDate(new Date());
        oldProductDetail.setActiveFlag(0);
        productDetailDAO.update(oldProductDetail);
    }
}
