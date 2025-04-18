package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import connect.ConnectDB;
import dao.DonDatThuocDao;
import entity.DonDatThuoc;
import entity.Thuoc;
import format.SearchText;

public class FormDanhSachDonNhap extends JFrame implements ActionListener{
	private DonDatThuocDao donNhapDao;
	
	private DefaultTableModel tblModelDonNhap;
	private JTable tblDonNhap;

	private JButton btnChiTietDon, btnTim;

	private JComboBox<String> cboTimKiem;

	private JTextField txtTimKiem;

	private JButton btnLamMoi;
	
	public FormDanhSachDonNhap() throws SQLException {

		
		setSize(700,400);
		
		setLayout(new BorderLayout());
		try {
			ConnectDB.getInstance().connectDataBase();
			System.out.println("Kết nối thành công SQL với Form Danh Sách Đơn Nhập");
		}catch (SQLException e) {
			e.printStackTrace();
		}
		donNhapDao = new DonDatThuocDao();
		JPanel pnlGiua, pnlDuoi, pnlTren;
		add(pnlGiua = new JPanel(), BorderLayout.CENTER);
		add(pnlDuoi = new JPanel(), BorderLayout.SOUTH);
		add(pnlTren = new JPanel(), BorderLayout.NORTH);
		//Tren
		pnlTren.setBorder(new TitledBorder("Tìm kiếm đơn nhập"));
		pnlTren.add(cboTimKiem = new JComboBox<String>());
		cboTimKiem.addItem("Mã đơn");
		cboTimKiem.addItem("SĐT Nhân viên");
		cboTimKiem.addItem("Tên nhà cung cấp");
		pnlTren.add(txtTimKiem = new JTextField(25));
		pnlTren.add(btnTim = new JButton("Tìm kiếm"));
		pnlTren.add(btnLamMoi = new JButton("Làm Mới"));
		//Giua
		pnlGiua.setBorder(new TitledBorder("Danh sách đơn nhập"));
		pnlGiua.setLayout(new BorderLayout());
		String[] headerDonNhap = {"Mã đơn nhập", "Mã nhân viên", "Mã nhà cung cấp", "Ngày nhập"};
        tblModelDonNhap = new DefaultTableModel(headerDonNhap, 0);
        tblDonNhap = new JTable(tblModelDonNhap);
        JScrollPane scrDonNhap = new JScrollPane(tblDonNhap);
        pnlGiua.add(scrDonNhap, BorderLayout.CENTER);
        
        //Duoi
        pnlDuoi.add(btnChiTietDon = new JButton("Xem chi tiết đơn nhập"));
		
		xoaDataTable();
		nhapDuLieuSQLLenTable();
		
		//event
		btnChiTietDon.addActionListener(this);
		btnTim.addActionListener(this);
		btnLamMoi.addActionListener(this);
	}
	
	public void nhapDuLieuSQLLenTable() throws SQLException {
		List<DonDatThuoc> lstDon = donNhapDao.timTatCaDonDat();
		for(DonDatThuoc don : lstDon) {
			tblModelDonNhap.addRow(new Object[] {
					don.getMaDon(),don.getMaNV(),don.getNcc(),don.getNgayNhap()
			});
		}
	}
	public void xoaDataTable() {
		DefaultTableModel tblModel = (DefaultTableModel) tblDonNhap.getModel();
		tblModel.getDataVector().removeAllElements();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnChiTietDon)) {
			int r = tblDonNhap.getSelectedRow();
			FormChiTietDonNhap frmCTDN = null;
			String maDN = tblDonNhap.getValueAt(r, 0).toString();
			System.out.println("ma " + maDN);
			try {
				
				frmCTDN = new FormChiTietDonNhap(maDN);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			frmCTDN.setVisible(true);
			
		}else if(o.equals(btnTim)) {
			String luaChon = (String)cboTimKiem.getSelectedItem();
			if(luaChon.equals("Mã đơn")) {
				timDonTheoMaDon();
			}
			else if(luaChon.equals("SĐT Nhân viên")) {
				timDonTheoSDT();
			}
			else if(luaChon.equals("Tên nhà cung cấp")) {
				timDonTheoTenNCC();
			}
		}else if(o.equals(btnLamMoi)){
			xoaDataTable();
			try {
				nhapDuLieuSQLLenTable();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	private void timDonTheoMaDon() {
		String ma = txtTimKiem.getText();
		DonDatThuoc d = donNhapDao.layDonDatBangMa(ma);
		if(d != null ) {
			xoaDataTable();
			JOptionPane.showMessageDialog(this, "Tìm đơn nhập theo mã thành công!");
		}
		else {
			JOptionPane.showMessageDialog(this, "Không tìm thấy");
		}
			tblModelDonNhap.addRow(new Object[] {
					d.getMaDon(),d.getMaNV(),d.getNcc(),d.getNgayNhap()
			});
		
	}
	private void timDonTheoSDT() {
		String sdt = txtTimKiem.getText();
		ArrayList<DonDatThuoc> lstDon = donNhapDao.layDonDatTheoSDT(sdt);
		if(!lstDon.isEmpty() ) {
			xoaDataTable();
			JOptionPane.showMessageDialog(this, "Tìm đơn nhập theo sđt thành công!");
		}
		else {
			JOptionPane.showMessageDialog(this, "Không tìm thấy");
		}
		for(DonDatThuoc d:lstDon) {
			tblModelDonNhap.addRow(new Object[] {
				d.getMaDon(),d.getMaNV(),d.getMaNV(),d.getNgayNhap()
			});
		}
	}
	private void timDonTheoTenNCC() {
		String ten = txtTimKiem.getText();
		ArrayList<DonDatThuoc> lstDon = donNhapDao.layDonDatTenNCC(ten);
		if(!lstDon.isEmpty() ) {
			xoaDataTable();
			JOptionPane.showMessageDialog(this, "Tìm đơn nhập theo tên NCC thành công!");
		}else {
			JOptionPane.showMessageDialog(this, "Không tìm thấy");
		}
		for(DonDatThuoc d:lstDon) {
			tblModelDonNhap.addRow(new Object[] {
				d.getMaDon(),d.getMaNV(),d.getNcc(),d.getNgayNhap()
			});
		}
	}
}
