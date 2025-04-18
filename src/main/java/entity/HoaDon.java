package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
@Entity
public class HoaDon {
	@Id
	private String maHD;
	private String maKH;
	private String maNV;
	private LocalDateTime thoiGianXuatHD;
//	private boolean tinhTrangGiao;
	private double tongTien;
	private double tienNhan;
	private String phuongThucTT;
//
	
	@Override
	public int hashCode() {
		return Objects.hash(maHD);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HoaDon other = (HoaDon) obj;
		return maHD == other.maHD;
	}

	
	public HoaDon(String maKH, String maNV, LocalDateTime thoiGianXuatHD, double tongTien,
			double tienNhan, String phuongThucTT) {
//		super();
		this.maKH = maKH;
		this.maNV = maNV;
		this.thoiGianXuatHD = thoiGianXuatHD;
//		this.tinhTrangGiao = tinhTrangGiao;
		this.tongTien = tongTien;
		this.tienNhan = tienNhan;
		this.phuongThucTT = phuongThucTT;
	}


	public HoaDon(String maHD, String maKH, String maNV, LocalDateTime thoiGianXuatHD,
			double tongTien, double tienNhan, String phuongThucTT) {
//		super();
		this.maHD = maHD;
		this.maKH = maKH;
		this.maNV = maNV;
		this.thoiGianXuatHD = thoiGianXuatHD;
//		this.tinhTrangGiao = tinhTrangGiao;
		this.tongTien = tongTien;
		this.tienNhan = tienNhan;
		this.phuongThucTT = phuongThucTT;
	}


	public String getMaHD() {
		return maHD;
	}


	public void setMaHD(String maHD) {
		this.maHD = maHD;
	}


	public String getMaKH() {
		return maKH;
	}


	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}


	public String getMaNV() {
		return maNV;
	}


	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}


	public LocalDateTime getThoiGianXuatHD() {
		return thoiGianXuatHD;
	}


	public void setThoiGianXuatHD(LocalDateTime thoiGianXuatHD) {
		this.thoiGianXuatHD = thoiGianXuatHD;
	}

//
//	public boolean isTinhTrangGiao() {
//		return tinhTrangGiao;
//	}
//
//
//	public void setTinhTrangGiao(boolean tinhTrangGiao) {
//		this.tinhTrangGiao = tinhTrangGiao;
//	}


	public double getTongTien() {
		return tongTien;
	}


	public void setTongTien(double tongTien) {
		this.tongTien = tongTien;
	}


	public double getTienNhan() {
		return tienNhan;
	}


	public void setTienNhan(double tienNhan) {
		this.tienNhan = tienNhan;
	}


	public String getPhuongThucTT() {
		return phuongThucTT;
	}


	public void setPhuongThucTT(String phuongThucTT) {
		this.phuongThucTT = phuongThucTT;
	}


	public HoaDon(String maHD) {
//		super();
		this.maHD = maHD;
	}


	
	
	
	
}
