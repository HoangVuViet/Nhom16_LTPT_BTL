package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;
@Entity
public class KhachHang {
	@Id
    private String maKH;
    private LocalDate ngaySinh;
    private String tenKH;
    private boolean gioiTinh;
    private String email;
    private String PhoneNumber;
    private String Location;
	public String getMaKH() {
		return maKH;
	}
	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}
	public LocalDate getNgaySinh() {
		return ngaySinh;
	}
	public void setNgaySinh(LocalDate ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	public String getTenKH() {
		return tenKH;
	}
	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}
	public boolean isGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public String getSDT() {
		return PhoneNumber;
	}
	public void setSDT(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	public String getDiachi() {
		return Location;
	}
	public void setDiachi(String location) {
		Location = location;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
    
    public KhachHang(LocalDate ngaySinh, String tenKH, boolean gioiTinh, String email, String phoneNumber,
			String location) {
//		super();
		this.ngaySinh = ngaySinh;
		this.tenKH = tenKH;
		this.gioiTinh = gioiTinh;
		this.email = email;
		this.PhoneNumber = phoneNumber;
		this.Location = location;
	}
	public KhachHang(String maKH, LocalDate ngaySinh, String tenKH, boolean gioiTinh, String email, String phoneNumber,
			String location) {
//		super();
		this.maKH = maKH;
		this.ngaySinh = ngaySinh;
		this.tenKH = tenKH;
		this.gioiTinh = gioiTinh;
		this.email = email;
		this.PhoneNumber = phoneNumber;
		this.Location = location;
	}
	public KhachHang() {
		// TODO Auto-generated constructor stub
	}
	public KhachHang(String maKH) {
//		super();
		this.maKH = maKH;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(maKH);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KhachHang other = (KhachHang) obj;
		return maKH == other.maKH;
	}
	@Override
	public String toString() {
		return "KhachHang [maKH=" + maKH + ", ngaySinh=" + ngaySinh + ", tenKH=" + tenKH + ", gioiTinh=" + gioiTinh
				+ ", email=" + email + ", PhoneNumber=" + PhoneNumber + ", Location=" + Location + "]";
	}
	
    
    
    

}