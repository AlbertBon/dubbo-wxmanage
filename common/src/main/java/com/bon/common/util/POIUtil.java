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
        if (StringUtils.isBlank(filePath)) {
            throw new Exception("路径不能为空");
        }
        FileInputStream fis = new FileInputStream(filePath);
        if (filePath.endsWith(".xls")) {
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            return generateSql(workbook);
        } else if (filePath.endsWith(".xlsx")) {//暂时有问题
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            return generateSql(workbook);
        } else {
            throw new Exception("请导入xls或xlsx文档");
        }
    }

    /**
     * @param workbook
     * @Author: Bon
     * @Description: 导入xls
     * @return: java.lang.String
     * @Date: 2018/5/7 14:16
     */
    public static List<String> getTableNames(String filePath) throws Exception {
        if (StringUtils.isBlank(filePath)) {
            throw new Exception("路径不能为空");
        }
        Workbook workbook;
        FileInputStream fis = new FileInputStream(filePath);
        if (filePath.endsWith(".xls")) {
            workbook = new HSSFWorkbook(fis);
        } else if (filePath.endsWith(".xlsx")) {//暂时有问题
            workbook = new XSSFWorkbook(fis);
        } else {
            throw new Exception("请导入xls或xlsx文档");
        }
        List<String> list = new ArrayList<>();
        String tableName = "";//表名
        String isDropTable = "";//是否删除原表
        /*遍历sheet*/
        for (int i = 1; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            /*获取表名*/
            tableName = sheet.getRow(1).getCell(0).getRichStringCellValue().getString();
            /*是否删除原表*/
            isDropTable = sheet.getRow(1).getCell(2).getRichStringCellValue().getString();
            list.add(tableName+","+isDropTable);
        }
        return list;
    }

    /**
     * @param workbook
     * @Author: Bon
     * @Description: 导入xls
     * @return: java.lang.String
     * @Date: 2018/5/7 14:16
     */
    private static List<String> generateSql(Workbook workbook) throws Exception {
        List<String> list = new ArrayList<>();
        String tableName = "";//表名
        String tableComment = "";//表备注
        String isDropTable = "";//是否删除原表
        String nameStr = "";//字段名
        String typeStr = "";//数据类型
        String lengthStr = "";//数据长度
        String isNullStr = "";//是否为空 Y为空 N不为空
        String defaultStr = "";//默认值
        String commentStr = "";//注释
        String sql = "";//sql语句
        String tempStr = "";//临时字符串
        /*遍历sheet*/
        for (int i = 1; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            /*获取表名和备注*/
            tableName = sheet.getRow(1).getCell(0).getRichStringCellValue().getString();
            tableComment = sheet.getRow(1).getCell(1).getRichStringCellValue().getString();
            isDropTable = sheet.getRow(1).getCell(2).getRichStringCellValue().getString();
            /*开始写数据库语句，判断是否需要删除原表*/
            if(isDropTable.equals("是")){
                sql = "DROP TABLE IF EXISTS `" + tableName + "`;";
                list.add(sql);
            }
            /*清空sql语句*/
            sql = "";
            sql += "CREATE TABLE IF NOT EXISTS `" + tableName + "` ( ";
            /*从第二行开始遍历*/
            for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
                Row row = sheet.getRow(j);
                /*从第四列开始遍历*/
                for (int k = 3; k < row.getLastCellNum(); k++) {
                    Cell cell = row.getCell(k);
                    /*处理单元格的值*/
                    if (cell == null) {
                        tempStr = " ";
                    } else {
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_STRING:
                                tempStr = cell.getRichStringCellValue().getString();
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    tempStr = cell.getDateCellValue() + "";
                                } else {
                                    tempStr = (int) cell.getNumericCellValue() + "";
                                }
                                break;
                            case Cell.CELL_TYPE_BOOLEAN:
                                tempStr = cell.getBooleanCellValue() + "";
                                break;
                            default:
                                tempStr = "";
                        }
                    }

                    /*每一列处理，第四列开始*/
                    switch (k) {
                        case 3:
                            nameStr = tempStr;
                            break;
                        case 4:
                            typeStr = tempStr;
                            break;
                        case 5:
                            if (StringUtils.isNotBlank(tempStr)) {
                                lengthStr = "(" + tempStr + ") ";
                            } else {
                                lengthStr = tempStr;
                            }
                            break;
                        case 6:
                            if (tempStr.equals("N")) {
                                isNullStr = " NOT NULL ";
                            } else {
                                isNullStr = " NULL ";
                            }
                            break;
                        case 7:
                            if (StringUtils.isNotBlank(tempStr)) {
                                defaultStr = " DEFAULT '" + tempStr + "' ";
                            }
                            break;
                        case 8:
                            if (StringUtils.isNotBlank(tempStr)) {
                                commentStr = " COMMENT '" + tempStr + "'";
                            }
                            break;
                    }
                }
                /*根据行数填写信息*/
                if (j == 1) {
                    sql += "  `" + nameStr + "`  bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',PRIMARY KEY (`" + nameStr + "`)";
                } else if (j == sheet.getLastRowNum()) {
                    sql += "  `" + nameStr + "`  " + typeStr + lengthStr + isNullStr + defaultStr + commentStr + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 comment='" + tableComment + "';";
                } else {
                    sql += "  `" + nameStr + "`  " + typeStr + lengthStr + isNullStr + defaultStr + commentStr;
                }
                /*加上逗号*/
                if (j < sheet.getLastRowNum()) {
                    sql += ",";
                }
            }
            list.add(sql);
            sql = "";
        }
        return list;
    }

    private static List<String> generateViewSql(Workbook workbook) throws Exception {
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
                for (int k = 2; k < row.getLastCellNum(); k++) {
                    Cell cell = row.getCell(k);
                    /*处理单元格的值*/
                    if (cell == null) {
                        tempStr = "";
                    } else {
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_STRING:
                                tempStr = cell.getRichStringCellValue().getString();
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    tempStr = cell.getDateCellValue() + "";
                                } else {
                                    tempStr = (int) cell.getNumericCellValue() + "";
                                }
                                break;
                            case Cell.CELL_TYPE_BOOLEAN:
                                tempStr = cell.getBooleanCellValue() + "";
                                break;
                            default:
                                tempStr = "";
                        }
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
                            if (StringUtils.isNotBlank(tempStr)) {
                                lengthStr = "(" + tempStr + ") ";
                            } else {
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
                            if (StringUtils.isNotBlank(tempStr)) {
                                defaultStr = " DEFAULT '" + tempStr + "' ";
                            }
                            break;
                        case 7:
                            if (StringUtils.isNotBlank(tempStr)) {
                                commentStr = " COMMENT '" + tempStr + "' ";
                            }
                            break;
                    }
                }
                /*根据行数填写信息*/
                if (j == 1) {
                    sql += "  `" + nameStr + "`  bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',PRIMARY KEY (`" + nameStr + "`)";
                } else if (j == sheet.getLastRowNum()) {
                    sql += "  `" + nameStr + "`  " + typeStr + lengthStr + isNullStr + defaultStr + commentStr + "\n) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment='" + tableComment + "';\n";
                } else {
                    sql += "  `" + nameStr + "`  " + typeStr + lengthStr + isNullStr + defaultStr + commentStr;
                }
                /*加上逗号*/
                if (j < sheet.getLastRowNum()) {
                    sql += ",\n";
                }
            }
            list.add(sql);
        }
        return list;
    }

}
