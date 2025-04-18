package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;


import connect.ConnectDB;
import dao.ChiTietDonDatDao;
import dao.DonDatThuocDao;
import dao.DonViTinhDao;
import dao.LoaiThuocDao;
import dao.NhaCungCapDao;
import dao.ThuocDao;
import entity.ChiTietDonDat;
import entity.DonDatThuoc;
import entity.DonViTinh;
import entity.LoaiThuoc;
import entity.NhaCungCap;
import entity.NhanVien;
import entity.Thuoc;
import format.ImageIconCustom;
import format.PanelBorder;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.jdesktop.swingx.JXDatePicker;

public class FormNhapThuoc extends JFrame implements ActionListener, MouseListener {
	
	private PanelBorder pnlGiua,pnlDuoi, pnlHinhAnh;
	
	private JTextField txtMaNV, txtTongTien,txtGiaNhap,txtTenThuoc,txtDongia,txtHanSuDung,txtMoTaThuoc ;
	
	private JComboBox<String> cboNCC,cboDonvi,cboLoaiThuoc;
	
	private JButton btnThem, btnXoa,btnSua,btnDoiHinhanh,btnTaoDonNhap ;
	
	private DefaultTableModel tblModelDonNhap;
	private JTable tblDonNhap;
	
	private JLabel lblTongtien,lblHinhanhThuoc;
	
	private byte[] hinhThuocByte = null; 
	
	private ThuocDao thuocDao;
	private NhaCungCapDao nhaCCDao;
	private DonDatThuocDao donDatDao;
	private ChiTietDonDatDao chiTietDonDatDao;
	private LoaiThuocDao loaiThuocDao;
	
	private double thanhTien = 0, tongTien = 0;

	private JSpinner spnSoLuongThuoc;
	private JButton btnxoaTrang;
	private String fileName = null;

	private JXDatePicker ngaySanxuat;

	private DecimalFormat formatTien;

	private ArrayList<NhaCungCap> lstNcc;
	
	private ArrayList<String> maThuoc;

	private SimpleDateFormat dateFormat;
	
	private FormThuoc frmThuoc;

	private DonViTinhDao donviDao;

	private String tongTienChuoi;

	private JButton btnThemDonVi;

	private JButton btnThemLoaiThuoc;
	
