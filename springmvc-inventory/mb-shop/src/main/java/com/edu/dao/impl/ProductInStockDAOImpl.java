package com.edu.dao.impl;

import com.edu.dao.ProductInStockDAO;
import com.edu.entity.ProductInStock;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public class ProductInStockDAOImpl extends BaseDAOImpl<ProductInStock> implements ProductInStockDAO<ProductInStock> {
}
