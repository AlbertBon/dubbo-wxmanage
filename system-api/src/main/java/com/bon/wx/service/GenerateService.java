package com.bon.wx.service;

import java.io.File;

/**
 * @program: dubbo-wxmanage
 * @description: 生成sql
 * @author: Bon
 * @create: 2018-05-09 10:43
 **/
public interface GenerateService {
    void generateByFilePath(String path) throws Exception;
    void generateByFile(File file);
}
