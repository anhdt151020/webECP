package com.edu.controller;

import com.edu.entity.Category;
import com.edu.entity.ProductInfo;
import com.edu.model.Paging;
import com.edu.service.CategoryService;
import com.edu.service.ProductInfoService;
import com.edu.util.Constant;
import com.edu.validate.ProductInfoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductInfoController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductInfoValidator productInfoValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if (binder.getTarget() == null) return;
        if (binder.getTarget().getClass() == ProductInfo.class) {
            binder.setValidator(productInfoValidator);
        }
    }

    @RequestMapping(value = {"/product-info/list", "/product-info/list/"})
    public String moveListProductInfo() {
        return "redirect:/product-info/list/1";
    }

    @RequestMapping(value = "/product-info/list/{page}")
    public String showProductInfoList(Model model, @ModelAttribute("searchForm") ProductInfo mProductInfo, @PathVariable("page") int page, HttpSession session) {
        Paging paging = new Paging(3);
        paging.setIndexPage(page);
        List<ProductInfo> productInfos = productInfoService.getAllProductInfo(mProductInfo, paging);
        if (session.getAttribute(Constant.MSG_SUCCESS) != null) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if (session.getAttribute(Constant.MSG_ERROR) != null) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("productInfos", productInfos);
        return "productInfo-list";
    }

    @GetMapping("/product-info/add")
    public String add(Model model) {
        model.addAttribute("titlePage", "Add ProductInfo");
        model.addAttribute("mProductInfo", new ProductInfo());
        model.addAttribute("viewOnly", false);
        model.addAttribute("mapCategory", initMapCategory());
        return "productInfo-action";
    }

    @GetMapping("/product-info/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        ProductInfo productInfo = productInfoService.findProductInfoById(id);
        if (productInfo != null) {
            model.addAttribute("titlePage", "Edit ProductInfo");
            model.addAttribute("mProductInfo", productInfo);
            model.addAttribute("viewOnly", false);
            model.addAttribute("mapCategory", initMapCategory());
            return "productInfo-action";
        }
        return "redirect:/product-info/list";
    }

    @GetMapping("/product-info/view/{id}")
    public String view(Model model, @PathVariable("id") int id) {
        ProductInfo productInfo = productInfoService.findProductInfoById(id);
        if (productInfo != null) {
            model.addAttribute("titlePage", "View ProductInfo");
            model.addAttribute("mProductInfo", productInfo);
            model.addAttribute("viewOnly", true);
            return "productInfo-action";
        }
        return "redirect:/product-info/list";
    }

    @PostMapping("/product-info/save")
    public String save(Model model, @ModelAttribute("mProductInfo") @Validated ProductInfo mProductInfo, BindingResult result, HttpSession session) {
        String pictureDir = session.getServletContext().getRealPath("/") + File.separator + "static" + File.separator + "picture";
        if (result.hasErrors()) {
            if (mProductInfo.getId() != 0) {
                model.addAttribute("titlePage", "Edit ProductInfo");
            } else {
                model.addAttribute("titlePage", "Add ProductInfo");
            }
            model.addAttribute("mProductInfo", mProductInfo);
            model.addAttribute("viewOnly", false);
            model.addAttribute("mapCategory", initMapCategory());
            return "productInfo-action";
        }
        mProductInfo.setCategoryByCateId(categoryService.findCategoryById(mProductInfo.getCateId()));
        if (mProductInfo.getId() != 0) {
            try {
                doUploadFile(mProductInfo, pictureDir);
                productInfoService.updateProductInfo(mProductInfo);
                session.setAttribute(Constant.MSG_SUCCESS, "Update success");
            } catch (Exception e) {
                session.setAttribute(Constant.MSG_ERROR, "Update fail");
            }
        } else {
            try {
                doUploadFile(mProductInfo, pictureDir);
                productInfoService.createProductInfo(mProductInfo);
                session.setAttribute(Constant.MSG_SUCCESS, "Insert success");
            } catch (Exception e) {
                session.setAttribute(Constant.MSG_ERROR, "Insert fail");
            }
        }
        return "redirect:/product-info/list";

    }

    @GetMapping("/product-info/delete/{id}")
    public String delete(Model model, @PathVariable("id") int id, HttpSession session) {
        ProductInfo productInfo = productInfoService.findProductInfoById(id);
        if (productInfo != null) {
            try {
                productInfoService.deleteProductInfo(productInfo);
                session.setAttribute(Constant.MSG_SUCCESS, "Delete success");
            } catch (Exception e) {
                session.setAttribute(Constant.MSG_ERROR, "Delete fail");
            }
        }
        return "redirect:/product-info/list";
    }

    private void doUploadFile(ProductInfo mProductInfo, String dir) {
        MultipartFile multipartFile = mProductInfo.getMultipartFile();
        if (!multipartFile.getOriginalFilename().isEmpty()) {
            String uploadDir = dir;
            String imageUUID = multipartFile.getOriginalFilename();
            try {
                File file = new File(new File(uploadDir), imageUUID);
                if (!file.exists()) {
                    file.mkdirs();
                }
                multipartFile.transferTo(file);
                mProductInfo.setImgUrl(imageUUID);
            } catch (Exception e) {
                mProductInfo.setImgUrl("img404.PNG");
            }
        } else {
            mProductInfo.setImgUrl("img404.PNG");
        }
    }

    private Map<String, String> initMapCategory() {
        List<Category> categories = categoryService.getAllCategory();
        Map<String, String> mapCategory = new HashMap<>();
        categories.forEach(i -> {
            mapCategory.put(i.getId() + "", i.getCode());
        });
        return mapCategory;
    }
}
