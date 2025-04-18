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
import entity.DonDatThuoc;
import entity.Thuoc;

public class DonDatThuocDao {
	public DonDatThuocDao() {
	}
	//MaDonNhap MaNV MaNCC NgayNhap
	public ArrayList<DonDatThuoc> timTatCaDonDat(){
		ArrayList<DonDatThuoc> lstDon = new ArrayList<DonDatThuoc>();
		Connection con = null;
		Statement stmt=null;
		try {
			ConnectDB.getInstance().connectDataBase();
			con = ConnectDB.getConnection();
			String sql="Select * from DonNhapThuoc";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String maDon = rs.getString("MaDonNhap");
				String maNV = rs.getString("MaNV");
				String maNCC = rs.getString("MaNCC");
				LocalDateTime ngayHT = rs.getTimestamp("NgayNhap").toLocalDateTime();
				DonDatThuoc d = new DonDatThuoc(maDon, maNV, maNCC, ngayHT);
				lstDon.add(d);
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
		return lstDon;
	}
	
	public DonDatThuoc layDonDatBangMa(String ma){
		DonDatThuoc d=null;
		Connection con = null;
		PreparedStatement stmt=null;
		try {
			ConnectDB.getInstance().connectDataBase();
			con = ConnectDB.getConnection();
			String sql="Select * from DonNhapThuoc Where MaDonNhap like ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, "%" +ma + "%");
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				String maDon = rs.getString("MaDonNhap");
				String maNV = rs.getString("MaNV");
				String maNCC = rs.getString("MaNCC");
				LocalDateTime ngayHT = rs.getTimestamp("ngayNhap").toLocalDateTime();
				d = new DonDatThuoc(maDon, maNV, maNCC, ngayHT);
				
				
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
		return d;
	}
	
	public boolean taoDonNhapThuoc(DonDatThuoc donDat) throws SQLException {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement pstmt = null;
		int n = 0;
		try {
			pstmt = con.prepareStatement("insert into DonNhapThuoc "
					+ "(MaNV, MaNCC, NgayNhap) " 
					+ "values (?,?,?)");
			 pstmt.setString(1, donDat.getMaNV());
			 pstmt.setString(2, donDat.getNcc());
			 Timestamp timestamp = Timestamp.valueOf(donDat.getNgayNhap());
			 pstmt.setTimestamp(3, timestamp);
		
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
	public String layMaDonNhapMoiTao() throws SQLException {
//		KhachHang kh=null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		Statement stmt = null;
		String maDN = null;
		try {
			String sql = "SELECT Top 1 * FROM DonNhapThuoc ORDER BY ngayNhap DESC";
			stmt = con.createStatement();
			
			// ketqua
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				maDN=rs.getString("MaDonNhap");
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
		return maDN;
	}
	public ArrayList<DonDatThuoc> layDonDatMoiNhat(){
		ArrayList<DonDatThuoc> lstDonDatThuocs = new ArrayList<>();
		Connection con = null;
		PreparedStatement stmt=null;
		try {
			ConnectDB.getInstance().connectDataBase();
			con = ConnectDB.getConnection();
			String sql="Select * from DonNhapThuoc order by ngayNhap desc";
			stmt = con.prepareStatement(sql);
	
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				String maDon = rs.getString("MaDonNhap");
				String maNV = rs.getString("MaNV");
				String maNCC = rs.getString("MaNCC");
				LocalDateTime ngayHT = rs.getTimestamp("NgayNhap").toLocalDateTime();
				DonDatThuoc d = new DonDatThuoc(maDon, maNV, maNCC, ngayHT);
				lstDonDatThuocs.add(d);
				
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
		return lstDonDatThuocs;
	}
	public ArrayList<DonDatThuoc> layDonDatTheoSDT(String phone){
		ArrayList<DonDatThuoc> lstDonDatThuocs = new ArrayList<>();
		Connection con = null;
		PreparedStatement stmt=null;
		try {
			ConnectDB.getInstance().connectDataBase();
			con = ConnectDB.getConnection();
			String sql="select NhanVien.SoDT, DonNhapThuoc.* "
					+ "from DonNhapThuoc "
					+ "join NhanVien "
					+ "on NhanVien.MaNV = DonNhapThuoc.MaNV "
					+ "where NhanVien.SoDT like ? ";
			stmt = con.prepareStatement(sql);
	
			ResultSet rs = stmt.executeQuery();
			stmt.setString(1, "%" + phone + "%");
			
			while(rs.next()) {
				String maDon = rs.getString("MaDonNhap");
				String maNV = rs.getString("MaNV");
				String maNCC = rs.getString("MaNCC");
				LocalDateTime ngayHT = rs.getTimestamp("NgayNhap").toLocalDateTime();
				DonDatThuoc d = new DonDatThuoc(maDon, maNV, maNCC, ngayHT);
				lstDonDatThuocs.add(d);
				
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
		return lstDonDatThuocs;
	}
	public ArrayList<DonDatThuoc> layDonDatTenNCC(String ten){
		ArrayList<DonDatThuoc> lstDonDatThuocs = new ArrayList<>();
		Connection con = null;
		PreparedStatement stmt=null;
		try {
			ConnectDB.getInstance().connectDataBase();
			con = ConnectDB.getConnection();
			String sql="select NhaCungCap.TenNCC, DonNhapThuoc.* "
					+ "from DonNhapThuoc "
					+ "inner join NhaCungCap "
					+ "on NhaCungCap.MaNCC = DonNhapThuoc.MaNCC "
					+ "where NhaCungCap.TenNCC like ? ";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, "%" + ten  + "%");
	
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				String maDon = rs.getString("MaDonNhap");
				String maNV = rs.getString("MaNV");
				String maNCC = rs.getString("MaNCC");
				LocalDateTime ngayHT = rs.getTimestamp("NgayNhap").toLocalDateTime();
				DonDatThuoc d = new DonDatThuoc(maDon, maNV, maNCC, ngayHT);
				lstDonDatThuocs.add(d);
				
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
		return lstDonDatThuocs;
	}

	public ArrayList<DonDatThuoc> layDonDatTheoMaNCC(String maNCC){
		ArrayList<DonDatThuoc> lstDonDatThuocs = new ArrayList<>();
		Connection con = null;
		PreparedStatement stmt=null;
		try {
			ConnectDB.getInstance().connectDataBase();
			con = ConnectDB.getConnection();
			String sql="select * from DonNhapThuoc Where MaNCC = ? ";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, maNCC);
	
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				String maDon = rs.getString("MaDonNhap");
				String maNV = rs.getString("MaNV");
				String maNCC1 = rs.getString("MaNCC");
				LocalDateTime ngayHT = rs.getTimestamp("NgayNhap").toLocalDateTime();
				DonDatThuoc d = new DonDatThuoc(maDon, maNV, maNCC1, ngayHT);
				lstDonDatThuocs.add(d);
				
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
		return lstDonDatThuocs;
	}

	
}
