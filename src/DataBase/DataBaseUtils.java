package DataBase;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/*
 * @author ��ҵ��
 * @version 1.0
 * @time 2022-11-30
 * @comment Ϊ�˱����ظ�ʵ���������ӵĹ��̣���������̷�װ���൱����ֱ�ӵ���
 */
public class DataBaseUtils {
    static String driver;// ����
    static String url;// Ҫ�������ݿ��·��
    static String user;// �������ݿ���û���
    static String password;// �û�������Ӧ������

    static {
        try {
            // �����ļ�����
            Properties properties = new Properties();
            // ���ڶ�ȡ�ļ��ֽ���
            properties.load(new FileInputStream("src/DataBase/mysql.properties"));
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            //����driver�����Զ������ˣ���һ�����п�����
            driver = properties.getProperty("driver");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ��ȡ���Ӷ���
    public static Connection getConnection() {
        try {

            // �������ݿ�,���Ҳ��ҳ����������ݿ�
            Connection connection = DriverManager.getConnection(url, user, password);
            // cconnection�Ǿֲ����������ҲҪ������try�С�
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            /*
             * ����ִ��ʱ���������쳣����в������͵����޷�ִ��return���ؽ����������Υ���˷����Ķ��塣
             * �����Catch������ʱ��Ҫ���쳣����throw����
             */
            throw new RuntimeException();
        }
    }

    // �ر����ӣ���Ȼ����û���Ϲ���
    public static void close(Connection c) {
        // ���ж��Ƿ����ӳɹ������ӳɹ���ر�������Դ������Ͳ���Ҫ�ر�
        if (c != null) {
            // close()�ر�������Դ��������쳣
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
