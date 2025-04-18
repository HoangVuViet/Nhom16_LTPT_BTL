CREATE DATABASE QLiNhaThuoc
GO
USE QLiNhaThuoc
GO
--CREATE TABLE
CREATE TABLE NhaCungCap (
	MaNCC VARCHAR(10) NOT NULL PRIMARY KEY ,
	TenNCC NVARCHAR(50) not null,
	SoDT VARCHAR (10) not null,
	DiaChi NVARCHAR(50) not null,
) 
CREATE TABLE KhachHang (
	MaKH VARCHAR(20) NOT NULL PRIMARY KEY ,
	TenKH NVARCHAR(50) not null,
	GioiTinh BIT not null,
	NgaySinh DATE,
	SoDT VARCHAR(10) not null,
	DiaChi NVARCHAR(50),
	Email NVARCHAR(50),
) 
CREATE TABLE NhanVien (
	MaNV VARCHAR(10) NOT NULL PRIMARY KEY ,
	TenNV NVARCHAR(50) not null,
	GioiTinh bit not null,
	Luong float not null,
	NgaySinh DATE not null,
	SoDT VARCHAR(10) not null,
	TinhTrang varchar(20) not null,
	CCCD NVARCHAR(14) not null,
	DiaChi NVARCHAR(50),
	NgayVaoLam date not null,
	IsQuanLy BIT DEFAULT 0,
	HinhAnh VARBINARY(MAX)
)
CREATE TABLE HoaDon (
	MaHD varchar(20) NOT NULL primary key ,
	MaKH VARCHAR(20) NOT NULL,
	MaNV VARCHAR(10) NOT NULL,
	ThoiGianTao DATETIME,
	TongTien float not null,
	TienNhan float not null,
	PhuongThucTT nvarchar(20) not null,
) 

CREATE TABLE Thuoc
(
	stt int not null identity(1,1),
	MaThuoc VARCHAR(20) NOT NULL PRIMARY KEY,
	TenThuoc NVARCHAR(30)  not null,
	GiaBan float not null,
	DonViTinh int not null,
	LoaiThuoc int not null,
	MoTaChucNang nvarchar(50),
	SoLuongTon int not null,
	HanSuDung int not null,
	HinhMinhHoa varbinary(max),
	giaNhap float not null,
	ngaySanXuat date not null
)
CREATE TABLE LoaiThuoc
(
	Id int not null identity(1,1) PRIMARY KEY,
	LoaiThuoc nvarchar(50) not null
)
CREATE TABLE DonVi
(
	maDonVi int not null identity(1,1) PRIMARY KEY,
	tenDonVi nvarchar(50) not null
)

CREATE TABLE DonNhapThuoc (
	MaDonNhap varchar(20) NOT NULL PRIMARY KEY,
	MaNV VARCHAR(10) NOT NULL,
	MaNCC VARCHAR(10) NOT NULL,
	NgayNhap DateTime,
)
CREATE TABLE ChiTietHD (
	MaHD varchar(20) NOT NULL,
	MaThuoc VARCHAR(20) NOT NULL,
	SoLuong INT not null,
	ThanhTien float not null,
	
)
CREATE TABLE TaiKhoan (
	TaiKhoan VARCHAR(20) PRIMARY KEY NOT NULL ,
	MatKhau VARCHAR(20) NOT NULL ,
	MaNV VARCHAR(10),
	
)
CREATE TABLE ChiTietDonNhap (
	MaDonNhap varchar(20) NOT NULL,
	MaThuoc VARCHAR(20) NOT NULL,
	SoLuongNhap INT not null,
	TenThuoc NVARCHAR(30)  not null,
	giaNhap float not null,
) 
Alter table TaiKhoan add foreign key(MaNV) references NhanVien(MaNV)
Alter table Thuoc add foreign key(DonViTinh) references DonVi(maDonVi)
Alter table Thuoc add foreign key(LoaiThuoc) references LoaiThuoc(Id)
Alter table HoaDon add  foreign key(MaNV) references NhanVien (MaNV) 
Alter table HoaDon add  foreign key(MaKH) references KhachHang (MaKH) 
Alter table DonNhapThuoc add  foreign key(MaNCC) references NhaCungCap (MaNCC) 
Alter table DonNhapThuoc add  foreign key(MaNV) references NhanVien (MaNV) 
ALTER TABLE ChiTietDonNhap ADD FOREIGN KEY (MaThuoc) REFERENCES Thuoc(MaThuoc)
Alter table ChiTietDonNhap add  foreign key(MaDonNhap) references DonNhapThuoc (MaDonNhap)
Alter table ChiTietHD add  foreign key(MaThuoc) references Thuoc (MaThuoc) 
Alter table ChiTietHD add  foreign key(MaHD) references HoaDon (MaHD)

