package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Date;
import java.util.Arrays;

@Entity
public class Thuoc {
	@Id
	private String maThuoc;
	private String tenThuoc;
	private double gia;
	private int soLuong;
	private DonViTinh donViTinh;
	private int hanSD;//tháng
	private byte[] hinhMinhHoa;
	private LoaiThuoc loaiThuoc; //loaiThuoc
	private String moTa;
	private double giaNhap;
	private Date ngaySanXuat;

	
	
	public Thuoc(String tenThuoc, double gia, int soLuong, DonViTinh donViTinh, int hanSD, byte[] hinhMinhHoa,
			LoaiThuoc loaiThuoc, String moTa, double giaNhap, Date ngaySanXuat) {
//		super();
		this.tenThuoc = tenThuoc;
		this.gia = gia;
		this.soLuong = soLuong;
		this.donViTinh = donViTinh;
		this.hanSD = hanSD;
		this.hinhMinhHoa = hinhMinhHoa;
		this.loaiThuoc = loaiThuoc;
		this.moTa = moTa;
		this.giaNhap = giaNhap;
		this.ngaySanXuat = ngaySanXuat;
	}

	public Thuoc(String maThuoc, String tenThuoc, double gia, int soLuong, DonViTinh donViTinh, int hanSD,
			byte[] hinhMinhHoa, LoaiThuoc loaiThuoc, String moTa, double giaNhap, Date ngaySanXuat) {
//		super();
		this.maThuoc = maThuoc;
		this.tenThuoc = tenThuoc;
		this.gia = gia;
		this.soLuong = soLuong;
		this.donViTinh = donViTinh;
		this.hanSD = hanSD;
		this.hinhMinhHoa = hinhMinhHoa;
		this.loaiThuoc = loaiThuoc;
		this.moTa = moTa;
		this.giaNhap = giaNhap;
		this.ngaySanXuat = ngaySanXuat;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public Thuoc() {
	// TODO Auto-generated constructor stub
	}
	
	
	public void setGiaNhap(double giaNhap) {
		this.giaNhap = giaNhap;
	}
	public double getGiaNhap() {
		return giaNhap;
	}
	public void setNgaySanXuat(Date ngaySanXuat) {
		this.ngaySanXuat = ngaySanXuat;
	}
	public Date getNgaySanXuat() {
		return ngaySanXuat;
	}
	
	public Thuoc(String maThuoc) {
		this.maThuoc = maThuoc;
	}

	public String getMaThuoc() {
		return maThuoc;
	}

	public void setMaThuoc(String maThuoc) {
		this.maThuoc = maThuoc;
	}

	public String getTenThuoc() {
		return tenThuoc;
	}

	public void setTenThuoc(String tenThuoc) {
		this.tenThuoc = tenThuoc;
	}

	public double getGia() {
		return gia;
	}

	public void setGia(double gia) {
		this.gia = gia;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public DonViTinh getDonViTinh() {
		return donViTinh;
	}

	public void setDonViTinh(DonViTinh donViTinh) {
		this.donViTinh = donViTinh;
	}

	public int getHanSD() {
		return hanSD;
	}

	public void setHanSD(int hanSD) {
		this.hanSD = hanSD;
	}

	public byte[] getHinhMinhHoa() {
		return hinhMinhHoa;
	}

	public void setHinhMinhHoa(byte[] hinhMinhHoa) {
		this.hinhMinhHoa = hinhMinhHoa;
	}

	public LoaiThuoc getLoaiThuoc() {
		return loaiThuoc;
	}

	public void setLoaiThuoc(LoaiThuoc loaiThuoc) {
		this.loaiThuoc = loaiThuoc;
	}

	@Override
	public String toString() {
		return "Thuoc [maThuoc=" + maThuoc + ", tenThuoc=" + tenThuoc + ", gia=" + gia + ", soLuong=" + soLuong
				+ ", donViTinh=" + donViTinh + ", hanSD=" + hanSD + ", hinhMinhHoa=" + Arrays.toString(hinhMinhHoa)
				+ ", loaiThuoc=" + loaiThuoc + ", moTa=" + moTa + giaNhap + " gia nhap = ," +
				ngaySanXuat + " ngay sx = " + "]";
	}	
	
	
	
}
