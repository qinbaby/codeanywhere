package com.xcc.pjtwo.sys.aop;

import com.xcc.pjtwo.sys.annotation.RoutingDataSource;
import com.xcc.pjtwo.sys.config.DataSourceContextHolder;
import com.xcc.pjtwo.sys.enums.DataSourceType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

@Aspect
@Component
@Order(10000)
@Slf4j
public class RoutingDataSourceAOP {

    @Pointcut("@annotation(com.xcc.pjtwo.sys.annotation.RoutingDataSource)|| @within(com.xcc.pjtwo.sys.annotation.RoutingDataSource)")
    public void routingDataSourcePointcut() {
    }

    @Around("routingDataSourcePointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RoutingDataSource routerDataSource = method.getAnnotation(RoutingDataSource.class);
        // 如果没有设置则默认为 MASTER
        DataSourceType dataSourceType = Objects.isNull(routerDataSource) ?
                DataSourceType.MASTER : routerDataSource.value();
        // 通过向ThreadLocal设置对应的key，来选择数据源，达到动态切换数据源的目的。
        DataSourceContextHolder.setCurrentDataSource(dataSourceType);
        try {
            return joinPoint.proceed();
        } finally {
            DataSourceContextHolder.removeDataSource();
        }
    }
}
