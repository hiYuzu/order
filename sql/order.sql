/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : order

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 06/03/2020 09:13:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbl_sys_assemble
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sys_assemble`;
CREATE TABLE `tbl_sys_assemble`  (
  `assemble_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '组装ID',
  `pn_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'pn编码',
  `sn_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'sn编码',
  `assemble_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组装状态',
  `job_no` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生产工作单号',
  `crux_no` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关键部件',
  `assemble_memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组装备注',
  `complete_date` datetime(0) NULL DEFAULT NULL COMMENT '完成时间',
  `opt_user` int(11) NULL DEFAULT NULL COMMENT '操作者',
  `opt_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`assemble_id`) USING BTREE,
  UNIQUE INDEX `un_tsa_sncode`(`sn_code`) USING BTREE,
  INDEX `in_tsa_pncode`(`pn_code`) USING BTREE,
  CONSTRAINT `fk_tsa_pncode` FOREIGN KEY (`pn_code`) REFERENCES `tbl_sys_param` (`param_code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_sys_assemble
-- ----------------------------
INSERT INTO `tbl_sys_assemble` VALUES (4, 'PN1', 'TJUT1-00001', '061', '20191119', '20190001', '型号1组装，关键部件为部件1', '2019-11-19 00:00:00', 1, '2019-11-19 13:51:43');
INSERT INTO `tbl_sys_assemble` VALUES (6, 'PN1', 'TJUT1-00002', '090', '20191119', '20190003', '型号1，关键部件为部件1', '2023-11-19 00:00:00', 1, '2019-11-19 13:56:54');
INSERT INTO `tbl_sys_assemble` VALUES (7, 'PN2', 'TJUT2-00001', '060', '20191119', '20190005', '型号2组装', '2021-11-25 00:00:00', 1, '2019-11-19 13:58:07');

-- ----------------------------
-- Table structure for tbl_sys_assemble_old
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sys_assemble_old`;
CREATE TABLE `tbl_sys_assemble_old`  (
  `old_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '递增ID',
  `assemble_id` bigint(20) UNSIGNED NOT NULL COMMENT '组装ID',
  `begin_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `pass_flag` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否通过',
  `old_memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '测试备注',
  `opt_user` int(11) NULL DEFAULT NULL COMMENT '操作者',
  `opt_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`old_id`) USING BTREE,
  INDEX `in_tsao_assembleid`(`assemble_id`) USING BTREE,
  CONSTRAINT `fk_tsao_assembleid` FOREIGN KEY (`assemble_id`) REFERENCES `tbl_sys_assemble` (`assemble_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_sys_assemble_old
-- ----------------------------
INSERT INTO `tbl_sys_assemble_old` VALUES (1, 6, '2019-11-19 00:00:00', '2019-11-19 00:00:00', 1, '老化', 1, '2019-11-19 14:02:44');

-- ----------------------------
-- Table structure for tbl_sys_assemble_part
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sys_assemble_part`;
CREATE TABLE `tbl_sys_assemble_part`  (
  `part_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '部件ID',
  `assemble_id` bigint(20) UNSIGNED NOT NULL COMMENT '组装ID',
  `part_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部件类型',
  `part_no` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部件序号',
  `part_memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部件备注',
  `opt_user` int(11) NULL DEFAULT NULL COMMENT '操作者',
  `opt_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`part_id`, `assemble_id`) USING BTREE,
  UNIQUE INDEX `un_tsap_partno`(`part_no`) USING BTREE,
  INDEX `in_tsap_assembleid`(`assemble_id`) USING BTREE,
  INDEX `in_tsap_parttype`(`part_type`) USING BTREE,
  CONSTRAINT `fk_tsap_assembleid` FOREIGN KEY (`assemble_id`) REFERENCES `tbl_sys_assemble` (`assemble_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tsap_parttype` FOREIGN KEY (`part_type`) REFERENCES `tbl_sys_param` (`param_code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_sys_assemble_part
