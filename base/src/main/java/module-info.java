module base {
    requires org.tinylog.api;
    requires com.alibaba.fastjson2;
    requires java.net.http;
    exports com.stocktracker.base;
    exports com.stocktracker.base.entity;
    exports com.stocktracker.base.entity.dto;
    exports com.stocktracker.base.service;
}
