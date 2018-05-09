package com.bon.wx.service;

/**
 * @program: dubbo-wxmanage
 * @description: 生成sql
 * @author: Bon
 * @create: 2018-05-09 10:43
 **/
public interface GenerateService {
    void generateByFilePath(String path);
    void generateByFile();
}
