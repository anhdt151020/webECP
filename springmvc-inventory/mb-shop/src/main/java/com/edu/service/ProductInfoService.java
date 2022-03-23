package com.edu.service;

import com.edu.entity.ProductInfo;
import com.edu.model.Paging;

import java.util.List;

public interface ProductInfoService {
    List<ProductInfo> getAllProductInfo();

    List<ProductInfo> getAllProductInfo(ProductInfo productInfo, Paging paging);

    List<ProductInfo> findProductInfoByProperty(String property, Object value);

    ProductInfo findProductInfoById(int id);

    void createProductInfo(ProductInfo productInfo) throws Exception;

    void updateProductInfo(ProductInfo productInfo) throws Exception;

    void deleteProductInfo(ProductInfo productInfo) throws Exception;
}
