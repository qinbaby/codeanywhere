package com.xcc.pjtwo.sys.config;

import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.common.collect.ImmutableMap;
import com.xcc.pjtwo.sys.aop.RoutingDataSourceAOP;
import com.xcc.pjtwo.sys.enums.DataSourceType;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 动态配置数据源
 **/
@Configuration
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class, HikariDataSource.class})
@EnableConfigurationProperties(MybatisProperties.class)
public class RoutingDataSourceAutoConfiguration {

    /**
     * 配置master数据源
     *
     */
    @Bean(name = "masterDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 配置slave数据源,
     *
     */
    @Bean(name = "slaveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 初始化路由DataSource
     */
    @Bean
    public DataSource dataSource(
            @Autowired @Qualifier("masterDataSource") DataSource masterDataSource,
            @Autowired @Qualifier("slaveDataSource") DataSource slaveDataSource) {
        DataSource defaultTargetDataSource;
        Map<Object, Object> targetDataSources = ImmutableMap.of(
                DataSourceType.MASTER, defaultTargetDataSource = masterDataSource,
                DataSourceType.SLAVE, slaveDataSource);
        return new RoutingDataSourceWithType(defaultTargetDataSource, targetDataSources);
    }

    /**
     * 使用SqlSessionFactoryBean配置MyBatis的SqlSessionFactory
     **/
    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory(
            @Autowired @Qualifier("dataSource") DataSource routingDataSourceWithType,
            @Autowired MybatisProperties mybatisProperties,
            @Autowired ResourceLoader resourceLoader) throws Exception {

        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(routingDataSourceWithType);
        // 设置configuration
        org.apache.ibatis.session.Configuration configuration = mybatisProperties.getConfiguration();
        factory.setConfiguration(configuration);
        // 设置SqlSessionFactory属性
        String configLocation;
        if (StringUtils.isNotBlank(configLocation = mybatisProperties.getConfigLocation())) {
            factory.setConfigLocation(resourceLoader.getResource(configLocation));
        }
        Resource[] resolveMapperLocations;
        if (ArrayUtils.isNotEmpty(resolveMapperLocations = mybatisProperties.resolveMapperLocations())) {
            factory.setMapperLocations(resolveMapperLocations);
        }
        String typeHandlersPackage;
        if (StringUtils.isNotBlank(typeHandlersPackage = mybatisProperties.getTypeHandlersPackage())) {
            factory.setTypeHandlersPackage(typeHandlersPackage);
        }
        String typeAliasesPackage;
        if (StringUtils.isNotBlank(typeAliasesPackage = mybatisProperties.getTypeAliasesPackage())) {
            factory.setTypeAliasesPackage(typeAliasesPackage);
        }
        return factory.getObject();
    }

    /**
     * 使用routingDataSourceWithAddress配置数据库事务
     */
    @Bean
    @ConditionalOnMissingBean
    public DataSourceTransactionManager dataSourceTransactionManager(
            @Autowired @Qualifier("dataSource") DataSource routingDataSourceWithAddress) {
        return new DataSourceTransactionManager(routingDataSourceWithAddress);
    }

    /**
     * 编程式事务
     */
    @Bean
    public TransactionTemplate transactionTemplate(
            @Autowired @Qualifier("dataSourceTransactionManager") PlatformTransactionManager platformTransactionManager) {
        return new TransactionTemplate(platformTransactionManager);
    }

    @Bean
    @ConditionalOnMissingBean(RoutingDataSourceAOP.class)
    public RoutingDataSourceAOP routingDataSourceAOP() {
        return new RoutingDataSourceAOP();
    }

}
