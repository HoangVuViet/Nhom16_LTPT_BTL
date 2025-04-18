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
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import connect.ConnectDB;
import entity.DonViTinh;
import entity.KhachHang;
import entity.LoaiThuoc;
import entity.NhanVien;
import entity.Thuoc;

public class ThuocDao {
	
	public ThuocDao() {
		// TODO Auto-generated constructor stub
	}
	public ArrayList<Thuoc> timTatCaThuoc() throws SQLException{
		ArrayList<Thuoc> lstThuoc = new ArrayList<Thuoc>();
		Connection con = ConnectDB.getInstance().getConnection();
		Statement stmt=null;
		try {
			
			con = ConnectDB.getConnection();
			String sql="Select * from Thuoc";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String ma = rs.getString("MaThuoc");
				String ten = rs.getString("TenThuoc");
				double gia = rs.getFloat("GiaBan");
				int donvi = rs.getInt("DonViTinh");
				int maLoaiThuoc  = rs.getInt("LoaiThuoc");
				int soluongTon = rs.getInt("SoLuongTon");
				int hsd = rs.getInt("HanSuDung");
				byte[] hinhanh = rs.getBytes("HinhMinhHoa");
				String moTa=rs.getString("MoTaChucNang");
				double giaNhap = rs.getDouble("GiaNhap");
				Date ngaysx = rs.getDate("NgaySanXuat");
				
				LoaiThuoc l = new LoaiThuoc();
				l.setId(maLoaiThuoc);
				
				DonViTinh d = new DonViTinh(donvi);
				
				Thuoc thuoc = new Thuoc(ma, ten, gia, soluongTon, d, hsd, hinhanh, l , moTa,giaNhap,ngaysx);
				lstThuoc.add(thuoc);
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
		return lstThuoc;
	}
	
	public ArrayList<String> timLoaiThuoc() throws SQLException{
		ConnectDB.getInstance().getConnection();
		ArrayList<String> lst=new ArrayList<String>();
		ArrayList<Thuoc> listT=timTatCaThuoc();
		for(Thuoc t:listT) {
			lst.add(t.getLoaiThuoc().getLoaiThuoc());
		}
		
		return lst;
	}
	
	 public ArrayList<Thuoc> kiemTraThuocSapHetHan() throws SQLException {
	        ArrayList<Thuoc> lstThuocSapHetHan = new ArrayList<>();
	        ArrayList<Thuoc> lstTatCaThuoc = timTatCaThuoc();
	        Calendar calendar = Calendar.getInstance();

	        for (Thuoc thuoc : lstTatCaThuoc) {
	            calendar.setTime(thuoc.getNgaySanXuat());
	            calendar.add(Calendar.MONTH, thuoc.getHanSD());
	            java.util.Date ngayHetHan = calendar.getTime();

	            // Kiểm tra xem ngày hết hạn có gần đến hay không (ví dụ: trong vòng 30 ngày)
	            Calendar ngayHienTai = Calendar.getInstance();
	            ngayHienTai.add(Calendar.DAY_OF_MONTH, 30);
	            if (ngayHetHan.before(ngayHienTai.getTime())) {
	                lstThuocSapHetHan.add(thuoc);
	            }
	        }

	        return lstThuocSapHetHan;
	    }

	    public ArrayList<Thuoc> kiemTraThuocCanKietSoLuong() throws SQLException {
	        ArrayList<Thuoc> lstThuocCanKiet = new ArrayList<>();
	        ArrayList<Thuoc> lstTatCaThuoc = timTatCaThuoc();
	        int soLuongToiThieu = 30; // Số lượng tối thiểu trước khi coi là cạn kiệt

	        for (Thuoc thuoc : lstTatCaThuoc) {
	            if (thuoc.getSoLuong() < soLuongToiThieu) {
	                lstThuocCanKiet.add(thuoc);
	            }
	        }

	        return lstThuocCanKiet;
	    }
	
	public ArrayList<Thuoc> timThuocTheoMa(String maThuoc) throws SQLException {
		ArrayList<Thuoc> lstThuoc = new ArrayList<Thuoc>();
		
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "SELECT * FROM Thuoc WHERE maThuoc = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, maThuoc);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String ma = rs.getString("MaThuoc");
				String ten = rs.getString("TenThuoc");
				double gia = rs.getFloat("GiaBan");
				int donvi = rs.getInt("DonViTinh");
				int maLoaiThuoc  = rs.getInt("LoaiThuoc");
				int soluongTon = rs.getInt("SoLuongTon");
				int hsd = rs.getInt("HanSuDung");
				byte[] hinhanh = rs.getBytes("HinhMinhHoa");
				String moTa=rs.getString("MoTaChucNang");
				double giaNhap = rs.getDouble("giaNhap");
				Date ngaysx = rs.getDate("ngaySanXuat");
				
				LoaiThuoc l = new LoaiThuoc();
				l.setId(maLoaiThuoc);
				
				DonViTinh d = new DonViTinh(donvi);
				
				Thuoc thuoc = new Thuoc(ma, ten, gia, soluongTon, d, hsd, hinhanh, l, moTa,giaNhap,ngaysx);
				lstThuoc.add(thuoc);
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
		return lstThuoc;
	}
	
	public Thuoc layThuocTheoMa(String maThuoc) throws SQLException {
//		ArrayList<Thuoc> lstThuoc = new ArrayList<Thuoc>();
		Thuoc thuoc = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "SELECT * FROM Thuoc WHERE maThuoc = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, maThuoc);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String ma = rs.getString("MaThuoc");
				String ten = rs.getString("TenThuoc");
				double gia = rs.getFloat("GiaBan");
				int donvi = rs.getInt("DonViTinh");
				int maLoaiThuoc  = rs.getInt("LoaiThuoc");
				int soluongTon = rs.getInt("SoLuongTon");
				int hsd = rs.getInt("HanSuDung");
				byte[] hinhanh = rs.getBytes("HinhMinhHoa");
				String moTa=rs.getString("MoTaChucNang");
				double giaNhap = rs.getDouble("giaNhap");
				Date ngaysx = rs.getDate("ngaySanXuat");
				
				LoaiThuoc l = new LoaiThuoc();
				l.setId(maLoaiThuoc);
				
				DonViTinh d = new DonViTinh(donvi);

				thuoc = new Thuoc(ma, ten, gia, soluongTon, d, hsd, hinhanh, l, moTa,giaNhap,ngaysx);
				
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
		return thuoc;
	}
	
	public ArrayList<Thuoc> layThuocTheoTen(String tenThuoc){
		ArrayList<Thuoc> lstThuoc = new ArrayList<Thuoc>();	
		 try {
			ConnectDB.getInstance();
		 } catch (SQLException e) {
			e.printStackTrace();
		 }
		 
		 Connection con = ConnectDB.getConnection();
		 PreparedStatement pstmt = null;
		 try {
			 String sql ="Select * from Thuoc where tenThuoc like ?";
			 pstmt = con.prepareStatement(sql);
			 pstmt.setString(1, "%" + tenThuoc + "%" );
			 ResultSet rs = pstmt.executeQuery();
			 while(rs.next())
			 { 
				 String ma = rs.getString("MaThuoc");
					String ten = rs.getString("TenThuoc");
					double gia = rs.getFloat("GiaBan");
					int donvi = rs.getInt("DonViTinh");
					int maLoaiThuoc  = rs.getInt("LoaiThuoc");
					int soluongTon = rs.getInt("SoLuongTon");
					int hsd = rs.getInt("HanSuDung");
					byte[] hinhanh = rs.getBytes("HinhMinhHoa");
					String moTa=rs.getString("MoTaChucNang");
					double giaNhap = rs.getDouble("giaNhap");
					Date ngaysx = rs.getDate("ngaySanXuat");
					
					LoaiThuoc l = new LoaiThuoc();
					l.setId(maLoaiThuoc);
					
					DonViTinh d = new DonViTinh(donvi);
					
					Thuoc thuoc = new Thuoc(ma, ten, gia, soluongTon, d, hsd, hinhanh, l, moTa,giaNhap,ngaysx);
					lstThuoc.add(thuoc);
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
		return lstThuoc;
	}
	
	public static ArrayList<String> layDanhSachThuocKhongTrung(ArrayList<String> danhSachLoaiThuoc) {
        HashSet<String> setLoaiThuocKhongTrung = new HashSet<String>(danhSachLoaiThuoc);
        return new ArrayList<>(setLoaiThuocKhongTrung);
    }
	public ArrayList<Thuoc> timThuocTheoMaVaTen(String ma,String ten,String loai){
		ArrayList<Thuoc> lstThuoc = new ArrayList<Thuoc>();	
		 try {
			ConnectDB.getInstance();
		 } catch (SQLException e) {
			e.printStackTrace();
		 }
		 
		 Connection con = ConnectDB.getConnection();
		 PreparedStatement pstmt = null;
		 try {
			 String sql ="Select * from Thuoc where MaThuoc Like ? and TenThuoc Like ? and LoaiThuoc = ?";
			 pstmt = con.prepareStatement(sql);
			 pstmt.setString(1, "%"+ma+"%");
			 pstmt.setString(2,"%"+ten+"%");
			 pstmt.setString(3,loai);
			 ResultSet rs = pstmt.executeQuery();
			 while(rs.next())
			 {
				 String ma1 = rs.getString("MaThuoc");
					String ten1 = rs.getString("TenThuoc");
					double gia = rs.getFloat("GiaBan");
					int donvi = rs.getInt("DonViTinh");
					int maLoaiThuoc  = rs.getInt("LoaiThuoc");
					int soluongTon = rs.getInt("SoLuongTon");
					int hsd = rs.getInt("HanSuDung");
					byte[] hinhanh = rs.getBytes("HinhMinhHoa");
					String moTa=rs.getString("MoTaChucNang");
					double giaNhap = rs.getDouble("giaNhap");
					Date ngaysx = rs.getDate("ngaySanXuat");
					
					LoaiThuoc l = new LoaiThuoc();
					l.setId(maLoaiThuoc);
					
					DonViTinh d = new DonViTinh(donvi);
					
					Thuoc thuoc = new Thuoc(ma1, ten1, gia, soluongTon, d, hsd, hinhanh, l, moTa,giaNhap,ngaysx);
					lstThuoc.add(thuoc);
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
		return lstThuoc;
	}
	
	public ArrayList<Thuoc> timThuocTheoLoai(String loaiThuoc){
		ArrayList<Thuoc> lstThuoc = new ArrayList<Thuoc>();	
		 try {
			ConnectDB.getInstance();
		 } catch (SQLException e) {
			e.printStackTrace();
		 }
		 
		 Connection con = ConnectDB.getConnection();
		 PreparedStatement pstmt = null;
		 try {
			 String sql ="Select t.*,l.LoaiThuoc from Thuoc t "
			 		+ "inner join LoaiThuoc l  on t.LoaiThuoc = l.Id "
			 		+ "where l.LoaiThuoc like ?";
			 pstmt = con.prepareStatement(sql);
			 pstmt.setString(1, "%" + loaiThuoc + "%" );
			 ResultSet rs = pstmt.executeQuery();
			 while(rs.next())
			 {
				 String ma = rs.getString("MaThuoc");
					String ten = rs.getString("TenThuoc");
					double gia = rs.getFloat("GiaBan");
					int donvi = rs.getInt("DonViTinh");
					int maLoaiThuoc  = rs.getInt("LoaiThuoc");
					int soluongTon = rs.getInt("SoLuongTon");
					int hsd = rs.getInt("HanSuDung");
					byte[] hinhanh = rs.getBytes("HinhMinhHoa");
					String moTa=rs.getString("MoTaChucNang");
					double giaNhap = rs.getDouble("giaNhap");
					Date ngaysx = rs.getDate("ngaySanXuat");
					
					LoaiThuoc l = new LoaiThuoc();
					l.setId(maLoaiThuoc);
					
					DonViTinh d = new DonViTinh(donvi);

					Thuoc thuoc = new Thuoc(ma, ten, gia, soluongTon, d, hsd, hinhanh, l, moTa,giaNhap,ngaysx);
					lstThuoc.add(thuoc);
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
		return lstThuoc;
	}
	
//chua xong	
	public boolean taoThuoc(Thuoc thuoc) throws SQLException {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement pstmt = null;
		int n = 0;
		try {
			pstmt = con.prepareStatement("insert into Thuoc "
					+ "(TenThuoc,GiaBan,DonViTinh ,LoaiThuoc,MoTaChucNang, SoLuongTon, HanSuDung,HinhMinhHoa,giaNhap, ngaySanXuat) " 
					+ "values (?,?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1, thuoc.getTenThuoc());
			pstmt.setDouble(2, thuoc.getGia());
			
			pstmt.setInt(3, thuoc.getDonViTinh().getMaDonVi());
			
			pstmt.setInt(4, thuoc.getLoaiThuoc().getId());
			
			pstmt.setString(5, thuoc.getMoTa());
			pstmt.setInt(6, thuoc.getSoLuong());
			pstmt.setInt(7, thuoc.getHanSD());
			pstmt.setBytes(8, thuoc.getHinhMinhHoa());
			pstmt.setDouble(9, thuoc.getGiaNhap());
			pstmt.setDate(10, thuoc.getNgaySanXuat());
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
	
//	public boolean xoaThuoc(String thuoc) throws SQLException {
//		ConnectDB.getInstance();
//		Connection con=ConnectDB.getConnection();
//		PreparedStatement stmt=null;
//		ChiTietDonDatDao ctdnDao=new ChiTietDonDatDao();
//		int n=0;
//		try {
//			stmt=con.prepareStatement("delete from Thuoc where MaThuoc=?");
//			
//			stmt.setString(1, thuoc);
//			ctdnDao.xoaChiTietDonDatBangMaThuoc(thuoc);
//			n=stmt.executeUpdate();
//			System.out.println("Xoá thuốc "+thuoc+" khỏi database thành công!");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		finally {
//			if(con!=null) {
//				try {
//					stmt.close();
//				} catch (SQLException e2) {
//					e2.printStackTrace();
//				}
//			}
//		}
//		return n>0;
//	}
	
	public boolean xoaThuoc(String maThuoc) throws SQLException {
		ConnectDB.getInstance();
		Connection con=ConnectDB.getConnection();
		PreparedStatement stmt=null;
		int n=0;
		try {
			stmt = con.prepareStatement("UPDATE Thuoc " +
					"SET SoLuongTon = ? WHERE maThuoc = ?");
			stmt.setInt(1, 0);
			stmt.setString(2, maThuoc);
			n=stmt.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("e1 update Thuoc error");
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
	
	
	public boolean capnhatThuoc(Thuoc thuoc,String maThuoc) throws SQLException {
		ConnectDB.getInstance();
		Connection con=ConnectDB.getConnection();
		PreparedStatement stmt=null;
		int n=0;
		try {
			stmt = con.prepareStatement("UPDATE Thuoc " +
					"SET HinhMinhHoa = ?, MoTaChucNang = ? "
					+
					"WHERE maThuoc = ?");
			stmt.setBytes(1, thuoc.getHinhMinhHoa());
			stmt.setString(3, maThuoc);
			stmt.setString(2, thuoc.getMoTa());
			n=stmt.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("e1 update Thuoc error");
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
	public Thuoc timThuocTheoTenVaLoai(String ten, String loai) {
		Thuoc thuoc = null;
		 try {
			ConnectDB.getInstance();
		 } catch (SQLException e) {
			e.printStackTrace();
		 }
		 
		 Connection con = ConnectDB.getConnection();
		 PreparedStatement pstmt = null;
		 try {
			 String sql ="Select * from Thuoc where tenThuoc = ? and loaiThuoc = ?";
			 pstmt = con.prepareStatement(sql);
			 pstmt.setString(1, ten);
			 pstmt.setString(2, loai);
			 ResultSet rs = pstmt.executeQuery();
			 while(rs.next())
			 {
				 	String ma = rs.getString("MaThuoc");
					String tenThuoc = rs.getString("TenThuoc");
					double gia = rs.getFloat("GiaBan");
					int donvi = rs.getInt("DonViTinh");
					int maLoaiThuoc  = rs.getInt("LoaiThuoc");
					int soluongTon = rs.getInt("SoLuongTon");
					int hsd = rs.getInt("HanSuDung");
					byte[] hinhanh = rs.getBytes("HinhMinhHoa");
					String moTa=rs.getString("MoTaChucNang");
					double giaNhap = rs.getDouble("giaNhap");
					Date ngaysx = rs.getDate("ngaySanXuat");
					
					LoaiThuoc l = new LoaiThuoc();
					l.setId(maLoaiThuoc);
					
					DonViTinh d = new DonViTinh(donvi);
					
					thuoc = new Thuoc(ma, tenThuoc, gia, soluongTon, d, hsd, hinhanh, l, moTa,giaNhap,ngaysx);
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
		 return thuoc;
	}
	
	public boolean truSoLuongSanPhamBangMaSP(int so,String ma) throws SQLException {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		int rs=0;
		try {
			String sql = "UPDATE Thuoc SET SoLuongTon = SoLuongTon - ? WHERE MaThuoc = ?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, so);
			stmt.setString(2, ma);
			rs = stmt.executeUpdate();
		
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
		
		System.out.println("Thuoc "+ma+"giam "+so+"so luong!");
		return rs>0;
		
	}
	
	public ArrayList<Thuoc> timThuocTheoMaTenLoai(String ma, String ten, String loai) throws SQLException {
	    ArrayList<Thuoc> list = new ArrayList<Thuoc>();
	    Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    DonViTinhDao dvDao=new DonViTinhDao();
	    try {
	        String sql = "SELECT Thuoc.* FROM Thuoc JOIN LoaiThuoc ON Thuoc.LoaiThuoc = LoaiThuoc.Id " +
	                     "WHERE Thuoc.MaThuoc LIKE ? AND Thuoc.TenThuoc LIKE ? AND LoaiThuoc.LoaiThuoc LIKE ?";
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, "%" + ma + "%");
	        stmt.setString(2, "%" + ten + "%");
	        stmt.setString(3, "%"+loai+"%");
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            String maT = rs.getString("MaThuoc");
	            String tenT = rs.getString("TenThuoc");
	            double gia = rs.getFloat("GiaBan");
	            int donvi = rs.getInt("DonViTinh");
	            int maLoaiThuoc = rs.getInt("LoaiThuoc");
//	            String tenLoaiThuoc = rs.getString("LoaiThuoc");
	            int soluongTon = rs.getInt("SoLuongTon");
	            int hsd = rs.getInt("HanSuDung");
//	            String maDonNhap = rs.getString("MaDonNhap");
	            byte[] hinhanh = rs.getBytes("HinhMinhHoa");
	            String moTa = rs.getString("MoTaChucNang");
	            Date ngaySX = rs.getDate("ngaySanXuat");
	            double giaNhap = rs.getDouble("giaNhap");

	            LoaiThuoc l = new LoaiThuoc(maLoaiThuoc, loai);
	            DonViTinh d = dvDao.timDonViTheoMa(donvi);
	            Thuoc thuoc = new Thuoc(maT, tenT, gia, soluongTon, d, hsd, hinhanh, l, moTa, giaNhap, ngaySX);
	            list.add(thuoc);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (con != null) {
	            try {
	                stmt.close();
	            } catch (SQLException e2) {
	                e2.printStackTrace();
	            }
	        }
	    }
	    return list;
	}
	
	public String layMaThuocMoiTao() throws SQLException {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		Statement stmt = null;
		String maThuoc = null;
		try {
			String sql = "SELECT TOP 1 *"
					+ " FROM Thuoc"
					+ " ORDER BY stt DESC";
			stmt = con.createStatement();
			
			// ketqua
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				maThuoc=rs.getString("maThuoc");
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
		return maThuoc;
	}
	
	
}