go
CREATE FUNCTION dbo.Generate_MaThuoc ()
RETURNS VARCHAR(20)
AS
BEGIN
    DECLARE @NextId INT;
    DECLARE @NewId VARCHAR(20);

    -- Lấy ra giá trị cuối cùng của MA_THUOC
    SELECT @NextId = ISNULL(MAX(CAST(SUBSTRING(MaThuoc, 3, LEN(MaThuoc) - 2) AS INT)), 0) + 1
    FROM Thuoc;

    -- Tạo giá trị mới cho MA_THUOC
    SET @NewId = 'TH' + RIGHT('00' + CAST(@NextId AS VARCHAR(10)), 3);

    RETURN @NewId;
END;
go


CREATE TRIGGER TRG_THUOC_INSERT
ON Thuoc
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @NewId VARCHAR(20);

    -- Lặp qua các bản ghi được chèn vào
    DECLARE @Inserted TABLE (TenThuoc NVARCHAR(30), GiaBan FLOAT, DonViTinh NVARCHAR(10), LoaiThuoc int, MoTaChucNang NVARCHAR(50), SoLuongTon INT, HanSuDung INT, HinhMinhHoa VARBINARY(MAX), giaNhap float, ngaySanxuat date);
    INSERT INTO @Inserted
    SELECT TenThuoc, GiaBan, DonViTinh, LoaiThuoc, MoTaChucNang, SoLuongTon, HanSuDung, HinhMinhHoa, giaNhap,ngaySanXuat
    FROM inserted; 

    -- Tạo mã thuốc mới
    SELECT @NewId = dbo.Generate_MaThuoc();

    -- Chèn dữ liệu vào bảng với mã thuốc mới
    INSERT INTO THUOC (MaThuoc, TenThuoc, GiaBan, DonViTinh, LoaiThuoc, MoTaChucNang, SoLuongTon, HanSuDung, HinhMinhHoa, giaNhap,ngaySanXuat)
    SELECT @NewId, TenThuoc, GiaBan, DonViTinh, LoaiThuoc, MoTaChucNang, SoLuongTon, HanSuDung, HinhMinhHoa, giaNhap,ngaySanXuat
    FROM @Inserted;
END;
go-- Hàm tạo mã mới cho NHACUNGCAP
CREATE FUNCTION dbo.Generate_MaNCC ()
RETURNS VARCHAR(20)
AS
BEGIN
    DECLARE @NextId INT;
    DECLARE @NewId VARCHAR(10);

    SELECT @NextId = ISNULL(MAX(CAST(SUBSTRING(MaNCC, 4, LEN(MaNCC) - 3) AS INT)), 0) + 1
    FROM NhaCungCap;

    SET @NewId = 'NCC' + RIGHT('0000' + CAST(@NextId AS VARCHAR(10)), 4);

    RETURN @NewId;
END;
GO

-- Hàm tạo mã mới cho KHACHHANG
CREATE FUNCTION dbo.Generate_MaKHACHHANG ()
RETURNS VARCHAR(20)
AS
BEGIN
    DECLARE @NextId INT;
    DECLARE @NewId VARCHAR(20);

    SELECT @NextId = ISNULL(MAX(CAST(SUBSTRING(MaKH, 3, LEN(MaKH) - 2) AS INT)), 0) + 1
    FROM KhachHang;

    SET @NewId = 'KH' + RIGHT('0000' + CAST(@NextId AS VARCHAR(20)), 4);

    RETURN @NewId;
END;
GO

