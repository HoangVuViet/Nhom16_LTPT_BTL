package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class LoaiThuoc {
	@Id
	private int id;
	private String loaiThuoc;
	
	public LoaiThuoc() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoaiThuoc() {
		return loaiThuoc;
	}

	public void setLoaiThuoc(String loaiThuoc) {
		this.loaiThuoc = loaiThuoc;
	}

	public LoaiThuoc(int id, String loaiThuoc) {
		super();
		this.id = id;
		this.loaiThuoc = loaiThuoc;
	}

	public LoaiThuoc(String loaiThuoc) {
		super();
		this.loaiThuoc = loaiThuoc;
	}

	@Override
	public String toString() {
		return "LoaiThuoc [id=" + id + ", loaiThuoc=" + loaiThuoc + "]";
	}
	
}
