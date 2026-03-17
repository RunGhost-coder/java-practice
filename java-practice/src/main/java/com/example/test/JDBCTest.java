package com.example.test;
import com.example.util.JDBCUtil;
import java.sql.*;
public class JDBCTest {
    public static void main(String[] args) {
        // 你可以分别调用各个方法测试
        selectAll();
    }
    public static void selectAll() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT id, name, age, email FROM user";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String email = rs.getString("email");
                System.out.println(id + "\t" + name + "\t" + age + "\t" + email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn, stmt, rs);
        }
    }
    public static void insert() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtil.getConnection();
            String sql = "INSERT INTO user (name, age, email) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "王五");
            pstmt.setInt(2, 28);
            pstmt.setString(3, "wangwu@example.com");
            int rows = pstmt.executeUpdate();
            System.out.println("插入了 " + rows + " 行");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn, pstmt, null);
        }
    }
    public static void update() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtil.getConnection();
            String sql = "UPDATE user SET age = ? WHERE name = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 26);
            pstmt.setString(2, "张三");
            int rows = pstmt.executeUpdate();
            System.out.println("更新了 " + rows + " 行");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn, pstmt, null);
        }
    }
    public static void delete() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtil.getConnection();
            String sql = "DELETE FROM user WHERE name = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "李四");
            int rows = pstmt.executeUpdate();
            System.out.println("删除了 " + rows + " 行");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn, pstmt, null);
        }
    }
}
