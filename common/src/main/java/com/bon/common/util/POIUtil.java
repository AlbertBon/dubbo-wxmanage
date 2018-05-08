package com.bon.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: dubbo-wxmanage
 * @description: excel导入导出工具
 * @author: Bon
 * @create: 2018-05-07 11:46
 **/
public class POIUtil {

    public static List<String> excelSqlImport(String filePath) throws Exception {
        if (filePath == null) {
            throw new Exception("路径不能为空");
        }
        FileInputStream fis = new FileInputStream(filePath);
        if (filePath.endsWith("xls")) {
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            return generateSql(workbook);
        } else if (filePath.endsWith("xlsx")) {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            return generateSql(workbook);
        } else {
            throw new Exception("文件格式不对");
        }
    }

    /**
     * @param file
     * @Author: Bon
     * @Description: 导入xls
     * @return: java.lang.String
     * @Date: 2018/5/7 14:16
     */
    private static List<String> generateSql(Workbook workbook) throws Exception {
        List<String> list = new ArrayList<>();
        String tableName = "";//表名
        String tableComment = "";//表备注
        String nameStr = "";//字段名
        String typeStr = "";//数据类型
        String lengthStr = "";//数据长度
        String isNullStr = "";//是否为空 Y为空 N不为空
        String defaultStr = "";//默认值
        String commentStr = "";//注释
        String sql = "";//sql语句
        String tempStr = "";//临时字符串
        /*遍历sheet*/
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            /*获取表名和备注*/
            tableName = sheet.getRow(1).getCell(0).getRichStringCellValue().getString();
            tableComment = sheet.getRow(1).getCell(1).getRichStringCellValue().getString();
            /*开始写数据库语句，如果数据库中已存在表，则删除表*/
            sql = "\nDROP TABLE IF EXISTS `" + tableName + "`;\n";
            sql += "CREATE TABLE `" + tableName + "` ( \n";
            /*从第二行开始遍历*/
            for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
                Row row = sheet.getRow(j);
                /*从第三列开始遍历*/
                for (int k = 2; k < row.getPhysicalNumberOfCells(); k++) {
                    Cell cell = row.getCell(k);
                    /*处理单元格的值*/
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            tempStr = cell.getRichStringCellValue().getString();
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                tempStr = cell.getDateCellValue() + "";
                            } else {
                                tempStr = cell.getNumericCellValue() + "";
                            }
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            tempStr = cell.getBooleanCellValue() + "";
                            break;
                        default:
                            tempStr = "";
                    }
                    /*每一列处理*/
                    switch (k) {
                        case 2:
                            nameStr = tempStr;
                            break;
                        case 3:
                            typeStr = tempStr;
                            break;
                        case 4:
                            if(StringUtils.isNotBlank(tempStr)){
                                lengthStr = "(" + tempStr + ") ";
                            }else {
                                lengthStr = tempStr;
                            }
                            break;
                        case 5:
                            if (tempStr.equals("N")) {
                                isNullStr = " NOT NULL ";
                            } else {
                                isNullStr = " NULL ";
                            }
                            break;
                        case 6:
                            defaultStr = tempStr;
                            break;
                        case 7:
                            commentStr = tempStr;
                            break;
                    }
                }
                /*根据行数填写信息*/
                if (j == 1) {
                    sql += "  `" + nameStr + "`  bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',PRIMARY KEY (`" + nameStr + "`)";
                } else if (j == sheet.getLastRowNum()) {
                    sql += "  `" + nameStr + "`  " + typeStr + lengthStr + isNullStr + "COMMENT '" + commentStr + "'\n) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment='"+tableComment+"';\n";
                } else {
                    sql += "  `" + nameStr + "`  " + typeStr + lengthStr + isNullStr + "COMMENT '" + commentStr + "'";
                }
                /*加上逗号*/
                if(j<sheet.getLastRowNum()){
                    sql+= ",\n";
                }
            }
            list.add(sql);
        }
        return list;
    }

    /**
     * @param file
     * @Author: Bon
     * @Description: 导入xlsx
     * @return: java.lang.String
     * @Date: 2018/5/7 14:17
     */
    private static String xlsxImport(XSSFWorkbook workbook) throws Exception {

        return "";
    }
}
