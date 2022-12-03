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
 * @time 2022-12-2
 * @comment 可以查看教师自己负责的某一课程的学生及其成绩，并且可以修改学生成绩
 */
public class TselectMySC {
    public TselectMySC(String Tno, String Cno) throws SQLException {
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
        Connection connection = DataBaseUtils.getConnection();
        String sql = "select Sno,Grade from sc where Tno=? and Cno=? ";
        final PreparedStatement[] preparedStatement = {connection.prepareStatement(sql)};
        preparedStatement[0].setString(1, Tno);
        preparedStatement[0].setString(2, Cno);
        final ResultSet[] resultSet = {preparedStatement[0].executeQuery()};

        //将得到的数据填充到表格中
        String[] names = {"Sno", "Grade"};
        String[] data = new String[2];
        DefaultTableModel model = new DefaultTableModel(names, 0);
        boolean flag = true;
        while (resultSet[0].next()) {
            for (int i = 1; i <= 2; i++) {
                data[i - 1] = resultSet[0].getString(i);
            }
            model.addRow(data);
            flag = false;
        }

        //新建表格
        JTable table = new JTable();
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        //对菜单栏按键监听，同时刷新表格
        item.addActionListener(new ActionListener() {
                                   @Override
                                   public void actionPerformed(ActionEvent e) {
                                       String Sno = JOptionPane.showInputDialog("请输入要修改成绩的学生的学号");
                                       String Grade = JOptionPane.showInputDialog("请输入成绩");
                                       try {
                                           boolean f = DataBaseOperation.UpdateGrade(Grade, Sno, Cno, Tno);
                                           if (f) {
                                               JOptionPane.showConfirmDialog(frame, "修改成功", "提示", JOptionPane.CLOSED_OPTION);
                                               preparedStatement[0] = connection.prepareStatement(sql);
                                               preparedStatement[0].setString(1, Tno);
                                               preparedStatement[0].setString(2, Cno);
                                               resultSet[0] = preparedStatement[0].executeQuery();
                                               model.setRowCount(0);
                                               while (resultSet[0].next()) {
                                                   for (int i = 1; i <= 2; i++) {
                                                       data[i - 1] = resultSet[0].getString(i);
                                                   }
                                                   model.addRow(data);
                                               }
                                               table.updateUI();
                                           } else JOptionPane.showConfirmDialog(frame, "修改失败", "提示", JOptionPane.CLOSED_OPTION);
                                       } catch (SQLException ex) {
                                           throw new RuntimeException(ex);
                                       }
                                   }
                               }
        );

        //用来提示查询有误
        if (flag) {
            JOptionPane.showMessageDialog(frame, "查询课程号有误", "提示", JOptionPane.CLOSED_OPTION);
            return;
        }

        frame.setVisible(true);
    }
}
