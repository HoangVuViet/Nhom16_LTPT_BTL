package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EventListener;

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
import dao.NhanVienDao;
import dao.TaiKhoanDao;
import dao.ThuocDao;
import entity.NhanVien;
import entity.TaiKhoan;
import entity.Thuoc;
import format.ButtonLogin;
import format.PanelPasswordField;
import format.PanelTextField;
import format.ToggleSwitch;

public class FormDangNhap extends JFrame implements ActionListener, MouseListener,KeyListener{
	private JButton btnThoat;	
	private PanelTextField pnlTaikhoan;
	private PanelPasswordField pnlMatkhau;
	private ButtonLogin btnDangnhap;
	private JLabel lblSignUp;
	private JComboBox<String> cboLanguage;
	private JLabel lblVN, lblEng;
	private ToggleSwitch pnlBatTat;
	private String maNVDaDN;
	private TaiKhoanDao tkDao;
	private NhanVienDao nvDao;
	private ThuocDao thDao;
	
	public FormDangNhap(String taikhoan) {
		
		try {
			ConnectDB.getInstance().connectDataBase();
			System.out.println("Succes connect to Form Đăng ký");
		}catch (SQLException e) {
			e.printStackTrace();
		}
		nvDao = new NhanVienDao();
		tkDao = new TaiKhoanDao();
		
		setSize(400,450);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 40, 40));	//40 là đường kính góc bo tròn
		
		setLayout(new BorderLayout());	        
		  // Panel top
		        JPanel panelTop = new JPanel(new BorderLayout());
		        panelTop.setOpaque(false); // Đặt nền trong suốt cho panel
		        add(panelTop, BorderLayout.NORTH);
		        
		        JLabel lblTitle = new JLabel("ĐĂNG NHẬP TÀI KHOẢN");
		        lblTitle.setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa theo chiều ngang
		        lblTitle.setFont(new Font(lblTitle.getFont().getName(), Font.BOLD, 25));
		        panelTop.add(lblTitle, BorderLayout.SOUTH);	      
		        panelTop.setBorder(new EmptyBorder(4, 0, 0, 10));
		        // Button "X" ở góc trên cùng bên phải
		        btnThoat = new JButton("X");
		        panelTop.add(btnThoat, BorderLayout.EAST);
		        btnThoat.setBackground(null);
		        btnThoat.setContentAreaFilled(false);
		        btnThoat.setOpaque(false);
		        btnThoat.setBorder(null);
		        Font font = new Font(btnThoat.getFont().getName(), Font.BOLD, 16);
		        btnThoat.setFont(font);	        
		  //panel center
		        JPanel panelCen = new JPanel();
		        panelCen.setOpaque(false);
		        panelCen.setLayout(new BoxLayout(panelCen, BoxLayout.Y_AXIS));
		        panelCen.setBorder(new EmptyBorder(30,0,0,0));
		        add(panelCen, BorderLayout.CENTER);
		        
		        Box b1,b2,b3,b4,b5,b6,b7;
		        b1= Box.createHorizontalBox();  b2= Box.createHorizontalBox();  b3= Box.createHorizontalBox(); 
		        b4= Box.createHorizontalBox();  b5= Box.createHorizontalBox();  b6= Box.createHorizontalBox(); b7= Box.createHorizontalBox();
		        panelCen.add(b1); panelCen.add(b2); panelCen.add(b3); panelCen.add(b4); panelCen.add(b5); panelCen.add(b6); panelCen.add(b7);
		        
		        b1.setBorder(new EmptyBorder(0, 30, 15, 30)); // Đặt margin trái và phải cho b1
		        b2.setBorder(new EmptyBorder(0, 30, 15, 30)); // Đặt margin trái và phải cho b2
		        b3.setBorder(new EmptyBorder(0, 30, 25, 30)); // Đặt margin trái và phải cho b3
		        b4.setBorder(new EmptyBorder(0, 30, 15, 30)); // Đặt margin trái và phải cho b4
		        b5.setBorder(new EmptyBorder(0, 30, 15, 30)); // Đặt margin trái và phải cho b5
		        b6.setBorder(new EmptyBorder(0, 130, 0, 130)); // Đặt margin trái và phải cho b6
		        b7.setBorder(new EmptyBorder(15, 180, 0, 180)); // Đặt margin trái và phải cho b7
		        
		        b1.add(pnlTaikhoan = new PanelTextField("Tài khoản", "/images/profile-user.png"));
		        pnlTaikhoan.setText(taikhoan);
		        panelCen.add(Box.createVerticalStrut(55));
		        panelCen.add(Box.createVerticalGlue());
		        b2.add(pnlMatkhau = new PanelPasswordField("Mật khẩu", "/images/hidden.png", "/images/padlock.png"));
		        panelCen.add(Box.createVerticalStrut(55));
		        panelCen.add(Box.createVerticalGlue());
		        b4.add(btnDangnhap = new ButtonLogin("Đăng Nhập"));
		        b5.add(lblSignUp = new JLabel("Đăng ký tài khoản !!!"));
		        lblSignUp.setFont(new Font(lblSignUp.getFont().getName(), Font.PLAIN, 15));	
		        
		        cboLanguage = new JComboBox<String>();
	            cboLanguage.setBorder(new EmptyBorder(2,5,2,5));
		        cboLanguage.addItem("Tiếng Việt");
		        cboLanguage.addItem("Tiếng Anh");
		        
		        b6.add(cboLanguage);
		        b7.add(pnlBatTat = new ToggleSwitch());
		        pnlBatTat.setBorderRadius(50);
		        pnlBatTat.setActiveSwitch(Color.GREEN);
		        pnlBatTat.setBorderColor(null);
		  //sự kiện      
		        btnThoat.addActionListener(this);
		        lblSignUp.addMouseListener(this);
		        btnThoat.addMouseListener(this);
		        btnDangnhap.addActionListener(this);
		        pnlMatkhau.addKeyListener(this);
	}
	
	public static void main(String[] args) {
		new FormDangNhap("").setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnThoat)) {
			System.exit(0);
		}
		else if(o.equals(btnDangnhap)) {
			
			try {
				TaiKhoan tk = tkDao.dangNhap(pnlTaikhoan.getTxtUser().getText(), pnlMatkhau.getTxtPass().getText());
				if(tk==null) {
					JOptionPane.showMessageDialog(this, "Đăng nhập thất bại!");
					return;
				}
				else {
					JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
					MainFrame main = new MainFrame(tk);
					
					main.setVisible(true);
					dispose();
				}
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		FormDangKy frmDKy = new FormDangKy();
		frmDKy.setVisible(true);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() == lblSignUp) {
			lblSignUp.setBorder(new MatteBorder(0, 0, 1, 0, Color.red));
			lblSignUp.setForeground(Color.red);
		
		}
		else if(e.getSource() == btnThoat) {
			btnThoat.setForeground(Color.red);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() == lblSignUp) {
			lblSignUp.setBorder(null);
			lblSignUp.setForeground(Color.black);
		}
		else if(e.getSource() == btnThoat) {
			btnThoat.setForeground(Color.black);	
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		Object o=e.getSource();
			 if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				 try {
						TaiKhoan tk = tkDao.dangNhap(pnlTaikhoan.getTxtUser().getText(), pnlMatkhau.getTxtPass().getText());
						if(tk==null) {
							JOptionPane.showMessageDialog(this, "Đăng nhập thất bại!");
							return;
						}
						else {
							JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
							MainFrame main = new MainFrame(tk);
							main.setVisible(true);
							maNVDaDN=tk.getMaNV();
							tkDao.luuLogDangNhap(maNVDaDN);
							dispose();
						}
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
             }
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
