
CREATE TABLE `blind_box` (
     `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID（自增主键）',
     `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
     `gmt_modified` datetime DEFAULT NULL COMMENT '最后更新时间',
     `name` varchar(512) DEFAULT NULL COMMENT '盲盒名称',
     `cover` varchar(512) DEFAULT NULL COMMENT '盲盒封面',
     `detail` text COMMENT '详情',
     `identifier` varchar(128) DEFAULT NULL COMMENT '幂等号',
     `state` varchar(128) DEFAULT NULL COMMENT '状态',
     `quantity` bigint DEFAULT NULL COMMENT '盲盒数量',
     `price` decimal(18,6) DEFAULT NULL COMMENT '价格',
     `saleable_inventory` bigint DEFAULT NULL COMMENT '可销售库存',
     `occupied_inventory` bigint DEFAULT NULL COMMENT '已占用库存',
     `create_time` datetime DEFAULT NULL COMMENT '盲盒创建时间',
     `sale_time` datetime DEFAULT NULL COMMENT '盲盒发售时间',
     `sync_chain_time` datetime DEFAULT NULL COMMENT '上链时间',
     `allocate_rule` varchar(512) DEFAULT NULL COMMENT '盲盒分配规则',
     `creator_id` varchar(128) DEFAULT NULL COMMENT '创建者',
     `collection_configs` text COMMENT '藏品配置',
     `book_start_time` datetime DEFAULT NULL COMMENT '预约开始时间',
     `book_end_time` datetime DEFAULT NULL COMMENT '预约结束时间',
     `can_book` int DEFAULT NULL COMMENT '是否可以预约',
     `deleted` int DEFAULT NULL COMMENT '是否逻辑删除，0为未删除，非0为已删除',
     `lock_version` int DEFAULT NULL COMMENT '乐观锁版本号',
     PRIMARY KEY (`id`)
)  ;

CREATE TABLE `blind_box_inventory_stream` (
      `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID（自增主键）',
      `gmt_create` datetime NOT NULL COMMENT '创建时间',
      `gmt_modified` datetime NOT NULL COMMENT '最后更新时间',
      `blind_box_id` bigint DEFAULT NULL COMMENT '盲盒id',
      `changed_quantity` bigint DEFAULT NULL COMMENT '本次变更的数量',
      `price` decimal(18,6) DEFAULT NULL COMMENT '价格',
      `quantity` bigint DEFAULT NULL COMMENT '藏品数量',
      `state` varchar(128)  DEFAULT NULL COMMENT '状态',
      `saleable_inventory` bigint DEFAULT NULL COMMENT '可售库存',
      `occupied_inventory` bigint DEFAULT NULL COMMENT '已占库存',
      `stream_type` varchar(128)  DEFAULT NULL COMMENT '流水类型',
      `identifier` varchar(128)  DEFAULT NULL COMMENT '幂等号',
      `deleted` int DEFAULT NULL COMMENT '是否逻辑删除，0为未删除，非0为已删除',
      `lock_version` int DEFAULT NULL COMMENT '乐观锁版本号',
      `extend_info` varchar(512)  DEFAULT NULL COMMENT '扩展信息',
      PRIMARY KEY (`id`)
)  ;

CREATE TABLE `blind_box_item` (
      `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID（自增主键）',
      `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
      `gmt_modified` datetime DEFAULT NULL COMMENT '最后更新时间',
      `blind_box_id` bigint DEFAULT NULL COMMENT '盲盒id',
      `name` varchar(512) DEFAULT NULL COMMENT '盲盒名称',
      `cover` varchar(512) DEFAULT NULL COMMENT '盲盒封面',
      `collection_name` varchar(512)  DEFAULT NULL COMMENT '藏品名称',
      `collection_cover` varchar(512) DEFAULT NULL COMMENT '藏品封面',
      `collection_detail` text COMMENT '藏品详情',
      `collection_serial_no` varchar(128) DEFAULT NULL COMMENT '持有藏品的序列号',
      `state` varchar(128) DEFAULT NULL COMMENT '状态',
      `user_id` varchar(128) DEFAULT NULL COMMENT '持有人id',
      `purchase_price` decimal(18,6) DEFAULT NULL COMMENT '购入价格',
      `order_id` varchar(128) DEFAULT NULL COMMENT '订单号',
      `deleted` int DEFAULT NULL COMMENT '是否逻辑删除，0为未删除，非0为已删除',
      `lock_version` int DEFAULT NULL COMMENT '乐观锁版本号',
      PRIMARY KEY (`id`)
)  ;
