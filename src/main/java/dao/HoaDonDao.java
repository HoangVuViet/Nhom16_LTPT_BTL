package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import connect.ConnectDB;
import entity.HoaDon;
import entity.KhachHang;

public class HoaDonDao {
	public HoaDonDao() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean taoHoaDon(HoaDon hd) throws SQLException {
		ConnectDB.getInstance();
		Connection con=ConnectDB.getConnection();
		PreparedStatement stmt=null;
		int n=0;
		try {
			stmt=con.prepareStatement("insert into HoaDon "
					+ "(MaKH,MaNV,ThoiGianTao,TongTien,TienNhan,PhuongThucTT)"
					+ "  values( ?, ?, ?, ?, ?,?)");
			stmt.setString(1,hd.getMaKH());
			stmt.setString(2, hd.getMaNV());
			Timestamp time=Timestamp.valueOf(hd.getThoiGianXuatHD());
			stmt.setTimestamp(3,time);
			stmt.setDouble(4,hd.getTongTien());
			stmt.setDouble(5,hd.getTienNhan());
			stmt.setString(6,hd.getPhuongThucTT());
			
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
		return
				n>0;
	}
	
	public ArrayList<HoaDon> layTatCaHoaDonGiamDan(){
		ArrayList<HoaDon> lstHD=new ArrayList<HoaDon>();
		Connection con = ConnectDB.getConnection();
		Statement statement = null;
		try {
			ConnectDB.getInstance().connectDataBase();;
			con=ConnectDB.getConnection();
			String sql="Select * from HoaDon ORDER BY ThoiGianTao DESC";
			statement=con.createStatement();
			ResultSet rs= statement.executeQuery(sql);
			
			while(rs.next()) {
				String maHD=rs.getString("MaHD");
				String maKH=rs.getString("MaKH");
				String maNV=rs.getString("MaNV");
				Timestamp time=rs.getTimestamp("ThoiGianTao");
				LocalDateTime thoiGianTao=time.toLocalDateTime();
				double tongTien=rs.getDouble("TongTien");
				double tienNhan=rs.getDouble("TienNhan");
//				boolean tinhTrang=rs.getBoolean("TinhTrang")
				String phuongThucTT=rs.getString("PhuongThucTT");
//				lstKH.add(kh);
				HoaDon hd=new HoaDon(maHD, maKH, maNV, thoiGianTao, tongTien, tienNhan, phuongThucTT);
				lstHD.add(hd);
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
	

	public ArrayList<HoaDon> timHoaDonBangSoDTKHThoiGianMaNV(String sodt,Date date,String manv){
		ArrayList<HoaDon> lstHD=new ArrayList<HoaDon>();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			ConnectDB.getInstance().connectDataBase();;
			con=ConnectDB.getConnection();
			String sql="SELECT HoaDon.MaHD, HoaDon.MaKH,HoaDon.MaNV,HoaDon.ThoiGianTao,HoaDon.TongTien,HoaDon.TienNhan,HoaDon.PhuongThucTT"
					+ " FROM HoaDon"
					+ " JOIN KhachHang ON HoaDon.MaKH = KhachHang.MaKH"
					+ " WHERE KhachHang.SoDT LIKE ? AND HoaDon.ThoiGianTao >= ? AND HoaDon.ThoiGianTao <= ? AND HoaDon.MaNV like ? ";
			statement=con.prepareStatement(sql);
			statement.setString(1, "%"+sodt+"%");
			statement.setTimestamp(2, new Timestamp(date.getTime()));
			statement.setTimestamp(3, new Timestamp(date.getTime() + 24 * 60 * 60 * 1000)); // Ngày tiếp theo
			statement.setString(4, "%"+manv+"%");
			ResultSet rs= statement.executeQuery();
			
			while(rs.next()) {
				String maHD=rs.getString("MaHD");
				String maKH=rs.getString("MaKH");
				String maNV=rs.getString("MaNV");
				Timestamp time=rs.getTimestamp("ThoiGianTao");
				LocalDateTime thoiGianTao=time.toLocalDateTime();
				double tongTien=rs.getDouble("TongTien");
				double tienNhan=rs.getDouble("TienNhan");
//				boolean tinhTrang=rs.getBoolean("TinhTrang")
				String phuongThucTT=rs.getString("PhuongThucTT");
//				lstKH.add(kh);
				HoaDon hd=new HoaDon(maHD, maKH, maNV, thoiGianTao, tongTien, tienNhan, phuongThucTT);
				lstHD.add(hd);
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
	
	public ArrayList<HoaDon> timHoaDonBangSoDTMaNV(String sodt,String manv){
		ArrayList<HoaDon> lstHD=new ArrayList<HoaDon>();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			ConnectDB.getInstance().connectDataBase();;
			con=ConnectDB.getConnection();
			String sql="SELECT HoaDon.MaHD, HoaDon.MaKH,HoaDon.MaNV,HoaDon.ThoiGianTao,HoaDon.TongTien,HoaDon.TienNhan,HoaDon.PhuongThucTT"
					+ "FROM HoaDon"
					+ "JOIN KhachHang ON HoaDon.MaKH = KhachHang.MaKH"
					+ "WHERE KhachHang.SoDT LIKE ? And HoaDon.MaNV LIKE ? ";
			statement=con.prepareStatement(sql);
			statement.setString(1, "%"+sodt+"%");
			statement.setString(2, "%"+manv+"%");
			ResultSet rs= statement.executeQuery();
			
			while(rs.next()) {
				String maHD=rs.getString("MaHD");
				String maKH=rs.getString("MaKH");
				String maNV=rs.getString("MaNV");
				Timestamp time=rs.getTimestamp("ThoiGianTao");
				LocalDateTime thoiGianTao=time.toLocalDateTime();
				double tongTien=rs.getDouble("TongTien");
				double tienNhan=rs.getDouble("TienNhan");
//				boolean tinhTrang=rs.getBoolean("TinhTrang")
				String phuongThucTT=rs.getString("PhuongThucTT");
//				lstKH.add(kh);
				HoaDon hd=new HoaDon(maHD, maKH, maNV, thoiGianTao, tongTien, tienNhan, phuongThucTT);
				lstHD.add(hd);
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
	

	public ArrayList<HoaDon> timHoaDonBangTenKHThoiGianMaNV(String tenkh,Date date,String manv){
		ArrayList<HoaDon> lstHD=new ArrayList<HoaDon>();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			ConnectDB.getInstance().connectDataBase();;
			con=ConnectDB.getConnection();
			String sql = "SELECT HoaDon.MaHD, HoaDon.MaKH, HoaDon.MaNV, HoaDon.ThoiGianTao, HoaDon.TongTien, HoaDon.TienNhan, HoaDon.PhuongThucTT " +
		             "FROM HoaDon " +
		             "JOIN KhachHang ON HoaDon.MaKH = KhachHang.MaKH " +
		             "WHERE KhachHang.TenKH COLLATE Latin1_General_CI_AI LIKE ? AND HoaDon.ThoiGianTao >= ? AND HoaDon.ThoiGianTao <= ? AND HoaDon.MaNV like ?";
			statement=con.prepareStatement(sql);
			statement.setString(1, "%" + tenkh + "%");
			statement.setTimestamp(2, new Timestamp(date.getTime()));
			statement.setTimestamp(3, new Timestamp(date.getTime() + 24 * 60 * 60 * 1000)); // Ngày tiếp theo
			statement.setString(4,"%" +manv+"%");
			ResultSet rs= statement.executeQuery();
			
			while(rs.next()) {
				String maHD=rs.getString("MaHD");
				String maKH=rs.getString("MaKH");
				String maNV=rs.getString("MaNV");
				Timestamp time=rs.getTimestamp("ThoiGianTao");
				LocalDateTime thoiGianTao=time.toLocalDateTime();
				double tongTien=rs.getDouble("TongTien");
				double tienNhan=rs.getDouble("TienNhan");
//				boolean tinhTrang=rs.getBoolean("TinhTrang")
				String phuongThucTT=rs.getString("PhuongThucTT");
//				lstKH.add(kh);
				HoaDon hd=new HoaDon(maHD, maKH, maNV, thoiGianTao, tongTien, tienNhan, phuongThucTT);
				lstHD.add(hd);
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
	
	public ArrayList<HoaDon> timHoaDonBangMaKH(String maKH){
		ArrayList<HoaDon> lstHD=new ArrayList<HoaDon>();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			ConnectDB.getInstance().connectDataBase();;
			con=ConnectDB.getConnection();
			String sql = "SELECT * FROM HoaDon WHERE MaKH = ?";
			statement=con.prepareStatement(sql);
			statement.setString(1, maKH );
			ResultSet rs= statement.executeQuery();
			
			while(rs.next()) {
				String maHD=rs.getString("MaHD");
				String maKH1=rs.getString("MaKH");
				String maNV=rs.getString("MaNV");
				Timestamp time=rs.getTimestamp("ThoiGianTao");
				LocalDateTime thoiGianTao=time.toLocalDateTime();
				double tongTien=rs.getDouble("TongTien");
				double tienNhan=rs.getDouble("TienNhan");
//				boolean tinhTrang=rs.getBoolean("TinhTrang")
				String phuongThucTT=rs.getString("PhuongThucTT");
//				lstKH.add(kh);
				HoaDon hd=new HoaDon(maHD, maKH1, maNV, thoiGianTao, tongTien, tienNhan, phuongThucTT);
				lstHD.add(hd);
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
	
	public ArrayList<HoaDon> timHoaDonBangTenMaNV(String tenkh,String manv){
		ArrayList<HoaDon> lstHD=new ArrayList<HoaDon>();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			ConnectDB.getInstance().connectDataBase();;
			con=ConnectDB.getConnection();
			String sql = "SELECT HoaDon.MaHD, HoaDon.MaKH, HoaDon.MaNV, HoaDon.ThoiGianTao, HoaDon.TongTien, HoaDon.TienNhan, HoaDon.PhuongThucTT " +
		             "FROM HoaDon " +
		             "JOIN KhachHang ON HoaDon.MaKH = KhachHang.MaKH " +
		             "WHERE KhachHang.TenKH COLLATE Latin1_General_CI_AI LIKE ? AND HoaDon.MaNV like ?";
			statement=con.prepareStatement(sql);
			statement.setString(1, "%" + tenkh + "%");
			statement.setString(2,"%" +manv+"%");
			ResultSet rs= statement.executeQuery();
			
			while(rs.next()) {
				String maHD=rs.getString("MaHD");
				String maKH=rs.getString("MaKH");
				String maNV=rs.getString("MaNV");
				Timestamp time=rs.getTimestamp("ThoiGianTao");
				LocalDateTime thoiGianTao=time.toLocalDateTime();
				double tongTien=rs.getDouble("TongTien");
				double tienNhan=rs.getDouble("TienNhan");
//				boolean tinhTrang=rs.getBoolean("TinhTrang")
				String phuongThucTT=rs.getString("PhuongThucTT");
//				lstKH.add(kh);
				HoaDon hd=new HoaDon(maHD, maKH, maNV, thoiGianTao, tongTien, tienNhan, phuongThucTT);
				lstHD.add(hd);
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
	
	public ArrayList<HoaDon> timHoaDonBangMaHDThoiGianMaNV(String mahd,Date date,String manv){
		ArrayList<HoaDon> lstHD=new ArrayList<HoaDon>();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			ConnectDB.getInstance().connectDataBase();;
			con=ConnectDB.getConnection();
			String sql = "SELECT * FROM HoaDon WHERE MaHD LIKE ? AND ThoiGianTao >= ? AND ThoiGianTao <= ? AND MaNV like ?";
			statement = con.prepareStatement(sql);
			statement.setString(1,"%"+ mahd+"%");
			statement.setTimestamp(2, new Timestamp(date.getTime()));
			statement.setTimestamp(3, new Timestamp(date.getTime() + 24 * 60 * 60 * 1000)); // Ngày tiếp theo
			statement.setString(4,"%"+manv+"%");
			ResultSet rs= statement.executeQuery();
			
			while(rs.next()) {
				String maHD=rs.getString("MaHD");
				String maKH=rs.getString("MaKH");
				String maNV=rs.getString("MaNV");
				Timestamp time=rs.getTimestamp("ThoiGianTao");
				LocalDateTime thoiGianTao=time.toLocalDateTime();
				double tongTien=rs.getDouble("TongTien");
				double tienNhan=rs.getDouble("TienNhan");
//				boolean tinhTrang=rs.getBoolean("TinhTrang")
				String phuongThucTT=rs.getString("PhuongThucTT");
//				lstKH.add(kh);
				HoaDon hd=new HoaDon(maHD, maKH, maNV, thoiGianTao, tongTien, tienNhan, phuongThucTT);
				lstHD.add(hd);
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
	
	public ArrayList<HoaDon> timHoaDonBangMaHDMaNV(String mahd,String manv){
		ArrayList<HoaDon> lstHD=new ArrayList<HoaDon>();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			ConnectDB.getInstance().connectDataBase();;
			con=ConnectDB.getConnection();
			String sql = "SELECT * FROM HoaDon WHERE MaHD LIKE ? AND MaNV like ?";
			statement = con.prepareStatement(sql);
			statement.setString(1,"%"+ mahd+"%");
			statement.setString(2,"%"+manv+"%");
			ResultSet rs= statement.executeQuery();
			
			while(rs.next()) {
				String maHD=rs.getString("MaHD");
				String maKH=rs.getString("MaKH");
				String maNV=rs.getString("MaNV");
				Timestamp time=rs.getTimestamp("ThoiGianTao");
				LocalDateTime thoiGianTao=time.toLocalDateTime();
				double tongTien=rs.getDouble("TongTien");
				double tienNhan=rs.getDouble("TienNhan");
//				boolean tinhTrang=rs.getBoolean("TinhTrang")
				String phuongThucTT=rs.getString("PhuongThucTT");
//				lstKH.add(kh);
				HoaDon hd=new HoaDon(maHD, maKH, maNV, thoiGianTao, tongTien, tienNhan, phuongThucTT);
				lstHD.add(hd);
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
	
	public HoaDon timHoaDonBangMaHD(String mahd){
		HoaDon hd=null;
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			ConnectDB.getInstance().connectDataBase();;
			con=ConnectDB.getConnection();
			String sql = "SELECT * FROM HoaDon WHERE MaHD = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1,mahd);
			
			ResultSet rs= statement.executeQuery();
			
			while(rs.next()) {
				String maHD=rs.getString("MaHD");
				String maKH=rs.getString("MaKH");
				String maNV=rs.getString("MaNV");
				Timestamp time=rs.getTimestamp("ThoiGianTao");
				LocalDateTime thoiGianTao=time.toLocalDateTime();
				double tongTien=rs.getDouble("TongTien");
				double tienNhan=rs.getDouble("TienNhan");
//				boolean tinhTrang=rs.getBoolean("TinhTrang")
				String phuongThucTT=rs.getString("PhuongThucTT");
//				lstKH.add(kh);
				hd=new HoaDon(maHD, maKH, maNV, thoiGianTao, tongTien, tienNhan, phuongThucTT);
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
		return hd;
	}
	
	public boolean xoaHoaDonVaCTHDTheoMaHD(String ma) throws SQLException {
		ConnectDB.getInstance();
		Connection con=ConnectDB.getConnection();
		PreparedStatement stmt=null;
		ChiTietHoaDonDao cthdDao=new ChiTietHoaDonDao();
		int n=0;
		try {
			cthdDao.xoaChiTietHDTheoMaHD(ma);
			stmt=con.prepareStatement("delete from HoaDon where MaHD=?");
			
			stmt.setString(1,ma);
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
		
	public String layMaHDMoiTao() throws SQLException {
//		KhachHang kh=null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		Statement stmt = null;
		String maHD = null;
		try {
			String sql = "SELECT Top 1 * FROM HoaDon ORDER BY ThoiGianTao DESC";
			stmt = con.createStatement();
			
			// ketqua
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				maHD=rs.getString("MaHD");
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(con!=null) {
				try {
					stmt.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
		}
		return maHD;
	}
	
	
}
