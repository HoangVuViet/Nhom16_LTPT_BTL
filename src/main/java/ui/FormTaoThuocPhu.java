package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import connect.ConnectDB;
import dao.DonDatThuocDao;
import dao.DonViTinhDao;
import dao.LoaiThuocDao;
import dao.ThuocDao;
//import dao.ThuocDaoCu;
import entity.DonDatThuoc;
import entity.DonViTinh;
import entity.LoaiThuoc;
import entity.Thuoc;
import format.ImageIconCustom;
import format.PanelBorder;

public class FormTaoThuocPhu extends JPanel implements ActionListener,MouseListener{
	
	private JTextField txtTenThuoc,txtMaThuoc;
    private JButton btnTimKiem, btnXacNhan,btnChonThuoc,btnBoChon;
    private JLabel lblTenThuoc, lblLoaiThuoc, lblNCC, lblTieuDe;
    
    private JComboBox<String> cboLoaiThuoc, cboNCC;
	private ArrayList<Thuoc> listThuocChon;
	private ThuocDao thuocDao;
	private JTable tblThuocChon,tblThuoc;
	private DefaultTableModel model,model2;
	private DonDatThuocDao dDao;
	private LoaiThuocDao loaiDao;
	private DonViTinhDao donviDao;
	private NumberFormat currencyFormatter;
	private JButton btnLamMoi;
	private JLabel lblHinhanhThuoc;
	private JButton btnDoiHinhanh;
	private PanelBorder pnlHinhAnh;
	private byte[] hinhThuocByte;
	
