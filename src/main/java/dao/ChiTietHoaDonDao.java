package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import connect.ConnectDB;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;

public class ChiTietHoaDonDao {
	public ChiTietHoaDonDao() {
		// TODO Auto-generated constructor stub
	}
	public boolean taoChiTietHoaDon(ChiTietHoaDon cthd) throws SQLException {
		ConnectDB.getInstance();
		Connection con=ConnectDB.getConnection();
		PreparedStatement stmt=null;
		int n=0;
		try {
			stmt=con.prepareStatement("insert into ChiTietHD "
					+ "(MaHD,MaThuoc,SoLuong,ThanhTien)"
					+ "  values( ?, ?, ?, ?)");
			stmt.setString(1,cthd.getMaHD());
			stmt.setString(2, cthd.getMaSP());
			
			stmt.setInt(3,cthd.getSoLuong());
			stmt.setDouble(4,cthd.getThanhTienSP());
			
			n=stmt.executeUpdate();
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
		return n>0;
	}
	
	public ArrayList<ChiTietHoaDon> layTatCaChiTietHoaDonTheoMaHD(String ma){
		ArrayList<ChiTietHoaDon> lstHD=new ArrayList<ChiTietHoaDon>();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			ConnectDB.getInstance().connectDataBase();;
			con=ConnectDB.getConnection();
			String sql="Select * from ChiTietHD WHERE MaHD=?";
			statement=con.prepareStatement(sql);
			statement.setString(1, ma);
			ResultSet rs= statement.executeQuery();
			
			while(rs.next()) {
				String maHD=rs.getString("MaHD");
				String maThuoc=rs.getString("MaThuoc");
				int soLuong=rs.getInt("SoLuong");
				double tien=rs.getDouble("ThanhTien");
				ChiTietHoaDon cthd=new ChiTietHoaDon(maHD, maThuoc, soLuong, tien);
				lstHD.add(cthd);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(con!=null) {
				try {
					statement.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return lstHD;
	}
	
	public boolean xoaChiTietHDTheoMaHD(String ma) throws SQLException {
		ConnectDB.getInstance();
		Connection con=ConnectDB.getConnection();
		PreparedStatement stmt=null;
		int n=0;
		try {
			stmt=con.prepareStatement("delete from ChiTietHD where MaHD=?");
			
			stmt.setString(1,ma);
			n=n+stmt.executeUpdate();
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
		System.out.println("Đã xoá "+n+"chi tiết hoá đơn của "+ma);
		return n>0;
	}
}