-- ----------------------------
INSERT INTO `tbl_sys_assemble_part` VALUES (3, 4, 'PT1', '20190001', '型号1部件1', 1, '2019-11-19 13:51:43');
INSERT INTO `tbl_sys_assemble_part` VALUES (4, 4, 'PT2', '20190002', '型号1部件2', 1, '2019-11-19 13:51:43');
INSERT INTO `tbl_sys_assemble_part` VALUES (9, 6, 'PT1', '20190003', '部件1', 1, '2019-11-19 13:56:53');
INSERT INTO `tbl_sys_assemble_part` VALUES (10, 6, 'PT2', '20190004', '部件2', 1, '2019-11-19 13:56:53');
INSERT INTO `tbl_sys_assemble_part` VALUES (11, 7, 'PT1', '20190005', '', 1, '2019-11-19 13:58:07');

-- ----------------------------
-- Table structure for tbl_sys_assemble_test
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sys_assemble_test`;
CREATE TABLE `tbl_sys_assemble_test`  (
  `test_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '递增ID',
  `assemble_id` bigint(20) UNSIGNED NOT NULL COMMENT '组装ID',
  `begin_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `pass_flag` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否通过',
  `test_memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '测试备注',
  `opt_user` int(11) NULL DEFAULT NULL COMMENT '操作者',
  `opt_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`test_id`) USING BTREE,
  INDEX `in_tsat_assembleid`(`assemble_id`) USING BTREE,
  CONSTRAINT `fk_tsat_assembleid` FOREIGN KEY (`assemble_id`) REFERENCES `tbl_sys_assemble` (`assemble_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_sys_assemble_test
-- ----------------------------
INSERT INTO `tbl_sys_assemble_test` VALUES (1, 6, '2019-11-19 00:00:00', '2019-11-19 00:00:00', 1, '测试通过', 1, '2019-11-19 14:01:38');

-- ----------------------------
-- Table structure for tbl_sys_authority_set
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sys_authority_set`;
CREATE TABLE `tbl_sys_authority_set`  (
  `set_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自动递增ID',
  `user_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户类型',
  `page_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `authority_flag` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '0:不可用；1：可用',
  `opt_user` int(11) NULL DEFAULT NULL COMMENT '操作者',
  `opt_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`set_id`) USING BTREE,
  INDEX `in_tsas_pi`(`page_code`) USING BTREE,
  CONSTRAINT `fk_tsas_pc` FOREIGN KEY (`page_code`) REFERENCES `tbl_sys_page` (`page_code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_sys_authority_set
