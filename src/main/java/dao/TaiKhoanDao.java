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

import javax.swing.JOptionPane;

import connect.ConnectDB;
import entity.KhachHang;
import entity.NhanVien;
import entity.TaiKhoan;

public class TaiKhoanDao {
	public TaiKhoanDao() {
		
	}
	//đăng ký tài khoản
	public boolean taoTaiKhoan(TaiKhoan tk) throws SQLException {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement pstmt = null;
		int n = 0;
		try {
			pstmt = con.prepareStatement("insert into TaiKhoan(TaiKhoan, MatKhau, MaNV)"
					+ "values (?,?,?) ");
			pstmt.setString(1, tk.getTenDangNhap());
			pstmt.setString(2, tk.getMatkhau());
			
			pstmt.setString(3, tk.getMaNV());
			//pstmt.setString(4, tk.getTrangThai());
			n=pstmt.executeUpdate();
					
		}
		catch (SQLException e) {
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
	
	public boolean doiMatKhau(TaiKhoan tk,String mk) throws SQLException {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement pstmt = null;
		int n = 0;
		try {
			pstmt = con.prepareStatement("UPDATE TaiKhoan SET MatKhau = ? WHERE TaiKhoan = ?");
			pstmt.setString(1, mk);
			pstmt.setString(2, tk.getTenDangNhap());
			//pstmt.setString(4, tk.getTrangThai());
			n=pstmt.executeUpdate();
			System.out.println("Đổi Mật Khẩu Thành Công!");
			JOptionPane.showMessageDialog(null,"Đổi Mật Khẩu Thành Công!");
		}
		catch (SQLException e) {
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
	
	public boolean xoaTaiKhoan(String maNV) throws SQLException {
		
		ConnectDB.getInstance();
		Connection con=ConnectDB.getConnection();
		PreparedStatement stmt=null;
		int n=0;
		try {
			stmt=con.prepareStatement("delete from TaiKhoan where MaNV=?");
			
			stmt.setString(1, maNV);
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
	
	//đăng nhập
	public TaiKhoan dangNhap(String tenDangnhap, String matkhau) throws SQLException {
		TaiKhoan tk = null;
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement pstmt=null;
		try {
			String sql = "select *"
					+ "from TaiKhoan where TaiKhoan = ? AND MatKhau = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tenDangnhap);
			pstmt.setString(2, matkhau);

			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String tkhoan = rs.getString("TaiKhoan");
				String mkhau = rs.getString("MatKhau");
				String maNVien = rs.getString("MaNV");
				
				tk = new TaiKhoan(maNVien, tkhoan, mkhau, null);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(con!=null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
		}
//		rs.close();
	
		return tk;
	}
	
	public ArrayList<TaiKhoan> layTkBangMaNV(String maNV) throws SQLException {
		TaiKhoan tk = null;
		ArrayList<TaiKhoan> list=new ArrayList<TaiKhoan>();
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement pstmt=null;
		try {
			String sql = "select *"
					+ "from TaiKhoan where MaNV = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, maNV);


			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String tkhoan = rs.getString("TaiKhoan");
				String mkhau = rs.getString("MatKhau");
				String maNVien = rs.getString("MaNV");
				
				tk = new TaiKhoan(maNVien, tkhoan, mkhau, null);
				list.add(tk);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(con!=null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
		}
//		rs.close();
	
		return list;
	}
	
	public boolean luuLogDangNhap(String maNVDangNhap) throws SQLException {
	    Connection con =  con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    boolean success = false;

	    try {
	       
	        LocalDateTime localDateTime = LocalDateTime.now();
	        Timestamp sqlTimestamp = Timestamp.valueOf(localDateTime);

	        stmt = con.prepareStatement("INSERT INTO LogDangNhap (MaNV, ThoiGianDN) VALUES (?, ?)");
	        stmt.setString(1, maNVDangNhap);
	        stmt.setTimestamp(2, sqlTimestamp);

	        int rowsAffected = stmt.executeUpdate();
	        success = (rowsAffected > 0);
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

	    return success;
	}
	
	public String layMaNVMoiNhatDangNhap() throws SQLException {
//		KhachHang kh=null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		Statement stmt = null;
		String maNV = null;
		try {
			String sql = "Select Top 1 * FROM LogDangNhap ORDER BY ThoiGianDN DESC";
			stmt = con.createStatement();
			
			// ketqua
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				maNV=rs.getString("MaNV");
				
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
		return maNV;

	}
}
