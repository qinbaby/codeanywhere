package com.xcc.pjtwo.sys.config;

import com.xcc.pjtwo.sys.enums.DataSourceType;

public class DataSourceContextHolder {
    private static final ThreadLocal<DataSourceType> CONTEXT_HOLDER = ThreadLocal.withInitial(() -> DataSourceType.MASTER);

    public static void setCurrentDataSource(DataSourceType dataSourceType) {
        CONTEXT_HOLDER.set(dataSourceType);
    }
    public static DataSourceType getCurrentDataSource() {
        return CONTEXT_HOLDER.get();
    }
    public static void removeDataSource() {
        CONTEXT_HOLDER.remove();
    }
}