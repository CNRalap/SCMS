package frame;


import DataBase.DataBaseOperation;
import com.formdev.flatlaf.FlatDarkLaf;
import frame.ChildFrame.SelectCourse;
import frame.ChildFrame.SselectMySC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;

/*
 * @author ��ҵ��
 * @version 1.0
 * @time 2022-11-29
 * @comment ѧ���Ĳ�������
 */
public class StudentMenu {
    public StudentMenu(String Sno) {
        //����UI���棬����������
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        //���ô��ڴ�С��λ�ã��������϶�����С�Ŵ�
        JFrame frame = new JFrame("ѧ����" + Sno);
        Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (ScreenSize.getWidth() / 2 - 500 / 2);
        int y = (int) (ScreenSize.getHeight() / 2 - 300 / 2);
        frame.setBounds(x, y, 500, 300);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setFont(new Font("΢���ź�", Font.BOLD, 14));

        //��Ӱ�ť
        JButton button1 = new JButton("�鿴�γ̱�");
        JButton button2 = new JButton("��ѯѡ�����");
        JButton button3 = new JButton("����ѡ��");
        button1.setBounds(180, 60, 120, 25);
        button2.setBounds(180, 120, 120, 25);
        button3.setBounds(180, 180, 120, 25);
        frame.add(button1);
        frame.add(button2);
        frame.add(button3);

        //������ť1����
        button1.addActionListener(e -> {
            try {
                SelectCourse selectCourse = new SelectCourse();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        //������ť2����
        button2.addActionListener(e -> {
            try {
                SselectMySC sselectMySC = new SselectMySC(Sno);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        //������ť3����
        button3.addActionListener(e -> {
            String Cno = JOptionPane.showInputDialog("��������Ҫѡ�޵Ŀγ̺�");
            if (!Cno.isEmpty())
                try {
                    boolean flag = DataBaseOperation.AddSC(Cno, Sno);
                    if (flag) JOptionPane.showConfirmDialog(frame, "ѡ�γɹ�", "��ʾ", JOptionPane.CLOSED_OPTION);
                    else JOptionPane.showConfirmDialog(frame, "ѡ��ʧ��", "��ʾ", JOptionPane.CLOSED_OPTION);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
        });

        //�ر�ʱ������ʾ
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); //Ĭ�Ϲر���Ϊ�趨Ϊʲô������
        frame.addWindowListener(new WindowAdapter() { //�趨�رմ��ڵ���Ϊ
            @Override
            public void windowClosing(WindowEvent e) {
                int value = JOptionPane.showConfirmDialog(frame, "�Ƿ�Ҫ�˳�?", "��ʾ", JOptionPane.YES_NO_OPTION); //�����Ի������ѯ���Ƿ�ر�
                if (value == JOptionPane.OK_OPTION) System.exit(0);
            }
        });

        frame.setVisible(true);
    }
}
