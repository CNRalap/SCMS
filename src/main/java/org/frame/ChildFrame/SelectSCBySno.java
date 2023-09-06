package org.frame.ChildFrame;

import com.formdev.flatlaf.FlatDarkLaf;
import org.entity.SC;
import org.util.DataBaseUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

/*
 *@author lyz
 *@version 2.0
 *@time 2023.3.5
 *@commit 学生界面的子界面，让学生可以查看到自己的选课情况，并且可以退换课
 */
public class SelectSCBySno {
    public SelectSCBySno(String Sno) throws SQLException {
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
        JMenuItem item1 = new JMenuItem("新增选课");
        JMenuItem item2 = new JMenuItem("删除选课");
        menu.add(item1);
        menu.add(item2);
        bar.add(menu);
        frame.setJMenuBar(bar);

        //执行SQL语句
        final List<SC>[] list = new List[]{DataBaseUtil.SelectAllSCBySno(Sno)};

        //将得到的数据填充到表格中
        String[] names = {"Cno", "Sno", "Tno", "Grade"};
        String[] data = new String[4];
        final String[] temp = new String[1];
        DefaultTableModel model = new DefaultTableModel(names, 0);
        for (int i = 0; i < list[0].size(); i++) {
            SC sc = list[0].get(i);
            temp[0] = String.valueOf(sc.getCno());
            data[0] = temp[0];
            data[1] = sc.getSno();
            data[2] = sc.getTno();
            temp[0] = String.valueOf(sc.getGrade());
            data[3] = temp[0];
            model.addRow(data);
        }

        //新建表格
        JTable table = new JTable();
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Cno = JOptionPane.showInputDialog("请输入要选修课程的课程号");
                try {
                    DataBaseUtil.AddSC(Cno, Sno);
                    JOptionPane.showConfirmDialog(frame, "选课成功", "提示", JOptionPane.CLOSED_OPTION);
                    list[0].clear();
                    list[0] = DataBaseUtil.SelectAllSCBySno(Sno);
                    model.setRowCount(0);
                    for (int i = 0; i < list[0].size(); i++) {
                        SC sc = list[0].get(i);
                        temp[0] = String.valueOf(sc.getCno());
                        data[0] = temp[0];
                        data[1] = sc.getSno();
                        data[2] = sc.getTno();
                        temp[0] = String.valueOf(sc.getGrade());
                        data[3] = temp[0];
                        model.addRow(data);
                    }
                    table.updateUI();
                } catch (Exception ex) {
                    JOptionPane.showConfirmDialog(frame, "选课失败", "提示", JOptionPane.CLOSED_OPTION);
                    throw new RuntimeException(ex);
                }
            }
        });

        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Cno = JOptionPane.showInputDialog("请输入要退课课程的课程号");
                int flag = 0;
                flag = DataBaseUtil.DeleteSC(Cno, Sno);
                if (flag == 1) {
                    JOptionPane.showConfirmDialog(frame, "退课成功", "提示", JOptionPane.CLOSED_OPTION);
                    list[0].clear();
                    list[0] = DataBaseUtil.SelectAllSCBySno(Sno);
                    model.setRowCount(0);
                    for (int i = 0; i < list[0].size(); i++) {
                        SC sc = list[0].get(i);
                        temp[0] = String.valueOf(sc.getCno());
                        data[0] = temp[0];
                        data[1] = sc.getSno();
                        data[2] = sc.getTno();
                        temp[0] = String.valueOf(sc.getGrade());
                        data[3] = temp[0];
                        model.addRow(data);
                    }
                    table.updateUI();
                } else JOptionPane.showConfirmDialog(frame, "退课失败", "提示", JOptionPane.CLOSED_OPTION);
            }
        });

        frame.setVisible(true);
    }
}
