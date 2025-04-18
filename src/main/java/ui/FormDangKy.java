package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import connect.ConnectDB;
import dao.TaiKhoanDao;
import entity.TaiKhoan;
import format.ConfirmButton;
import format.PanelPasswordField;
import format.PanelTextField;
import format.ToggleSwitch;


public class FormDangKy extends JFrame implements ActionListener, MouseListener{
	private JButton btnExit;	
	private PanelTextField pnlTextField, pnlTextfield3;
	private PanelPasswordField pnlPassfield;
	private ConfirmButton btnDangky;
	private JLabel lblLogin;
	private JComboBox<String> cboLanguage;
	
	private JLabel lblVN, lblEng;
	private ToggleSwitch panelSwitch;
	private TaiKhoanDao tkDao;
	public FormDangKy() {
		
		try {
			ConnectDB.getInstance().connectDataBase();
			System.out.println("Succes connect to Form Đăng ký");
		}catch (SQLException e) {
			e.printStackTrace();
		}
		tkDao = new TaiKhoanDao();
		
		
		setSize(400,500);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 40, 40));	//40 là đường kính góc bo tròn
		
		setLayout(new BorderLayout());	        
		  // Panel top
		        JPanel panelTop = new JPanel(new BorderLayout());
		        panelTop.setOpaque(false); // Đặt nền trong suốt cho panel
		        add(panelTop, BorderLayout.NORTH);
		        
		        JLabel lblTitle = new JLabel("ĐĂNG KÝ TÀI KHOẢN");
		        lblTitle.setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa theo chiều ngang
		        lblTitle.setFont(new Font(lblTitle.getFont().getName(), Font.BOLD, 25));
		        panelTop.add(lblTitle, BorderLayout.SOUTH);	      
		        panelTop.setBorder(new EmptyBorder(4, 0, 0, 10));
		        // Button "X" ở góc trên cùng bên phải
		        btnExit = new JButton("X");
		        panelTop.add(btnExit, BorderLayout.EAST);
		        btnExit.setBackground(null);
		        btnExit.setContentAreaFilled(false);
		        btnExit.setOpaque(false);
		        btnExit.setBorder(null);
		        Font font = new Font(btnExit.getFont().getName(), Font.BOLD, 16);
		        btnExit.setFont(font);	        
		  //panel center
		        JPanel panelCen = new JPanel();
		        panelCen.setOpaque(false);
		        panelCen.setLayout(new BoxLayout(panelCen, BoxLayout.Y_AXIS));
		        panelCen.setBorder(new EmptyBorder(30,0,0,0));
		        add(panelCen, BorderLayout.CENTER);
		        
		        Box b1,b2,b3,b4,b5,b6, b7;
		        b1= Box.createHorizontalBox();  b2= Box.createHorizontalBox();  b3= Box.createHorizontalBox(); 
		        b4= Box.createHorizontalBox();  b5= Box.createHorizontalBox();  b6= Box.createHorizontalBox(); b7= Box.createHorizontalBox();
		        panelCen.add(b1); panelCen.add(b2); panelCen.add(b3); panelCen.add(b4); panelCen.add(b5); panelCen.add(b6); panelCen.add(b7);
		        //căn lềS
		        b1.setBorder(new EmptyBorder(0, 30, 15, 30)); 
		        b2.setBorder(new EmptyBorder(0, 30, 15, 30)); 
		        b3.setBorder(new EmptyBorder(0, 30, 25, 30)); 
		        b4.setBorder(new EmptyBorder(0, 30, 15, 30)); 
		        b5.setBorder(new EmptyBorder(0, 30, 15, 30)); 
		        b6.setBorder(new EmptyBorder(0, 130, 0, 130)); 
		        b7.setBorder(new EmptyBorder(15, 180, 0, 180)); 
		        
		        b1.add(pnlTextField = new PanelTextField("Tài khoản", "/images/profile-user.png"));
		        panelCen.add(Box.createVerticalStrut(55));
		        panelCen.add(Box.createVerticalGlue());
		        b2.add(pnlPassfield = new PanelPasswordField("Mật khẩu", "images/hidden.png", "images/padlock.png"));
		        panelCen.add(Box.createVerticalStrut(55));
		        panelCen.add(Box.createVerticalGlue());
		        b3.add(pnlTextfield3 = new PanelTextField("Mã nhân viên", "images/email.png"));
		        panelCen.add(Box.createVerticalStrut(55));
		        panelCen.add(Box.createVerticalGlue());
		        b4.add(btnDangky = new ConfirmButton());
		        b5.add(lblLogin = new JLabel("Tôi đã có tài khoản !!!"));
		        lblLogin.setFont(new Font(lblLogin.getFont().getName(), Font.PLAIN, 15));	
		        
		        cboLanguage = new JComboBox<String>();
	            cboLanguage.setBorder(new EmptyBorder(2,5,2,5));
		        cboLanguage.addItem("Tiếng Việt");
		        cboLanguage.addItem("Tiếng Anh");
		        
		        b6.add(cboLanguage);
		        b7.add(panelSwitch = new ToggleSwitch());
		        panelSwitch.setBorderRadius(50);
		        panelSwitch.setActiveSwitch(Color.GREEN);
		        panelSwitch.setBorderColor(null);
		  //sự kiện      
		        btnExit.addActionListener(this);
		        lblLogin.addMouseListener(this);
		        btnExit.addMouseListener(this);
		        btnDangky.addActionListener(this);
	}
	
	public static void main(String[] args) {
		new FormDangKy().setVisible(true);
	}
	@Override
	public void paintComponents(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		GradientPaint gp =  new GradientPaint(0, 0, Color.decode("#EDEAFC"), getWidth() , getHeight(), Color.decode("#CFDEF3"));
		g2d.setPaint(gp);
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
		super.paintComponents(g);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnExit)) {
			System.exit(0);
		}
		else if(o.equals(btnDangky)) {
			int dk = JOptionPane.showConfirmDialog(this, "Bạn có muốn đăng ký", "Xác nhận", JOptionPane.YES_NO_OPTION);
			if(dk == JOptionPane.NO_OPTION) {
				return;
			}
			try {
				TaiKhoan tk = new TaiKhoan();
				tk.setTenDangNhap(pnlTextField.getTxtUser().getText());
				tk.setMatkhau(pnlPassfield.getTxtPass().getText());
				tk.setMaNV(pnlTextfield3.getTxtUser().getText());
				
				boolean kq = tkDao.taoTaiKhoan(tk);
				if(pnlTextField.getTxtUser().getText().equals("") ||pnlPassfield.getTxtPass().getText().equals("") ||
						pnlTextfield3.getTxtUser().getText().equals("")) {
					JOptionPane.showMessageDialog(this, "Thông tin không được để trống!");
				}
				else if(kq){
					JOptionPane.showMessageDialog(this, "Đăng ký thành công!");
					FormDangNhap frmDangnhap = new FormDangNhap(tk.getTenDangNhap());
					this.dispose();
					frmDangnhap.setVisible(true);
				}else 
				{
					JOptionPane.showMessageDialog(this, "Đăng ký thất bại!");
				}
			}catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if(o.equals(lblLogin)) {
			FormDangNhap frmDN = new FormDangNhap(null);
			frmDN.setVisible(true);
			dispose();
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
		if(e.getSource() == lblLogin) {
			lblLogin.setBorder(new MatteBorder(0, 0, 1, 0, Color.red));
			lblLogin.setForeground(Color.red);
		}
		else if(e.getSource() == btnExit) {
			btnExit.setForeground(Color.red);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() == lblLogin) {
			lblLogin.setBorder(null);
			lblLogin.setForeground(Color.black);
		}
		else if(e.getSource() == btnExit) {
			btnExit.setForeground(Color.black);	
		}
	}
}

