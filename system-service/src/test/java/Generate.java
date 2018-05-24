import com.bon.common.util.MyLog;
import com.bon.wx.SystemApplication;
import com.bon.wx.dao.GenerateMapper;
import com.bon.wx.service.GenerateService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

/**
 * @program: dubbo-wxmanage
 * @description: 测试类
 * @author: Bon
 * @create: 2018-05-22 11:46
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {SystemApplication.class})
public class Generate {

    private static final MyLog LOG = MyLog.getLog(Generate.class);

    @Autowired
    private GenerateService generateService;

    @Autowired
    private GenerateMapper generateMapper;


    @Before
    public void before() throws Exception {
        LOG.info(String.format("【生成开始】"));
    }

    @After
    public void after() throws Exception {
        LOG.info(String.format("【生成结束】"));
    }

    @Test
    public void generateByPath() throws Exception {
        generateService.generateByFile(new File(GenerateService.class.getResource("/excel/generate.xls").getFile()));
    }


}
