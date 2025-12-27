package dao;

import Koneksi.DBConnection;
import java.sql.*;


public class TransaksiDAO {

    private Connection conn;

    public TransaksiDAO() {
        try {
            conn = DBConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // ===============================
    // SIMPAN TRANSAKSI (HEADER)
    // ===============================
    public int insert(int total) {
        int idTransaksi = 0;
        String sql = "INSERT INTO transaksi (tanggal, total) VALUES (NOW(), ?)";

        try (PreparedStatement ps = conn.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, total);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idTransaksi = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idTransaksi;
    }

    // ===============================
    // SIMPAN DETAIL TRANSAKSI
    // ===============================
    public void insertDetail(int idTransaksi,String namaMenu, int harga, int qty) {
        String sql = "INSERT INTO transaksi_detail "
                   + "(id_transaksi, nama_menu, harga, qty, subtotal) "
                   + "VALUES (?,?,?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTransaksi);
            ps.setString(2, namaMenu);
            ps.setInt(3, harga);
            ps.setInt(4, qty);
            ps.setInt(5, harga * qty);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
