-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.5.28 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win32
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 wxmanage 的数据库结构
CREATE DATABASE IF NOT EXISTS `wxmanage` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `wxmanage`;


-- 导出  表 wxmanage.menu 结构
CREATE TABLE IF NOT EXISTS `menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `name` varchar(64) DEFAULT NULL COMMENT '菜单名称',
  `path` varchar(128) DEFAULT NULL COMMENT '菜单地址',
  `component` varchar(128) DEFAULT NULL COMMENT '视图文件路径',
  `redirect` varchar(128) DEFAULT NULL COMMENT '跳转地址（如果设置为noredirect会在面包屑导航中无连接）',
  `title` varchar(64) DEFAULT NULL COMMENT '菜单显示名称',
  `icon` varchar(64) DEFAULT NULL COMMENT '菜单图标',
  `hidden` char(2) DEFAULT NULL COMMENT '00:true,01:false如果设置true，会在导航中隐藏',
  `always_show` char(2) DEFAULT NULL COMMENT '00:true,01:false没有子菜单也会显示在导航中',
  `data_path` varchar(512) DEFAULT NULL COMMENT '数据库id地址',
  `parent` bigint(20) DEFAULT NULL COMMENT '父菜单id',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- 数据导出被取消选择。


-- 导出  表 wxmanage.permission 结构
CREATE TABLE IF NOT EXISTS `permission` (
  `permission_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `permission_name` varchar(32) DEFAULT NULL COMMENT '权限名称',
  `type` char(2) DEFAULT NULL COMMENT '00:菜单权限',
  `object_id` bigint(20) DEFAULT NULL COMMENT '对应表id（菜单权限即为菜单id）',
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- 数据导出被取消选择。


-- 导出  表 wxmanage.role 结构
CREATE TABLE IF NOT EXISTS `role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `role_name` varchar(32) DEFAULT NULL COMMENT '角色名称',
  `role_flag` varchar(32) DEFAULT NULL COMMENT '角色标识',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- 数据导出被取消选择。


-- 导出  表 wxmanage.role_permission 结构
CREATE TABLE IF NOT EXISTS `role_permission` (
  `role_permission_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `permission_id` bigint(20) DEFAULT NULL COMMENT '权限id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`role_permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- 数据导出被取消选择。


-- 导出  表 wxmanage.user 结构
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `phone` char(11) DEFAULT NULL COMMENT '手机',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `telephone` varchar(16) DEFAULT NULL COMMENT '电话',
  `address` varchar(64) DEFAULT NULL COMMENT '地址',
  `username` varchar(64) DEFAULT NULL COMMENT '登录名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `wx_openid` varchar(32) DEFAULT NULL COMMENT '微信openid',
  `app_id` char(32) DEFAULT NULL COMMENT '应用id',
  `secret_key` char(64) DEFAULT NULL COMMENT '密钥',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- 数据导出被取消选择。


-- 导出  表 wxmanage.user_role 结构
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`user_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色映射表';

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
