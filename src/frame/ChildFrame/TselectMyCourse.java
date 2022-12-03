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
 * @author ��ҵ��
 * @version 1.0
 * @time 2022-12-1
 * @comment �鿴��ʦ�Լ�����γ̵Ľ��棬������������ɾ���Լ�����Ŀγ�
 */
public class TselectMyCourse {
    public TselectMyCourse(String Tno) throws SQLException {
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

        //���ò˵����Ͱ���
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("�༭");
        JMenuItem item1 = new JMenuItem("ɾ���γ�");
        JMenuItem item2 = new JMenuItem("�����γ�");
        menu.add(item1);
        menu.add(item2);
        bar.add(menu);
        frame.setJMenuBar(bar);

        //ִ��SQL���
        Connection connection = DataBaseUtils.getConnection();
        String sql = "select * from course where Tno=? ";
        final PreparedStatement[] preparedStatement = {connection.prepareStatement(sql)};
        preparedStatement[0].setString(1, Tno);
        final ResultSet[] resultSet = {preparedStatement[0].executeQuery()};

        //���õ���������䵽�����
        String[] names = {"Cno", "Cname", "Ccredit", "Cnum", "Tno"};
        String[] data = new String[5];
        DefaultTableModel model = new DefaultTableModel(names, 0);
        while (resultSet[0].next()) {
            for (int i = 1; i <= 5; i++) {
                data[i - 1] = resultSet[0].getString(i);
            }
            model.addRow(data);
        }

        //�½����
        JTable table = new JTable();
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        //�Բ˵�������������ͬʱˢ�±��
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Cno = JOptionPane.showInputDialog("��������Ҫɾ���Ŀγ̺�");
                if (!Cno.isEmpty())
                    try {
                        boolean f = DataBaseOperation.DeleteCourse(Cno, Tno);
                        if (f) {
                            JOptionPane.showConfirmDialog(frame, "ɾ���ɹ�", "��ʾ", JOptionPane.CLOSED_OPTION);
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
                        } else JOptionPane.showConfirmDialog(frame, "ɾ��ʧ��", "��ʾ", JOptionPane.CLOSED_OPTION);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
            }
        });

        //�Բ˵�������������ͬʱˢ�±��
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Cno = JOptionPane.showInputDialog("��������Ҫ�����γ̵Ŀγ̺�");
                String Cname = JOptionPane.showInputDialog("��������Ҫ�����γ̵Ŀγ���");
                String Ccredit = JOptionPane.showInputDialog("��������Ҫ�����γ̵�ѧ��(<=4)");
                String Cnum = JOptionPane.showInputDialog("��������Ҫ�����γ̵����ѡ������(<=120)");
                if (!Cno.isEmpty() && !Cname.isEmpty() && !Ccredit.isEmpty() && !Cnum.isEmpty())
                    try {
                        boolean f = DataBaseOperation.AddCourse(Cno, Cname, Ccredit, Cnum, Tno);
                        if (f) {
                            JOptionPane.showConfirmDialog(frame, "�����ɹ�", "��ʾ", JOptionPane.CLOSED_OPTION);
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
                        } else JOptionPane.showConfirmDialog(frame, "����ʧ��", "��ʾ", JOptionPane.CLOSED_OPTION);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
            }
        });

        frame.setVisible(true);
    }
}