	public FormNhapThuoc(NhanVien nv,FormThuoc frmThuoc ) throws SQLException {
		this.frmThuoc = frmThuoc;
	dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	maThuoc = new ArrayList<String>();
	formatTien = new DecimalFormat();
	try {
		ConnectDB.getInstance().connectDataBase();
		System.out.println("Kết nối thành công SQL với FormThuoc");
	}catch (SQLException e) {
		e.printStackTrace();
	}
	//khởi tạo 
	thuocDao = new ThuocDao();
	nhaCCDao = new NhaCungCapDao();
	donDatDao= new DonDatThuocDao();
	chiTietDonDatDao = new ChiTietDonDatDao();
	loaiThuocDao = new LoaiThuocDao();
	donviDao = new DonViTinhDao();
	
	setSize(1200,600);
	setLocationRelativeTo(null);
	
	pnlGiua = new PanelBorder();
	pnlDuoi = new PanelBorder();
	
	setLayout(new BorderLayout());
	
	//pnlImage
    lblHinhanhThuoc = new JLabel();
    lblHinhanhThuoc.setIcon(new ImageIconCustom("/images/drugstore.png", 130, 130));
    
    btnDoiHinhanh = new JButton();
    btnDoiHinhanh.setText("Đổi Ảnh");
   
    pnlHinhAnh = new PanelBorder();
    pnlHinhAnh.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
    pnlHinhAnh.setBackground(new Color(250, 250, 250));
    pnlHinhAnh.add(lblHinhanhThuoc);
    pnlHinhAnh.add(btnDoiHinhanh);
    GroupLayout layout1 = new GroupLayout(pnlHinhAnh);
    pnlHinhAnh.setLayout(layout1);

    layout1.setHorizontalGroup(
        layout1.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addGroup(layout1.createSequentialGroup()
                .addComponent(lblHinhanhThuoc)
                .addGap(10) 
                .addComponent(btnDoiHinhanh))
    );

    layout1.setVerticalGroup(
        layout1.createSequentialGroup()
            .addGroup(layout1.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(lblHinhanhThuoc)
                .addComponent(btnDoiHinhanh))
    );
	//pnlTren
    pnlGiua.setBorder(new TitledBorder("Quản lý thuốc"));
    pnlGiua.setBackground(new Color(250, 250, 250));
	 //pnlTren.setPreferredSize(new Dimension(getWidth(), 100));
	add(pnlGiua, BorderLayout.CENTER);
	Box b = Box.createVerticalBox();
    b.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // Padding
    Box a, b5, b1, b2, b3, b51,b6;;
    a = Box.createHorizontalBox();
    a.add(pnlHinhAnh);
    a.add(b);
    b.add(Box.createVerticalStrut(15));

    b.add(b1 = Box.createHorizontalBox());
    b.add(Box.createVerticalStrut(10));
    JLabel lblTenThuoc;
    JLabel lblNCC;
    b1.add(lblTenThuoc = new JLabel("Tên thuốc: "));
    b1.add(txtTenThuoc = new JTextField());
    JLabel lblGiaBan;
    b1.add(lblGiaBan = new JLabel("Giá bán: "));
    b1.add(txtDongia = new JTextField());

    b.add(b2 = Box.createHorizontalBox());
    b.add(Box.createVerticalStrut(15));
    ngaySanxuat = new JXDatePicker();
    JLabel lblNgaySX;
    b2.add(lblNgaySX = new JLabel("Ngày sản xuất: "));
    b2.add(ngaySanxuat);JLabel lblHanSuDung = new JLabel("Hạn sử dụng (Tháng): ");
    b2.add(lblHanSuDung);
    b2.add(txtHanSuDung = new JTextField());

    JLabel lblLoaiThuoc;
    
    // Tạo và đổ dữ liệu vào comboBox
    b.add(b3 = Box.createHorizontalBox());
    b.add(Box.createVerticalStrut(15));
    JLabel lblDonvi = new JLabel("Đơn vị: ");
    b3.add(lblDonvi);
    b3.add(cboDonvi = new JComboBox<String>());
    b3.add(Box.createHorizontalStrut(20)); 
    
    ImageIcon iconThem = new ImageIconCustom("/images/plus.png", 16, 16);
    b3.add(btnThemDonVi = new JButton(iconThem));
    
    b3.add(lblLoaiThuoc = new JLabel("Loại thuốc: "));
    b3.add(cboLoaiThuoc = new JComboBox<String>());
    b3.add(btnThemLoaiThuoc = new JButton(iconThem));
    HashSet<String> loaiThuocSet = new HashSet<String>();
    
    //Thêm loại thuốc vào cbo bõx
    ArrayList<LoaiThuoc> lstLoai = loaiThuocDao.timTatCaLoaiThuoc();
    for(LoaiThuoc l : lstLoai) {
    	String tenLoai = l.getLoaiThuoc();
    	cboLoaiThuoc.addItem(tenLoai);
    }
    
    HashSet<String> donViSet = new HashSet<String>();
    ArrayList<DonViTinh> lstDonvi = donviDao.timTatCaDonViThuoc();
    for(DonViTinh d : lstDonvi) {
    	String donvi = d.getTenDonVi();
    	cboDonvi.addItem(donvi);
    }
    
    
    b.add(b5 = Box.createHorizontalBox());
    b.add(Box.createVerticalStrut(15));
    b5.add(new JLabel("Giá nhập: "));
    b5.add(Box.createHorizontalStrut(20));
    b5.add(txtGiaNhap = new JTextField());
    JLabel lblMoTaThuoc = new JLabel("Mô tả: ");
    b5.add(lblMoTaThuoc);
    b.add(Box.createVerticalStrut(15));
    b5.add(txtMoTaThuoc = new JTextField());
    b5.add(Box.createHorizontalStrut(15));
    
    b.add(b51 = Box.createHorizontalBox());
    b.add(Box.createVerticalStrut(15));
    b51.add(new JLabel("Số lượng: "));
    b51.add(Box.createHorizontalStrut(20));
    SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, 1000, 1);
    spnSoLuongThuoc = new JSpinner(spinnerModel);
    b51.add(spnSoLuongThuoc);
    b51.add(Box.createHorizontalStrut(15));
    
