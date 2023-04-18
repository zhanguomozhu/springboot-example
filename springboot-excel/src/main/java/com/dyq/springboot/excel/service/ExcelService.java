package com.dyq.springboot.excel.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface ExcelService {

    void lowerVersionExcel(HttpServletResponse response);

    void hightVersionExcel(HttpServletResponse response);

    void importExcel();

    void exportExcel();

    void importExcel1(MultipartFile file);
}
