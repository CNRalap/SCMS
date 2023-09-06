package org.frame;

import com.formdev.flatlaf.FlatDarkLaf;
import org.frame.ChildFrame.SelectAllCourse;
import org.frame.ChildFrame.SelectCourseByTno;
import org.frame.ChildFrame.SelectStudentByCno;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

/*
 *@author lyz
 *@version 2.0
 *@time 2023.3.5
 *@commit 教师界面
 */
public class TeacherMenu {
    public TeacherMenu(String Tno) {
        //更改UI界面，导入美化包
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        //设置窗口大小和位置，不允许拖动和缩小放大
        JFrame frame = new JFrame("教师：" + Tno);
        Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (ScreenSize.getWidth() / 2 - 500 / 2);
        int y = (int) (ScreenSize.getHeight() / 2 - 300 / 2);
        frame.setBounds(x, y, 500, 300);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setFont(new Font("微软雅黑", Font.BOLD, 14));

        //添加按钮
        JButton button1 = new JButton("查看课程表");
        JButton button2 = new JButton("查看负责课程");
        JButton button3 = new JButton("查看课程学生");
        button1.setBounds(180, 60, 120, 25);
        button2.setBounds(180, 120, 120, 25);
        button3.setBounds(180, 180, 120, 25);
        frame.add(button1);
        frame.add(button2);
        frame.add(button3);

        //监听按钮1
        button1.addActionListener(e -> {
            try {
                new SelectAllCourse();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        //监听按钮2
        button2.addActionListener(e -> {
            try {
                new SelectCourseByTno(Tno);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        //监听按钮3
        button3.addActionListener(e -> {
            String Cno = JOptionPane.showInputDialog("请输入你要查看的课程号");
            if (!(Cno == null)) {
                try {
                    new SelectStudentByCno(Tno, Cno);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });

        //关闭时弹出提示
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); //默认关闭行为设定为什么都不做
        frame.addWindowListener(new WindowAdapter() { //设定关闭窗口的行为
            @Override
            public void windowClosing(WindowEvent e) {
                int value = JOptionPane.showConfirmDialog(frame, "是否要退出?", "提示", JOptionPane.YES_NO_OPTION); //弹出对话框进行询问是否关闭
                if (value == JOptionPane.OK_OPTION) System.exit(0);
            }
        });

        frame.setVisible(true);
    }
}
