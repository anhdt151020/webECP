package com.edu.model;

import com.edu.entity.Invoice;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class InvoiceReport extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        Sheet sheet = workbook.createSheet("data");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("#");
        header.createCell(1).setCellValue("Id");
        header.createCell(2).setCellValue("Product Code");
        header.createCell(3).setCellValue("Invoice Code");
        header.createCell(4).setCellValue("Qty");
        header.createCell(5).setCellValue("Price");
        header.createCell(6).setCellValue("Date");

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Invoice> invoices = (List<Invoice>) map.get("key_report");
        try {
            String fileName;
            if (invoices.get(0).getType() == 1) {
                fileName = "attachment;filename=\"goods-receipt-export.xlsx\"";
            }else {
                fileName = "attachment;filename=\"goods-issues-export.xlsx\"";
            }
            httpServletResponse.setHeader("Content-Disposition", fileName);
            int rownum = 1;
            for (Invoice invoice : invoices) {
                Row row = sheet.createRow(rownum++);
                row.createCell(0).setCellValue(rownum - 1);
                row.createCell(1).setCellValue(invoice.getId());
                row.createCell(2).setCellValue(invoice.getProductInfoByProductId().getCode());
                row.createCell(3).setCellValue(invoice.getCode());
                row.createCell(4).setCellValue(invoice.getQty());
                row.createCell(5).setCellValue(invoice.getPrice().toString());
                row.createCell(6).setCellValue(sdf.format(invoice.getModifiedDate()));
            }
        } catch (Exception e) {
            //Row row = sheet.createRow(1);
        }
    }
}