    b.add(b6 = Box.createHorizontalBox());
    b6.add(btnThem = new JButton("Thêm"));
    b6.add(Box.createHorizontalStrut(15));
    b6.add(btnSua = new JButton("Sửa"));
    b6.add(Box.createHorizontalStrut(15));
    b6.add(btnxoaTrang = new JButton("Xoá trắng"));
    b6.add(Box.createHorizontalStrut(15));
    
    lblMoTaThuoc.setPreferredSize(lblNgaySX.getPreferredSize());
    lblTenThuoc.setPreferredSize(lblNgaySX.getPreferredSize());
    lblDonvi.setPreferredSize(lblNgaySX.getPreferredSize());
    
    pnlGiua.setLayout(new GridBagLayout());
	GridBagConstraints constraints = new GridBagConstraints();
	constraints.fill = GridBagConstraints.BOTH; 
	constraints.weightx = 1.0; 
	constraints.weighty = 1.0; 

	pnlGiua.add(a, constraints);
	
	//pnlDuoi
    pnlDuoi.setLayout(new GridBagLayout());pnlDuoi.setPreferredSize(new Dimension(getWidth(), 300));
    pnlDuoi.setBorder(new TitledBorder("ĐƠN NHẬP"));
    add(pnlDuoi, BorderLayout.SOUTH);

    GridBagConstraints gbcDuoi = new GridBagConstraints();
    gbcDuoi.fill = GridBagConstraints.BOTH;
    gbcDuoi.weightx = 1;
    gbcDuoi.weighty = 1;

    Box bDuoiTong, bDuoiTrai, bDuoiPhai;
    bDuoiTong = Box.createHorizontalBox();
    pnlDuoi.add(bDuoiTong, gbcDuoi);
    bDuoiTong.add(bDuoiTrai = Box.createVerticalBox());
    String[] headerDonNhap = {"Tên sản phẩm", "Đơn vị", "Loại thuốc", "Số lượng", "Ngày sản xuất","HSD","Mô tả" ,"Giá nhập","Giá bán", "Thành tiền"};
    tblModelDonNhap = new DefaultTableModel(headerDonNhap, 0);
    tblDonNhap = new JTable(tblModelDonNhap);
    JScrollPane scrDonNhap = new JScrollPane(tblDonNhap);
    bDuoiTrai.add(scrDonNhap);

    bDuoiTong.add(bDuoiPhai = Box.createVerticalBox());

    //pnlDuoi thanh toán
    Box ttDau, tt0,tt1, tt2, tt3, tt4;
    bDuoiPhai.add(Box.createVerticalStrut(15));
    bDuoiPhai.add(ttDau = Box.createHorizontalBox());
    bDuoiPhai.add(Box.createVerticalStrut(15)); 
    ttDau.add(lblNCC = new JLabel("Tên NCC: "));
    ttDau.add(cboNCC = new JComboBox<String>());
    lstNcc = new ArrayList<NhaCungCap>();
    lstNcc = nhaCCDao.layTatCaNCC();
    for(NhaCungCap ncc : lstNcc) {
    	cboNCC.addItem(ncc.getTenNCC());
    }
    bDuoiPhai.add(Box.createVerticalStrut(15));
    bDuoiPhai.add(tt0 = Box.createHorizontalBox());
    bDuoiPhai.add(Box.createVerticalStrut(15)); 
	tt0.add(new JLabel("Nhân viên: ")); 
	tt0.add(txtMaNV = new JTextField()); txtMaNV.setBorder(null);
	txtMaNV.setEditable(false); 
	txtMaNV.setText(nv.getEmployeeId());
    bDuoiPhai.add(tt1 = Box.createHorizontalBox());
    tt1.add(btnXoa = new JButton("Xoá"));
    tt1.add(btnTaoDonNhap = new JButton("Tạo đơn"));
    
