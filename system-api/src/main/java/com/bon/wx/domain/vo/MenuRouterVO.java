package com.bon.wx.domain.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @program: 后台
 *
 * @description: 菜单路由视图
 *
 * @author: Bon
 *
 * @create: 2018-06-04 22:33
 **/
public class MenuRouterVO implements Serializable{

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "菜单地址")
    private String path;

    @ApiModelProperty(value = "视图文件路径")
    private String component;

    @ApiModelProperty(value = "跳转地址（如果设置为noredirect会在面包屑导航中无连接）")
    private String redirect;

    @ApiModelProperty(value = "菜单显示名称与图标集合")
    private Meta meta;

    @ApiModelProperty(value = "00:true,01:false如果设置true，会在导航中隐藏")
    private String hidden;

    @ApiModelProperty(value = "00:true,01:false没有子菜单也会显示在导航中")
    private String alwaysShow;

    @ApiModelProperty(value = "子菜单")
    private List<MenuRouterVO> children;

    public class Meta implements Serializable{
        @ApiModelProperty(value = "菜单显示名称")
        private String title;

        @ApiModelProperty(value = "菜单图标")
        private String icon;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public String getHidden() {
        return hidden;
    }

    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    public String getAlwaysShow() {
        return alwaysShow;
    }

    public void setAlwaysShow(String alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    public List<MenuRouterVO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuRouterVO> children) {
        this.children = children;
    }
}
