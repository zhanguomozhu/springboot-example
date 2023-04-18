CREATE TABLE `tenant` (
  `id` int(11) NOT NULL,
  `tenant_name` varchar(255) DEFAULT NULL COMMENT '租户名称',
  `type` varchar(255) DEFAULT NULL COMMENT '数据库类型',
  `driver_class_name` varchar(255) DEFAULT NULL COMMENT '数据库驱动',
  `jdbc_url` varchar(255) DEFAULT NULL COMMENT '数据库连接地址',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `data_base` varchar(255) DEFAULT NULL COMMENT '数据库',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='租户数据库信息';

INSERT INTO `tenant` (`id`, `tenant_name`, `type`, `driver_class_name`, `jdbc_url`, `username`, `password`, `data_base`) VALUES (1, 'sass_1', 'mysql', 'com.mysql.jdbc.Driver', 'jdbc:mysql://localhost:3306/sass_1?useUnicode=true&characterEncoding=utf8&useSSL=false', 'root', 'root', 'sass_1');
INSERT INTO `tenant` (`id`, `tenant_name`, `type`, `driver_class_name`, `jdbc_url`, `username`, `password`, `data_base`) VALUES (2, 'sass_2', 'mysql', 'com.mysql.jdbc.Driver', 'jdbc:mysql://localhost:3306/sass_2?useUnicode=true&characterEncoding=utf8&useSSL=false', 'root', 'root', 'sass_2');


CREATE TABLE `person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `sex` int(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;


INSERT INTO `person` (`id`, `name`, `sex`) VALUES (1, 'ccc', 7);
INSERT INTO `person` (`id`, `name`, `sex`) VALUES (2, 'ddd', 8);
