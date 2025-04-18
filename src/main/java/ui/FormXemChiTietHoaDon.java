package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;


import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
//import javax.swing.text.Document;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.w3c.dom.ls.LSException;

import dao.ChiTietHoaDonDao;
import dao.DonViTinhDao;
import dao.HoaDonDao;
import dao.KhachHangDao;
import dao.NhanVienDao;
import dao.ThuocDao;
import entity.ChiTietHoaDon;
import entity.DonViTinh;
import entity.HoaDon;
import entity.KhachHang;
import entity.Thuoc;
import format.PrintPreviewDialog;
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

public class FormXemChiTietHoaDon extends JFrame {
	private JPanel pnlTable,pnlRight,pnlIn,pnlThongTin;
	private JButton btnIn;
	private DefaultTableModel defaultTableModel;
	private JTable tblOrder;
	private LocalDateTime time;
	private HoaDon hd;
	private HoaDonDao hdDao;
	private ChiTietHoaDonDao cthdDao;
	private ArrayList<ChiTietHoaDon> listCTHD;
	private ThuocDao thDao;
	private NhanVienDao nvDao;
	private KhachHangDao khDao;
	private String htmlContent;
	private DonViTinhDao donviDao;

	public FormXemChiTietHoaDon(String maHD) throws SQLException {
		hdDao=new HoaDonDao();
		cthdDao=new ChiTietHoaDonDao();
		thDao=new ThuocDao();
		nvDao=new NhanVienDao();
		khDao=new KhachHangDao();
		donviDao = new DonViTinhDao();
		
		
		hd=hdDao.timHoaDonBangMaHD(maHD);
		listCTHD=cthdDao.layTatCaChiTietHoaDonTheoMaHD(maHD);
		setSize(1100,400);
		setTitle("Chi Tiết "+maHD);
		setLocationRelativeTo(null);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pnlTable = new JPanel(new BorderLayout());
        pnlTable.setBorder(new TitledBorder("Danh sách sản phẩm"));
        String[] header = {"Mã Thuốc", "Tên Thuốc", "Mô Tả","Số Lượng","Đơn Vị", "NSX","HSD(Tháng)","Đơn Giá(VND)","Thành Tiền(VND)"};
        defaultTableModel = new DefaultTableModel(header, 0) {
        	@Override
            public boolean isCellEditable(int row, int column) {
                // Chỉ cho phép chỉnh sửa cột số lượng (cột thứ 1)
                return column ==-1;
            }
        };
        
        tblOrder = new JTable(defaultTableModel);
        JScrollPane scrollPane = new JScrollPane(tblOrder);
        scrollPane.setPreferredSize(new Dimension(800,350));
        tblOrder.setRowHeight(30);
//        tblOrder.getCo
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        TableColumn columnMaThuoc = tblOrder.getColumnModel().getColumn(0);
        columnMaThuoc.setPreferredWidth(80);
        columnMaThuoc.setCellRenderer(centerRenderer);
        TableColumn columnTen = tblOrder.getColumnModel().getColumn(1);
        columnTen.setPreferredWidth(100);
        columnTen.setCellRenderer(centerRenderer);
        TableColumn columnMoTa = tblOrder.getColumnModel().getColumn(2);
        columnMoTa.setPreferredWidth(130);
        columnMoTa.setCellRenderer(centerRenderer);
        TableColumn columnSoLuong = tblOrder.getColumnModel().getColumn(3);
        columnSoLuong.setPreferredWidth(80);
        columnSoLuong.setCellRenderer(centerRenderer);
        TableColumn columnDonVi = tblOrder.getColumnModel().getColumn(4);
        columnDonVi.setPreferredWidth(100);
        TableColumn columnNSX = tblOrder.getColumnModel().getColumn(5);
        columnDonVi.setPreferredWidth(100);
        TableColumn columnHSD = tblOrder.getColumnModel().getColumn(6);
        columnDonVi.setPreferredWidth(100);
        columnDonVi.setCellRenderer(centerRenderer);
        TableColumn columnDonGia = tblOrder.getColumnModel().getColumn(7);
        columnDonGia.setPreferredWidth(110);
        columnDonGia.setCellRenderer(centerRenderer);
        TableColumn columnThanhTien = tblOrder.getColumnModel().getColumn(8);
        columnThanhTien.setPreferredWidth(130);
        columnThanhTien.setCellRenderer(centerRenderer);
        pnlTable.add(scrollPane);
        
        add(pnlTable,BorderLayout.WEST);
        
        pnlThongTin=new JPanel();
        pnlThongTin.setPreferredSize(new Dimension(300,350));
        pnlRight = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        pnlRight.setBorder(BorderFactory.createTitledBorder("Thông Tin Đơn Hàng"));
//        bTime=Box.createHorizontalBox();
        
        time=hd.getThoiGianXuatHD();
        int day = time.getDayOfMonth();
        int month = time.getMonthValue();
        int year = time.getYear();
        int hour = time.getHour();
        int minute = time.getMinute();
        JLabel lblHour = new JLabel("Thời gian: " + hour + ":" + minute);
        JLabel lblYear = new JLabel("Ngày: " + day + "/" + month + "/" + year);        
        pnlRight.add(lblHour,gbc);
        gbc.gridy++;
        pnlRight.add(lblYear,gbc);
        gbc.gridy++;
        gbc.gridy++;
        gbc.gridy++;
        gbc.gridy++;
        JLabel lblNV = new JLabel("Nhân Viên: "+nvDao.layNhanVienTheoMa(hd.getMaNV()).getEmployeeName());
        JLabel lblKH=new JLabel();
        KhachHang kh=khDao.layKhachHangTheoMaKH(hd.getMaKH());
        lblKH.setText("Khách Hàng: "+kh.getTenKH()+" - "+kh.getSDT()+"");
        pnlRight.add(lblNV,gbc);
        gbc.gridy++;
        pnlRight.add(lblKH,gbc);
        gbc.gridy++;
        
        JLabel lblTong = new JLabel("Tổng Tiền Đơn Hàng: " + hd.getTongTien());
        pnlRight.add(lblTong,gbc);
        gbc.gridy++;
        
        JLabel lblTT=new JLabel("Đã Thanh Toán: "+hd.getTienNhan()+" - "+hd.getPhuongThucTT());
        pnlRight.add(lblTT,gbc);
        gbc.gridy++;
        pnlThongTin.add(pnlRight,BorderLayout.NORTH);
      
        pnlIn=new JPanel();
        btnIn=new JButton("In Hoá Đơn");
        pnlIn.add(btnIn);
        pnlThongTin.add(pnlIn,BorderLayout.SOUTH);
        
        add(pnlThongTin,BorderLayout.CENTER);
        
        removeAllTableData();
        layChiTietHDVaoTable(listCTHD);
        btnIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("in");
                String folderPath = "hoaDon"; // không dùng / để tránh lỗi đường dẫn gốc
                File folder = new File(folderPath);
                if (!folder.exists()) {
                    folder.mkdirs();
                }

                String outputFile = folderPath + "/" + hd.getMaHD() + ".pdf";

                NumberFormat cf = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                double tienThoi = hd.getTienNhan() - hd.getTongTien();

                try {
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream(outputFile));
                    document.open();

                    Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
                    Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

                    Paragraph title = new Paragraph("HÓA ĐƠN BÁN HÀNG", titleFont);
                    title.setAlignment(Element.ALIGN_CENTER);
                    document.add(title);
                    document.add(new Paragraph(" "));

                    // Thông tin hóa đơn
                    document.add(new Paragraph("Mã hóa đơn: " + hd.getMaHD(), normalFont));
                    document.add(new Paragraph("Mã nhân viên: " + hd.getMaNV(), normalFont));
                    document.add(new Paragraph("Mã khách hàng: " + hd.getMaKH(), normalFont));
                    document.add(new Paragraph(" "));

                    // Bảng sản phẩm
                    PdfPTable table = new PdfPTable(4);
                    table.setWidthPercentage(100);
                    table.addCell("Mã SP");
                    table.addCell("Tên SP");
                    table.addCell("Số lượng");
                    table.addCell("Thành tiền");

                    for (int i = 0; i < tblOrder.getRowCount(); i++) {
                        String maSP = tblOrder.getValueAt(i, 0).toString();
                        String tenSP = tblOrder.getValueAt(i, 1).toString();
                        String soLuong = tblOrder.getValueAt(i, 3).toString();
                        String thanhTienStr = tblOrder.getValueAt(i, 8).toString();

                        double thanhTien = cf.parse(thanhTienStr).doubleValue();

                        table.addCell(maSP);
                        table.addCell(tenSP);
                        table.addCell(soLuong);
                        table.addCell(cf.format(thanhTien));
                    }

                    document.add(table);
                    document.add(new Paragraph(" "));

                    // Tổng kết
                    document.add(new Paragraph("Tổng tiền: " + cf.format(hd.getTongTien()), normalFont));
                    document.add(new Paragraph("Tiền khách đưa: " + cf.format(hd.getTienNhan()), normalFont));
                    document.add(new Paragraph("Tiền thối lại: " + cf.format(tienThoi), normalFont));

                    document.close();
                    try {
                        File pdfFile = new File(outputFile);
                        if (pdfFile.exists()) {
                            if (Desktop.isDesktopSupported()) {
                                Desktop.getDesktop().open(pdfFile);
                            } else {
                                System.out.println("Mở file không được: Desktop không được hỗ trợ.");
                            }
                        } else {
                            System.out.println("File không tồn tại: " + outputFile);
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    System.out.println("Đã xuất hóa đơn PDF: " + outputFile);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

    }


	 public void removeAllTableData() {
			DefaultTableModel dm = (DefaultTableModel) tblOrder.getModel();
			dm.getDataVector().removeAllElements();
		}
	public void layChiTietHDVaoTable(ArrayList<ChiTietHoaDon> listCTHD) throws SQLException {
		NumberFormat cf = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy",new Locale("vi","VN"));
		for(ChiTietHoaDon c:listCTHD) {
			Thuoc t=thDao.layThuocTheoMa(c.getMaSP());
			DonViTinh d = donviDao.timDonViTheoMa(t.getDonViTinh().getMaDonVi());
			
			defaultTableModel.addRow(new Object[] {
					c.getMaSP(),t.getTenThuoc(),t.getMoTa(),c.getSoLuong(),d.getTenDonVi(),sdf.format(t.getNgaySanXuat()),
					t.getHanSD(),cf.format(t.getGia()),cf.format(c.getSoLuong()*t.getGia())
			});
		}
	}
	
	public static void main(String[] args) throws SQLException {
		new FormXemChiTietHoaDon("HD00000001").setVisible(true);
	}
}
