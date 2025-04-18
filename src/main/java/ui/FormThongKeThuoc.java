package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import connect.ConnectDB;
import dao.ChiTietDonDatDao;
import dao.DonDatThuocDao;
import dao.DonViTinhDao;
import dao.ThongKeDao;
import dao.ThuocDao;
import entity.DonViTinh;
import org.jdesktop.swingx.JXDatePicker;

public class FormThongKeThuoc extends JPanel implements ActionListener{
	private JXDatePicker ngayBatdau,ngayKetThuc;
	
	private JButton btnThongKe;
	
	private DefaultTableModel tblModelDoanhthuThuocNhap, tblModelDoanhthuThuocBan;
	private JTable tblThuocNhap,tblThuocBan;
	
	private JTextField txtTongTienNhap, txtTongTienBan;
	
	private ThuocDao thuocDao;
	private ChiTietDonDatDao ctddDao;
	private DonDatThuocDao donDatDao;
	private ThongKeDao thongkeDao;

	private NumberFormat formatTien;

	private JButton btnXuatExcel;

	private DonViTinhDao donviDao;

	public FormThongKeThuoc() {
		
		try {
			ConnectDB.getInstance().connectDataBase();
			System.out.println("Kết nối thành công SQL với FormThuoc");
		}catch (SQLException e) {
			e.printStackTrace();
		}
		thuocDao = new ThuocDao();
		ctddDao = new ChiTietDonDatDao();
		donDatDao = new DonDatThuocDao();
		thongkeDao = new ThongKeDao();
		donviDao = new DonViTinhDao();
		
		setLayout(new BorderLayout());
		setBorder(new TitledBorder("Tính tổng tiền thuốc đã nhập và bán theo ngày: "));
		
		JPanel pnlTren = new JPanel();
		JPanel pnlGiua = new JPanel();
		JPanel pnlDuoi = new JPanel();
		
		add(pnlTren,BorderLayout.NORTH);
		add(pnlGiua, BorderLayout.CENTER);
		add(pnlDuoi, BorderLayout.SOUTH);
		
		pnlTren.setPreferredSize(new Dimension(1000, 70)); 
        pnlGiua.setPreferredSize(new Dimension(1000, 400)); 
        pnlDuoi.setPreferredSize(new Dimension(1000, 260)); 
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		
		//tren
		pnlTren.setBackground(Color.decode("#FEFBF6"));
		pnlTren.setLayout(new GridBagLayout());
		Box bTren, bTrenTrai,bTrenGiua, bTrenPhai;
		pnlTren.add(bTren = Box.createHorizontalBox(), gbc);
		
		bTren.add(bTrenTrai = Box.createVerticalBox());
		
		bTrenTrai.add(new JLabel("Từ Ngày:"));
		bTrenTrai.add(ngayBatdau = new JXDatePicker());
		bTrenTrai.setBorder(new EmptyBorder(5,10,10,10));
		
		bTren.add(bTrenGiua = Box.createVerticalBox());
		bTrenGiua.add(new JLabel("Đến Ngày:"));
		bTrenGiua.add(ngayKetThuc = new JXDatePicker());
		bTrenGiua.setBorder(new EmptyBorder(5,10,10,10));
		
		bTren.add(bTrenPhai = Box.createVerticalBox());
		bTrenPhai.add(btnThongKe = new JButton("Thống kê"));
		bTrenPhai.setBorder(new EmptyBorder(5,10,10,10));
		
		pnlTren.setBorder(new EmptyBorder(5,0,15,0));
		//-------------------------------
		//giua
		pnlGiua.setBackground(Color.decode("#FEFBF6"));
		
		String[] headerDoanhthuThuocNhap = {"Tên Sản Phẩm", "Đơn Vị", "Tổng Số Lượng Nhập","Thành tiền"};
        tblModelDoanhthuThuocNhap = new DefaultTableModel(headerDoanhthuThuocNhap, 0);
        tblThuocNhap = new JTable(tblModelDoanhthuThuocNhap);
        tblThuocNhap.setRowHeight(30);
        JScrollPane scrDonNhap = new JScrollPane(tblThuocNhap);
        pnlGiua.add(scrDonNhap);
        
       
        String[] headerThuocBan = {"Tên Sản Phẩm", "Đơn Vị", "Tổng Số Lượng Bán ","Doanh Thu"};
        tblModelDoanhthuThuocBan = new DefaultTableModel(headerThuocBan, 0);
        tblThuocBan = new JTable(tblModelDoanhthuThuocBan);
        tblThuocBan.setRowHeight(30);
        JScrollPane scrDonBan = new JScrollPane(tblThuocBan);
        pnlGiua.add(scrDonBan);
        
		//-------------------------------
        //duoi
        pnlDuoi.setBorder(new EmptyBorder(20,0,10,0));
        pnlDuoi.setBackground(Color.decode("#FEFBF6"));
        JLabel lblTongtienNhap = new JLabel("Tổng tiền nhập:");
        lblTongtienNhap.setFont(new Font("Times New Roman", Font.BOLD, 25));
        txtTongTienNhap = new JTextField(10);
        txtTongTienNhap.setFont(new Font("Times New Roman", Font.BOLD, 25));
        txtTongTienNhap.setOpaque(false);;
        txtTongTienNhap.setBackground(null);
        txtTongTienNhap.setBorder(null);
        txtTongTienNhap.setEditable(false);
		
        JLabel lblTongtienBan = new JLabel("Tổng tiền bán:");
        lblTongtienBan.setFont(new Font("Times New Roman", Font.BOLD, 25));
        txtTongTienBan = new JTextField(10);
        txtTongTienBan.setOpaque(false);;
        txtTongTienBan.setBackground(null);
        txtTongTienBan.setBorder(null);
        txtTongTienBan.setEditable(false);
		txtTongTienBan.setFont(new Font("Times New Roman", Font.BOLD, 25));
		
        pnlDuoi.add(lblTongtienNhap); pnlDuoi.add(txtTongTienNhap);
        pnlDuoi.add(lblTongtienBan); pnlDuoi.add(txtTongTienBan);
       
        pnlDuoi.add(btnXuatExcel = new JButton("Xuất Excel"), BorderLayout.SOUTH);
        xoaDataTable();
		//event
		btnThongKe.addActionListener(this);
		btnXuatExcel.addActionListener(this);
	}

