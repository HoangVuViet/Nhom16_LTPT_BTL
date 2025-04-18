package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import connect.ConnectDB;

public class ThongKeDao {
	public ArrayList<Object[]> thongKeThuocNhapTheoNgay(Date ngayBatdau, Date ngayKetthuc){
		ArrayList<Object[]> lstDoanhthu = new ArrayList<>();
		Connection con = null;
		PreparedStatement stmt=null;
		try {
			ConnectDB.getInstance().connectDataBase();
			con = ConnectDB.getConnection();
			String sql="SELECT "
					+ "    t.TenThuoc,"
					+ "	   t.DonViTinh,"
					+ "    ctdn.SoLuongNhap,"
					+ "    ctdn.SoLuongNhap * t.GiaNhap AS TongTienNhap "
					+ "FROM "
					+ "    DonNhapThuoc dnt "
					+ "JOIN "
					+ "    ChiTietDonNhap ctdn ON dnt.MaDonNhap = ctdn.MaDonNhap "
					+ "JOIN "
					+ "    Thuoc t ON ctdn.MaThuoc = t.MaThuoc "
					+ "WHERE "
					+ "    cast(dnt.NgayNhap as date) >= ? AND cast(dnt.NgayNhap as date) <= ?";
			
			stmt=con.prepareStatement(sql);
			stmt.setDate(1, ngayBatdau);
			stmt.setDate(2, ngayKetthuc);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				String tenThuoc = rs.getString("TenThuoc");
				int donvi = rs.getInt("DonViTinh");
				int soLuong = rs.getInt("SoLuongNhap");
				double tongTien = rs.getDouble("TongTienNhap");
				
				lstDoanhthu.add(new Object[] {tenThuoc, donvi, soLuong, tongTien});		
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
		return lstDoanhthu;
	}
	public ArrayList<Object[]> thongKeThuocBanTheoNgay(Date ngayBatdau, Date ngayKetthuc){
		ArrayList<Object[]> lstDoanhthu = new ArrayList<>();
		Connection con = null;
		PreparedStatement stmt=null;
		try {
			ConnectDB.getInstance().connectDataBase();
			con = ConnectDB.getConnection();
			String sql="SELECT "
					+ "    t.TenThuoc,"
					+ "	   t.DonViTinh,"
					+ "    cthd.SoLuong,"
					+ "    cthd.SoLuong * t.GiaBan AS TongTienBan "
					+ "FROM "
					+ "    HoaDon hd "
					+ "JOIN "
					+ "    ChiTietHD cthd ON hd.MaHD = cthd.MaHD "
					+ "JOIN "
					+ "    Thuoc t ON cthd.MaThuoc = t.MaThuoc "
					+ "WHERE "
					+ "    cast(hd.ThoiGianTao as date) >= ? AND cast(hd.ThoiGianTao as date) <= ?";
			
			stmt=con.prepareStatement(sql);
			stmt.setDate(1, ngayBatdau);
			stmt.setDate(2, ngayKetthuc);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				String tenThuoc = rs.getString("TenThuoc");
				int donvi = rs.getInt("DonViTinh");
				int soLuong = rs.getInt("SoLuong");
				double tongTien = rs.getDouble("TongTienBan");
				
				lstDoanhthu.add(new Object[] {tenThuoc, donvi, soLuong, tongTien});		
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
		return lstDoanhthu;
	}
	
	public ArrayList<Object[]> thongKeLoinhuanTheoNgay(Date ngayBatdau, Date ngayKetthuc){
		ArrayList<Object[]> lstLoinhuan = new ArrayList<>();
		Connection con = null;
		PreparedStatement stmt=null;
		try {
			ConnectDB.getInstance().connectDataBase();
			con = ConnectDB.getConnection();
			String sql ="WITH TongTienBan AS ("
					+ "    SELECT "
					+ "        CONVERT(date, ThoiGianTao) AS Ngay,"
					+ "        SUM(TongTien) AS TongTienBan"
					+ "    FROM "
					+ "        HoaDon\r\n"
					+ "    GROUP BY \r\n"
					+ "        CONVERT(date, ThoiGianTao)"
					+ "),"
					+ "TongTienVon AS ("
					+ "    SELECT "
					+ "        CONVERT(date, HD.ThoiGianTao) AS Ngay,"
					+ "        SUM(CTHD.SoLuong * T.giaNhap) AS TongTienVon"
					+ "    FROM "
					+ "        HoaDon HD"
					+ "    JOIN "
					+ "        ChiTietHD CTHD ON HD.MaHD = CTHD.MaHD"
					+ "    JOIN "
					+ "        Thuoc T ON CTHD.MaThuoc = T.MaThuoc"
					+ "    GROUP BY "
					+ "        CONVERT(date, HD.ThoiGianTao)"
					+ ")"
					+ "SELECT "
					+ "    TongTienBan.Ngay,"
					+ "    TongTienBan.TongTienBan,"
					+ "    TongTienVon.TongTienVon,"
					+ "	TongTienBan.TongTienBan - TongTienVon.TongTienVon as Loinhuan "
					+ "FROM "
					+ "    TongTienBan "
					+ "JOIN "
					+ "    TongTienVon ON TongTienBan.Ngay = TongTienVon.Ngay "
					+ "where cast(TongTienBan.Ngay as date) >= ? and cast(TongTienBan.Ngay as date) <= ? ";
			
			stmt=con.prepareStatement(sql);
			stmt.setDate(1, ngayBatdau);
			stmt.setDate(2, ngayKetthuc);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Date ngay = rs.getDate("Ngay");
				double tienBan = rs.getDouble("TongTienBan");
				double tienNhap = rs.getDouble("TongTienVon");
				double loinhuan = rs.getDouble("Loinhuan");
				
				
				lstLoinhuan.add(new Object[] {ngay, tienBan, tienNhap, loinhuan});		
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
		return lstLoinhuan;
		
	}
	
	public ArrayList<Object[]> thongKeTopNVBanChay(Date ngayBatdau, Date ngayKetthuc){
		ArrayList<Object[]> lstNV = new ArrayList<>();
		Connection con = null;
		PreparedStatement stmt=null;
		try {
			ConnectDB.getInstance().connectDataBase();
			con = ConnectDB.getConnection();
			String sql="select top 10 sum(TongTien) as TongTien,"
					+ "		MaNV "
					+ "from HoaDon "
					+ "where cast(ThoiGianTao as date) >= ? and cast(ThoiGianTao as date) <= ? "
					+ "group by MaNV "
					+ "order by TongTien desc";
			
			stmt=con.prepareStatement(sql);
			stmt.setDate(1, ngayBatdau);
			stmt.setDate(2, ngayKetthuc);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				String maNV = rs.getString("MaNV");
				double tongTien = rs.getDouble("TongTien");
				
				lstNV.add(new Object[] {maNV, tongTien});		
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
		return lstNV;
	}
}
