package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


import connect.ConnectDB;
import dao.DonViTinhDao;
import dao.LoaiThuocDao;
import dao.NhaCungCapDao;
import dao.ThuocDao;
import entity.DonViTinh;
import entity.LoaiThuoc;
import entity.NhaCungCap;
import entity.NhanVien;
import entity.Thuoc;
import format.ImageIconCustom;
import format.PanelBorder;
import format.SearchText;

public class FormThuoc extends PanelBorder implements ActionListener, MouseListener {
	
private static final long serialVersionUID = 1L;
    
	private PanelBorder pnlTren, pnlDuoi, pnlGiua, pnlHinhAnh;
	
    private SearchText txtTimkiem;
    
    private String fileName = null;
    private byte[] hinhThuocByte = null; 

	private JScrollPane scrTable;
	private JTable tblThuoc;
	
	private JTextField txtMoTaThuoc;
	
	private JComboBox<String> cboFind;
	
	private JButton btnXoa, btnSua, btnxoaTrang, btnTimkiem, btnDoiHinhanh, btnNhapThuoc, btnLamMoi;
	
	private DefaultTableModel tblModelThuoc;
	
	private JLabel lblHinhanhThuoc;

	private ThuocDao thuocDao;
	private NhanVien nv;

	private JButton btnXemDonNhap;

	private DonViTinhDao donviDao;

	private LoaiThuocDao loaithuocDao;

