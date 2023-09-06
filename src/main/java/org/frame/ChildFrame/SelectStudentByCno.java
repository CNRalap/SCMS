package org.frame.ChildFrame;

import com.formdev.flatlaf.FlatDarkLaf;
import org.entity.SC;
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
 *@commit 教师界面的子界面，教师可以查看自己某一课程的选课学生，并且修改其成绩
 */
public class SelectStudentByCno {
    public SelectStudentByCno(String Tno, String Cno) throws SQLException {
        //更改UI界面，导入美化包
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        //设置窗口大小和位置，不允许拖动和缩小放大
        JFrame frame = new JFrame("课程号：" + Cno);
        Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (ScreenSize.getWidth() / 2 - 800 / 2);
        int y = (int) (ScreenSize.getHeight() / 2 - 600 / 2);
        frame.setBounds(x, y, 800, 600);
        frame.setResizable(false);

        //设置菜单栏和按键
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("编辑");
        JMenuItem item = new JMenuItem("编辑成绩");
        menu.add(item);
        bar.add(menu);
        frame.setJMenuBar(bar);

        //执行SQL语句
        final List<SC>[] list = new List[]{DataBaseUtil.SelectStudentAndGrade(Cno, Tno)};
        boolean flag = false;
        if (!list[0].isEmpty()) flag = true;

        //将得到的数据填充到表格中
        String[] names = {"Sno", "Grade"};
        String[] data = new String[2];
        final String[] temp = new String[1];
        DefaultTableModel model = new DefaultTableModel(names, 0);
        for (int i = 0; i < list[0].size(); i++) {
            SC sc = list[0].get(i);
            data[0] = sc.getSno();
            temp[0] = String.valueOf(sc.getGrade());
            data[1] = temp[0];
            model.addRow(data);
        }

        //新建表格
        JTable table = new JTable();
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        //对菜单栏按键监听，同时刷新表格
        item.addActionListener(e -> {
                    String Sno = JOptionPane.showInputDialog("请输入要修改成绩的学生的学号");
                    String Grade = JOptionPane.showInputDialog("请输入成绩");
                    int f = DataBaseUtil.UpdateGrade(Grade, Sno, Cno, Tno);
                    if (f == 1) {
                        JOptionPane.showConfirmDialog(frame, "修改成功", "提示", JOptionPane.CLOSED_OPTION);
                        list[0].clear();
                        list[0] = DataBaseUtil.SelectStudentAndGrade(Cno, Tno);
                        model.setRowCount(0);
                        for (int i = 0; i < list[0].size(); i++) {
                            SC sc = list[0].get(i);
                            data[0] = sc.getSno();
                            temp[0] = String.valueOf(sc.getGrade());
                            data[1] = temp[0];
                            model.addRow(data);
                        }
                        table.updateUI();
                    } else JOptionPane.showConfirmDialog(frame, "修改失败", "提示", JOptionPane.CLOSED_OPTION);
                }
        );

        //用来提示查询有误
        if (!flag) {
            JOptionPane.showMessageDialog(frame, "查询课程号有误", "提示", JOptionPane.CLOSED_OPTION);
            return;
        }

        frame.setVisible(true);
    }
}