-- Hàm tạo mã mới cho NHANVIEN
CREATE FUNCTION dbo.Generate_MaNHANVIEN ()
RETURNS VARCHAR(10)
AS
BEGIN
    DECLARE @NextId INT;
    DECLARE @NewId VARCHAR(10);

    SELECT @NextId = ISNULL(MAX(CAST(SUBSTRING(MaNV, 3, LEN(MaNV) - 2) AS INT)), 0) + 1
    FROM NhanVien;

    SET @NewId = 'NV' + RIGHT('0000' + CAST(@NextId AS VARCHAR(10)), 4);

    RETURN @NewId;
END;
GO

-- Hàm tạo mã mới cho HOADON

CREATE FUNCTION dbo.Generate_MaHOADON()
RETURNS VARCHAR(20)
AS
BEGIN
    DECLARE @NextId INT;
    DECLARE @NewId VARCHAR(20);

    SELECT @NextId = ISNULL(MAX(CAST(SUBSTRING(MaHD, 3, LEN(MaHD) - 2) AS INT)), 0) + 1
    FROM HoaDon

    SET @NewId = 'HD' + RIGHT('0000000' + CAST(@NextId AS VARCHAR(20)), 8);

    RETURN @NewId;
END;
GO

-- Hàm tạo mã mới cho DonNhapThuoc
CREATE FUNCTION dbo.Generate_MaDonNhap()
RETURNS VARCHAR(20)
AS
BEGIN
    DECLARE @NextId INT;
    DECLARE @NewId VARCHAR(20);

    SELECT @NextId = ISNULL(MAX(CAST(SUBSTRING(MaDonNhap, 3, LEN(MaDonNhap) - 2) AS INT)), 0) + 1
    FROM DonNhapThuoc;

    SET @NewId = 'DN' + RIGHT('0000000' + CAST(@NextId AS VARCHAR(20)), 8);

    RETURN @NewId;
END;
GO
-- Trigger để tự động tạo mã mới cho NHACUNGCAP
CREATE TRIGGER TRG_NHACUNGCAP_INSERT
ON NhaCungCap
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @NewId VARCHAR(10);

    SELECT @NewId = dbo.Generate_MaNCC();

    INSERT INTO NhaCungCap (MaNCC, TenNCC, SoDT, DiaChi)
    SELECT @NewId, TenNCC, SoDT, DiaChi
    FROM inserted;
END;
GO
CREATE TRIGGER TRG_KHACHHANG_INSERT
ON KhachHang
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @NewId VARCHAR(20);

    SELECT @NewId = dbo.Generate_MaKHACHHANG();

    INSERT INTO KhachHang (MaKH, TenKH, GioiTinh,NgaySinh, SoDT, DiaChi, Email)
    SELECT @NewId, TenKH, GioiTinh,NgaySinh, SoDT, DiaChi, Email
    FROM inserted;
END;
go
CREATE TRIGGER TRG_NHANVIEN_INSERT
ON NhanVien
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @NewId VARCHAR(10);

    SELECT @NewId = dbo.Generate_MaNHANVIEN();

    INSERT INTO NhanVien (MaNV, TenNV, GioiTinh,Luong, NgaySinh, SoDT, TinhTrang, CCCD, DiaChi, NgayVaoLam, IsQuanLy, HinhAnh)
    SELECT @NewId, TenNV, GioiTinh,Luong, NgaySinh, SoDT, TinhTrang, CCCD, DiaChi, NgayVaoLam, IsQuanLy, HinhAnh
    FROM inserted;
END;
go
CREATE TRIGGER TRG_HOADON_INSERT
ON HoaDon
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @NewId varchar(20);

    SELECT @NewId = dbo.Generate_MaHOADON();

    INSERT INTO HoaDon (MaHD, MaKH, MaNV, ThoiGianTao, TongTien, TienNhan,PhuongThucTT)
    SELECT @NewId, MaKH, MaNV, ThoiGianTao, TongTien,TienNhan,PhuongThucTT
    FROM inserted;
END;
go
go
CREATE TRIGGER TRG_DonNhapThuoc_INSERT
ON DonNhapThuoc
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @NewId VARCHAR(20);

    SELECT @NewId = dbo.Generate_MaDonNhap();

    INSERT INTO DonNhapThuoc (MaDonNhap, MaNV, MaNCC, NgayNhap)
    SELECT @NewId, MaNV, MaNCC, NgayNhap
    FROM inserted;
