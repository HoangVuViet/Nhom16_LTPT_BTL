package ui;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.pdf.PdfWriter;

import connect.ConnectDB;
import dao.ChiTietDonDatDao;
import dao.DonDatThuocDao;
import dao.DonViTinhDao;
import dao.NhanVienDao;
import dao.ThuocDao;
import entity.ChiTietDonDat;
import entity.DonDatThuoc;
import entity.DonViTinh;
import entity.Thuoc;

public class FormChiTietDonNhap extends JFrame implements ActionListener {
	
	private JTable tblCTDN;
	private ChiTietDonDatDao ctdDao;
	private DonDatThuocDao donDatDao;
	private ThuocDao thuocDao;
	private NhanVienDao nvDao;
	private ArrayList<ChiTietDonDat> lstCTDD;
	private DefaultTableModel tblModelCTDN;
	private DecimalFormat formatTien;
	private JTextField txtNgay;
	private JTextField txtGio;
	private JTextField txtNCC;
	private JButton btnIn;
	private JTextField txtTongTien;
	private JLabel lblTieuDe;
	private String maDN;
	private JTextField txtNhanvien;
	private JPanel pnlTren;
	private JPanel pnlGiua;
	private JPanel pnlPhai;
	private DonViTinhDao donviDao;
	private String tongTienString;
	
