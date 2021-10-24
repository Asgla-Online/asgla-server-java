package com.asgla.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.JMX;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.sql.DataSource;
import java.lang.management.ManagementFactory;

public class Database {

    private static final Logger log = LoggerFactory.getLogger(Database.class);

    private static HikariPoolMXBean poolProxy;

    private static volatile HikariDataSource pool;

    public static void close() {
        if (Base.hasConnection()) {
            Base.close();
        }
    }

    public static void open() {
        if (!Base.hasConnection()) {
            Base.open(pool());
        }
    }

    public static void openTransaction() {
        if (Base.hasConnection()) {
            Base.openTransaction();
        }
    }

    public static void rollbackTransaction() {
        if (Base.hasConnection()) {
            Base.rollbackTransaction();
        }
    }

    public static void commitTransaction() {
        if (Base.hasConnection()) {
            Base.commitTransaction();
        }
    }

    public static HikariPoolMXBean monitor() {
        return poolProxy;
    }

    public static DataSource pool() {
        if (pool == null) {
            setup();
        }
        return pool;
    }

    private static void setup() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:mariadb://localhost:3306/asgla");
        config.setUsername("root");
        config.setPassword("321");

        config.setMaximumPoolSize(20);
        config.setRegisterMbeans(true);
        config.setPoolName("explosion");

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("useLocalSessionState", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("cacheResultSetMetadata", "true");
        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("elideSetAutoCommits", "true");
        config.addDataSourceProperty("maintainTimeStats", "false");

        pool = new HikariDataSource(config);

        try {
            poolProxy = JMX.newMXBeanProxy(ManagementFactory.getPlatformMBeanServer(), new ObjectName("com.zaxxer.hikari:type=explosion"), HikariPoolMXBean.class);
        } catch (MalformedObjectNameException e) {
            log.error(e.getMessage());
        }
    }

}
