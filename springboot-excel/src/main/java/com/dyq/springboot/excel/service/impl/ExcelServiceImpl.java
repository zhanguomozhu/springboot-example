package com.dyq.springboot.excel.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dyq.springboot.excel.listener.ExcelListener;
import com.dyq.springboot.excel.listener.UserData;
import com.dyq.springboot.excel.service.ExcelService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class ExcelServiceImpl implements ExcelService {
 
    private final static Logger logger = LoggerFactory.getLogger(ExcelServiceImpl.class);

    @Override
    public void lowerVersionExcel(HttpServletResponse response) {
        logger.info("## Ready to create excel ...");
        try {
            Workbook workbook = new HSSFWorkbook(); //创建了一个全新（里面什么都没有）的工作薄
            Sheet sheet = workbook.createSheet("demo测试");  //创建了一个全新（里面什么都没有）的工作表
            Row row = sheet.createRow(0);  //创建了第一行（空的）
            Cell cell = row.createCell(0);//创建的是第一行的第一个单元格
            cell.setCellValue("这是我第一次玩POI");
            //        把工作薄输出到本地磁盘
//            workbook.write(new FileOutputStream("d://test.xls"));
            download("test.xlsx",response,workbook);
            logger.info("## create excel success...");
        }catch (Exception e){
            logger.info("## create excel fail...:{}",e.getMessage());
        }
    }

    @Override
    public void hightVersionExcel(HttpServletResponse response) {
        logger.info("## Ready to create excel ...");
        try {
            Workbook workbook = new XSSFWorkbook(); //创建了一个全新（里面什么都没有）的工作薄
            Sheet sheet = workbook.createSheet("demo测试");  //创建了一个全新（里面什么都没有）的工作表
            Row row = sheet.createRow(0);  //创建了第一行（空的）
            Cell cell = row.createCell(0);//创建的是第一行的第一个单元格
            cell.setCellValue("这是我第一次玩POI");
            //        把工作薄输出到本地磁盘
//            workbook.write(new FileOutputStream("d://test.xlsx"));
            download("test.xlsx",response,workbook);
            logger.info("## create excel success...");
        }catch (Exception e){
            logger.info("## create excel fail...:{}",e.getMessage());
        }
    }

    @Override
    public void importExcel() {
        logger.info("## Ready to import excel ...");
        try {
            //同步读取文件内容
            FileInputStream inputStream = new FileInputStream(new File("C:/Users/Administrator/Desktop/user.xlsx"));
            List<UserData> list = EasyExcel.read(inputStream).head(UserData.class).sheet().doReadSync();
            logger.info("## import excel success... {}",JSONArray.toJSONString(list));
        } catch (Exception e) {
            logger.info("## import excel fail...:{}",e.getMessage());
        }
    }

    @Override
    public void exportExcel() {
        logger.info("## Ready to export excel ...");
        try {
            List<UserData> dataList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                UserData userEntity = new UserData();
                userEntity.setUid(i);
                userEntity.setUsername("张三" + i);
                dataList.add(userEntity);
            }
            //定义文件输出位置
            FileOutputStream outputStream = new FileOutputStream(new File("C:/Users/Administrator/Desktop/user.xlsx"));
            EasyExcel.write(outputStream, UserData.class).sheet("用户信息").doWrite(dataList);
            logger.info("## export excel success... {}");
        } catch (Exception e) {
            logger.info("## export excel fail...:{}",e.getMessage());
        }
    }

    @Override
    public void importExcel1(MultipartFile file) {
        logger.info("## Ready to import excel ...");
        try {
            ExcelListener listener = new ExcelListener();
            EasyExcel.read(file.getInputStream(), listener).sheet().doRead();
            List<Map<Integer, Map<Integer, String>>> list = listener.getList();
            Map<Integer, String> headTitleMap = listener.getHeadTitleMap();
            List<Map<String, String>> mapList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Map<Integer, Map<Integer, String>> integerMapMap = list.get(i);
                integerMapMap.forEach((k, l) -> {
                    Map<String, String> map = new HashMap<>();
                    l.forEach((y, z) -> {
                        map.put(headTitleMap.get(y), z);
                    });
                    mapList.add(map);
                });
            }
            logger.info("## import excel success... {}",JSONObject.toJSONString(mapList));
        } catch (Exception e) {
            logger.info("## import excel fail...:{}",e.getMessage());
        }
    }


    public void download(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
 