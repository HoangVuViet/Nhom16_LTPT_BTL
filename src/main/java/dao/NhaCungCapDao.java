package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import connect.ConnectDB;
import entity.NhaCungCap;
import entity.NhanVien;
import entity.Thuoc;

public class NhaCungCapDao {
	public NhaCungCapDao() {

	}

	//lấy tất cả nhân viên
	public ArrayList<NhaCungCap> layTatCaNCC() throws SQLException{
		 ArrayList<NhaCungCap> lstNCC = new ArrayList<NhaCungCap>();	
		 Statement stmt=null;
		 Connection con = ConnectDB.getInstance().getConnection();
		 try {
			 ConnectDB.getInstance();
//			 Connection con = ConnectDB.getConnection();
			 String sql = "Select * from NhaCungCap";
			 stmt = con.createStatement();
			 
			 ResultSet rs = stmt.executeQuery(sql);
			 while(rs.next())
			 {
				String maNCC = rs.getString("MaNCC");
				String ten = rs.getString("TenNCC");
				String sdt = rs.getString("SoDT");
				String diachi = rs.getString("DiaChi");
				
				NhaCungCap n = new NhaCungCap(maNCC, ten, sdt, diachi);
				lstNCC.add(n);
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
		return lstNCC;
	}
	
	// lấy nhân viên theo mã
		public NhaCungCap layNCCTheoMa(String id) throws SQLException{	
			NhaCungCap ncc = null;
			 Connection con = ConnectDB.getInstance().getConnection();
			 PreparedStatement pstmt = null;
			 try {
				 String sql ="Select * from NhaCungCap where MaNCC = ?";
				 pstmt = con.prepareStatement(sql);
				 pstmt.setString(1, id);
				 
				 ResultSet rs = pstmt.executeQuery();
				 while(rs.next())
				 { 
					String maNCC = rs.getString("MaNCC");
					String ten = rs.getString("TenNCC");
					String sdt = rs.getString("SoDT");
					String diachi = rs.getString("DiaChi");
					ncc = new NhaCungCap(maNCC, ten, sdt, diachi);
				 }
			 }
			 catch (SQLException e) {
				e.printStackTrace();
			}
			 finally {
				 if(con!=null) {
						try {
							pstmt.close();
						} catch (SQLException e2) {
							e2.printStackTrace();
						}
					}
			}
			 return ncc;
		}
	// lấy nhân viên theo mã
	public ArrayList<NhaCungCap> layNCCTheoTen(String ten) throws SQLException{	
		ArrayList<NhaCungCap> lstNCC = new ArrayList<>();
			 Connection con = ConnectDB.getInstance().getConnection();
			 PreparedStatement pstmt = null;
			 try {
				 String sql ="Select * from NhaCungCap where TenNCC like ?";
				 pstmt = con.prepareStatement(sql);
				 pstmt.setString(1, "%" + ten + "%");
				 
				 ResultSet rs = pstmt.executeQuery();
				 while(rs.next())
				 { 
					String maNCC = rs.getString("MaNCC");
					String tenNCC = rs.getString("TenNCC");
					String sdt = rs.getString("SoDT");
					String diachi = rs.getString("DiaChi");
					NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC, sdt, diachi);
					lstNCC.add(ncc);
				 }
			 }
			 catch (SQLException e) {
				e.printStackTrace();
			}
			 finally {
				 if(con!=null) {
						try {
							pstmt.close();
						} catch (SQLException e2) {
							e2.printStackTrace();
						}
					}
			}
			 return lstNCC;
	}		
	public ArrayList<NhaCungCap> layNCCTheoSdt(String sdt) throws SQLException{
		ArrayList<NhaCungCap> lstNCC = new ArrayList<NhaCungCap>();	
		 try {
			ConnectDB.getInstance();
		 } catch (SQLException e) {
			e.printStackTrace();
		 }
		 
		 Connection con = ConnectDB.getConnection();
		 PreparedStatement pstmt = null;
		 try {
			
			 String sql = "select * from NhaCungCap where SoDT like ?";
			 pstmt = con.prepareStatement(sql);
			 
			 pstmt.setString(1, "%" + sdt + "%" );
			 
			 ResultSet rs = pstmt.executeQuery();
			 while(rs.next())
			 {
				String maNCC = rs.getString("MaNCC");
				String ten = rs.getString("TenNCC");
				String sdtt = rs.getString("SoDT");
				String diachi = rs.getString("DiaChi");
				
				NhaCungCap n = new NhaCungCap(maNCC, ten, sdtt, diachi);
				lstNCC.add(n);
			 }
		 }
		 catch (SQLException e) {
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
		return lstNCC;
	}		
	public boolean taoNCC(NhaCungCap ncc) throws SQLException {
		ConnectDB.getInstance();
		Connection con=ConnectDB.getConnection();
		PreparedStatement stmt=null;
		int n=0;
		try {
			stmt = con.prepareStatement("INSERT INTO NhaCungCap "
					+ "(TenNCC, SoDT, DiaChi) " +
	                "VALUES (?, ?, ?)");
			stmt.setString(1, ncc.getTenNCC());
			stmt.setString(2,ncc.getSdtNCC());
			stmt.setString(3, ncc.getDiaChi());
			
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
	public boolean xoaNCC(NhaCungCap ncc) throws SQLException {
		ConnectDB.getInstance();
		Connection con=ConnectDB.getConnection();
		PreparedStatement stmt=null;
		int n=0;
		try {
			stmt=con.prepareStatement("delete from NhaCungCap where MaNCC=?");
			
			stmt.setString(1, ncc.getMaNCC());
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
	public boolean capnhatNCC(NhaCungCap ncc,String code) throws SQLException {
		ConnectDB.getInstance();
		Connection con=ConnectDB.getConnection();
		PreparedStatement stmt=null;
		int n=0;
		try {
			stmt = con.prepareStatement("UPDATE NhaCungCap " +
					"SET TenNCC = ?, SoDT = ?, DiaChi = ? WHERE MaNCC = ?");
			stmt.setString(1, ncc.getTenNCC());
			stmt.setString(2, ncc.getSdtNCC());
			stmt.setString(3, ncc.getDiaChi());
			stmt.setString(4, code);
			
			n=stmt.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("e1 update NhanVien error");
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
	

