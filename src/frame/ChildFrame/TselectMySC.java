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
 * @time 2022-12-2
 * @comment ���Բ鿴��ʦ�Լ������ĳһ�γ̵�ѧ������ɼ������ҿ����޸�ѧ���ɼ�
 */
public class TselectMySC {
    public TselectMySC(String Tno, String Cno) throws SQLException {
        //����UI���棬����������
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        //���ô��ڴ�С��λ�ã��������϶�����С�Ŵ�
        JFrame frame = new JFrame("�γ̺ţ�" + Cno);
        Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (ScreenSize.getWidth() / 2 - 800 / 2);
        int y = (int) (ScreenSize.getHeight() / 2 - 600 / 2);
        frame.setBounds(x, y, 800, 600);
        frame.setResizable(false);

        //���ò˵����Ͱ���
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("�༭");
        JMenuItem item = new JMenuItem("�༭�ɼ�");
        menu.add(item);
        bar.add(menu);
        frame.setJMenuBar(bar);

        //ִ��SQL���
        Connection connection = DataBaseUtils.getConnection();
        String sql = "select Sno,Grade from sc where Tno=? and Cno=? ";
        final PreparedStatement[] preparedStatement = {connection.prepareStatement(sql)};
        preparedStatement[0].setString(1, Tno);
        preparedStatement[0].setString(2, Cno);
        final ResultSet[] resultSet = {preparedStatement[0].executeQuery()};

        //���õ���������䵽�����
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

        //�½����
        JTable table = new JTable();
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        //�Բ˵�������������ͬʱˢ�±��
        item.addActionListener(new ActionListener() {
                                   @Override
                                   public void actionPerformed(ActionEvent e) {
                                       String Sno = JOptionPane.showInputDialog("������Ҫ�޸ĳɼ���ѧ����ѧ��");
                                       String Grade = JOptionPane.showInputDialog("������ɼ�");
                                       try {
                                           boolean f = DataBaseOperation.UpdateGrade(Grade, Sno, Cno, Tno);
                                           if (f) {
                                               JOptionPane.showConfirmDialog(frame, "�޸ĳɹ�", "��ʾ", JOptionPane.CLOSED_OPTION);
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
                                           } else JOptionPane.showConfirmDialog(frame, "�޸�ʧ��", "��ʾ", JOptionPane.CLOSED_OPTION);
                                       } catch (SQLException ex) {
                                           throw new RuntimeException(ex);
                                       }
                                   }
                               }
        );

        //������ʾ��ѯ����
        if (flag) {
            JOptionPane.showMessageDialog(frame, "��ѯ�γ̺�����", "��ʾ", JOptionPane.CLOSED_OPTION);
            return;
        }

        frame.setVisible(true);
    }
}
