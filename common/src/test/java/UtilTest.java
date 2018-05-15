import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bon.common.util.MyLog;
import com.bon.common.util.POIUtil;
import org.apache.ibatis.annotations.Mapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
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
}
