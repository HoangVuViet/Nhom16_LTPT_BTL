package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TaiKhoan {
	@Id
	private String maNV;
	private String tenDangNhap;
	private String matkhau;
	private String trangThai;
	
	public TaiKhoan() {
		// TODO Auto-generated constructor stub
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public TaiKhoan(String maNV, String tenDangNhap, String matkhau, String trangThai) {
		super();
		this.maNV = maNV;
		this.tenDangNhap = tenDangNhap;
		this.matkhau = matkhau;
		this.trangThai = trangThai;
	}

	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}

	public String getTenDangNhap() {
		return tenDangNhap;
	}

	public void setTenDangNhap(String tenDangNhap) {
		this.tenDangNhap = tenDangNhap;
	}

	public String getMatkhau() {
		return matkhau;
	}

	public void setMatkhau(String matkhau) {
		this.matkhau = matkhau;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Tai khoan: " + maNV + tenDangNhap + matkhau + trangThai;
	}
}
