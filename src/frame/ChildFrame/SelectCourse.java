package frame.ChildFrame;

import DataBase.DataBaseUtils;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * @author lyz
 * @version 1.0
 * @time 2022-11-30
 * @comment 查询课程表的界面
 */
public class SelectCourse {
    public SelectCourse() throws SQLException {
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
        Connection connection = DataBaseUtils.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from course");

        //将得到的数据填充到表格中
        String[] names = {"Cno", "Cname", "Ccredit", "Cnum", "Tno"};
        String[] data = new String[5];
        DefaultTableModel model = new DefaultTableModel(names, 0);
        while (resultSet.next()) {
            for (int i = 1; i <= 5; i++) {
                data[i - 1] = resultSet.getString(i);
            }
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
