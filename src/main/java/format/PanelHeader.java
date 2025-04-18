 package format;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import dao.DonViTinhDao;
import dao.ThuocDao;
import entity.DonViTinh;
import entity.NhanVien;
import entity.TaiKhoan;
import entity.Thuoc;
import ui.FormDangNhap;
import ui.FormDoiMatKhau;
import ui.MainFrame;

public class PanelHeader extends JPanel implements MouseListener{
	
	private MainFrame main;
	private JLabel lblUserIcon, lblPosition, lblName;
	private JLabel lblEmail, lblNotification, lblExpand;
	private JLabel lblTitle;
	private ArrayList<String> listTb;
	private ThuocDao thDao;
	private SimpleDateFormat dateFormat;
	private JPopupMenu popupMenu;
	private TaiKhoan tk;
	public PanelHeader(MainFrame mainFrame, NhanVien nv,TaiKhoan tk) throws SQLException {
		this.tk=tk;
		this.main = mainFrame;
		dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		setOpaque(false);
		setLayout(new GridBagLayout());
      	thDao=new ThuocDao();
        // Tạo các thành phần JLabel
        lblExpand = new JLabel(new ImageIconCustom("/images/3line.png", 35, 35));
        lblNotification = new JLabel(new ImageIconCustom("/images/notification.png", 30, 30));
//        lblEmail = new JLabel(new ImageIconCustom("gmail.png", 30, 30));
        lblTitle = new JLabel("HMedicine");
    
        JPanel pnlUserInfo = new JPanel();
        pnlUserInfo.setLayout(new GridBagLayout());
        pnlUserInfo.setOpaque(false);
        pnlUserInfo.setBorder(new MatteBorder(0, 1, 0, 0, Color.black));
        GridBagConstraints gbc = new GridBagConstraints();
        //gbc.insets = new Insets(5, 5, 5, 5);
        lblName = new JLabel("Employee Name");
        lblPosition = new JLabel("Employee Position");
        lblUserIcon = new JLabel(new ImageIconCustom("/images/profile-user.png",35,35));
        if(nv != null) {
        	lblName.setText(nv.getEmployeeName());
        	if(nv.isQuanLy()==true) {
            	lblPosition.setText("Quản lý");
        	}else {
        	lblPosition.setText("Nhân viên");
        	}
        }
        Font nameFont = new Font(lblName.getFont().getName(), Font.BOLD, 14);
        lblName.setFont(nameFont);
        lblName.setForeground(Color.DARK_GRAY);
        Font positionFont = new Font(lblName.getFont().getName(), Font.PLAIN, 12);
        lblPosition.setFont(positionFont);
        lblPosition.setForeground(Color.GRAY);
        
        gbc.gridx = 1; // Cột 2
        gbc.gridy = 0; // Dòng 1
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0.7; 
        pnlUserInfo.add(lblName, gbc);

        gbc.gridx = 1; // Cột  2
        gbc.gridy = 1; // Dòng 2
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0.7; 
        pnlUserInfo.add(lblPosition, gbc);

        gbc.gridx = 0; // côt 1
        gbc.gridy = 0; // Dòng 1
        gbc.gridheight = 2; // Chiếm 2 ô dọc
        gbc.anchor = GridBagConstraints.WEST; // Căn lề trái
        pnlUserInfo.add(lblUserIcon, gbc);
        
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc2.gridx = 0;  // Cột 1
        gbc2.gridy = 0;  // Dòng 1
        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.weightx = 0.03;
        add(lblExpand, gbc2);

        gbc2.gridx = 1;  // Cột 2
        gbc2.gridy = 0;  // Dòng 1
        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.weightx = 0.5;
        add(lblTitle, gbc2);

        gbc2.gridx = 2;  // Cột 3
        gbc2.gridy = 0;  // Dòng 1
        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.weightx = 0.05;
        add(lblNotification, gbc2);
        lblNotification.setBorder(new MatteBorder(0, 1, 0, 0, Color.black));
        
        gbc2.gridx = 3;  // Cột 4
        gbc2.gridy = 0;  // Dòng 1
        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.weightx = 0.05;
//        add(lblEmail, gbc2);
//        lblEmail.setBorder(new MatteBorder(0, 1, 0, 0, Color.black));
//
//        gbc2.gridx = 4;  // Cột 1
//        gbc2.gridy = 0;  // Dòng 2
//        gbc2.fill = GridBagConstraints.BOTH;
//        gbc2.weightx = 0.1;
        add(pnlUserInfo, gbc2);
        listTb=new ArrayList<String>();
        popupMenu = new JPopupMenu();
        themThongBao();
        //sự kiện
        lblUserIcon.addMouseListener(this);
        lblNotification.addMouseListener(this);
		
	}
    private void hienThiFormThongTinNV(MouseEvent e) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem changePasswordItem = new JMenuItem("Đổi mật khẩu");
        JMenuItem logoutItem = new JMenuItem("Đăng xuất");
        popupMenu.add(changePasswordItem);
        popupMenu.add(logoutItem);

