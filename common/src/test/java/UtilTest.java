import com.bon.common.util.MyLog;
import com.bon.common.util.POIUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

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
//        File file = new File("D:\\test\\generate.xls");
        try {
           List<String> list = POIUtil.excelSqlImport("D:\\test\\generate.xlsx");
           for (String str:list){
               log.info("sql:{}",str);
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
