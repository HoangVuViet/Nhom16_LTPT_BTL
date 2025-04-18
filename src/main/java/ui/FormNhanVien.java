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
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
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
import dao.NhanVienDao;
import dao.TaiKhoanDao;
import entity.KhachHang;
import entity.NhanVien;
import entity.TaiKhoan;
import entity.Thuoc;
import format.ImageIconCustom;
import format.PanelBorder;
import format.SearchText;
import org.jdesktop.swingx.JXDatePicker;

public class FormNhanVien extends PanelBorder implements ActionListener,MouseListener{
	
private static final long serialVersionUID = 1L;
    
	private PanelBorder pnlCen;
    private PanelBorder pnlBot;
	private PanelBorder pnlTop2;
	private PanelBorder pnlTop;
    private SearchText searchText;
    
    private String fileName = null;
    private byte[] person_image = null; 

	private JScrollPane jScrollPane1;
	private JTable tblEmployee;
	
	private JTextField txtEmployeeId;
	private JTextField txtCitizenNumber;
	private JTextField txtEmployeeName;
	private JTextField txtEmployeeLocation;
	private JTextField txtSalary;
	private JTextField txtPhone;

	private JCheckBox chkSex;
	
	private JComboBox<String> cboTinhTrang;
	private JComboBox<String> cboFind;	
	
	private JButton btnXoa;
	private JButton btnXoaTrang;
	private JButton btnThem;
	private JButton btnCapnhat;
	private JButton btnTimKiem;
	private JButton btnImg;

	private DefaultTableModel tableEmployeeModel;
	private JXDatePicker birthdayy ;
	private JXDatePicker dateStart;
	private JLabel imageAvatars;
	private NhanVienDao nvDao;

	private JButton btnLamMoi;

	private NumberFormat cf;

//	private byte[] hinhNVByte=null;

