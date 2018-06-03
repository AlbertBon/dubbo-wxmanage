package com.bon.wx.domain.enums;

/**
 * Enum 权限类型
 */
public enum PermissionType {
    MENU("00", "菜单"),
    OTHER("01", ""),;

    private String key;
    private String value;

    PermissionType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}


