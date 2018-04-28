package com.bon.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated Sat Apr 28 14:48:10 CST 2018
     */
    private Long id;

    /**
     * 姓名
     *
     * @mbggenerated Sat Apr 28 14:48:10 CST 2018
     */
    private String name;

    /**
     * 生日
     *
     * @mbggenerated Sat Apr 28 14:48:10 CST 2018
     */
    private Date birthday;

    /**
     * 地址
     *
     * @mbggenerated Sat Apr 28 14:48:10 CST 2018
     */
    private String address;

    /**
     * 创建时间
     *
     * @mbggenerated Sat Apr 28 14:48:10 CST 2018
     */
    private Date gmtCreate;

    /**
     * 最后一次修改时间
     *
     * @mbggenerated Sat Apr 28 14:48:10 CST 2018
     */
    private Date gmtModified;

    private static final long serialVersionUID = 1L;

    public User(Long id, String name, Date birthday, String address, Date gmtCreate, Date gmtModified) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.address = address;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
    }

    public User() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", birthday=").append(birthday);
        sb.append(", address=").append(address);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append("]");
        return sb.toString();
    }
}