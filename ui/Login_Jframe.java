package ui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.ImageIcon;
import Mysql_op.mysql_in;

public class Login_Jframe extends JFrame implements DocumentListener, ActionListener, MouseListener {
    // 素材路径
    private String path = "PTgame_demo\\image\\login\\";
    // 随机数
    private Random ran = new Random(System.currentTimeMillis());
    // 用户名输入文本框
    private JTextField user_text = new JTextField();
    // 密码输入文本框
    private JPasswordField pass_text = new JPasswordField();
    // 验证码输入文本框
    private JTextField pass_code_text = new JTextField();
    // 登录按钮
    private String sign_str;
    // 注册按钮
    private String sign_up_str;
    // 验证码
    private String code_str;
    // 验证码
    private JLabel code;
    // 生成的验证码
    private String code_str_create;
    // 用户账户
    private String user_account;
    // 用户密码
    private String user_password;
    // 输入的验证码
    private String input_code;
    // 登录按钮
    private JLabel login_button;
    // 注册按钮
    private JLabel register_button;
    // 显示密码按钮
    private JLabel hide_pass_b;
    // 数据库引擎
    private mysql_in T;

    public Login_Jframe() {
        initDate();
        initJframe();
        initUI();
        this.setVisible(true);
    }

    private void initDate() {
        try {
            T = new mysql_in();
        } catch (Exception e) {
            System.out.println("数据库连接失败");
            return;
        }
        sign_str = "登录按钮.png";
        sign_up_str = "注册按钮.png";
        code_str = "显示密码.png";
        user_account = "请输入用户名";
        user_password = "请输入密码";
        input_code = "请输入验证码";
        code_str_create = create_code();
    }

    private String create_code() {
        char S[] = new char[4];

        S[0] = (char) (ran.nextInt(9) + 48);
        for (int i = 1; i < 4; i++) {
            if (i % 2 == 0)
                S[i] = (char) (ran.nextInt(26) + 97);
            else
                S[i] = (char) (ran.nextInt(26) + 65);
        }

        for (int i = 0; i < 4; i++) {
            int n = ran.nextInt(4);
            char c = S[n];
            S[n] = S[i];
            S[i] = c;
        }
        System.out.println(String.valueOf(S));
        return String.valueOf(S);
    }

    public void initUI() {
        this.getContentPane().removeAll();
        JLabel user = new JLabel(new ImageIcon(path + "用户名.png"));
        JLabel pass = new JLabel(new ImageIcon(path + "密码.png"));
        JLabel pass_code = new JLabel(new ImageIcon(path + "验证码.png"));

        login_button = new JLabel(new ImageIcon(path + sign_str));
        register_button = new JLabel(new ImageIcon(path + sign_up_str));
        hide_pass_b = new JLabel(new ImageIcon(path + code_str));
        code = new JLabel(code_str_create);

        user.setBounds(100, 100, 100, 50);
        pass.setBounds(110, 150, 100, 50);
        pass_code.setBounds(103, 200, 100, 50);
        hide_pass_b.setBounds(338, 165, 25, 25);
        login_button.setBounds(75, 280, 130, 60);
        register_button.setBounds(275, 280, 130, 60);
        code.setBounds(280, 213, 80, 25);

        this.getContentPane().add(user);
        this.getContentPane().add(pass);
        this.getContentPane().add(hide_pass_b);
        this.getContentPane().add(pass_code);
        this.getContentPane().add(login_button);
        this.getContentPane().add(register_button);
        this.getContentPane().add(code);

        user_text.setText(user_account);
        user_text.setBounds(190, 115, 150, 25);
        this.getContentPane().add(user_text);

        pass_text.setText(user_password);
        pass_text.setBounds(190, 165, 150, 25);
        this.getContentPane().add(pass_text);

        pass_code_text.setText(input_code);
        pass_code_text.setBounds(190, 213, 80, 25);
        this.getContentPane().add(pass_code_text);

        // 绑定事件
        user_text.getDocument().addDocumentListener(this);
        pass_text.getDocument().addDocumentListener(this);
        pass_code_text.getDocument().addDocumentListener(this);
        login_button.addMouseListener(this);
        register_button.addMouseListener(this);
        hide_pass_b.addMouseListener(this);
        code.addMouseListener(this);

        JLabel background = new JLabel(new ImageIcon(path + "background.png"));
        background.setBounds(0, 0, 470, 400);
        this.getContentPane().add(background);

        this.getContentPane().repaint();
    }

    public void initJframe() {
        this.setSize(485, 430);
        // 设置窗口标题
        this.setTitle("XC的拼图游戏");
        // 设置窗口置顶
        this.setAlwaysOnTop(true);
        // 设置窗口位置居中
        this.setLocationRelativeTo(null);
        // 设置窗口关闭模式
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 取消默认的居中方式(只有取消了才能按照xy坐标设置)
        this.setLayout(null);// 不设置的话缩放窗口，图片会乱跑
    }

    @Override
    public void changedUpdate(DocumentEvent arg0) {
    }

    @Override
    public void insertUpdate(DocumentEvent arg0) {
        // 文本发生变化时触发
        Object text = arg0.getDocument();
        if (text == user_text.getDocument()) {
            user_account = user_text.getText();
        } else if (text == pass_text.getDocument()) {
            user_password = pass_text.getText();
        } else if (text == pass_code_text.getDocument()) {
            input_code = pass_code_text.getText();
        }
    }

    @Override
    public void removeUpdate(DocumentEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // 在数据库中查询用户名密码是否正确
    private boolean check_user_pass() {
        if (T.is_user(user_account, user_password))
            return true;
        else
            return false;
    }

    // 检查验证码是否正确
    private boolean check_code() {
        if (input_code.equalsIgnoreCase(code_str_create))
            return true;
        else
            return false;
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
        Object button = e.getSource();
        if (button == login_button) {
            if (sign_str.equals("登录按钮.png"))
                sign_str = "登录按下.png";
            if (check_user_pass()) {
                if (check_code()) {
                    new Game_Jframe(user_account);
                } else
                    Pop_error("验证码错误");
            } else
                Pop_error("用户名或密码错误");
            sign_str = "登录按钮.png";
            login_button.requestFocusInWindow();
        } else if (button == register_button) {
            if (sign_up_str.equals("注册按钮.png"))
                sign_up_str = "注册按下.png";
            new Register_Jframe();
            sign_up_str = "注册按钮.png";
            register_button.requestFocusInWindow();
        } else if (button == hide_pass_b) {
            if (code_str.equals("显示密码.png")) {
                code_str = "显示密码按下.png";
                pass_text.setEchoChar((char) 0);
            } else {
                code_str = "显示密码.png";
                pass_text.setEchoChar('*');
            }
            hide_pass_b.requestFocusInWindow();
        } else if (button == code) {
            code.requestFocusInWindow();// 防止光标丢失
            initDate();
        }
        user_account = user_text.getText();
        user_password = pass_text.getText();
        input_code = pass_code_text.getText();

        initUI();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

}
