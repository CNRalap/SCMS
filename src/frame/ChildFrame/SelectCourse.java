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
 * @author ��ҵ��
 * @version 1.0
 * @time 2022-11-30
 * @comment ��ѯ�γ̱�Ľ���
 */
public class SelectCourse {
    public SelectCourse() throws SQLException {
        //����UI���棬����������
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        //���ô��ڴ�С��λ�ã��������϶�����С�Ŵ�
        JFrame frame = new JFrame("�γ̱�");
        Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (ScreenSize.getWidth() / 2 - 800 / 2);
        int y = (int) (ScreenSize.getHeight() / 2 - 600 / 2);
        frame.setBounds(x, y, 800, 600);
        frame.setResizable(false);

        //ִ��SQL���
        Connection connection = DataBaseUtils.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from course");

        //���õ���������䵽�����
        String[] names = {"Cno", "Cname", "Ccredit", "Cnum", "Tno"};
        String[] data = new String[5];
        DefaultTableModel model = new DefaultTableModel(names, 0);
        while (resultSet.next()) {
            for (int i = 1; i <= 5; i++) {
                data[i - 1] = resultSet.getString(i);
            }
            model.addRow(data);
        }
        //�½����
        JTable table = new JTable();
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
