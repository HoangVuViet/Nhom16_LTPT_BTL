package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import connect.ConnectDB;
import dao.HoaDonDao;
import dao.KhachHangDao;
import dao.NhanVienDao;
import entity.HoaDon;
import entity.NhanVien;
import entity.TaiKhoan;
import format.PanelBorder;
import format.RoundedBorder;
import org.jdesktop.swingx.JXDatePicker;

public class FormHoaDon extends JPanel implements ActionListener, MouseListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PanelBorder roundPanel1,roundPanel2;
    private JButton btnThem, btnXoa, btnTimkiem, btnXoaRong, btnCapnhat,btnXemChiTiet;
    private DefaultTableModel tableModel;
    private JTable tblHoadon;
    private JButton btnTrangthaiHoadon;
    private JTextField txtTimNV,txtTimKH,txtTimHD;
    private JComboBox<String> cboTimKiem,cboNV;
    private JXDatePicker ngayMua;
    private TaiKhoan tkDN;
    private HoaDonDao hdDao;
    private WindowListener windowListener;
    private KhachHangDao khDao;
    private NhanVienDao nvDao;
    private FormTaoHoaDon frmTaoHoaDon;
    private DateTimeFormatter formatter;
	private NumberFormat currencyFormatter;
	private FormTaoHoaDon frmTaoHD;
	
	
    public FormHoaDon(TaiKhoan tk) throws SQLException {
    	tkDN=tk;
    	nvDao=new NhanVienDao();
    	khDao=new KhachHangDao();
    	hdDao=new HoaDonDao();
        try {
            ConnectDB.getInstance().connectDataBase();
            System.out.println("Success To Connect HoaDon Form!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        roundPanel1 = new PanelBorder();
        roundPanel1.setBackground(Color.decode("#FEFBF6"));

        Box box = Box.createVerticalBox();

        Box b1 = Box.createHorizontalBox();
        Box b2 = Box.createHorizontalBox();
        Box b3 = Box.createHorizontalBox();
        Box b4 = Box.createHorizontalBox();

        box.setBorder(new TitledBorder("Tìm Kiếm Theo"));
        txtTimHD=new JTextField(10);
        JLabel lblDate = new JLabel("Ngày Mua Hàng: ");
        ngayMua = new JXDatePicker();
        cboTimKiem = new JComboBox<String>();
//        cboTimKiem.setPreferredSize(new Dimension(200, 20));
        cboTimKiem.addItem("Mã Hoá Đơn");
        cboTimKiem.addItem("Tên Khách Hàng");
        cboTimKiem.addItem("Số Điện Thoại");
        cboTimKiem.setSelectedIndex(0);
        b1.add(cboTimKiem);
        b1.add(txtTimHD);
        b1.add(Box.createHorizontalStrut(10)); 
        b1.add(lblDate);
        b1.add(ngayMua);

        JLabel lblNV = new JLabel("Tìm Theo Nhân Viên: ");
//        JComboBox<String> cboNV = new JComboBox<>();
        ArrayList<NhanVien> listNV=nvDao.layTatCaNhanVien();
        cboNV=new JComboBox<String>();
        NhanVien nv=nvDao.layNhanVienTheoMa(tk.getMaNV());
        int t=listNV.indexOf(nv);
        
        for(NhanVien n:listNV) {
        	cboNV.addItem(n.getEmployeeName());
        }
//        cboNV.addItem("");
        cboNV.setSelectedItem(null);
        b1.add(Box.createHorizontalStrut(10)); 
        b1.add(lblNV);
        b1.add(cboNV);
        
        btnTimkiem = new JButton("Tìm kiếm");
        btnThem = new JButton("Tạo Hoá Đơn");
        btnXoa = new JButton("Xoá Hoá Đơn");
        btnXoaRong = new JButton("Xoá Rỗng");
        btnCapnhat = new JButton("Làm Mới");
        btnXemChiTiet=new JButton("Xem Chi Tiết");
//        btnTrangthaiHoadon = new JButton("Đổi trạng thái giao hàng");
        b4.add(btnTimkiem);
        b4.add(btnThem);
        b4.add(btnCapnhat);
        b4.add(btnXoa);
        b4.add(btnXoaRong);
        b4.add(btnXemChiTiet);
//        b4.add(btnTrangthaiHoadon);
        b4.add(Box.createHorizontalStrut(10)); 
        box.add(Box.createVerticalStrut(10));
        box.add(b1);
        box.add(Box.createVerticalStrut(10));
        box.add(b2);
//        box.add(Box.createVerticalStrut(10));
//        box.add(b3);
        box.add(Box.createVerticalStrut(10));
        box.add(b4);
        box.add(Box.createVerticalStrut(10));

      
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        roundPanel1.setLayout(new GridBagLayout());

        roundPanel1.add(box, constraints);
        roundPanel2 = new PanelBorder();
        roundPanel2.setBackground(Color.decode("#FEFBF6"));
        
        PanelBorder pnlBott = new PanelBorder();
        String[] header = { "Mã HĐ", "Thời Gian", "Khách Hàng", "Số Điện Thoại", "Tổng Tiền(VND)","Đã Thanh Toán(VND)","Nhân Viên"};
        tableModel = new DefaultTableModel(header, 0) {
        	@Override
            public boolean isCellEditable(int row, int column) {
               
                return column ==-1;
            }
        };;
        tblHoadon = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblHoadon);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        scrollPane.setPreferredSize(new Dimension(1050, 400));
        pnlBott.add(scrollPane, constraints);
        TableColumn columnCode = tblHoadon.getColumnModel().getColumn(0);
        columnCode.setPreferredWidth(80);
        columnCode.setCellRenderer(centerRenderer);
        TableColumn columnDate = tblHoadon.getColumnModel().getColumn(1);
        columnDate.setPreferredWidth(100);
        columnDate.setCellRenderer(centerRenderer);
        TableColumn columnCustomer = tblHoadon.getColumnModel().getColumn(2);
        columnCustomer.setPreferredWidth(130);
        columnCustomer.setCellRenderer(centerRenderer);
        TableColumn columnPhone = tblHoadon.getColumnModel().getColumn(3);
        columnPhone.setPreferredWidth(90);
        columnPhone.setCellRenderer(centerRenderer);
        TableColumn columnTotal = tblHoadon.getColumnModel().getColumn(4);
        columnTotal.setPreferredWidth(100);
        columnTotal.setCellRenderer(centerRenderer);
        TableColumn columnInsurance = tblHoadon.getColumnModel().getColumn(5);
        columnInsurance.setPreferredWidth(100);
        columnInsurance.setCellRenderer(centerRenderer);
        TableColumn columnEmployee = tblHoadon.getColumnModel().getColumn(6);
        columnEmployee.setPreferredWidth(130);
        columnEmployee.setCellRenderer(centerRenderer);
//        TableColumn columnStatus=tblHoadon.getColumnModel().getColumn(7);
//        columnStatus.setPreferredWidth();
        pnlBott.setBorder(new TitledBorder("Danh Sách Hoá Đơn:"));

        roundPanel2.add(pnlBott, constraints);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(this.roundPanel1, -1, -1, 32767)
                .addComponent(this.roundPanel2, -1, -1, 32767)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(this.roundPanel1, -2, -1, -2)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(this.roundPanel2, -1, -1, 32767)
                )
        );
        layDuLieuHoaDonVaoTable();
        //event