    bDuoiPhai.add(Box.createVerticalStrut(15)); 
    bDuoiPhai.add(tt1 = Box.createHorizontalBox());
    lblTongtien = new JLabel("Tổng tiền: ");
    lblTongtien.setFont(new Font("SansSerif", Font.BOLD, 20));
    tt1.add(lblTongtien);
    tt1.add(txtTongTien = new JTextField());
    txtTongTien.setOpaque(false);
    txtTongTien.setEditable(false);
    txtTongTien.setFont(new Font("SansSerif", Font.BOLD, 20));

    GridBagConstraints gbcDuoiTrai = new GridBagConstraints();
    gbcDuoiTrai.fill = GridBagConstraints.BOTH;
    gbcDuoiTrai.weightx = 9; 

    pnlDuoi.add(bDuoiTrai, gbcDuoiTrai);

    GridBagConstraints gbcDuoiPhai = new GridBagConstraints();
    gbcDuoiPhai.fill = GridBagConstraints.BOTH;
    gbcDuoiPhai.weightx = 1; 

    pnlDuoi.add(bDuoiPhai, gbcDuoiPhai);

	//sự kiện
    btnDoiHinhanh.addActionListener(this);
	btnSua.addActionListener(this);btnThem.addActionListener(this);
	btnXoa.addActionListener(this);
	btnxoaTrang.addActionListener(this);
	btnTaoDonNhap.addActionListener(this);
	tblDonNhap.addMouseListener(this);
	btnThemDonVi.addActionListener(this);
	btnThemLoaiThuoc.addActionListener(this);
}

	public void tinhTongTienDH() throws SQLException {
//    	int columnIndex = 9; // Chỉ số cột "ThanhTien"
        ArrayList<Double> listTienSP=new ArrayList<Double>();
        Double tong=0.0;
        if(tblDonNhap.getRowCount()>0) {
        	for (int row = 0; row < tblModelDonNhap.getRowCount(); row++) {
            	Double sum = 0.0;
                Double value =Double.parseDouble(tblModelDonNhap.getValueAt(row, 7).toString());
                int soLuong=Integer.parseInt(tblModelDonNhap.getValueAt(row, 3).toString());
                if (value instanceof Double) {
                    sum = soLuong* value;
                }
                listTienSP.add(sum);
            }
            for(int i=0;i<tblDonNhap.getRowCount();i++) {
            	tblModelDonNhap.setValueAt(listTienSP.get(i), i, 9);
            }
            for(Double d:listTienSP) {
            	tong=tong+d;
            }
        }
        tongTien=tong;
        txtTongTien.setText(tongTien+"");
    }
	
