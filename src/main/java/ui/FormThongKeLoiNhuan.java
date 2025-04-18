package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.NumberFormat;
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
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import connect.ConnectDB;
import dao.ThongKeDao;
import org.jdesktop.swingx.JXDatePicker;

public class FormThongKeLoiNhuan extends JPanel implements ActionListener{
	private JXDatePicker ngayBatdau, ngayKetThuc;
	
	private JButton btnThongKe, btnXuatExcel;
	
	private ThongKeDao thongkeDao;
	private DefaultTableModel tblModelLoinhuan;
	private JTable tblDoanhthu;
	
	private JTextField txtTongDoanhthu, txtTongTienvon, txtTongLoinhuan;
	private NumberFormat formatTien;

	public FormThongKeLoiNhuan() {
		
		try {
			ConnectDB.getInstance().connectDataBase();
			System.out.println("Kết nối thành công SQL với FormThuoc");
		}catch (SQLException e) {
			e.printStackTrace();
		}
		thongkeDao = new ThongKeDao();
		
		setLayout(new BorderLayout());
		setBorder(new TitledBorder("Doanh thu/Lợi nhuận theo thời gian: "));
		
		JPanel pnlTren = new JPanel();
		JPanel pnlGiua = new JPanel(new BorderLayout());
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
		
		String[] headerDoanhthu = {"Ngày", "Tổng tiền bán", "Tổng tiền vốn","Lợi nhuận thuần"};
        tblModelLoinhuan = new DefaultTableModel(headerDoanhthu, 0);
        tblDoanhthu = new JTable(tblModelLoinhuan);
        tblDoanhthu.setRowHeight(30);
        JScrollPane scrDonNhap = new JScrollPane(tblDoanhthu);
        pnlGiua.add(scrDonNhap, BorderLayout.CENTER);
		//-------------------------------
        //duoi
        pnlDuoi.setBorder(new EmptyBorder(20,0,10,0));
        pnlDuoi.setBackground(Color.decode("#FEFBF6"));
        JLabel lblTongDoanhthu = new JLabel("Tổng doanh thu:");
        lblTongDoanhthu.setFont(new Font("Times New Roman", Font.BOLD, 20));
        txtTongDoanhthu = new JTextField(10);
        txtTongDoanhthu.setFont(new Font("Times New Roman", Font.BOLD, 20));
        txtTongDoanhthu.setOpaque(false);;
        txtTongDoanhthu.setBackground(null);
        txtTongDoanhthu.setBorder(null);
        txtTongDoanhthu.setEditable(false);
		
        JLabel lblTongTienVon = new JLabel("Tổng tiền vốn:");
        lblTongTienVon.setFont(new Font("Times New Roman", Font.BOLD, 20));
        txtTongTienvon = new JTextField(10);
        txtTongTienvon.setOpaque(false);;
        txtTongTienvon.setBackground(null);
        txtTongTienvon.setBorder(null);
        txtTongTienvon.setEditable(false);
		txtTongTienvon.setFont(new Font("Times New Roman", Font.BOLD, 20));
		
		JLabel lblTongLoinhuan = new JLabel("Tổng lợi nhuận thuần:");
		lblTongLoinhuan.setFont(new Font("Times New Roman", Font.BOLD, 20));
        txtTongLoinhuan = new JTextField(10);
        txtTongLoinhuan.setOpaque(false);;
        txtTongLoinhuan.setBackground(null);
        txtTongLoinhuan.setBorder(null);
        txtTongLoinhuan.setEditable(false);
        txtTongLoinhuan.setFont(new Font("Times New Roman", Font.BOLD, 20));
		
        pnlDuoi.add(lblTongDoanhthu); pnlDuoi.add(txtTongDoanhthu);
        pnlDuoi.add(lblTongTienVon); pnlDuoi.add(txtTongTienvon);
        pnlDuoi.add(lblTongLoinhuan); pnlDuoi.add(txtTongLoinhuan);
        pnlDuoi.add(btnXuatExcel = new JButton("Xuất Excel"));
        
        //event
        btnThongKe.addActionListener(this);
        btnXuatExcel.addActionListener(this);
	}


