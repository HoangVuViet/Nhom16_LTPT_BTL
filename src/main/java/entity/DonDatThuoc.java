package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
@Entity
public class DonDatThuoc {
	@Id
	private String maDon;
	private String maNV;
	private String ncc;
	private LocalDateTime ngayNhap;
	
	
	public DonDatThuoc(String maDon) {
//		super();
		this.maDon = maDon;
	}

	public DonDatThuoc() {
		// TODO Auto-generated constructor stub
	}

	public String getMaDon() {
		return maDon;
	}

	public void setMaDon(String maDon) {
		this.maDon = maDon;
	}

	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}

	public String getNcc() {
		return ncc;
	}

	public void setNcc(String ncc) {
		this.ncc = ncc;
	}

	public LocalDateTime getNgayNhap() {
		return ngayNhap;
	}

	public void setNgayNhap(LocalDateTime ngayNhap) {
		this.ngayNhap = ngayNhap;
	}

	public DonDatThuoc(String maDon, String maNV, String ncc, LocalDateTime ngayNhap) {
		super();
		this.maDon = maDon;
		this.maNV = maNV;
		this.ncc = ncc;
		this.ngayNhap = ngayNhap;
	}

	public DonDatThuoc(String maNV, String ncc, LocalDateTime ngayNhap) {
		super();
		this.maNV = maNV;
		this.ncc = ncc;
		this.ngayNhap = ngayNhap;
	}

	public DonDatThuoc(String maDon, String maNV, String ncc) {
		super();
		this.maDon = maDon;
		this.maNV = maNV;
		this.ncc = ncc;
	}

	
	
	
}