package com.edu.service;

import com.edu.entity.ProductDetail;
import com.edu.model.Paging;

import java.util.List;

public interface ProductDetailService {
    List<ProductDetail> getAllProductDetail();

    List<ProductDetail> getAllProductDetail(ProductDetail productDetail, Paging paging);

    List<ProductDetail> findProductDetailByProperty(String property, Object value);

    ProductDetail findProductDetailById(int id);

    void createProductDetail(ProductDetail productDetail) throws Exception;

    void updateProductDetail(ProductDetail productDetail) throws Exception;

    void deleteProductDetail(ProductDetail productDetail) throws Exception;
}
