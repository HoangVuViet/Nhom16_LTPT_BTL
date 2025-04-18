package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

import connect.ConnectDB;
import entity.DonViTinh;
import entity.LoaiThuoc;

public class LoaiThuocDao {
	public LoaiThuocDao() {
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<LoaiThuoc> timTatCaLoaiThuoc() throws SQLException{
		ArrayList<LoaiThuoc> lstL = new ArrayList<LoaiThuoc>();
		Connection con = ConnectDB.getInstance().getConnection();
		Statement stmt=null;
		try {
			
			con = ConnectDB.getConnection();
			String sql="Select * from LoaiThuoc";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int ma = rs.getInt("Id");
				String ten = rs.getString("LoaiThuoc");
				
				LoaiThuoc l = new LoaiThuoc(ma,ten);
				
				lstL.add(l);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(con!=null) {
				try {
					stmt.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
		}
		return lstL;
	}
	public LoaiThuoc timLoaiThuocTheoTen(String ten) throws SQLException{
		LoaiThuoc l = new LoaiThuoc();
		
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "select * "
					+ "from LoaiThuoc l "
					+ "where l.loaiThuoc = ?";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, ten);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String tenDonvi = rs.getString("LoaiThuoc");
				int ma = rs.getInt("Id");
				
				l.setId(ma); l.setLoaiThuoc(tenDonvi);
				
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(con!=null) {
				try {
					stmt.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
		}
		return l;
	}
	
	public ArrayList<String> layDanhSachLoaiThuocKhongTrung(ArrayList<String> danhSachLoaiThuoc) {
        HashSet<String> setLoaiThuocKhongTrung = new HashSet<String>(danhSachLoaiThuoc);
        return new ArrayList<>(setLoaiThuocKhongTrung);
    }
	
	public LoaiThuoc timLoaiThuocTheoMa(int maLoai) throws SQLException{
		LoaiThuoc l = new LoaiThuoc();
		
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "select * "
					+ "from LoaiThuoc l where l.Id = ?";
			
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, maLoai);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String tenLoai = rs.getString("LoaiThuoc");
				int ma = rs.getInt("Id");
				
				l.setId(ma); l.setLoaiThuoc(tenLoai);
				
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(con!=null) {
				try {
					stmt.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
		}
		return l;
	}
	public boolean taoLoaiThuoc(LoaiThuoc l) throws SQLException {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement pstmt = null;
		int n = 0;
		try {
			pstmt = con.prepareStatement("insert into LoaiThuoc(LoaiThuoc) "
					+ "values(?)");
			pstmt.setString(1, l.getLoaiThuoc());
			
			n = pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(con!=null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
		}
		return n>0;
	}
	public boolean xoaLoaiThuoc(int ma) throws SQLException {
		ConnectDB.getInstance();
		Connection con=ConnectDB.getConnection();
		PreparedStatement stmt=null;
		int n=0;
		try {
			stmt=con.prepareStatement("delete from LoaiThuoc where Id = ?");
			
			stmt.setInt(1, ma);
			
			n=stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(con!=null) {
				try {
					stmt.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
		}
		return n>0;
	}
}
