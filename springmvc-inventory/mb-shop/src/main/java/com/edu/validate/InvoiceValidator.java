package com.edu.validate;

import com.edu.entity.Invoice;
import com.edu.entity.ProductInfo;
import com.edu.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.util.List;

@Component
public class InvoiceValidator implements Validator {
    @Autowired
    private InvoiceService invoiceService;
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == Invoice.class;
    }

    @Override
    public void validate(Object o, Errors errors) {
        Invoice invoice = (Invoice) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "msg.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "qty", "msg.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "msg.required");
        if (invoice.getCode() != null) {
            List<Invoice> rs = invoiceService.findInvoiceByProperty("code", invoice.getCode());
            if (!rs.isEmpty()) {
                if (invoice.getId() != rs.get(0).getId()) { //neu tim ra 1 inv trung code != current-inv >> error
                    errors.rejectValue("code", "msg.code.exist");
                }
            }
        }
        if (invoice.getQty() != null && invoice.getQty() <= 0) {
            errors.rejectValue("qty", "msg.wrong.qty");
        }
        if (invoice.getPrice() != null && invoice.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            errors.rejectValue("price", "msg.wrong.price");
        }
        if (invoice.getFromDate() != null && invoice.getToDate() != null && invoice.getFromDate().after(invoice.getToDate())) {
            errors.rejectValue("fromDate", "msg.wrong.date");
        }
    }
}
