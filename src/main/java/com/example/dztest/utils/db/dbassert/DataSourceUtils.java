package com.example.dztest.utils.db.dbassert;

import cn.hutool.core.util.StrUtil;
import cn.hutool.db.ds.simple.SimpleDataSource;
import com.example.dztest.utils.SpringContextUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @ClassName: DataSourceUtils
 * @author: jian.ma@msxf.com
 * @create: 2022/6/7
 * @update: 2022/6/7
 **/

public final class DataSourceUtils {

    private static final ConcurrentMap<String, DataSource> DATA_SOURCE_CONCURRENT_MAP =
            new ConcurrentHashMap<>(16);

    private DataSourceUtils() {

    }

    /**
     * Executes the given SQL statement, which may be an <code>INSERT</code>,
     * <code>UPDATE</code>, or <code>DELETE</code> statement or an
     * SQL statement that returns nothing, such as an SQL DDL statement.
     * @param dbName 执行的目标数据库
     * @param sql 执行的sql
     * @return 影响行数
     * @throws SQLException
     */
    public static int execSql(String dbName, String sql) throws SQLException {
        Statement stmt;
        int row;

        Connection connection = DataSourceUtils.getDataSource(dbName).getConnection();
        stmt = connection.createStatement();
        row = stmt.executeUpdate(sql);
        int updateCount = stmt.getUpdateCount();
        connection.close();

        return row;
    }

    public static List<Map<Integer, Object>> queryData(String dbName, String sql) throws SQLException {
        // 根据数据库名称查询数据
        ResultSet resultSet = getResult(dbName, sql);
        // select value1,value2
        List<Map<Integer, Object>> list = Lists.newArrayList();
        handleResultSet(resultSet, list);
        resultSet.close();
        return list;
    }

    private static ResultSet getResult(String dbName, String sqlString) {
        Statement stmt;
        ResultSet rs = null;
        try {
            Connection connection = DataSourceUtils.getDataSource(dbName).getConnection();
            stmt = connection.createStatement();
            stmt.execute(sqlString);
            rs = stmt.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    private static void handleResultSet(ResultSet resultSet, List<Map<Integer, Object>> list) throws SQLException {
        // 处理ResultSet结果集
        int size = resultSet.getMetaData().getColumnCount();
        while (resultSet.next()) {
            Map<Integer, Object> map = Maps.newHashMap();
            for (int i = 0; i < size; i++) {
                // 是从1开始
                map.put(i, resultSet.getObject(i + 1));
            }
            list.add(map);
        }
    }

    public static DataSource getDataSource(String dbName) {
        // 优先从spring 容器中获取DataSource获取数据源，使用场景为：研发项目引入测试maven坐标
        try {
            DataSource source = DATA_SOURCE_CONCURRENT_MAP.get(dbName);
            if (Objects.nonNull(source)) {
                return source;
            }

            DataSource dataSource = (DataSource) SpringContextUtil.getBean(DataSource.class);
            DATA_SOURCE_CONCURRENT_MAP.put(dbName, dataSource);
            return dataSource;

        } catch (Exception e) {
            // 如果从 spring 容器中获取DataSource 失败，则从加载数据库连接配置文件初始化
            final String dbUrl = StrUtil.format(
                    "jdbc:mysql://{}/{}" +
                            "?useUnicode=true&characterEncoding=UTF8&zeroDateTimeBehavior=round&useSSL=false",
                    DBInfoConfig.getStr(ConfigConstants.DATABASE_URL),
                    dbName);
            DataSource dataSource = new SimpleDataSource(
                    dbUrl, DBInfoConfig.getStr(ConfigConstants.DATABASE_USER_NAME),
                    DBInfoConfig.getStr(ConfigConstants.DATABASE_PASSWORD));
            DATA_SOURCE_CONCURRENT_MAP.put(dbName, dataSource);
            return dataSource;
        }
    }
}