	public FormNhanVien() throws SQLException {
		nvDao=new NhanVienDao();
		 try {
				ConnectDB.getInstance().connectDataBase();
				System.out.println("Success To Connect NhanVien Form!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		cf = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
		pnlCen = new PanelBorder();
        
        pnlTop2 = new PanelBorder();
        
        searchText = new SearchText();
      
        pnlBot = new PanelBorder();
        
        imageAvatars = new JLabel();
        imageAvatars.setIcon(new ImageIconCustom("/images/profile-user.png", 130, 130));
        btnImg = new JButton();
        btnImg.setText("Đổi Ảnh");
        btnImg.setBackground(new Color(110,191,55));
        
        pnlTop = new PanelBorder();
        pnlTop.setBackground(new Color(250, 250, 250));
        pnlTop.add(imageAvatars);
        pnlTop.add(btnImg);
        
        GroupLayout layout1 = new GroupLayout(pnlTop);
//        pnlTop.setLayout(layout1);
//        layout1.setHorizontalGroup(layout1.createParallelGroup(GroupLayout.Alignment.CENTER).
//        		addGroup(layout1.createSequentialGroup().	
//                addComponent(imageAvatars, 20, 50, 100).
//                addGap(25).
//                addComponent(btnImg, -2, 120, 150)));
//        layout1.setVerticalGroup(layout1.
//                createParallelGroup(GroupLayout.Alignment.LEADING).
//                addGroup(layout1.createParallelGroup().
//                        addComponent(imageAvatars, 20, 50, 100).
//                        addGroup(layout1.createSequentialGroup().
//                        		addGap(25).
//                        		addComponent(btnImg, -2 ,20 , 30))
//                        ));
//        
        
        pnlTop2.setBackground(new Color(250, 250, 250));
        
        btnTimKiem = new JButton("Tìm kiếm");
//        btnEdit.setIcon(new ImageIconCustom("woman.png", 100, 100));
        btnTimKiem.setBackground(new Color(110,191,55));
        searchText.setPreferredSize(new Dimension(200, 30));
        btnLamMoi=new JButton("Làm Mới");
        btnLamMoi.setBackground(new Color(110,191,55));
      
        pnlTop2.add(cboFind = new JComboBox<String>());
        pnlTop2.add(searchText, BorderLayout.CENTER);
        
        pnlTop2.add(btnTimKiem);
        pnlTop2.add(btnLamMoi);
        
		//cboFind.setEditable(true);	
		cboFind.setBackground(new Color(110,191,55));
		cboFind.addItem("Tên");
		cboFind.addItem("Mã");
		cboFind.addItem("SĐT");

        pnlCen.setBackground(new Color(250, 250, 250));

        Box b = Box.createVerticalBox();

		Box b5, b1, b2, b3, b4, b6;
		b.add(Box.createVerticalStrut(10));
		b.add(b1 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(10));
		JLabel lblCitizenNumber;
		b1.add(lblCitizenNumber = new JLabel("CCCD: "));
		b1.add(txtCitizenNumber = new JTextField());
		
		b.add(b2 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(10));
		JLabel lblEmployeeName;
		b2.add(lblEmployeeName = new JLabel("Tên NV: "));
		b2.add(txtEmployeeName = new JTextField());
		birthdayy  = new JXDatePicker();
		JLabel lblBirthDay;
		b2.add(lblBirthDay = new JLabel("Ngày sinh: "));
		b2.add(birthdayy );

		b.add(b3 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(10));
		JLabel lblEmployeeLocation;
		b3.add(lblEmployeeLocation = new JLabel("Địa chỉ: "));
		b3.add(txtEmployeeLocation = new JTextField());
		JLabel lblSex;
		b3.add(lblSex = new JLabel("Giới Tính: "));
		b3.add(chkSex = new JCheckBox("Nữ"));

		b.add(b4 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(10));
		JLabel lblPhone;
		b4.add(lblPhone = new JLabel("SĐT: "));
		b4.add(txtPhone = new JTextField());
		JLabel lblTinhTrang;
		b4.add(lblTinhTrang = new JLabel("Tình trạng: "));
		
		//Tạo và đổ dữ liệu vào comboBox
		b4.add(cboTinhTrang = new JComboBox<String>());
		//cboPosition.setEditable(true);	
		cboTinhTrang.setBackground(new Color(110,191,55));
		cboTinhTrang.addItem("Đang Làm");
		cboTinhTrang.addItem("Đã Nghỉ");
		cboTinhTrang.addItem("Thử việc");

		
		b.add(b6 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(10));
		JLabel lblSalaray;
		b6.add(lblSalaray = new JLabel("Lương: "));
		b6.add(txtSalary = new JTextField());
		JLabel lblDateStart;
		b6.add(lblDateStart = new JLabel("Ngày Vào Làm: "));
		dateStart = new JXDatePicker();
		b6.add(dateStart);
		
		b.add(b5 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(15));
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
		
		lblCitizenNumber.setPreferredSize(lblEmployeeName.getPreferredSize());
		lblEmployeeLocation.setPreferredSize(lblEmployeeName.getPreferredSize());
		lblEmployeeLocation.setPreferredSize(lblEmployeeName.getPreferredSize());
		lblPhone.setPreferredSize(lblEmployeeName.getPreferredSize());
		lblSalaray.setPreferredSize(lblEmployeeName.getPreferredSize());
        
		pnlCen.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH; 
		constraints.weightx = 1.0; 
		constraints.weighty = 1.0; 

		pnlCen.add(b, constraints);
        pnlBot.setBackground(new Color(250, 250, 250));
        
        GroupLayout roundPanel2Layout = new GroupLayout(this.pnlBot);
        pnlBot.setLayout(roundPanel2Layout);
        
        jScrollPane1 = new JScrollPane();
        tblEmployee = new JTable();
        pnlBot.setBackground(new Color(250, 250, 250));
        tblEmployee.setModel( tableEmployeeModel = new DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Mã NV", "Họ Tên", "CCCD", "Ngày Sinh", "Giới Tính", "Địa Chỉ",
                        "SĐT", "Lương", "Ngày Vào","Tình Trạng", "Chức Vụ"
                }
                
        ){
        	
			private static final long serialVersionUID = 1L;
			@Override
            public boolean isCellEditable(int row, int column) {
               
                return column ==-1;
            }
        });
        jScrollPane1.setViewportView(tblEmployee);
        
        //chỉnhh tiêu đề bảng: CHỈNH MÀU 
        JTableHeader tableHeader = tblEmployee.getTableHeader();
        tableHeader.setDefaultRenderer( new DefaultTableCellRenderer() {
			@Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                component.setBackground(new Color(110,191,55));
                component.setForeground(Color.WHITE);
                return component;
            }
        });
        
