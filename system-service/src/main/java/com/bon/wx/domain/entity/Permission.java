package com.bon.wx.domain.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
/**
 * 
 * 实体类对应的数据表为：  permission
 * @author null
 * @date 2018-06-03 17:20:08
 */
@ApiModel(value ="Permission")
public class Permission implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "ID")
    private Long permissionId;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;

    @ApiModelProperty(value = "最后一次更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;

    @ApiModelProperty(value = "权限名称")
    private String permissionName;

    @ApiModelProperty(value = "00:菜单权限")
    private String type;

    @ApiModelProperty(value = "对应表id（菜单权限即为菜单id）")
    private Long objectId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table permission
     *
     * @mbg.generated 2018-06-03 17:20:08
     */
    private static final long serialVersionUID = 1L;

    public Permission(Long permissionId, Date gmtCreate, Date gmtModified, String permissionName, String type, Long objectId) {
        this.permissionId = permissionId;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.permissionName = permissionName;
        this.type = type;
        this.objectId = objectId;
    }

    public Permission() {
        super();
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName == null ? null : permissionName.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }
}