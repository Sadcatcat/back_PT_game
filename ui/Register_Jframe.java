package ui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import Mysql_op.mysql_in;

public class Register_Jframe extends JFrame implements ActionListener, DocumentListener, MouseListener {
    // 路径
    private String path = "PTgame_demo\\image\\register\\";
    // 用户名输入框
    private JTextField user_input_text = new JTextField();
    // 密码输入框
    private JPasswordField pass_input_text = new JPasswordField();
    // 确认密码输入框
    private JPasswordField again_pass_input_text = new JPasswordField();
    // 用户输入内容
    private String user_input_str;
    // 密码输入内容
    private String pass_input_str;
    // 确认密码输入内容
    private String again_pass_input_str;
    // 注册按钮状态
    private String regist_btn_str;
    // 重置按钮状态
    private String reset_btn_str;
    // 注册按钮
    private JLabel regist_btn;
    // 重置按钮
    private JLabel reset_btn;
    // 数据库操作对象
    private mysql_in mysql;

    public Register_Jframe() {
        initData();
        initRUI();
        initJframe();
        this.setVisible(true);
    }

    private void initData() {
        try {
            mysql = new mysql_in();
        } catch (Exception e) {
            System.out.println("数据库连接失败");
            return;
        }
        regist_btn_str = "注册按钮.png";
        reset_btn_str = "重置按钮.png";
        user_input_str = "请输入你要注册的用户名";
        pass_input_str = "";
        again_pass_input_str = "";
    }

    private void initJframe() {
        this.setSize(480, 430);
        // 设置窗口标题
        this.setTitle("XC的拼图游戏");
        // 设置窗口置顶
        this.setAlwaysOnTop(true);
        // 设置窗口位置居中
        this.setLocationRelativeTo(null);
        // 设置窗口关闭模式
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLayout(null);// 不设置的话缩放窗口，图片会乱跑
    }

    private void initRUI() {
        this.getContentPane().removeAll();

        JLabel user_text = new JLabel(new ImageIcon(path + "注册用户名.png"));
        JLabel pass_text = new JLabel(new ImageIcon(path + "注册密码.png"));
        JLabel again_pass_text = new JLabel(new ImageIcon(path + "再次输入密码.png"));

        regist_btn = new JLabel(new ImageIcon(path + regist_btn_str));
        reset_btn = new JLabel(new ImageIcon(path + reset_btn_str));

        regist_btn.setBounds(42, 280, 200, 100);
        reset_btn.setBounds(232, 280, 200, 100);

        user_text.setBounds(20, 110, 200, 50);
        pass_text.setBounds(30, 170, 200, 50);
        again_pass_text.setBounds(20, 230, 200, 50);

        user_input_text.setBounds(185, 122, 200, 30);
        pass_input_text.setBounds(185, 182, 200, 30);
        again_pass_input_text.setBounds(185, 242, 200, 30);

        user_input_text.setText(user_input_str);
        pass_input_text.setText(pass_input_str);
        again_pass_input_text.setText(again_pass_input_str);

        this.getContentPane().add(user_input_text);
        this.getContentPane().add(pass_input_text);
        this.getContentPane().add(again_pass_input_text);
        this.getContentPane().add(user_text);
        this.getContentPane().add(pass_text);
        this.getContentPane().add(again_pass_text);
        this.getContentPane().add(regist_btn);
        this.getContentPane().add(reset_btn);

        user_input_text.getDocument().addDocumentListener(this);
        pass_input_text.getDocument().addDocumentListener(this);
        again_pass_input_text.getDocument().addDocumentListener(this);
        regist_btn.addMouseListener(this);
        reset_btn.addMouseListener(this);

        JLabel backg = new JLabel(new ImageIcon(path + "background.png"));
        backg.setBounds(-20, -50, 500, 500);
        this.getContentPane().add(backg);

        this.getContentPane().repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        Object text = e.getDocument();
        if (text == user_input_text.getDocument()) {
            user_input_str = user_input_text.getText();
        } else if (text == pass_input_text.getDocument()) {
            pass_input_str = pass_input_text.getText();
        } else if (text == again_pass_input_text.getDocument()) {
            again_pass_input_str = again_pass_input_text.getText();
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    // 检查是否可以注册
    private int checkRegist() {
        if (user_input_str.equals("") || pass_input_str.equals("") || again_pass_input_str.equals(""))
            return 1;
        else if (pass_input_str.length() < 6)
            return 2;
        else if (mysql.is_user(pass_input_str, again_pass_input_str))
            return 3;
        else if (mysql.insert_user(pass_input_str, again_pass_input_str))
            return 4;
        return 0;
    }

    private void Pop_error(String str) {
        JDialog dw = new JDialog();
        JLabel error = new JLabel(str);
        error.setBounds(200, 100, 200, 50);
        error.setHorizontalAlignment(SwingConstants.CENTER);
        error.setForeground(Color.RED);
        error.setFont(new Font("微软雅黑", Font.BOLD, 20));
        dw.add(error);
        dw.setSize(300, 150);
        // 设置弹窗居中
        dw.setLocationRelativeTo(null);
        // 设置弹窗置顶
        dw.setAlwaysOnTop(true);
        // 弹窗不关闭，无法操作下面的界面
        dw.setModal(true);
        dw.setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object bt = e.getSource();
        if (bt == regist_btn) {
            if (regist_btn_str == "注册按钮.png") {
                regist_btn_str = "注册按下.png";
                switch (checkRegist()) {
                    case 0:
                        Pop_error("注册失败,服务器异常");
                        break;
                    case 1:
                        Pop_error("注册失败,请填写完整信息");
                        break;
                    case 2:
                        Pop_error("注册失败,密码长度不能小于6位");
                        break;
                    case 3:
                        Pop_error("注册失败,用户名已存在");
                        break;
                    case 4:
                        Pop_error("注册成功");
                        break;
                    default:
                        Pop_error("注册失败,未知错误");
                        break;
                }
            } else
                regist_btn_str = "注册按钮.png";
            regist_btn.requestFocusInWindow();// 放置光标回到文本框
        } else if (bt == reset_btn) {
            if (reset_btn_str == "重置按钮.png") {
                reset_btn_str = "重置按下.png";
                user_input_str = "请输入你要注册的用户名";
                pass_input_str = "";
                again_pass_input_str = "";
            } else
                reset_btn_str = "重置按钮.png";
            reset_btn.requestFocusInWindow();
        }
        initRUI();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

}