	public FormTaoThuocPhu() throws SQLException {
		try {
			ConnectDB.getInstance().connectDataBase();
			System.out.println("Succes connect to FormThuocBan");
		}catch (SQLException e) {
			e.printStackTrace();
		}
		currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
		dDao=new DonDatThuocDao();
		thuocDao=new ThuocDao();
		loaiDao=new LoaiThuocDao();
		donviDao=new DonViTinhDao();
		ArrayList<Thuoc> listThuocTim=new ArrayList<Thuoc>();
		listThuocChon=new ArrayList<Thuoc>();
		
//        setLocationRelativeTo(null);

//        JPanel contentPane = new JPanel();
//        contentPane.setLayout(new BorderLayout());
//        setContentPane(contentPane);

        // Panel bên trái
        JPanel pnlTrai = new JPanel();
        pnlTrai.setPreferredSize(new Dimension(200, 600)); // Kích thước
        Border emptyBorder = BorderFactory.createEmptyBorder(20, 10, 14, 12);
        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
        Border compoundBorder = BorderFactory.createCompoundBorder(emptyBorder, lineBorder);
        pnlTrai.setBorder(compoundBorder);
        
        pnlTrai.setBackground(null); // Xoá background
        lblHinhanhThuoc = new JLabel();
        lblHinhanhThuoc.setIcon(new ImageIconCustom("/images/drugstore.png", 130, 130));
       
       
        pnlHinhAnh = new PanelBorder();
        pnlHinhAnh.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        pnlHinhAnh.setBackground(new Color(250, 250, 250));
        pnlHinhAnh.add(lblHinhanhThuoc);
    
        pnlTrai.add(pnlHinhAnh);
        
        Box b = Box.createVerticalBox();
       
        lblTenThuoc = new JLabel("Tên thuốc:");
        lblLoaiThuoc = new JLabel("Loại thuốc:");
//        lblNCC = new JLabel("Nhà cung cấp:");

        Font labelFont = lblTenThuoc.getFont().deriveFont(Font.PLAIN, 16);
        lblTenThuoc.setFont(labelFont);
        lblLoaiThuoc.setFont(labelFont);
//        lblNCC.setFont(labelFont);

        txtTenThuoc = new JTextField();
        cboLoaiThuoc = new JComboBox<String>();
        ArrayList<LoaiThuoc> loaiList=loaiDao.timTatCaLoaiThuoc();
        ArrayList<String> listLoai=new ArrayList<String>();
        for(LoaiThuoc l:loaiList) {
        	listLoai.add(l.getLoaiThuoc());
        }
        
        ArrayList<String> listLoaiThuoc=loaiDao.layDanhSachLoaiThuocKhongTrung(listLoai);
        for(String s:listLoaiThuoc) {
        	cboLoaiThuoc.addItem(s);
        }
        cboLoaiThuoc.setSelectedItem(null);
        lblTenThuoc.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblLoaiThuoc.setAlignmentX(Component.CENTER_ALIGNMENT);
//        lblNCC.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lblMaThuoc=new JLabel("Mã thuốc:");
        lblMaThuoc.setFont(labelFont);
        lblMaThuoc.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        txtMaThuoc=new JTextField();
        b.add(Box.createVerticalStrut(10));
        b.add(lblMaThuoc);
        b.add(Box.createVerticalStrut(10));
        b.add(txtMaThuoc);
        b.add(Box.createVerticalStrut(10)); 
        b.add(lblTenThuoc);
        b.add(Box.createVerticalStrut(10));
        b.add(txtTenThuoc);
        b.add(Box.createVerticalStrut(15)); 
        b.add(lblLoaiThuoc);
        b.add(Box.createVerticalStrut(10)); 
        b.add(cboLoaiThuoc);
//        b.add(Box.createVerticalStrut(15)); 
//        b.add(lblNCC);
//        b.add(Box.createVerticalStrut(10)); 
//        b.add(cboNCC);
        b.add(Box.createVerticalStrut(20)); 
        btnTimKiem = new JButton("Tìm Kiếm");
        btnTimKiem.setAlignmentX(CENTER_ALIGNMENT); 
        b.add(btnTimKiem);
        b.add(Box.createVerticalStrut(30));
        btnLamMoi=new JButton("Làm Mới");
        btnLamMoi.setAlignmentX(CENTER_ALIGNMENT); 
        b.add(btnLamMoi);
        b.add(Box.createVerticalStrut(30));
        btnChonThuoc=new JButton("Chọn Thuốc");
        btnChonThuoc.setAlignmentX(CENTER_ALIGNMENT); 
        b.add(btnChonThuoc);
        b.add(Box.createVerticalStrut(30));
        btnBoChon=new JButton("Bỏ Chọn");
        btnBoChon.setAlignmentX(CENTER_ALIGNMENT);
        b.add(btnBoChon);
        pnlTrai.add(b, BorderLayout.CENTER);

        add(pnlTrai, BorderLayout.WEST);

        // Panel ở giữa và dưới cùng giữ nguyên
        JPanel pnlPhai=new JPanel();
        JPanel pnlGiua = new JPanel();
        pnlGiua.setLayout(new BorderLayout());
        pnlGiua.setBorder(new EmptyBorder(10, 0, 10, 10));
        
        JPanel pnlTimKiem = new JPanel();
        pnlTimKiem.setLayout(new FlowLayout(FlowLayout.CENTER));
        lblTieuDe = new JLabel("TÌM KIẾM THUỐC");
        lblTieuDe.setFont(new Font(getName(), Font.BOLD, 27));
        pnlTimKiem.add(lblTieuDe);

        pnlGiua.add(pnlTimKiem, BorderLayout.NORTH);

        JPanel pnlThuocTim=new JPanel();
        pnlThuocTim.setBorder(BorderFactory.createTitledBorder("Danh sách thuốc đã tìm:"));
        
        model = new DefaultTableModel();
        model.addColumn("Mã Thuốc");
        model.addColumn("Tên Thuốc");
        model.addColumn("Loại Thuốc");
        model.addColumn("Tồn Kho");
        model.addColumn("Đơn Vị");
        model.addColumn("HSD(Tháng)");
        model.addColumn("Đơn Giá");
        model.addColumn("Ngày Sản Xuất");
        model.addColumn("Mô tả");
       
        tblThuoc = new JTable(model);
        TableColumn tTen=tblThuoc.getColumnModel().getColumn(1);
        tTen.setPreferredWidth(150);
//        TableColumn tTon=tblThuoc.getColumnModel().getColumn(3);
//        tTen.setPreferredWidth(120);
        
        pnlThuocTim.setSize(750, 400);
        JScrollPane scrollPane = new JScrollPane(tblThuoc);
        scrollPane.setPreferredSize(new Dimension(750, 180));
        pnlThuocTim.add(scrollPane);
        pnlGiua.add(pnlThuocTim, BorderLayout.CENTER);
      
        JPanel pnlThuocChon=new JPanel();
        pnlThuocChon.setBorder(BorderFactory.createTitledBorder("Danh Sách Thuốc Đã Chọn:"));
        model2 = new DefaultTableModel();
        model2.addColumn("Mã Thuốc");
        model2.addColumn("Tên Thuốc");
        model2.addColumn("Loại Thuốc");
        model2.addColumn("Tồn Kho");
        model2.addColumn("Đơn Vị");
        model2.addColumn("HSD(Tháng)");
        model2.addColumn("Đơn Giá");
        model2.addColumn("Ngày Sản Xuất");
        model2.addColumn("Mô Tả");
        tblThuocChon=new JTable(model2);
        TableColumn tTen2=tblThuocChon.getColumnModel().getColumn(1);
        tTen2.setPreferredWidth(150);
//        tblThuocChon.setPreferredSize(new Dimension(750, 300));
        JScrollPane sPane=new JScrollPane(tblThuocChon);
        pnlThuocChon.setSize(750, 400);
        sPane.setPreferredSize(new Dimension(750, 300));
        pnlThuocChon.add(sPane);
        pnlGiua.add(pnlThuocChon,BorderLayout.SOUTH);
        
        pnlPhai.add(pnlGiua, BorderLayout.CENTER);
        add(pnlPhai,BorderLayout.EAST);
        
        
        nhapDuLieuThuocLenTable();
        
//        JPanel pnlDuoi = new JPanel();
//        pnlDuoi.setBorder(new EmptyBorder(0, 10, 10, 10));
//        btnXacNhan = new JButton("Xác nhận");
//        pnlDuoi.add(btnXacNhan);

//        add(pnlDuoi, BorderLayout.SOUTH);
//        btnXacNhan.addActionListener(this);
        btnTimKiem.addActionListener(this);
        btnChonThuoc.addActionListener(this);
        btnBoChon.addActionListener(this);
        btnLamMoi.addActionListener(this);
        tblThuoc.addMouseListener(this);
        tblThuocChon.addMouseListener(this);
//        btnXacNhan.addActionListener(this);
	}
	public ArrayList<Thuoc> getListThuocChon() throws SQLException{
		listThuocChon=new ArrayList<Thuoc>();
		for(int i=0;i<tblThuocChon.getRowCount();i++) {
			String ma=model2.getValueAt(i, 0).toString();
			Thuoc t=thuocDao.layThuocTheoMa(ma);
			listThuocChon.add(t);
		}
		return listThuocChon;
	}
	public void removeAllTableData() {
		DefaultTableModel dm = (DefaultTableModel) tblThuoc.getModel();
		dm.getDataVector().removeAllElements();
	}
	private void nhapDuLieuThuocLenTable() throws SQLException {
		
		List<Thuoc> lstThuoc = thuocDao.timTatCaThuoc();
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		
		for(int i = 0;i<lstThuoc.size();i++) {
//			if(thuoc.getMaDonNhap()==null) {
//				continue;
//			}else
			Thuoc thuoc=lstThuoc.get(i);
			LoaiThuoc loai=loaiDao.timLoaiThuocTheoMa(thuoc.getLoaiThuoc().getId());
			DonViTinh donvi=donviDao.timDonViTheoMa(thuoc.getDonViTinh().getMaDonVi());
			
			if(thuoc.getSoLuong()>0) {
				{
					model.addRow(new Object[] {
							thuoc.getMaThuoc(), thuoc.getTenThuoc(),loai.getLoaiThuoc(),thuoc.getSoLuong(),
							donvi.getTenDonVi(), thuoc.getHanSD(),currencyFormatter.format(thuoc.getGia()),sdf.format(thuoc.getNgaySanXuat()),thuoc.getMoTa()
					});
				}
			}
		}
	}
	
