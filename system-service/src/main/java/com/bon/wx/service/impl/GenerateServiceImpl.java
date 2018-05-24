package com.bon.wx.service.impl;

import com.bon.common.util.MyLog;
import com.bon.common.util.POIUtil;
import com.bon.common.util.StringUtils;
import com.bon.wx.dao.GenerateMapper;
import com.bon.wx.service.GenerateService;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: dubbo-wxmanage
 * @description: 生成sql
 * @author: Bon
 * @create: 2018-05-09 10:45
 **/
@Service
public class GenerateServiceImpl implements GenerateService {

    private static final MyLog LOG = MyLog.getLog(GenerateServiceImpl.class);

    @Autowired
    private GenerateMapper generateMapper;

    @Override
    @Transactional
    public void generateByFilePath(String path) throws Exception {

        LOG.info("开始执行创建表语句");
        List<String> list = new ArrayList<>();
        list = POIUtil.excelSqlImport(path);
        for (String sql : list) {
            generateMapper.createTable(sql);
        }
        LOG.info("创建表完成");

        LOG.info("开始修改generate.xml文件");
        list = POIUtil.getTableNames(path);
        List<String> tableList = new ArrayList<>();
        //循环判断获取到的list是否删除原表，若是需要重新生成实体类
        for(String str : list){
            if(!str.split(",")[1].equals("是")||str.split(",")[1].equals("y")){
                tableList.add(str.split(",")[0]);
            }
        }
        if(tableList.size()<=0){
            LOG.info("没有需要修改的实体类");
            return ;
        }
        //创建Document对象，读取已存在的Xml文件generator.xml
        Document doc = new SAXReader().read(new File(GenerateService.class.getResource("/generator.xml").getFile()));
        //删除所有table标签
        List<Element> elements = doc.getRootElement().element("context").elements();
        for(Element element :elements){
            if(element.getName().equals("table")){
                element.detach();
            }
        }
        //循环添加新的table标签
        for (String tableName : tableList) {
            String domainName = StringUtils.upperCase(StringUtils.underline2Camel(tableName,false));
            //1.得到属性值标签
            Element tableElem = doc.getRootElement().element("context").addElement("table");
            //2.通过增加同名属性的方法，修改属性值----key相同，覆盖；不存在key，则添加
            tableElem.addAttribute("tableName", tableName).addAttribute("domainObjectName", domainName)
                    .addAttribute("enableCountByExample", "false").addAttribute("enableUpdateByExample", "false")
                    .addAttribute("enableDeleteByExample", "false").addAttribute("enableSelectByExample", "false")
                    .addAttribute("selectByExampleQueryId", "false");
            tableElem.addElement("property").addAttribute("name", "useActualColumnNames").addAttribute("value", "false");
        }
        //指定文件输出的位置
        FileOutputStream out =new FileOutputStream(GenerateService.class.getResource("/generator.xml").getFile());
        // 指定文本的写出的格式：
        OutputFormat format=OutputFormat.createPrettyPrint();   //漂亮格式：有空格换行
        format.setEncoding("UTF-8");
        //1.创建写出对象
        XMLWriter writer=new XMLWriter(out,format);
        //2.写出Document对象
        writer.write(doc);
        //3.关闭流
        writer.close();
        LOG.info("修改generate.xml文件完成");


        try {
            LOG.info("开始生成实体类 ...");
            List<String> warnings = new ArrayList<String>();
            boolean overwrite = true;
            File configFile = new File(GenerateService.class.getResource("/generator.xml").getFile());
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
            LOG.info("实体类生成完毕");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLParserException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generateByFile(File file) {
        try {
            generateByFilePath(file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
