package com.dyq.springboot.excel.controller;


import com.dyq.springboot.excel.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ExcelController {

    @Autowired
    ExcelService excelService;

    @GetMapping("/excel/lower")
    public void lowerExcel(HttpServletRequest request, HttpServletResponse response){
        excelService.lowerVersionExcel(response);
    }

    @GetMapping("/excel/height")
    public void heightExcel(HttpServletRequest request, HttpServletResponse response){
        excelService.hightVersionExcel(response);
    }

    @GetMapping("/excel/import")
    public void importExcel(){
        excelService.importExcel();
    }

    @GetMapping("/excel/import1")
    public void importExcel1(MultipartFile file){
        excelService.importExcel1(file);
    }

    @GetMapping("/excel/export")
    public void exportExcel(){
        excelService.exportExcel();
    }

}
