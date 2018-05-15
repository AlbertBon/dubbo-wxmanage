package com.bon.wx.service.impl;

import com.bon.common.util.MyLog;
import com.bon.common.util.POIUtil;
import com.bon.wx.dao.GenerateMapper;
import com.bon.wx.service.GenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: dubbo-wxmanage
 * @description: 生成sql
 * @author: Bon
 * @create: 2018-05-09 10:45
 **/
@Service
public class GenerateServiceImpl implements GenerateService{

    private static final MyLog log = MyLog.getLog(GenerateServiceImpl.class);

    @Autowired
    private GenerateMapper generateMapper;

    @Override
    @Transactional
    public void generateByFilePath(String path) throws Exception{
        List<String> list = new ArrayList<>();
        list = POIUtil.excelSqlImport(path);
        for(String sql:list){
            generateMapper.createTable(sql);
        }
    }

    @Override
    public void generateByFile() {

    }
}