@Override
public void actionPerformed(ActionEvent e) {
	Object o = e.getSource();
	if(o.equals(btnThem)) {
		if (! kiemTraDuLieuRong()) {
			return;
		}else 
			if(duLieuHopLe()) {
				kiemTraDuLieuRong();
				LocalDate ngaySanXuat = ngaySanxuat.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				String tenThuoc = txtTenThuoc.getText();
				Double giaBan = Double.parseDouble(txtDongia.getText());
				int hsd = Integer.parseInt(txtHanSuDung.getText().trim());
				String donVi = (String)cboDonvi.getSelectedItem();
				String loaiThuoc = (String)cboLoaiThuoc.getSelectedItem();
				String moTa = txtMoTaThuoc.getText();
				Double giaNhap = Double.parseDouble(txtGiaNhap.getText().toString());
				int soLuong = (Integer)spnSoLuongThuoc.getValue();
				//tạo điều kiện k đc trùng tên,loại,đơn vị
				for(int r=0; r < tblDonNhap.getRowCount(); r++ ) {
					if(tenThuoc.equals(tblDonNhap.getValueAt(r, 0).toString())) {
						JOptionPane.showMessageDialog(this, "Tên thuốc đã được nhập, vui lòng nhập thuốc khác");
						txtTenThuoc.selectAll();
						txtTenThuoc.requestFocus();
						return;
					}
				}
				
				thanhTien = giaNhap * soLuong;
				tongTien += thanhTien;
				tongTienChuoi = formatTien.format(tongTien);
				
				tblModelDonNhap.addRow(new Object[] {
				tenThuoc,donVi,loaiThuoc,soLuong,ngaySanXuat,hsd,moTa,giaNhap, giaBan,thanhTien
			});
			//hiển thị tổng tiền
			txtTongTien.setText(tongTienChuoi);
			}


	}else if(o.equals(btnXoa)) {
		int r = tblDonNhap.getSelectedRow();
		if(r == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn đơn thuốc cần xoá!");
			return;
		}
		int xacNhan = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xoá Thuốc này chứ ?", "Huỷ bỏ",JOptionPane.YES_NO_OPTION);
		if(xacNhan == JOptionPane.NO_OPTION) {
			return;
		}else if(xacNhan==JOptionPane.YES_OPTION) {
//			Number numberThanhTien = null;
//			try {
//				numberThanhTien = formatTien.parse(tblDonNhap.getValueAt(r, 9).toString());
//			} catch (ParseException e1) {
//				e1.printStackTrace();
//			}
//			double doubleThanhTien = numberThanhTien.doubleValue();
//			System.out.println("Tổng tiền hiện tại : " + tongTien);
//			System.out.println("tiền đã xoá " + doubleThanhTien);
//			tongTien -= doubleThanhTien ;
//			String tongTienChuoi = formatTien.format(tongTien);
//			txtTongTien.setText(tongTienChuoi);
			tblModelDonNhap.removeRow(r);
			try {
				tinhTongTienDH();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(this, "Xoá đơn thuốc thành công!");
			
		}
		
		
	}else if(o.equals(btnSua)) {
		int r = tblDonNhap.getSelectedRow();
		if(r == -1 ) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn đơn thuốc cần sửa!");
		return;
		}
		if (! kiemTraDuLieuRong()) {
			return;
		}else 
			if(duLieuHopLe()) {
				kiemTraDuLieuRong();
				LocalDate ngaySanXuat = ngaySanxuat.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				String tenThuoc = txtTenThuoc.getText();
				Double giaBan = Double.parseDouble(txtDongia.getText());
				int hsd = Integer.parseInt(txtHanSuDung.getText().trim());
				String donVi = (String)cboDonvi.getSelectedItem();
				String loaiThuoc = (String)cboLoaiThuoc.getSelectedItem();
				String moTa = txtMoTaThuoc.getText();
				Double giaNhap = Double.parseDouble(txtGiaNhap.getText().toString());
				int soLuong = (Integer)spnSoLuongThuoc.getValue();
				
				
				thanhTien = giaNhap * soLuong; 
				tongTien-= Double.parseDouble(tblDonNhap.getValueAt(r, 9).toString());
				
				tongTien += thanhTien;
				String tongTienChuoi = formatTien.format(tongTien);
				//tenThuoc,donVi,loaiThuoc,soLuong,ngaySanXuat,hsd,moTa,giaNhapChuoi, giaBanChuoi,thanhTienChuoi
				tblDonNhap.setValueAt(tenThuoc, r, 0);
				tblDonNhap.setValueAt(donVi, r, 1);
				tblDonNhap.setValueAt(loaiThuoc, r, 2);
				tblDonNhap.setValueAt(soLuong, r, 3);
				tblDonNhap.setValueAt(ngaySanXuat, r, 4);
				tblDonNhap.setValueAt(hsd, r, 5);
				tblDonNhap.setValueAt(moTa, r, 6);
				tblDonNhap.setValueAt(giaNhap, r, 7);
				tblDonNhap.setValueAt(giaBan, r, 8);
				tblDonNhap.setValueAt(thanhTien, r, 9);
	
				txtTongTien.setText(tongTienChuoi);
			}
		}
			else if(o.equals(btnxoaTrang)) {
				xoaTrangCacField();
	
			}else if(o.equals(btnDoiHinhanh)) {
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(null);
				File f = chooser.getSelectedFile();
				fileName = f.getAbsolutePath();
				ImageIcon imgIcon = new ImageIcon(new ImageIcon(fileName).getImage().getScaledInstance(lblHinhanhThuoc.getWidth(), lblHinhanhThuoc.getHeight(), Image.SCALE_SMOOTH));
				lblHinhanhThuoc.setIcon(imgIcon);
				try {
					File img = new File(fileName);
					FileInputStream fis = new FileInputStream(img);
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					byte[] buf = new byte[1024];
					for(int readNum; (readNum = fis.read(buf))!= -1; ) {
						bos.write(buf,0,readNum);
					}
					hinhThuocByte = bos.toByteArray();
		
				}catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}else if(o.equals(btnTaoDonNhap)) {
	
				LocalDateTime ngayHT = LocalDateTime.now();
				int ncc = cboNCC.getSelectedIndex();
				NhaCungCap ncchon=lstNcc.get(ncc);
				DonDatThuoc donDatThuoc = new DonDatThuoc(txtMaNV.getText(), ncchon.getMaNCC(), ngayHT);
	
				try {
					donDatDao.taoDonNhapThuoc(donDatThuoc);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				int tongDong = tblDonNhap.getRowCount();
				String maDonNhapp = null;
				for(int r=0; r< tongDong; r++) {//lấy mã đơn đặt từ sql
					try {
						maDonNhapp = donDatDao.layMaDonNhapMoiTao();
						System.out.println("mã đơn đặt: " + maDonNhapp);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
		
					String ten = tblDonNhap.getValueAt(r, 0).toString();
					String donvi = tblDonNhap.getValueAt(r, 1).toString();
					String loaiThuoc = tblDonNhap.getValueAt(r, 2).toString();
					int soLuong = Integer.parseInt(tblDonNhap.getValueAt(r, 3).toString());
					String ngaySXString = tblDonNhap.getValueAt(r, 4).toString();
					Date ngaySx = null;
					java.sql.Date sqlNgaySX = null;
					try {
						ngaySx = dateFormat.parse(ngaySXString);
						sqlNgaySX = new java.sql.Date(ngaySx.getTime());
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					int hsd = Integer.parseInt(tblDonNhap.getValueAt(r, 5).toString());
					String moTa = tblDonNhap.getValueAt(r, 6).toString();
		
					String giaNhapString = tblDonNhap.getValueAt(r, 7).toString();
					String giaBanString = tblDonNhap.getValueAt(r, 8).toString();
					double giaNhap= 0.;
					try {
						giaNhap = chuyenChuThanhDouble(giaNhapString);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					double giaBan = 0;
					try {
						giaBan = chuyenChuThanhDouble(giaBanString);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					
					thanhTien = soLuong * giaBan;
		
					LoaiThuoc l = null;
					try {
						l = loaiThuocDao.timLoaiThuocTheoTen(loaiThuoc);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					DonViTinh d = null;
					try {
						d = donviDao.timDonViTheoTen(donvi);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
		
					Thuoc th = new Thuoc(ten, giaBan, soLuong, d, hsd, hinhThuocByte, l, moTa, giaNhap, sqlNgaySX);
					try {
						thuocDao.taoThuoc(th);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					String maThuocMoiTao = null;
					try {
						maThuocMoiTao = thuocDao.layMaThuocMoiTao();
						System.out.println("Thuốc mới tạo : " + maThuocMoiTao);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
		
					ChiTietDonDat ctdd = new ChiTietDonDat(maDonNhapp, maThuocMoiTao, soLuong, ten, giaNhap);
					try {
						chiTietDonDatDao.taoChiTietDonDat(ctdd);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					frmThuoc.xoaDataTable();
					try {
						frmThuoc.nhapDuLieuSQLLenTable();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				int xacNhan = JOptionPane.showConfirmDialog(this, "Tạo đơn nhập thành công, Bán có muốn in không", "Huỷ bỏ", JOptionPane.YES_NO_OPTION);
				if(xacNhan == JOptionPane.NO_OPTION) {
					this.dispose();
				}else {
					System.out.println("In Đơn nhập");

					String folderPath = "/hoaDonNhap";
					File folder = new File(folderPath);
					if (!folder.exists()) {
						folder.mkdirs();
					}

					String outputFile = folderPath + maDonNhapp + ".pdf";

// Chuẩn bị dữ liệu từ bảng
					List<ChiTietDonDat> lstC = new ArrayList<>();
					for (int i = 0; i < tblDonNhap.getRowCount(); i++) {
						String ten = tblDonNhap.getValueAt(i, 0).toString();
						int soluong = Integer.parseInt(tblDonNhap.getValueAt(i, 3).toString());
						double gianhap = Double.parseDouble(tblDonNhap.getValueAt(i, 7).toString());
						double thanhtien = Double.parseDouble(tblDonNhap.getValueAt(i, 9).toString());

						lstC.add(new ChiTietDonDat(soluong, ten, gianhap, thanhtien));
					}

// Lấy thông tin đơn đặt
					DonDatThuoc d = donDatDao.layDonDatBangMa(maDonNhapp);
					DonDatThuoc d2 = new DonDatThuoc(d.getMaDon(), d.getMaNV(), d.getNcc());

// Tạo nguồn dữ liệu cho JasperReport
					JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(lstC);
					Map<String, Object> map = new HashMap<>();
					map.put("CollectionBeanParam", itemsJRBean);
					map.put("maDon", d2.getMaDon());
					map.put("maNV", d2.getMaNV());
					map.put("maNCC", d2.getNcc());
					map.put("tongTien", txtTongTien.getText());

					try (FileInputStream input = new FileInputStream("C:\\Users\\vanhi\\JaspersoftWorkspace\\JasperProject\\JasperReport_A4.jrxml")) {
						JasperDesign jasperDesign = JRXmlLoader.load(input);
						JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
						JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, new JREmptyDataSource());

						// Hiển thị báo cáo
						JasperViewer.viewReport(jasperPrint, false);

						// Xuất ra file PDF
						JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile);
						System.out.println("Đã in hóa đơn nhập: " + outputFile);

						// ✅ Mở file sau khi in
						File pdfFile = new File(outputFile);
						if (pdfFile.exists()) {
							if (Desktop.isDesktopSupported()) {
								Desktop.getDesktop().open(pdfFile);
							} else {
								System.out.println("Không mở được file: Desktop không hỗ trợ.");
							}
						}

					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (JRException e1) {
						e1.printStackTrace();
					} catch (IOException e2) {
						e2.printStackTrace();
					}

					this.dispose();

				}
}	
			else if(o.equals(btnThemDonVi)) {
				FormThemDonViThuoc frmDonvi = null;
				try {
					frmDonvi = new FormThemDonViThuoc(this);
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frmDonvi.setVisible(true);
			}
			else if(o.equals(btnThemLoaiThuoc)) {
				FormThemLoaiThuoc frmLoaithuoc = null;
				try {
					frmLoaithuoc = new FormThemLoaiThuoc(this);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frmLoaithuoc.setVisible(true);
			}
}
public JComboBox<String> getCboDonvi() {
	return cboDonvi;
}

public void setCboDonvi(JComboBox<String> cboDonvi) {
	this.cboDonvi = cboDonvi;
}

public JComboBox<String> getCboLoaiThuoc() {
	return cboLoaiThuoc;
}

public void setCboLoaiThuoc(JComboBox<String> cboLoaiThuoc) {
	this.cboLoaiThuoc = cboLoaiThuoc;
}

private void xoaTrangCacField() {
txtDongia.setText("");
txtHanSuDung.setText("");
txtMoTaThuoc.setText("");
txtTenThuoc.setText("");
ngaySanxuat.setDate(null);
cboDonvi.setSelectedIndex(0);
cboLoaiThuoc.setSelectedIndex(0);
cboNCC.setSelectedIndex(0);lblHinhanhThuoc.setIcon(new ImageIconCustom("images/hinhRong.png", 130, 130));
txtGiaNhap.setText("");

}

	private boolean kiemTraDuLieuRong() {
		if(txtTenThuoc.getText()=="" || txtDongia.getText()=="" ||
				ngaySanxuat.getDate() == null || txtHanSuDung.getText()=="" || txtGiaNhap.getText()=="")
		{
			JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
			return false;
		}
		return true;
	}
	private boolean duLieuHopLe() {
		String giaNhapChuoi = txtGiaNhap.getText().trim();
		String giaChuoi = txtDongia.getText();
		String hsdChuoi = txtHanSuDung.getText();
		double gia;
		try {
			gia = Double.parseDouble(giaChuoi);
			if(gia <= 1000 ) {
				JOptionPane.showMessageDialog(txtDongia, "Gía phải lớn hơn 1,000VNĐ");
				return false;
			}
		}catch (NumberFormatException  e) {
			JOptionPane.showMessageDialog(txtDongia, "Chỉ được nhập số");
            return false;
		}
		try {
			int hanSuDung = Integer.parseInt(hsdChuoi);
			if(hanSuDung <= 0) {
				JOptionPane.showMessageDialog(txtHanSuDung, "Hạn sử dụng phải lớn hơn 0");
			return false;
			}
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(txtHanSuDung, "Chỉ được nhập số");
            return false;
        }
		
		Date d = new Date();
		if(ngaySanxuat.getDate().after(d)) {
			JOptionPane.showMessageDialog(ngaySanxuat, "Ngày không được lớn hơn ngày hiện tại");
			return false;
		}
		double giaNhap;
		try {
			giaNhap = Double.parseDouble(giaNhapChuoi);
			if(giaNhap >= gia || giaNhap < 1000 ) {
				JOptionPane.showMessageDialog(txtGiaNhap, "Giá nhập không được lớn hơn giá bán và phải lớn hơn 1,000VNĐ");
				return false;	
			}
		}catch (NumberFormatException  e) {
			JOptionPane.showMessageDialog(txtGiaNhap, "Chỉ được nhập số");
            return false;
		}
		if(gia > giaNhap*3)
		{
			JOptionPane.showMessageDialog(txtDongia, "Gía bán không hợp lý");
			return false;
		}
		if((int)spnSoLuongThuoc.getValue() ==0) {
			JOptionPane.showMessageDialog(spnSoLuongThuoc, "Số lượng không hợp lệ");
			return false;
		}


		return true;
	}
	private double chuyenChuThanhDouble(String text) throws ParseException {
		Number a = formatTien.parse(text);
		double kq = a.doubleValue();
		return kq;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		int r = tblDonNhap.getSelectedRow();
		txtDongia.setText(tblDonNhap.getValueAt(r, 8).toString());
		txtGiaNhap.setText(tblDonNhap.getValueAt(r, 7).toString());
		txtHanSuDung.setText(tblDonNhap.getValueAt(r, 5).toString());
		txtMoTaThuoc.setText(tblDonNhap.getValueAt(r, 6).toString());
		txtTenThuoc.setText(tblDonNhap.getValueAt(r, 0).toString());
		spnSoLuongThuoc.setValue(Integer.parseInt(tblDonNhap.getValueAt(r, 3).toString()));
		cboDonvi.setSelectedItem(tblDonNhap.getValueAt(r, 1).toString());
		cboLoaiThuoc.setSelectedItem(tblDonNhap.getValueAt(r, 2).toString());
		
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

@Override
public void mouseEntered(MouseEvent e) {

}

@Override
public void mouseExited(MouseEvent e) {
// TODO Auto-generated method stub

}
}