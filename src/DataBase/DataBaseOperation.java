package DataBase;

import java.io.IOException;
import java.sql.*;

/*
 * @author lyz
 * @version 1.0
 * @time 2022-11-30
 * @comment 用来实现数据库连接和相关sql操作的类
 */
public class DataBaseOperation {
    //注意，数据库SCDB设计之初使用学生模式和教师模式的，但由于为了代码简化，此处直接用root用户

    //CheckUser方法顾名思义是用来判断用户是学生还是老师
    public static int CheckUser(String account, String PWD) throws SQLException, IOException {
        //用flag判断用户是学生还是老师
        boolean flag = true;
        char head = 't';
        char account_head = account.charAt(0);
        if (head == account_head) flag = false;

        //建立连接
        Connection connection = DataBaseUtils.getConnection();

        //查询帐号和密码是否对得上，返回对应的值
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

    //AddSC就是用来在SC表新增学生选课用的
    public static boolean AddSC(String Cno, String Sno) throws IOException, SQLException {
        //建立连接
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

    //UpdateGrade方法就是在SC中修改成绩用的
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

    //DeleteCourse方法就是在course表中删除课程用的
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

    //AddCourse就是在course表中新增课程用的
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