	public void xoaDataTable() {
		DefaultTableModel modelTbl = (DefaultTableModel) tblDoanhthu.getModel();
		DefaultTableModel modelTbl1 = (DefaultTableModel) tblDoanhthu.getModel();
		modelTbl.getDataVector().removeAllElements();
		modelTbl1.getDataVector().removeAllElements();
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnThongKe)) {
			
			//xoa du lieu moi khi nhan nut
			DefaultTableModel modelTbl1 = (DefaultTableModel) tblDoanhthu.getModel();
			modelTbl1.setRowCount(0);
			
			
			formatTien = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
			double tongDoanhthu = 0;
			double tongTienVon = 0;
			double tongLoinhuan = 0;
			
			java.util.Date ngayBdau = ngayBatdau.getDate();
			java.util.Date ngayKthuc= ngayKetThuc.getDate();
			if(ngayBdau == null || ngayKthuc == null ) {
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
			
			ArrayList<Object[]> lstDoanhthuNhap = thongkeDao.thongKeLoinhuanTheoNgay(dateNgayBdau, dateNgayKthuc);
			if(lstDoanhthuNhap != null && !lstDoanhthuNhap.isEmpty()) {
				for(Object[] obj : lstDoanhthuNhap) {
					Date ngay   = (Date)obj[0];
					double doanhThu =(double)obj[1];
					double tienVon  =(double)obj[2];
					double loiNhuan = (double)obj[3];
				
					tblModelLoinhuan.addRow(new Object[] {
							ngay,doanhThu,tienVon,loiNhuan
					});
					tongDoanhthu +=doanhThu; 
					tongTienVon+=tienVon; 	
					tongLoinhuan +=loiNhuan;
				}		
			}
			else {
	        	 JOptionPane.showMessageDialog(this, "không có dữ liệu ");

			}
			String tongDoanhthuChuoi = formatTien.format(tongDoanhthu); txtTongDoanhthu.setText(tongDoanhthuChuoi);
			String tongTienVonChuoi = formatTien.format(tongTienVon); txtTongTienvon.setText(tongTienVonChuoi);
			String tongnLoinhuanChuoi= formatTien.format(tongLoinhuan);txtTongLoinhuan.setText(tongnLoinhuanChuoi);
		}
		else if(o.equals(btnXuatExcel)) {
			try (XSSFWorkbook workbook = new XSSFWorkbook()) {
				
				if(tblDoanhthu.getRowCount() == 0) {
					JOptionPane.showMessageDialog(this, "không có dữ liệu nên không thể xuất excel");
					return;
				}
				else {
					// Sheet 1
				    XSSFSheet sheet1 = workbook.createSheet("Thống kê lợi nhuận theo ngày");
				    XSSFRow row1 = null;
				    Cell cell1 = null;
				    row1 = sheet1.createRow(0);

				    cell1 = row1.createCell(0);
				    cell1.setCellValue("Ngày");
				    cell1 = row1.createCell(1);
				    cell1.setCellValue("Doanh thu");
				    cell1 = row1.createCell(2);
				    cell1.setCellValue("Tiền vốn");
				    cell1 = row1.createCell(3);
				    cell1.setCellValue("Lợi nhuận");

				    for (int r = 0; r < tblDoanhthu.getRowCount(); r++) {
				        row1 = sheet1.createRow(r+1);
				        cell1 = row1.createCell(0);
				        cell1.setCellValue(tblDoanhthu.getValueAt(r, 0).toString());
				        cell1 = row1.createCell(1);
				        cell1.setCellValue(Double.parseDouble(tblDoanhthu.getValueAt(r, 1).toString()));
				        cell1 = row1.createCell(2);
				        cell1.setCellValue(Double.parseDouble(tblDoanhthu.getValueAt(r, 2).toString()));
				        cell1 = row1.createCell(3, CellType.NUMERIC);
				        cell1.setCellValue(Double.parseDouble(tblDoanhthu.getValueAt(r, 3).toString()));
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