        changePasswordItem.addActionListener(new ActionListener() {
            private FormDoiMatKhau formDoiMK;

			@Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Bạn Vừa Chọn Đổi Mật Khẩu!");
                formDoiMK=new FormDoiMatKhau(tk);
                formDoiMK.setVisible(true);
            }
        });

        logoutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	FormDangNhap frmDangnhap = new FormDangNhap("");
            	frmDangnhap.setVisible(true);
            	main.dispose();
            }
        });
        popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }
    
   
  
	
    
    public Date getHanSuDung(Date nsx,int hsd) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nsx);
        calendar.add(Calendar.MONTH, hsd);
        return calendar.getTime();
    }
    
    public void themThongBao() throws SQLException {
        listTb = new ArrayList<String>();
        ArrayList<Thuoc> thuocSapHetHan = thDao.kiemTraThuocSapHetHan();
        ArrayList<Thuoc> thuocSapHetSL = thDao.kiemTraThuocCanKietSoLuong();

        LocalDate now = LocalDate.now(); // Lấy ngày hiện tại
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Kiểm tra thuốc sắp hết hạn
        for (Thuoc t : thuocSapHetHan) {
            LocalDate nsx = t.getNgaySanXuat().toLocalDate(); // Lấy ngày sản xuất của Thuoc
            int hanSD = t.getHanSD(); // Lấy hạn sử dụng của Thuoc (số tháng)
            LocalDate han = nsx.plusMonths(hanSD); // Tính ngày hết hạn bằng cách cộng thêm số tháng vào ngày sản xuất
            if (han.isBefore(now)) { // Kiểm tra nếu ngày hết hạn đã qua
                JLabel tb = new JLabel("");
                tb.setText("Thuốc: " + t.getMaThuoc() + " - " + t.getTenThuoc() + " Đã Hết Hạn Dùng (" + df.format(han) + "), Vui Lòng Kiểm Tra!!!");
                popupMenu.add(tb);
            } else { // Ngày hết hạn chưa qua
                JLabel tb1 = new JLabel("");
                tb1.setText("Thuốc: " + t.getMaThuoc() + " - " + t.getTenThuoc() + " Sắp Hết Hạn Dùng (" + df.format(han) + "), Vui Lòng Kiểm Tra!");
                popupMenu.add(tb1);
                System.out.println(tb1.getText());
            }
        }

        // Kiểm tra thuốc sắp hết số lượng
        for (Thuoc th : thuocSapHetSL) {
            if (th.getSoLuong() > 0) { // Chỉ thêm thông báo nếu số lượng > 0
                JLabel tb2 = new JLabel("");
                DonViTinhDao donviDao = new DonViTinhDao();
                DonViTinh donvi = donviDao.timDonViTheoMa(th.getDonViTinh().getMaDonVi());
                tb2.setText("Thuốc: " + th.getMaThuoc() + " - " + th.getTenThuoc() + " Sắp Hết Hàng (Chỉ Còn " + th.getSoLuong() + " " + donvi.getTenDonVi() + ")");
                popupMenu.add(tb2);
                System.out.println(tb2.getText());
            }
        }

        if (popupMenu.getComponentCount() == 0) {
            popupMenu.add("Không Có Thông Báo Mới!");
        }
    }
    public void HienThiThongBao(MouseEvent e) throws SQLException {
    	
		popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }	

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //lam min canh
		g2d.setColor(getBackground());
		g2d.fillRect(0, 0, 25, getHeight());
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(),15,15); // vẽ hcn, 0,0 là toạ độ góc trái, bo góc 15 pixel
		g2d.fillRect(getWidth()-25, getHeight()-25, getWidth(), getHeight());
		super.paintComponent(g);
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		 
		 Object o=e.getSource();
		 if(o.equals(lblNotification)) {
			 try {
				HienThiThongBao(e);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		 }
		 else if(o.equals(lblUserIcon)) {
			 hienThiFormThongTinNV(e);
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
