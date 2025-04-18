package format;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

public class PanelPasswordField extends JPanel implements MouseListener {
	
	private JLabel lblUser;
    private JPasswordField txtPass;
	private JLabel lblIcon;
	private JLabel lblEye;
	
	private String click = "close", url = "images/hidden.png";
    
	public PanelPasswordField(String title, String url, String url2 ) {
		setOpaque(false);
		setLayout(new BorderLayout());
		setBorder(new MatteBorder(0,0,1,0, Color.BLACK));
		
		lblUser = new JLabel(title);
		lblUser.setFont(new Font(lblUser.getFont().getName(),Font.ITALIC , 20));
		add(lblUser, BorderLayout.NORTH);
		
		txtPass = new JPasswordField();
		txtPass.setBackground(null);
		txtPass.setOpaque(false);
		txtPass.setBorder(null);
		txtPass.setFont(new Font(lblUser.getFont().getName(),Font.PLAIN , 18));
		add(txtPass, BorderLayout.CENTER);
		
//		lblEye = new JLabel();
//		lblEye.setIcon(new ImageIconCustom(url2,20,20));
//		add(lblEye, BorderLayout.WEST);
	
		lblIcon = new JLabel();
		lblIcon.setIcon(new ImageIconCustom(url, 30, 30));
		add(lblIcon, BorderLayout.EAST);
		
		//sự kiện
		lblIcon.addMouseListener(this);
	}
	public JPasswordField getTxtPass() {
		return txtPass;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == lblIcon) {
			if(click.equals("close")) {
				txtPass.setEchoChar((char)0);
				click = "open";
				lblIcon.setIcon(new ImageIconCustom("images/eye.png",30,30));
			}
			else {
				txtPass.setEchoChar('\u2022');
				lblIcon.setIcon(new ImageIconCustom(url, 30, 30));
				click = "close";
			}
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

