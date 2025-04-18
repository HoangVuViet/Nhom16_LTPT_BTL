package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import connect.ConnectDB;
import dao.HoaDonDao;
import dao.KhachHangDao;
import entity.HoaDon;
import entity.KhachHang;
import format.PanelBorder;
import format.SearchText;
import org.jdesktop.swingx.JXDatePicker;

public class FormKhachHang extends PanelBorder implements ActionListener,MouseListener{
	 	private PanelBorder pnlTop, pnlBot;
		private JTextField txtName,txtID,txtPhone,txtLocation;
		private JButton btnThem,btnXoa,btnTimkiem,btnXoaRong,btnCapnhat,btnLamMoi;
		private JCheckBox chkWMen;
		private DefaultTableModel tblModelKH;
		private JTable tblKH;
		private JComboBox<String> cboFind;
		private SearchText txtFind;
		private JXDatePicker dateBirth;
		private JTextField txtEmail;
		private KhachHangDao khDao;
		
	    public FormKhachHang(){
	    	 try {
					ConnectDB.getInstance().connectDataBase();
					System.out.println("Success To Connect Customer Form!");
				} catch (SQLException e) {
					e.printStackTrace();
				}
	    	khDao=new KhachHangDao();
	    	pnlTop = new PanelBorder();
	        
	    	Box box, b1, b2, b3, b4, b5;
	    	box = Box.createVerticalBox();
	    	b1 = Box.createHorizontalBox();
	    	JLabel lblName = new JLabel("Tên khách hàng: ");
	    	txtName = new JTextField(30);
	    	JLabel lblPhone = new JLabel("Số điện thoại: ");
	    	txtPhone = new JTextField(20);
	    	JLabel lblBirth = new JLabel("Ngày sinh: ");
	    	dateBirth = new JXDatePicker();
	    	b1.add(lblName);
	    	b1.add(txtName);
	    	b1.add(lblPhone);
	    	b1.add(txtPhone);
	    	b1.add(lblBirth);
	    	b1.add(dateBirth);

	    	b2 = Box.createHorizontalBox();
	    	
	    	JLabel lblLocation = new JLabel("Địa chỉ: ");
	    	txtLocation = new JTextField();   
	    	JLabel lblEmail = new JLabel("Email: "); 
	    	txtEmail = new JTextField(5);	    
	    	
	    	chkWMen = new JCheckBox("Nữ");
	    	b2.add(lblLocation);
	    	b2.add(txtLocation);
	    	b2.add(lblEmail); 
	    	b2.add(txtEmail);
	    	b2.add(chkWMen);
	    	
	    	b3 = Box.createHorizontalBox();
	    	btnThem = new JButton("Thêm");
	    	btnXoa = new JButton("Xoá");
	    	btnXoaRong = new JButton("Xoá rỗng");
	    	btnCapnhat = new JButton("Cập nhật");
	    	b3.add(btnThem);
	    	b3.add(btnXoa);
	    	b3.add(btnXoaRong);
	    	b3.add(btnCapnhat);

	    	b4 = Box.createHorizontalBox();
	    	btnTimkiem = new JButton("Tìm kiếm");
	    	cboFind = new JComboBox<String>();
	    	txtFind = new SearchText();
	    	cboFind.setBackground(new Color(110, 191, 55));
	    	cboFind.addItem("Tên Khách Hàng");
	    	cboFind.addItem("Mã Khách Hàng");
	    	cboFind.addItem("Số Điện Thoại");
	    	btnThem.setBackground(Color.GREEN);
	    	btnXoa.setBackground(Color.GREEN);
	    	btnXoaRong.setBackground(Color.GREEN);
	    	btnCapnhat.setBackground(Color.green);
	    	btnTimkiem.setBackground(Color.GREEN);
	    	btnLamMoi=new JButton("Làm Mới");
	    	btnLamMoi.setBackground(Color.GREEN);
	    	b4.add(cboFind);
	    	b4.add(txtFind);
	    	b4.add(btnTimkiem);
	    	b4.add(btnLamMoi);

	    	box.add(Box.createVerticalStrut(10));
	    	lblLocation.setPreferredSize(lblName.getPreferredSize());
//	    	txtLocation.setPreferredSize(txtName.getPreferredSize());
	    	lblEmail.setPreferredSize(lblPhone.getPreferredSize());
	    	GridBagConstraints constraints = new GridBagConstraints();
	    	constraints.fill = GridBagConstraints.BOTH;
	    	constraints.weightx = 1.0;
	    	constraints.weighty = 1.0;
	    	pnlTop.setLayout(new GridBagLayout());

	    	box.add(b1);
	    	box.add(Box.createVerticalStrut(10)); 
	    	box.add(b2);
	    	box.add(Box.createVerticalStrut(10)); 
//	    	box.add(b5);
//	    	box.add(Box.createVerticalStrut(10));
	    	box.add(b3);
	    	box.add(Box.createVerticalStrut(10)); 
	    	box.add(b4);

	    	pnlTop.add(box, constraints);

	        pnlBot = new PanelBorder();

	        PanelBorder pnlBott=new PanelBorder();
			String[] header= {"Mã Khách hàng","Họ Tên","Số điện thoại","Giới tính","Email","Ngày sinh","Địa chỉ"};
			tblModelKH = new DefaultTableModel(header,0);
			tblKH=new JTable(tblModelKH);
			JScrollPane scrollPane = new JScrollPane(tblKH);	
			scrollPane.setPreferredSize(new Dimension(1000, 400));
			pnlBott.add(scrollPane,constraints);
			TableColumn columnCode= tblKH.getColumnModel().getColumn(0);
			columnCode.setPreferredWidth(100);
			TableColumn columnTen= tblKH.getColumnModel().getColumn(1);
			columnTen.setPreferredWidth(200);
			TableColumn columnSDT = tblKH.getColumnModel().getColumn(2);
			columnSDT.setPreferredWidth(130);
			TableColumn columnGioi= tblKH.getColumnModel().getColumn(3);
			columnGioi.setPreferredWidth(70);
			TableColumn columnGender = tblKH.getColumnModel().getColumn(4);
			columnGender.setPreferredWidth(180);
			TableColumn columnPhone = tblKH.getColumnModel().getColumn(5);
			columnPhone.setPreferredWidth(120);
			TableColumn columnLocation = tblKH.getColumnModel().getColumn(6);
			columnLocation.setPreferredWidth(200);
			pnlBott.setBorder(new TitledBorder("Danh Sách Khách Hàng"));
			
			pnlBot.add(pnlBott,constraints);

	        GroupLayout layout = new GroupLayout(this);
	        this.setLayout(layout);
	        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
	                addComponent(this.pnlTop, -1, -1, 32767).
	                addComponent(this.pnlBot, -1, -1, 32767));
	        layout.setVerticalGroup(layout.
	                createParallelGroup(GroupLayout.Alignment.LEADING).
	                addGroup(layout.createSequentialGroup().
	                        addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).
	                        addComponent(this.pnlTop, -2, -1, -2).
	                        addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).
	                        addComponent(this.pnlBot, -1, -1, 32767)));
	        btnThem.addActionListener(this);
	        btnXoa.addActionListener(this);
	        btnLamMoi.addActionListener(this);
	        btnTimkiem.addActionListener(this);
	        btnXoaRong.addActionListener(this);
	        btnCapnhat.addActionListener(this);
	        removeAllTableData();
	        importSQLDataToTable();
	        tblKH.addMouseListener(this);
	       
	    
	    }
	    
	    public void importSQLDataToTable() {
			List<KhachHang> list=khDao.layTatCaKhangHang();
			for(KhachHang c:list) {
				SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy",new Locale("vi","VN"));
				Date sinh=java.sql.Date.valueOf(c.getNgaySinh());
				tblModelKH.addRow(new Object[] {c.getMaKH(),c.getTenKH(),c.getSDT(),
						c.isGioiTinh()==false?"Nam":"Nu",c.getEmail(),sdf.format(sinh),c.getDiachi()});
				System.out.println("Imported KhachHang Data!");
			}
		}
	    public void removeAllTableData() {
			DefaultTableModel dm = (DefaultTableModel) tblKH.getModel();
			dm.getDataVector().removeAllElements();
		}
	    
		@Override
		public void actionPerformed(ActionEvent e){
			Object o = e.getSource();
			if(o.equals(btnThem)) {
				
				if(! kiemTraDuLieuRong()) {
					return;
				}else {
					if(duLieuHopLe()) {
						kiemTraDuLieuRong();
						String ten=txtName.getText().trim();
						String sdt=txtPhone.getText().trim();
						String diaChi=txtLocation.getText().trim();
						boolean gioiTinh=chkWMen.isSelected();
						String mail=txtEmail.getText().trim();
						Date nSinh=dateBirth.getDate();
						LocalDate ngaySinh=nSinh.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						KhachHang kh=new KhachHang(ngaySinh, ten, gioiTinh, mail, sdt, diaChi);
						try {
							khDao.taoKhachHang(kh);
							removeAllTableData();
							importSQLDataToTable();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				
				
			}
			else if(o.equals(btnCapnhat)) {
				int r = tblKH.getSelectedRow();
				if(r==-1) {
					JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 KH cần sửa!");
					return;
				}
				
				String ten=txtName.getText().trim();
				String sdt=txtPhone.getText().trim();
				String diaChi=txtLocation.getText().trim();
				boolean gioiTinh=chkWMen.isSelected();
				String mail=txtEmail.getText().trim();
				Date nSinh=dateBirth.getDate();
				LocalDate ngaySinh=nSinh.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				KhachHang kh=new KhachHang(ngaySinh, ten, gioiTinh, mail, sdt, diaChi);
				
				r=tblKH.getSelectedRow();
				String cusCode=tblKH.getValueAt(r, 0).toString();
				try {
					khDao.capnhatKhachHang(kh, cusCode);
					removeAllTableData();
					importSQLDataToTable();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			else if(o.equals(btnTimkiem)) {
				String filter = cboFind.getSelectedItem().toString();
				String find=txtFind.getText().trim();
				ArrayList<KhachHang> d = null;
				if(filter.equalsIgnoreCase("Tên Khách Hàng")) {	
						try {
							d = khDao.layKhachHangTheoTen(find);
							if(!d.isEmpty()) {
								removeAllTableData();
								System.out.println("Founded!");
								for(KhachHang c:d) {
									
									SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy",new Locale("vi","VN"));
									Date sinh=java.sql.Date.valueOf(c.getNgaySinh());
									tblModelKH.addRow(new Object[] {c.getMaKH(),c.getTenKH(),c.getSDT(),
											c.isGioiTinh()==false?"Nam":"Nu",c.getEmail(),sdf.format(sinh),c.getDiachi()});
			
								}
							}else {
								JOptionPane.showMessageDialog(this, "Không Tìm Thấy Khách Hàng!");
							}
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
						if(d==null) {
							JOptionPane.showMessageDialog(this, "Không Tìm Thấy Khách Hàng!");
						}
					}else if(filter.equalsIgnoreCase("Số Điện Thoại")) {
							ArrayList<KhachHang> dth = null;
							try {
								dth = khDao.layKhachHangTheoSdt(find);

								if(!dth.isEmpty()) {
									removeAllTableData();
									for(KhachHang c:dth) {
										
										SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy",new Locale("vi","VN"));
										Date sinh=java.sql.Date.valueOf(c.getNgaySinh());
										tblModelKH.addRow(new Object[] {c.getMaKH(),c.getTenKH(),c.getSDT(),
												c.isGioiTinh()==false?"Nam":"Nu",c.getEmail(),sdf.format(sinh),c.getDiachi()});
										
									}
								}else {
									JOptionPane.showMessageDialog(this, "Không Tìm Thấy Khách Hàng!");
								}
							} catch (SQLException e1) {
								
								e1.printStackTrace();
							}
							
							if(dth==null) {
								JOptionPane.showMessageDialog(this, "Không Tìm Thấy Khách Hàng!");
							}
						
					}if(filter.equalsIgnoreCase("Mã Khách Hàng")) {
						
							ArrayList<KhachHang> d1 = null;
							try {
								d1 = khDao.layKhachHangTheoMa(find);
								if(!d1.isEmpty()) {
									System.out.println("Founded!");
									removeAllTableData();
									for(KhachHang c:d1) {
										
										SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy",new Locale("vi","VN"));
										Date sinh=java.sql.Date.valueOf(c.getNgaySinh());
										tblModelKH.addRow(new Object[] {c.getMaKH(),c.getTenKH(),c.getSDT(),
												c.isGioiTinh()==false?"Nam":"Nu",c.getEmail(),sdf.format(sinh),c.getDiachi()});
										
									}
								}else {
									JOptionPane.showMessageDialog(this, "Không Tìm Thấy Khách Hàng!");
								}
							} catch (SQLException e1) {
								
								e1.printStackTrace();
							}				
							if(d1==null) {
								JOptionPane.showMessageDialog(this, "Không Tìm Thấy Khách Hàng!");
							}
					}
			}
			else if(o.equals(btnXoaRong)) {
				txtName.setText("");
				txtEmail.setText("");
				txtFind.setText("");
				txtLocation.setText("");
				txtPhone.setText("");
				dateBirth.setDate(null);
				chkWMen.setSelected(false);
				cboFind.setSelectedItem(null);
			}else if(o.equals(btnXoa)) {
				int r=tblKH.getSelectedRow();
				if(r == -1) {
					JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 Khách Hàng cần xoá!");
					return;
				}
				String code=tblKH.getValueAt(r, 0).toString();
				KhachHang cus = null;
				HoaDonDao hdDao=new HoaDonDao();
				ArrayList<HoaDon> list=hdDao.timHoaDonBangMaKH(code);
				if(list.size()>0) {
					JOptionPane.showMessageDialog(null,"Không Thể Xoá Khách Hàng Đã Mua Hàng! Hãy Liên Hệ Bộ Phận IT");
				}else {
					try {
						cus = khDao.layKhachHangTheoMaKH(code);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						int i=JOptionPane.showConfirmDialog(null,"Bạn có chắc muốn xoá khách hàng: "+cus.getTenKH()+"- Số điện thoại: "+cus.getSDT()+" ?");
						if(i==0) {
						khDao.xoaKhachHang(cus);
						tblModelKH.removeRow(r);
						System.out.println("Removed!");
						JOptionPane.showMessageDialog(null,"Xoá thành công!");
						}
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}else if(o.equals(btnLamMoi)){
				removeAllTableData();
				importSQLDataToTable();
			}
		}
		
		private boolean duLieuHopLe() {
			String sdt = txtPhone.getText();
			if(!sdt.matches("^(09|08|07|03|05|04|02|06|01)\\d{8}$")) {
				JOptionPane.showMessageDialog(txtPhone,"Nhập Lại Số Điện Thoại Hợp Lệ!");
				return false;
			}
			if(dateBirth.getDate().after(new Date())) {
				JOptionPane.showMessageDialog(dateBirth,"Nhập Lại ngày sinh hợp lệ!");
				return false;
			}
			if(! txtEmail.getText().toString().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
				JOptionPane.showMessageDialog(txtEmail,"Nhập Lại địa chỉ email hợp lệ!");
				return false;
			}	
			
			return true;
		}

		private boolean kiemTraDuLieuRong() {
			if(txtName.getText()=="" || txtPhone.getText()=="" || txtEmail.getText()==""
					|| txtLocation.getText() =="" || dateBirth.getDate()==null	) 
				{
					JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin Khách hàng!");
					return false;
				}
				return true;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			int row=tblKH.getSelectedRow();
//			txtCode.setText(tableModel.getValueAt(row, 0).toString());
			txtName.setText(tblModelKH.getValueAt(row, 1).toString());
			txtPhone.setText(tblModelKH.getValueAt(row, 2).toString());
			chkWMen.setSelected(tblModelKH.getValueAt(row, 3).toString()=="Nu"?true:false);
			
			txtEmail.setText(tblModelKH.getValueAt(row, 4).toString());
			try {
				dateBirth.setDate(sdf.parse(tblModelKH.getValueAt(row, 5).toString()));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			txtLocation.setText(tblModelKH.getValueAt(row, 6).toString());
			
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


	


