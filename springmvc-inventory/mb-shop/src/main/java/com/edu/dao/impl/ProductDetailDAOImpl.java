package com.edu.dao.impl;

import com.edu.dao.ProductDetailDAO;
import com.edu.entity.ProductDetail;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public class ProductDetailDAOImpl extends BaseDAOImpl<ProductDetail> implements ProductDetailDAO<ProductDetail> {
}