	private void nhapDuLieuThuocTuListLenTable(ArrayList<Thuoc> lstThuoc) throws SQLException {
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		
		for(int i = 0;i<lstThuoc.size();i++) {
//			if(thuoc.getMaDonNhap()==null) {
//				continue;
//			}else
			Thuoc thuoc=lstThuoc.get(i);
			LoaiThuoc loai=thuoc.getLoaiThuoc();
			DonViTinh donvi=donviDao.timDonViTheoMa(thuoc.getDonViTinh().getMaDonVi());
			if(thuoc.getSoLuong()>0) {
				{
					model.addRow(new Object[] {
							thuoc.getMaThuoc(), thuoc.getTenThuoc(),loai.getLoaiThuoc(),thuoc.getSoLuong(),
							donvi.getTenDonVi(), thuoc.getHanSD(),currencyFormatter.format(thuoc.getGia()),sdf.format(thuoc.getNgaySanXuat()),thuoc.getMoTa()
					});
				}
			}
		}
	}
	
	public Date getHanSuDung(Date nsx,int hsd) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nsx);
        calendar.add(Calendar.MONTH, hsd);
        return calendar.getTime();
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnTimKiem)) {
			String ma=txtMaThuoc.getText().trim();
			String ten=txtTenThuoc.getText().trim();
			String loai="";
			ArrayList<Thuoc> listT=new ArrayList<Thuoc>();
			
			
			if(cboLoaiThuoc.getSelectedItem()!=null) {
				loai=cboLoaiThuoc.getSelectedItem().toString();			
			}
			try {
				
				listT=thuocDao.timThuocTheoMaTenLoai(ma, ten, loai);
				if(listT.size()>0) {
					System.out.println("Tìm Thấy!");
					DefaultTableModel dm = (DefaultTableModel) tblThuoc.getModel();
					dm.getDataVector().removeAllElements();
					SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
					for(Thuoc thuoc:listT) {
						LoaiThuoc loai1=thuoc.getLoaiThuoc();
						DonViTinh donvi;
						try {
							donvi = donviDao.timDonViTheoMa(thuoc.getDonViTinh().getMaDonVi());
							model.addRow(new Object[] {
									thuoc.getMaThuoc(), thuoc.getTenThuoc(),loai1.getLoaiThuoc(),thuoc.getSoLuong(),
									donvi.getTenDonVi(), thuoc.getHanSD(),currencyFormatter.format(thuoc.getGia()),sdf.format(thuoc.getNgaySanXuat()),thuoc.getMoTa()
							});
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}		
					}
				}else {
					JOptionPane.showMessageDialog(null,"Không Tìm Thấy Thuốc!");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(o.equals(btnChonThuoc)) {
			Thuoc t=null;
			int r=tblThuoc.getSelectedRow();
			String m=model.getValueAt(r, 0).toString();
			try {
				t=thuocDao.layThuocTheoMa(m);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//			DonDatThuocDao dDao = new DonDatThuocDao();
			SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
			boolean isSelected=false;
			for(int i=0;i<tblThuocChon.getRowCount();i++) {
				if(m.equalsIgnoreCase(model2.getValueAt(i, 0).toString())) {
					isSelected=true;
				}
			}
	        LocalDate now = LocalDate.now(); // Lấy ngày hiện tại
			LocalDate nsx = t.getNgaySanXuat().toLocalDate(); // Lấy ngày sản xuất của Thuoc
	        int hanSD = t.getHanSD(); // Lấy hạn sử dụng của Thuoc (số tháng)
	        LocalDate han = nsx.plusMonths(hanSD);
			if(isSelected==false && t.getSoLuong()>0 && han.isAfter(now.plusDays(7))) {
				Thuoc thuoc=t;
				LoaiThuoc loai;
				DonViTinh donvi;
				try {
					loai=loaiDao.timLoaiThuocTheoMa(thuoc.getLoaiThuoc().getId());
					donvi = donviDao.timDonViTheoMa(thuoc.getDonViTinh().getMaDonVi());
					model2.addRow(new Object[] {
							thuoc.getMaThuoc(), thuoc.getTenThuoc(),loai.getLoaiThuoc(),thuoc.getSoLuong(),
							donvi.getTenDonVi(), thuoc.getHanSD(),currencyFormatter.format(thuoc.getGia()),sdf.format(thuoc.getNgaySanXuat()),thuoc.getMoTa()
					});
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else if(isSelected==false && t.getSoLuong()>0 && han.isAfter(now)) {
				int x=JOptionPane.showConfirmDialog(null,"Thuốc Đã Chọn Chỉ Còn Hạn "+ChronoUnit.DAYS.between(now,han)+"Ngày. Bạn Có Muốn Chọn?","Xác Nhận Chọn Thuốc",JOptionPane.YES_NO_OPTION);
				if(x==0) {
					Thuoc thuoc=t;
					LoaiThuoc loai;
					DonViTinh donvi;
					try {
						loai=loaiDao.timLoaiThuocTheoMa(thuoc.getLoaiThuoc().getId());
						donvi = donviDao.timDonViTheoMa(thuoc.getDonViTinh().getMaDonVi());
						model2.addRow(new Object[] {
								thuoc.getMaThuoc(), thuoc.getTenThuoc(),loai.getLoaiThuoc(),thuoc.getSoLuong(),
								donvi.getTenDonVi(), thuoc.getHanSD(),currencyFormatter.format(thuoc.getGia()),sdf.format(thuoc.getNgaySanXuat()),thuoc.getMoTa()
						});
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}else {
				if(t.getSoLuong()<1) {
					JOptionPane.showMessageDialog(null, "Thuốc Đã Hết Số Lượng!");
				}else if(han.isBefore(now)){
					JOptionPane.showMessageDialog(null, "Thuốc Đã Hết Hạn Sử Dụng!");
				}else {
					JOptionPane.showMessageDialog(null, "Thuốc Đã Được Chọn!");
				}
			}
		}
		else if(o.equals(btnBoChon)) {
			int r=tblThuocChon.getSelectedRow();
			model2.removeRow(r);
		}else if(o.equals(btnLamMoi)) {
			removeAllTableData();
			try {
				nhapDuLieuThuocLenTable();
				txtMaThuoc.setText("");
				txtTenThuoc.setText("");
				cboLoaiThuoc.setSelectedItem(null);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	public static void main(String[] args) throws SQLException {
		JFrame n=new JFrame();
		n.add(new FormTaoThuocPhu());
		n.setVisible(true);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		
		
		if(o.equals(tblThuoc)) {
			int r = tblThuoc.getSelectedRow();
			ArrayList<Thuoc> lstThuoc = new ArrayList<>();
			try {
				lstThuoc = thuocDao.timThuocTheoMa(tblThuoc.getValueAt(r, 0).toString());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			Thuoc t=lstThuoc.get(0);
			if(t.getHinhMinhHoa()!=null) {
				
				
				for(Thuoc th:lstThuoc) {
					if(th.getHinhMinhHoa() != null) {
						hinhThuocByte = th.getHinhMinhHoa();
						ImageIcon icon = new ImageIcon(hinhThuocByte);
						Image image = icon.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
						lblHinhanhThuoc.setIcon(new ImageIcon(image));
					}
				}
			}
			else {
				lblHinhanhThuoc.setIcon(new ImageIconCustom("/images/hinhRong.png", 130, 130));
			}
		}
		if(o.equals(tblThuocChon)) {
			int r = tblThuocChon.getSelectedRow();
			ArrayList<Thuoc> lstThuoc = new ArrayList<>();
			try {
				lstThuoc = thuocDao.timThuocTheoMa(tblThuocChon.getValueAt(r, 0).toString());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			Thuoc t=lstThuoc.get(0);
			if(t.getHinhMinhHoa()!=null) {
				
				
				for(Thuoc th:lstThuoc) {
					if(th.getHinhMinhHoa() != null) {
						hinhThuocByte = th.getHinhMinhHoa();
						ImageIcon icon = new ImageIcon(hinhThuocByte);
						Image image = icon.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
						lblHinhanhThuoc.setIcon(new ImageIcon(image));
					}
				}
			}
			else {
				lblHinhanhThuoc.setIcon(new ImageIconCustom("images/hinhRong.png", 130, 130));
			}
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
