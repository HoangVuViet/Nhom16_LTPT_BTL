package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;


import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dao.ChiTietHoaDonDao;
import dao.DonViTinhDao;
import dao.HoaDonDao;
import dao.KhachHangDao;
import dao.NhanVienDao;
import dao.TaiKhoanDao;
import dao.ThuocDao;
//import dao.ThuocDaoCu;
import entity.ChiTietHoaDon;
import entity.DonViTinh;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhaCungCap;
import entity.TaiKhoan;
import entity.Thuoc;
import format.ButtonEditor;
import format.ButtonRenderer;
import format.CustomCellRenderer;
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



public class FormTaoHoaDon extends JPanel implements ActionListener {
    private JComboBox<String> cboSP;
    private ArrayList<Thuoc> listThuocChon;
    private JPanel pnlTop, pnlRight, pnlBott, pnlLeft;
    private DefaultTableModel defaultTableModel;
    private JTable tblOrder;
    private JButton btnTong, btnTT, btnXoa, btnXuatHD, btnThuoc, btnKH,btnXacNhan,btnGiam,btnTang;
    private double tongTien;
    private JTextField txtThuoc, txtSoLuong,txtDonGia,txtTT,txtSL;
    private JLabel lblTong,lblKH;
	private JComboBox<String> cboTT;
	private KhachHang khChon;
	private FormTaoKhachHangPhu frmKH;
	private ThuocDao thDao;
	private NumberFormat currencyFormatter;
	private TaiKhoan tkDN;
	private NhanVienDao nvDao;
	private FormTaoThuocPhu frmThuocPhu;
	private String htmlContent;
	private HoaDonDao hdDao;
	private ChiTietHoaDonDao cthdDao;
	private KhachHangDao khDao;
	private DonViTinhDao donviDao;
	
