package com.xcc.pjtwo.sys.annotation;

import com.xcc.pjtwo.sys.enums.DataSourceType;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RoutingDataSource {
    /**
     * 路由的DataSource地址，默认是Master
     * @return
     */
    DataSourceType value() default DataSourceType.MASTER;
}
