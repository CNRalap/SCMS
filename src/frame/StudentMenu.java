package frame;


import DataBase.DataBaseOperation;
import com.formdev.flatlaf.FlatDarkLaf;
import frame.ChildFrame.SelectCourse;
import frame.ChildFrame.SselectMySC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;

/*
 * @author lyz
 * @version 1.0
 * @time 2022-11-29
 * @comment 学生的操作界面
 */
public class StudentMenu {
    public StudentMenu(String Sno) {
        //更改UI界面，导入美化包
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        //设置窗口大小和位置，不允许拖动和缩小放大
        JFrame frame = new JFrame("学生：" + Sno);
        Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (ScreenSize.getWidth() / 2 - 500 / 2);
        int y = (int) (ScreenSize.getHeight() / 2 - 300 / 2);
        frame.setBounds(x, y, 500, 300);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setFont(new Font("微软雅黑", Font.BOLD, 14));

        //添加按钮
        JButton button1 = new JButton("查看课程表");
        JButton button2 = new JButton("查询选课情况");
        JButton button3 = new JButton("新增选课");
        JButton button4 = new JButton("删除选课");
        button1.setBounds(180, 20, 120, 25);
        button2.setBounds(180, 80, 120, 25);
        button3.setBounds(180, 140, 120, 25);
        button4.setBounds(180, 200, 120, 25);
        frame.add(button1);
        frame.add(button2);
        frame.add(button3);
        frame.add(button4);

        //监听按钮1动作
        button1.addActionListener(e -> {
            try {
                SelectCourse selectCourse = new SelectCourse();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        //监听按钮2动作
        button2.addActionListener(e -> {
            try {
                SselectMySC sselectMySC = new SselectMySC(Sno);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        //监听按钮3动作
        button3.addActionListener(e -> {
            String Cno = JOptionPane.showInputDialog("请输入你要选修的课程号");
            if (!Cno.isEmpty())
                try {
                    boolean flag = DataBaseOperation.AddSC(Cno, Sno);
                    if (flag) JOptionPane.showConfirmDialog(frame, "选课成功", "提示", JOptionPane.CLOSED_OPTION);
                    else JOptionPane.showConfirmDialog(frame, "选课失败", "提示", JOptionPane.CLOSED_OPTION);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
        });

        //监听按钮4动作
        button4.addActionListener(e -> {
            String Cno = JOptionPane.showInputDialog("请输入你要选修的课程号");
            if (!Cno.isEmpty())
                try {
                    boolean flag = DataBaseOperation.DeleteSC(Cno, Sno);
                    if (flag) JOptionPane.showConfirmDialog(frame, "退课成功", "提示", JOptionPane.CLOSED_OPTION);
                    else JOptionPane.showConfirmDialog(frame, "退课失败", "提示", JOptionPane.CLOSED_OPTION);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
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
