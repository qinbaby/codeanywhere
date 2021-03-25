package com.xcc.pjtwo.sys.config;

import com.xcc.pjtwo.sys.enums.DataSourceType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

@Slf4j
public class RoutingDataSourceWithType extends AbstractRoutingDataSource {
    /**
     * 路由数据源类进行初始化
     * @param defaultTargetDataSource 默认的 DataSource
     * @param targetDataSources       配置的所有 DataSource
     */
    public RoutingDataSourceWithType(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
    }
    /**
     *配置的数据源
     */
    @Override
    protected Object determineCurrentLookupKey() {
        //通过ThreadLocal获取到key，来切换数据源
        DataSourceType dataSourceTypeEnum = DataSourceContextHolder.getCurrentDataSource();
        if (log.isDebugEnabled()) {
            log.debug("routing data source address is {}", dataSourceTypeEnum.name());
        }
        return dataSourceTypeEnum;
    }

}