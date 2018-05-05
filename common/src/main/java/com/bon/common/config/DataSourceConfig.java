//package com.bon.common.config;
//
//import com.bon.common.util.MyLog;
//import com.github.pagehelper.PageHelper;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Conditional;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Properties;
//
///**
// * Created by SinjinSong on 2017/7/10.
// * 配置读写数据源
// */
//@Configuration
//@EnableTransactionManagement
//@MapperScan(value = "com.bon.wx.dao")
//public class DataSourceConfig {
//
//    private static final MyLog log = MyLog.getLog(DataSourceConfig.class);
//
//    @Value("${spring.datasource.type:}")
//    private Class<? extends DataSource> dataSourceType;
//
//    @Value("${mybatis.mapper-locations:}")
//    private String mapperLocations;
//
//
//    @Bean(name = "init")
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource dataSource() {
//        log.info("-------------------- dataSource init ---------------------");
//        return DataSourceBuilder.create().type(dataSourceType).build();
//    }
//
//    @Bean
//    public SqlSessionFactory sqlSessionFactory() throws Exception {
////        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
////        sqlSessionFactoryBean.setDataSource(dataSource());
//////        sqlSessionFactoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(configLocation));
////        sqlSessionFactoryBean.setMapperLocations(
////                new PathMatchingResourcePatternResolver().getResources(mapperLocations));
////        return sqlSessionFactoryBean.getObject();
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource());
//        //mybatis分页
//        PageHelper pageHelper = new PageHelper();
//        Properties props = new Properties();
//        props.setProperty("dialect", "mysql");
//        props.setProperty("reasonable", "true");
//        props.setProperty("supportMethodsArguments", "true");
//        props.setProperty("returnPageInfo", "check");
//        props.setProperty("params", "count=countSql");
//        pageHelper.setProperties(props); //添加插件
//        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper});
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        DataSource dataSource=dataSource();
//        if(StringUtils.isNotBlank(mapperLocations)){
//            sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/*.xml"));
//        }
//        return sqlSessionFactoryBean.getObject();
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager() throws SQLException {
//        return new DataSourceTransactionManager(dataSource());
//    }
//
//}
