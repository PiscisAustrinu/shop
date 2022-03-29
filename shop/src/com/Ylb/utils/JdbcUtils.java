package com.Ylb.utils;

import com.alibaba.druid.pool.DruidAbstractDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {

    private static DruidAbstractDataSource dataSource;
    private static ThreadLocal<Connection> conns = new ThreadLocal<Connection>();
    static {
        try {
            Properties properties = new Properties();
            //读取Jdbc.properties属性
            InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            //从流中加载数据
            properties.load(inputStream);
            //创建数据库连接池
            dataSource = (DruidAbstractDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取数据库连接池池中的连接
     * @return  如果返回null，说明获取连接失败   有值就是获取连接成功
     */
    public static Connection getConnection(){
        Connection conn = conns.get();
        if (conn==null){
            try {
                conn = dataSource.getConnection();  //从数据库连接池中获取连接
                conns.set(conn);    //保存到TreadLocal对象中，供后面的jdbc使用
                conn.setAutoCommit(false);  //设置为手动管理事务
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    /**
     * 提交事务，并关闭释放连接
     */
    public static void commitAndClose(){
        Connection connection = conns.get();
        if (connection!=null){//如果不等于null，说明之前使用过连接，操作过数据库
            try {
                connection.commit();//提交事务
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();//关闭连接
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        //一定要执行remove操作，否则会出错（因为Tomcat服务器底层使用线程池技术）
        conns.remove();
    }

    /**
     * 回滚事务，并释放连接
     */
    public static void rollbackAndClose(){
        Connection connection = conns.get();
        if (connection!=null){//如果不等于null，说明之前使用过连接，操作过数据库
            try {
                connection.rollback();//回滚事务事务
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();//关闭连接
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        //一定要执行remove操作，否则会出错（因为Tomcat服务器底层使用线程池技术）
        conns.remove();
    }

//    /**
//     * 放回数据库连接池中的连接
//     * @param conn
//     */
//    public static void close(Connection conn){
//        if (conn != null){
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
