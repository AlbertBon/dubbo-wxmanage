import com.bon.common.util.MyLog;
import com.bon.common.util.POIUtil;
import com.bon.common.util.StringUtils;
import com.bon.wx.SystemApplication;
import com.bon.wx.dao.GenerateMapper;
import com.bon.wx.service.GenerateService;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: dubbo-wxmanage
 * @description: 测试类
 * @author: Bon
 * @create: 2018-05-22 11:46
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {SystemApplication.class})
public class AppTest {

    private static final MyLog LOG = MyLog.getLog(AppTest.class);

    @Autowired
    private GenerateService generateService;

    @Autowired
    private GenerateMapper generateMapper;


    @Before
    public void before() throws Exception {
        LOG.info(String.format("【测试开始】"));
    }

    @After
    public void after() throws Exception {
        LOG.info(String.format("【测试结束】"));
    }

    @Test
    public void generateByPath() throws Exception {
        generateService.generateByFilePath("D:/test/generate.xls");
    }
}
