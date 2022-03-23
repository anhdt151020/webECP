package com.edu.controller;

import com.edu.entity.Invoice;
import com.edu.entity.ProductInfo;
import com.edu.entity.Users;
import com.edu.model.InvoiceReport;
import com.edu.model.Paging;
import com.edu.service.InvoiceService;
import com.edu.service.ProductInfoService;
import com.edu.util.Constant;
import com.edu.validate.InvoiceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private InvoiceValidator invoiceValidator;


    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if (binder.getTarget() == null) return;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
        if (binder.getTarget().getClass() == ProductInfo.class) {
            binder.setValidator(invoiceValidator);
        }
    }

//    goods-receipt
    @RequestMapping(value = {"/goods-receipt/list", "/goods-receipt/list/"})
    public String moveListReceipt() {
        return "redirect:/goods-receipt/list/1";
    }

    @RequestMapping(value = "/goods-receipt/list/{page}")
    public String showReceiptList(Model model, @ModelAttribute("searchForm") Invoice mInvoice, @PathVariable("page") int page, HttpSession session) {
        Paging paging = new Paging(8);
        paging.setIndexPage(page);
        mInvoice.setType(Constant.TYPE_GOODS_RECEIPT); // goods receipt
        List<Invoice> invoices = invoiceService.getAllInvoice(mInvoice, paging);
        if (session.getAttribute(Constant.MSG_SUCCESS) != null) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if (session.getAttribute(Constant.MSG_ERROR) != null) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("invoices", invoices);
        return "goods-receipt-list";
    }

    @GetMapping("/goods-receipt/add")
    public String addReceipt(Model model) {
        model.addAttribute("titlePage", "Add Invoice");
        model.addAttribute("mInvoice", new Invoice());
        model.addAttribute("viewOnly", false);
        model.addAttribute("mapProduct", initMapProduct());
        model.addAttribute("checkActive", false);
        return "goods-receipt-action";
    }

    @GetMapping("/goods-receipt/edit/{id}")
    public String editReceipt(Model model, @PathVariable("id") int id) {
        Invoice invoice = invoiceService.findInvoiceById(id);
        if (invoice != null) {
            model.addAttribute("titlePage", "Add Invoice");
            model.addAttribute("mInvoice", invoice);
            model.addAttribute("viewOnly", false);
            model.addAttribute("mapProduct", initMapProduct());
            model.addAttribute("checkActive",invoice.getActiveFlag()==2 ? false : true);
            return "goods-receipt-action";
        }
        return "redirect:/goods-receipt/list";
    }

    @GetMapping("/goods-receipt/view/{id}")
    public String viewReceipt(Model model, @PathVariable("id") int id) {
        Invoice invoice = invoiceService.findInvoiceById(id);
        if (invoice != null) {
            model.addAttribute("titlePage", "View Invoice");
            model.addAttribute("mInvoice", invoice);
            model.addAttribute("viewOnly", true);
            return "goods-receipt-action";
        }
        return "redirect:/goods-receipt/list";
    }

    @PostMapping("/goods-receipt/save")
    public String saveReceipt(Model model, @ModelAttribute("mInvoice") @Validated Invoice mInvoice, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            if (mInvoice.getId() != 0) {
                model.addAttribute("titlePage", "Edit Invoice");
            } else {
                model.addAttribute("titlePage", "Add Invoice");
            }
            model.addAttribute("mInvoice", mInvoice);
            model.addAttribute("viewOnly", false);
            model.addAttribute("mapProduct", initMapProduct());
            return "goods-receipt-action";
        }
        mInvoice.setProductInfoByProductId(productInfoService.findProductInfoById(mInvoice.getProductId()));
        mInvoice.setType(Constant.TYPE_GOODS_RECEIPT);
        mInvoice.setAction(((Users) session.getAttribute(Constant.USER_INFO)).getUsername());
        if (mInvoice.getId() != 0) {
            try {
                invoiceService.updateInvoice(mInvoice);
                session.setAttribute(Constant.MSG_SUCCESS, "Update success");
            } catch (Exception e) {
                session.setAttribute(Constant.MSG_ERROR, "Update fail");
            }
        } else {
            try {
                invoiceService.createInvoice(mInvoice);
                session.setAttribute(Constant.MSG_SUCCESS, "Insert success");
            } catch (Exception e) {
                session.setAttribute(Constant.MSG_ERROR, "Insert fail");
            }
        }
        return "redirect:/goods-receipt/list";

    }

    @GetMapping("/goods-receipt/export")
    public ModelAndView exportReceiptReport() {
        ModelAndView modelAndView = new ModelAndView();
        Invoice invoice = new Invoice();
        invoice.setType(Constant.TYPE_GOODS_RECEIPT);
        List<Invoice> invoices = invoiceService.getAllInvoice(invoice, null);
        modelAndView.addObject("key_report", invoices);
        modelAndView.setView(new InvoiceReport());
        return modelAndView;
    }

    //    goods-issue
    @RequestMapping(value = {"/goods-issue/list", "/goods-issue/list/"})
    public String moveListIssue() {
        return "redirect:/goods-issue/list/1";
    }

    @RequestMapping(value = "/goods-issue/list/{page}")
    public String showIssueList(Model model, @ModelAttribute("searchForm") Invoice mInvoice, @PathVariable("page") int page, HttpSession session) {
        Paging paging = new Paging(8);
        paging.setIndexPage(page);
        mInvoice.setType(Constant.TYPE_GOODS_ISSUES); // goods issue
        List<Invoice> invoices = invoiceService.getAllInvoice(mInvoice, paging);
        if (session.getAttribute(Constant.MSG_SUCCESS) != null) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if (session.getAttribute(Constant.MSG_ERROR) != null) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("invoices", invoices);
        return "goods-issue-list";
    }

    @GetMapping("/goods-issue/add")
    public String addIssue(Model model) {
        model.addAttribute("titlePage", "Add Invoice");
        model.addAttribute("mInvoice", new Invoice());
        model.addAttribute("viewOnly", false);
        model.addAttribute("mapProduct", initMapProduct());
        model.addAttribute("checkActive", false);
        return "goods-issue-action";
    }

    @GetMapping("/goods-issue/edit/{id}")
    public String editIssue(Model model, @PathVariable("id") int id) {
        Invoice invoice = invoiceService.findInvoiceById(id);
        if (invoice != null) {
            model.addAttribute("titlePage", "Add Invoice");
            model.addAttribute("mInvoice", invoice);
            model.addAttribute("viewOnly", false);
            model.addAttribute("mapProduct", initMapProduct());
            model.addAttribute("checkActive", invoice.getActiveFlag() == 2 ? false : true);
            return "goods-issue-action";
        }
        return "redirect:/goods-issue/list";
    }

    @GetMapping("/goods-issue/view/{id}")
    public String viewIssue(Model model, @PathVariable("id") int id) {
        Invoice invoice = invoiceService.findInvoiceById(id);
        if (invoice != null) {
            model.addAttribute("titlePage", "View Invoice");
            model.addAttribute("mInvoice", invoice);
            model.addAttribute("viewOnly", true);
            return "goods-issue-action";
        }
        return "redirect:/goods-issue/list";
    }

    @PostMapping("/goods-issue/save")
    public String saveIssue(Model model, @ModelAttribute("mInvoice") @Validated Invoice mInvoice, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            if (mInvoice.getId() != 0) {
                model.addAttribute("titlePage", "Edit Invoice");
            } else {
                model.addAttribute("titlePage", "Add Invoice");
            }
            model.addAttribute("mInvoice", mInvoice);
            model.addAttribute("viewOnly", false);
            model.addAttribute("mapProduct", initMapProduct());
            return "goods-issue-action";
        }
        mInvoice.setProductInfoByProductId(productInfoService.findProductInfoById(mInvoice.getProductId()));
        mInvoice.setType(Constant.TYPE_GOODS_ISSUES);
        mInvoice.setAction(((Users) session.getAttribute(Constant.USER_INFO)).getUsername());
        if (mInvoice.getId() != 0) { //update
            try {
                invoiceService.updateInvoice(mInvoice);
                session.setAttribute(Constant.MSG_SUCCESS, "Update success");
            } catch (Exception e) {
                session.setAttribute(Constant.MSG_ERROR, "Update fail");
            }
        } else { //create
            try {
                invoiceService.createInvoice(mInvoice);
                session.setAttribute(Constant.MSG_SUCCESS, "Insert success");
            } catch (Exception e) {
                session.setAttribute(Constant.MSG_ERROR, "Insert fail");
            }
        }
        return "redirect:/goods-issue/list";

    }

    @GetMapping("/goods-issue/export")
    public ModelAndView exportIssueReport() {
        ModelAndView modelAndView = new ModelAndView();
        Invoice invoice = new Invoice();
        invoice.setType(Constant.TYPE_GOODS_ISSUES);
        List<Invoice> invoices = invoiceService.getAllInvoice(invoice, null);
        modelAndView.addObject("key_report", invoices);
        modelAndView.setView(new InvoiceReport());
        return modelAndView;
    }

    @GetMapping(value = {"/goods-receipt/change-status/{id}", "/goods-issue/change-status/{id}"})
    public String changeActive(Model model, @PathVariable("id") int id, HttpSession session) {
        Invoice invoice = invoiceService.findInvoiceById(id);
        try {
            String errMess = invoiceService.checkQty(invoice);
            if (errMess.isEmpty()) { // check true
                invoice.setActiveFlag(invoice.getActiveFlag() == 1 ? 2 : 1);
                invoice.setAction(((Users) session.getAttribute(Constant.USER_INFO)).getUsername()); // history note
                invoice.setDescription("Change Status " + invoice.getCode()); // history note
                invoiceService.updateInvoice(invoice);
                session.setAttribute(Constant.MSG_SUCCESS, "Change Status success");
            } else {
                session.setAttribute(Constant.MSG_ERROR, errMess);
            }
        } catch (Exception e) {
            session.setAttribute(Constant.MSG_ERROR, "Change Status fail");
        }
        return invoice.getType() == 1 ? "redirect:/goods-receipt/list" : "redirect:/goods-issue/list";
    }

    private Map<String, String> initMapProduct() {
        List<ProductInfo> productInfos = productInfoService.getAllProductInfo();
        Map<String, String> mapProduct = new HashMap<>();
        productInfos.forEach(i -> {
            mapProduct.put(i.getId() + "", i.getCode());
        });
        return mapProduct;
    }
}
