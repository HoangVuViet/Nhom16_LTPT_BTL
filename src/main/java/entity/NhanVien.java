package entity;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
@Entity
public class NhanVien {
	@Id
    private String maNV;
    private String hoTen;
    private String canCuocCD;
    private LocalDate ngaySinh;
    private boolean gioiTinh;
    private String diaChi;
    private String soDT;
    private double luong;
    private String tinhTrang;
    private LocalDate ngayVaoLam;
    private byte[] hinhNV; //hinh cua nhan vien
    private boolean isQuanLy;

    
	public String getTinhTrang() {
		return tinhTrang;
	}



	public void setTinhTrang(String tinhTrang) {
		this.tinhTrang = tinhTrang;
	}



	public boolean isQuanLy() {
		return isQuanLy;
	}



	public void setQuanLy(boolean isQuanLy) {
		this.isQuanLy = isQuanLy;
	}



	public String getEmployeeId() {
		return maNV;
	}
	


	public String getMaNV() {
		return maNV;
	}



	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}



	public void setEmployeeId(String employeeId) {
		this.maNV = employeeId;
	}



	public byte[] getImage() {
		return hinhNV;
	}



	public void setImage(byte[] image) {
		this.hinhNV = image;
	}



	public String getEmployeeCode() {
        return maNV;
    }


    public String getEmployeeName() {
        return hoTen;
    }

    public void setEmployeeName(String employeeName) {
        this.hoTen = employeeName;
    }

    public String getCitizenIdNumber() {
        return canCuocCD;
    }

    public void setCitizenIdNumber(String citizenIdNumber) {
        this.canCuocCD = citizenIdNumber;
    }

    public LocalDate getEmployeeBirthday() {
        return ngaySinh;
    }

    public void setEmployeeBirthday(LocalDate employeeBirthday) {
        this.ngaySinh = employeeBirthday;
    }

    public boolean isEmployeeSex() {
        return gioiTinh;
    }

    public void setEmployeeSex(boolean employeeSex) {
        this.gioiTinh = employeeSex;
    }

    public String getEmployeeLocation() {
        return diaChi;
    }

    public void setEmployeeLocation(String employeeLocation) {
        this.diaChi = employeeLocation;
    }

    public String getEmployeePhoneNumber() {
        return soDT;
    }

    public void setEmployeePhoneNumber(String employeePhoneNumber) {
        this.soDT = employeePhoneNumber;
    }



    public double getSalary() {
        return luong;
    }

    public void setSalary(double salary) {
        this.luong = salary;
    }

    public LocalDate getDateStart() {
        return ngayVaoLam;
    }

    public void setDateStart(LocalDate dateStart) {
        this.ngayVaoLam = dateStart;
    }
    
    
    
	

	public NhanVien(String hoTen, String canCuocCD, LocalDate ngaySinh, boolean gioiTinh, String diaChi, String soDT,
			double luong, String tinhTrang, LocalDate ngayVaoLam, byte[] hinhNV, boolean isQuanLy) {
//		super();
		this.hoTen = hoTen;
		this.canCuocCD = canCuocCD;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
		this.diaChi = diaChi;
		this.soDT = soDT;
		this.luong = luong;
		this.tinhTrang = tinhTrang;
		this.ngayVaoLam = ngayVaoLam;
		this.hinhNV = hinhNV;
		this.isQuanLy = isQuanLy;
	}



	public NhanVien(String maNV, String hoTen, String canCuocCD, LocalDate ngaySinh, boolean gioiTinh, String diaChi,
			String soDT, double luong, String tinhTrang, LocalDate ngayVaoLam, byte[] hinhNV, boolean isQuanLy) {
//		super();
		this.maNV = maNV;
		this.hoTen = hoTen;
		this.canCuocCD = canCuocCD;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
		this.diaChi = diaChi;
		this.soDT = soDT;
		this.luong = luong;
		this.tinhTrang = tinhTrang;
		this.ngayVaoLam = ngayVaoLam;
		this.hinhNV = hinhNV;
		this.isQuanLy = isQuanLy;
	}



	public NhanVien() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public NhanVien(String employeeId) {
		super();
		this.maNV = employeeId;
	}



	@Override
	public int hashCode() {
		return Objects.hash(maNV);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NhanVien other = (NhanVien) obj;
		return Objects.equals(maNV, other.maNV);
	}



	@Override
	public String toString() {
		return "NhanVien [maNV=" + maNV + ", hoTen=" + hoTen + ", canCuocCD=" + canCuocCD + ", ngaySinh=" + ngaySinh
				+ ", gioiTinh=" + gioiTinh + ", diaChi=" + diaChi + ", soDT=" + soDT + ", luong=" + luong
				+ ", ngayVaoLam=" + ngayVaoLam + ", hinhNV=" + Arrays.toString(hinhNV) + ", isQuanLy=" + isQuanLy + "]";
	}

	

	
    
}
