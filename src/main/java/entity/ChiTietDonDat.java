package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.ArrayList;
@Entity
public class ChiTietDonDat {
	@Id
	private String maDondat;
	private String maThuoc;
	private int soLuong;
	private String tenThuoc;
	private double giaNhap;
	private double thanhtien;
	
	public ChiTietDonDat() {
	}

	public ChiTietDonDat(String maThuoc, int soLuong, String tenThuoc, double giaNhap) {
		super();
		this.maThuoc = maThuoc;
		this.soLuong = soLuong;
		this.tenThuoc = tenThuoc;
		this.giaNhap = giaNhap;
	}

	public double getThanhtien() {
		return soLuong * giaNhap;
	}

	public ChiTietDonDat(String maDondat, String maThuoc, int soLuong, String tenThuoc, double giaNhap) {
		super();
		this.maDondat = maDondat;
		this.maThuoc = maThuoc;
		this.soLuong = soLuong;
		this.tenThuoc = tenThuoc;
		this.giaNhap = giaNhap;
	}


	public String getMaDondat() {
		return maDondat;
	}

	public ChiTietDonDat(int soLuong, String tenThuoc, double giaNhap, double thanhtien) {
		super();
		this.soLuong = soLuong;
		this.tenThuoc = tenThuoc;
		this.giaNhap = giaNhap;
		this.thanhtien = thanhtien;
	}

	public void setMaDondat(String maDondat) {
		this.maDondat = maDondat;
	}


	public String getMaThuoc() {
		return maThuoc;
	}


	public void setMaThuoc(String maThuoc) {
		this.maThuoc = maThuoc;
	}


	public int getSoLuong() {
		return soLuong;
	}


	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}


	public String getTenThuoc() {
		return tenThuoc;
	}


	public void setTenThuoc(String tenThuoc) {
		this.tenThuoc = tenThuoc;
	}


	public double getGiaNhap() {
		return giaNhap;
	}


	public void setGiaNhap(double giaNhap) {
		this.giaNhap = giaNhap;
	}

	
	
	
}
