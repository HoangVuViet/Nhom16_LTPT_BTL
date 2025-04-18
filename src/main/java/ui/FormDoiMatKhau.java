package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dao.TaiKhoanDao;
import entity.TaiKhoan;
import format.PanelPasswordField;

public class FormDoiMatKhau extends JFrame implements ActionListener,KeyListener{
	private PanelPasswordField pnlMKCu,pnlMKMoi,pnlNhapLai;
	private JPasswordField txtMKCu,txtMKMoi,txtNhapLai;
	private JButton btnXacNhan;
	private TaiKhoan tkdoi;
	private TaiKhoanDao tkDao;
	
	public FormDoiMatKhau(TaiKhoan tk) {
		tkDao=new TaiKhoanDao();
		tkdoi=tk;
//		setTitle("Đổi Mật Khẩu");
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(300,320);
		JPanel pnlDoiMK=new JPanel();
		pnlDoiMK.setBorder(BorderFactory.createTitledBorder("Đổi Mật Khẩu"));
		Box box,b1,b2,b3;
		box=Box.createVerticalBox();
		b1=Box.createHorizontalBox();
//		JLabel lblmkCU=new JLabel("Mật Khẩu Cũ:");
		pnlMKCu=new PanelPasswordField("Mật Khẩu Cũ", "images/hidden.png", "images/padlock.png");
//		b1.add(lblmkCU);
		b1.add(pnlMKCu);
		
		box.add(b1);
		box.createVerticalStrut(10);
		
		b2=Box.createHorizontalBox();
//		JLabel lblmkMoi=new JLabel("Mật Khẩu Mới:");
		pnlMKMoi=new PanelPasswordField("Mật Khẩu Mới", "images/hidden.png", "images/padlock.png");
//		b2.add(lblmkMoi);
		b2.add(pnlMKMoi);
		
		box.add(b2);
		box.createVerticalStrut(10);
		
		b3=Box.createHorizontalBox();
//		JLabel lblnhapLai=new JLabel("Nhập Lại Mật Khẩu Mới: ");
		pnlNhapLai=new PanelPasswordField("Nhập Lại Mật Khẩu Mới", "/images/hidden.png", "/images/padlock.png");
//		b3.add(lblnhapLai);
		b3.add(pnlNhapLai);
		
		box.add(b3);
		box.createVerticalStrut(20);
		
		
	
//		
//		lblmkCU.setPreferredSize(lblnhapLai.getPreferredSize());
//		lblmkMoi.setPreferredSize(lblnhapLai.getPreferredSize());
		
		pnlDoiMK.add(box);
		add(pnlDoiMK);
	
		btnXacNhan=new JButton("Xác Nhận");
		add(btnXacNhan,BorderLayout.SOUTH);
		
		System.out.println(tkdoi.getMatkhau());
		pnlMKCu.addKeyListener(this);
		pnlMKMoi.addKeyListener(this);
		pnlNhapLai.addKeyListener(this);
		btnXacNhan.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o=e.getSource();
		if(o.equals(btnXacNhan)) {
			
			if(!pnlMKCu.getTxtPass().getText().trim().equals(tkdoi.getMatkhau())) {
//				System.out.println(pnlMKCu.getTxtPass().getText());
				JOptionPane.showMessageDialog(null,"Mật Khẩu Cũ Không Chính Xác!");
			}else if(pnlMKMoi.getTxtPass().getText().trim()=="" || pnlMKMoi.getTxtPass()==null) {
				JOptionPane.showMessageDialog(null,"Nhập Mật Khẩu Mới!");
			}else if(pnlMKMoi.getTxtPass().getText().trim().equals(tkdoi.getMatkhau())) {
				JOptionPane.showMessageDialog(null,"Nhập Mật Khẩu Mới Khác Mật Khẩu Cũ!");
			}else if(!pnlMKMoi.getTxtPass().getText().trim().equals(pnlNhapLai.getTxtPass().getText().trim())) {
				JOptionPane.showMessageDialog(null,"Mật Khẩu Mới Và Nhập Lại Không Trùng Khớp!");
			}else {
				try {
					tkDao.doiMatKhau(tkdoi,pnlMKMoi.getTxtPass().getText().trim());
					tkdoi.setMatkhau(pnlMKMoi.getTxtPass().getText().trim());
					dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		
        		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
