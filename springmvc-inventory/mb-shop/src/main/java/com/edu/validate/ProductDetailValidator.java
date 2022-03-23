package com.edu.validate;

import com.edu.entity.Invoice;
import com.edu.entity.ProductDetail;
import com.edu.service.InvoiceService;
import com.edu.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class ProductDetailValidator implements Validator {
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private InvoiceService invoiceService;
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == ProductDetail.class;
    }
    @Override
    public void validate(Object o, Errors errors) {
        ProductDetail productDetail = (ProductDetail) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serial", "msg.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "msg.required");
        ValidationUtils.rejectIfEmpty(errors, "description", "msg.required");
        if (productDetail.getSerial() != null) {
            List<ProductDetail> rs = productDetailService.findProductDetailByProperty("serial", productDetail.getSerial());
            if (rs != null && !rs.isEmpty()) {
                if (productDetail.getId() != rs.get(0).getId()) { //neu tim ra 1 prod trung code != current-prod >> error
                    errors.rejectValue("serial", "msg.code.exist");
                }
            }
        }
        //check invoice
        ProductDetail oldProductDetail = productDetailService.findProductDetailById(productDetail.getId());
        if (oldProductDetail!=null && !productDetail.getReceiptcode().equals(oldProductDetail.getReceiptcode()) && !oldProductDetail.getReceiptcode().isEmpty()) { // when code not change
            try {
                if (invoiceService.findInvoiceByProperty("code", oldProductDetail.getReceiptcode()).get(0).getActiveFlag() != 2) { // when oldcode not in process
                    errors.rejectValue("receiptcode", "msg.wrong.receiptcode");
                }
            } catch (Exception e) { // code not exist or empty
                if (!productDetail.getReceiptcode().isEmpty()) {
                    errors.rejectValue("receiptcode", "msg.wrong.invoice");
                }
            }
        }
        if (oldProductDetail != null && !productDetail.getIssuecode().equals(oldProductDetail.getIssuecode()) && !oldProductDetail.getIssuecode().isEmpty()) { // when code not change
            try {
                if (invoiceService.findInvoiceByProperty("code", oldProductDetail.getIssuecode()).get(0).getActiveFlag() != 2) { // when oldcode not in process
                    errors.rejectValue("issuecode", "msg.wrong.receiptcode");
                }
            } catch (Exception e) { // code not exist or empty
                if (!productDetail.getIssuecode().isEmpty()) {
                    errors.rejectValue("issuecode", "msg.wrong.invoice");
                }
            }
        }

    }
}