-- ----------------------------
INSERT INTO `tbl_sys_authority_set` VALUES (1, '1', 'deliver', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (2, '1', 'param', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (3, '2', 'business', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (4, '3', 'install', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (5, '3', 'repair', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (6, '4', 'install', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (7, '4', 'repair', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (8, '5', 'assemble', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (9, '5', 'test', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (10, '5', 'old', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (11, '5', 'stock', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (12, '5', 'authority', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (13, '5', 'business', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (14, '5', 'deliver', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (15, '5', 'install', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (16, '5', 'param', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (17, '5', 'repair', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (18, '5', 'user', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (19, '5', 'news', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (20, '5', 'outsourcing', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (21, '6', 'assemble', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (22, '7', 'assemble', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (23, '7', 'test', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (24, '8', 'assemble', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (25, '8', 'test', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (26, '8', 'old', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (27, '8', 'param', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (28, '9', 'assemble', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (29, '9', 'test', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (30, '9', 'old', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (31, '9', 'stock', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (32, '9', 'deliver', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (33, '9', 'param', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (34, '10', 'param', '1', 3, '2019-08-26 09:45:55');
INSERT INTO `tbl_sys_authority_set` VALUES (35, '10', 'outsourcing', '1', 3, '2019-08-26 09:45:55');

-- ----------------------------
-- Table structure for tbl_sys_deliver
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sys_deliver`;
CREATE TABLE `tbl_sys_deliver`  (
  `deliver_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '发货单ID',
  `deliver_type` int(2) NOT NULL DEFAULT 1 COMMENT '发货单类型（1：生产；2：外购）',
  `contract_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合同编码',
  `contract_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合同类型',
  `customer_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `deliver_date` datetime(0) NULL DEFAULT NULL COMMENT '发货日期',
  `warranty_date` datetime(0) NULL DEFAULT NULL COMMENT '发货保质期',
  `deliver_address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发货起点',
  `business_user` int(11) NOT NULL COMMENT '商务处理人',
  `deliver_remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `deliver_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发货单状态',
  `deliver_no` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '运单号',
  `contract_warranty` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合同质保期',
  `contract_amount` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合同金额',
  `contract_new_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '新合同编码',
  `contract_begin_time` datetime(0) NULL DEFAULT NULL COMMENT '合同开始时间',
  `contract_end_time` datetime(0) NULL DEFAULT NULL COMMENT '合同结束时间',
  `opt_user` int(11) NULL DEFAULT NULL COMMENT '操作者',
  `opt_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`deliver_id`) USING BTREE,
  INDEX `in_tsd_ct`(`contract_type`) USING BTREE,
  INDEX `in_tsd_bu`(`business_user`) USING BTREE,
  INDEX `in_tsd_ou`(`opt_user`) USING BTREE,
  CONSTRAINT `fk_tsd_bu` FOREIGN KEY (`business_user`) REFERENCES `tbl_sys_user` (`user_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_tsd_ct` FOREIGN KEY (`contract_type`) REFERENCES `tbl_sys_param` (`param_code`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_tsd_ou` FOREIGN KEY (`opt_user`) REFERENCES `tbl_sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_sys_deliver
-- ----------------------------
INSERT INTO `tbl_sys_deliver` VALUES (1, 1, 'HT20191119', 'CT1', '彭莉然', '2019-11-19 00:00:00', '2019-11-19 00:00:00', '天津理工大学', 2, 'P/N : TJUT1', '301', 'SF123456789', '3年', '1000000', 'HT20191119', NULL, NULL, 1, '2019-11-19 14:21:39');
INSERT INTO `tbl_sys_deliver` VALUES (2, 2, NULL, NULL, '天津理工大学', NULL, NULL, NULL, 2, '外购物料1', '100', NULL, NULL, NULL, NULL, NULL, NULL, 1, '2019-11-19 14:48:27');
INSERT INTO `tbl_sys_deliver` VALUES (3, 2, NULL, NULL, '天津理工大学', NULL, NULL, NULL, 2, '外购物料2', '101', NULL, NULL, NULL, NULL, NULL, NULL, 1, '2019-11-19 14:49:47');

-- ----------------------------
-- Table structure for tbl_sys_deliver_file
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sys_deliver_file`;
CREATE TABLE `tbl_sys_deliver_file`  (
  `file_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '递增字段ID',
  `install_id` bigint(20) NOT NULL COMMENT '安装ID',
  `file_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `file_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件路径',
  `upload_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传时间',
  `opt_user` int(11) NULL DEFAULT NULL COMMENT '操作者',
  `opt_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`file_id`) USING BTREE,
  INDEX `in_tsdf_ii`(`install_id`) USING BTREE,
  INDEX `in_tsdf_ou`(`opt_user`) USING BTREE,
  CONSTRAINT `fk_tsdf_ii` FOREIGN KEY (`install_id`) REFERENCES `tbl_sys_deliver_install` (`install_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tsdf_ou` FOREIGN KEY (`opt_user`) REFERENCES `tbl_sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tbl_sys_deliver_goods
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sys_deliver_goods`;
CREATE TABLE `tbl_sys_deliver_goods`  (
  `goods_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '发货单货物ID',
  `deliver_id` bigint(20) NOT NULL COMMENT '发货单号',
  `pn_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'pn编码',
  `sn_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'sn编码',
  `install_enterprise` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '安装企业',
  `job_no` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生产工作单号',
  `analyzer_number` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分析仪序列号',
  `goods_amount` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '获取金额',
  `goods_status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '货物状态（0：正常；1：退货）',
  `goods_memo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '货物备注',
  `return_memo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退货信息',
  `goods_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备名称',
  `purchase_contract` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '采购合同',
  `storage_date` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入库日期',
  `goods_supplier` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '供货商名称',
  `deliver_point` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发货起点',
  `warranty_clause` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '质保期条款',
  `opt_user` int(11) NULL DEFAULT NULL COMMENT '操作者',
  `opt_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`goods_id`) USING BTREE,
  INDEX `in_tsdg_di`(`deliver_id`) USING BTREE,
  INDEX `in_tsdg_ou`(`opt_user`) USING BTREE,
  INDEX `fk_tsdg_pc`(`pn_code`) USING BTREE,
  CONSTRAINT `fk_tsdg_di` FOREIGN KEY (`deliver_id`) REFERENCES `tbl_sys_deliver` (`deliver_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tsdg_ou` FOREIGN KEY (`opt_user`) REFERENCES `tbl_sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_tsdg_pc` FOREIGN KEY (`pn_code`) REFERENCES `tbl_sys_param` (`param_code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_sys_deliver_goods
-- ----------------------------
INSERT INTO `tbl_sys_deliver_goods` VALUES (2, 1, 'PN1', 'TJUT1-00002', '天津理工大学', '20191119', NULL, NULL, 0, '', '', NULL, NULL, NULL, NULL, NULL, NULL, 1, '2019-11-19 14:16:41');
INSERT INTO `tbl_sys_deliver_goods` VALUES (3, 2, 'OS1', '20191119001', NULL, NULL, NULL, NULL, 0, '备注', NULL, '物料1', '20191119001', '2019-11-19', '天津理工大学', '天津理工大学', '质保条款', 1, '2019-11-19 14:48:27');
INSERT INTO `tbl_sys_deliver_goods` VALUES (4, 3, 'OS2', '20191119002', NULL, NULL, NULL, NULL, 0, '外购商品备注', NULL, '物料2', '2019111901', '2019-11-19', '天津理工大学', '天津理工大学', '质保条款', 1, '2019-11-19 14:49:47');

-- ----------------------------
-- Table structure for tbl_sys_deliver_goods_part
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sys_deliver_goods_part`;
CREATE TABLE `tbl_sys_deliver_goods_part`  (
  `part_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '部件ID',
  `goods_id` bigint(20) NOT NULL COMMENT '货物ID',
  `part_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部件类型',
  `part_no` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部件序号',
  `part_memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部件备注',
  `opt_user` int(11) NULL DEFAULT NULL COMMENT '操作者',
  `opt_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`part_id`, `goods_id`) USING BTREE,
  UNIQUE INDEX `un_tsdgp_partno`(`part_no`) USING BTREE,
  INDEX `in_tsdgp_goodsid`(`goods_id`) USING BTREE,
  INDEX `in_tsdgp_parttype`(`part_type`) USING BTREE,
  CONSTRAINT `fk_tsdgp_goodsid` FOREIGN KEY (`goods_id`) REFERENCES `tbl_sys_deliver_goods` (`goods_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tsdgp_parttype` FOREIGN KEY (`part_type`) REFERENCES `tbl_sys_param` (`param_code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tbl_sys_deliver_install
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sys_deliver_install`;
CREATE TABLE `tbl_sys_deliver_install`  (
  `install_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '递增id',
  `goods_id` bigint(20) NOT NULL COMMENT '安装获取id',
  `install_user` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '安装人员',
  `begin_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `warranty_period` datetime(0) NULL DEFAULT NULL COMMENT '质保期限',
  `install_status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '安装状态',
  `instrument_brand` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '仪器品牌',
  `install_memo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '安装备注',
  `opt_user` int(11) NULL DEFAULT NULL COMMENT '操作者',
  `opt_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`install_id`) USING BTREE,
  UNIQUE INDEX `in_tsdi_gi`(`goods_id`) USING BTREE,
  INDEX `in_tsdi_ou`(`opt_user`) USING BTREE,
  CONSTRAINT `fk_tsdi_gi` FOREIGN KEY (`goods_id`) REFERENCES `tbl_sys_deliver_goods` (`goods_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tsdi_ou` FOREIGN KEY (`opt_user`) REFERENCES `tbl_sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_sys_deliver_install
-- ----------------------------
INSERT INTO `tbl_sys_deliver_install` VALUES (1, 2, '安装人员1', '2019-11-19 00:00:00', '2019-11-19 00:00:00', '2022-01-19 14:44:17', 1, 'ABC', '备注', 1, '2019-11-19 14:41:50');

-- ----------------------------
-- Table structure for tbl_sys_deliver_repair
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sys_deliver_repair`;
CREATE TABLE `tbl_sys_deliver_repair`  (
  `repair_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '递增字段',
  `install_id` bigint(20) NOT NULL COMMENT '安装ID',
  `repair_user` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '运维人员',
  `repair_time` datetime(0) NULL DEFAULT NULL COMMENT '运维时间',
  `materiel_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '物料名称',
  `materiel_number` int(11) NULL DEFAULT NULL COMMENT '物料数量',
  `opt_user` int(11) NULL DEFAULT NULL COMMENT '操作者',
  `opt_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`repair_id`) USING BTREE,
  INDEX `in_tsdr_ii`(`install_id`) USING BTREE,
  INDEX `fk_tsdr_ou`(`opt_user`) USING BTREE,
  CONSTRAINT `fk_tsdr_ii` FOREIGN KEY (`install_id`) REFERENCES `tbl_sys_deliver_install` (`install_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tsdr_ou` FOREIGN KEY (`opt_user`) REFERENCES `tbl_sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_sys_deliver_repair
-- ----------------------------
INSERT INTO `tbl_sys_deliver_repair` VALUES (1, 1, '维修人员1', '2019-11-19 00:00:00', '物料1', 1, 1, '2019-11-19 14:44:58');

-- ----------------------------
-- Table structure for tbl_sys_news
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sys_news`;
CREATE TABLE `tbl_sys_news`  (
  `news_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '递增字段ID',
  `news_title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `news_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `news_author` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作者',
  `news_show` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否展示',
  `show_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `opt_user` int(11) NULL DEFAULT NULL COMMENT '操作者',
  `opt_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`news_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_sys_news
-- ----------------------------
INSERT INTO `tbl_sys_news` VALUES (1, '公告测试', '<b>公&nbsp;&nbsp;</b><i>告&nbsp;&nbsp;</i><u>测&nbsp;&nbsp;</u><strike>试&nbsp;&nbsp;<img src=\"http://localhost:8085/layui/images/face/54.gif\" alt=\"[good]\"></strike>', '刘凌宇', 1, '2019-12-19', 1, '2019-11-19 14:52:26');

-- ----------------------------
-- Table structure for tbl_sys_page
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sys_page`;
CREATE TABLE `tbl_sys_page`  (
  `page_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '递增id',
  `page_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '页面编码',
  `page_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '页面路径',
  `page_name_en` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '英文名称',
  `page_name_cn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '中文名称',
  `page_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`page_id`) USING BTREE,
  INDEX `page_code`(`page_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_sys_page
-- ----------------------------
INSERT INTO `tbl_sys_page` VALUES (1, 'assemble', 'page/assemble/assembleList.html', 'assemble', '组装', '组装管理');
INSERT INTO `tbl_sys_page` VALUES (2, 'test', 'page/test/testList.html', 'test', '测试', '测试管理');
INSERT INTO `tbl_sys_page` VALUES (3, 'old', 'page/old/oldList.html', 'old', '老化', '老化管理');
INSERT INTO `tbl_sys_page` VALUES (4, 'stock', 'page/stock/stockList.html', 'stock', '库存', '库存管理');
INSERT INTO `tbl_sys_page` VALUES (5, 'outsourcing', 'page/outsourcing/outsourcingList.html', 'outsourcing', '外购', '外购管理');
INSERT INTO `tbl_sys_page` VALUES (6, 'deliver', 'page/deliver/deliverList.html', 'deliver', '发货', '发货管理');
INSERT INTO `tbl_sys_page` VALUES (7, 'business', 'page/business/businessList.html', 'business', '商务', '商务管理');
INSERT INTO `tbl_sys_page` VALUES (8, 'install', 'page/install/installList.html', 'install', '安装', '安装管理');
INSERT INTO `tbl_sys_page` VALUES (9, 'repair', 'page/repair/repairList.html', 'repair', '维修', '维修管理');
INSERT INTO `tbl_sys_page` VALUES (10, 'user', 'page/user/userList.html', 'user', '用户', '用户管理');
INSERT INTO `tbl_sys_page` VALUES (11, 'param', 'page/param/paramList.html', 'param', '参数', '参数管理');
INSERT INTO `tbl_sys_page` VALUES (12, 'authority', 'page/system/authoritySet.html', 'authority', '权限', '权限管理');
INSERT INTO `tbl_sys_page` VALUES (13, 'news', 'page/news/newsList.html', 'news', '公告', '公告管理');

-- ----------------------------
-- Table structure for tbl_sys_param
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sys_param`;
CREATE TABLE `tbl_sys_param`  (
  `param_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '参数ID',
  `param_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参数类型编码',
  `param_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参数编码',
  `param_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数名称',
  `opt_user` int(11) NULL DEFAULT NULL COMMENT '操作者',
  `opt_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`param_id`) USING BTREE,
  UNIQUE INDEX `un_tsp_pc`(`param_code`) USING BTREE,
  INDEX `in_tsp_pt`(`param_type`) USING BTREE,
  INDEX `fk_tsp_ou`(`opt_user`) USING BTREE,
  CONSTRAINT `fk_tsp_ou` FOREIGN KEY (`opt_user`) REFERENCES `tbl_sys_user` (`user_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_tsp_pt` FOREIGN KEY (`param_type`) REFERENCES `tbl_util_param_type` (`type_code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_sys_param
-- ----------------------------
INSERT INTO `tbl_sys_param` VALUES (1, 'PN', 'PN1', '型号1', 1, '2018-03-15 10:30:07');
INSERT INTO `tbl_sys_param` VALUES (2, 'PN', 'PN2', '型号2', 1, '2018-03-15 10:30:19');
INSERT INTO `tbl_sys_param` VALUES (3, 'PT', 'PT1', '部件1', 1, '2019-11-19 13:26:01');
INSERT INTO `tbl_sys_param` VALUES (4, 'PT', 'PT2', '部件2', 1, '2019-11-19 13:27:31');
INSERT INTO `tbl_sys_param` VALUES (5, 'CT', 'CT1', '合同1', 1, '2019-11-19 14:07:30');
INSERT INTO `tbl_sys_param` VALUES (6, 'CT', 'CT2', '合同2', 1, '2019-11-19 14:07:52');
INSERT INTO `tbl_sys_param` VALUES (7, 'CT', 'CT_TS', '技术服务合同', 1, '2019-11-19 14:26:00');
INSERT INTO `tbl_sys_param` VALUES (8, 'OS', 'OS1', '物料1', 1, '2019-11-19 14:46:13');
INSERT INTO `tbl_sys_param` VALUES (9, 'OS', 'OS2', '物料2', 1, '2019-11-19 14:46:29');

-- ----------------------------
-- Table structure for tbl_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sys_user`;
CREATE TABLE `tbl_sys_user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '递增ID',
  `user_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户编码',
  `user_password` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
  `user_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `user_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户类型',
  `stop_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '停用标识（0：正常；1：停用）',
  `user_detail` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户描述',
  `end_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后登录日期',
  `opt_user` int(11) NULL DEFAULT NULL COMMENT '操作者',
  `opt_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `un_tsu_uc`(`user_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_sys_user
-- ----------------------------
INSERT INTO `tbl_sys_user` VALUES (1, 'admin', 'd033e22ae348aeb5660fc2140aec35850c4da997', '管理员', '5', 0, '系统管理', '2020-01-06 11:23:02', 1, '2019-11-19 11:30:07');
INSERT INTO `tbl_sys_user` VALUES (2, 'shangwu', 'd033e22ae348aeb5660fc2140aec35850c4da997', '商务人员1', '2', 0, '商务用户', '', 1, '2019-11-19 14:13:30');

-- ----------------------------
-- Table structure for tbl_util_deliver_status
-- ----------------------------
DROP TABLE IF EXISTS `tbl_util_deliver_status`;
CREATE TABLE `tbl_util_deliver_status`  (
  `status_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '发货单状态ID',
  `status_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发货单状态编码',
  `status_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发货单状态名称',
  `status_stage` int(11) NULL DEFAULT NULL COMMENT '阶段',
  `status_remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发货单状态备注',
  PRIMARY KEY (`status_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_util_deliver_status
-- ----------------------------
INSERT INTO `tbl_util_deliver_status` VALUES (1, '100', '已保存,待提交', 2, '发货');
INSERT INTO `tbl_util_deliver_status` VALUES (2, '101', '已提交,待商务', 2, '发货');
INSERT INTO `tbl_util_deliver_status` VALUES (3, '102', '被拒绝,待修改', 2, '发货');
INSERT INTO `tbl_util_deliver_status` VALUES (4, '200', '已商务,待安装', 2, '发货');
INSERT INTO `tbl_util_deliver_status` VALUES (5, '300', '安装中', 2, '发货');
INSERT INTO `tbl_util_deliver_status` VALUES (6, '301', '已安装', 2, '发货');
INSERT INTO `tbl_util_deliver_status` VALUES (7, '060', '组装中', 1, '组装');
INSERT INTO `tbl_util_deliver_status` VALUES (8, '061', '已组装,待测试', 1, '组装');
INSERT INTO `tbl_util_deliver_status` VALUES (9, '062', '被驳回,待组装', 1, '组装');
INSERT INTO `tbl_util_deliver_status` VALUES (10, '070', '测试中', 1, '组装');
INSERT INTO `tbl_util_deliver_status` VALUES (11, '071', '已测试,待老化', 1, '组装');
INSERT INTO `tbl_util_deliver_status` VALUES (12, '072', '被驳回,待测试', 1, '组装');
INSERT INTO `tbl_util_deliver_status` VALUES (13, '080', '老化中', 1, '组装');
INSERT INTO `tbl_util_deliver_status` VALUES (14, '081', '已老化,待发货', 1, '组装');
INSERT INTO `tbl_util_deliver_status` VALUES (15, '090', '已发货', 1, '组装');
INSERT INTO `tbl_util_deliver_status` VALUES (16, '092', '已重置,待发货', 1, '组装');

-- ----------------------------
-- Table structure for tbl_util_param_type
-- ----------------------------
DROP TABLE IF EXISTS `tbl_util_param_type`;
CREATE TABLE `tbl_util_param_type`  (
  `type_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '参数类型ID',
  `type_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参数类型编码',
  `type_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数类型名称',
  `type_remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数备注',
  PRIMARY KEY (`type_id`) USING BTREE,
  INDEX `type_code`(`type_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_util_param_type
-- ----------------------------
INSERT INTO `tbl_util_param_type` VALUES (1, 'PN', 'P/N码', NULL);
INSERT INTO `tbl_util_param_type` VALUES (2, 'PT', '部件类型', NULL);
INSERT INTO `tbl_util_param_type` VALUES (3, 'CT', '合同类型', NULL);
INSERT INTO `tbl_util_param_type` VALUES (4, 'OS', '外购类型', NULL);

SET FOREIGN_KEY_CHECKS = 1;