    public FormTaoHoaDon(TaiKhoan tk) throws SQLException {
    	
    	donviDao = new DonViTinhDao();
    	
    	
//    	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	tkDN=tk;
    	currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    	thDao=new ThuocDao();
        setLayout(new BorderLayout());
        pnlLeft = new JPanel();
        pnlLeft.setLayout(new BorderLayout());

        pnlTop = new JPanel();
        pnlTop.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnThuoc = new JButton("Chọn Thuốc");
        btnXoa=new JButton("Xoá Thuốc");
//        txtThuoc = new JTextField(20);
//        JLabel lblSoLuong = new JLabel("Số lượng: ");
//        txtSoLuong = new JTextField(3);
//        JLabel lblDonGia = new JLabel("Đơn giá: ");
//        txtDonGia = new JTextField(20);
//        JLabel lblVND = new JLabel("VNĐ");
        pnlTop.add(btnThuoc);
        pnlTop.add(btnXoa);
//        pnlTop.add(txtThuoc);
//        pnlTop.add(lblSoLuong); pnlTop.add(txtSoLuong);
//        pnlTop.add(lblDonGia); pnlTop.add(txtDonGia); pnlTop.add(lblVND);

        pnlBott = new JPanel(new BorderLayout());
        pnlBott.setBorder(new TitledBorder("Danh sách sản phẩm:"));
        String[] header = {"Mã Thuốc", "Tên Thuốc", "Mô Tả","Số Lượng","Đơn Vị","NSX","HSD(Tháng)", "Đơn Giá(VND)","Thành Tiền(VND)"};
        defaultTableModel = new DefaultTableModel(header, 0) {
        	@Override
            public boolean isCellEditable(int row, int column) {
                // Chỉ cho phép chỉnh sửa cột số lượng (cột thứ 1)
                return column == 3;
            }
        };
        
        tblOrder = new JTable(defaultTableModel);
        JScrollPane scrollPane = new JScrollPane(tblOrder);
        
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
        columnSoLuong.setPreferredWidth(70);
        columnSoLuong.setCellRenderer(centerRenderer);
        TableColumn columnDonVi = tblOrder.getColumnModel().getColumn(4);
        columnDonVi.setPreferredWidth(60);
        columnDonVi.setCellRenderer(centerRenderer);
        TableColumn columnNSX = tblOrder.getColumnModel().getColumn(5);
        columnDonVi.setPreferredWidth(80);
        columnDonVi.setCellRenderer(centerRenderer);
        TableColumn columnHSD = tblOrder.getColumnModel().getColumn(6);
        columnDonVi.setPreferredWidth(60);
        columnDonVi.setCellRenderer(centerRenderer);
        TableColumn columnDonGia = tblOrder.getColumnModel().getColumn(7);
        columnDonGia.setPreferredWidth(90);
        columnDonGia.setCellRenderer(centerRenderer);
        TableColumn columnThanhTien = tblOrder.getColumnModel().getColumn(8);
        columnThanhTien.setPreferredWidth(120);
        columnThanhTien.setCellRenderer(centerRenderer);
        
		  
        pnlBott.add(scrollPane, BorderLayout.CENTER);

        pnlLeft.add(pnlTop, BorderLayout.NORTH);
        pnlLeft.add(pnlBott, BorderLayout.CENTER);

        pnlRight = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        nvDao=new NhanVienDao();
        String tenNV=nvDao.layNhanVienTheoMa(tk.getMaNV()).getEmployeeName();
        
        LocalDateTime now = LocalDateTime.now();
        int day = now.getDayOfMonth();
        int month = now.getMonthValue();
        int year = now.getYear();
        int hour = now.getHour();
        int minute = now.getMinute();
        JLabel lblHour = new JLabel("Thời Gian: " + hour + ":" + minute);
        JLabel lblYear = new JLabel("Ngày: " + day + "/" + month + "/" + year);
        JLabel lblNV = new JLabel("Nhân Viên: "+tenNV);
        btnKH = new JButton("Chọn Khách Hàng:");
        lblKH=new JLabel("Khách Hàng:");
        btnTong = new JButton("Tính Tổng Tiền Đơn Hàng");
        lblTong = new JLabel("Tổng Tiền Đơn Hàng: " + tongTien);
        Box boxTT=Box.createHorizontalBox();
        boxTT.setBorder(BorderFactory.createTitledBorder("Thanh toán:"));
        cboTT=new JComboBox<String>();
        cboTT.addItem("Tiền Mặt");
        cboTT.addItem("Chuyển Khoản");
        txtTT=new JTextField(10);
        boxTT.add(cboTT);
        boxTT.add(txtTT);
//        btnTT = new JButton("Thanh toán");
        JTextArea txtGhiChu = new JTextArea("Ghi Chú", 5, 15);
//        btnTao = new JButton("Tạo đơn hàng");
        btnXuatHD = new JButton("Xuất Hoá Đơn");

        pnlRight.add(lblHour, gbc);
        gbc.gridy++;
        pnlRight.add(lblYear, gbc);
        gbc.gridy++;
        pnlRight.add(lblNV, gbc);
        gbc.gridy++;
        pnlRight.add(btnKH, gbc);
        gbc.gridy++;
        pnlRight.add(lblKH, gbc);
        gbc.gridy++;
//        pnlRight.add(btnTong, gbc);
//        gbc.gridy++;
        pnlRight.add(lblTong, gbc);
        gbc.gridy++;
        pnlRight.add(boxTT, gbc);
        gbc.gridy++;
//        pnlRight.add(btnTao, gbc);
//        gbc.gridy++;
//        pnlRight.add(btnXuatHD, gbc);

        add(pnlLeft, BorderLayout.CENTER);
        add(pnlRight, BorderLayout.EAST);

        defaultTableModel.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 3) {
                	String sl=defaultTableModel.getValueAt(e.getFirstRow(), 3).toString();
                	if(!sl.matches("-?\\d+")) {
                		JOptionPane.showMessageDialog(null,"Số Lượng Chỉ Cho Phép Nhập Số Nguyên!");
                		defaultTableModel.setValueAt(1,e.getFirstRow(),3);
                	}else {
                		try {
						tinhTongTienDH();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
                    }
                }
            }
        });
        
        
        
        
    //sự kiện
        btnKH.addActionListener(this);
        btnThuoc.addActionListener(this);
