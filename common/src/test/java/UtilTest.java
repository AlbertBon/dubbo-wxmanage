import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bon.common.util.MyLog;
import com.bon.common.util.POIUtil;
import com.bon.common.util.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bon.common.util.StringUtils.camel2Underline;
import static com.bon.common.util.StringUtils.underline2Camel;
import static com.bon.common.util.StringUtils.upperCase;

/**
 * @program: dubbo-wxmanage
 * @description: 工具测试
 * @author: Bon
 * @create: 2018-05-07 12:00
 **/
public class UtilTest {

    private static final MyLog log = MyLog.getLog(UtilTest.class);

    @Before
    public void before() throws Exception {
        log.info(String.format("【测试开始】"));
    }

    @After
    public void after() throws Exception {
        log.info(String.format("【测试结束】"));
    }

    @Test
    public void test(){
        String str="#123#"+"DROP TABLE IF EXISTS `";
        String str1=str.split("#")[1];
        log.info(str1.toString());
    }

    @Test
    public void poiUtil(){
        try {
           List<String> list = POIUtil.excelSqlImport("D:\\test\\generate.xls");
           for (String str:list){
               log.info("sql:{}",str);
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void baseDTO(){
        Map<String,String>  map= new HashMap<>();
        map.put("or:","[{'id=':'3','id=':'4'}]");

        List list = new ArrayList();
        String str= map.get("or:");
        list = JSON.parseArray(str);
        log.info("list:{}",list.toString());

        map = JSONObject.parseObject("{'id =':'3','name =':'4'}",Map.class);
        for (Map.Entry<String,String> en: map.entrySet()) {
            log.info("");
        }
    }

    @Test
    public void mybatisGenerate(){
        try {
            System.out.println("start generator ...");
            List<String> warnings = new ArrayList<String>();
            boolean overwrite = true;
            File configFile = new File(UtilTest.class.getResource("/generator.xml").getFile());
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
            System.out.println("end generator!");
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

    @Test
    public void StringUtils(){
        String line="tb_user";
        String camel=underline2Camel(line,false);
        System.out.println(camel);
        System.out.println(camel2Underline(camel));
    }
    /**
     * 修改：属性值、文本
     */
    @Test
    public void testXmlUpdate() throws DocumentException, IOException{
        String tableName = "user";
        String domainName = StringUtils.upperCase(tableName);
        //创建Document对象，读取已存在的Xml文件generator.xml
        Document doc = new SAXReader().read(new File(UtilTest.class.getResource("/generator.xml").getFile()));
        //1.得到属性值标签
        Element tableElem = doc.getRootElement().element("context").addElement("table");
        //2.通过增加同名属性的方法，修改属性值----key相同，覆盖；不存在key，则添加
        tableElem.addAttribute("tableName", tableName).addAttribute("domainObjectName", domainName)
                .addAttribute("enableCountByExample", "false").addAttribute("enableUpdateByExample", "false")
                .addAttribute("enableDeleteByExample", "false").addAttribute("enableSelectByExample", "false")
                .addAttribute("selectByExampleQueryId", "false");
        tableElem.addElement("property").addAttribute("useActualColumnNames", "false");
//        //创建Document对象，读取已存在的Xml文件person.xml
//        Document doc=new SAXReader().read(new File(UtilTest.class.getResource("/generator.xml").getFile()));
//
//        /**
//         * 修改属性值（方案一）
//         * 方法：使用Attribute类(属性类)的setValue()方法
//         * 1.得到标签对象
//         * 2.得到属性对象
//         * 3.修改属性值
//         */
//    /*    //1.1 得到标签对象
//        Element stuElem=doc.getRootElement().element("student");
//        //1.2 得到id属性对象
//        Attribute idAttr=stuElem.attribute("id");
//        //1.3 修改属性值
//        idAttr.setValue("000001");
//    */
//
//        /**
//         *  修改属性值（方案二）
//         *  方法：Element标签类的addAttribute("attr","value")方法
//         */
//        //1.得到属性值标签
//        Element tableElem=doc.getRootElement().element("context").addElement("table");
//        //2.通过增加同名属性的方法，修改属性值
//        tableElem.addAttribute("tableName", "user1").addAttribute("domainObjectName","User1");  //key相同，覆盖；不存在key，则添加
////        /**
////         * 修改文本
////         * 方法：Element标签类的setTest("新文本值")方法
////         * 1.得到标签对象
////         * 2.修改文本
////         */
////        Element nameElem=doc.getRootElement().element("student").element("name");
////        nameElem.setText("王二麻子");

        //指定文件输出的位置
        FileOutputStream out =new FileOutputStream("d:/test/student.xml");
        // 指定文本的写出的格式：
        OutputFormat format=OutputFormat.createPrettyPrint();   //漂亮格式：有空格换行
        format.setEncoding("UTF-8");
        //1.创建写出对象
        XMLWriter writer=new XMLWriter(out,format);
        //2.写出Document对象
        writer.write(doc);
        //3.关闭流
        writer.close();
    }
}
