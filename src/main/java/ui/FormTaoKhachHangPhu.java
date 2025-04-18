package ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import connect.ConnectDB;
import dao.KhachHangDao;
import entity.KhachHang;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FormTaoKhachHangPhu extends JPanel implements ActionListener{

    private JTextField txtMaKH, txtTenKH, txtDiaChi, txtSDT;
    private JButton btnTimKiem, btnTaoKH, btnChonKH;
    private JLabel lblMaKH, lblTenKH, lblSDT, lblDiaChi;
	private DefaultTableModel model;
	private JTable tblKhachHang;
	private KhachHangDao khDao;
	
    public FormTaoKhachHangPhu() throws SQLException {
    	try {
			ConnectDB.getInstance().connectDataBase();
			System.out.println("Success To Connect ChonKhachHang Form!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	khDao=new KhachHangDao();
//        setSize(800, 350);
//        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
//        setContentPane(contentPane);
        khDao=new KhachHangDao();
        // Panel bên trái
        JPanel pnlTrai = new JPanel();
        pnlTrai.setSize(new Dimension(100,250)); // Kích thước
        Border emptyBorder = BorderFactory.createEmptyBorder(20, 10, 14, 12);
        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
        Border compoundBorder = BorderFactory.createCompoundBorder(emptyBorder, lineBorder);
        pnlTrai.setBorder(compoundBorder);
        
        pnlTrai.setBackground(null); // Xoá background

        Box b = Box.createVerticalBox();

//        lblMaKH = new JLabel("Mã khách hàng:");
        lblTenKH = new JLabel("Tên khách hàng:");
        lblSDT = new JLabel("Số điện thoại:");
//        lblDiaChi = new JLabel("Địa chỉ:");

        Font labelFont = lblTenKH.getFont().deriveFont(Font.PLAIN, 16);
//        lblMaKH.setFont(labelFont);
        lblTenKH.setFont(labelFont);
        lblSDT.setFont(labelFont);
//        lblDiaChi.setFont(labelFont);

//        txtMaKH = new JTextField(); txtMaKH.setEditable(false);
        txtTenKH = new JTextField();
        txtSDT = new JTextField();
//        txtDiaChi = new JTextField();

//        lblMaKH.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTenKH.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSDT.setAlignmentX(Component.CENTER_ALIGNMENT);
//        lblDiaChi.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        b.add(Box.createVerticalStrut(10)); 

//        b.add(lblMaKH);
        b.add(Box.createVerticalStrut(10)); 
        b.add(Box.createVerticalStrut(15));
        b.add(lblTenKH);
        b.add(Box.createVerticalStrut(10)); 
        b.add(txtTenKH);
        b.add(Box.createVerticalStrut(15)); 
        b.add(lblSDT);
        b.add(Box.createVerticalStrut(10)); 
        b.add(txtSDT);
        b.add(Box.createVerticalStrut(15)); 
//        b.add(lblDiaChi);
        
//        b.add(txtDiaChi);
        b.add(Box.createVerticalStrut(15));
        btnTimKiem = new JButton("Tìm kiếm");
        
        btnTaoKH = new JButton("Tạo khách hàng");
        btnTimKiem.setAlignmentX(CENTER_ALIGNMENT); btnTaoKH.setAlignmentX(CENTER_ALIGNMENT);
        b.add(btnTimKiem);
        b.add(Box.createVerticalStrut(10)); 
        b.add(Box.createVerticalStrut(15));
        b.add(btnTaoKH);
        b.add(Box.createVerticalStrut(10));
        
        pnlTrai.add(b, BorderLayout.CENTER);

        add(pnlTrai, BorderLayout.WEST);

        // Panel ở giữa và dưới cùng giữ nguyên
        JPanel pnlGiua = new JPanel();
        pnlGiua.setLayout(new BorderLayout());
        pnlGiua.setBorder(new EmptyBorder(10, 0, 10, 10));

        JPanel pnlTimKiem = new JPanel();
        pnlTimKiem.setLayout(new FlowLayout(FlowLayout.CENTER));
        lblMaKH = new JLabel("TÌM KIẾM KHÁCH HÀNG");
        lblMaKH.setFont(new Font(getName(), Font.BOLD, 27));
        pnlTimKiem.add(lblMaKH);

        pnlGiua.add(pnlTimKiem, BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.addColumn("Mã KH");
        model.addColumn("Tên KH");
        
        model.addColumn("Số Điện Thoại");
        model.addColumn("Giới Tính");
//        model.addColumn("Email");
        model.addColumn("Ngày Sinh");
//        model.addColumn("Địa Chỉ");

        tblKhachHang = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tblKhachHang);
        scrollPane.setPreferredSize(new Dimension(500,250));
        pnlGiua.add(scrollPane, BorderLayout.CENTER);

        add(pnlGiua, BorderLayout.CENTER);
        importSQLDataToTable();
//        JPanel pnlDuoi = new JPanel();
//        pnlDuoi.setBorder(new EmptyBorder(0, 10, 10, 10));
//        btnChonKH = new JButton("Chọn khách hàng");
//        pnlDuoi.add(btnChonKH);
//
//        contentPane.add(pnlDuoi, BorderLayout.SOUTH);

        btnTimKiem.addActionListener(this);
        btnTaoKH.addActionListener(this);
//        btnChonKH.addActionListener(this);
    }
    public int getTableRowSelected() {
    	int r=tblKhachHang.getSelectedRow();
    	return r;
    }
    
    public void removeAllTableData() {
		DefaultTableModel dm = (DefaultTableModel) tblKhachHang.getModel();
		dm.getDataVector().removeAllElements();
	}
    
    public KhachHang getKhachHangChon() throws SQLException {
        int r = tblKhachHang.getSelectedRow();
        if (r >= 0) {
            KhachHang kh = khDao.layKhachHangTheoMaKH(model.getValueAt(r,0).toString());
            return kh;
        } else {
            return null; 
        }
    }
    public void importSQLDataToTable() throws SQLException {
		List<KhachHang> list=khDao.layTatCaKhangHang();
		for(KhachHang c:list) {
			SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy",new Locale("vi","VN"));
			Date sinh=java.sql.Date.valueOf(c.getNgaySinh());
			model.addRow(new Object[] {c.getMaKH(),c.getTenKH(),c.getSDT(),
					c.isGioiTinh()==false?"Nam":"Nu",sdf.format(sinh)});
			System.out.println("Imported KhachHang Data!");
		}
	}
    public void importArrayListToTable(ArrayList<KhachHang> list) throws SQLException {
//		List<KhachHang> list=khDao.layTatCaKhangHang();
		for(KhachHang c:list) {
			SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy",new Locale("vi","VN"));
			Date sinh=java.sql.Date.valueOf(c.getNgaySinh());
			model.addRow(new Object[] {c.getMaKH(),c.getTenKH(),c.getSDT(),
					c.isGioiTinh()==false?"Nam":"Nu",sdf.format(sinh)});
			System.out.println("Imported KhachHang Data!");
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnTaoKH)) {
			JFrame frmTaoKH=new JFrame("Tạo Khách Hàng");
			frmTaoKH.setSize(500,200);
			frmTaoKH.setLocationRelativeTo(null);
			JPanel pnlTaoKH=new JPanel();
			pnlTaoKH.setBorder(BorderFactory.createTitledBorder("Nhập Thông Tin Khách Hàng"));
			Box box,bTen,bSDT,bGioi;
			box=Box.createVerticalBox();
			
			bTen=Box.createHorizontalBox();
			JLabel lblTen=new JLabel("Tên Khách Hàng: ");
			JTextField txtTen=new JTextField(20);
			bTen.add(lblTen);
			bTen.createHorizontalStrut(10);
			bTen.add(txtTen);
			
			bSDT=Box.createHorizontalBox();
			JLabel lblsdt=new JLabel("Số Điện Thoại:");
			lblsdt.setPreferredSize(lblTen.getPreferredSize());
			JTextField txtsdt=new JTextField(20);
			bSDT.add(lblsdt);
			bSDT.createHorizontalStrut(10);
			bSDT.add(txtsdt);
			
			bGioi=Box.createHorizontalBox();
			JCheckBox chkNU=new JCheckBox("Nữ");
			bGioi.add(chkNU);
			
			box.add(bTen);
			box.add(bSDT);
			box.add(chkNU);
			pnlTaoKH.add(box);
			frmTaoKH.add(pnlTaoKH);
			JPanel pnlTao=new JPanel();
			JButton btnTao=new JButton("Tạo Khách Hàng");
//			btnTao.setSize(5, HEIGHT);
			pnlTao.add(btnTao);
			frmTaoKH.add(pnlTao,BorderLayout.SOUTH);
			btnTao.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Object o=e.getSource();
					if(o.equals(btnTao)) {
						String tenKH=txtTen.getText().trim();
						String sdt=txtsdt.getText().trim();
						boolean gioi=chkNU.isSelected();
						if(!tenKH.matches("^[A-ZÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ]"
						   		+ "[a-zàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđ]*"
						   		+ "(?:[ ][A-ZÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ]"
						   		+ "[a-zàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđ]*)*$")) {
							JOptionPane.showMessageDialog(null,"Nhập Lại Tên Khách Hàng!");
						}
						else if(!sdt.matches("^(09|08|07|03|05|04|02|06|01)\\d{8}$")) {
							JOptionPane.showMessageDialog(null,"Nhập Lại Số Điện Thoại Khách Hàng!");
						}
						else {
							LocalDate date=LocalDate.now();
							KhachHang kh=new KhachHang(date, tenKH, gioi,"", sdt,"");
							try {
								khDao.taoKhachHang(kh);
								System.out.println("Tạo Khách Hàng Mới Thành Công!");
								frmTaoKH.dispose();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							try {
								removeAllTableData();
								importSQLDataToTable();
								
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}
			});
			frmTaoKH.setVisible(true);
		}
		else if(o.equals(btnTimKiem)) {
			ArrayList<KhachHang> lKH=new ArrayList<KhachHang>();
			String ten=txtTenKH.getText().trim();
			String sdt=txtSDT.getText().trim();
			if(sdt.equalsIgnoreCase("")) {
				try {
					lKH=khDao.layKhachHangTheoTen(ten);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else {
				if(!sdt.matches("^[0-9]+$")) {
					JOptionPane.showMessageDialog(null,"Nhập Lại Số Điện Thoại Tìm Kiếm!");
				}else {
					try {
						lKH=khDao.layKhachHangTheoTenSDT(ten, sdt);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			if(lKH.size()==0) {
				JOptionPane.showMessageDialog(null,"Không Tìm Thấy Khách Hàng!");
			}else {
				removeAllTableData();
				try {
					importArrayListToTable(lKH);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			}
		}
		
	}
}
