package frame.ChildFrame;

import DataBase.DataBaseOperation;
import DataBase.DataBaseUtils;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/*
 * @author 赖业智
 * @version 1.0
 * @time 2022-12-1
 * @comment 查看教师自己负责课程的界面，可以新增或者删除自己负责的课程
 */
public class TselectMyCourse {
    public TselectMyCourse(String Tno) throws SQLException {
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
        Connection connection = DataBaseUtils.getConnection();
        String sql = "select * from course where Tno=? ";
        final PreparedStatement[] preparedStatement = {connection.prepareStatement(sql)};
        preparedStatement[0].setString(1, Tno);
        final ResultSet[] resultSet = {preparedStatement[0].executeQuery()};

        //将得到的数据填充到表格中
        String[] names = {"Cno", "Cname", "Ccredit", "Cnum", "Tno"};
        String[] data = new String[5];
        DefaultTableModel model = new DefaultTableModel(names, 0);
        while (resultSet[0].next()) {
            for (int i = 1; i <= 5; i++) {
                data[i - 1] = resultSet[0].getString(i);
            }
            model.addRow(data);
        }

        //新建表格
        JTable table = new JTable();
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        //对菜单栏按键监听，同时刷新表格
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Cno = JOptionPane.showInputDialog("请输入你要删除的课程号");
                if (!Cno.isEmpty())
                    try {
                        boolean f = DataBaseOperation.DeleteCourse(Cno, Tno);
                        if (f) {
                            JOptionPane.showConfirmDialog(frame, "删除成功", "提示", JOptionPane.CLOSED_OPTION);
                            preparedStatement[0] = connection.prepareStatement(sql);
                            preparedStatement[0].setString(1, Tno);
                            resultSet[0] = preparedStatement[0].executeQuery();
                            model.setRowCount(0);
                            while (resultSet[0].next()) {
                                for (int i = 1; i <= 5; i++) {
                                    data[i - 1] = resultSet[0].getString(i);
                                }
                                model.addRow(data);
                            }
                            table.updateUI();
                        } else JOptionPane.showConfirmDialog(frame, "删除失败", "提示", JOptionPane.CLOSED_OPTION);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
            }
        });

        //对菜单栏按键监听，同时刷新表格
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Cno = JOptionPane.showInputDialog("请输入你要新增课程的课程号");
                String Cname = JOptionPane.showInputDialog("请输入你要新增课程的课程名");
                String Ccredit = JOptionPane.showInputDialog("请输入你要新增课程的学分(<=4)");
                String Cnum = JOptionPane.showInputDialog("请输入你要新增课程的最大选修人数(<=120)");
                if (!Cno.isEmpty() && !Cname.isEmpty() && !Ccredit.isEmpty() && !Cnum.isEmpty())
                    try {
                        boolean f = DataBaseOperation.AddCourse(Cno, Cname, Ccredit, Cnum, Tno);
                        if (f) {
                            JOptionPane.showConfirmDialog(frame, "新增成功", "提示", JOptionPane.CLOSED_OPTION);
                            preparedStatement[0] = connection.prepareStatement(sql);
                            preparedStatement[0].setString(1, Tno);
                            resultSet[0] = preparedStatement[0].executeQuery();
                            model.setRowCount(0);
                            while (resultSet[0].next()) {
                                for (int i = 1; i <= 5; i++) {
                                    data[i - 1] = resultSet[0].getString(i);
                                }
                                model.addRow(data);
                            }
                            table.updateUI();
                        } else JOptionPane.showConfirmDialog(frame, "新增失败", "提示", JOptionPane.CLOSED_OPTION);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
            }
        });

        frame.setVisible(true);
    }
}
