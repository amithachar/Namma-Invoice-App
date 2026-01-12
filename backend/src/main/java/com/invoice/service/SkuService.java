package com.invoice.service;

import com.invoice.model.SkuDto;
import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SkuService {

    public List<SkuDto> loadSkus() throws Exception {

        List<SkuDto> list = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(
                new ClassPathResource("sku.xlsx").getInputStream()
        );

        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            list.add(new SkuDto(
                    row.getCell(0).getStringCellValue(),
                    row.getCell(1).getStringCellValue(),
                    row.getCell(2).getNumericCellValue()
            ));
        }

        workbook.close();
        return list;
    }
}