	public void xoaDataTable() {
		DefaultTableModel modelTbl = (DefaultTableModel) tblThuocBan.getModel();
		DefaultTableModel modelTbl1 = (DefaultTableModel) tblThuocNhap.getModel();
		modelTbl.getDataVector().removeAllElements();
		modelTbl1.getDataVector().removeAllElements();
	}
	public void xoaDataTableThuocBan() {
		DefaultTableModel modelTbl = (DefaultTableModel) tblThuocBan.getModel();
		modelTbl.getDataVector().removeAllElements();
	}
	public void xoaDataTableThuocNhap() {
		DefaultTableModel modelTbl1 = (DefaultTableModel) tblThuocNhap.getModel();
		modelTbl1.getDataVector().removeAllElements();
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnThongKe)) {
			// Xóa dữ liệu bảng trước khi thêm dữ liệu mới
	        DefaultTableModel modelTblBan = (DefaultTableModel) tblThuocBan.getModel();
	        DefaultTableModel modelTblNhap = (DefaultTableModel) tblThuocNhap.getModel();
	        modelTblBan.setRowCount(0);
	        modelTblNhap.setRowCount(0);
	        
	        System.out.println("Thống kê");
	        formatTien = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
	        double tongTienNhap = 0;
	        double tongTienBan = 0;

	        java.util.Date ngayBdau = ngayBatdau.getDate();
	        java.util.Date ngayKthuc = ngayKetThuc.getDate();
	        if (ngayBdau == null || ngayKthuc == null) {
	        	JOptionPane.showMessageDialog(this, "Ngày bắt đầu hoặc ngày kết thúc không được để trống.");
	            return;
	        }
	        if(ngayBdau.after(ngayKthuc)) {
				JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được lớn hơn ngày kết thúc.");
	            return;
			}
			if(ngayKthuc.after(new java.util.Date())) {
				JOptionPane.showMessageDialog(this, "Ngày kết thúc không được lớn hơn ngày hiện tại.");
	            return;
			}
	        
	        Date dateNgayBdau = new Date(ngayBdau.getTime());
	        Date dateNgayKthuc = new Date(ngayKthuc.getTime());

	        ArrayList<Object[]> lstDoanhthuNhap = thongkeDao.thongKeThuocNhapTheoNgay(dateNgayBdau, dateNgayKthuc);
	        if (lstDoanhthuNhap != null && !lstDoanhthuNhap.isEmpty()) {
	            for (Object[] obj : lstDoanhthuNhap) {
	                String ten = (String) obj[0];
	                int donvi = (int) obj[1];
	                int soLuong = (int) obj[2];
	                double thanhTien = (double) obj[3];

	                DonViTinh d = null;
	                try {
	                    d = donviDao.timDonViTheoMa(donvi);
	                } catch (SQLException e1) {
	                    e1.printStackTrace();
	                    System.out.println("Lỗi khi tìm đơn vị tính: " + e1.getMessage());
	                }

	                tblModelDoanhthuThuocNhap.addRow(new Object[]{
	                    ten, d.getTenDonVi(), soLuong, thanhTien
	                });
	                tongTienNhap += thanhTien;
	            }
	        } else {
	            JOptionPane.showMessageDialog(this, "không có dữ liệu thuốc nhập");
	        }

	        txtTongTienNhap.setText(formatTien.format(tongTienNhap));

	        ArrayList<Object[]> lstDoanhthuBan = thongkeDao.thongKeThuocBanTheoNgay(dateNgayBdau, dateNgayKthuc);
	        if (lstDoanhthuBan != null && !lstDoanhthuBan.isEmpty()) {
	            for (Object[] obj : lstDoanhthuBan) {
	                String ten = (String) obj[0];
	                int donvi = (int) obj[1];
	                int soLuong = (int) obj[2];
	                double thanhTien = (double) obj[3];

	                DonViTinh d = null;
	                try {
	                    d = donviDao.timDonViTheoMa(donvi);
	                } catch (SQLException e1) {
	                    e1.printStackTrace();
	                    System.out.println("Lỗi khi tìm đơn vị tính: " + e1.getMessage());
	                }

	                tblModelDoanhthuThuocBan.addRow(new Object[]{
	                    ten, d.getTenDonVi(), soLuong, thanhTien
	                });
	                tongTienBan += thanhTien;
	            }
	        } else {
	        	 JOptionPane.showMessageDialog(this, "không có dữ liệu thuốc bán");
	        }

	        String tienBanChuoi = formatTien.format(tongTienBan);
	        txtTongTienBan.setText(tienBanChuoi);
	        String tienNhapChuoi = formatTien.format(tongTienNhap);
	        txtTongTienNhap.setText(tienNhapChuoi);

	        System.out.println(tienBanChuoi);
	        System.out.println(tienNhapChuoi);
		}
		else if(o.equals(btnXuatExcel)) {
			
			try (XSSFWorkbook workbook = new XSSFWorkbook()) {
			    // Sheet 1
				if(tblThuocNhap.getRowCount() == 0 && tblThuocBan.getRowCount() == 0) {
			    	JOptionPane.showMessageDialog(this, "Không có dữ liệu nên không thể xuất excel");
			    	return;
			    }
				else {
					XSSFSheet sheet1 = workbook.createSheet("Thống kê tổng tiền thuốc nhập theo ngày");
				    XSSFRow row1 = null;
				    Cell cell1 = null;
				    row1 = sheet1.createRow(0);

				    cell1 = row1.createCell(0);
				    cell1.setCellValue("Sản phẩm");
				    cell1 = row1.createCell(1);
				    cell1.setCellValue("Đơn vị");
				    cell1 = row1.createCell(2);
				    cell1.setCellValue("Số lượng");
				    cell1 = row1.createCell(3);
				    cell1.setCellValue("Thành tiền");
				    
				    	for (int r = 0; r < tblThuocNhap.getRowCount(); r++) {
					        row1 = sheet1.createRow(r+1);
					        cell1 = row1.createCell(0);
					        cell1.setCellValue(tblThuocNhap.getValueAt(r, 0).toString());
					        cell1 = row1.createCell(1);
					        cell1.setCellValue(tblThuocNhap.getValueAt(r, 1).toString());
					        cell1 = row1.createCell(2);
					        cell1.setCellValue(tblThuocNhap.getValueAt(r, 2).toString());
					        cell1 = row1.createCell(3, CellType.NUMERIC);
					        cell1.setCellValue(Double.parseDouble(tblThuocNhap.getValueAt(r, 3).toString()));
					    }	
				
					// Sheet 2
				    XSSFSheet sheet2 = workbook.createSheet("Thống kê tổng tiền thuốc bán theo ngày");
				    XSSFRow row2 = null;
				    Cell cell2 = null;
				    row2 = sheet2.createRow(0);
				    
				    cell2 = row2.createCell(0); 
				    cell2.setCellValue("Sản phẩm");
				    cell2 = row2.createCell(1);
				    cell2.setCellValue("Đơn vị"); 
				    cell2 = row2.createCell(2);
				    cell2.setCellValue("Số lượng");
				    cell2 = row2.createCell(3); 
				    cell2.setCellValue("Thành tiền");

				    for (int r = 0; r < tblThuocBan.getRowCount(); r++) {
				        row2 = sheet2.createRow(r+1);
				        cell2 = row2.createCell(0);
				        cell2.setCellValue(tblThuocBan.getValueAt(r, 0).toString());
				        cell2 = row2.createCell(1);
				        cell2.setCellValue(tblThuocBan.getValueAt(r, 1).toString());
				        cell2 = row2.createCell(2);
				        cell2.setCellValue(tblThuocBan.getValueAt(r, 2).toString());
				        cell2 = row2.createCell(3, CellType.NUMERIC);
				        cell2.setCellValue(Double.parseDouble(tblThuocBan.getValueAt(r, 3).toString()));
				    }
				
			    JFileChooser fileChooser = new JFileChooser();
			    int userSelection = fileChooser.showSaveDialog(null);

			    if (userSelection == JFileChooser.APPROVE_OPTION) {
			        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
			        if (!filePath.toLowerCase().endsWith(".xlsx")) {
			            filePath += ".xlsx";
			        }

			        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
			            workbook.write(outputStream);
			            System.out.println("Đã xuất file Excel!");
			        } catch (IOException e2) {
			            e2.printStackTrace();
			        }
			    }
			}
			} catch (IOException e1) {
			    e1.printStackTrace();
			}	
		}
		
	}

}
