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
 *@commit 用来生成学生界面和教师界面的子界面，可以查看所有课程
 */
public class SelectAllCourse {
    public SelectAllCourse() throws SQLException {
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

        //执行SQL语句
        List<Course> list=DataBaseUtil.SelectAllCourse();

        //将得到的数据填充到表格中
        String[] names = {"Cno", "Cname", "Ccredit", "Cnum", "Tno"};
        String[] data = new String[5];
        String temp;
        DefaultTableModel model = new DefaultTableModel(names, 0);
        for (int i=0;i<list.size();i++){
            Course course=list.get(i);
            temp=String.valueOf(course.getCno());
            data[0] = temp;
            data[1]=course.getCname();
            temp=String.valueOf(course.getCcredit());
            data[2]=temp;
            temp=String.valueOf(course.getCnum());
            data[3]=temp;
            data[4]=course.getTno();
            model.addRow(data);
        }

        //新建表格
        JTable table = new JTable();
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
