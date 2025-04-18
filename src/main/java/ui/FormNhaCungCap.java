package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import connect.ConnectDB;
import dao.DonDatThuocDao;
import dao.NhaCungCapDao;
import entity.DonDatThuoc;
import entity.KhachHang;
import entity.NhaCungCap;
import format.PanelBorder;
import format.SearchText;

public class FormNhaCungCap extends PanelBorder implements ActionListener, MouseListener {
	
	private PanelBorder pnlTop;
	private PanelBorder pnlCen;
	private PanelBorder pnlBot;
	
	private JButton btnTimkiem;
	private SearchText txtSearch;
	private JComboBox<String> cboFind;
	private JTextField txtTenNCC;
	private JTextField txtDiachi;
	private JTextField txtSDT;
	private JTable tblNCC;
	private JScrollPane jScrollPane1;
	private DefaultTableModel tblModelNCC;
	private JButton btnCapnhat;
	private JButton btnXoa;
	private JButton btnXoaTrang;
	private JButton btnThem;
	private NhaCungCapDao nccDao;
	private JButton btnLamMoi;
	
	public FormNhaCungCap() throws SQLException {
		try {
			ConnectDB.getInstance().connectDataBase();
			System.out.println("Success To Connect NCC Form!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		nccDao = new NhaCungCapDao();
		
	    // khởi tạo
	    pnlTop = new PanelBorder();
	    pnlCen = new PanelBorder();
	    pnlBot = new PanelBorder();
	    
	    btnTimkiem = new JButton("Tìm kiếm");
	    txtSearch = new SearchText();
	    txtSDT = new JTextField();
	    txtDiachi = new JTextField();
	    txtTenNCC = new JTextField();

	    //pnlTop + pnlCen
	    pnlTop.setBackground(new Color(250, 250, 250));
	    btnTimkiem.setBackground(new Color(110, 191, 55));
	    txtSearch.setPreferredSize(new Dimension(200, 30));

	    pnlTop.add(btnTimkiem);
	    pnlTop.add(txtSearch, BorderLayout.CENTER);
	    pnlTop.add(cboFind = new JComboBox<String>());

	    cboFind.setBackground(new Color(110, 191, 55));
	    cboFind.addItem("Tên");
	    cboFind.addItem("SĐT");

	    pnlCen.setBackground(new Color(250, 250, 250));
	    Box verticalBox = Box.createVerticalBox();
	    Box b2, b3, b4, b5;
	    b2 = Box.createHorizontalBox();
	    b3 = Box.createHorizontalBox();
	    b4 = Box.createHorizontalBox();

	    JLabel lblTenNCC = new JLabel("Tên NCC: ");
	    txtTenNCC = new JTextField(10);
	    b2.add(lblTenNCC);
	    b2.add(txtTenNCC);
	    verticalBox.add(Box.createVerticalStrut(10));
	    verticalBox.add(b2);

	    JLabel lblDiachi = new JLabel("Địa chỉ: ");
	    txtDiachi = new JTextField(10);
	    b3.add(lblDiachi);
	    b3.add(txtDiachi);
	    verticalBox.add(Box.createVerticalStrut(10));
	    verticalBox.add(b3);

	    JLabel lblSDT = new JLabel("Số điện thoại: ");
	    txtSDT = new JTextField(10);
	    b4.add(lblSDT);
	    b4.add(txtSDT);
	    verticalBox.add(Box.createVerticalStrut(10));
	    verticalBox.add(b4);
	    verticalBox.add(Box.createVerticalStrut(10));
	    verticalBox.add(b5 = Box.createHorizontalBox());
		verticalBox.add(Box.createVerticalStrut(15));
		b5.add(btnCapnhat = new JButton("Cập nhật"));
		btnCapnhat.setBackground(new Color(110,191,55));
		b5.add(Box.createHorizontalStrut(15));
		b5.add(btnXoa = new JButton("Xoá"));
		btnXoa.setBackground(new Color(110,191,55));
		b5.add(Box.createHorizontalStrut(15));
		b5.add(btnXoaTrang = new JButton("Xoá trắng"));
		btnXoaTrang.setBackground(new Color(110,191,55));
		b5.add(Box.createHorizontalStrut(15));
		b5.add(btnThem = new JButton("Thêm"));
		btnThem.setBackground(new Color(110,191,55));
		b5.add(Box.createHorizontalStrut(15));
		b5.add(btnLamMoi = new JButton("Làm Mới"));
		btnLamMoi.setBackground(new Color(110,191,55));
		b5.add(Box.createHorizontalStrut(15));
	    
//		lblMaNCC.setPreferredSize(lblSDT.getPreferredSize());
		lblTenNCC.setPreferredSize(lblSDT.getPreferredSize());
		lblDiachi.setPreferredSize(lblSDT.getPreferredSize());

	    pnlCen.setLayout(new GridBagLayout());
	    GridBagConstraints gbcCen = new GridBagConstraints();
	    gbcCen.fill = GridBagConstraints.BOTH;
	    gbcCen.weightx = 1.0;
	    gbcCen.weighty = 1.0;
	    pnlCen.add(verticalBox, gbcCen);

	    PanelBorder combinedPanel = new PanelBorder();
	    GroupLayout combinedLayout = new GroupLayout(combinedPanel);
	    combinedPanel.setLayout(combinedLayout);
	    combinedLayout.setHorizontalGroup(combinedLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addComponent(pnlTop, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	            .addComponent(pnlCen, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
	    combinedLayout.setVerticalGroup(combinedLayout.createSequentialGroup()
	            .addComponent(pnlTop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
	            .addComponent(pnlCen, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
	    //---------------------------------------------------------------------------------------------------
	    // pnlBot
	    pnlBot.setBackground(new Color(250, 250, 250));
        
        GroupLayout roundPanel2Layout = new GroupLayout(this.pnlBot);
        pnlBot.setLayout(roundPanel2Layout);
        
        jScrollPane1 = new JScrollPane();
        tblNCC = new JTable();
        pnlBot.setBackground(new Color(250, 250, 250));
        tblNCC.setModel( tblModelNCC = new DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Mã NCC", "Tên NCC", "Số điện thoại", "Địa chỉ"
                }
        ));
        jScrollPane1.setViewportView(tblNCC);
        
        //chỉnhh tiêu đề bảng: CHỈNH MÀU 
        JTableHeader tableHeader = tblNCC.getTableHeader();
        tableHeader.setDefaultRenderer( new DefaultTableCellRenderer() {
			@Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                component.setBackground(new Color(110,191,55));
                component.setForeground(Color.WHITE);
                return component;
            }
        });
        
        if (tblNCC.getColumnModel().getColumnCount() > 0) {
            tblNCC.getColumnModel().getColumn(0).setMinWidth(50);
            tblNCC.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblNCC.getColumnModel().getColumn(2).setMinWidth(100);
            
        }        
        roundPanel2Layout.setHorizontalGroup(roundPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addGroup(roundPanel2Layout.createSequentialGroup().addContainerGap().addComponent(jScrollPane1)));
	    roundPanel2Layout.setVerticalGroup(roundPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addGroup(roundPanel2Layout.createSequentialGroup().addContainerGap()
	                    .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE).addContainerGap()));
	    //---------------------------------------------------------
	    // Layout chính
	    GroupLayout layout = new GroupLayout(this);
	    this.setLayout(layout);
	    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addComponent(combinedPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	            .addComponent(pnlBot, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
	    layout.setVerticalGroup(layout.createSequentialGroup()
	            .addComponent(combinedPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
	                    GroupLayout.PREFERRED_SIZE)
	            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
	            .addComponent(pnlBot, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
	   
	    btnCapnhat.addActionListener(this);
		btnXoa.addActionListener(this);;
		btnXoaTrang.addActionListener(this);;
		btnThem.addActionListener(this);
		btnTimkiem.addActionListener(this);
		tblNCC.addMouseListener(this);
		btnLamMoi.addActionListener(this);
		
		xoaDataTable();
		nhapDuLieuSQLLenTable();
	}

	private void xoaDataTable() {
		DefaultTableModel dm = (DefaultTableModel) tblNCC.getModel();
		dm.getDataVector().removeAllElements();
	}

	private void nhapDuLieuSQLLenTable() throws SQLException {
	    List<NhaCungCap> lstNCC = nccDao.layTatCaNCC();
	    for(NhaCungCap ncc:lstNCC) {
	        tblModelNCC.addRow(new Object[] {
	                ncc.getMaNCC(), ncc.getTenNCC(),ncc.getSdtNCC(), ncc.getDiaChi()
	        });
	    }
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnThem)) {
			if(txtDiachi.getText().equals("") || txtSDT.getText().equals("")||
					txtTenNCC.getText().equals("")) {
				System.out.println("thieu tt");
				JOptionPane.showMessageDialog(this, "vui long nhap du thong tin");
				return;
			}
			else { 
				
				if(duLieuHopLe()) {
					String tenNCC = txtTenNCC.getText();
					String sdt = txtSDT.getText();
					String diachi = txtDiachi.getText();
					
					//tạo điều kiện k đc trùng tên NCC
					for(int r=0; r < tblNCC.getRowCount(); r++ ) {
						if(tenNCC.equals(tblNCC.getValueAt(r, 0).toString())) {
							JOptionPane.showMessageDialog(this, "Tên NCC đã được nhập, vui lòng nhập ten khác");
							txtTenNCC.selectAll();
							txtTenNCC.requestFocus();
							return;
						}
					}
					NhaCungCap ncc = new NhaCungCap(tenNCC, sdt, diachi);
					try {
						nccDao.taoNCC(ncc);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					xoaDataTable();
					try {
						nhapDuLieuSQLLenTable();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(this, "Them ncc thanh cong");
				
				}
			}
		}
		else if(o.equals(btnCapnhat)) {
			int r = tblNCC.getSelectedRow();
			if(r==-1) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 NCC cần sửa!");
				return;
			}
			String ten = txtTenNCC.getText().trim();
			String diaChi =  txtDiachi.getText().trim();
			String sdt = txtSDT.getText().trim();
			
			NhaCungCap ncc = new NhaCungCap(ten, sdt, diaChi);
			try {
				nccDao.capnhatNCC(ncc, tblNCC.getValueAt(r, 0).toString());
				JOptionPane.showMessageDialog(this, "cập nhật thành công");
				xoaDataTable();
				nhapDuLieuSQLLenTable();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if(o.equals(btnXoa)) {
			int r = tblNCC.getSelectedRow();
			if(r == -1) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 NCC cần xoá!");
				return;
			}
			String idNCC = tblNCC.getValueAt(r, 0).toString();
			NhaCungCap ncc = null;
			DonDatThuocDao donDao=new DonDatThuocDao();
			ArrayList<DonDatThuoc> list=donDao.layDonDatTheoMaNCC(idNCC);
			if(list.size()>0) {
				JOptionPane.showMessageDialog(null,"Không Thể Xoá Nhà Cung Cấp Đã Từng Nhập Hàng! Hãy Liên Hệ Bộ Phận IT");
			}else {
				try {
					ncc = nccDao.layNCCTheoMa(idNCC);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					int i=JOptionPane.showConfirmDialog(null,"Bạn có chắc muốn xoá k");
					if(i==0) {
					nccDao.xoaNCC(ncc);
					tblModelNCC.removeRow(r);
					System.out.println("Removed!");
					JOptionPane.showMessageDialog(null,"Xoá thành công!");
					}
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		else if(o.equals(btnXoaTrang)) {
			txtDiachi.setText("");
			txtSDT.setText("");
			txtTenNCC.setText("");
			txtSearch.setText("");
			
		}
		else if(o.equals(btnTimkiem)) {
			System.out.println("tim");
			String filter = cboFind.getSelectedItem().toString(); System.out.println(filter);
			String tuKhoa = txtSearch.getText().trim();
			if(filter.equalsIgnoreCase("Tên")) {
				try {
					timNCCTheoTen();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if(filter.equalsIgnoreCase("SĐT")) {
				try {
					timNCCTheoSDT();
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
		}else if(o.equals(btnLamMoi)) {
			xoaDataTable();
			try {
				nhapDuLieuSQLLenTable();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

	private void timNCCTheoSDT() throws SQLException {
		String sdt = txtSearch.getText();
		ArrayList<NhaCungCap> lstNCC = nccDao.layNCCTheoSdt(sdt);
		if(! lstNCC.isEmpty()) {
			xoaDataTable();
			JOptionPane.showMessageDialog(this, "Tìm NCC theo SDT thành công!");
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Không tìm thấy");
		}
		for(NhaCungCap ncc : lstNCC) {
			
			tblModelNCC.addRow(new Object[] {
					ncc.getMaNCC(), ncc.getTenNCC(),ncc.getSdtNCC(),
					ncc.getDiaChi()
			});
		}
		
	}

	private void timNCCTheoTen() throws SQLException {
		String ten = txtSearch.getText();
		ArrayList<NhaCungCap> lstNCC = nccDao.layNCCTheoTen(ten);
		if(! lstNCC.isEmpty()) {
			xoaDataTable();
			JOptionPane.showMessageDialog(this, "Tìm NCC theo Ten thành công!");
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Không tìm thấy");
		}
		for(NhaCungCap ncc : lstNCC) {
			
			tblModelNCC.addRow(new Object[] {
					ncc.getMaNCC(), ncc.getTenNCC(),ncc.getSdtNCC(),
					ncc.getDiaChi()
			});
		}
		
	}

	private boolean duLieuHopLe() {
		String sdt = txtSDT.getText();
		if(!sdt.matches("^(09|08|07|03|05|04|02|06|01)\\d{8}$")) {
			JOptionPane.showMessageDialog(null,"Nhập Lại Số Điện Thoại Hợp Lệ!");
			return false;
		}
		return true;
	}

	private boolean kiemTraDuLieuRong() {
		if(txtDiachi.getText()=="" || txtSDT.getText()=="" ||
			 txtTenNCC.getText()=="")
		{
			JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
			return false;
		}
		return true;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int r = tblNCC.getSelectedRow();
		
		txtDiachi.setText(tblNCC.getValueAt(r, 3).toString());
		txtSDT.setText(tblNCC.getValueAt(r, 2).toString());
		txtTenNCC.setText(tblNCC.getValueAt(r, 1).toString());
		
		
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
