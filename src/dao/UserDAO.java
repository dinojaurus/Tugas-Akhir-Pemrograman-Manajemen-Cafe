package dao;

import Koneksi.DBConnection;
import util.HashUtil;
import java.sql.*;

public class UserDAO {

    public boolean login(String username, String password) {
        try {
            Connection c = Koneksi.DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, HashUtil.hash(password)); // WAJIB HASH
            ResultSet rs = ps.executeQuery();
            return rs.next(); // true jika ada
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // REGISTER (opsional)
    public boolean register(String username, String password) {
        try {
            Connection c = Koneksi.DBConnection.getConnection();
            String sql = "INSERT INTO users (username, password) VALUES (?,?)";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, HashUtil.hash(password));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}