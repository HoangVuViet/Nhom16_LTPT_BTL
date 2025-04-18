package dao;

import java.sql.Statement;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import connect.ConnectDB;
import entity.KhachHang;
import entity.KhachHang;



public class KhachHangDao {
//	private Connection con=null;
	public KhachHangDao() {
	
	}

	public ArrayList<KhachHang> layTatCaKhangHang(){
		ArrayList<KhachHang> lstKH=new ArrayList<KhachHang>();
		Connection con = ConnectDB.getConnection();
		Statement statement = null;
		try {
			ConnectDB.getInstance().connectDataBase();;
			con=ConnectDB.getConnection();
			String sql="Select * from KhachHang";
			statement=con.createStatement();
			ResultSet rs= statement.executeQuery(sql);
			
			while(rs.next()) {
				String maKH=rs.getString("MaKH");
				String tenKh=rs.getString("tenKH");
				boolean gioiTinh=rs.getBoolean("GioiTinh");
				String sdt=rs.getString("SoDT");
				String diachi = rs.getString("DiaChi");
				Date nSinh = rs.getDate("NgaySinh");
				LocalDate ngaysinh=nSinh.toLocalDate();
				String mail = rs.getString("Email");
				KhachHang kh=new KhachHang(maKH, ngaysinh, tenKh, gioiTinh, mail, sdt, diachi);
				lstKH.add(kh);
				
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
		return lstKH;
	}
	
	public ArrayList<KhachHang> layKhachHangTheoMa(String id) throws SQLException {
		ArrayList<KhachHang> lstKH = new ArrayList<KhachHang>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "SELECT * FROM KhachHang WHERE MaKH LIKE ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, "%"+id+"%");
			// ketqua
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maKH=rs.getString("MaKH");
				String tenKh=rs.getString("TenKH");
				boolean gioiTinh=rs.getBoolean("GioiTinh");
				String sdt=rs.getString("SoDT");
				String diachi = rs.getString("DiaChi");
				Date nSinh = rs.getDate("NgaySinh");
				LocalDate ngaysinh=nSinh.toLocalDate();
				String mail = rs.getString("Email");
				KhachHang kh=new KhachHang(maKH, ngaysinh, tenKh, gioiTinh, mail, sdt, diachi);
				lstKH.add(kh);
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
		return lstKH;

	}
	
	public ArrayList<KhachHang> layKhachHangTheoTenSDT(String ten,String sdtTim) throws SQLException {
		ArrayList<KhachHang> lstKH = new ArrayList<KhachHang>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "SELECT * FROM KhachHang WHERE TenKH COLLATE Latin1_General_CI_AI LIKE ? AND SoDT LIKE ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, "%"+ten+"%");
			stmt.setString(2,"%" +sdtTim+"%");
			// ketqua
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maKH=rs.getString("MaKH");
				String tenKh=rs.getString("TenKH");
				boolean gioiTinh=rs.getBoolean("GioiTinh");
				String sdt=rs.getString("SoDT");
				String diachi = rs.getString("DiaChi");
				Date nSinh = rs.getDate("NgaySinh");
				LocalDate ngaysinh=nSinh.toLocalDate();
				String mail = rs.getString("Email");
				KhachHang kh=new KhachHang(maKH, ngaysinh, tenKh, gioiTinh, mail, sdt, diachi);
				lstKH.add(kh);
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
		return lstKH;

	}
	
	public ArrayList<KhachHang> layKhachHangTheoTen(String name) throws SQLException {
	    ArrayList<KhachHang> lstKH = new ArrayList<KhachHang>();
	    ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    try {
	        String sql = "SELECT * FROM KhachHang WHERE TenKH COLLATE Latin1_General_CI_AI LIKE ?";
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, "%" + name + "%"); // Sử dụng LIKE để tìm tên chứa biến name
	        // Kết quả
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            String maKH = rs.getString("MaKH");
	            String tenKh = rs.getString("TenKH");
	            boolean gioiTinh = rs.getBoolean("GioiTinh");
	            String sdt = rs.getString("SoDT");
	            String diachi = rs.getString("DiaChi");
	            Date nSinh = rs.getDate("NgaySinh");
	            LocalDate ngaysinh = nSinh.toLocalDate();
	            String mail = rs.getString("Email");
	            KhachHang kh = new KhachHang(maKH, ngaysinh, tenKh, gioiTinh, mail, sdt, diachi);
	            lstKH.add(kh);
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
	    return lstKH;
	}
	
	public KhachHang layKhachHangTheoMaKH(String id) throws SQLException {
		KhachHang kh=null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "SELECT * FROM KhachHang WHERE MaKH = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			// ketqua
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maKH=rs.getString("MaKH");
				String tenKh=rs.getString("TenKH");
				boolean gioiTinh=rs.getBoolean("GioiTinh");
				String sdt=rs.getString("SoDT");
				String diachi = rs.getString("DiaChi");
				Date nSinh = rs.getDate("NgaySinh");
				LocalDate ngaysinh=nSinh.toLocalDate();
				String mail = rs.getString("Email");
				kh=new KhachHang(maKH, ngaysinh, tenKh, gioiTinh, mail, sdt, diachi);
		
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
		return kh;

	}

	

	public ArrayList<KhachHang> layKhachHangTheoSdt(String sdtt) throws SQLException {
		ArrayList<KhachHang> lstKH = new ArrayList<KhachHang>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "SELECT * FROM KhachHang WHERE SoDT LIKE ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, "%"+sdtt+"%");
			// ketqua
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maKH=rs.getString("MaKH");
				String tenKh=rs.getString("TenKH");
				boolean gioiTinh=rs.getBoolean("GioiTinh");
				String sdt=rs.getString("SoDT");
				String diachi = rs.getString("DiaChi");
				Date nSinh = rs.getDate("NgaySinh");
				LocalDate ngaysinh=nSinh.toLocalDate();
				String mail = rs.getString("Email");
				KhachHang kh=new KhachHang(maKH, ngaysinh, tenKh, gioiTinh, mail, sdt, diachi);
				lstKH.add(kh);
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
		return lstKH;

	}
	
	public boolean taoKhachHang(KhachHang cus) throws SQLException {
		ConnectDB.getInstance();
		Connection con=ConnectDB.getConnection();
		PreparedStatement stmt=null;
		int n=0;
		try {
			stmt=con.prepareStatement("insert into KhachHang "
					+ "(TenKH,SoDT,GioiTinh,Email,DiaChi,ngaySinh)"
					+ "  values( ?, ?, ?, ?, ?,?)");
			stmt.setString(1,cus.getTenKH());
			stmt.setString(2, cus.getSDT());
			stmt.setBoolean(3,cus.isGioiTinh());
			stmt.setString(4,cus.getEmail());
			stmt.setString(5,cus.getDiachi());
			stmt.setDate(6, Date.valueOf(cus.getNgaySinh()));
			
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
	
	public boolean taoKhachHangNhanh(KhachHang cus) throws SQLException {
		ConnectDB.getInstance();
		Connection con=ConnectDB.getConnection();
		PreparedStatement stmt=null;
		int n=0;
		try {
			stmt=con.prepareStatement("insert into KhachHang "
					+ "(TenKH,SoDT,GioiTinh)"
					+ "  values( ?, ?, ?)");
			stmt.setString(1,cus.getTenKH());
			stmt.setString(2, cus.getSDT());
			stmt.setBoolean(3,cus.isGioiTinh());
//			stmt.setString(4,cus.getEmail());
//			stmt.setString(5,cus.getDiachi());
//			stmt.setDate(6, java.sql.Date.valueOf(cus.getNgaySinh()));
			
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
	
	public boolean xoaKhachHang(KhachHang kh) throws SQLException {
		ConnectDB.getInstance();
		Connection con=ConnectDB.getConnection();
		PreparedStatement stmt=null;
		int n=0;
		try {
			stmt=con.prepareStatement("delete from KhachHang where MaKH=?");
			
			stmt.setString(1,kh.getMaKH());
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
	public boolean capnhatKhachHang(KhachHang cus,String code) throws SQLException {
		ConnectDB.getInstance();
		Connection con=ConnectDB.getConnection();
		PreparedStatement stmt=null;
		int n=0;
		try {
			stmt = con.prepareStatement("UPDATE KhachHang "
					+ "SET TenKH =?, SoDT =?, GioiTinh =?, Email =?, "
					+ "DiaChi =?, NgaySinh =? WHERE MaKH =? ");
	
			stmt.setString(1,cus.getTenKH());
			stmt.setString(2, cus.getSDT());
			stmt.setBoolean(3,cus.isGioiTinh());
			stmt.setString(4,cus.getEmail());
			stmt.setString(5,cus.getDiachi());
			stmt.setDate(6, Date.valueOf(cus.getNgaySinh()));
			stmt.setString(7,code);
			
			n=stmt.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("e1 update customer error");
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
//	public boolean updateStatus(int cusCode,boolean status) throws SQLException {
//		ConnectDB.getInstance();
//		Connection con=ConnectDB.getConnection();
//		PreparedStatement stmt=null;
//		int n=0;
//		try {
//			stmt = con.prepareStatement("UPDATE KhachHang  SET Status=? Where Customer=?");
//			
//			
//			stmt.setInt(2,cusCode);
//			stmt.setInt(1,status==true?1:0);
//			n=stmt.executeUpdate();
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//			System.out.println("e1 update customer error");
//		}
//		finally {
//			try {
//				stmt.close();
//			} catch (SQLException e2) {
//				e2.printStackTrace();
//				System.out.println("e2 update customer error");
//			}
//		}
//		return n>0;
//	}
//	
}