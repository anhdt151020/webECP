package com.edu.validate;

import com.edu.entity.ProductInfo;
import com.edu.service.ProductInfoService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class ProductInfoValidator implements Validator {
    @Autowired
    private ProductInfoService productInfoService;
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == ProductInfo.class;
    }

    @Override
    public void validate(Object o, Errors errors) {
        ProductInfo productInfo = (ProductInfo) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "msg.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "msg.required");
        ValidationUtils.rejectIfEmpty(errors, "description", "msg.required");
        if (productInfo.getId() != 0 ) {
            ValidationUtils.rejectIfEmpty(errors, "multipartFile", "msg.required");
        }
        if (productInfo.getCode() != null) {
            List<ProductInfo> rs = productInfoService.findProductInfoByProperty("code", productInfo.getCode());
            if (rs != null && !rs.isEmpty()) {
                if (productInfo.getId() != rs.get(0).getId()) { //neu tim ra 1 prod trung code != current-prod >> error
                    errors.rejectValue("code", "msg.code.exist");
                }
            }
        }
        if (!productInfo.getMultipartFile().getOriginalFilename().isEmpty()) {
            String extension = FilenameUtils.getExtension(productInfo.getMultipartFile().getOriginalFilename());
            if (!extension.equals("jpeg") && !extension.equals("jpg") && !extension.equals("png")) {
                errors.rejectValue("multipartFile","msg.wrong.format");
            }
        }
    }
}