END;

--data Đơn vị
insert into DonVi(tenDonVi)
values(N'viên');
insert into DonVi(tenDonVi)
values(N'vỉ');
insert into DonVi(tenDonVi)
values(N'hộp');
--data Loại thuốc

INSERT INTO LoaiThuoc (LoaiThuoc)
VALUES (N'Thuốc sốt');
INSERT INTO LoaiThuoc (LoaiThuoc)
VALUES (N'Thuốc đau');
INSERT INTO LoaiThuoc (LoaiThuoc)
VALUES (N'Kháng sinh');


--data Thuốc

INSERT INTO Thuoc (TenThuoc, GiaBan, DonViTinh, LoaiThuoc, MoTaChucNang, SoLuongTon, HanSuDung, HinhMinhHoa, giaNhap, ngaySanXuat)
VALUES (N'Paracetamol', 5000, 1, 1, N'Uống không quá 2 viên 1 ngày', 100, 24, NULL, 4000, '2023-03-22');

INSERT INTO Thuoc (TenThuoc, GiaBan, DonViTinh, LoaiThuoc, MoTaChucNang, SoLuongTon, HanSuDung, HinhMinhHoa, giaNhap, ngaySanXuat)
VALUES (N'Ibuprofen', 8000, 2, 2, N'Uống sau khi ăn', 150, 24, NULL, 7000, '2023-04-04');

INSERT INTO Thuoc (TenThuoc, GiaBan, DonViTinh, LoaiThuoc, MoTaChucNang, SoLuongTon, HanSuDung, HinhMinhHoa, giaNhap, ngaySanXuat)
VALUES (N'Amoxicillin', 10000, 3, 3, N'Uống sau khi ăn', 80, 24, NULL, 8000, '2022-12-30');

--khách hàng data
INSERT INTO KhachHang (TenKH, GioiTinh,NgaySinh, SoDT, DiaChi, Email)
VALUES 
    (N'Nguyễn Đức Huy', 0, '2002-03-22', '0868661273', N'Hồ Chí Minnh ','ndhuy2203@gmail.com');
	INSERT INTO KhachHang (TenKH, GioiTinh,NgaySinh, SoDT, DiaChi, Email)
VALUES
    (N'Nguyễn Thị Mỹ Trinh', 1, '2003-02-06', '0987654321', N'Hồ Chí Minh', 'mytrinh2003@gmail.com');
	INSERT INTO KhachHang (TenKH, GioiTinh,NgaySinh, SoDT, DiaChi, Email)
VALUES
    (N'Vũ Việt Hoàng', 0, '2002-09-14', '0369876543', N'Nghệ An', 'hoangvuviet@gmail.com');

--data Nhà cung cấp
INSERT INTO NHACUNGCAP (TenNCC, SoDT, DiaChi)
VALUES 
    (N'H Medicine', '0123456789', N'Hà Nội');
	INSERT INTO NHACUNGCAP (TenNCC, SoDT, DiaChi)
VALUES 
    (N'Pharmacity', '0987654321', N'Hồ Chí Minh');
	INSERT INTO NHACUNGCAP (TenNCC, SoDT, DiaChi)
VALUES 
    (N'MediMart', '0369876543', N'Đà Nẵng');
--data nhân viên
INSERT INTO NhanVien ( TenNV, GioiTinh,Luong, NgaySinh, SoDT, TinhTrang, CCCD, DiaChi, NgayVaoLam, IsQuanLy, HinhAnh)
VALUES 
	(N'Trần Thị Hằng', 1,50000000, '1995-05-20', '0987654321', N'Đang thử việc', '12345678901235', N'Hồ Chí Minh', '2009-03-15', 1, NULL);
INSERT INTO NhanVien ( TenNV, GioiTinh,Luong, NgaySinh, SoDT, TinhTrang, CCCD, DiaChi, NgayVaoLam, IsQuanLy, HinhAnh)
VALUES 
    (N'Nguyễn Đức Long', 0, 5000000,'1990-01-15', '0123456789', N'Đang làm việc', '12345678901234', N'Hà Nội', '2010-05-10', 0, NULL);
