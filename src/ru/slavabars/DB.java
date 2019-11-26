package ru.slavabars;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DB {

    Map<Integer, String> langs;

    private Connection connect() {
        String url = "jdbc:sqlite:database.sqlite3";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public Map<Integer, String> langs() {
        String sql = "SELECT id, lang FROM langs";
        langs = new HashMap<Integer, String>();
        try {
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                langs.put(rs.getInt("id"), rs.getString("lang"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return langs;
    }
}
