package org.frame.ChildFrame;

import com.formdev.flatlaf.FlatDarkLaf;
import org.entity.Course;
import org.util.DataBaseUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/*
 *@author lyz
 *@version 2.0
 *@time 2023.3.5
 *@commit 用于教师界面的子界面，教师可以查看自己负责哪些课程
 */
public class SelectCourseByTno {
    public SelectCourseByTno(String Tno) throws SQLException {
        //更改UI界面，导入美化包
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        //设置窗口大小和位置，不允许拖动和缩小放大
        JFrame frame = new JFrame("课程表");
        Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (ScreenSize.getWidth() / 2 - 800 / 2);
        int y = (int) (ScreenSize.getHeight() / 2 - 600 / 2);
        frame.setBounds(x, y, 800, 600);
        frame.setResizable(false);

        //设置菜单栏和按键
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("编辑");
        JMenuItem item1 = new JMenuItem("删除课程");
        JMenuItem item2 = new JMenuItem("新增课程");
        menu.add(item1);
        menu.add(item2);
        bar.add(menu);
        frame.setJMenuBar(bar);

        //执行SQL语句
        final List<Course>[] list = new List[]{DataBaseUtil.SelectCourseByTno(Tno)};

        //将得到的数据填充到表格中
        String[] names = {"Cno", "Cname", "Ccredit", "Cnum", "Tno"};
        String[] data = new String[5];
        final String[] temp = new String[1];
        DefaultTableModel model = new DefaultTableModel(names, 0);
        for (int i = 0; i < list[0].size(); i++) {
            Course course = list[0].get(i);
            temp[0] = String.valueOf(course.getCno());
            data[0] = temp[0];
            data[1] = course.getCname();
            temp[0] = String.valueOf(course.getCcredit());
            data[2] = temp[0];
            temp[0] = String.valueOf(course.getCnum());
            data[3] = temp[0];
            data[4] = course.getTno();
            model.addRow(data);
        }

        //新建表格
        JTable table = new JTable();
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        //对菜单栏按键监听，同时刷新表格
        item1.addActionListener(e -> {
            String Cno = JOptionPane.showInputDialog("请输入你要删除的课程号");
            if (!(Cno == null)) {
                int f = DataBaseUtil.DeleteCourse(Cno, Tno);
                if (f == 1) {
                    JOptionPane.showConfirmDialog(frame, "删除成功", "提示", JOptionPane.CLOSED_OPTION);
                    list[0].clear();
                    list[0] = DataBaseUtil.SelectCourseByTno(Tno);
                    model.setRowCount(0);
                    for (int i = 0; i < list[0].size(); i++) {
                        Course course = list[0].get(i);
                        temp[0] = String.valueOf(course.getCno());
                        data[0] = temp[0];
                        data[1] = course.getCname();
                        temp[0] = String.valueOf(course.getCcredit());
                        data[2] = temp[0];
                        temp[0] = String.valueOf(course.getCnum());
                        data[3] = temp[0];
                        data[4] = course.getTno();
                        model.addRow(data);
                    }
                    table.updateUI();
                } else JOptionPane.showConfirmDialog(frame, "删除失败", "提示", JOptionPane.CLOSED_OPTION);
            }
        });

        //对菜单栏按键监听，同时刷新表格
        item2.addActionListener(e -> {
            String Cno = JOptionPane.showInputDialog("请输入你要新增课程的课程号");
            String Cname = JOptionPane.showInputDialog("请输入你要新增课程的课程名");
            String Ccredit = JOptionPane.showInputDialog("请输入你要新增课程的学分(<=4)");
            try {
                if (!(Cno == null) && !(Cname == null) && !(Ccredit == null)) {
                    DataBaseUtil.AddCourse(Cno, Cname, Ccredit, "0", Tno);
                    JOptionPane.showConfirmDialog(frame, "新增成功", "提示", JOptionPane.CLOSED_OPTION);
                    list[0].clear();
                    list[0] = DataBaseUtil.SelectCourseByTno(Tno);
                    model.setRowCount(0);
                    for (int i = 0; i < list[0].size(); i++) {
                        Course course = list[0].get(i);
                        temp[0] = String.valueOf(course.getCno());
                        data[0] = temp[0];
                        data[1] = course.getCname();
                        temp[0] = String.valueOf(course.getCcredit());
                        data[2] = temp[0];
                        temp[0] = String.valueOf(course.getCnum());
                        data[3] = temp[0];
                        data[4] = course.getTno();
                        model.addRow(data);
                    }
                    table.updateUI();
                }
            } catch (Exception exception) {
                JOptionPane.showConfirmDialog(frame, "新增失败", "提示", JOptionPane.CLOSED_OPTION);
                throw new RuntimeException(exception);
            }
        });

        frame.setVisible(true);
    }
}
