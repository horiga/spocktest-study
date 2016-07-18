/*
 * Copyright (c) 2016 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.horiga.study.spocktest.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class MyDatasourceConfig {

    @Value("${ds.default.username}")
    private String username;

    @Value("${ds.default.password}")
    private String password;

    @Value("${ds.default.driverClassName}")
    private String driverClassName;

    @Value("${ds.default.dataSourceClassName}")
    private String dataSourceClassName;

    @Value("${ds.default.url}")
    private String url;

    @Value("${ds.default.connectionTimeout:10000}")
    private long connectionTimeout;

    /*
    This property controls the maximum amount of time that a connection is allowed to sit idle in the pool
    . This setting only applies when minimumIdle is defined to be less than maximumPoolSize. Whether a
    connection is retired as idle or not is subject to a maximum variation of +30 seconds, and average
    variation of +15 seconds. A connection will never be retired as idle before this timeout. A value of 0
    means that idle connections are never removed from the pool. Default: 600000 (10 minutes)
    */
    @Value("${ds.default.idleTimeout:300000}")
    private long idleTimeout;

    /*
    This property controls the maximum lifetime of a connection in the pool. When a connection reaches this
    timeout it will be retired from the pool, subject to a maximum variation of +30 seconds. An in-use
    connection will never be retired, only when it is closed will it then be removed. We strongly recommend
    setting this value, and it should be at least 30 seconds less than any database-level connection timeout.
     A value of 0 indicates no maximum lifetime (infinite lifetime), subject of course to the idleTimeout
     setting. Default: 1800000 (30 minutes)
     */
    @Value("${ds.default.maxLifeTime:600000}")
    private long maxLifeTime;

    /*
    This property controls the minimum number of idle connections that HikariCP tries to maintain in the pool
    . If the idle connections dip below this value, HikariCP will make a best effort to add additional
    connections quickly and efficiently. However, for maximum performance and responsiveness to spike
    demands, we recommend not setting this value and instead allowing HikariCP to act as a fixed size
    connection pool. Default: same as maximumPoolSize.
     */
    @Value("${ds.default.pool.minIdle:-1}")
    private int minIdle;

    /*
    This property controls the maximum size that the pool is allowed to reach, including both idle and in-use
     connections. Basically this value will determine the maximum number of actual connections to the
     database backend. A reasonable value for this is best determined by your execution environment. When the
      pool reaches this size, and no idle connections are available, calls to getConnection() will block for
      up to connectionTimeout milliseconds before timing out. Default: 10
     */
    @Value("${ds.default.pool.maximumPoolSize:10}")
    private int maximumPoolSize;

    /*
    This property represents a user-defined name for the connection pool and appears mainly in logging and
    JMX management consoles to identify pools and pool configurations. Default: auto-generated
     */
    @Value("${ds.default.pool.poolName:hikari-dbcp}")
    private String poolName;

    /*
     * @see https://github.com/brettwooldridge/HikariCP
     */
    @Bean(destroyMethod = "close")
    public DataSource datasource() {
        HikariConfig config = new HikariConfig();

        //
        config.setDataSourceClassName(dataSourceClassName);
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

        config.setConnectionTimeout(connectionTimeout);
        config.setIdleTimeout(idleTimeout);
        config.setMaxLifetime(maxLifeTime);

        if (minIdle > 0) { config.setMinimumIdle(minIdle); }

        config.setMaximumPoolSize(maximumPoolSize);
        config.setPoolName(poolName);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        // ThreadFactory

        HikariDataSource _ds = new HikariDataSource(config);

        return _ds;
    }

    // TODO
}
