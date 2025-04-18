package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXDatePicker;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;



import connect.ConnectDB;
import dao.NhanVienDao;
import dao.ThongKeDao;
import entity.NhanVien;

public class FormThongKeNhanVien extends JPanel implements ActionListener{
	private JXDatePicker ngayBatdau, ngayKetThuc;
	
	private JButton btnThongKe;
	
	private JTextField txtMaNV, txtTenNV;
	
	private ThongKeDao thongkeDao;
	private NhanVienDao nvDao;
	
	private ArrayList<Object[]> lstObj;
	
	private JFreeChart barChart;
	private JPanel pnlGiua;

	public FormThongKeNhanVien() {
		
		try {
			ConnectDB.getInstance().connectDataBase();
			System.out.println("Kết nối thành công SQL với FormThuoc");
		}catch (SQLException e) {
			e.printStackTrace();
		}
		thongkeDao = new ThongKeDao();
		nvDao = new NhanVienDao();
		
		setLayout(new BorderLayout());
		
		JPanel pnlTren = new JPanel();
		pnlGiua = new JPanel();
		JPanel pnlDuoi = new JPanel();
		
		add(pnlTren,BorderLayout.NORTH);
		add(pnlGiua, BorderLayout.CENTER);
		add(pnlDuoi, BorderLayout.SOUTH);
		
		pnlTren.setPreferredSize(new Dimension(1000, 70));   pnlTren.setBackground(Color.decode("#FEFBF6"));
        pnlGiua.setPreferredSize(new Dimension(1000, 400));  pnlGiua.setBackground(Color.decode("#FEFBF6"));
        pnlDuoi.setPreferredSize(new Dimension(1000, 260));  pnlDuoi.setBackground(Color.decode("#FEFBF6"));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		
		//tren
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
		
		//-------------------------------
		//giua
		pnlGiua.setLayout(new BorderLayout());
		barChart = ChartFactory.createBarChart("", "Số tiền", "Nhân viên", khoiTaoData());
        ChartPanel pnlBarChart = new ChartPanel(barChart);
        pnlGiua.add(pnlChart("Biểu đồ thống kê top nhân viên bán chạy", pnlBarChart));
      	
		//-------------------------------
		//duoi
      	 pnlDuoi.setBorder(new EmptyBorder(20,0,10,0));
         pnlDuoi.setBackground(Color.decode("#FEFBF6"));
 		
         JLabel lblNV = new JLabel("Nhân viên xuất sắc nhất:");
         lblNV.setFont(new Font("Times New Roman", Font.BOLD, 20));
         txtMaNV = new JTextField(10);
         txtMaNV.setOpaque(false);;
         txtMaNV.setBackground(null);
         txtMaNV.setBorder(null);
         txtMaNV.setEditable(false);
         txtMaNV.setFont(new Font("Times New Roman", Font.BOLD, 25));
 		
 		 JLabel lblTen = new JLabel("Tên NV:");
 		 lblTen.setFont(new Font("Times New Roman", Font.BOLD, 20));
         txtTenNV = new JTextField(10);
         txtTenNV.setOpaque(false);;
         txtTenNV.setBackground(null);
         txtTenNV.setBorder(null);
         txtTenNV.setEditable(false);
         txtTenNV.setFont(new Font("Times New Roman", Font.BOLD, 25));
 		
         pnlDuoi.add(lblNV); pnlDuoi.add(txtMaNV);
         pnlDuoi.add(lblTen); pnlDuoi.add(txtTenNV);
		//-------------------------------
		
		setBackground(Color.BLUE);
		
		setBackground(Color.BLUE);
		
		//event
		btnThongKe.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnThongKe)) {
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
	        
	        // Lấy danh sách nhân viên bán chạy và đưa lên biểu đồ
	        lstObj = thongkeDao.thongKeTopNVBanChay(dateNgayBdau, dateNgayKthuc);
	        System.out.println(lstObj);
	        if (lstObj != null && !lstObj.isEmpty()) {
	            DefaultCategoryDataset data = new DefaultCategoryDataset();
	            for (Object[] obj : lstObj) {
	                data.addValue((double) obj[1], "Tổng tiền", (String) obj[0]);
	            }
	            barChart.getCategoryPlot().setDataset(data);
	            
	            // Lấy dữ liệu nhân viên
	            Object[] obj1 = lstObj.get(0);
	            try {
	                NhanVien nv = nvDao.layNhanVienTheoMa((String) obj1[0]);
	                txtMaNV.setText(nv.getEmployeeCode());
	                txtTenNV.setText(nv.getEmployeeName());
	            } catch (SQLException e1) {
	                e1.printStackTrace();
	            }
	        } else {
	            JOptionPane.showMessageDialog(this, "Không có dữ liệu!");
	            DefaultCategoryDataset data = new DefaultCategoryDataset();
	            data.addValue(0, "Tổng tiền", "không có data nv");
	            barChart.getCategoryPlot().setDataset(data);
	        }	
		}
	}
	 private JPanel pnlChart(String title, ChartPanel chartPanel) {
	        JPanel panel = new JPanel(new BorderLayout());
	        panel.setBackground(Color.decode("#FEFBF6"));
	        JLabel label = new JLabel(title);
	        label.setFont(new Font("Times New Roman", Font.BOLD, 16));
	        panel.add(label, BorderLayout.NORTH);
	        panel.add(chartPanel, BorderLayout.CENTER);
	        return panel;
	 }
	 private static CategoryDataset khoiTaoData() {
	        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	        dataset.addValue(0, "Số tiền", "NV1");
	        dataset.addValue(0, "Số tiền", "NV2");
	        dataset.addValue(0, "Số tiền", "NV3");
	        return dataset;
	    }
}