	public FormThuoc(NhanVien nv) throws SQLException {
		this.nv = nv;
		try {
			ConnectDB.getInstance().connectDataBase();
			System.out.println("Kết nối thành công SQL với FormThuoc");
		}catch (SQLException e) {
			e.printStackTrace();
		}
		//khởi tạo 
		thuocDao = new ThuocDao();
		donviDao = new DonViTinhDao();
		loaithuocDao = new LoaiThuocDao();
		
		pnlTren = new PanelBorder();
        pnlGiua = new PanelBorder();
        txtTimkiem = new SearchText();
        pnlDuoi = new PanelBorder();
        //-------------------------
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

        //-----------------------------------------------------------------
        pnlGiua.setBorder(new TitledBorder("Tìm kiếm thuốc"));
        pnlGiua.setBackground(new Color(250, 250, 250));
        
        btnTimkiem = new JButton("Tìm kiếm");
        txtTimkiem.setPreferredSize(new Dimension(200, 30));
      
        pnlGiua.add(btnTimkiem);
        pnlGiua.add(txtTimkiem, BorderLayout.CENTER);
        pnlGiua.add(cboFind = new JComboBox<String>());
        
			//cboFind.setEditable(true);	
		cboFind.addItem("Tên thuốc");
		cboFind.addItem("Mã thuốc");
		cboFind.addItem("Loại thuốc");
		
		pnlTren.setBorder(new TitledBorder("Quản lý thuốc"));
		pnlTren.setBackground(new Color(250, 250, 250));
        Box b = Box.createVerticalBox();
        b.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // Padding
        Box a, b1, b6;;
        a = Box.createHorizontalBox();
        a.add(pnlHinhAnh);
        a.add(b);
        b.add(Box.createVerticalStrut(15));

        b.add(b1 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(10));
          JLabel lblMoTaThuoc = new JLabel("Mô tả: ");
          b1.add(lblMoTaThuoc);
          b.add(Box.createVerticalStrut(15));
          b1.add(txtMoTaThuoc = new JTextField());
       
        b.add(b6 = Box.createHorizontalBox());
        b6.add(Box.createHorizontalStrut(15));
        b6.add(btnXoa = new JButton("Huỷ"));
        b6.add(Box.createHorizontalStrut(15));
        b6.add(btnSua = new JButton("Sửa"));
        b6.add(Box.createHorizontalStrut(15));
        b6.add(btnxoaTrang = new JButton("Làm Mới"));
        b6.add(Box.createHorizontalStrut(15));
        b6.add(btnNhapThuoc = new JButton("Nhập thuốc"));
        b6.add(Box.createHorizontalStrut(15));
        b6.add(btnXemDonNhap = new JButton("Danh sách đơn nhập"));
        
        pnlTren.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH; 
		constraints.weightx = 1.0; 
		constraints.weighty = 1.0; 

		pnlTren.add(a, constraints);
		//---------------------------------------------------------
		pnlDuoi.setBorder(new TitledBorder("Danh sách thuốc"));
        pnlDuoi.setBackground(new Color(250, 250, 250));
        
        GroupLayout layoutDuoi = new GroupLayout(this.pnlDuoi);
        pnlDuoi.setLayout(layoutDuoi);
        
        scrTable = new JScrollPane();
        tblThuoc = new JTable();
        pnlDuoi.setBackground(new Color(250, 250, 250));
        tblThuoc.setModel( tblModelThuoc = new DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Mã", "Tên", "Đơn vị", "Loại thuốc", "Số lượng","Hạn sử dụng","Đơn giá","Mô tả chức năng","Hình ảnh","Ngày sản xuất"
                }
                
        ) {
        	@Override
            public boolean isCellEditable(int row, int column) {
                
                return column == -1;
            }
        }
        );
        scrTable.setViewportView(tblThuoc);
        
        //chỉnhh tiêu đề bảng: CHỈNH MÀU 
        JTableHeader tableHeader = tblThuoc.getTableHeader();
        tableHeader.setDefaultRenderer( new DefaultTableCellRenderer() {
			@Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                component.setBackground(new Color(110,191,55));
                component.setForeground(Color.WHITE);
                return component;
            }
        });
        
        if (tblThuoc.getColumnModel().getColumnCount() > 0) {
            tblThuoc.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblThuoc.getColumnModel().getColumn(1).setPreferredWidth(50);
            tblThuoc.getColumnModel().getColumn(2).setPreferredWidth(50);
            tblThuoc.getColumnModel().getColumn(3).setPreferredWidth(60);
            tblThuoc.getColumnModel().getColumn(4).setPreferredWidth(30);
            tblThuoc.getColumnModel().getColumn(5).setPreferredWidth(50);
            tblThuoc.getColumnModel().getColumn(6).setPreferredWidth(60);
            tblThuoc.getColumnModel().getColumn(7).setPreferredWidth(100);
            tblThuoc.getColumnModel().getColumn(7).setPreferredWidth(20);
            tblThuoc.getColumnModel().getColumn(8).setPreferredWidth(50);
        }        
        layoutDuoi.setHorizontalGroup(layoutDuoi.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addGroup(layoutDuoi.createSequentialGroup().addContainerGap().addComponent(scrTable)));
	    layoutDuoi.setVerticalGroup(layoutDuoi.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addGroup(layoutDuoi.createSequentialGroup().addContainerGap()
	                    .addComponent(scrTable, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE).addContainerGap()));

        GroupLayout layoutForm = new GroupLayout(this);
        this.setLayout(layoutForm);
        layoutForm.setHorizontalGroup(layoutForm.createParallelGroup(GroupLayout.Alignment.LEADING).
                addComponent(pnlTren,-1,-1,32767).
                addComponent(pnlGiua, -1, -1, 32767).
                addComponent(pnlDuoi, -1, -1, 32767));
        layoutForm.setVerticalGroup(layoutForm.
                createParallelGroup(GroupLayout.Alignment.LEADING).
                addGroup(layoutForm.createSequentialGroup().
                		addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).
                        addComponent(pnlTren,-1,-1,-2).
                        addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).
                        addComponent(pnlGiua, -2, -1, -2).
                        addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).
                        addComponent(this.pnlDuoi, -1, -1, 32767)));
	//event
          btnxoaTrang.addActionListener(this);
          btnXoa.addActionListener(this);
          btnSua.addActionListener(this);
          btnTimkiem.addActionListener(this);
          btnDoiHinhanh.addActionListener(this);
          btnNhapThuoc.addActionListener(this);
          tblThuoc.addMouseListener(this);
          btnXemDonNhap.addActionListener(this);
          
        xoaDataTable();
        nhapDuLieuSQLLenTable();
	}
	public void nhapDuLieuSQLLenTable() throws SQLException {
		List<Thuoc> lstThuoc = thuocDao.timTatCaThuoc();
		for(Thuoc thuoc:lstThuoc) {
			
			DonViTinh d = donviDao.timDonViTheoMa(thuoc.getDonViTinh().getMaDonVi()); 
			LoaiThuoc l = loaithuocDao.timLoaiThuocTheoMa(thuoc.getLoaiThuoc().getId());
			
			if(thuoc.getHinhMinhHoa() != null && thuoc.getSoLuong() != 0) {
				tblModelThuoc.addRow(new Object[] {
						thuoc.getMaThuoc(), thuoc.getTenThuoc(),d.getTenDonVi() ,l.getLoaiThuoc(),
						thuoc.getSoLuong(), thuoc.getHanSD(),thuoc.getGia(),thuoc.getMoTa(),"Đã có hình!",thuoc.getNgaySanXuat()
				});
//				System.out.println("import data: " + thuoc.getHinhMinhHoa());
			}
			if(thuoc.getHinhMinhHoa() == null && thuoc.getSoLuong() != 0) {
				tblModelThuoc.addRow(new Object[] {
						thuoc.getMaThuoc(), thuoc.getTenThuoc(),d.getTenDonVi() ,l.getLoaiThuoc(),
						thuoc.getSoLuong(), thuoc.getHanSD(),thuoc.getGia(),thuoc.getMoTa(), "Chưa có hình!",thuoc.getNgaySanXuat()
				});
			}
		}
	}
	public void xoaDataTable() {
		DefaultTableModel modelTbl = (DefaultTableModel) tblThuoc.getModel();
		modelTbl.getDataVector().removeAllElements();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnTimkiem)) {
			String tuKhoa = cboFind.getSelectedItem().toString();
			if(tuKhoa.equalsIgnoreCase("Tên thuốc")){
				try {
					timThuocTheoTen();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if(tuKhoa.equals("Mã thuốc")) {
				try {
					timThuocTheoMa();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			else if(tuKhoa.equals("Loại thuốc")) {
				try {
					timThuocTheoLoai();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		else if(o.equals(btnxoaTrang)) {
			txtMoTaThuoc.setText("");
			lblHinhanhThuoc.setIcon(new ImageIconCustom("/images/hinhRong.png", 130, 130));
			xoaDataTable();
			try {
				nhapDuLieuSQLLenTable();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(o.equals(btnXoa)) {
			int r = tblThuoc.getSelectedRow();
			String ma = (String) tblThuoc.getValueAt(r, 0);
			int xacNhan = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn huỷ Thuốc này chứ ?", "Huỷ bỏ", JOptionPane.YES_NO_OPTION);
			if(xacNhan == JOptionPane.NO_OPTION) {
				return;
			}else {
//				tblModelThuoc.removeRow(r);
				
				
				try {
					thuocDao.xoaThuoc(ma);
					JOptionPane.showMessageDialog(this, "Xoá thuốc thành công!");
					xoaDataTable();
			        nhapDuLieuSQLLenTable();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		}
		else if(o.equals(btnSua)) {
			String moTa = txtMoTaThuoc.getText();
			byte[] hinhanh = hinhThuocByte; 
			Thuoc thuoc = new Thuoc();
			thuoc.setMoTa(moTa);
			thuoc.setHinhMinhHoa(hinhanh);
			
			int row = tblThuoc.getSelectedRow();
			if(row == -1) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn thuốc cần sửa!");
				return;
			}
			String maThuoc = (String)tblThuoc.getValueAt(row, 0);
			try {
				thuocDao.capnhatThuoc(thuoc, maThuoc);
				if(thuocDao.capnhatThuoc(thuoc, maThuoc)) {
					JOptionPane.showMessageDialog(this, "Cập nhật thuốc thành công!");	
					xoaDataTable();
					nhapDuLieuSQLLenTable();
				}else {
					JOptionPane.showMessageDialog(this, "Không tìm thấy thuốc trong hệ thống!");
		
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if(o.equals(btnNhapThuoc)) {
			FormNhapThuoc frmNhapThuoc = null;
			try {
				frmNhapThuoc = new FormNhapThuoc(nv,this);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			frmNhapThuoc.setVisible(true);
		}
		else if(o.equals(btnDoiHinhanh)) {
			JFileChooser chooser = new JFileChooser("C:\\");
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
		}else if(o.equals(btnXemDonNhap)) {
			FormDanhSachDonNhap frmDSDonNhap = null;
			try {
				frmDSDonNhap = new FormDanhSachDonNhap();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			frmDSDonNhap.setLocationRelativeTo(null);
			frmDSDonNhap.setVisible(true);
		}
	}
	private void timThuocTheoLoai() throws SQLException {
		String loai = txtTimkiem.getText();
		ArrayList<Thuoc> lstThuoc = thuocDao.timThuocTheoLoai(loai);
		if(!lstThuoc.isEmpty() ) {
			xoaDataTable();
			JOptionPane.showMessageDialog(this, "Tìm thuốc theo Loại thuốc thành công!");
		}else {
			JOptionPane.showMessageDialog(this, "Không tìm thấy");
		}
		for(Thuoc thuoc:lstThuoc) {
			
			DonViTinh d = donviDao.timDonViTheoMa(thuoc.getDonViTinh().getMaDonVi()); 
			LoaiThuoc l = loaithuocDao.timLoaiThuocTheoMa(thuoc.getLoaiThuoc().getId());
			
			if(thuoc.getHinhMinhHoa() != null) {
				tblModelThuoc.addRow(new Object[] {
						thuoc.getMaThuoc(), thuoc.getTenThuoc(),d.getTenDonVi() ,l.getLoaiThuoc(),
						thuoc.getSoLuong(), thuoc.getHanSD(),thuoc.getGia(),thuoc.getMoTa(),thuoc.getHinhMinhHoa(),thuoc.getNgaySanXuat()
				});
				System.out.println("import data: " + thuoc.getHinhMinhHoa());
			}
			else {
				tblModelThuoc.addRow(new Object[] {
						thuoc.getMaThuoc(), thuoc.getTenThuoc(),d.getTenDonVi() ,l.getLoaiThuoc(),
						thuoc.getSoLuong(), thuoc.getHanSD(),thuoc.getGia(),thuoc.getMoTa(), "Chưa có hình",thuoc.getNgaySanXuat()
				});
			}
			hinhThuocByte = thuoc.getHinhMinhHoa();
		}
	}
	private void timThuocTheoMa() throws SQLException {
		String ma = txtTimkiem.getText();
		ArrayList<Thuoc> lstThuoc = thuocDao.timThuocTheoMa(ma);
		if(!lstThuoc.isEmpty() ) {
			xoaDataTable();
			JOptionPane.showMessageDialog(this, "Tìm thuốc theo Loại thuốc thành công!");
		}else {
			JOptionPane.showMessageDialog(this, "Không tìm thấy");
		}
		for(Thuoc thuoc:lstThuoc) {
			DonViTinh d = donviDao.timDonViTheoMa(thuoc.getDonViTinh().getMaDonVi()); 
			LoaiThuoc l = loaithuocDao.timLoaiThuocTheoMa(thuoc.getLoaiThuoc().getId());
			if(thuoc.getHinhMinhHoa() != null) {
				tblModelThuoc.addRow(new Object[] {
						thuoc.getMaThuoc(), thuoc.getTenThuoc(),d.getTenDonVi(),l.getLoaiThuoc(),
						thuoc.getSoLuong(), thuoc.getHanSD(),thuoc.getGia(),thuoc.getMoTa(),thuoc.getHinhMinhHoa(),thuoc.getNgaySanXuat()
				});
				System.out.println("import data: " + thuoc.getHinhMinhHoa());
			}
			else {
				tblModelThuoc.addRow(new Object[] {
						thuoc.getMaThuoc(), thuoc.getTenThuoc(), d.getTenDonVi(), l.getLoaiThuoc(),
						thuoc.getSoLuong(), thuoc.getHanSD(),thuoc.getGia(),thuoc.getMoTa(), "Chưa có hình",thuoc.getNgaySanXuat()
				});
			}
			hinhThuocByte = thuoc.getHinhMinhHoa();
		}
	}
	private void timThuocTheoTen() throws SQLException {
		String ten = txtTimkiem.getText();
		ArrayList<Thuoc> lstThuoc = thuocDao.layThuocTheoTen(ten);
		if(!lstThuoc.isEmpty() ) {
			xoaDataTable();
			JOptionPane.showMessageDialog(this, "Tìm thuốc theo tên thuốc thành công!");
		}else {
			JOptionPane.showMessageDialog(this, "Không tìm thấy");
		}
		for(Thuoc thuoc:lstThuoc) {
			DonViTinh d = donviDao.timDonViTheoMa(thuoc.getDonViTinh().getMaDonVi()); 
			LoaiThuoc l = loaithuocDao.timLoaiThuocTheoMa(thuoc.getLoaiThuoc().getId());
			
			if(thuoc.getHinhMinhHoa() != null) {
				tblModelThuoc.addRow(new Object[] {
						thuoc.getMaThuoc(), thuoc.getTenThuoc(),d.getTenDonVi() ,l.getLoaiThuoc(),
						thuoc.getSoLuong(), thuoc.getHanSD(),thuoc.getGia(),thuoc.getMoTa(),thuoc.getHinhMinhHoa(),thuoc.getNgaySanXuat()
				});
				System.out.println("import data: " + thuoc.getHinhMinhHoa());
			}
			else {
				tblModelThuoc.addRow(new Object[] {
						thuoc.getMaThuoc(), thuoc.getTenThuoc(),d.getTenDonVi() ,l.getLoaiThuoc(),
						thuoc.getSoLuong(), thuoc.getHanSD(),thuoc.getGia(),thuoc.getMoTa(), "Chưa có hình",thuoc.getNgaySanXuat()
				});
			}
			hinhThuocByte = thuoc.getHinhMinhHoa();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if(o.equals(tblThuoc)) {
			int r = tblThuoc.getSelectedRow();
			txtMoTaThuoc.setText(tblThuoc.getValueAt(r, 7).toString());
			if(!tblThuoc.getValueAt(r, 8).toString().equals("Chưa có hình!")) {
				ArrayList<Thuoc> lstThuoc = new ArrayList<>();
				try {
					lstThuoc = thuocDao.timThuocTheoMa(tblThuoc.getValueAt(r, 0).toString());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
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
		
	}
	
}
