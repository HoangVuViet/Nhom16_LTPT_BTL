package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ChiTietHoaDon {
	@Id
	private String maHD;
	private String maSP;
	private int soLuong;
	private double thanhTienSP;
	public String getMaHD() {
		return maHD;
	}
	public void setMaHD(String maHD) {
		this.maHD = maHD;
	}
	public String getMaSP() {
		return maSP;
	}
	public void setMaSP(String maSP) {
		this.maSP = maSP;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public double getThanhTienSP() {
		return thanhTienSP;
	}
	public void setThanhTienSP(double thanhTienSP) {
		this.thanhTienSP = thanhTienSP;
	}
	public ChiTietHoaDon(String maHD, String maSP, int soLuong, double thanhTienSP) {
//		super();
		this.maHD = maHD;
		this.maSP = maSP;
		this.soLuong = soLuong;
		this.thanhTienSP = thanhTienSP;
	}
	@Override
	public String toString() {
		return "ChiTietHoaDon [maHD=" + maHD + ", maSP=" + maSP + ", soLuong=" + soLuong + ", thanhTienSP="
				+ thanhTienSP + "]";
	}
	
	
	
}