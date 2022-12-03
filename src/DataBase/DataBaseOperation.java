package DataBase;

import java.io.IOException;
import java.sql.*;

/*
 * @author ��ҵ��
 * @version 1.0
 * @time 2022-11-30
 * @comment ����ʵ�����ݿ����Ӻ����sql��������
 */
public class DataBaseOperation {
    //ע�⣬���ݿ�SCDB���֮��ʹ��ѧ��ģʽ�ͽ�ʦģʽ�ģ�������Ϊ�˴���򻯣��˴�ֱ����root�û�

    //CheckUser��������˼���������ж��û���ѧ��������ʦ
    public static int CheckUser(String account, String PWD) throws SQLException, IOException {
        //��flag�ж��û���ѧ��������ʦ
        boolean flag = true;
        char head = 't';
        char account_head = account.charAt(0);
        if (head == account_head) flag = false;

        //��������
        Connection connection = DataBaseUtils.getConnection();

        //��ѯ�ʺź������Ƿ�Ե��ϣ����ض�Ӧ��ֵ
        if (flag) {
            String sql = "select Sno,Spwd from student where Sno=? and Spwd=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account);
            preparedStatement.setString(2, PWD);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return 1;
            else return 0;
        } else {
            String sql = "select Tno,Tpwd from teacher where Tno=? and Tpwd=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account);
            preparedStatement.setString(2, PWD);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return 2;
            else return 0;
        }
    }

    //AddSC����������SC������ѧ��ѡ���õ�
    public static boolean AddSC(String Cno, String Sno) throws IOException, SQLException {
        //��������
        Connection connection = DataBaseUtils.getConnection();
        String Tno = null;
        String sql1 = "select Tno from course where Cno=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql1);
        preparedStatement.setString(1, Cno);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Tno = resultSet.getString(1);
        }

        try {
            String sql2 = "insert into sc(Cno,Sno,Tno) values (?,?,?)";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql2);
            preparedStatement1.setString(1, Cno);
            preparedStatement1.setString(2, Sno);
            preparedStatement1.setString(3, Tno);
            int result = preparedStatement1.executeUpdate();
            if (result > 0) return true;
            else return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //UpdateGrade����������SC���޸ĳɼ��õ�
    public static boolean UpdateGrade(String Grade, String Sno, String Cno, String Tno) throws SQLException {
        Connection connection = DataBaseUtils.getConnection();
        try {
            String sql = "update sc set Grade=? where Sno=? and Cno=? and Tno=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Grade);
            preparedStatement.setString(2, Sno);
            preparedStatement.setString(3, Cno);
            preparedStatement.setString(4, Tno);
            int result = preparedStatement.executeUpdate();
            if (result > 0) return true;
            else return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //DeleteCourse����������course����ɾ���γ��õ�
    public static boolean DeleteCourse(String Cno, String Tno) {
        Connection connection = DataBaseUtils.getConnection();
        try {
            String sql = "delete from course where Cno=? and Tno=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Cno);
            preparedStatement.setString(2, Tno);
            int result = preparedStatement.executeUpdate();
            if (result > 0) return true;
            else return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //AddCourse������course���������γ��õ�
    public static boolean AddCourse(String Cno, String Cname, String Ccredit, String Cnum, String Tno) {
        Connection connection = DataBaseUtils.getConnection();
        try {
            String sql = "insert into course values (?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Cno);
            preparedStatement.setString(2, Cname);
            preparedStatement.setString(3, Ccredit);
            preparedStatement.setString(4, Cnum);
            preparedStatement.setString(5, Tno);
            int result = preparedStatement.executeUpdate();
            if (result > 0) return true;
            else return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