INSERT INTO NhanVien ( TenNV, GioiTinh,Luong, NgaySinh, SoDT, TinhTrang, CCCD, DiaChi, NgayVaoLam, IsQuanLy, HinhAnh)
VALUES 
    (N'Lê Văn Tuấn', 0,6000000, '1988-08-10', '0369876543', N'Đã nghỉ việc', '12345678901236', N'Đà Nẵng', '2012-09-20', 0, NULL);
	--data tai khoan
insert into TaiKhoan (TaiKhoan, MatKhau, MaNV)
values
	('admin','admin','NV0001');
	insert into TaiKhoan (TaiKhoan, MatKhau, MaNV)
values
	('testnv','123456','NV0002');
-- thong ke tong doanh thu thuoc nhap

SELECT
	dnt.NgayNhap,
    ctdn.MaThuoc,
    ctdn.SoLuongNhap,
    ctdn.SoLuongNhap * t.GiaNhap AS TongTienNhap
FROM 
    DonNhapThuoc dnt
JOIN 
    ChiTietDonNhap ctdn ON dnt.MaDonNhap = ctdn.MaDonNhap
JOIN 
    Thuoc t ON ctdn.MaThuoc = t.MaThuoc
WHERE 
    cast(dnt.NgayNhap as date) BETWEEN '2024-04-08' AND '2024-05-26'
   --dnt.NgayNhap >= '2024-04-08' AND dnt.NgayNhap <= '2024-05-25'

---- thong ke tong doanh thu thuoc ban
SELECT 
	hd.ThoiGianTao,
	t.TenThuoc,
	t.DonViTinh,
	cthd.SoLuong,
	cthd.SoLuong * t.GiaBan AS TongTienBan 
FROM 
	HoaDon hd 
JOIN 
	ChiTietHD cthd ON hd.MaHD = cthd.MaHD 
JOIN 
	Thuoc t ON cthd.MaThuoc = t.MaThuoc 
WHERE 
	CAST(hd.ThoiGianTao as date) >= '2024-04-01' AND CAST(hd.ThoiGianTao as date) <= '2024-05-26'

--thong ke loi nhuan theo ngay
WITH TongTienBan AS (
    SELECT 
        CONVERT(date, ThoiGianTao) AS Ngay,
        SUM(TongTien) AS TongTienBan
    FROM 
        HoaDon
    GROUP BY 
        CONVERT(date, ThoiGianTao)
),
TongTienVon AS (
    SELECT 
        CONVERT(date, HD.ThoiGianTao) AS Ngay,
        SUM(CTHD.SoLuong * T.giaNhap) AS TongTienVon
    FROM 
        HoaDon HD
    JOIN 
        ChiTietHD CTHD ON HD.MaHD = CTHD.MaHD
    JOIN 
        Thuoc T ON CTHD.MaThuoc = T.MaThuoc
    GROUP BY 
        CONVERT(date, HD.ThoiGianTao)
)
SELECT 
    TongTienBan.Ngay,
    TongTienBan.TongTienBan,
    TongTienVon.TongTienVon,
	TongTienBan.TongTienBan - TongTienVon.TongTienVon as Loinhuan
FROM 
    TongTienBan
JOIN 
    TongTienVon ON TongTienBan.Ngay = TongTienVon.Ngay
where cast(TongTienBan.Ngay as date) >= '05-24-2024' and cast(TongTienBan.Ngay as date) <='05-26-2024'
----thống kê nhân viên bán chạy
select top 10 sum(TongTien) as TongTien,
		MaNV
from HoaDon
where cast(ThoiGianTao as date) >= '04-01-2024' and cast(ThoiGianTao as date) <= '05-26-2024'
group by MaNV
order by TongTien desc


select * from HoaDon
Select * from Thuoc
select * from LoaiThuoc

Select t.*,l.LoaiThuoc from Thuoc t
inner join LoaiThuoc l  on t.LoaiThuoc = l.Id
where l.LoaiThuoc like '%%'


select *
from DonVi d where d.maDonVi = 2