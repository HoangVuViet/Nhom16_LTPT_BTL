package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class DonViTinh {
	@Id
	private int maDonVi;
	private String tenDonVi;
	
	public DonViTinh() {
		// TODO Auto-generated constructor stub
	}

	public int getMaDonVi() {
		return maDonVi;
	}

	public void setMaDonVi(int maDonVi) {
		this.maDonVi = maDonVi;
	}

	public String getTenDonVi() {
		return tenDonVi;
	}

	public void setTenDonVi(String tenDonVi) {
		this.tenDonVi = tenDonVi;
	}

	public DonViTinh(int maDonVi, String tenDonVi) {
		super();
		this.maDonVi = maDonVi;
		this.tenDonVi = tenDonVi;
	}

	public DonViTinh(int maDonVi) {
		super();
		this.maDonVi = maDonVi;
	}

	public DonViTinh(String tenDonVi) {
		super();
		this.tenDonVi = tenDonVi;
	}
	
}
