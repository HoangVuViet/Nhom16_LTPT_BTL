package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class NhaCungCap {
	@Id
	private String maNCC;
	private String tenNCC;
	private String sdtNCC;
	private String diaChi;
	
	public NhaCungCap() {
		// TODO Auto-generated constructor stub
	}

	public NhaCungCap(String maNCC, String tenNCC, String sdtNCC, String diaChi) {
		super();
		this.maNCC = maNCC;
		this.tenNCC = tenNCC;
		this.sdtNCC = sdtNCC;
		this.diaChi = diaChi;
	}

	
	
	public NhaCungCap(String tenNCC, String sdtNCC, String diaChi) {
		super();
		this.tenNCC = tenNCC;
		this.sdtNCC = sdtNCC;
		this.diaChi = diaChi;
	}

	public String getMaNCC() {
		return maNCC;
	}

	public void setMaNCC(String maNCC) {
		this.maNCC = maNCC;
	}

	public String getTenNCC() {
		return tenNCC;
	}

	public void setTenNCC(String tenNCC) {
		this.tenNCC = tenNCC;
	}

	public String getSdtNCC() {
		return sdtNCC;
	}

	public void setSdtNCC(String sdtNCC) {
		this.sdtNCC = sdtNCC;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	@Override
	public String toString() {
		return "nha cc: "+maNCC+tenNCC+sdtNCC+diaChi ;
	}
}
