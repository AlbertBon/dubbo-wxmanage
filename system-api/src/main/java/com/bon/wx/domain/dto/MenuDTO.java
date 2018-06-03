package com.bon.wx.domain.dto;/**
 * 类描述信息
 * com.bon.wx.domain.dto
 *
 * @author pengwen
 * @create 2018/6/3 0003
 **/

import com.bon.common.domain.dto.BaseDTO;
import com.bon.wx.domain.entity.Menu;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @program: 后台
 *
 * @description: 菜单参数类
 *
 * @author: Bon
 *
 * @create: 2018-06-03 17:23
 **/
public class MenuDTO extends BaseDTO<Menu> {

    @ApiModelProperty(value = "ID")
    private Long menuId;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;

    @ApiModelProperty(value = "最后一次更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "菜单地址")
    private String path;

    @ApiModelProperty(value = "跳转地址（如果设置为noredirect会在面包屑导航中无连接）")
    private String redirect;

    @ApiModelProperty(value = "菜单显示名称")
    private String title;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "00:true,01:false如果设置true，会在导航中隐藏")
    private String hidden;

    @ApiModelProperty(value = "00:true,01:false没有子菜单也会显示在导航中")
    private String alwaysShow;

    @ApiModelProperty(value = "子菜单字符组")
    private String children;

    @ApiModelProperty(value = "父菜单id")
    private Long parent;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
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

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

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

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }
}