//        btnTong.addActionListener(this); 
        btnXoa.addActionListener(this);
//        btnTao.addActionListener(this);
//        btnXuatHD.addActionListener(this);
        
    }
    public void layListVaoTable(ArrayList<Thuoc> list) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("vi", "VN"));

        for (Thuoc t : list) {
            DonViTinh d = donviDao.timDonViTheoMa(t.getDonViTinh().getMaDonVi());
            System.out.println(t.getDonViTinh().getMaDonVi());

            // Check if a drug with the same code already exists in the table
            boolean drugExists = false;
            for (int i = 0; i < defaultTableModel.getRowCount(); i++) {
                if (t.getMaThuoc().equals(defaultTableModel.getValueAt(i, 0))) {
                    drugExists = true;
                    break;
                }
            }

            if (!drugExists) {
                defaultTableModel.addRow(new Object[]{
                        t.getMaThuoc(), t.getTenThuoc(), t.getMoTa(),
                        1, // Cột chứa JButton và JTextField
                        d.getTenDonVi(), sdf.format(t.getNgaySanXuat()), t.getHanSD(),
                        currencyFormatter.format(t.getGia()), currencyFormatter.format(t.getGia())
                });
            }
        }
        tinhTongTienDH();
    }

	public void inHD(String maHD) throws SQLException {
		HoaDonDao hdDao = new HoaDonDao();
		HoaDon hd = hdDao.timHoaDonBangMaHD(maHD);
		NumberFormat cf = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

		double tienThoi = hd.getTienNhan() - hd.getTongTien();
		String folderPath = "hoaDon"; // nằm trong thư mục gốc của dự án
		File folder = new File(folderPath);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		String outputFile = folderPath + "/" + hd.getMaHD() + ".pdf";

		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(outputFile));
			document.open();

			// Tiêu đề
			Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
			Paragraph title = new Paragraph("HÓA ĐƠN BÁN HÀNG", titleFont);
			title.setAlignment(Element.ALIGN_CENTER);
			document.add(title);
			document.add(new Paragraph(" "));

			// Thông tin hóa đơn
			document.add(new Paragraph("Mã hóa đơn: " + hd.getMaHD()));
			document.add(new Paragraph("Mã nhân viên: " + hd.getMaNV()));
			document.add(new Paragraph("Mã khách hàng: " + hd.getMaKH()));
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
				int soLuong = Integer.parseInt(tblOrder.getValueAt(i, 3).toString());
				String strTien = tblOrder.getValueAt(i, 8).toString();
				double thanhTien = cf.parse(strTien).doubleValue();

				Thuoc th = thDao.layThuocTheoMa(maSP);
				String tenSP = th.getTenThuoc();

				table.addCell(maSP);
				table.addCell(tenSP);
				table.addCell(String.valueOf(soLuong));
				table.addCell(cf.format(thanhTien));
			}

			document.add(table);
			document.add(new Paragraph(" "));

			// Tổng kết
			document.add(new Paragraph("Tổng tiền: " + cf.format(hd.getTongTien())));
			document.add(new Paragraph("Tiền khách đưa: " + cf.format(hd.getTienNhan())));
			document.add(new Paragraph("Tiền thối lại: " + cf.format(tienThoi)));

			document.close();
			System.out.println("Hóa đơn đã được xuất PDF tại: " + outputFile);

			// ✅ Mở file PDF sau khi in
			File pdfFile = new File(outputFile);
			if (pdfFile.exists()) {
				if (Desktop.isDesktopSupported()) {
					Desktop.getDesktop().open(pdfFile);
				} else {
					System.out.println("Không mở được file: Desktop không được hỗ trợ.");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnKH)) {
        	JFrame frame=new JFrame();
        	frame.setSize(800, 400);
        	frame.setLocationRelativeTo(null);
//            frmKH = null;
			try {
				frmKH = new FormTaoKhachHangPhu();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            frame.add(frmKH);
            JPanel pnlXacNhan=new JPanel();
        	pnlXacNhan.setBorder(new EmptyBorder(0, 10, 10, 10));
        	btnXacNhan = new JButton("Chọn Khách Hàng");
        	pnlXacNhan.add(btnXacNhan);
        	frame.add(pnlXacNhan,BorderLayout.SOUTH);
            frame.setVisible(true);
            btnXacNhan.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Object o =e.getSource();
					if(o.equals(btnXacNhan)) {
						try {
							khChon=frmKH.getKhachHangChon();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if(khChon!=null) {
							
					        lblKH.setText("Khách Hàng: "+khChon.getTenKH()+" - "+khChon.getSDT()+"");
							
							frame.dispose();
						}else {
							JOptionPane.showMessageDialog(null,"Hãy Chọn Khách Hàng!");
						}
				
					}
				}
			});
            
        }
        else if(o.equals(btnThuoc)) {
        	JFrame frmTaoThuocBan=new JFrame();
        	frmTaoThuocBan.setSize(1100, 700);
        	
			try {
				frmThuocPhu = new FormTaoThuocPhu();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
        	frmTaoThuocBan.setLocationRelativeTo(null);
        	frmTaoThuocBan.add(frmThuocPhu);
        	JPanel pnlXacNhan=new JPanel();
        	pnlXacNhan.setBorder(new EmptyBorder(0, 10, 10, 10));
        	btnXacNhan = new JButton("Xác nhận");
        	pnlXacNhan.add(btnXacNhan);
        	frmTaoThuocBan.add(pnlXacNhan,BorderLayout.SOUTH);
        	frmTaoThuocBan.setVisible(true);
        	btnXacNhan.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Object o =e.getSource();
					if(o.equals(btnXacNhan)) {
						try {
							if(frmThuocPhu.getListThuocChon().size()>0) {
								try {
									listThuocChon=frmThuocPhu.getListThuocChon();
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								frmTaoThuocBan.dispose();
								try {
									layListVaoTable(listThuocChon);
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}else {
								JOptionPane.showMessageDialog(null,"Hãy Chọn Thuốc!");
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			});
        	
        }
        else if(o.equals(btnTong)) {
        	try {
				tinhTongTienDH();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        else if(o.equals(btnXoa)) {
       
        	try {
        	 	int r=tblOrder.getSelectedRow();
            	defaultTableModel.removeRow(r);
				tinhTongTienDH();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        
    }
    
    
    
    public boolean XuatHD() throws SQLException {
    	DonViTinhDao dvDao=new DonViTinhDao();
    	for(int i=0;i<tblOrder.getRowCount();i++) {
    		
    		Thuoc th=thDao.layThuocTheoMa(defaultTableModel.getValueAt(i, 0).toString());
    		DonViTinh dv=dvDao.timDonViTheoMa(th.getDonViTinh().getMaDonVi());
    		int soLuong=Integer.parseInt(defaultTableModel.getValueAt(i, 3).toString().replaceAll("[^\\d.]", ""));
    		if(soLuong>th.getSoLuong()) {
    			JOptionPane.showMessageDialog(null,"Thuốc "+th.getMaThuoc()+" - "+th.getTenThuoc()+" Chỉ Còn "+th.getSoLuong()+" "+dv.getTenDonVi()+"!");
    			return false;
    		}
    	}
    	if(khChon==null) {
    		JOptionPane.showMessageDialog(null, "Khách Hàng Không Được Để Trống!");
    		return false;
    	}else if(tblOrder.getRowCount()<1) {
    		JOptionPane.showMessageDialog(null, "Thuốc Không Được Để Trống!");
    		return false;
    	}else {
    		String tt=txtTT.getText().trim();
        	if(!tt.matches("-?\\d+")) {
        		JOptionPane.showMessageDialog(null, "Tiền Thanh Toán Phải Nhập Số!");
        		txtTT.setText("");
        		return false;
        	}else {
        		double tienTT=Double.parseDouble(tt);
        		if(tienTT<tongTien){
            		JOptionPane.showMessageDialog(null,"Tiền Thanh Toán Không Được Ít Hơn Tổng Tiền Hoá Đơn!");
            		return false;
        		}else {
            		int y=JOptionPane.showConfirmDialog(btnXuatHD, "Xuất Hoá Đơn?","Xác Nhận Xuất Hoá Đơn",JOptionPane.YES_NO_OPTION);
                	if(y==0) {
                		String maKH=khChon.getMaKH();
                    	String maNV=tkDN.getMaNV();
                    	LocalDateTime thoiGian=LocalDateTime.now();
                    	String pThucTT=cboTT.getSelectedItem().toString();
                    	double tienNhan=Double.parseDouble(txtTT.getText().trim());
                    	if(tienNhan>tongTien) {
                    		double thoi=tienNhan- tongTien;
                    		JOptionPane.showMessageDialog(null,"Hoàn Cho Khách "+currencyFormatter.format(thoi)+"!");
                    	}
                    	HoaDon hd=new HoaDon(maKH, maNV, thoiGian, tongTien, tienNhan, pThucTT);
                    	HoaDonDao hdDao=new HoaDonDao();
                    	String maHDTao=null;
                    	try {
            				hdDao.taoHoaDon(hd);
            			} catch (SQLException e1) {
            				// TODO Auto-generated catch block
            				e1.printStackTrace();
            			}
                    	try {
            				maHDTao=hdDao.layMaHDMoiTao();
            			} catch (SQLException e1) {
            				// TODO Auto-generated catch block
            				e1.printStackTrace();
            			}
                    	ChiTietHoaDonDao cthdDao=new ChiTietHoaDonDao();
                    	
                    	ArrayList<ChiTietHoaDon> ctHD=new ArrayList<ChiTietHoaDon>();
                    	for(int i=0;i<tblOrder.getRowCount();i++) {
                    		Thuoc th=thDao.layThuocTheoMa(defaultTableModel.getValueAt(i, 0).toString());
                    		int soLuong=Integer.parseInt(defaultTableModel.getValueAt(i, 3).toString().replaceAll("[^\\d.]", ""));
                    		ChiTietHoaDon ctHoaDon=new ChiTietHoaDon(maHDTao,(String)defaultTableModel.getValueAt(i, 0), 
                    				soLuong, soLuong*th.getGia());
                    		try {
            					cthdDao.taoChiTietHoaDon(ctHoaDon);
            				} catch (SQLException e1) {
            					// TODO Auto-generated catch block
            					e1.printStackTrace();
            				}
                    		try {
            					thDao.truSoLuongSanPhamBangMaSP(Integer.parseInt(defaultTableModel.getValueAt(i, 3).toString().replaceAll("[^\\d.]", "")),(String)defaultTableModel.getValueAt(i, 0));
            				} catch (NumberFormatException | SQLException e1) {
            					// TODO Auto-generated catch block
            					e1.printStackTrace();
            				}
                    	}
                    	System.out.println("Xuat HD thanh cong!");
                    	return true;
                	}
            	}
        	}
    	}
		return false;
    }
    public void tinhTongTienDH() throws SQLException {
    	int columnIndex = 8; // Chỉ số cột "ThanhTien"
        ArrayList<Double> listTienSP=new ArrayList<Double>();
        Double tong=0.0;
        if(tblOrder.getRowCount()>0) {
        	for (int row = 0; row < defaultTableModel.getRowCount(); row++) {
            	Double sum = 0.0;
            	Thuoc t=thDao.layThuocTheoMa(defaultTableModel.getValueAt(row,0).toString());
                Double value =t.getGia();
                int soLuong=Integer.parseInt(defaultTableModel.getValueAt(row, 3).toString());
                if (value instanceof Double) {
                    sum = soLuong* value;
                }
                listTienSP.add(sum);
            }
            for(int i=0;i<tblOrder.getRowCount();i++) {
            	defaultTableModel.setValueAt(currencyFormatter.format(listTienSP.get(i)), i, 8);
            }
            for(Double d:listTienSP) {
            	tong=tong+d;
            }
        }
        tongTien=tong;
        lblTong.setText("Tổng Tiền Đơn Hàng: "+currencyFormatter.format(tongTien));
    }
//    public static void main(String[] args) {
//		JFrame n=new JFrame();
//		n.add(new FormTaoHoaDon(tk));
//		n.setVisible(true);
//	}
}
