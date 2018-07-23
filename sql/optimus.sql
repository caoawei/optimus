/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50628
Source Host           : localhost:3306
Source Database       : optimus

Target Server Type    : MYSQL
Target Server Version : 50628
File Encoding         : 65001

Date: 2018-03-31 14:30:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT,
  `mobile` bigint(12) NOT NULL,
  `password` varchar(255) NOT NULL,
  `mask` varchar(32) NOT NULL,
  `status` int(5) NOT NULL,
  `gmt_created` datetime NOT NULL,
  `gmt_modify` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_user_info
-- ----------------------------
DROP TABLE IF EXISTS `t_user_info`;
CREATE TABLE `t_user_info` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(12) NOT NULL,
  `mobile` bigint(12) NOT NULL,
  `nice_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `id_number` varchar(20) DEFAULT NULL COMMENT '身份证',
  `real_name` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `sex` int(5) DEFAULT NULL COMMENT '性别:(1|2 男|女)',
  `birthday` varchar(15) DEFAULT NULL COMMENT '生日;(yyyyMMdd)',
  `status` int(5) NOT NULL COMMENT '状态',
  `bind_bank_flag` int(5) DEFAULT '0' COMMENT '绑卡标识:(0 未绑卡:1绑卡)',
  `gmt_created` datetime NOT NULL,
  `gmt_modify` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
