package ui;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import Mysql_op.mysql_in;
import Mysql_op.userTable;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game_Jframe extends JFrame implements KeyListener, ActionListener {
    private int file_list[][] = new int[4][4];
    private int temp_list[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0 };
    private int x = 0;
    private int y = 0;
    private Random ran = new Random(System.currentTimeMillis());
    private String path = "PTgame_demo\\image\\girl\\girl";
    private int step = 0;
    // 当前登录的用户
    private String User;
    // 创建菜单上的选项面板对象
    private JMenu function_menu = new JMenu("功能");
    private JMenu about_menu = new JMenu("关于我们");
    // 创建功能面板上的选项对象
    private JMenu choese_picture = new JMenu("选择图片");
    private JMenuItem girl = new JMenuItem("美女");
    private JMenuItem animal = new JMenuItem("动物");
    private JMenuItem sport = new JMenuItem("运动");
    private JMenuItem remake_game = new JMenuItem("重新游戏");
    private JMenuItem remake_login = new JMenuItem("重新登录");
    private JMenuItem close_game = new JMenuItem("关闭游戏");
    private JMenuItem public_num = new JMenuItem("公众号");
    private JMenuItem show_score = new JMenuItem("查看分数");
    // 链接数据库以便保存分数
    private mysql_in mysql;

    private boolean is_game_over() {
        int c = 1;
        for (int i = 0; i < file_list.length; i++) {
            for (int j = 0; j < file_list[i].length; j++) {
                if (i == 3 && j == 3)
                    c = 0;
                if (file_list[i][j] != c)
                    return false;
                else
                    c++;
            }
        }
        return true;
    }

    Game_Jframe() {
    }

    public Game_Jframe(String user) {
        this.User = user;
        int start = 10, end = 1;
        if (User.equals("123456")) {
            start = 1;
            end = 12;
        }
        int num = ran.nextInt(start) + end;
        path += num + "\\";
        initJframe();
        initJmenu();
        initData();
        initImage();
        // 设置窗口是否可见（一般最后）
        this.setVisible(true);
    }

    private void initData() {
        Random rand = new Random();
        try {
            mysql = new mysql_in();
        } catch (Exception e) {
            System.out.println("数据库连接失败");
        }
        for (int i = 0; i < temp_list.length - 1; i++) {
            int j = rand.nextInt(temp_list.length - 1);
            int temp = temp_list[i];
            temp_list[i] = temp_list[j];
            temp_list[j] = temp;
        }

        for (int i = 0; i < temp_list.length; i++) {
            if (temp_list[i] == 0) {
                x = i / 4;
                y = i % 4;
            }
            file_list[i / 4][i % 4] = temp_list[i];
        }

    }

    // 初始化图片
    private void initImage() {
        // 清空图片
        this.getContentPane().removeAll();

        // 判断胜利
        if (is_game_over()) {
            JLabel win = new JLabel(new ImageIcon("PTgame_demo\\image\\win.png"));
            win.setBounds(325, 322, 197, 73);
            this.getContentPane().add(win);
            // 刷新图片
            this.getContentPane().repaint();
            if (mysql.update_score(User, step)) {
                System.out.println("保存分数成功");
            } else
                System.out.println("保存分数失败,数据库未连接,或者当前分数低于历史分数");
        }

        // 记录步数
        JLabel step_label = new JLabel("您当前的步数：" + step);
        step_label.setBounds(50, 30, 200, 100);
        this.getContentPane().add(step_label);

        // 先添加的图片在上面，后添加的图片在下面
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++) {
                // 图片
                String image_path = path + file_list[i][j] + ".jpg";
                // 创建一个JLabel对象(管理容器)
                JLabel label = new JLabel(new ImageIcon(image_path));
                // 指定Jlabel位置大小
                label.setBounds(105 * j + 183, 105 * i + 150, 105, 105);
                // 给图片添加边框(0:标识让图片凸起来，1:标识让图片凹下来)
                label.setBorder(new BevelBorder(BevelBorder.LOWERED));
                // 把管理容器添加到界面中
                this.getContentPane().add(label);
            }
        JLabel background = new JLabel(new ImageIcon("PTgame_demo\\image\\background.png"));
        background.setBounds(139, 56, 508, 560);
        this.getContentPane().add(background);

        // 刷新图片
        this.getContentPane().repaint();
    }

    private void initJframe() {
        // 设置窗口宽高
        this.setSize(800, 800);
        // 设置窗口标题
        this.setTitle("XC的拼图游戏");
        // 设置窗口置顶
        this.setAlwaysOnTop(true);
        // 设置窗口位置居中
        this.setLocationRelativeTo(null);
        // 设置窗口关闭模式
        // DO_NOTHING_ON_CLOSE：不关闭窗口，窗口内的组件会继续执行
        // EXIT_ON_CLOSE：关闭窗口，窗口内的组件也会关闭
        // HIDE_ON_CLOSE：关闭窗口，窗口内的组件会隐藏；
        // DISPOSE_ON_CLOSE：关闭窗口，窗口内的组件会销毁。
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        // 取消默认的居中方式(只有取消了才能按照xy坐标设置)
        this.setLayout(null);
        this.addKeyListener(this);
    }

    private void initJmenu() {
        // 创建菜单栏
        JMenuBar menuBar = new JMenuBar();

        // 添加选项到功能面板
        function_menu.add(choese_picture);

        choese_picture.add(girl);
        choese_picture.add(animal);
        choese_picture.add(sport);
        function_menu.add(remake_game);
        function_menu.add(remake_login);
        function_menu.add(close_game);
        function_menu.add(show_score);
        about_menu.add(public_num);

        // 给条目绑定事件
        girl.addActionListener(this);
        animal.addActionListener(this);
        sport.addActionListener(this);
        remake_game.addActionListener(this);
        remake_login.addActionListener(this);
        close_game.addActionListener(this);
        public_num.addActionListener(this);
        show_score.addActionListener(this);

        // 添加面板到菜单栏
        menuBar.add(function_menu);
        menuBar.add(about_menu);

        // 给整个界面设置菜单
        this.setJMenuBar(menuBar);
    }

    @Override // 按住不送
    public void keyPressed(KeyEvent e) {
        if (is_game_over())
            return;
        int code = e.getKeyCode();
        if (code == 65 || code == 97) {
            this.getContentPane().removeAll();
            JLabel all = new JLabel(new ImageIcon(path + "all.jpg"));
            all.setBounds(183, 150, 420, 420);
            this.getContentPane().add(all);
            JLabel background = new JLabel(new ImageIcon("PTgame_demo\\image\\background.png"));
            background.setBounds(139, 56, 508, 560);
            this.getContentPane().add(background);
            this.getContentPane().repaint();
        }
    }

    private boolean is_aryy_list(int x, int y) {
        if (x >= 0 && x < 4 && y >= 0 && y < 4)
            return true;
        return false;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (is_game_over())
            return;

        int code = e.getKeyCode();
        if (code == 37 || code == 38 || code == 39 || code == 40)
            step += 1;
        // 左
        if (code == 37) {
            if (is_aryy_list(x, y - 1)) {
                file_list[x][y] = file_list[x][y - 1];
                file_list[x][y - 1] = 0;
                y--;
            }
        }
        // 上
        else if (code == 38) {
            if (is_aryy_list(x - 1, y)) {
                file_list[x][y] = file_list[x - 1][y];
                file_list[x - 1][y] = 0;
                x--;
            }
        }
        // 右
        else if (code == 39) {
            if (is_aryy_list(x, y + 1)) {
                file_list[x][y] = file_list[x][y + 1];
                file_list[x][y + 1] = 0;
                y++;
            }
        }
        // 下
        else if (code == 40) {
            if (is_aryy_list(x + 1, y)) {
                file_list[x][y] = file_list[x + 1][y];
                file_list[x + 1][y] = 0;
                x++;
            }
        } else if (code == 'w' || code == 'W') {
            file_list = new int[][] {
                    { 1, 2, 3, 4 },
                    { 5, 6, 7, 8 },
                    { 9, 10, 11, 12 },
                    { 13, 14, 15, 0 }
            };
            x = 3;
            y = 3;
            initImage();
        }
        initImage();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        path = "PTgame_demo\\image\\";
        if (obj == girl) {
            System.out.println("美女");
            path += "girl\\girl";
            int start = 10, end = 1;
            if (User.equals("123456")) {
                start = 1;
                end = 12;
            }
            int num = ran.nextInt(start) + end;
            path += num + "\\";
            initData();
            initImage();
        } else if (obj == animal) {
            System.out.println("动物");
            path += "animal\\animal";
            int num = ran.nextInt(7) + 1;
            path += num + "\\";
            initData();
            initImage();
        } else if (obj == sport) {
            System.out.println("运动");
            path += "sport\\sport";
            int num = ran.nextInt(9) + 1;
            path += num + "\\";
            initData();
            initImage();
        } else if (obj == remake_game) {
            System.out.println("重新游戏");
            step = 0;
            initData();
            initImage();
        } else if (obj == remake_login) {
            System.out.println("重新登录");
            this.setVisible(false);
            new Login_Jframe();
        } else if (obj == close_game) {
            System.out.println("关闭游戏");
            System.exit(0);
        } else if (obj == public_num) {
            System.out.println("公众号");
            // 创建一个弹窗对象
            JDialog jDialog = new JDialog();
            JLabel jLabel = new JLabel(new ImageIcon("PTgame_demo\\image\\about.png"));
            jLabel.setBounds(0, 0, 200, 200);
            jDialog.getContentPane().add(jLabel);
            // 设置弹窗大小
            jDialog.setSize(620, 604);
            // 设置弹窗置顶
            jDialog.setAlwaysOnTop(true);
            // 设置弹窗居中
            jDialog.setLocationRelativeTo(null);
            // 弹窗不关闭，无法操作下面的界面
            jDialog.setModal(true);
            // 显示弹窗
            jDialog.setVisible(true);
        } else if (obj == show_score) {
            System.out.println("查看分数");
            this.getContentPane().repaint();
            show_score();
        }
    }

    private void show_score() {
        ArrayList<userTable> S = mysql.get_user_score();
        JDialog dialog = new JDialog();
        dialog.setTitle("分数榜单");
        dialog.setSize(300, 400);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        // 设置弹窗置顶
        dialog.setAlwaysOnTop(true);
        // 设置弹窗居中
        dialog.setLocationRelativeTo(null);
        // 弹窗不关闭，无法操作下面的界面
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        for (userTable it : S) {
            textArea.append("用户名：" + it.getUsername() + "\t" + "分数：" + it.getScore() + "分" + "\n");
        }
        JScrollPane scrollPane = new JScrollPane(textArea);
        dialog.add(scrollPane, BorderLayout.CENTER);

        dialog.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

}
