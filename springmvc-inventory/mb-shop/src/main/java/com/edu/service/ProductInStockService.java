package com.edu.service;

import com.edu.entity.Invoice;
import com.edu.entity.ProductInStock;
import com.edu.model.Paging;

import java.util.List;

public interface ProductInStockService {
    List<ProductInStock> getAllProductInStock();

    List<ProductInStock> getAllProductInStock(ProductInStock productInStock, Paging paging);

    List<ProductInStock> findProductInStockByProperty(String property, Object value);

    void createOrUpdate(Invoice invoice) throws Exception;
}