	public FormChiTietDonNhap(String maDN) throws SQLException {
		
		this.maDN = maDN;
		try {
			ConnectDB.getInstance().connectDataBase();
			System.out.println("Kết nối thành công SQL với Form Chi tiết đơn đặt");
		}catch (SQLException e) {
			e.printStackTrace();
		}
		ctdDao = new ChiTietDonDatDao();
		donDatDao = new DonDatThuocDao();
		thuocDao = new ThuocDao();
		nvDao = new NhanVienDao();
		donviDao = new DonViTinhDao();
		
		DonDatThuoc donDat = donDatDao.layDonDatBangMa(maDN);
		
		lstCTDD = ctdDao.layTatCaChiTietDonBangMaDonNhap(maDN);
		
		setLayout(new BorderLayout());
		pnlTren = new JPanel();
		pnlGiua = new JPanel(new BorderLayout());
		pnlPhai = new JPanel(new BorderLayout());
		
		add(pnlTren, BorderLayout.NORTH);
		add(pnlGiua, BorderLayout.CENTER);
		add(pnlPhai, BorderLayout.EAST);
		
		lblTieuDe = new JLabel("Chi Tiết Đơn Nhập " + maDN);
		lblTieuDe.setFont(new Font("SansSerif", Font.BOLD, 30));
		pnlTren.add(lblTieuDe);
		
		pnlGiua.setBorder(new TitledBorder("Danh sách thuốc"));
		String[] headerCTDN = {"Tên Thuốc","Số Lượng","Đơn Vị", "Đơn Giá(VND)","Thành Tiền(VND)"};
        tblModelCTDN = new DefaultTableModel(headerCTDN, 0);
        tblCTDN = new JTable(tblModelCTDN);
        tblCTDN.setRowHeight(30);
        JScrollPane scrDonNhap = new JScrollPane(tblCTDN);
        pnlGiua.add(scrDonNhap);
		
        pnlPhai.setBorder(new TitledBorder("Thông tin đơn nhập"));
        Box b,b1,b2,b3,b4,b5,b6;
        pnlPhai.add(b = Box.createVerticalBox(), BorderLayout.CENTER);
        b.add(Box.createVerticalStrut(10));
        b.add(b1 = Box.createHorizontalBox());
        JLabel lblNgayTao;
		b1.add(lblNgayTao = new JLabel("Ngày tạo: "));
		b1.add(txtNgay = new JTextField(donDat.getNgayNhap().getDayOfMonth()+"/"+donDat.getNgayNhap().getMonthValue()+"/"+donDat.getNgayNhap().getYear()));
		txtNgay.setOpaque(false); txtNgay.setBorder(null);
		
        //b.add(Box.createVerticalStrut(10));
        b.add(b2 = Box.createHorizontalBox());
        JLabel lblGio;
		b2.add(lblGio = new JLabel("Thời gian: "));
		b2.add(txtGio = new JTextField(donDat.getNgayNhap().getHour()+":"+donDat.getNgayNhap().getMinute()+":"+donDat.getNgayNhap().getSecond()));
		txtGio.setOpaque(false); txtGio.setBorder(null);
		
		JLabel lblNhanVien;
		//b.add(Box.createVerticalStrut(10));
        b.add(b3 = Box.createHorizontalBox());
		b3.add(lblNhanVien = new JLabel("Mã NV: "));
		b3.add(txtNhanvien = new JTextField(donDat.getMaNV()));
		txtNhanvien.setOpaque(false); txtNhanvien.setBorder(null);
		
		//b.add(Box.createVerticalStrut(10));
        b.add(b4 = Box.createHorizontalBox());
        JLabel lblNCC;
		b4.add(lblNCC = new JLabel("Nhà cung cấp: "));
        b4.add(txtNCC = new JTextField(donDat.getNcc()));
        txtNCC.setOpaque(false); txtNCC.setBorder(null);
        
        b.add(b5 = Box.createHorizontalBox());
        JLabel lblTongtien;
		b5.add(lblTongtien = new JLabel("Tổng tiền: "));
		b5.add(txtTongTien = new JTextField());
		txtTongTien.setOpaque(false); txtTongTien.setBorder(null);
		
       // b.add(Box.createVerticalStrut(10));
        b.add(b6 = Box.createHorizontalBox());
        b6.add(btnIn = new JButton("In Đơn"));
        
        pnlGiua.setPreferredSize(new Dimension(900, 300));
        pnlPhai.setPreferredSize(new Dimension(300,300));
        removeAllTableData();
        themDataThuocVaoTable();
        
		setSize(1100,400);
		setTitle("Chi Tiết Đơn Nhập "+ maDN);
		setLocationRelativeTo(null);
		
		btnIn.addActionListener(this);
		
		formatTien = new DecimalFormat();
	}
		public void themDataThuocVaoTable() throws SQLException {
			NumberFormat cf = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
			double tongTien = 0.0;
			for(ChiTietDonDat c: lstCTDD) {
				Thuoc th = thuocDao.layThuocTheoMa(c.getMaThuoc());
				DonViTinh donvi = donviDao.timDonViTheoMa(th.getDonViTinh().getMaDonVi());
					tblModelCTDN.addRow(new Object[] {
							
							th.getTenThuoc(),c.getSoLuong(),donvi.getTenDonVi() ,c.getGiaNhap(),c.getThanhtien()
					});
					tongTien += c.getThanhtien();
			}
			tongTienString = cf.format(tongTien);
			txtTongTien.setText(tongTienString);
		}
		public void removeAllTableData() {
				DefaultTableModel dm = (DefaultTableModel) tblCTDN.getModel();
				dm.getDataVector().removeAllElements();
		}
		@Override
		public void actionPerformed(ActionEvent e) {
	        Object o = e.getSource();
	        if (o.equals(btnIn)) {
				System.out.println("In Đơn nhập (iTextPDF)");

				String folderPath = "/hoaDonNhap";
				File folder = new File(folderPath);
				if (!folder.exists()) {
					folder.mkdirs();
				}

				String outputFile = folderPath +  maDN + ".pdf";

				// Chuẩn bị dữ liệu từ bảng
				List<ChiTietDonDat> lstC = new ArrayList<>();
				for (int i = 0; i < tblCTDN.getRowCount(); i++) {
					String ten = tblCTDN.getValueAt(i, 0).toString();
					int soluong = Integer.parseInt(tblCTDN.getValueAt(i, 1).toString());
					double gianhap = Double.parseDouble(tblCTDN.getValueAt(i, 3).toString());
					double thanhtien = Double.parseDouble(tblCTDN.getValueAt(i, 4).toString());
					lstC.add(new ChiTietDonDat(soluong, ten, gianhap, thanhtien));
				}

				// Lấy thông tin đơn đặt
				DonDatThuoc d = donDatDao.layDonDatBangMa(maDN);

				try {
					com.itextpdf.text.Document document = new com.itextpdf.text.Document();
					PdfWriter.getInstance(document, new FileOutputStream(outputFile));
					document.open();

					String fontPath = "C:\\Windows\\Fonts\\times.ttf";
					com.itextpdf.text.FontFactory.register(fontPath, "TimesNewRoman");
					com.itextpdf.text.Font titleFont = com.itextpdf.text.FontFactory.getFont("TimesNewRoman", 18, com.itextpdf.text.Font.BOLD);
					com.itextpdf.text.Font normalFont = com.itextpdf.text.FontFactory.getFont("TimesNewRoman", 12);
//					com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 18, com.itextpdf.text.Font.BOLD);
//					com.itextpdf.text.Font normalFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12);

					document.add(new com.itextpdf.text.Paragraph("HÓA ĐƠN NHẬP", titleFont));
					document.add(new com.itextpdf.text.Paragraph(" "));
					document.add(new com.itextpdf.text.Paragraph("Mã đơn: " + d.getMaDon(), normalFont));
					document.add(new com.itextpdf.text.Paragraph("Mã nhân viên: " + d.getMaNV(), normalFont));
					document.add(new com.itextpdf.text.Paragraph("Nhà cung cấp: " + d.getNcc(), normalFont));
					document.add(new com.itextpdf.text.Paragraph("Ngày tạo: " + txtNgay.getText(), normalFont));
					document.add(new com.itextpdf.text.Paragraph("Thời gian: " + txtGio.getText(), normalFont));
					document.add(new com.itextpdf.text.Paragraph(" "));

					com.itextpdf.text.pdf.PdfPTable table = new com.itextpdf.text.pdf.PdfPTable(5);
					table.setWidthPercentage(100);
					table.addCell("Tên Thuốc");
					table.addCell("Số Lượng");
					table.addCell("Đơn Vị");
					table.addCell("Đơn Giá (VND)");
					table.addCell("Thành Tiền (VND)");

					for (int i = 0; i < tblCTDN.getRowCount(); i++) {
						table.addCell(tblCTDN.getValueAt(i, 0).toString());
						table.addCell(tblCTDN.getValueAt(i, 1).toString());
						table.addCell(tblCTDN.getValueAt(i, 2).toString());
						table.addCell(tblCTDN.getValueAt(i, 3).toString());
						table.addCell(tblCTDN.getValueAt(i, 4).toString());
					}

					document.add(table);
					document.add(new com.itextpdf.text.Paragraph(" "));
					document.add(new com.itextpdf.text.Paragraph("Tổng tiền: " + txtTongTien.getText(), normalFont));

					document.close();

					System.out.println("Đã in hóa đơn nhập (iTextPDF): " + outputFile);

					// Mở file PDF sau khi in
					File pdfFile = new File(outputFile);
					if (pdfFile.exists()) {
						if (Desktop.isDesktopSupported()) {
							Desktop.getDesktop().open(pdfFile);
						} else {
							System.out.println("Không mở được file: Desktop không hỗ trợ.");
						}
					}
				} catch (Exception ex) {
					System.err.println("Lỗi khi in hóa đơn nhập (iTextPDF): " + ex.getMessage());
				}

				this.dispose();

			}
		}
	
}	        	
	        	


