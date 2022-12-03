package frame;

import DataBase.DataBaseOperation;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;

/*
 * @author lyz
 * @version 1.0
 * @time 2022-11-28
 * @comment 登录界面
 */
public class LoginMenu {
    public static void main(String[] args) {
        //更改UI界面，导入美化包
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        //设置窗口大小和位置，不允许拖动和缩小放大
        JFrame frame = new JFrame("学生选课管理系统");
        Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (ScreenSize.getWidth() / 2 - 500 / 2);
        int y = (int) (ScreenSize.getHeight() / 2 - 300 / 2);
        frame.setBounds(x, y, 500, 300);
        frame.setLayout(null);
        frame.setResizable(false);

        //设置主题标签
        JLabel label1 = new JLabel("用户登录");
        label1.setFont(new Font("微软雅黑", Font.BOLD, 20));
        label1.setBounds(210, 40, 100, 20);
        frame.add(label1);

        //设置文本框和密码框
        JTextField field1 = new JTextField();
        JPasswordField jPasswordField = new JPasswordField();
        field1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        jPasswordField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        field1.setBounds(160, 80, 200, 20);
        jPasswordField.setBounds(160, 120, 200, 20);
        field1.setText("请输入帐号");
        jPasswordField.setText("123456");
        frame.add(field1);
        frame.add(jPasswordField);

        //设置登录按钮
        JButton button = new JButton("登录");
        button.setFont(new Font("微软雅黑", Font.BOLD, 14));
        button.setBounds(220, 160, 60, 20);
        frame.add(button);

        //关闭时弹出提示
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); //默认关闭行为设定为什么都不做
        frame.addWindowListener(new WindowAdapter() { //设定关闭窗口的行为
            @Override
            public void windowClosing(WindowEvent e) {
                int value = JOptionPane.showConfirmDialog(frame, "是否要退出?", "提示", JOptionPane.YES_NO_OPTION); //弹出对话框进行询问是否关闭
                if (value == JOptionPane.OK_OPTION) System.exit(0);
            }
        });


        //设置监听器获取帐号和密码，由于是小项目懒得MD5加密处理，因此安全性较低
        //如果帐号和密码有误则弹出提示窗口
        button.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String account = field1.getText();
                        String pwd = String.valueOf(jPasswordField.getPassword());
                        int flag = 100;
                        try {
                            flag = DataBaseOperation.CheckUser(account, pwd);
                        } catch (SQLException | IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        if (flag == 0) {
                            int value = JOptionPane.showConfirmDialog(frame, "帐号或密码有误", "提示", JOptionPane.CLOSED_OPTION);
                        } else if (flag == 1) {
                            StudentMenu studentMenu = new StudentMenu(account);
                            frame.dispose();
                        } else {
                            TeacherMenu teacherMenu = new TeacherMenu(account);
                            frame.dispose();
                        }
                    }
                }
        );

        //将窗口设置可视，放在最后是为了避免框出来了组件没加载出来的问题
        frame.setVisible(true);
    }
}
