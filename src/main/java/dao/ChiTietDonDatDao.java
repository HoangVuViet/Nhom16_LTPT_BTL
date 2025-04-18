package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import connect.ConnectDB;
import entity.ChiTietDonDat;
import entity.DonDatThuoc;
import entity.KhachHang;

public class ChiTietDonDatDao {

	public ChiTietDonDatDao() {
	// TODO Auto-generated constructor stub
	}
	
	public ArrayList<ChiTietDonDat> layTatCaChiTietDonBangMaDonNhap(String ma) throws SQLException{
		ArrayList<ChiTietDonDat> list=new ArrayList<ChiTietDonDat>();
		Connection con = ConnectDB.getConnection();
		ConnectDB.getInstance();
		PreparedStatement stmt = null;
		try {
			String sql = "SELECT * FROM ChiTietDonNhap WHERE MaDonNhap = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, ma);
			// ketqua
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maDN=rs.getString("MaDonNhap");
				String maThuoc=rs.getString("MaThuoc");
				int soLuongNhap=rs.getInt("SoLuongNhap");
				String tenThuoc = rs.getString("TenThuoc");
				double giaNhap = rs.getDouble("giaNhap");
				ChiTietDonDat c=new ChiTietDonDat(maDN, maThuoc, soLuongNhap, tenThuoc, giaNhap);
				list.add(c);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(con!=null) {
				try {
					stmt.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	public boolean taoChiTietDonDat(ChiTietDonDat d) throws SQLException {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement pstmt = null;
		int n = 0;
		try {
			pstmt = con.prepareStatement("insert into"
					+ " ChiTietDonNhap (maDonNhap,maThuoc, soLuongNhap,tenThuoc,giaNhap)"
					+ " values(?,?,?,?,?)");
			pstmt.setString(1, d.getMaDondat());
			pstmt.setString(2, d.getMaThuoc());
			pstmt.setInt(3, d.getSoLuong());
			pstmt.setString(4,d.getTenThuoc());
			pstmt.setDouble(5, d.getGiaNhap());
			
			n = pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(con!=null) {
				try {
					pstmt.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return n > 0;
	}
	
	public boolean xoaChiTietDonDatBangMaThuoc(String d) throws SQLException {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement pstmt = null;
		int n = 0;
		try {
			pstmt = con.prepareStatement("delete from ChiTietDonNhap"
					+ " Where MaThuoc=?");
			pstmt.setString(1, d);
			
			
			n = pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(con!=null) {
				try {
					pstmt.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return n > 0;
	}
}
