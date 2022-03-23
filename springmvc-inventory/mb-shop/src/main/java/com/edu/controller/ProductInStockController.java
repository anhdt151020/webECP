package com.edu.controller;

import com.edu.entity.ProductInStock;
import com.edu.model.Paging;
import com.edu.service.ProductInStockService;
import com.edu.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
public class ProductInStockController {
    @Autowired
    private ProductInStockService productInStockService;

    @RequestMapping(value = {"/product-in-stock/list", "/product-in-stock/list/"})
    public String moveListProductInStock() {
        return "redirect:/product-in-stock/list/1";
    }

    @RequestMapping(value = "/product-in-stock/list/{page}")
    public String showProductInStockList(Model model, @ModelAttribute("searchForm") ProductInStock mProductInStock, @PathVariable("page") int page) {
        Paging paging = new Paging(10);
        paging.setIndexPage(page);
        List<ProductInStock> productInStocks = productInStockService.getAllProductInStock(mProductInStock, paging);
        model.addAttribute("pageInfo", paging);
        model.addAttribute("productInStocks", productInStocks);
        return "productInStock-list";

    }
}