        if (tblEmployee.getColumnModel().getColumnCount() > 0) {
            tblEmployee.getColumnModel().getColumn(0).setMinWidth(50);
            tblEmployee.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblEmployee.getColumnModel().getColumn(2).setMinWidth(100);
            tblEmployee.getColumnModel().getColumn(3).setPreferredWidth(70);
            tblEmployee.getColumnModel().getColumn(4).setPreferredWidth(40);
            tblEmployee.getColumnModel().getColumn(5).setMinWidth(90);
            tblEmployee.getColumnModel().getColumn(5).setPreferredWidth(75);
            tblEmployee.getColumnModel().getColumn(5).setMaxWidth(95);
            tblEmployee.getColumnModel().getColumn(6).setMinWidth(70);
            tblEmployee.getColumnModel().getColumn(7).setMinWidth(100);
            tblEmployee.getColumnModel().getColumn(8).setMinWidth(50);

        }        
        roundPanel2Layout.setHorizontalGroup(roundPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addGroup(roundPanel2Layout.createSequentialGroup().addContainerGap().addComponent(jScrollPane1)));
	    roundPanel2Layout.setVerticalGroup(roundPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addGroup(roundPanel2Layout.createSequentialGroup().addContainerGap()
	                    .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE).addContainerGap()));

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
        		addComponent(pnlTop,-1,-1,32767).
                addComponent(pnlTop2,-1,-1,32767).
                addComponent(pnlCen, -1, -1, 32767).
                addComponent(pnlBot, -1, -1, 32767));
        layout.setVerticalGroup(layout.
                createParallelGroup(GroupLayout.Alignment.LEADING).
                addGroup(layout.createSequentialGroup().
                		addComponent(pnlTop,-1,-2,32767).
                		addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).
                        addComponent(pnlTop2,-1,-1,-2).
                        addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).
                        addComponent(this.pnlCen, -2, -1, -2).
                        addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).
                        addComponent(this.pnlBot, -1, -1, 32767)));
        removeAllTableData();
        importSQLDataToTable();
       //event
    	btnXoa.addActionListener(this);
    	btnXoaTrang.addActionListener(this);
    	btnThem.addActionListener(this);
    	btnCapnhat.addActionListener(this);
    	btnTimKiem.addActionListener(this);
    	btnImg.addActionListener(this);
    	tblEmployee.addMouseListener(this);
    	btnLamMoi.addActionListener(this);
	}
	
	public boolean validData() {
		String ten=txtEmployeeName.getText().trim();
		String cccd=txtCitizenNumber.getText().trim();
		String dc=txtEmployeeLocation.getText().trim();
		String sdt=txtPhone.getText().trim();
		String luong=txtSalary.getText().trim();
		Date nSinh=birthdayy.getDate();
		String tinhTrang=cboTinhTrang.getSelectedItem().toString();
		boolean gioi=chkSex.isSelected();
		if(!ten.matches("^[A-ZÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ]"
						   		+ "[a-zàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđ]*"
						   		+ "(?:[ ][A-ZÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ]"
						   		+ "[a-zàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđ]*)*$")) {
			JOptionPane.showMessageDialog(null,"Nhập Lại Tên Hợp Lệ!");
			return false;
		}
		if(!cccd.matches("\\d{9,12}")) {
			JOptionPane.showMessageDialog(null,"Nhập Lại Số Căn Cước Hợp Lệ!");
			return false;
		}
		if(!sdt.matches("^(09|08|07|03|05|04|02|06|01)\\d{8}$")) {
			JOptionPane.showMessageDialog(null,"Nhập Lại Số Điện Thoại Hợp Lệ!");
			return false;
		}
		if(!luong.matches("^\\d+$")) {
			JOptionPane.showMessageDialog(null,"Nhập Lại Lương Hợp Lệ!");
			return false;
		}if(cboTinhTrang.getSelectedItem()==null) {
			JOptionPane.showMessageDialog(null,"Chọn Tình Trạng Hợp Lệ!");
			return false;
		}
		if(birthdayy.getDate()==null) {
			JOptionPane.showMessageDialog(null,"Chọn Ngày Sinh Hợp Lệ!");
			return false;
		}
		if(dateStart.getDate()==null) {
			JOptionPane.showMessageDialog(null,"Chọn Ngày Vào Làm Hợp Lệ!");
			return false;
		}
		return true;
	}
	public void importSQLDataToTable() throws SQLException {
			List<NhanVien> list=nvDao.layTatCaNhanVien();
			for(NhanVien c:list) {
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date sinh=java.sql.Date.valueOf(c.getEmployeeBirthday());
				tableEmployeeModel.addRow(new Object[] {c.getEmployeeCode(),c.getEmployeeName(),c.getCitizenIdNumber(),
						sdf.format(java.sql.Date.valueOf(c.getEmployeeBirthday())),c.isEmployeeSex()==true?"Nữ":"Nam",c.getEmployeeLocation(),
								c.getEmployeePhoneNumber(),cf.format(c.getSalary()),sdf.format(java.sql.Date.valueOf(c.getDateStart())),c.getTinhTrang(),
								c.isQuanLy()==true?"Quản Lý":"Nhân Viên"});
				System.out.println("Imported NhanVien Data!");
			}
		}
	
	public void importListDataToTable(ArrayList<NhanVien> list) throws SQLException {
		
		for(NhanVien c:list) {
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date sinh=java.sql.Date.valueOf(c.getEmployeeBirthday());
			tableEmployeeModel.addRow(new Object[] {c.getEmployeeCode(),c.getEmployeeName(),c.getCitizenIdNumber(),
					sdf.format(java.sql.Date.valueOf(c.getEmployeeBirthday())),c.isEmployeeSex()==true?"Nữ":"Nam",c.getEmployeeLocation(),
							c.getEmployeePhoneNumber(),cf.format(c.getSalary()),sdf.format(java.sql.Date.valueOf(c.getDateStart())),c.getTinhTrang(),
							c.isQuanLy()==true?"Quản Lý":"Nhân Viên"});
			System.out.println("Imported NhanVien Data!");
		}
	}
	
	public void removeAllTableData() {
			DefaultTableModel dm = (DefaultTableModel) tblEmployee.getModel();
			dm.getDataVector().removeAllElements();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnXoa)) {
			int r=tblEmployee.getSelectedRow();
			String code=tblEmployee.getValueAt(r, 0).toString();
			NhanVien cus = null;
				
			try {
				if(tableEmployeeModel.getValueAt(r, 10).toString().equalsIgnoreCase("Quản Lý")) {
					JOptionPane.showMessageDialog(null,"Không Thể Xoá Quản Lý!");
				}else {
					cus = nvDao.layNhanVienTheoMa(code);
					TaiKhoanDao tkDao=new TaiKhoanDao();
					ArrayList<TaiKhoan> list=tkDao.layTkBangMaNV(code);
					if(list.size()>0) {
						JOptionPane.showMessageDialog(null,"Không Thể Xoá Nhân Viên Đã Đăng Ký Tài Khoản. Hãy Liên Hệ Bộ Phận IT!");
					}else {
						int i=JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xoá nhân viên: "+cus.getEmployeeName()+"- Mã nhân viên: "+cus.getEmployeeCode()+" ?","Xác Nhận Xoá", 0);
						if(i==0) {
						nvDao.xoaNhanVien(cus);
						tableEmployeeModel.removeRow(r);
						System.out.println("Removed!");
						JOptionPane.showMessageDialog(null,"Xoá thành công!");
						}
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}else if(o.equals(btnLamMoi)){
			removeAllTableData();
			try {
				importSQLDataToTable();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		else if(o.equals(btnXoaTrang)) {
			txtCitizenNumber.setText("");
//			txtEmployeeId.setText("");
			txtEmployeeLocation.setText("");
			txtEmployeeName.setText("");
			txtPhone.setText("");
			txtSalary.setText("");
			birthdayy.setDate(null);
			cboTinhTrang.setSelectedItem(null);
			chkSex.setSelected(false);
			dateStart.setDate(null);
		}
		else if(o.equals(btnThem)) {
			if(validData()) {
				String ten=txtEmployeeName.getText().trim();
				String cccd=txtCitizenNumber.getText().trim();
				String dc=txtEmployeeLocation.getText().trim();
				String sdt=txtPhone.getText().trim();
				String luong=txtSalary.getText().trim();
				Date nSinh=birthdayy.getDate();
				LocalDate nsinh=nSinh.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				Date nVao=dateStart.getDate();
				LocalDate nvao=nVao.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				String tinhTrang=cboTinhTrang.getSelectedItem().toString();
				boolean gioi=chkSex.isSelected();
				NhanVien nv=new NhanVien(ten, cccd, nsinh, gioi, dc,
						sdt, Double.parseDouble(luong), tinhTrang, nvao, person_image,false);
				try {
					nvDao.taoNhanVien(nv);
					removeAllTableData();
					importSQLDataToTable();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("Thêm Nhân Viên Mới Thành Công!");
			}
		}
		else if(o.equals(btnCapnhat)) {
			int r=tblEmployee.getSelectedRow();
			if(r>=0) {
				String ten=txtEmployeeName.getText().trim();
				String cccd=txtCitizenNumber.getText().trim();
				String dc=txtEmployeeLocation.getText().trim();
				String sdt=txtPhone.getText().trim();
				String luong=txtSalary.getText().trim();
				Date nSinh=birthdayy.getDate();
				LocalDate nsinh=nSinh.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				Date nVao=dateStart.getDate();
				LocalDate nvao=nVao.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				String tinhTrang=cboTinhTrang.getSelectedItem().toString();
				boolean gioi=chkSex.isSelected();
				byte[] img=person_image;
				NhanVien nv=new NhanVien(ten, cccd, nsinh, gioi, dc,
						sdt, Double.parseDouble(luong), tinhTrang, nvao, img,false);
				try {
					nvDao.capnhatNhanVien(nv,tableEmployeeModel.getValueAt(r, 0).toString());
					removeAllTableData();
					importSQLDataToTable();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else {
				JOptionPane.showMessageDialog(null,"Chọn Nhân Viên Trước!");
			}
		}
		else if(o.equals(btnTimKiem)) {
//			System.out.println("Tìm Kiếm!");
			ArrayList<NhanVien> lNV=new ArrayList<NhanVien>();
			int tim=cboFind.getSelectedIndex();
			String txtTim=searchText.getText().trim();
			if(searchText.getText().trim()==null || searchText.getText().trim()=="") {
				
				JOptionPane.showMessageDialog(null,"Mời Nhập Lại "+tim+" Cần Tìm!");
			}else {
				System.out.println("Đang Tìm Kiếm!");
				if(tim==0) {
					try {
						lNV=nvDao.timNhanVienTheoTen(txtTim);
						removeAllTableData();
						importListDataToTable(lNV);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else if(tim==1) {
					try {
						lNV=nvDao.timNhanVienTheoMa(txtTim);
						removeAllTableData();
						importListDataToTable(lNV);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else if(tim==2) {
					try {
						lNV=nvDao.timNhanVienTheoSDT(txtTim);
						removeAllTableData();
						importListDataToTable(lNV);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			
			
			
		}
		else if(o.equals(btnImg)) {
			JFileChooser chooser = new JFileChooser();
	    	chooser.showOpenDialog(null);
	    	File f = chooser.getSelectedFile();
	    	fileName = f.getAbsolutePath();
	    	ImageIcon imgIcon = new ImageIcon(new ImageIcon(fileName).getImage().getScaledInstance(imageAvatars.getWidth(), imageAvatars.getHeight(), Image.SCALE_SMOOTH));
	    	imageAvatars.setIcon(imgIcon);
	    	try {
	    		File img = new File(fileName);
	    		FileInputStream fis = new FileInputStream(img);
	    		ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    		byte[] buf = new byte[1024];
	    		for(int readNum; (readNum = fis.read(buf))!= -1; ) {
	    			bos.write(buf,0,readNum);
	    		}
	    		person_image = bos.toByteArray();
	    		
	    	}catch (Exception e1) {
				JOptionPane.showMessageDialog(null, e1);
	    	}
		}else if(o.equals(btnLamMoi)) {
			removeAllTableData();
			try {
				importSQLDataToTable();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		int row=tblEmployee.getSelectedRow();
//		txtCode.setText(tableModel.getValueAt(row, 0).toString());
		txtEmployeeName.setText(tableEmployeeModel.getValueAt(row,1).toString());
		txtPhone.setText(tableEmployeeModel.getValueAt(row, 6).toString());
		chkSex.setSelected(tableEmployeeModel.getValueAt(row, 4).toString()=="Nữ"?true:false);
		int i = 0;
		if(tableEmployeeModel.getValueAt(row,9 ).toString()
				.equalsIgnoreCase("Đang Làm Việc")) {
			i=0;
		}else if(tableEmployeeModel.getValueAt(row,9 ).toString()
				.equalsIgnoreCase("Đã Nghỉ Việc")) {
			i=1;
		}else {
			i=2;
		}
		cboTinhTrang.setSelectedIndex(i);
		txtCitizenNumber.setText(tableEmployeeModel.getValueAt(row, 2).toString());
		try {
			dateStart.setDate(sdf.parse(tableEmployeeModel.getValueAt(row, 8).toString()));
			birthdayy.setDate(sdf.parse(tableEmployeeModel.getValueAt(row, 3).toString()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			txtSalary.setText(cf.parse(tableEmployeeModel.getValueAt(row, 7).toString()).toString());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		txtEmployeeLocation.setText(tableEmployeeModel.getValueAt(row, 5).toString());
		
		try {
			NhanVien nv=nvDao.layNhanVienTheoMa(tableEmployeeModel.getValueAt(row, 0).toString());
			if(nv.getImage()!=null) {
				person_image=nv.getImage();
				ImageIcon icon=new ImageIcon(person_image);
				Image img=icon.getImage().getScaledInstance(130,130, Image.SCALE_SMOOTH);
				imageAvatars.setIcon(new ImageIcon(img));
			}else {
				imageAvatars.setIcon(new ImageIconCustom("images/hinhRong.png", 130, 130));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		for(Thuoc th:lstThuoc) {
//			if(th.getHinhMinhHoa() != null) {
//				hinhThuocByte = th.getHinhMinhHoa();
//				ImageIcon icon = new ImageIcon(hinhThuocByte);
//				Image image = icon.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
//				lblHinhanhThuoc.setIcon(new ImageIcon(image));
//			}
//		}
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
