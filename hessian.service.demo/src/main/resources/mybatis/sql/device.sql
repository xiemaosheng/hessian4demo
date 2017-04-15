DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `device_sn` varchar(20) NOT NULL COMMENT '设备编号',
  `device_cat_id` int(1) DEFAULT NULL COMMENT '设备类型',
  `device_name` varchar(64) DEFAULT NULL COMMENT '设备名称',
  `device_type` varchar(64) DEFAULT NULL COMMENT '设备型号',
  PRIMARY KEY (`device_sn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* 存储过程*/
DROP PROCEDURE IF EXISTS `countDevicesName`;
DELIMITER ;;
CREATE  PROCEDURE `countDevicesName`(IN dName VARCHAR(12),OUT deviceCount INT)
BEGIN
SELECT COUNT(*) INTO deviceCount  FROM cus_device WHERE device_name = dName;

END
;;
DELIMITER ;