package com.bon.common.plugins;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @program: dubbo-wxmanage
 * @description: mybatis生成xml覆盖插件；修改生成的方法名
 * @author: Bon
 * @create: 2018-05-07 11:16
 **/
public class OverIsMergeablePlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        try {
            Field field = sqlMap.getClass().getDeclaredField("isMergeable");
            field.setAccessible(true);
            field.setBoolean(sqlMap, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 生成dao
     */
    @Override
    public boolean clientGenerated(Interface interfaze,
                                   TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        /**
         * 添加继承信息
         */
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("Mapper<"
                + introspectedTable.getBaseRecordType() + ">");
        FullyQualifiedJavaType imp = new FullyQualifiedJavaType(
                "tk.mybatis.mapper.common.Mapper");
        /**
         * 添加 extends MybatisBaseMapper
         */
        interfaze.addSuperInterface(fqjt);
        /**
         * 添加import my.mabatis.example.base.MybatisBaseMapper;
         */
        interfaze.addImportedType(imp);
        /**
         * 根据反射机制设置方法名
         */
        for (int i = 0; i < interfaze.getMethods().size(); i++) {
            Method m = interfaze.getMethods().get(i);
            if (m.getName().equals("selectByPrimaryKey")) {
                try {
                    Field field = m.getClass().getDeclaredField("name");
                    field.setAccessible(true);
                    field.set(m, "getById");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        interfaze.getAnnotations().clear();
        return true;
    }

    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        for (int i = 0; i < element.getAttributes().size(); i++) {
            Attribute a = element.getAttributes().get(i);
            if (a.getName().equals("id")) {
                try {
                    Field field = a.getClass().getDeclaredField("value");
                    field.setAccessible(true);
                    field.set(a, "getById");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        return true;
    }

}
