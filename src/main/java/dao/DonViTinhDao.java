package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connect.ConnectDB;
import entity.DonViTinh;
import entity.LoaiThuoc;
import entity.Thuoc;

public class DonViTinhDao {
	public DonViTinhDao() {
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<DonViTinh> timTatCaDonViThuoc() throws SQLException{
		ArrayList<DonViTinh> lstDonvi = new ArrayList<DonViTinh>();
		Connection con = ConnectDB.getInstance().getConnection();
		Statement stmt=null;
		try {
			
			con = ConnectDB.getConnection();
			String sql="Select * from DonVi";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				
				int id = rs.getInt("maDonVi");
				String ten = rs.getString("TenDonVi");
				DonViTinh d = new DonViTinh(id, ten);
				
				lstDonvi.add(d);
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
		return lstDonvi;
	}
	
	public DonViTinh timDonViTheoTen(String ten) throws SQLException{
		DonViTinh d = new DonViTinh();
		
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "select * "
					+ "from DonVi d "
					+ "where d.tenDonVi = ?";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, ten);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String tenDonvi = rs.getString("tenDonVi");
				int ma = rs.getInt("maDonVi");
				d.setMaDonVi(ma); d.setTenDonVi(tenDonvi);
				
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
		return d;
	}
	public DonViTinh timDonViTheoMa(int maDonvi) throws SQLException{
		DonViTinh d = new DonViTinh();
		
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "select * "
					+ "from DonVi d where d.maDonVi = ?";
			
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, maDonvi);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String tenDonvi = rs.getString("tenDonVi");
				int ma = rs.getInt("maDonVi");
				
				d.setMaDonVi(ma); d.setTenDonVi(tenDonvi);
				
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
		return d;
	}
	public boolean taoDonvi(DonViTinh d) throws SQLException {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement pstmt = null;
		int n = 0;
		try {
			pstmt = con.prepareStatement("insert into DonVi(tenDonVi) "
					+ "values(?)");
			pstmt.setString(1, d.getTenDonVi());
			
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
	public boolean xoaDonvi(int ma) throws SQLException {
		ConnectDB.getInstance();
		Connection con=ConnectDB.getConnection();
		PreparedStatement stmt=null;
		int n=0;
		try {
			stmt=con.prepareStatement("delete from Donvi where maDonVi = ?");
			
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