//        btnTrangthaiHoadon.addActionListener(this);
        btnThem.addActionListener(this);
        btnXoa.addActionListener(this);
        btnXoaRong.addActionListener(this);
        btnCapnhat.addActionListener(this);
        btnTimkiem.addActionListener(this);
        tblHoadon.addMouseListener(this);
        btnXemChiTiet.addActionListener(this);
    }
    public void removeAllTableData() {
		DefaultTableModel dm = (DefaultTableModel) tblHoadon.getModel();
		dm.getDataVector().removeAllElements();
	}
    
    public void layDuLieuHoaDonVaoTable() throws SQLException {
    	ArrayList<HoaDon> listHD=hdDao.layTatCaHoaDonGiamDan();
    	formatter = DateTimeFormatter.ofPattern("HH:mm:ss - dd/MM/yyyy ");
    	currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    	for(HoaDon hd:listHD) {
    		String nv=nvDao.layNhanVienTheoMa(hd.getMaNV()).getEmployeeName();
    		String tenKH=khDao.layKhachHangTheoMaKH(hd.getMaKH()).getTenKH();
    		String sdtKH=khDao.layKhachHangTheoMaKH(hd.getMaKH()).getSDT();
    		
    		tableModel.addRow(new Object[] {
    				hd.getMaHD(),(hd.getThoiGianXuatHD()).format(formatter),tenKH,sdtKH,
    				currencyFormatter.format(hd.getTongTien()),currencyFormatter.format(hd.getTienNhan())+" - "+hd.getPhuongThucTT(),nv
    		});
    	}
    }
    public void layDuLieuHoaDonTuArrayVaoTable(ArrayList<HoaDon> listHD) throws SQLException {
//    	ArrayList<HoaDon> listHD=hdDao.layTatCaHoaDonGiamDan();
    	ArrayList<HoaDon> listHDSapXepGiamDan = new ArrayList<>(listHD);
    	listHDSapXepGiamDan.sort((h1, h2) -> h2.getThoiGianXuatHD().compareTo(h1.getThoiGianXuatHD()));
    	formatter = DateTimeFormatter.ofPattern("HH:mm:ss - dd/MM/yyyy ");
    	currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    	for(HoaDon hd:listHDSapXepGiamDan) {
    		String nv=nvDao.layNhanVienTheoMa(hd.getMaNV()).getEmployeeName();
    		String tenKH=khDao.layKhachHangTheoMaKH(hd.getMaKH()).getTenKH();
    		String sdtKH=khDao.layKhachHangTheoMaKH(hd.getMaKH()).getSDT();
    		
    		tableModel.addRow(new Object[] {
    				hd.getMaHD(),(hd.getThoiGianXuatHD()).format(formatter),tenKH,sdtKH,
    				currencyFormatter.format(hd.getTongTien()),currencyFormatter.format(hd.getTienNhan())+" - "+hd.getPhuongThucTT(),nv
    		});
    	}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnThem)) {
        	JFrame frmTaoHoaDon=new JFrame();
        	try {
				frmTaoHD = new FormTaoHoaDon(tkDN);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
           
            frmTaoHoaDon.setTitle("Tạo Hoá Đơn");
            frmTaoHoaDon.setLocation(510, 170);
            frmTaoHoaDon.setSize(1000, 500);
            frmTaoHoaDon.add(frmTaoHD);
            JPanel pnlXuat=new JPanel();
            JButton btnXuatHD=new JButton("Xuất Hoá Đơn");
            btnXuatHD.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							Object o=e.getSource();
							if(o.equals(btnXuatHD)) {
								
								try {
									if(frmTaoHD.XuatHD()==true) {
										String maHD;
										
										try {
											maHD = hdDao.layMaHDMoiTao();
											frmTaoHoaDon.setVisible(false);
											int option =JOptionPane.showInternalConfirmDialog(null, "Bạn Có Muốn In Hoá Đơn?","In Hoá Đơn",JOptionPane.YES_NO_OPTION);
											if(option==JOptionPane.YES_OPTION) {
												frmTaoHD.inHD(maHD);
											}else {
												System.out.println("Không In Hoá Đơn!");
											}
										} catch (SQLException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}

									frmTaoHoaDon.dispose();
									removeAllTableData();
									try {
										layDuLieuHoaDonVaoTable();
									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									}
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
			});
		   
            pnlXuat.add(btnXuatHD);
            frmTaoHoaDon.add(pnlXuat,BorderLayout.SOUTH);
            frmTaoHoaDon.setVisible(true);
            
            
            
           
            
        }
        else if(o.equals(btnXoa)) {
        	int r=tblHoadon.getSelectedRow();
        	if(r == -1) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 Hoá Đơn cần xoá!");
				return;
			}
        	int choice=JOptionPane.showConfirmDialog(btnXoa, ("Bạn Có Chắc Muốn Xoá "+tableModel.getValueAt(r, 0).toString()+"?"),"Xác Nhận Xoá!",JOptionPane.YES_NO_OPTION);
        	 if (choice == JOptionPane.YES_OPTION) {
        		 
     				try {
						hdDao.xoaHoaDonVaCTHDTheoMaHD(tableModel.getValueAt(r, 0).toString());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
     				System.out.println("Đã xoá "+tableModel.getValueAt(r, 0).toString());
     			    System.out.println("Deleted!");
             } else if (choice == JOptionPane.NO_OPTION) {
                 // Người dùng nhấn No
                 System.out.println("Canceled!");
             }
        	removeAllTableData();
			try {
				layDuLieuHoaDonVaoTable();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        else if(o.equals(btnXoaRong)) {
//        	txtTim.setText("");
        	txtTimHD.setText("");
//        	txtTimKH.setText("");
//        	txtTimNV.setText("");
//        	cboKH.setSelectedItem(null);
        	cboNV.setSelectedItem(null);
        	cboTimKiem.setSelectedItem(0);
        	ngayMua.setDate(null);
        }
        else if(o.equals(btnCapnhat)) {
        	try {
        		removeAllTableData();
        		
				layDuLieuHoaDonVaoTable();
				System.out.println("Form HoaDon Updated!");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        }
        else if(o.equals(btnTimkiem)) {
        	ArrayList<HoaDon> lHD=new ArrayList<HoaDon>();
        	int tim=cboTimKiem.getSelectedIndex();
        	String txtTim=txtTimHD.getText().trim();
        	
        	ArrayList<NhanVien> lNV=null;
        	NhanVien n=new NhanVien();
			try {
				if(cboNV.getSelectedItem()==null) {
					n.setMaNV("");
				}else {
					lNV = nvDao.timNhanVienTheoTen(cboNV.getSelectedItem().toString());
					n.setMaNV(lNV.get(0).getMaNV());
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

        	
        	if(ngayMua.getDate()==null) {
        		if(tim==0) {
	        		lHD=hdDao.timHoaDonBangMaHDMaNV(txtTim,n.getMaNV());
	        	}else
	        	if(tim==1) {
	        		lHD=hdDao.timHoaDonBangTenMaNV(txtTim,n.getMaNV());
	        	}else
	        	if(tim==2) {
	        		if(!txtTim.matches("-?\\d+")&&!txtTim.equals("")) {
	        			JOptionPane.showMessageDialog(null,"Số Điện Thoại Tìm Kiếm Phải Là Số!");
	        			txtTimHD.setText("");
	        		}else {
	        			if(txtTim.equals("")) {
	        				
	        			}else {
	        				
	        			}
	        		}
	        	}
        	}else {
        		
        		Date utilDate = ngayMua.getDate(); // Tạo một java.util.Date
        		java.time.LocalDate localDate = utilDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            	java.sql.Date ngayTim=java.sql.Date.valueOf(localDate);
        		if(cboNV.getSelectedItem()==null) {
            		lHD=hdDao.timHoaDonBangMaHDThoiGianMaNV(txtTim,ngayTim,"");
            	}else {
    	        	if(tim==0) {
    	        		lHD=hdDao.timHoaDonBangMaHDThoiGianMaNV(txtTim,ngayTim,n.getMaNV());
    	        	}else
    	        	if(tim==1) {
    	        		lHD=hdDao.timHoaDonBangTenKHThoiGianMaNV(txtTim, ngayTim,n.getMaNV());
    	        	}else
    	        	if(tim==2) {
    	        		if(!txtTim.matches("-?\\d+")) {
    	        			JOptionPane.showMessageDialog(null,"Nhập Lại Số Điện Thoại Tìm Kiếm!");
    	        			txtTimHD.setText("");
    	        		}else {
    	        			lHD=hdDao.timHoaDonBangSoDTKHThoiGianMaNV(txtTim, ngayTim,n.getMaNV());
    	        		}
    	        		
    	        	}
            	}
        	}
        	if(lHD.size()==0) {
        		JOptionPane.showMessageDialog(null,"Không Tìm Thấy Hoá Đơn!");
        		removeAllTableData();
        		try {
					layDuLieuHoaDonVaoTable();;
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}else {
        		try {
        			removeAllTableData();
					layDuLieuHoaDonTuArrayVaoTable(lHD);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		System.out.println("Tim HoaDon Thanh Cong!");
        	}
        }
        else if(o.equals(btnXemChiTiet)) {
        	int r=tblHoadon.getSelectedRow();
        	if(r>=0) {
        		try {
    				FormXemChiTietHoaDon f=new FormXemChiTietHoaDon(tableModel.getValueAt(r, 0).toString());
    				f.setVisible(true);
    			} catch (SQLException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
        	}else {
        		JOptionPane.showMessageDialog(null,"Chọn Một Hoá Đơn Trước!");
        	}
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
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
