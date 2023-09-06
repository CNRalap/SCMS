package org.frame;

import com.formdev.flatlaf.FlatDarkLaf;
import org.util.DataBaseUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*
 *@author lyz
 *@version 2.0
 *@time 2023.3.5
 *@commit 登录界面
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
        label1.setBounds(200, 40, 100, 20);
        frame.add(label1);

        //设置文本框和密码框
        JTextField field1 = new JTextField();
        JPasswordField jPasswordField = new JPasswordField();
        field1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        jPasswordField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        field1.setBounds(150, 80, 200, 25);
        jPasswordField.setBounds(150, 120, 200, 25);
        field1.setText("请输入帐号");
        jPasswordField.setText("******");
        frame.add(field1);
        frame.add(jPasswordField);

        //设置登录按钮
        JButton button = new JButton("登录");
        button.setFont(new Font("微软雅黑", Font.BOLD, 15));
        button.setBounds(210, 160, 60, 25);
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
                e -> {
                    String account = field1.getText();
                    String pwd = String.valueOf(jPasswordField.getPassword());
                    int flag = 100;
                    flag = DataBaseUtil.CheckUser(account, pwd);
                    if (flag == 0) {
                        JOptionPane.showConfirmDialog(frame, "帐号或密码有误", "提示", JOptionPane.CLOSED_OPTION);
                    } else if (flag == 1) {
                        new StudentMenu(account);
                        frame.dispose();
                    } else if (flag == 2) {
                        new TeacherMenu(account);
                        frame.dispose();
                    }
                }
        );

        //将窗口设置可视，放在最后是为了避免框出来了组件没加载出来的问题
        frame.setVisible(true);
    }
}
