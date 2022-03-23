package com.edu.controller;

import com.edu.entity.Invoice;
import com.edu.entity.ProductDetail;
import com.edu.entity.ProductInfo;
import com.edu.model.Paging;
import com.edu.service.InvoiceService;
import com.edu.service.ProductDetailService;
import com.edu.service.ProductInfoService;
import com.edu.util.Constant;
import com.edu.validate.ProductDetailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductDetailController {
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private ProductDetailValidator productDetailValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if (binder.getTarget() == null) return;
        if (binder.getTarget().getClass() == ProductDetail.class) {
            binder.setValidator(productDetailValidator);
        }
    }

    @RequestMapping(value = {"/product-detail/list", "/product-detail/list/"})
    public String moveListProductDetail() {
        return "redirect:/product-detail/list/1";
    }

    @RequestMapping(value = "/product-detail/list/{page}")
    public String showProductDetailList(Model model, @ModelAttribute("searchForm") ProductDetail mProductDetail, @PathVariable("page") int page, HttpSession session) {
        Paging paging = new Paging(12);
        paging.setIndexPage(page);
        List<ProductDetail> productDetails = productDetailService.getAllProductDetail(mProductDetail, paging);
        if (session.getAttribute(Constant.MSG_SUCCESS) != null) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if (session.getAttribute(Constant.MSG_ERROR) != null) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("productDetails", productDetails);
        return "productDetail-list";
    }

    @GetMapping("/product-detail/add")
    public String add(Model model) {
        model.addAttribute("titlePage", "Add Product Detail");
        model.addAttribute("mProductDetail", new ProductDetail());
        model.addAttribute("viewOnly", false);
        model.addAttribute("mapProductInfo", initMapProduct());
        model.addAttribute("mapReceiptCode", initReceiptCode(null));
        model.addAttribute("mapIssueCode", initIssueCode(null));
        return "productDetail-action";
    }

    @GetMapping("/product-detail/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        ProductDetail productDetail = productDetailService.findProductDetailById(id);
        if (productDetail != null) {
            model.addAttribute("titlePage", "Edit Product Detail");
            model.addAttribute("mProductDetail", productDetail);
            model.addAttribute("viewOnly", false);
            model.addAttribute("mapProductInfo", initMapProduct());
            model.addAttribute("mapReceiptCode", initReceiptCode(productDetail));
            model.addAttribute("mapIssueCode", initIssueCode(productDetail));
            return "productDetail-action";
        }
        return "redirect:/product-detail/list";
    }

    @GetMapping("/product-detail/view/{id}")
    public String view(Model model, @PathVariable("id") int id) {
        ProductDetail productDetail = productDetailService.findProductDetailById(id);
        if (productDetail != null) {
            model.addAttribute("titlePage", "View Product Detail");
            model.addAttribute("mProductDetail", productDetail);
            model.addAttribute("viewOnly", true);
            return "productDetail-action";
        }
        return "redirect:/product-detail/list";
    }

    @PostMapping("/product-detail/save")
    public String save(Model model, @ModelAttribute("mProductDetail") @Validated ProductDetail mProductDetail, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            if (mProductDetail.getId() != 0) {
                model.addAttribute("titlePage", "Edit Product Detail");
            } else {
                model.addAttribute("titlePage", "Add Product Detail");
            }
            model.addAttribute("mProduct Detail", mProductDetail);
            model.addAttribute("viewOnly", false);
            model.addAttribute("mapProductInfo", initMapProduct());
            return "productDetail-action";
        }
        if (mProductDetail.getId() != 0) {
            try {
                productDetailService.updateProductDetail(mProductDetail);
                session.setAttribute(Constant.MSG_SUCCESS, "Update success");
            } catch (Exception e) {
                session.setAttribute(Constant.MSG_ERROR, "Update fail");
            }
        } else {
            try {
                productDetailService.createProductDetail(mProductDetail);
                session.setAttribute(Constant.MSG_SUCCESS, "Insert success");
            } catch (Exception e) {
                session.setAttribute(Constant.MSG_ERROR, "Insert fail");
            }
        }
        return "redirect:/product-detail/list";

    }

    @GetMapping("/product-detail/delete/{id}")
    public String delete(Model model, @PathVariable("id") int id, HttpSession session) {
        ProductDetail productDetail = productDetailService.findProductDetailById(id);
        if (productDetail != null) {
            try {
                productDetailService.deleteProductDetail(productDetail);
                session.setAttribute(Constant.MSG_SUCCESS, "Delete success");
            } catch (Exception e) {
                session.setAttribute(Constant.MSG_ERROR, "Delete fail");
            }
        }
        return "redirect:/product-detail/list";
    }

    private Map<String, String> initMapProduct() {
        List<ProductInfo> productInfos = productInfoService.getAllProductInfo();
        Map<String, String> mapProductInfo = new HashMap<>();
        productInfos.forEach(i -> {
            mapProductInfo.put(i.getId() + "", i.getCode());
        });
        return mapProductInfo;
    }

    private Map<String, String> initReceiptCode(ProductDetail productDetail) {
        List<Invoice> invoices = invoiceService.getAllInvoice();
        Map<String, String> mapReceptCode = new HashMap<>();
        invoices.forEach(i -> {
            if (i.getType() == 1 && i.getActiveFlag() == 2) {
                mapReceptCode.put(i.getCode(), i.getCode());
            }
        });
        if (productDetail != null) {
            mapReceptCode.put(productDetail.getReceiptcode(), productDetail.getReceiptcode());
        }
        mapReceptCode.put("", "NULL");
        return mapReceptCode;
    }

    private Map<String, String> initIssueCode(ProductDetail productDetail) {
        List<Invoice> invoices = invoiceService.getAllInvoice();
        Map<String, String> mapIssueCode = new HashMap<>();
        invoices.forEach(i -> {
            if (i.getType() == -1 && i.getActiveFlag() == 2) {
                mapIssueCode.put(i.getCode(), i.getCode());
            }
        });
        if (productDetail != null) {
            mapIssueCode.put(productDetail.getIssuecode(), productDetail.getIssuecode());
        }
        mapIssueCode.put("", "NULL");
        return mapIssueCode;
    }


}
