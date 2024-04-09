package Mysql_op;

import java.io.FileInputStream;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class mysql_in {
    private Connection conn;
    private Statement stmt;
    private ArrayList<userTable> list = new ArrayList<>();

    public mysql_in() throws ClassNotFoundException, SQLException {
        InitData();
    }

    public void InitData() throws ClassNotFoundException, SQLException {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("PTgame_demo\\Mysql_op\\mysql.properties"));
        } catch (Exception e) {
            System.out.println("配置文件加载失败");
        }

        Class.forName(props.getProperty("driver"));
        System.out.println("加载驱动成功");
        conn = DriverManager.getConnection(props.getProperty("url"), props.getProperty("user"),
                props.getProperty("pass"));
        System.out.println("连接数据库成功");
    }

    // 查询用户
    public boolean is_user(String username, String password) {
        String sql = "SELECT * FROM Pt_game WHERE username = " + '"' + username + '"' + " AND password = " + '"'
                + password + '"' + "";
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
                return true;
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    // 注册用户
    public boolean insert_user(String username, String password) {
        String sql = "INSERT INTO Pt_game (username,password) VALUES ('" + username + "','" + password + "')";
        try {
            stmt = conn.createStatement();
            int count = stmt.executeUpdate(sql);
            if (count > 0)
                return true;
        } catch (SQLException e) {
            System.err.println("注册失败,服务器异常");
        }
        return false;
    }

    // 更新分数
    public boolean update_score(String username, int score) {
        String sql = "UPDATE Pt_game SET score = " + score + " WHERE username = " + '"' + username + '"' + "and" + score
                + "<" + "score";
        try {
            stmt = conn.createStatement();
            int count = stmt.executeUpdate(sql);
            if (count > 0)
                return true;
        } catch (SQLException e) {
            System.err.println("更新分数失败,服务器异常");
        }
        return false;
    }

    // 获取用户名和成绩
    public ArrayList<userTable> get_user_score() {
        String sql = "SELECT username,score FROM Pt_game order by score ASC";
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String username = rs.getString("username");
                int score = rs.getInt("score");
                userTable user = new userTable(username, "", score);
                list.add(user);
            }
        } catch (SQLException e) {
            System.err.println("获取用户名和成绩失败,服务器异常");
        }
        return list;
    }
}