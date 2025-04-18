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
import entity.NhanVien;
//import model.NameModel;


public class NhanVienDao {
	
	public NhanVienDao() {
		
	}
	//lấy tất cả nhân viên
	public ArrayList<NhanVien> layTatCaNhanVien() throws SQLException{
		 ArrayList<NhanVien> listEmploy = new ArrayList<NhanVien>();	
		 Statement stmt =null;
		 try {
			 ConnectDB.getInstance();
			 Connection con = ConnectDB.getConnection();
			 String sql = "Select * from NhanVien";
			 stmt = con.createStatement();
			 
			 ResultSet rs = stmt.executeQuery(sql);
			 while(rs.next())
			 {
				 String employeeCode = rs.getString("MaNV");
				 String name = rs.getString("TenNV");
				 String citizenNumber= rs.getString("CCCD");
				 Date bitrhDayy = rs.getDate("NgaySinh");
				 LocalDate bitrhDay = bitrhDayy.toLocalDate();
				 boolean sex = rs.getBoolean("GioiTinh");
				 String employeeLocation = rs.getString("DiaChi");
				 String employeePhone = rs.getString("SoDT");
//				 String employeePosition = rs.getString("ChucVu"); 
				 BigDecimal salary = rs.getBigDecimal("Luong");
				 String tinhTrang=rs.getString("TinhTrang");
				 double salaryDouble = salary.doubleValue();
				 Date dateStartt = rs.getDate("NgayVaoLam");
				 LocalDate dateStart = dateStartt.toLocalDate();
				 boolean isQuanLy=rs.getBoolean("IsQuanLy");
				 byte[] image = rs.getBytes("HinhAnh");
				 
				 NhanVien e = new NhanVien(employeeCode, name, citizenNumber, bitrhDay, sex,
						 employeeLocation, employeePhone, salaryDouble, tinhTrang, dateStart, image, isQuanLy);
				 listEmploy.add(e);
			 }
		 }catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(stmt!=null) {
				stmt.close();
			}
		}
		return listEmploy;
	}
	// lấy nhân viên theo mã
	public NhanVien layNhanVienTheoMa(String id) throws SQLException{	
		NhanVien nv = null;
		
		 Connection con = ConnectDB.getInstance().getConnection();
		 PreparedStatement pstmt = null;
		 try {
			 String sql ="Select * from NhanVien where MaNV = ?";
			 pstmt = con.prepareStatement(sql);
			 pstmt.setString(1, id);
			 
			 ResultSet rs = pstmt.executeQuery();
			 while(rs.next())
			 { 
				 String employeeCode = rs.getString("MaNV");
				 String name = rs.getString("TenNV");
				 String citizenNumber= rs.getString("CCCD");
				 Date bitrhDayy = rs.getDate("NgaySinh");
				 LocalDate bitrhDay = bitrhDayy.toLocalDate();
				 boolean sex = rs.getBoolean("GioiTinh");
				 String employeeLocation = rs.getString("DiaChi");
				 String employeePhone = rs.getString("SoDT");
//				 String employeePosition = rs.getString("ChucVu"); 
				 BigDecimal salary = rs.getBigDecimal("Luong");
				 String tinhTrang=rs.getString("TinhTrang");
				 double salaryDouble = salary.doubleValue();
				 Date dateStartt = rs.getDate("NgayVaoLam");
				 LocalDate dateStart = dateStartt.toLocalDate();
				 boolean isQuanLy=rs.getBoolean("IsQuanLy");
				 byte[] image = rs.getBytes("HinhAnh");
				 
				 nv = new NhanVien(employeeCode, name, citizenNumber, bitrhDay, sex,
						 employeeLocation, employeePhone, salaryDouble, tinhTrang, dateStart, image, isQuanLy);;
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
				}try {
				pstmt.close();
			}
			
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		 return nv;
	}
	
	public ArrayList<NhanVien> timNhanVienTheoMa(String id) throws SQLException{	
		ArrayList<NhanVien> lnv=new ArrayList<NhanVien>();
		
		 Connection con = ConnectDB.getInstance().getConnection();
		 PreparedStatement pstmt = null;
		 try {
			 String sql ="Select * from NhanVien where MaNV LIKE ?";
			 pstmt = con.prepareStatement(sql);
			 pstmt.setString(1, "%"+id+"%");
			 
			 ResultSet rs = pstmt.executeQuery();
			 while(rs.next())
			 { 
				 String employeeCode = rs.getString("MaNV");
				 String name = rs.getString("TenNV");
				 String citizenNumber= rs.getString("CCCD");
				 Date bitrhDayy = rs.getDate("NgaySinh");
				 LocalDate bitrhDay = bitrhDayy.toLocalDate();
				 boolean sex = rs.getBoolean("GioiTinh");
				 String employeeLocation = rs.getString("DiaChi");
				 String employeePhone = rs.getString("SoDT");
//				 String employeePosition = rs.getString("ChucVu"); 
				 BigDecimal salary = rs.getBigDecimal("Luong");
				 String tinhTrang=rs.getString("TinhTrang");
				 double salaryDouble = salary.doubleValue();
				 Date dateStartt = rs.getDate("NgayVaoLam");
				 LocalDate dateStart = dateStartt.toLocalDate();
				 boolean isQuanLy=rs.getBoolean("IsQuanLy");
				 byte[] image = rs.getBytes("HinhAnh");
				 
				 NhanVien nv = new NhanVien(employeeCode, name, citizenNumber, bitrhDay, sex,
						 employeeLocation, employeePhone, salaryDouble, tinhTrang, dateStart, image, isQuanLy);;
				lnv.add(nv);
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
				}try {
				pstmt.close();
			}
			
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		 return lnv;
	}
	
	public ArrayList<NhanVien> timNhanVienTheoTen(String ten) throws SQLException{	
		ArrayList<NhanVien> lnv=new ArrayList<NhanVien>();
		
		 Connection con = ConnectDB.getInstance().getConnection();
		 PreparedStatement pstmt = null;
		 try {
			 String sql ="Select * from NhanVien where TenNV LIKE ?";
			 pstmt = con.prepareStatement(sql);
			 pstmt.setString(1, "%"+ten+"%");
			 
			 ResultSet rs = pstmt.executeQuery();
			 while(rs.next())
			 { 
				 String employeeCode = rs.getString("MaNV");
				 String name = rs.getString("TenNV");
				 String citizenNumber= rs.getString("CCCD");
				 Date bitrhDayy = rs.getDate("NgaySinh");
				 LocalDate bitrhDay = bitrhDayy.toLocalDate();
				 boolean sex = rs.getBoolean("GioiTinh");
				 String employeeLocation = rs.getString("DiaChi");
				 String employeePhone = rs.getString("SoDT");
//				 String employeePosition = rs.getString("ChucVu"); 
				 BigDecimal salary = rs.getBigDecimal("Luong");
				 String tinhTrang=rs.getString("TinhTrang");
				 double salaryDouble = salary.doubleValue();
				 Date dateStartt = rs.getDate("NgayVaoLam");
				 LocalDate dateStart = dateStartt.toLocalDate();
				 boolean isQuanLy=rs.getBoolean("IsQuanLy");
				 byte[] image = rs.getBytes("HinhAnh");
				 
				 NhanVien nv = new NhanVien(employeeCode, name, citizenNumber, bitrhDay, sex,
						 employeeLocation, employeePhone, salaryDouble, tinhTrang, dateStart, image, isQuanLy);;
				lnv.add(nv);
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
				}try {
				pstmt.close();
			}
			
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		 return lnv;
	}
	//lay nhan vien theo sdt
	public ArrayList<NhanVien> timNhanVienTheoSDT(String sdt) throws SQLException{	
		ArrayList<NhanVien> lnv=new ArrayList<NhanVien>();
		
		 Connection con = ConnectDB.getInstance().getConnection();
		 PreparedStatement pstmt = null;
		 try {
			 String sql ="select * from NhanVien where SoDT LIKE ?";
			 pstmt = con.prepareStatement(sql);
			 pstmt.setString(1, "%"+sdt+"%");
			 
			 ResultSet rs = pstmt.executeQuery();
			 while(rs.next())
			 { 
				 String employeeCode = rs.getString("MaNV");
				 String name = rs.getString("TenNV");
				 String citizenNumber= rs.getString("CCCD");
				 Date bitrhDayy = rs.getDate("NgaySinh");
				 LocalDate bitrhDay = bitrhDayy.toLocalDate();
				 boolean sex = rs.getBoolean("GioiTinh");
				 String employeeLocation = rs.getString("DiaChi");
				 String employeePhone = rs.getString("SoDT");
//				 String employeePosition = rs.getString("ChucVu"); 
				 BigDecimal salary = rs.getBigDecimal("Luong");
				 String tinhTrang=rs.getString("TinhTrang");
				 double salaryDouble = salary.doubleValue();
				 Date dateStartt = rs.getDate("NgayVaoLam");
				 LocalDate dateStart = dateStartt.toLocalDate();
				 boolean isQuanLy=rs.getBoolean("IsQuanLy");
				 byte[] image = rs.getBytes("HinhAnh");
				 
				 NhanVien nv = new NhanVien(employeeCode, name, citizenNumber, bitrhDay, sex,
						 employeeLocation, employeePhone, salaryDouble, tinhTrang, dateStart, image, isQuanLy);;
				lnv.add(nv);
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
				}try {
				pstmt.close();
			}
			
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		 return lnv;
	}
	
	public boolean taoNhanVien(NhanVien em) throws SQLException {
		ConnectDB.getInstance();
		Connection con=ConnectDB.getConnection();
		PreparedStatement stmt=null;
		int n=0;
		try {
			stmt = con.prepareStatement("INSERT INTO NhanVien "
					+ "(TenNV, CCCD, NgaySinh, GioiTinh, "
					+ "DiaChi, SoDT, TinhTrang, "
					+ "Luong, NgayVaoLam, HinhAnh) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			
			stmt.setString(1, em.getEmployeeName());

			stmt.setString(2, em.getCitizenIdNumber());
			LocalDate birth = em.getEmployeeBirthday();
			Date birthDay = Date.valueOf(birth);
			stmt.setDate(3, birthDay );
			stmt.setBoolean(4, em.isEmployeeSex());
			
			stmt.setString(5, em.getEmployeeLocation());
			stmt.setString(6, em.getEmployeePhoneNumber());
			stmt.setString(7, em.getTinhTrang());
			double salary = em.getSalary();
			BigDecimal decimalSalary = BigDecimal.valueOf(salary);
			stmt.setBigDecimal(8, decimalSalary);
			LocalDate start = em.getDateStart();
			Date dateStart = Date.valueOf(start);
			stmt.setDate(9, dateStart);
			stmt.setBytes(10, em.getImage());
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
	
	public boolean xoaNhanVien(NhanVien em) throws SQLException {
		
		ConnectDB.getInstance();
		Connection con=ConnectDB.getConnection();
		PreparedStatement stmt=null;
		int n=0;
		try {
			stmt=con.prepareStatement("delete from NhanVien where MaNV=?");
			
			stmt.setString(1, em.getEmployeeCode());
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
	
	public boolean capnhatNhanVien(NhanVien em,String code) throws SQLException {
		ConnectDB.getInstance();
		Connection con=ConnectDB.getConnection();
		PreparedStatement stmt=null;
		int n=0;
		try {
			stmt = con.prepareStatement("UPDATE NhanVien " +
					"SET TenNV = ?, CCCD = ?, NgaySinh = ?, GioiTinh = ?, DiaChi = ?, SoDT = ?, TinhTrang = ?, Luong = ?, NgayVaoLam = ?, HinhAnh = ? " 
					+
					"WHERE MaNV = ?");
			
			
			stmt.setString(1, em.getEmployeeName());
		
			stmt.setString(2, em.getCitizenIdNumber());
			
			LocalDate birth = em.getEmployeeBirthday();
			Date birthDay = Date.valueOf(birth);
			stmt.setDate(3, birthDay );
			
			stmt.setBoolean(4, em.isEmployeeSex());
			stmt.setString(5, em.getEmployeeLocation());
			stmt.setString(6, em.getEmployeePhoneNumber());
//			stmt.setString(8, em.getEmployeePosition());
			stmt.setString(7, em.getTinhTrang());
			double salary = em.getSalary();
			BigDecimal decimalSalary = BigDecimal.valueOf(salary);
			stmt.setBigDecimal(8, decimalSalary);
			
			LocalDate start = em.getDateStart();
			Date dateStart = Date.valueOf(start);
			stmt.setDate(9, dateStart);
			
			stmt.setBytes(10, em.getImage());
			stmt.setString(11, code);
			
			n=stmt.executeUpdate();
			System.out.println("Cập Nhật Thông Tin Nhân Viên "+em.getEmployeeName()+" Thành Công!");
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
//	//test	 THANH CONG
//	public static void main(String[] args) throws SQLException {
//		ConnectDB.getInstance().connectDataBase();
//		System.out.println("xuất hết table: \n" + new EmployeeDao().getAllTableEmployee()); // ra 3 db
//		System.out.println("tìm theo id: \n" + new EmployeeDao().getEmployeeByID("NV001")); // ra 1
//		System.out.println("tìm theo tên: \n"+ new EmployeeDao().getEmployeeByName("Nguyễn Văn A"));
//		System.out.println("tìm theo sdt: \n"+ new EmployeeDao().getEmployeeByPhone("0901234567"));
//		
//		// Tạo đối tượng Employee mới
//        Employee employee = new Employee("EMP002");
//        
//        NameModel namee = new NameModel("Huyy");
//        employee.setEmployeeName(namee);
//        
//        employee.setCitizenIdNumber("123456789");
//        employee.setEmployeeBirthday(LocalDate.of(1990, 5, 15));
//        employee.setEmployeeSex(true);
//        employee.setEmployeeLocation("New York");
//        employee.setEmployeePhoneNumber("1234567890");
//        employee.setEmployeePosition("Manager");
//        employee.setSalary(5000.0);
//        employee.setDateStart(LocalDate.now());
//		
//		//System.out.println("tao nv: \n" + new EmployeeDao().createEmployee(employee));
//		
//		// Update đối tượng Employee theo EmployeeCode
//        Employee employee1 = new Employee("EMP003");
//        
//        NameModel namee1 = new NameModel("Trinh");
//        employee1.setEmployeeName(namee1);
//        
//        employee1.setCitizenIdNumber("123456789");
//        employee1.setEmployeeBirthday(LocalDate.of(1990, 5, 15));
//        employee1.setEmployeeSex(true);
//        employee1.setEmployeeLocation("New York");
//        employee1.setEmployeePhoneNumber("1234567890");
//        employee1.setEmployeePosition("Saler");
//        employee1.setSalary(15000.0);
//        employee1.setDateStart(LocalDate.now());
//		//System.out.println("update nv: \n" + new EmployeeDao().updateEmployee(employee1, "EMP002"));
//		
//        // xoa đối tượng Employee theo EmployeeCode
//        Employee employee2 = new Employee("EMP001");
//        System.out.println("xoa nv theo id: \n " + new EmployeeDao().removeEmployee(employee2));
//		
//	}
	
}



