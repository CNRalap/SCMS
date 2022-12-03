package frame;

import DataBase.DataBaseOperation;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;

/*
 * @author ��ҵ��
 * @version 1.0
 * @time 2022-11-28
 * @comment ��¼����
 */
public class LoginMenu {
    public static void main(String[] args) {
        //����UI���棬����������
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        //���ô��ڴ�С��λ�ã��������϶�����С�Ŵ�
        JFrame frame = new JFrame("ѧ��ѡ�ι���ϵͳ");
        Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (ScreenSize.getWidth() / 2 - 500 / 2);
        int y = (int) (ScreenSize.getHeight() / 2 - 300 / 2);
        frame.setBounds(x, y, 500, 300);
        frame.setLayout(null);
        frame.setResizable(false);

        //���������ǩ
        JLabel label1 = new JLabel("�û���¼");
        label1.setFont(new Font("΢���ź�", Font.BOLD, 20));
        label1.setBounds(210, 40, 100, 20);
        frame.add(label1);

        //�����ı���������
        JTextField field1 = new JTextField();
        JPasswordField jPasswordField = new JPasswordField();
        field1.setFont(new Font("΢���ź�", Font.PLAIN, 15));
        jPasswordField.setFont(new Font("΢���ź�", Font.PLAIN, 15));
        field1.setBounds(160, 80, 200, 20);
        jPasswordField.setBounds(160, 120, 200, 20);
        field1.setText("�������ʺ�");
        jPasswordField.setText("123456");
        frame.add(field1);
        frame.add(jPasswordField);

        //���õ�¼��ť
        JButton button = new JButton("��¼");
        button.setFont(new Font("΢���ź�", Font.BOLD, 14));
        button.setBounds(220, 160, 60, 20);
        frame.add(button);

        //�ر�ʱ������ʾ
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); //Ĭ�Ϲر���Ϊ�趨Ϊʲô������
        frame.addWindowListener(new WindowAdapter() { //�趨�رմ��ڵ���Ϊ
            @Override
            public void windowClosing(WindowEvent e) {
                int value = JOptionPane.showConfirmDialog(frame, "�Ƿ�Ҫ�˳�?", "��ʾ", JOptionPane.YES_NO_OPTION); //�����Ի������ѯ���Ƿ�ر�
                if (value == JOptionPane.OK_OPTION) System.exit(0);
            }
        });


        //���ü�������ȡ�ʺź����룬������С��Ŀ����MD5���ܴ�����˰�ȫ�Խϵ�
        //����ʺź����������򵯳���ʾ����
        button.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String account = field1.getText();
                        String pwd = String.valueOf(jPasswordField.getPassword());
                        int flag = 100;
                        try {
                            flag = DataBaseOperation.CheckUser(account, pwd);
                        } catch (SQLException | IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        if (flag == 0) {
                            int value = JOptionPane.showConfirmDialog(frame, "�ʺŻ���������", "��ʾ", JOptionPane.CLOSED_OPTION);
                        } else if (flag == 1) {
                            StudentMenu studentMenu = new StudentMenu(account);
                            frame.dispose();
                        } else {
                            TeacherMenu teacherMenu = new TeacherMenu(account);
                            frame.dispose();
                        }
                    }
                }
        );

        //���������ÿ��ӣ����������Ϊ�˱������������û���س���������
        frame.setVisible(true);
    }
}
