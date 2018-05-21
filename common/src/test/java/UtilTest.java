import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bon.common.util.MyLog;
import com.bon.common.util.POIUtil;
import org.apache.ibatis.annotations.Mapper;
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
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
