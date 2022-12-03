package DataBase;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/*
 * @author lyz
 * @version 1.0
 * @time 2022-11-30
 * @comment 为了避免重复实现数据连接的过程，将这个过程封装成类当工具直接调用
 */
public class DataBaseUtils {
    static String driver;// 驱动
    static String url;// 要连接数据库的路径
    static String user;// 连接数据库的用户名
    static String password;// 用户名所对应的密码

    static {
        try {
            // 配置文件对象
            Properties properties = new Properties();
            // 用于读取文件字节流
            properties.load(new FileInputStream("src/DataBase/mysql.properties"));
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            //现在driver都是自动加载了，这一步可有可无了
            driver = properties.getProperty("driver");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 获取连接对象
    public static Connection getConnection() {
        try {

            // 连接数据库,查找并且尝试连接数据库
            Connection connection = DriverManager.getConnection(url, user, password);
            // cconnection是局部变量，因此也要定义在try中。
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            /*
             * 程序执行时，若发现异常会进行捕获处理，就导致无法执行return返回结果，这样就违背了方法的定义。
             * 因此在Catch捕获处理时需要对异常进行throw处理，
             */
            throw new RuntimeException();
        }
    }

    // 关闭连接（虽然基本没用上过）
    public static void close(Connection c) {
        // 先判断是否连接成功，连接成功则关闭连接资源，否则就不需要关闭
        if (c != null) {
            // close()关闭连接资源自身带有异常
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
